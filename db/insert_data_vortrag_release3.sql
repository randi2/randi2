-- phpMyAdmin SQL Dump
-- version 2.9.1.1-Debian-2ubuntu1
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Jul 05, 2007 at 12:36 AM
-- Server version: 5.0.38
-- PHP Version: 5.2.1
-- 
-- Database: `randi2`
-- 

-- 
-- Dumping data for table `Aktivierung`
-- 



-- 
-- Dumping data for table `Person`
-- 

INSERT INTO `Person` (`personenID`, `Person_personenID`, `nachname`, `vorname`, `titel`, `geschlecht`, `telefonnummer`, `handynummer`, `fax`, `email`) VALUES 
(1, NULL, 'Randi2', 'Sysop', 'kein Titel', 'm', '07131-2790980', NULL, NULL, 'randi2@dev.randi2.org'),
(2, NULL, 'Hess', 'Frank', 'kein Titel', 'm', '07131123456', NULL, NULL, 'frank@dev.randi2.org'),
(3, 2, 'Plotnicki', 'Lukasz', 'kein Titel', 'm', '017626157884', NULL, NULL, 'lukasz@dev.randi2.org'),
(4, NULL, 'Hess', 'Frank', 'kein Titel', 'm', '07131123456', NULL, NULL, 'frank@dev.randi2.org'),
(5, 4, 'Haehn', 'Daniel', 'kein Titel', 'm', '016097753596', NULL, NULL, 'daniel@dev.randi2.org'),
(6, NULL, 'Reifschneider', 'Fred', 'kein Titel', 'm', '07131654321', NULL, NULL, 'fred@dev.randi2.org');

-- 


INSERT INTO `Zentrum` (`zentrumsID`, `Person_personenID`, `institution`, `abteilungsname`, `ort`, `plz`, `strasse`, `hausnummer`, `passwort`, `aktiviert`) VALUES 
(1, 1, 'Randi2', 'Testcenter', 'Heilbronn', '74081', 'Max-Planck-Str.', '31', '3edff78bcf959ce04c3663e12dfde2f6e6319a9b493a70d8e348269b82b0a3d3', 1);

-- 
-- Dumping data for table `Benutzerkonto`
-- 

INSERT INTO `Benutzerkonto` (`benutzerkontenID`, `Zentrum_zentrumsID`, `Person_personenID`, `loginname`, `passwort`, `rolle`, `erster_login`, `letzter_login`, `gesperrt`) VALUES 
(1, 1, 1, 'operator', '999b4c009f6ba111e232e16b4e15cd15828d342c3b5769faf3e472da1167dd63', 'SYSOP', '2007-07-04 00:00:00', '2007-07-04 00:00:00', 0),
(2, 1, 3, 'lplotni', '292ce20490a8a8deb95d128eeae2c60fc3437eee4a329fc5dcec11918262251d', 'ADMIN', '2007-07-04 00:00:00', '2007-07-04 00:00:00', 0),
(3, 1, 5, 'dhaehn', '2e1268da89bdcd30d5409c0ae3fa47eca6d9eccd607c7fb86c5eb40ed0d0f920', 'STUDIENLEITER', '2007-07-04 00:00:00', '2007-07-04 00:00:00', 0),
(4, 1, 5, 'statistiker1F', '2428d66d6c312338a3a84c8e65f7d225f076a0bcf0fe3f1ef7fe7102f79d587d', 'STATISTIKER', NULL, NULL, 0),
(5, 1, 6, 'fred@dev.randi2.org', '5ee28fdaf908f261e0b9b611509cc6233ddeb6984852adc390a0c89baec2cc70', 'STUDIENARZT', '2007-07-04 00:00:00', '2007-07-04 00:00:00', 0);

-- 
-- Dumping data for table `Block`
-- 

INSERT INTO `Studie` (`studienID`, `Benutzerkonto_benutzerkontenID`, `statistikerID`, `name`, `beschreibung`, `randomisationsalgorithmus`, `startdatum`, `enddatum`, `studienprotokoll`, `status_Studie`, `blockgroesse`) VALUES 
(1, 3, 4, 'Randi2 Offizielle Feldstudie', 'Diese Studie untersucht das Verhalten eines typischen Randi-Entwicklers', 'StrataBlockRandomisation', '2007-07-10', '2007-07-24', 'iGvqjqgrandi2_feldstudie_protokoll_version_1.pdf', 'in Vorbereitung', 2);


INSERT INTO `Strata_Typen` (`strata_TypenID`, `Studie_studienID`, `name`, `beschreibung`) VALUES 
(1, 1, 'Getraenk', 'Das Lieblingsgetraenk'),
(2, 1, 'Essen', 'Das Lieblingsessen');
-- 
-- Dumping data for table `Patient`
-- 
-- Dumping data for table `Strata_Auspraegung`
-- 

INSERT INTO `Strata_Auspraegung` (`strata_WerteID`, `Strata_Typen_strata_TypenID`, `wert`) VALUES 
(1, 1, 'Cola'),
(2, 1, 'Kaffee'),
(3, 1, 'Energy'),
(4, 2, 'Pizza'),
(5, 2, 'Pommes'),
(6, 2, 'Burger'),
(7, 2, 'Haute Cuisine'),
(8, 2, 'Salat');





-- 
-- Dumping data for table `Strata_Typen`
-- 


-- 
-- Dumping data for table `Studie`
-- 
-- 
-- Dumping data for table `Studie_has_Zentrum`
-- 

INSERT INTO `Studie_has_Zentrum` (`Studie_studienID`, `Zentrum_zentrumsID`) VALUES 
(1, 1);

INSERT INTO `Studienarm` (`studienarmID`, `Studie_studienID`, `status_aktivitaet`, `bezeichnung`, `beschreibung`) VALUES 
(1, 1, 'aktiv', 'Arm 1', 'Der 1. Arm'),
(2, 1, 'aktiv', 'Arm 2', 'Der 2. Arm');

-- 
-- Dumping data for table `Studienarm`
-- 

-- 
-- Dumping data for table `Zentrum`
-- 
