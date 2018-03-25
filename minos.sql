-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 25 mars 2018 à 10:16
-- Version du serveur :  5.7.18-log
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `minos`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

DROP TABLE IF EXISTS `adresse`;
CREATE TABLE IF NOT EXISTS `adresse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rue` varchar(500) NOT NULL,
  `numero` varchar(255) NOT NULL,
  `boite` varchar(255) DEFAULT NULL,
  `code_postal` varchar(255) NOT NULL,
  `pays` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `assignation_tribunal`
--

DROP TABLE IF EXISTS `assignation_tribunal`;
CREATE TABLE IF NOT EXISTS `assignation_tribunal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_dossier` int(11) NOT NULL,
  `id_document` int(11) NOT NULL,
  `date` date NOT NULL,
  `id_tribunal` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_assignation_tribunal_dossier` (`id_dossier`),
  KEY `fk_id_assignation_tribunal_document` (`id_document`),
  KEY `fk_id_assignation_tribunal_tribunal` (`id_tribunal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `corr_dossier_document`
--

DROP TABLE IF EXISTS `corr_dossier_document`;
CREATE TABLE IF NOT EXISTS `corr_dossier_document` (
  `id_dossier` int(11) NOT NULL,
  `id_document` int(11) NOT NULL,
  KEY `fk_id_dossier_document_document` (`id_document`),
  KEY `fk_id_dossier_document_dossier` (`id_dossier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE IF NOT EXISTS `document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `contenu` longblob NOT NULL,
  `date_reception` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `dossier`
--

DROP TABLE IF EXISTS `dossier`;
CREATE TABLE IF NOT EXISTS `dossier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `jugement`
--

DROP TABLE IF EXISTS `jugement`;
CREATE TABLE IF NOT EXISTS `jugement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_dossier` int(11) NOT NULL,
  `id_document` int(11) NOT NULL,
  `id_juge` int(11) NOT NULL,
  `date_effet` date NOT NULL,
  `recevable` varchar(255) NOT NULL,
  `fonde` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_jugement_document` (`id_document`),
  KEY `fk_id_jugement_dossier` (`id_dossier`),
  KEY `fk_id_juge` (`id_juge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

DROP TABLE IF EXISTS `personne`;
CREATE TABLE IF NOT EXISTS `personne` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(1) NOT NULL,
  `nom` varchar(500) NOT NULL,
  `prenom` varchar(500) DEFAULT NULL,
  `niss` varchar(255) DEFAULT NULL,
  `id_adresse` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_personne_adresse` (`id_adresse`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `rendez_vous`
--

DROP TABLE IF EXISTS `rendez_vous`;
CREATE TABLE IF NOT EXISTS `rendez_vous` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_dossier` int(11) NOT NULL,
  `id_role_adresse` int(11) NOT NULL,
  `date_heure` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_rendez_vous_role_adresse` (`id_role_adresse`),
  KEY `fk_id_rendez_vous_dossier` (`id_dossier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `requete`
--

DROP TABLE IF EXISTS `requete`;
CREATE TABLE IF NOT EXISTS `requete` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_dossier` int(11) NOT NULL,
  `id_requerant` int(11) NOT NULL,
  `id_document` int(11) NOT NULL,
  `date_effet` date NOT NULL,
  `numero_role` varchar(255) NOT NULL,
  `numero_rg` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_requete_dossier` (`id_dossier`),
  KEY `fk_id_requete_document` (`id_document`),
  KEY `fk_id_requete_personne` (`id_requerant`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `role_adresse`
--

DROP TABLE IF EXISTS `role_adresse`;
CREATE TABLE IF NOT EXISTS `role_adresse` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_adresse` int(11) NOT NULL,
  `nom` varchar(500) NOT NULL,
  `niveau_tribunal` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_id_adresse` (`id_adresse`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `assignation_tribunal`
--
ALTER TABLE `assignation_tribunal`
  ADD CONSTRAINT `fk_id_assignation_tribunal_document` FOREIGN KEY (`id_document`) REFERENCES `document` (`id`),
  ADD CONSTRAINT `fk_id_assignation_tribunal_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`),
  ADD CONSTRAINT `fk_id_assignation_tribunal_tribunal` FOREIGN KEY (`id_tribunal`) REFERENCES `role_adresse` (`id`);

--
-- Contraintes pour la table `corr_dossier_document`
--
ALTER TABLE `corr_dossier_document`
  ADD CONSTRAINT `fk_id_dossier_document_document` FOREIGN KEY (`id_document`) REFERENCES `document` (`id`),
  ADD CONSTRAINT `fk_id_dossier_document_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`);

--
-- Contraintes pour la table `jugement`
--
ALTER TABLE `jugement`
  ADD CONSTRAINT `fk_id_juge` FOREIGN KEY (`id_juge`) REFERENCES `personne` (`id`),
  ADD CONSTRAINT `fk_id_jugement_document` FOREIGN KEY (`id_document`) REFERENCES `document` (`id`),
  ADD CONSTRAINT `fk_id_jugement_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`);

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `fk_id_personne_adresse` FOREIGN KEY (`id_adresse`) REFERENCES `adresse` (`id`);

--
-- Contraintes pour la table `rendez_vous`
--
ALTER TABLE `rendez_vous`
  ADD CONSTRAINT `fk_id_rendez_vous_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`),
  ADD CONSTRAINT `fk_id_rendez_vous_role_adresse` FOREIGN KEY (`id_role_adresse`) REFERENCES `role_adresse` (`id`);

--
-- Contraintes pour la table `requete`
--
ALTER TABLE `requete`
  ADD CONSTRAINT `fk_id_requete_document` FOREIGN KEY (`id_document`) REFERENCES `document` (`id`),
  ADD CONSTRAINT `fk_id_requete_dossier` FOREIGN KEY (`id_dossier`) REFERENCES `dossier` (`id`),
  ADD CONSTRAINT `fk_id_requete_personne` FOREIGN KEY (`id_requerant`) REFERENCES `personne` (`id`);

--
-- Contraintes pour la table `role_adresse`
--
ALTER TABLE `role_adresse`
  ADD CONSTRAINT `fk_id_adresse` FOREIGN KEY (`id_adresse`) REFERENCES `adresse` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
