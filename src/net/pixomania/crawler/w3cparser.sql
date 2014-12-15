-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 15, 2014 at 02:37 PM
-- Server version: 5.5.40-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.5

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `w3cparser`
--

-- --------------------------------------------------------

--
-- Table structure for table `persons`
--

CREATE TABLE IF NOT EXISTS `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `currentAffiliation` varchar(128) DEFAULT NULL,
  `currentAffiliationUntil` varchar(32) DEFAULT NULL,
  `standardAffiliation` varchar(128) DEFAULT NULL,
  `standardAffiliationUntil` varchar(32) DEFAULT NULL,
  `viaAffiliation` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `workgroup` varchar(64) DEFAULT NULL,
  `formerAffiliation` varchar(128) DEFAULT NULL,
  `full` varchar(1024) NOT NULL,
  `sv_editor` int(11) DEFAULT NULL,
  `sv_author` int(11) DEFAULT NULL,
  `sv_contributor` int(11) DEFAULT NULL,
  `sv_previousEditors` int(11) DEFAULT NULL,
  `sv_seriesEditors` int(11) DEFAULT NULL,
  `sv_contributingAuthors` int(11) DEFAULT NULL,
  `sv_editorInChief` int(11) DEFAULT NULL,
  `sv_principalAuthors` int(11) DEFAULT NULL,
  `sv_principalContributors` int(11) DEFAULT NULL,
  `sv_wgchair` int(11) DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sv_editor` (`sv_editor`),
  KEY `sv_author` (`sv_author`),
  KEY `sv_contributor` (`sv_contributor`),
  KEY `sv_previousEditors` (`sv_previousEditors`),
  KEY `sv_seriesEditors` (`sv_seriesEditors`),
  KEY `sv_contributingAuthors` (`sv_contributingAuthors`),
  KEY `sv_editorInChief` (`sv_editorInChief`),
  KEY `sv_principalAuthors` (`sv_principalAuthors`),
  KEY `sv_principalContributors` (`sv_principalContributors`),
  KEY `sv_wgchair` (`sv_wgchair`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8460 ;

--
-- RELATIONS FOR TABLE `persons`:
--   `sv_editor`
--       `standardversions` -> `id`
--   `sv_wgchair`
--       `standardversions` -> `id`
--   `sv_author`
--       `standardversions` -> `id`
--   `sv_contributor`
--       `standardversions` -> `id`
--   `sv_previousEditors`
--       `standardversions` -> `id`
--   `sv_seriesEditors`
--       `standardversions` -> `id`
--   `sv_contributingAuthors`
--       `standardversions` -> `id`
--   `sv_editorInChief`
--       `standardversions` -> `id`
--   `sv_principalAuthors`
--       `standardversions` -> `id`
--   `sv_principalContributors`
--       `standardversions` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `persons_websites`
--

CREATE TABLE IF NOT EXISTS `persons_websites` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `website` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `person_id` (`person_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2365 ;

--
-- RELATIONS FOR TABLE `persons_websites`:
--   `person_id`
--       `persons` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `standards`
--

CREATE TABLE IF NOT EXISTS `standards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=244 ;

-- --------------------------------------------------------

--
-- Table structure for table `standards_names`
--

CREATE TABLE IF NOT EXISTS `standards_names` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `standard_id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `standard_id_idx` (`standard_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1102 ;

--
-- RELATIONS FOR TABLE `standards_names`:
--   `standard_id`
--       `standards` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `standardversions`
--

CREATE TABLE IF NOT EXISTS `standardversions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `standard_id` int(11) DEFAULT NULL,
  `title` varchar(256) NOT NULL,
  `date` varchar(32) NOT NULL,
  `status` varchar(64) NOT NULL,
  `link` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `link` (`link`),
  KEY `standard_id` (`standard_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1973 ;

--
-- RELATIONS FOR TABLE `standardversions`:
--   `standard_id`
--       `standards` -> `id`
--

-- --------------------------------------------------------

--
-- Table structure for table `standardversions_standardversions`
--

CREATE TABLE IF NOT EXISTS `standardversions_standardversions` (
  `sv` int(11) NOT NULL,
  `sv_prev` int(11) NOT NULL,
  KEY `sv` (`sv`),
  KEY `sv_prev` (`sv_prev`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `standardversions_standardversions`:
--   `sv`
--       `standardversions` -> `id`
--   `sv_prev`
--       `standardversions` -> `id`
--

--
-- Constraints for dumped tables
--

--
-- Constraints for table `persons`
--
ALTER TABLE `persons`
  ADD CONSTRAINT `persons_ibfk_1` FOREIGN KEY (`sv_editor`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_10` FOREIGN KEY (`sv_wgchair`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_2` FOREIGN KEY (`sv_author`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_3` FOREIGN KEY (`sv_contributor`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_4` FOREIGN KEY (`sv_previousEditors`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_5` FOREIGN KEY (`sv_seriesEditors`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_6` FOREIGN KEY (`sv_contributingAuthors`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_7` FOREIGN KEY (`sv_editorInChief`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_8` FOREIGN KEY (`sv_principalAuthors`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `persons_ibfk_9` FOREIGN KEY (`sv_principalContributors`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `persons_websites`
--
ALTER TABLE `persons_websites`
  ADD CONSTRAINT `persons_websites_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `standards_names`
--
ALTER TABLE `standards_names`
  ADD CONSTRAINT `standards_names_ibfk_1` FOREIGN KEY (`standard_id`) REFERENCES `standards` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `standardversions`
--
ALTER TABLE `standardversions`
  ADD CONSTRAINT `standardversions_ibfk_1` FOREIGN KEY (`standard_id`) REFERENCES `standards` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `standardversions_standardversions`
--
ALTER TABLE `standardversions_standardversions`
  ADD CONSTRAINT `standardversions_standardversions_ibfk_1` FOREIGN KEY (`sv`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `standardversions_standardversions_ibfk_2` FOREIGN KEY (`sv_prev`) REFERENCES `standardversions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
SET FOREIGN_KEY_CHECKS=1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
