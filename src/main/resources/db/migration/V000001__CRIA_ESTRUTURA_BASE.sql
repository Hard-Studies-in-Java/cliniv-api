-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: ftgestdev
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ANAMNESE`
--

DROP TABLE IF EXISTS `ANAMNESE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAMNESE` (
  `ID` decimal(10,0) NOT NULL,
  `ID_VISITA` decimal(10,0) NOT NULL,
  `ID_PACIENTE` decimal(10,0) NOT NULL,
  `ID_ANAMNESE_ITEM` decimal(10,0) NOT NULL,
  `RESPOSTA` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ANAM_ITEM_idx` (`ID_ANAMNESE_ITEM`),
  KEY `FK_ANAM_PAC_idx` (`ID_PACIENTE`),
  KEY `FK_ANAM_VIS_idx` (`ID_VISITA`),
  CONSTRAINT `FK_ANAM_ITEM` FOREIGN KEY (`ID_ANAMNESE_ITEM`) REFERENCES `ANAMNESE_ITEM` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ANAM_PAC` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `PACIENTE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ANAM_VIS` FOREIGN KEY (`ID_VISITA`) REFERENCES `VISITA` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANAMNESE`
--

LOCK TABLES `ANAMNESE` WRITE;
/*!40000 ALTER TABLE `ANAMNESE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ANAMNESE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ANAMNESE_ITEM`
--

DROP TABLE IF EXISTS `ANAMNESE_ITEM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ANAMNESE_ITEM` (
  `ID` decimal(10,0) NOT NULL,
  `PERGUNTA` varchar(45) NOT NULL,
  `SIM_NAO` varchar(3) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ANAMNESE_ITEM`
--

LOCK TABLES `ANAMNESE_ITEM` WRITE;
/*!40000 ALTER TABLE `ANAMNESE_ITEM` DISABLE KEYS */;
INSERT INTO `ANAMNESE_ITEM` VALUES (1,'Qual o Peso do paciente?','NAO');
/*!40000 ALTER TABLE `ANAMNESE_ITEM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PACIENTE`
--

DROP TABLE IF EXISTS `PACIENTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PACIENTE` (
  `ID` decimal(10,0) NOT NULL,
  `NOME` varchar(50) NOT NULL,
  `SOBRENOME` varchar(200) NOT NULL,
  `RG` varchar(7) DEFAULT NULL,
  `CPF` varchar(11) NOT NULL,
  `DATA_NASCIMENTO` date NOT NULL,
  `SEXO` varchar(1) NOT NULL COMMENT 'TABELA DE CADASTRO DE PACIENTES',
  `NOME_COMP_PAI` varchar(250) DEFAULT NULL,
  `NOME_COMP_MAE` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PACIENTE`
--

LOCK TABLES `PACIENTE` WRITE;
/*!40000 ALTER TABLE `PACIENTE` DISABLE KEYS */;
/*!40000 ALTER TABLE `PACIENTE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESPONSAVEL`
--

DROP TABLE IF EXISTS `RESPONSAVEL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESPONSAVEL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(100) NOT NULL,
  `CPF` varchar(11) NOT NULL,
  `REGISTRO_PROFISSIONAL` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESPONSAVEL`
--

LOCK TABLES `RESPONSAVEL` WRITE;
/*!40000 ALTER TABLE `RESPONSAVEL` DISABLE KEYS */;
INSERT INTO `RESPONSAVEL` VALUES (1,'Vinícios Rodrigues','08911768456','5646164651643165874'),(2,'Caroline','11111111111','5516513086464168464'),(3,'Teste','51789658741','5548646573521387351'),(4,'Teste2','33333333333','45646841684646464341'),(5,'Te','33333333331','45646841684646464341'),(6,'Te','33333333331','45646841684646464341'),(7,'Te','33333333331','45646841684646464341'),(8,'Te','33333333333','45646841684646464341');
/*!40000 ALTER TABLE `RESPONSAVEL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TIPO_EVENTO`
--

DROP TABLE IF EXISTS `TIPO_EVENTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TIPO_EVENTO` (
  `ID` decimal(10,0) NOT NULL,
  `NOME` varchar(45) NOT NULL,
  `DESCRICAO` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TIPO_EVENTO`
--

LOCK TABLES `TIPO_EVENTO` WRITE;
/*!40000 ALTER TABLE `TIPO_EVENTO` DISABLE KEYS */;
INSERT INTO `TIPO_EVENTO` VALUES (1,'Hemograma','Hemograma'),(2,'Radiografia','Radiografia'),(3,'Ultrasonografia','Ultrasonografia');
/*!40000 ALTER TABLE `TIPO_EVENTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VISITA`
--

DROP TABLE IF EXISTS `VISITA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VISITA` (
  `ID` decimal(10,0) NOT NULL,
  `ID_PACIENTE` decimal(10,0) NOT NULL,
  `DH_ENTRADA` datetime NOT NULL,
  `DH_SAIDA` datetime DEFAULT NULL,
  `MOTIVO_ENTRADA` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_VISITA_PACIENTE_idx` (`ID_PACIENTE`),
  CONSTRAINT `FK_VISITA_PACIENTE` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `PACIENTE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VISITA`
--

LOCK TABLES `VISITA` WRITE;
/*!40000 ALTER TABLE `VISITA` DISABLE KEYS */;
/*!40000 ALTER TABLE `VISITA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `VISITA_EVENTO`
--

DROP TABLE IF EXISTS `VISITA_EVENTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `VISITA_EVENTO` (
  `ID` decimal(10,0) NOT NULL,
  `ID_VISITA` decimal(10,0) NOT NULL,
  `ID_PACIENTE` decimal(10,0) NOT NULL,
  `ID_TIPO_EVENTO` decimal(10,0) NOT NULL,
  `ID_RESPONSAVEL` bigint(20) NOT NULL,
  `URL_DOC` varchar(500) DEFAULT NULL,
  `TITULO` varchar(45) NOT NULL,
  `OBSERVACOES` varchar(500) DEFAULT NULL,
  `DH_EVENTO` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_VISEVENT_EVENTO_idx` (`ID_VISITA`),
  KEY `FK_VISEVENT_TIPO_idx` (`ID_TIPO_EVENTO`),
  KEY `FK_VISEVENT_PAC` (`ID_PACIENTE`),
  KEY `FK_VISEVENT_RESP_idx` (`ID_RESPONSAVEL`),
  CONSTRAINT `FK_VISEVENT_EVENTO` FOREIGN KEY (`ID_VISITA`) REFERENCES `VISITA` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VISEVENT_PAC` FOREIGN KEY (`ID_PACIENTE`) REFERENCES `PACIENTE` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VISEVENT_RESP` FOREIGN KEY (`ID_RESPONSAVEL`) REFERENCES `RESPONSAVEL` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_VISEVENT_TIPO` FOREIGN KEY (`ID_TIPO_EVENTO`) REFERENCES `TIPO_EVENTO` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `VISITA_EVENTO`
--

LOCK TABLES `VISITA_EVENTO` WRITE;
/*!40000 ALTER TABLE `VISITA_EVENTO` DISABLE KEYS */;
/*!40000 ALTER TABLE `VISITA_EVENTO` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-08 20:54:47