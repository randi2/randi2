use randi2;

SET FOREIGN_KEY_CHECKS=0;

SET AUTOCOMMIT=0;
START TRANSACTION;

DROP TABLE IF EXISTS `Person`;
CREATE TABLE Person (
  personenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Person_personenID INT UNSIGNED NULL,
  nachname VARCHAR(50) NOT NULL,
  vorname VARCHAR(50) NOT NULL,
  titel VARCHAR(20) NULL,
  geschlecht VARCHAR(20) NOT NULL,
  telefonnummer VARCHAR(26) NOT NULL,
  handynummer VARCHAR(26) NULL,
  fax VARCHAR(50) NULL,
  email VARCHAR(50) NOT NULL,
  PRIMARY KEY(personenID),
  INDEX Person_FKIndex1(Person_personenID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE SET NULL
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Zentrum`;
CREATE TABLE Zentrum (
 zentrumsID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Person_personenID INT UNSIGNED NOT NULL,
  institution VARCHAR(70) NOT NULL,
  abteilungsname VARCHAR(70) NOT NULL,
  ort VARCHAR(50) NOT NULL,
  plz CHAR(5) NOT NULL,
  strasse VARCHAR(50) NOT NULL,
  hausnummer VARCHAR(20) NOT NULL,
  passwort CHAR(64) NOT NULL,
  aktiviert BOOL NOT NULL,
  PRIMARY KEY(zentrumsID),
  INDEX Zentrum_FKIndex1(Person_personenID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Benutzerkonto`;
CREATE TABLE Benutzerkonto (
   benutzerkontenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Zentrum_zentrumsID INT UNSIGNED NOT NULL,
  Person_personenID INT UNSIGNED NOT NULL,
  loginname VARCHAR(50) NOT NULL UNIQUE,
  passwort CHAR(64) NOT NULL,
  rolle VARCHAR(25) NOT NULL,
  erster_login DATETIME NULL,
  letzter_login DATETIME NULL,
  gesperrt BOOL NOT NULL,
  PRIMARY KEY(benutzerkontenID),
  INDEX Benutzerkonto_FKIndex1(Person_personenID),
  INDEX Benutzerkonto_FKIndex2(Zentrum_zentrumsID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(Zentrum_zentrumsID)
    REFERENCES Zentrum(zentrumsID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Studie`;
CREATE TABLE Studie (
   studienID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  statistikerID INT UNSIGNED NULL,
  name VARCHAR(50) NOT NULL,
  beschreibung TEXT NULL,
  randomisationsalgorithmus VARCHAR(50) NULL,
  startdatum DATE NOT NULL,
  enddatum DATE NOT NULL,
  studienprotokoll VARCHAR(255) NOT NULL,
  status_Studie VARCHAR(25) NOT NULL,
  blockgroesse INT NULL,
  PRIMARY KEY(studienID),
  INDEX Studie_FKIndex1(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE RESTRICT
      ON UPDATE CASCADE,
  FOREIGN KEY(statistikerID)
      REFERENCES Benutzerkonto(benutzerkontenID)
          ON DELETE SET NULL
          ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Studienarm`;
CREATE TABLE Studienarm (
  studienarmID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  status_aktivitaet VARCHAR(25) NOT NULL,
  bezeichnung VARCHAR(50) NOT NULL,
  beschreibung TEXT NULL,
  PRIMARY KEY(studienarmID),
  INDEX Studienarm_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Block`;
CREATE TABLE Block (
  blockId INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  blockwert INTEGER UNSIGNED NOT NULL,
  strataKombination TEXT NULL,
  PRIMARY KEY(blockId),
  INDEX Blockeintraege_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Aktivierung`;
CREATE TABLE Aktivierung (
  aktivierungsID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  aktivierungslink CHAR(20) NOT NULL,
  versanddatum DATE NOT NULL,
  PRIMARY KEY(aktivierungsID),
  INDEX Aktivierung_FKIndex1(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Studie_has_Zentrum`;
CREATE TABLE Studie_has_Zentrum (
  Studie_studienID INT UNSIGNED NOT NULL,
  Zentrum_zentrumsID INT UNSIGNED NOT NULL,
  PRIMARY KEY(Studie_studienID, Zentrum_zentrumsID),
  INDEX Studie_has_Zentrum_FKIndex1(Studie_studienID),
  INDEX Studie_has_Zentrum_FKIndex2(Zentrum_zentrumsID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(Zentrum_zentrumsID)
    REFERENCES Zentrum(zentrumsID)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Patient`;
CREATE TABLE Patient (
  patientenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  Studienarm_studienarmID INT UNSIGNED NOT NULL,
  initialen VARCHAR(4) NOT NULL,
  geburtsdatum DATE NOT NULL,
  geschlecht VARCHAR(20) NOT NULL,
  aufklaerungsdatum DATE NOT NULL,
  koerperoberflaeche FLOAT NOT NULL,
  performancestatus VARCHAR(10) NOT NULL,
  strata_gruppe TEXT NULL,
  PRIMARY KEY(patientenID),
  INDEX Patient_FKIndex1(Studienarm_studienarmID),
  INDEX Patient_FKIndex2(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Studienarm_studienarmID)
    REFERENCES Studienarm(studienarmID)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Strata_Typen`;
CREATE TABLE Strata_Typen (
  strata_TypenID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  name VARCHAR(40) NOT NULL,
  beschreibung TEXT NULL,
  PRIMARY KEY(strata_TypenID),
  INDEX Strata_Typen_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Strata_Auspraegung`;
CREATE TABLE Strata_Auspraegung (
  strata_WerteID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Strata_Typen_strata_TypenID INTEGER UNSIGNED NOT NULL,
  wert VARCHAR(100) NOT NULL,
  PRIMARY KEY(strata_WerteID),
  INDEX Strata_Werte_FKIndex1(Strata_Typen_strata_TypenID),
  FOREIGN KEY(Strata_Typen_strata_TypenID)
    REFERENCES Strata_Typen(strata_TypenID)
      ON DELETE CASCADE
      ON UPDATE CASCADE
)
TYPE=InnoDB;

# View ermittelt die Verteilung von Patienten pro Studienarm, sowie den maennlichen und weiblichen Anteil
CREATE VIEW verteilungPatMW AS
select sa.Studie_studienID AS studienID, sa.studienarmID AS studienarmID, count(*) AS anzG,
sum(case when geschlecht='m' then 1 else 0 end) AS anzM,
sum(case when geschlecht='w' then 1 else 0 end) AS anzW
from Patient p, Studienarm sa
where sa.studienarmID=p.Studienarm_studienarmID GROUP BY sa.studienarmID;

SET FOREIGN_KEY_CHECKS=1;

COMMIT;
