insert into Person (nachname,vorname,geschlecht,telefonnummer,handynummer,fax,email)  
values ('Hess','Frank','m','07131-1231232',null,null,'frank.hess@hs-heilbronn.de'),
		('Graeff','Valentin','m','07131-3456',null,null,'vgraeff@stud.hs-heilbronn.de'),
		('Nadine','Zwink','w','07131-5673456',null,null,'nzwinck@stud.hs-heilbronn.de'),
		('Hans','Dampf','m','07131-123456',null,null,'hans.damp@allenGassen.de');


#Passwoerter: 
	#Frank Hess: 		1$hess80
	#Valentin Graeff: 	1$graeff83
	#Nadine Zwink: 		1$zwink83
	#Hans Dampf: 		1$dampf75

insert into Zentrum (Person_personenID,institution,abteilungsname,ort,plz,strasse,hausnummer,passwort,aktiviert) 
values('1','nch','knochen','heilbronn','74081','hessstrasse','34','a2dc01d419169a14447c792bfb5ece25a50757c09f28798a2c4f51bea329e35d',0),
		('2','hno','nase','heilbronn','74081','graeffstrasse','23','806092d3f870df2536c6685f6532d0fadead7d460127d34786443419ed364474',1),
		('3','mzk','zahn','heilbronn','74081','zwinkstrasse','12','4bc34ee364f766e4877e3e6bc7fce62402910200967678714db8d7b4e062c1aa',0),
		('4','paed','kind','heilbronn','74081','dampfstrasse','44','7422090dd18d88965d3c2273c6121af4595bbe5c11f0c7a847a158555a4f65be',1);

insert into Benutzerkonto (Zentrum_zentrumsID,Person_personenID,loginname,passwort,rolle,gesperrt) 
values ('1','1','frank80','a2dc01d419169a14447c792bfb5ece25a50757c09f28798a2c4f51bea329e35d','Studienarzt',0),
		('2','2','valentin','806092d3f870df2536c6685f6532d0fadead7d460127d34786443419ed364474','Studienleiter',0),
		('3','3','nadine','4bc34ee364f766e4877e3e6bc7fce62402910200967678714db8d7b4e062c1aa','System Operator',1),
		('4','4','hans75','7422090dd18d88965d3c2273c6121af4595bbe5c11f0c7a847a158555a4f65be','Administrator',1);

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
