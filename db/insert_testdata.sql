use randi2;

insert into Person (nachname,vorname,titel,geschlecht,telefonnummer,handynummer,fax,email)  
values ('Hess','Frank','Prof.','m','07131-1231232',null,null,'frank.hess@hs-heilbronn.de'),
        ('Graeff','Valentin','Dr.','m','07131-3456',null,null,'vgraeff@stud.hs-heilbronn.de'),
        ('Nadine','Zwink','kein Titel','w','07131-5673456',null,null,'nzwinck@stud.hs-heilbronn.de'),
        ('Hans','Dampf','Prof. Dr.','m','07131-123456',null,null,'hans.damp@allenGassen.de');

#Passwoerter Zentren:
		# 1) nch!"§knochen
		# 2) hno!"§nase
		# 3) mzk!"§zahn
		# 4) paed!"§kind


insert into Zentrum (Person_personenID,institution,abteilungsname,ort,plz,strasse,hausnummer,passwort,aktiviert) 
values('1','nch','knochen','heilbronn','74081','hessstrasse','34','e4daf35c3c78e719d7b1ca6c16c8f8522d68b88352cb7068e010f46e3c1948c1',0),
        ('2','hno','nase','heilbronn','74081','graeffstrasse','23','091c32a7a4f07be62d314c43768f4f9551bfab318522c69419064d0b1436c2ba',1),
        ('3','mzk','zahn','heilbronn','74081','zwinkstrasse','12','8cf0a1ed41b621c4d5a1096ba53f1a2814943b54dab6eb39256f9cc7132b1cd2',0),
        ('4','paed','kind','heilbronn','74081','dampfstrasse','44','4623de6469aff3cdb1761fb6219fe0cd180873376cc588287c6b44504100bc6c',1);

#Passwoerter User: 
    #Frank Hess:         1$hess80
    #Valentin Graeff:     1$graeff83
    #Nadine Zwink:         1$zwink83
    #Hans Dampf:         1$dampf75

insert into Benutzerkonto (Zentrum_zentrumsID,Person_personenID,loginname,passwort,rolle,gesperrt) 
values ('1','1','frank80','a2dc01d419169a14447c792bfb5ece25a50757c09f28798a2c4f51bea329e35d','STUDIENARZT',0),
        ('2','2','valentin','806092d3f870df2536c6685f6532d0fadead7d460127d34786443419ed364474','STUDIENLEITER',0),
        ('3','3','nadine','4bc34ee364f766e4877e3e6bc7fce62402910200967678714db8d7b4e062c1aa','SYSOP',1),
        ('4','4','hans75','7422090dd18d88965d3c2273c6121af4595bbe5c11f0c7a847a158555a4f65be','ADMIN',1);

insert into Studie (Benutzerkonto_benutzerkontenID,name,beschreibung,startdatum,enddatum,studienprotokoll,randomisationArt,status_Studie) 
values('1','hess','','2001-01-01','2005-05-05','c:na','VOLLSTAENDIGE','fortsetzen'),
        ('2','graeff','','2002-02-02','2006-06-06','d:na','BLOCK','pausieren'),
        ('3','zwink','','2003-03-03','2007-07-07','e:na','BLOCK MIT STRATA','stoppen'),
        ('4','dampf','','2004-04-04','2008-08-08','f:na','MINIMISATION','fortsetzen');

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
values('1','1','fh','2003-12-03','MAENNLICH','2001-01-01','23','1'),
        ('2','2','vg','1984-01-01','MAENNLICH','2002-02-02','32','2'),
        ('3','3','nz','1875-03-03','WEIBLICH','2003-03-03','45','3'),
        ('4','4','hd','1973-05-05','MAENNLICH','2004-04-04','56','0');

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

