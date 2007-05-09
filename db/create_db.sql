-- phpMyAdmin SQL Dump
-- version 2.9.1.1-Debian-2ubuntu1
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Erstellungszeit: 07. Mai 2007 um 11:13
-- Server Version: 5.0.38
-- PHP-Version: 5.2.1

SET FOREIGN_KEY_CHECKS=0;

SET AUTOCOMMIT=0;
START TRANSACTION;

-- 
-- Datenbank: `randi2`
-- 

-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Aktivierung`
-- 

DROP TABLE IF EXISTS `Aktivierung`;
CREATE TABLE `Aktivierung` (
  `aktivierungsID` int(10) unsigned NOT NULL auto_increment,
  `Benutzerkonto_benutzerkontenID` int(10) unsigned NOT NULL,
  `aktivierungslink` char(20) NOT NULL,
  `versanddatum` date NOT NULL,
  PRIMARY KEY  (`aktivierungsID`),
  UNIQUE KEY `aktivierungslink` (`aktivierungslink`),
  KEY `Aktivierung_FKIndex1` (`Benutzerkonto_benutzerkontenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Aktivierung`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Benutzerkonto`
-- 

DROP TABLE IF EXISTS `Benutzerkonto`;
CREATE TABLE `Benutzerkonto` (
  `benutzerkontenID` int(10) unsigned NOT NULL auto_increment,
  `Person_personenID` int(10) unsigned NOT NULL,
  `loginname` varchar(14) NOT NULL,
  `passwort` char(64) NOT NULL,
  `rolle` enum('Studienarzt','Studienleiter','System Operator','Administrator') NOT NULL,
  `erster_login` datetime default NULL,
  `letzter_login` datetime default NULL,
  `gesperrt` tinyint(1) NOT NULL,
  PRIMARY KEY  (`benutzerkontenID`),
  KEY `Benutzerkonto_FKIndex1` (`Person_personenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Benutzerkonto`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Block`
-- 

DROP TABLE IF EXISTS `Block`;
CREATE TABLE `Block` (
  `blockId` int(10) unsigned NOT NULL auto_increment,
  `Studie_studienId` int(10) unsigned default NULL,
  `block_wert` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`blockId`),
  KEY `Studie_studienId` (`Studie_studienId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Block`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Patient`
-- 

DROP TABLE IF EXISTS `Patient`;
CREATE TABLE `Patient` (
  `patientenID` int(10) unsigned NOT NULL auto_increment,
  `Benutzerkonto_benutzerkontenID` int(10) unsigned NOT NULL,
  `Studienarm_studienarmID` int(10) unsigned NOT NULL,
  `initialen` varchar(4) NOT NULL,
  `geburtsdatum` date NOT NULL,
  `geschlecht` enum('weiblich','maennlich') NOT NULL,
  `aufklaerungsdatum` date NOT NULL,
  `koerperoberflaeche` float NOT NULL,
  `performancestatus` enum('0','1','2','3','4') NOT NULL,
  PRIMARY KEY  (`patientenID`),
  KEY `Patient_FKIndex1` (`Studienarm_studienarmID`),
  KEY `Patient_FKIndex2` (`Benutzerkonto_benutzerkontenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Patient`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Person`
-- 

DROP TABLE IF EXISTS `Person`;
CREATE TABLE `Person` (
  `personenID` int(10) unsigned NOT NULL auto_increment,
  `stellvertreterID` int(10) unsigned default NULL,
  `nachname` varchar(50) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `titel` enum('Prof.','Dr.','Prof. Dr.') default NULL,
  `geschlecht` enum('w','m') NOT NULL,
  `telefonnummer` varchar(26) NOT NULL,
  `handynummer` varchar(26) default NULL,
  `fax` varchar(26) default NULL,
  `email` varchar(26) NOT NULL,
  PRIMARY KEY  (`personenID`),
  KEY `Person_FKIndex1` (`stellvertreterID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Person`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Strata_Auspraegung`
-- 

DROP TABLE IF EXISTS `Strata_Auspraegung`;
CREATE TABLE `Strata_Auspraegung` (
  `strata_WerteID` int(10) unsigned NOT NULL auto_increment,
  `Strata_Typen_strata_TypenID` int(10) unsigned NOT NULL,
  `wert` varchar(100) NOT NULL,
  PRIMARY KEY  (`strata_WerteID`),
  KEY `Strata_Werte_FKIndex1` (`Strata_Typen_strata_TypenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Strata_Auspraegung`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Strata_Typen`
-- 

DROP TABLE IF EXISTS `Strata_Typen`;
CREATE TABLE `Strata_Typen` (
  `strata_TypenID` int(10) unsigned NOT NULL auto_increment,
  `Studie_studienID` int(10) unsigned NOT NULL,
  `name` varchar(40) NOT NULL,
  `beschreibung` text,
  PRIMARY KEY  (`strata_TypenID`),
  KEY `Strata_Typen_FKIndex1` (`Studie_studienID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Strata_Typen`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Strata_Werte_has_Patient`
-- 

DROP TABLE IF EXISTS `Strata_Werte_has_Patient`;
CREATE TABLE `Strata_Werte_has_Patient` (
  `Strata_Auspraegung_strata_WerteID` int(10) unsigned NOT NULL,
  `Patient_patientenID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`Strata_Auspraegung_strata_WerteID`,`Patient_patientenID`),
  KEY `Strata_Werte_has_Patient_FKIndex1` (`Strata_Auspraegung_strata_WerteID`),
  KEY `Strata_Werte_has_Patient_FKIndex2` (`Patient_patientenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Daten für Tabelle `Strata_Werte_has_Patient`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Studie`
-- 

DROP TABLE IF EXISTS `Studie`;
CREATE TABLE `Studie` (
  `studienID` int(10) unsigned NOT NULL auto_increment,
  `Benutzerkonto_benutzerkontenID` int(10) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `beschreibung` text,
  `startdatum` date NOT NULL,
  `enddatum` date NOT NULL,
  `studienprotokoll` longblob NOT NULL,
  `randomisationArt` enum('Vollstaendige','Block','Block mit Strata','Minimisation') NOT NULL,
  `status_Studie` enum('fortsetzen','pausieren','stoppen') NOT NULL,
  PRIMARY KEY  (`studienID`),
  KEY `Studie_FKIndex1` (`Benutzerkonto_benutzerkontenID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Studie`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Studie_has_Zentrum`
-- 

DROP TABLE IF EXISTS `Studie_has_Zentrum`;
CREATE TABLE `Studie_has_Zentrum` (
  `Studie_studienID` int(10) unsigned NOT NULL,
  `Zentrum_zentrumsID` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`Studie_studienID`,`Zentrum_zentrumsID`),
  KEY `Studie_has_Zentrum_FKIndex1` (`Studie_studienID`),
  KEY `Studie_has_Zentrum_FKIndex2` (`Zentrum_zentrumsID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Daten für Tabelle `Studie_has_Zentrum`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Studienarm`
-- 

DROP TABLE IF EXISTS `Studienarm`;
CREATE TABLE `Studienarm` (
  `studienarmID` int(10) unsigned NOT NULL auto_increment,
  `Studie_studienID` int(10) unsigned NOT NULL,
  `status_aktivitaet` tinyint(3) unsigned NOT NULL,
  `bezeichnung` varchar(50) NOT NULL,
  `beschreibung` text,
  PRIMARY KEY  (`studienarmID`),
  KEY `Studienarm_FKIndex1` (`Studie_studienID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Studienarm`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Zentrum`
-- 

DROP TABLE IF EXISTS `Zentrum`;
CREATE TABLE `Zentrum` (
  `zentrumsID` int(10) unsigned NOT NULL auto_increment,
  `ansprechpartnerID` int(10) unsigned NOT NULL,
  `institution` varchar(70) NOT NULL,
  `abteilungsname` varchar(70) NOT NULL,
  `ort` varchar(50) NOT NULL,
  `plz` char(5) NOT NULL,
  `strasse` varchar(50) NOT NULL,
  `hausnummer` varchar(6) NOT NULL,
  `passwort` char(64) NOT NULL,
  `aktiviert` tinyint(1) NOT NULL,
  PRIMARY KEY  (`zentrumsID`),
  KEY `ansprechpartnerID` (`ansprechpartnerID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- Daten für Tabelle `Zentrum`
-- 


-- --------------------------------------------------------

-- 
-- Tabellenstruktur für Tabelle `Strata_von_Studienarm`
-- 

DROP VIEW IF EXISTS `Strata_von_Studienarm`;
CREATE ALGORITHM=UNDEFINED DEFINER=`randi2`@`localhost` SQL SECURITY DEFINER VIEW `randi2`.`Strata_von_Studienarm` AS select `s`.`studienarmID` AS `studienarmId`,`sa`.`Strata_Typen_strata_TypenID` AS `Strata_Typen_strata_TypenID`,`sa`.`strata_WerteID` AS `strata_WerteID`,count(`p`.`patientenID`) AS `anzahl` from (((`randi2`.`Studienarm` `s` join `randi2`.`Patient` `p` on((`s`.`studienarmID` = `p`.`Studienarm_studienarmID`))) join `randi2`.`Strata_Werte_has_Patient` `swp` on((`p`.`patientenID` = `swp`.`Patient_patientenID`))) join `randi2`.`Strata_Auspraegung` `sa` on((`swp`.`Strata_Auspraegung_strata_WerteID` = `sa`.`strata_WerteID`))) group by `s`.`studienarmID`,`sa`.`Strata_Typen_strata_TypenID`,`sa`.`strata_WerteID`;

-- 
-- Constraints der exportierten Tabellen
-- 

-- 
-- Constraints der Tabelle `Aktivierung`
-- 
ALTER TABLE `Aktivierung`
  ADD CONSTRAINT `Aktivierung_ibfk_1` FOREIGN KEY (`Benutzerkonto_benutzerkontenID`) REFERENCES `Benutzerkonto` (`benutzerkontenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Benutzerkonto`
-- 
ALTER TABLE `Benutzerkonto`
  ADD CONSTRAINT `Benutzerkonto_ibfk_1` FOREIGN KEY (`Person_personenID`) REFERENCES `Person` (`personenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Block`
-- 
ALTER TABLE `Block`
  ADD CONSTRAINT `Block_ibfk_1` FOREIGN KEY (`Studie_studienId`) REFERENCES `Studie` (`studienID`) ON DELETE CASCADE ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Patient`
-- 
ALTER TABLE `Patient`
  ADD CONSTRAINT `Patient_ibfk_1` FOREIGN KEY (`Studienarm_studienarmID`) REFERENCES `Studienarm` (`studienarmID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Patient_ibfk_2` FOREIGN KEY (`Benutzerkonto_benutzerkontenID`) REFERENCES `Benutzerkonto` (`benutzerkontenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Person`
-- 
ALTER TABLE `Person`
  ADD CONSTRAINT `Person_ibfk_1` FOREIGN KEY (`stellvertreterID`) REFERENCES `Person` (`personenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Strata_Auspraegung`
-- 
ALTER TABLE `Strata_Auspraegung`
  ADD CONSTRAINT `Strata_Auspraegung_ibfk_1` FOREIGN KEY (`Strata_Typen_strata_TypenID`) REFERENCES `Strata_Typen` (`strata_TypenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Strata_Typen`
-- 
ALTER TABLE `Strata_Typen`
  ADD CONSTRAINT `Strata_Typen_ibfk_1` FOREIGN KEY (`Studie_studienID`) REFERENCES `Studie` (`studienID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Strata_Werte_has_Patient`
-- 
ALTER TABLE `Strata_Werte_has_Patient`
  ADD CONSTRAINT `Strata_Werte_has_Patient_ibfk_1` FOREIGN KEY (`Strata_Auspraegung_strata_WerteID`) REFERENCES `Strata_Auspraegung` (`strata_WerteID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Strata_Werte_has_Patient_ibfk_2` FOREIGN KEY (`Patient_patientenID`) REFERENCES `Patient` (`patientenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Studie`
-- 
ALTER TABLE `Studie`
  ADD CONSTRAINT `Studie_ibfk_1` FOREIGN KEY (`Benutzerkonto_benutzerkontenID`) REFERENCES `Benutzerkonto` (`benutzerkontenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Studie_has_Zentrum`
-- 
ALTER TABLE `Studie_has_Zentrum`
  ADD CONSTRAINT `Studie_has_Zentrum_ibfk_1` FOREIGN KEY (`Studie_studienID`) REFERENCES `Studie` (`studienID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `Studie_has_Zentrum_ibfk_2` FOREIGN KEY (`Zentrum_zentrumsID`) REFERENCES `Zentrum` (`zentrumsID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Studienarm`
-- 
ALTER TABLE `Studienarm`
  ADD CONSTRAINT `Studienarm_ibfk_1` FOREIGN KEY (`Studie_studienID`) REFERENCES `Studie` (`studienID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 
-- Constraints der Tabelle `Zentrum`
-- 
ALTER TABLE `Zentrum`
  ADD CONSTRAINT `Zentrum_ibfk_1` FOREIGN KEY (`ansprechpartnerID`) REFERENCES `Person` (`personenID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `Strata_von_Studienarm` AS select `s`.`studienarmID` AS `studienarmID`,`st`.`strata_TypenID` AS `strata_TypenID`,`sa`.`strata_WerteID` AS `strata_WerteID`,(select count(`p`.`patientenID`) AS `COUNT(p.patientenID)` from (`Patient` `p` join `Strata_Werte_has_Patient` `swp` on((`p`.`patientenID` = `swp`.`Patient_patientenID`))) where (`p`.`Studienarm_studienarmID` = `s`.`studienarmID`)) AS `anzahl` from ((`Studienarm` `s` join `Strata_Typen` `st` on((`s`.`Studie_studienID` = `st`.`Studie_studienID`))) join `Strata_Auspraegung` `sa` on((`st`.`strata_TypenID` = `sa`.`Strata_Typen_strata_TypenID`)));

SET FOREIGN_KEY_CHECKS=1;

COMMIT;
