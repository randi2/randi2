SET AUTOCOMMIT=0;
SET FOREIGN_KEY_CHECKS=0;
START TRANSACTION;

DROP database IF EXISTS randi2;

create database randi2;

GRANT ALL PRIVILEGES ON randi2.* TO 'randi2'@'%'
  IDENTIFIED BY 'randi2';

USE randi2;
  
CREATE TABLE Person (
  personenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  stellvertreterID INT UNSIGNED NULL,
  nachname VARCHAR(50) NOT NULL,
  vorname VARCHAR(50) NOT NULL,
  titel ENUM('Prof.', 'Dr.', 'Prof. Dr.') NULL,
  geschlecht ENUM('w', 'm') NOT NULL,
  telefonnummer VARCHAR(26) NOT NULL,
  handynummer VARCHAR(26) NULL,
  fax VARCHAR(26) NULL,
  email VARCHAR(26) NOT NULL,
  PRIMARY KEY(personenID),
  INDEX Person_FKIndex1(stellvertreterID),
  FOREIGN KEY(stellvertreterID)
    REFERENCES Person(personenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE Zentrum (
  zentrumsID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  ansprechpartnerID INT UNSIGNED NOT NULL,
  institution VARCHAR(70) NOT NULL,
  abteilungsname VARCHAR(70) NOT NULL,
  ort VARCHAR(50) NOT NULL,
  plz CHAR(5) NOT NULL,
  strasse VARCHAR(50) NOT NULL,
  hausnummer VARCHAR(6) NOT NULL,
  passwort CHAR(64) NOT NULL,
  aktiviert BOOL NOT NULL,
  PRIMARY KEY(zentrumsID),
  FOREIGN KEY(ansprechpartnerID)
    REFERENCES Person(personenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;


CREATE TABLE Benutzerkonto (
  benutzerkontenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Person_personenID INT UNSIGNED NOT NULL,
  loginname VARCHAR(14) NOT NULL,
  passwort CHAR(64) NOT NULL,
  rolle ENUM('Studienarzt', 'Studienleiter', 'System Operator', 'Administrator') NOT NULL,
  erster_login DATETIME NULL,
  letzter_login DATETIME NULL,
  gesperrt BOOL NOT NULL,
  PRIMARY KEY(benutzerkontenID),
  INDEX Benutzerkonto_FKIndex1(Person_personenID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE Aktivierung (
  aktivierungsID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  aktivierungslink CHAR(20) NOT NULL UNIQUE,
  versanddatum DATE NOT NULL,
  PRIMARY KEY(aktivierungsID),
  INDEX Aktivierung_FKIndex1(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;



CREATE TABLE Studie (
  studienID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL,
  beschreibung TEXT NULL,
  startdatum DATE NOT NULL,
  enddatum DATE NOT NULL,
  studienprotokoll LONGBLOB NOT NULL,
  randomisationArt ENUM('Vollstaendige', 'Block', 'Block mit Strata', 'Minimisation') NOT NULL,
  status_Studie ENUM('fortsetzen', 'pausieren', 'stoppen') NOT NULL,
  PRIMARY KEY(studienID),
  INDEX Studie_FKIndex1(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;



CREATE TABLE Studienarm (
  studienarmID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  status_aktivitaet TINYINT UNSIGNED NOT NULL,
  bezeichnung VARCHAR(50) NOT NULL,
  beschreibung TEXT NULL,
  PRIMARY KEY(studienarmID),
  INDEX Studienarm_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;


CREATE TABLE Studie_has_Zentrum (
  Studie_studienID INT UNSIGNED NOT NULL,
  Zentrum_zentrumsID INT UNSIGNED NOT NULL,
  PRIMARY KEY(Studie_studienID, Zentrum_zentrumsID),
  INDEX Studie_has_Zentrum_FKIndex1(Studie_studienID),
  INDEX Studie_has_Zentrum_FKIndex2(Zentrum_zentrumsID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Zentrum_zentrumsID)
    REFERENCES Zentrum(zentrumsID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;


CREATE TABLE Patient (
  patientenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  Studienarm_studienarmID INT UNSIGNED NOT NULL,
  initialen VARCHAR(4) NOT NULL,
  geburtsdatum DATE NOT NULL,
  geschlecht ENUM('weiblich', 'maennlich') NOT NULL,
  aufklaerungsdatum DATE NOT NULL,
  koerperoberflaeche FLOAT NOT NULL,
  performancestatus ENUM('0', '1', '2', '3', '4') NOT NULL,
  PRIMARY KEY(patientenID),
  INDEX Patient_FKIndex1(Studienarm_studienarmID),
  INDEX Patient_FKIndex2(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Studienarm_studienarmID)
    REFERENCES Studienarm(studienarmID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE Strata_Typen (
  strata_TypenID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  name VARCHAR(40) NOT NULL,
  beschreibung TEXT NULL,
  PRIMARY KEY(strata_TypenID),
  INDEX Strata_Typen_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE Strata_Auspraegung (
  strata_WerteID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Strata_Typen_strata_TypenID INTEGER UNSIGNED NOT NULL,
  wert VARCHAR(100) NOT NULL,
  PRIMARY KEY(strata_WerteID),
  INDEX Strata_Werte_FKIndex1(Strata_Typen_strata_TypenID),
  FOREIGN KEY(Strata_Typen_strata_TypenID)
    REFERENCES Strata_Typen(strata_TypenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;


CREATE TABLE Strata_Werte_has_Patient (
  Strata_Auspraegung_strata_WerteID INTEGER UNSIGNED NOT NULL,
  Patient_patientenID INT UNSIGNED NOT NULL,
  PRIMARY KEY(Strata_Auspraegung_strata_WerteID, Patient_patientenID),
  INDEX Strata_Werte_has_Patient_FKIndex1(Strata_Auspraegung_strata_WerteID),
  INDEX Strata_Werte_has_Patient_FKIndex2(Patient_patientenID),
  FOREIGN KEY(Strata_Auspraegung_strata_WerteID)
    REFERENCES Strata_Auspraegung(strata_WerteID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Patient_patientenID)
    REFERENCES Patient(patientenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE Block(
	blockId INTEGER UNSIGNED NOT NULL PRIMARY KEY 		AUTO_INCREMENT,
	block_studienId INTEGER UNSIGNED NULL,
	block_wert INTEGER UNSIGNED NOT NULL,
	FOREIGN KEY (Studie_studienId) 
		REFERENCES Studie(studienId)
		ON DELETE CASCADE
		ON UPDATE NO ACTION

)
TYPE=InnoDB;

CREATE VIEW Strata_von_Studienarm
AS
SELECT s.studienarmId, sa.Strata_Typen_strata_TypenID, sa.strata_WerteID,
    count(p.patientenID) AS anzahl
FROM Studienarm s
JOIN Patient p ON s.studienarmId = p.Studienarm_studienarmId
JOIN Strata_Werte_has_Patient swp ON p.patientenId = swp.Patient_patientenId
JOIN Strata_Auspraegung sa ON swp.Strata_Auspraegung_strata_WerteID = sa.strata_WerteId
GROUP BY s.studienarmId, sa.Strata_Typen_strata_TypenID, sa.strata_WerteID;

COMMIT;