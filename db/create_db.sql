DROP TABLE IF EXISTS `Person`;
CREATE TABLE Person (
  personenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Person_personenID INT UNSIGNED,
  nachname VARCHAR(50) NOT NULL,
  vorname VARCHAR(50) NOT NULL,
  titel VARCHAR(20) NULL,
  geschlecht VARCHAR(20) NOT NULL,
  telefonnummer VARCHAR(26) NOT NULL,
  handynummer VARCHAR(26) NULL,
  fax VARCHAR(26) NULL,
  email VARCHAR(26) NOT NULL,
  PRIMARY KEY(personenID),
  INDEX Person_FKIndex1(Person_personenID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
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
  hausnummer VARCHAR(6) NOT NULL,
  passwort CHAR(64) NOT NULL,
  aktiviert BOOL NOT NULL,
  PRIMARY KEY(zentrumsID),
  INDEX Zentrum_FKIndex1(Person_personenID),
  FOREIGN KEY(Person_personenID)
    REFERENCES Person(personenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Benutzerkonto`;
CREATE TABLE Benutzerkonto (
  benutzerkontenID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Zentrum_zentrumsID INT UNSIGNED NOT NULL,
  Person_personenID INT UNSIGNED NOT NULL,
  loginname VARCHAR(14) NOT NULL,
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
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Zentrum_zentrumsID)
    REFERENCES Zentrum(zentrumsID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Studie`;
CREATE TABLE Studie (
  studienID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  Benutzerkonto_benutzerkontenID INT UNSIGNED NOT NULL,
  name VARCHAR(50) NOT NULL,
  beschreibung TEXT NULL,
  startdatum DATE NOT NULL,
  enddatum DATE NOT NULL,
  studienprotokoll VARCHAR(255) NOT NULL,
  randomisationArt VARCHAR(100) NOT NULL,
  status_Studie VARCHAR(25) NOT NULL,
  PRIMARY KEY(studienID),
  INDEX Studie_FKIndex1(Benutzerkonto_benutzerkontenID),
  FOREIGN KEY(Benutzerkonto_benutzerkontenID)
    REFERENCES Benutzerkonto(benutzerkontenID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Studienarm`;
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

DROP TABLE IF EXISTS `Block`;
CREATE TABLE Block (
  blockId INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Studie_studienID INT UNSIGNED NOT NULL,
  blockwert INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY(blockId),
  INDEX Blockeintraege_FKIndex1(Studie_studienID),
  FOREIGN KEY(Studie_studienID)
    REFERENCES Studie(studienID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
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
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
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
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(Zentrum_zentrumsID)
    REFERENCES Zentrum(zentrumsID)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
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
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
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
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

DROP TABLE IF EXISTS `Strata_Werte_has_Patient`;
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
