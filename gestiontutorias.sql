-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: gestiontutorias
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asistencia`
--

DROP TABLE IF EXISTS `asistencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asistencia` (
  `idAsistencia` int NOT NULL AUTO_INCREMENT,
  `idReporteTutoria` int NOT NULL,
  `idTutorado` int NOT NULL,
  `asistio` tinyint(1) DEFAULT '0',
  `enRiesgo` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`idAsistencia`),
  KEY `fk_asist_rep` (`idReporteTutoria`),
  KEY `fk_asist_alum` (`idTutorado`),
  CONSTRAINT `fk_asist_alum` FOREIGN KEY (`idTutorado`) REFERENCES `tutorado` (`idTutorado`),
  CONSTRAINT `fk_asist_rep` FOREIGN KEY (`idReporteTutoria`) REFERENCES `reporte_tutoria` (`idReporteTutoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asistencia`
--

LOCK TABLES `asistencia` WRITE;
/*!40000 ALTER TABLE `asistencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `asistencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_experiencia_educativa`
--

DROP TABLE IF EXISTS `catalogo_experiencia_educativa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogo_experiencia_educativa` (
  `idCatalogoEE` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`idCatalogoEE`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_experiencia_educativa`
--

LOCK TABLES `catalogo_experiencia_educativa` WRITE;
/*!40000 ALTER TABLE `catalogo_experiencia_educativa` DISABLE KEYS */;
INSERT INTO `catalogo_experiencia_educativa` VALUES (1,'Principios de Construcción de Software'),(2,'Principios de Diseño de Software'),(3,'Bases de Datos'),(4,'Programación Orientada a Objetos'),(5,'Estructuras de Datos'),(6,'Redes de Computadoras'),(7,'Sistemas Operativos'),(8,'Ingeniería de Requerimientos');
/*!40000 ALTER TABLE `catalogo_experiencia_educativa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `catalogo_profesor`
--

DROP TABLE IF EXISTS `catalogo_profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `catalogo_profesor` (
  `idCatalogoProfesor` int NOT NULL AUTO_INCREMENT,
  `nombreCompleto` varchar(255) NOT NULL,
  PRIMARY KEY (`idCatalogoProfesor`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogo_profesor`
--

LOCK TABLES `catalogo_profesor` WRITE;
/*!40000 ALTER TABLE `catalogo_profesor` DISABLE KEYS */;
INSERT INTO `catalogo_profesor` VALUES (1,'Ángel Juan Sánchez García'),(2,'María Karen Cortés Verdín'),(3,'Jorge Octavio Ocharán Hernández'),(4,'Luis Gerardo Montané Jiménez'),(5,'Guillermo Humberto De la Rosa Peñaloza'),(6,'Patricia Martínez Moreno'),(7,'Juan Carlos Pérez Arriaga'),(8,'José Rafael Rojano Cáceres'),(9,'María de los Ángeles Arenas Valdés'),(10,'Rafael Gómez Aguilar'),(11,'Zoylo Roberto Méndez'),(12,'José Juan Muñoz León'),(13,'Fredy Castañeda Sánchez'),(14,'Verónica Bolaños López'),(15,'Octavio Elihú Monterrosa Utrera'),(16,'Lisbeth Hernández González'),(17,'Celia Romero'),(18,'Carmen Mezura Godoy'),(19,'Eduardo Ahumada'),(20,'Virginia Lagunes Barradas');
/*!40000 ALTER TABLE `catalogo_profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evidencia`
--

DROP TABLE IF EXISTS `evidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evidencia` (
  `idEvidencia` int NOT NULL AUTO_INCREMENT,
  `idReporteTutoria` int NOT NULL,
  `nombreArchivo` varchar(255) NOT NULL,
  `archivo` longblob NOT NULL,
  PRIMARY KEY (`idEvidencia`),
  KEY `fk_evidencia_reporte` (`idReporteTutoria`),
  CONSTRAINT `fk_evidencia_reporte` FOREIGN KEY (`idReporteTutoria`) REFERENCES `reporte_tutoria` (`idReporteTutoria`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evidencia`
--

LOCK TABLES `evidencia` WRITE;
/*!40000 ALTER TABLE `evidencia` DISABLE KEYS */;
/*!40000 ALTER TABLE `evidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario_tutor`
--

DROP TABLE IF EXISTS `horario_tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horario_tutor` (
  `idHorario` int NOT NULL AUTO_INCREMENT,
  `idTutor` int NOT NULL,
  `idSesion` int NOT NULL,
  `horaInicio` time NOT NULL,
  PRIMARY KEY (`idHorario`),
  UNIQUE KEY `uq_tutor_sesion` (`idTutor`,`idSesion`),
  KEY `fk_horario_sesion` (`idSesion`),
  CONSTRAINT `fk_horario_sesion` FOREIGN KEY (`idSesion`) REFERENCES `sesion_tutoria` (`idSesion`),
  CONSTRAINT `fk_horario_tutor` FOREIGN KEY (`idTutor`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario_tutor`
--

LOCK TABLES `horario_tutor` WRITE;
/*!40000 ALTER TABLE `horario_tutor` DISABLE KEYS */;
/*!40000 ALTER TABLE `horario_tutor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oferta_academica`
--

DROP TABLE IF EXISTS `oferta_academica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oferta_academica` (
  `idOferta` int NOT NULL AUTO_INCREMENT,
  `idProgramaEducativo` int NOT NULL,
  `idCatalogoEE` int NOT NULL,
  `idCatalogoProfesor` int NOT NULL,
  PRIMARY KEY (`idOferta`),
  UNIQUE KEY `uq_asignacion_academica` (`idProgramaEducativo`,`idCatalogoEE`,`idCatalogoProfesor`),
  KEY `fk_oferta_ee` (`idCatalogoEE`),
  KEY `fk_oferta_prof` (`idCatalogoProfesor`),
  CONSTRAINT `fk_oferta_ee` FOREIGN KEY (`idCatalogoEE`) REFERENCES `catalogo_experiencia_educativa` (`idCatalogoEE`),
  CONSTRAINT `fk_oferta_pe` FOREIGN KEY (`idProgramaEducativo`) REFERENCES `programa_educativo` (`idProgramaEducativo`),
  CONSTRAINT `fk_oferta_prof` FOREIGN KEY (`idCatalogoProfesor`) REFERENCES `catalogo_profesor` (`idCatalogoProfesor`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oferta_academica`
--

LOCK TABLES `oferta_academica` WRITE;
/*!40000 ALTER TABLE `oferta_academica` DISABLE KEYS */;
INSERT INTO `oferta_academica` VALUES (20,1,3,6),(19,1,3,20),(23,1,4,7),(21,1,5,4),(22,1,5,19),(11,2,3,18),(13,2,4,5),(9,2,6,11),(10,2,6,13),(12,2,7,12),(1,3,1,1),(2,3,1,3),(4,3,2,1),(3,3,2,2),(6,3,3,18),(5,3,3,20),(8,3,4,4),(7,3,8,2),(18,4,5,19),(14,4,6,11),(15,4,6,15),(16,4,7,12),(17,4,7,16);
/*!40000 ALTER TABLE `oferta_academica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `periodo_escolar`
--

DROP TABLE IF EXISTS `periodo_escolar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodo_escolar` (
  `idPeriodo` int NOT NULL AUTO_INCREMENT,
  `fechaInicio` date NOT NULL,
  `fechaFin` date NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPeriodo`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodo_escolar`
--

LOCK TABLES `periodo_escolar` WRITE;
/*!40000 ALTER TABLE `periodo_escolar` DISABLE KEYS */;
INSERT INTO `periodo_escolar` VALUES (1,'2024-08-01','2025-01-31','AGOSTO 2024 - ENERO 2025'),(2,'2025-02-01','2025-07-31','FEBRERO - JULIO 2025'),(3,'2025-08-01','2026-01-31','AGOSTO 2025 - ENERO 2026'),(4,'2026-02-01','2026-07-31','FEBRERO - JULIO 2026');
/*!40000 ALTER TABLE `periodo_escolar` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = cp850 */ ;
/*!50003 SET character_set_results = cp850 */ ;
/*!50003 SET collation_connection  = cp850_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_nombre_periodo` BEFORE INSERT ON `periodo_escolar` FOR EACH ROW BEGIN
    DECLARE anio INT;
    DECLARE mes INT;
    SET anio = YEAR(NEW.fechaInicio);
    SET mes = MONTH(NEW.fechaInicio);

    IF mes = 2 THEN 
        SET NEW.nombre = CONCAT('FEBRERO - JULIO ', anio);
    ELSEIF mes = 8 THEN 
        SET NEW.nombre = CONCAT('AGOSTO ', anio, ' - ENERO ', anio + 1);
    ELSE
        SET NEW.nombre = CONCAT('PERIODO ESPECIAL ', anio);
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `problematica_academica`
--

DROP TABLE IF EXISTS `problematica_academica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problematica_academica` (
  `idProblematica` int NOT NULL AUTO_INCREMENT,
  `idReporteTutoria` int NOT NULL,
  `idTutorado` int NOT NULL,
  `tipo` enum('ACADEMICA','PERSONAL') NOT NULL,
  `descripcion` text NOT NULL,
  `experienciaEducativa` varchar(150) DEFAULT NULL,
  `profesor` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`idProblematica`),
  KEY `fk_prob_rep` (`idReporteTutoria`),
  KEY `fk_prob_alum` (`idTutorado`),
  CONSTRAINT `fk_prob_alum` FOREIGN KEY (`idTutorado`) REFERENCES `tutorado` (`idTutorado`),
  CONSTRAINT `fk_prob_rep` FOREIGN KEY (`idReporteTutoria`) REFERENCES `reporte_tutoria` (`idReporteTutoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problematica_academica`
--

LOCK TABLES `problematica_academica` WRITE;
/*!40000 ALTER TABLE `problematica_academica` DISABLE KEYS */;
/*!40000 ALTER TABLE `problematica_academica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problematica_seleccionada`
--

DROP TABLE IF EXISTS `problematica_seleccionada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `problematica_seleccionada` (
  `idSeleccion` int NOT NULL AUTO_INCREMENT,
  `idReporteGeneral` int NOT NULL,
  `idProblematica` int NOT NULL,
  PRIMARY KEY (`idSeleccion`),
  KEY `fk_sel_rg` (`idReporteGeneral`),
  KEY `fk_sel_prob` (`idProblematica`),
  CONSTRAINT `fk_sel_prob` FOREIGN KEY (`idProblematica`) REFERENCES `problematica_academica` (`idProblematica`),
  CONSTRAINT `fk_sel_rg` FOREIGN KEY (`idReporteGeneral`) REFERENCES `reporte_general` (`idReporteGeneral`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problematica_seleccionada`
--

LOCK TABLES `problematica_seleccionada` WRITE;
/*!40000 ALTER TABLE `problematica_seleccionada` DISABLE KEYS */;
/*!40000 ALTER TABLE `problematica_seleccionada` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `programa_educativo`
--

DROP TABLE IF EXISTS `programa_educativo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `programa_educativo` (
  `idProgramaEducativo` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `idJefeCarrera` int DEFAULT NULL,
  `idCoordinador` int DEFAULT NULL,
  PRIMARY KEY (`idProgramaEducativo`),
  KEY `fk_pe_jefe` (`idJefeCarrera`),
  KEY `fk_pe_coord` (`idCoordinador`),
  CONSTRAINT `fk_pe_coord` FOREIGN KEY (`idCoordinador`) REFERENCES `usuario` (`idUsuario`) ON DELETE SET NULL,
  CONSTRAINT `fk_pe_jefe` FOREIGN KEY (`idJefeCarrera`) REFERENCES `usuario` (`idUsuario`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `programa_educativo`
--

LOCK TABLES `programa_educativo` WRITE;
/*!40000 ALTER TABLE `programa_educativo` DISABLE KEYS */;
INSERT INTO `programa_educativo` VALUES (1,'Lic. en Ingeniería en Ciencia de Datos (Plan 2024)',NULL,NULL),(2,'Lic. en Ingeniería en Sistemas y Tecnologías de la Información (Plan 2023)',NULL,NULL),(3,'Lic. en Ingeniería de Software (Plan 2023)',NULL,NULL),(4,'Lic. en Ingeniería de Ciberseguridad e Infraestructura de Cómputo (Plan 2023)',NULL,NULL);
/*!40000 ALTER TABLE `programa_educativo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_general`
--

DROP TABLE IF EXISTS `reporte_general`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_general` (
  `idReporteGeneral` int NOT NULL AUTO_INCREMENT,
  `idCoordinador` int NOT NULL,
  `idSesion` int NOT NULL,
  `idProgramaEducativo` int NOT NULL,
  `fechaElaboracion` datetime DEFAULT CURRENT_TIMESTAMP,
  `comentariosGenerales` text,
  `totalAsistentes` int DEFAULT '0',
  `totalEnRiesgo` int DEFAULT '0',
  `estatus` enum('BORRADOR','ENVIADO') DEFAULT 'BORRADOR',
  PRIMARY KEY (`idReporteGeneral`),
  KEY `fk_rg_coord` (`idCoordinador`),
  KEY `fk_rg_sesion` (`idSesion`),
  KEY `fk_rg_pe` (`idProgramaEducativo`),
  CONSTRAINT `fk_rg_coord` FOREIGN KEY (`idCoordinador`) REFERENCES `usuario` (`idUsuario`),
  CONSTRAINT `fk_rg_pe` FOREIGN KEY (`idProgramaEducativo`) REFERENCES `programa_educativo` (`idProgramaEducativo`),
  CONSTRAINT `fk_rg_sesion` FOREIGN KEY (`idSesion`) REFERENCES `sesion_tutoria` (`idSesion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_general`
--

LOCK TABLES `reporte_general` WRITE;
/*!40000 ALTER TABLE `reporte_general` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_general` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reporte_tutoria`
--

DROP TABLE IF EXISTS `reporte_tutoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reporte_tutoria` (
  `idReporteTutoria` int NOT NULL AUTO_INCREMENT,
  `idTutor` int NOT NULL,
  `idSesion` int NOT NULL,
  `idProgramaEducativo` int NOT NULL,
  `descripcionGeneral` varchar(300) DEFAULT NULL,
  `estado` enum('BORRADOR','ENVIADO','REVISADO') DEFAULT 'BORRADOR',
  `fechaEntrega` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idReporteTutoria`),
  KEY `fk_rt_tutor` (`idTutor`),
  KEY `fk_rt_sesion` (`idSesion`),
  KEY `fk_rt_pe` (`idProgramaEducativo`),
  CONSTRAINT `fk_rt_pe` FOREIGN KEY (`idProgramaEducativo`) REFERENCES `programa_educativo` (`idProgramaEducativo`),
  CONSTRAINT `fk_rt_sesion` FOREIGN KEY (`idSesion`) REFERENCES `sesion_tutoria` (`idSesion`),
  CONSTRAINT `fk_rt_tutor` FOREIGN KEY (`idTutor`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reporte_tutoria`
--

LOCK TABLES `reporte_tutoria` WRITE;
/*!40000 ALTER TABLE `reporte_tutoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte_tutoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `respuesta_reporte_general`
--

DROP TABLE IF EXISTS `respuesta_reporte_general`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `respuesta_reporte_general` (
  `idRespuesta` int NOT NULL AUTO_INCREMENT,
  `idReporteGeneral` int NOT NULL,
  `idUsuarioResponde` int NOT NULL,
  `mensaje` text NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idRespuesta`),
  KEY `fk_resp_rg` (`idReporteGeneral`),
  KEY `fk_resp_user` (`idUsuarioResponde`),
  CONSTRAINT `fk_resp_rg` FOREIGN KEY (`idReporteGeneral`) REFERENCES `reporte_general` (`idReporteGeneral`),
  CONSTRAINT `fk_resp_user` FOREIGN KEY (`idUsuarioResponde`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `respuesta_reporte_general`
--

LOCK TABLES `respuesta_reporte_general` WRITE;
/*!40000 ALTER TABLE `respuesta_reporte_general` DISABLE KEYS */;
/*!40000 ALTER TABLE `respuesta_reporte_general` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semestre`
--

DROP TABLE IF EXISTS `semestre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semestre` (
  `idSemestre` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`idSemestre`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semestre`
--

LOCK TABLES `semestre` WRITE;
/*!40000 ALTER TABLE `semestre` DISABLE KEYS */;
INSERT INTO `semestre` VALUES (1,'1'),(2,'2'),(3,'3'),(4,'4'),(5,'5'),(6,'6'),(7,'7'),(8,'8'),(9,'9'),(10,'10'),(11,'11'),(12,'12');
/*!40000 ALTER TABLE `semestre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sesion_tutoria`
--

DROP TABLE IF EXISTS `sesion_tutoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesion_tutoria` (
  `idSesion` int NOT NULL AUTO_INCREMENT,
  `idPeriodo` int NOT NULL,
  `idProgramaEducativo` int NOT NULL,
  `numeroSesion` int NOT NULL,
  `fechaSesion` date NOT NULL,
  `estado` enum('ABIERTA','CERRADA') DEFAULT 'ABIERTA',
  PRIMARY KEY (`idSesion`),
  KEY `fk_sesion_periodo` (`idPeriodo`),
  KEY `fk_sesion_pe` (`idProgramaEducativo`),
  CONSTRAINT `fk_sesion_pe` FOREIGN KEY (`idProgramaEducativo`) REFERENCES `programa_educativo` (`idProgramaEducativo`),
  CONSTRAINT `fk_sesion_periodo` FOREIGN KEY (`idPeriodo`) REFERENCES `periodo_escolar` (`idPeriodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesion_tutoria`
--

LOCK TABLES `sesion_tutoria` WRITE;
/*!40000 ALTER TABLE `sesion_tutoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `sesion_tutoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorado`
--

DROP TABLE IF EXISTS `tutorado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorado` (
  `idTutorado` int NOT NULL AUTO_INCREMENT,
  `matricula` varchar(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidoPaterno` varchar(255) NOT NULL,
  `apellidoMaterno` varchar(255) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `idProgramaEducativo` int NOT NULL,
  `idSemestre` int NOT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`idTutorado`),
  UNIQUE KEY `matricula` (`matricula`),
  KEY `fk_tutorado_pe` (`idProgramaEducativo`),
  KEY `fk_tutorado_semestre` (`idSemestre`),
  CONSTRAINT `fk_tutorado_pe` FOREIGN KEY (`idProgramaEducativo`) REFERENCES `programa_educativo` (`idProgramaEducativo`),
  CONSTRAINT `fk_tutorado_semestre` FOREIGN KEY (`idSemestre`) REFERENCES `semestre` (`idSemestre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorado`
--

LOCK TABLES `tutorado` WRITE;
/*!40000 ALTER TABLE `tutorado` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutorado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `noTrabajador` varchar(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellidoPaterno` varchar(255) NOT NULL,
  `apellidoMaterno` varchar(255) DEFAULT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `esAdministrador` tinyint(1) DEFAULT '0',
  `esTutor` tinyint(1) DEFAULT '0',
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `noTrabajador` (`noTrabajador`),
  UNIQUE KEY `correo` (`correo`),
  UNIQUE KEY `telefono` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'gestiontutorias'
--

--
-- Dumping routines for database 'gestiontutorias'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-06 19:14:31
