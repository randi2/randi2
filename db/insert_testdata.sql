use randi2;

insert into Person (nachname,vorname,titel,geschlecht,telefonnummer,handynummer,fax,email)  
values ('Hess','Frank','Prof.','m','07131-1231232',null,null,'frank.hess@hs-heilbronn.de'),
        ('Graeff','Valentin','Dr.','m','07131-3456',null,null,'vgraeff@stud.hs-heilbronn.de'),
        ('Zwink','Nadine','kein Titel','w','07131-5673456',null,null,'nzwinck@stud.hs-heilbronn.de'),
        ('Dampf','Hans','Prof. Dr.','m','07131-123456',null,null,'hans.damp@allenGassen.de');

#Passwoerter Zentren:
        # Nch Knochen: nch1!$knochen80
        # Hno Nase: hno1!$nase80
        # Mzk Zahn: mzk1!$zahn80
        # Paed Kind: paed1!$kind80


insert into Zentrum (Person_personenID,institution,abteilungsname,ort,plz,strasse,hausnummer,passwort,aktiviert) 
values('1','nch','knochen','heilbronn','74081','hessstrasse','34','d4f6e47af8fd9d5623e75a34cc9b846f0b05e85d2eda3cd03ec043852cb49f73',1),
        ('2','hno','nase','heilbronn','74081','graeffstrasse','23','e67d754bb223a82c27d7d5b6c065ccb75dc0081e255729989b8e3ff640f2a4bb',1),
        ('3','mzk','zahn','heilbronn','74081','zwinkstrasse','12','2bc665df3b6ea2ab4683f2e6dca239446b24128c70e127b46a461182bf94f12b',1),
        ('4','paed','kind','heilbronn','74081','dampfstrasse','44','7e629eb01372c76aa67ab6019ca780f3f7855fa0c7b0efbff06516287ae4d2db',1);

#Passwoerter User: 
    #Frank Hess:         1$hess80
    #Valentin Graeff:     1$graeff83
    #Nadine Zwink:         1$zwink83
    #Hans Dampf:         1$dampf75

insert into Benutzerkonto (Zentrum_zentrumsID,Person_personenID,loginname,passwort,rolle,gesperrt) 
values ('1','1','frank80','a2dc01d419169a14447c792bfb5ece25a50757c09f28798a2c4f51bea329e35d','STUDIENARZT',0),
        ('2','2','valentin','806092d3f870df2536c6685f6532d0fadead7d460127d34786443419ed364474','STUDIENLEITER',0),
        ('3','3','nadine','4bc34ee364f766e4877e3e6bc7fce62402910200967678714db8d7b4e062c1aa','SYSOP',0),
        ('4','4','hans75','7422090dd18d88965d3c2273c6121af4595bbe5c11f0c7a847a158555a4f65be','ADMIN',0);

insert into Studie (Benutzerkonto_benutzerkontenID,name,beschreibung,randomisationsalgorithmus,startdatum,enddatum,studienprotokoll,status_Studie) 
values('1','hess','irgendwelche Beschreibung der Studie','de.randi2.randomisation.VollstaendigeRandomisation','2001-01-01','2009-05-05','c:na','aktiv'),
        ('2','graeff','irgendwelche Beschreibung der Studie','de.randi2.randomisation.VollstaendigeRandomisation','2002-02-02','2010-06-06','d:na','aktiv'),
        ('3','zwink','irgendwelche Beschreibung der Studie','de.randi2.randomisation.BlockRandomisation','2003-03-03','2017-07-07','e:na','aktiv'),
        ('4','dampf','irgendwelche Beschreibung der Studie','de.randi2.randomisation.BlockRandomisation','2004-04-04','2008-08-08','f:na','aktiv');

insert into Studienarm (Studie_studienID,status_aktivitaet,bezeichnung,beschreibung) 
values('1','aktiv','irgendeine Bezeichnung','Beschreibund 1'),
        ('2','aktiv','irgendeine Bezeichnung','Beschreibund 2'),
        ('3','aktiv','irgendeine Bezeichnung','Beschreibund 3'),
        ('4','aktiv','irgendeine Bezeichnung','Beschreibund 4');

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


insert into Studie_has_Zentrum(Studie_studienID, Zentrum_zentrumsID)
values('1','1'),
        ('2','2'),
        ('3','3'),
        ('4','4');

