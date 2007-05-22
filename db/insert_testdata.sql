insert into Person (nachname,vorname,geschlecht,telefonnummer,handynummer,fax,email)  
values ('Hess','Frank','m','07131-1231232',null,null,'frank.hess@hs-heilbronn.de'),
		('Graeff','Valentin','m','07131-3456',null,null,'vgraeff@stud.hs-heilbronn.de'),
		('Nadine','Zwink','w','07131-5673456',null,null,'nzwinck@stud.hs-heilbronn.de'),
		('Hans','Dampf','m','07131-123456',null,null,'hans.damp@allenGassen.de');

insert into Zentrum (Person_personenID,institution,abteilungsname,ort,plz,strasse,hausnummer,passwort,aktiviert) 
values('1','nch','knochen','heilbronn','74081','hessstrasse','34','hess',0),
		('2','hno','nase','heilbronn','74081','graeffstrasse','23','graeff',1),
		('3','mzk','zahn','heilbronn','74081','zwinkstrasse','12','zwink',0),
		('4','paed','kind','heilbronn','74081','dampfstrasse','44','dampf',1);

insert into Benutzerkonto (Zentrum_zentrumsID,Person_personenID,loginname,passwort,rolle,gesperrt) 
values ('1','1','frank','hess','Studienarzt',0),
		('2','2','valentin','graeff','Studienleiter',0),
		('3','3','nadine','zwink','System Operator',1),
		('4','4','hans','dampf','Administrator',1);

insert into Studie (Benutzerkonto_benutzerkontenID,name,beschreibung,startdatum,enddatum,studienprotokoll,randomisationArt,status_Studie) 
values('1','hess','','2001-01-01','2005-05-05','c:na','Vollstaendige','fortsetzen'),
		('2','graeff','','2002-02-02','2006-06-06','d:na','Block','pausieren'),
		('3','zwink','','2003-03-03','2007-07-07','e:na','Block mit Strata','stoppen'),
		('4','dampf','','2004-04-04','2008-08-08','f:na','Minimisation','fortsetzen');

insert into Studienarm (Studie_studienID,status_aktivitaet,bezeichnung,beschreibung) 
values('1','0','0',null),
		('2','2','1',null),
		('3','3','1',null),
		('4','1','0',null);

insert into Block (Studie_studienID, blockwert)
values ('1','12'),
		('2','14'),
		('3','16'),
		('4','18');

insert into Aktivierung (Benutzerkonto_benutzerkontenID,aktivierungslink,versanddatum) 
values('1','http://www.eins.de','2005-12-05'),
		('2','http://www.test.de','2003-01-01'),
		('3','http://www.zwei.de','2003-03-03'),
		('4','http://www.drei.de','2004-04-04');



insert into Patient(Benutzerkonto_benutzerkontenID,studienarm_studienarmID,initialen,geburtsdatum,geschlecht,aufklaerungsdatum,koerperoberflaeche,performancestatus) 
values('1','1','fh','2003-12-03','maennlich','2001-01-01','23','1'),
		('2','2','vg','1984-01-01','maennlich','2002-02-02','32','2'),
		('3','3','nz','1875-03-03','weiblich','2003-03-03','45','3'),
		('4','4','hd','1973-05-05','maennlich','2004-04-04','56','0');

insert into Strata_Typen(Studie_studienID,name,beschreibung) 
values('1','eins','woass koane'),
		('2','zwei','immer noch ned'),
		('3','drei','auch jetzt noch nicht'),
		('4','vier','nie mehr');

insert into Strata_Auspraegung(strata_Typen_strata_TypenID,wert) 
values('1','10'),
		('2','20'),
		('3','30'),
		('4','40');

insert into Strata_Werte_has_Patient(Strata_Auspraegung_strata_WerteID,Patient_patientenID) 
values('1','1'),
		('2','2'),
		('3','3'),
		('4','4');

insert into Studie_has_Zentrum(Studie_studienID, Zentrum_zentrumsID)
values('1','1'),
		('2','2'),
		('3','3'),
		('4','4');
