-- phpMyAdmin SQL Dump
-- version 4.0.2
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 24 Mars 2014 à 11:25
-- Version du serveur: 5.6.11-log
-- Version de PHP: 5.3.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `pathworld`
--
CREATE DATABASE IF NOT EXISTS `pathworld` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `pathworld`;

DROP TABLE IF EXISTS tips cascade;
DROP TABLE IF EXISTS invitation cascade;
DROP TABLE IF EXISTS event cascade;
DROP TABLE IF EXISTS user cascade;
DROP TABLE IF EXISTS adress cascade;


-- --------------------------------------------------------

--
-- Structure de la table `adress`
--

CREATE TABLE IF NOT EXISTS `adress` (
  `idAdress` int(11) NOT NULL AUTO_INCREMENT,
  `place` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `latitude` decimal(15,8) DEFAULT NULL,
  `longitude` decimal(15,8) DEFAULT NULL,
  PRIMARY KEY (`idAdress`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='addresses storage' AUTO_INCREMENT=1 ;

insert into adress(place, location, latitude, longitude) values('myPlace', '4 rue de Paris, Paris', 48.2, 38.2);
-- --------------------------------------------------------

--
-- Structure de la table `event`
--

CREATE TABLE IF NOT EXISTS `event` (
  `idEvent` int(11) NOT NULL AUTO_INCREMENT,
  `entitled` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `eventType` varchar(255) NOT NULL COMMENT '"pro" ou "perso"',
  `idAdress` int(11) NOT NULL,
  `idCreator` int(11) NOT NULL,
  PRIMARY KEY (`idEvent`),
  KEY `FK_idCreator_Event` (`idCreator`),
  KEY `FK_idAdress_Event` (`idAdress`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='events storage' AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Structure de la table `invitation`
--

CREATE TABLE IF NOT EXISTS `invitation` (
  `idInvitation` int(11) NOT NULL AUTO_INCREMENT,
  `idEvent` int(11) NOT NULL,
  `idUser` int(11) NOT NULL,
  `response` int(1) NOT NULL COMMENT '0 -> no response / 1 -> yes / 2 -> no',
  `role` varchar(255) NOT NULL COMMENT '"guest" ou "staff" ou "protected guest"',
  `date` date NOT NULL,
  `geolocalization` int(1) NOT NULL COMMENT '0 -> no 1 -> yes',
  PRIMARY KEY (`idInvitation`),
  KEY `FK_idUser_invitation` (`idUser`),
  KEY `FK_idEvent_invitation` (`idEvent`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='invitations storage';

--
-- Index pour la table 'invitation'
--
alter table invitation add unique index(idEvent, idUser);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255),
  `firstName` varchar(255),
  `mail` varchar(255) NOT NULL COMMENT 'le mail sert aussi de login',
  `pwd` varchar(50) NOT NULL,
  `phone` varchar(15),
  `idHomeAddress` int(11),
  `idCurrentAddress` int(11),
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `mail` (`mail`),
  KEY `FK_idHomeAddress_User` (`idHomeAddress`),
  KEY `FK_idCurrentAddress_User` (`idCurrentAddress`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Users storage' AUTO_INCREMENT=1 ;

insert into user(name, firstName, mail, pwd, phone, idHomeAddress, idCurrentAddress) values('nom', 'prenom', 'test@gmail.com', 'password', '0125658575', 1, 1);
insert into user(name, firstName, mail, pwd, phone, idHomeAddress, idCurrentAddress) values('nom2', 'prenom2', 'test2@gmail.com', 'password', '0125658575', 1, 1);
-- --------------------------------------------------------

--
-- Structure de la table `tips`
--

CREATE TABLE IF NOT EXISTS `tips` (
  `idTips` int(11) NOT NULL AUTO_INCREMENT,
  `idUser` int(11) NOT NULL,
  `idAdresse` int(11) NOT NULL,
  `idEvent` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `details` varchar(50) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`idTips`),
  KEY `FK_idAddress_Tips` (`idAdresse`),
  KEY `FK_idUser_Tips` (`idUser`),
  KEY `FK_idEvent_Tips` (`idEvent`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='Tips storage' AUTO_INCREMENT=5 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FK_idAdress_Event` FOREIGN KEY (`idAdress`) REFERENCES `adress` (`idAdress`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser_Event` FOREIGN KEY (`idCreator`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `invitation`
--
ALTER TABLE `invitation`
  ADD CONSTRAINT `FK_idEvent_invitation` FOREIGN KEY (`idEvent`) REFERENCES `event` (`idEvent`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser_invitation` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_idHomeAddress_User` FOREIGN KEY (`idHomeAddress`) REFERENCES `adress` (`idAdress`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idCurrentAddress_User` FOREIGN KEY (`idCurrentAddress`) REFERENCES `adress` (`idAdress`) ON DELETE CASCADE ON UPDATE CASCADE;
  
--
-- Contraintes pour la table `tips`
--
ALTER TABLE `tips`
  ADD CONSTRAINT `FK_idAddress_Tips` FOREIGN KEY (`idAdresse`) REFERENCES `adress` (`idAdress`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idUser_Tips` FOREIGN KEY (`idUser`) REFERENCES `user` (`idUser`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_idEvent_Tips` FOREIGN KEY (`idEvent`) REFERENCES `event` (`idEvent`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
