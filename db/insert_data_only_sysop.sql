# RANDI2 Create Skript
# legt nur den SysOp an, mit welchem alle anderen Sachen in RANDI2 konfiguriert werden koennen
#

SET FOREIGN_KEY_CHECKS=0;

SET AUTOCOMMIT=0;
START TRANSACTION;
use randi2;

# SYSOP Account (Default)
# login: operator
# passwort: randi2$2007
insert into Person (nachname,vorname,titel,geschlecht,telefonnummer,handynummer,fax,email)  
values ('Randi2','Sysop','kein Titel','m','07131-2790980',null,null,'randi2@dev.randi2.org');

# Zentrum
# Randi2 Testcenter
# passwort: Randi2Testcenter!
insert into Zentrum (Person_personenID,institution,abteilungsname,ort,plz,strasse,hausnummer,passwort,aktiviert) 
values('1','Randi2','Testcenter','Heilbronn','74081','Max-Planck-Str.','31','3edff78bcf959ce04c3663e12dfde2f6e6319a9b493a70d8e348269b82b0a3d3',1);

insert into Benutzerkonto (Zentrum_zentrumsID,Person_personenID,loginname,passwort,rolle,gesperrt) 
values ('1','1','operator','999b4c009f6ba111e232e16b4e15cd15828d342c3b5769faf3e472da1167dd63','SYSOP',0);

SET FOREIGN_KEY_CHECKS=1;

COMMIT;

