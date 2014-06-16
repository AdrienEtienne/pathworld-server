delete from invitation;
delete from tips;
delete from event;
delete from user;
delete from adress;

insert into adress(idAdress, place, location, latitude, longitude) values(1, 'myPlace', '4 rue de Paris, Paris', 48.2, 38.2);

insert into user(idUser, name, firstName, mail, pwd, phone, idHomeAddress, idCurrentAddress) values(1, 'nom', 'prenom', 'test@gmail.com', 'password', '0125658575', 1, 1);
insert into user(idUser, name, firstName, mail, pwd, phone, idHomeAddress, idCurrentAddress) values(2, 'nom2', 'prenom2', 'test2@gmail.com', 'password', '0125658575', 1, 1);

INSERT INTO  event (idEvent, entitled,date ,eventType, idAdress, idCreator)VALUES (1, 'mon titre',  '2014-05-27',  'perso',  1,  2);
INSERT INTO  event (idEvent, entitled,date ,eventType, idAdress, idCreator)VALUES (2, 'mon 2e titre',  '2014-05-27',  'pro',  1,  1);

INSERT INTO  invitation (idInvitation, idEvent, idUser,response ,role, date, geolocalization)VALUES (1, 1, 2, 1, 'staff',  '2014-05-27',  1);

insert into tips(idTips, idUser, idAdresse, idEvent, titre, details, date) values(1, 2, 1, 1, 'mon cailloux', 'details cailloux', '2014-05-27');
insert into tips(idTips, idUser, idAdresse, idEvent, titre, details, date) values(2, 2, 1, 1, 'mon tips', 'details tips', '2014-05-27');