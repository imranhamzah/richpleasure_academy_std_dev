-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.29-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table android_api.000subjects
CREATE TABLE IF NOT EXISTS `000subjects` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_no` int(11) NOT NULL DEFAULT '0',
  `subject_code` varchar(50) DEFAULT NULL,
  `subject_icon_url` text,
  `edu_year_id` int(11) NOT NULL DEFAULT '0',
  `subject_short_form` varchar(4) DEFAULT NULL,
  `subject_title` varchar(50) DEFAULT NULL,
  `subject_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.000subjects: ~2 rows (approximately)
/*!40000 ALTER TABLE `000subjects` DISABLE KEYS */;
INSERT INTO `000subjects` (`subject_id`, `subject_no`, `subject_code`, `subject_icon_url`, `edu_year_id`, `subject_short_form`, `subject_title`, `subject_remark`) VALUES
	(1, 1, '3472', 'https://s3-ap-southeast-1.amazonaws.com/osemv3-content-test/my/cmn/icon/subject/icon_m3.png', 10, 'MT', 'Additional Mathematics', ''),
	(2, 2, '3472', 'https://s3-ap-southeast-1.amazonaws.com/osemv3-content-test/my/cmn/icon/subject/icon_m3.png', 11, 'MT', 'Additional Mathematics', '');
/*!40000 ALTER TABLE `000subjects` ENABLE KEYS */;

-- Dumping structure for table android_api.001chapters
CREATE TABLE IF NOT EXISTS `001chapters` (
  `chapter_id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_no` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) DEFAULT NULL,
  `edu_year_id` int(11) NOT NULL DEFAULT '0',
  `chapter_title` varchar(50) DEFAULT NULL,
  `chapter_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Dumping data for table android_api.001chapters: ~2 rows (approximately)
/*!40000 ALTER TABLE `001chapters` DISABLE KEYS */;
INSERT INTO `001chapters` (`chapter_id`, `chapter_no`, `subject_id`, `edu_year_id`, `chapter_title`, `chapter_remark`) VALUES
	(1, 1, 1, 10, 'Fungsi', NULL),
	(2, 2, 1, 10, 'Persamaan Kuadratik', NULL);
/*!40000 ALTER TABLE `001chapters` ENABLE KEYS */;

-- Dumping structure for table android_api.002sub_chapters
CREATE TABLE IF NOT EXISTS `002sub_chapters` (
  `subchapter_id` int(11) NOT NULL AUTO_INCREMENT,
  `subchapter_no` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) DEFAULT NULL,
  `edu_year_id` int(11) NOT NULL DEFAULT '0',
  `chapter_id` int(11) NOT NULL DEFAULT '0',
  `subchapter_title` varchar(50) DEFAULT NULL,
  `subchapter_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`subchapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Dumping data for table android_api.002sub_chapters: ~4 rows (approximately)
/*!40000 ALTER TABLE `002sub_chapters` DISABLE KEYS */;
INSERT INTO `002sub_chapters` (`subchapter_id`, `subchapter_no`, `subject_id`, `edu_year_id`, `chapter_id`, `subchapter_title`, `subchapter_remark`) VALUES
	(1, 1, 1, 10, 1, 'Hubungan', ''),
	(2, 2, 1, 10, 1, 'Fungsi', ''),
	(3, 3, 1, 10, 1, 'Fungsi Gubahan', ''),
	(4, 4, 1, 10, 1, 'Fungsi songsangan', '');
/*!40000 ALTER TABLE `002sub_chapters` ENABLE KEYS */;

-- Dumping structure for table android_api.003learning_outcome
CREATE TABLE IF NOT EXISTS `003learning_outcome` (
  `outcome_id` int(11) NOT NULL AUTO_INCREMENT,
  `outcome_no` varchar(50) DEFAULT NULL,
  `edu_year_id` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) DEFAULT NULL,
  `chapter_id` int(11) NOT NULL,
  `subchapter_id` int(11) DEFAULT '0',
  `outcome_description` text,
  `outcome_level` int(11) DEFAULT NULL,
  `suggested_activity` text,
  `outcome_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`outcome_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.003learning_outcome: ~13 rows (approximately)
/*!40000 ALTER TABLE `003learning_outcome` DISABLE KEYS */;
INSERT INTO `003learning_outcome` (`outcome_id`, `outcome_no`, `edu_year_id`, `subject_id`, `chapter_id`, `subchapter_id`, `outcome_description`, `outcome_level`, `suggested_activity`, `outcome_remark`) VALUES
	(1, '1.1', 10, 1, 1, 1, 'Mewakilkan sesuatu hubungan.', 1, 'Pendedahan idea tentang set diperlukan. Contoh-contoh hubungan dalam kehidupan harian perlu dibincangkan.\r\n', NULL),
	(2, '1.2', 10, 1, 1, 1, 'Menentukan domain, kodomain, objek, imej dan julat bagi sesuatu hubungan.', 2, 'Kes hubungan meliputi gambar rajah anak panah, pasangan bertertib dan graf.\r\n', NULL),
	(3, '1.3', 10, 1, 1, 1, 'Mengelaskan sesuatu hubungan yang ditunjukkan dalam rajah pemetaan sebagai jenis: satu kepada satu, banyak kepada satu, satu kepada banyak dan banyak kepada banyak.', 3, NULL, NULL),
	(4, '2.1', 10, 1, 1, 2, 'Mengenal pasti fungsi sebagai sejenis hubungan khas.', 1, 'Fungsi diwakilkan dalam bentuk gambar rajah anak panah, pasangan bertertib atau graf.\r\n', NULL),
	(5, '2.2', 10, 1, 1, 2, 'Menulis sesuatu fungsi dengan menggunakan tatatanda fungsi.', 2, 'Contoh : f: x ? 2x f(x) = 2x “f: x ? 2x” boleh dibaca sebagai “fungsi f yang memetakan x kepada 2x”. Contoh fungsi yang bukan berasaskan matematik diberikan juga.\r\n', NULL),
	(6, '2.3', 10, 1, 1, 2, 'Menentukan domain, julat, objek dan imej sesuatu fungsi.', 2, 'Contoh fungsi meliputi fungsi algebra dan trigonometri, termasuk fungsi nilai mutlak f:x ? | f(x) |, f(x) ialah fungsi linear, kuadratik atau trigonometri.\r\n', NULL),
	(7, '2.4', 10, 1, 1, 2, 'Menentukan imej sesuatu fungsi apabila objek diberi dan sebaliknya.', 3, 'Nama jenis-jenis fungsi tidak perlu ditegaskan.\r\n', NULL),
	(8, '3.1', 10, 1, 1, 3, 'Menentukan gubahan dua fungsi.', 2, 'Fungsi yang terlibat terhad kepada fungsi algebra. fg(x) bermakna f(g(x) ). Kaedah gambar rajah anak panah atau algebra boleh digunakan.\r\n', NULL),
	(9, '3.2', 10, 1, 1, 3, 'Menentukan imej sesuatu fungsi gubahan apabila objek diberi dan sebaliknya.', 2, 'Imej fungsi gubahan termasuk nilai tunggal atau sesuatu julat.\r\n', NULL),
	(10, '3.3', 10, 1, 1, 3, 'Mencari satu fungsi berkaitan apabila diberi fungsi gubahan dan salah satu fungsinya.', 3, NULL, NULL),
	(11, '4.1', 10, 1, 1, 4, 'Mencari nilai dalam domain yang sepadan dengan sesuatu nilai dalam julat melalui pemetaan songsangan apabila fungsinya diberi.', 2, 'Fungsi yang terlibat terhad kepada fungsi algebra. Songsangan bagi fungsi gubahan tidak diperlukan.\r\n', NULL),
	(12, '4.2', 10, 1, 1, 4, 'Menentukan fungsi songsangan secara algebra.', 3, 'Perlu diterangkan bahawa songsangan sesuatu fungsi itu tidak semestinya suatu fungsi juga.\r\n', NULL),
	(13, '4.3', 10, 1, 1, 4, 'Menentukan dan menyatakan syarat untuk kewujudan fungsi songsangan.', 3, NULL, NULL);
/*!40000 ALTER TABLE `003learning_outcome` ENABLE KEYS */;

-- Dumping structure for table android_api.edu_year
CREATE TABLE IF NOT EXISTS `edu_year` (
  `edu_year_id` int(11) NOT NULL AUTO_INCREMENT,
  `edu_year_title_en` varchar(50) DEFAULT NULL,
  `edu_year_title_bm` varchar(50) DEFAULT NULL,
  `edu_year_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`edu_year_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.edu_year: ~11 rows (approximately)
/*!40000 ALTER TABLE `edu_year` DISABLE KEYS */;
INSERT INTO `edu_year` (`edu_year_id`, `edu_year_title_en`, `edu_year_title_bm`, `edu_year_remark`) VALUES
	(1, 'Standard 1', 'Darjah 1', ''),
	(2, 'Standard 2', 'Darjah 2', ''),
	(3, 'Standard 3', 'Darjah 3', ''),
	(4, 'Standard 4', 'Darjah 4', ''),
	(5, 'Standard 5', 'Darjah 5', ''),
	(6, 'Standard 6', 'Darjah 6', ''),
	(7, 'Form 1', 'Tingkatan 1', ''),
	(8, 'Form 2', 'Tingkatan 2', ''),
	(9, 'Form 3', 'Tingkatan 3', ''),
	(10, 'Form 4', 'Tingkatan 4', ''),
	(11, 'Form 5', 'Tingkatan 6', '');
/*!40000 ALTER TABLE `edu_year` ENABLE KEYS */;

-- Dumping structure for table android_api.lessons
CREATE TABLE IF NOT EXISTS `lessons` (
  `lesson_id` int(11) NOT NULL AUTO_INCREMENT,
  `lesson_no` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) NOT NULL DEFAULT '0',
  `chapter_id` int(11) NOT NULL DEFAULT '0',
  `subchapter_id` int(11) NOT NULL DEFAULT '0',
  `content_text` text,
  `lesson_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lesson_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.lessons: ~6 rows (approximately)
/*!40000 ALTER TABLE `lessons` DISABLE KEYS */;
INSERT INTO `lessons` (`lesson_id`, `lesson_no`, `subject_id`, `chapter_id`, `subchapter_id`, `content_text`, `lesson_remark`) VALUES
	(1, 1, 1, 1, 1, 'Content A part 1', NULL),
	(2, 2, 1, 1, 1, 'Content A part 2', NULL),
	(3, 1, 1, 1, 2, 'Content B part 1', NULL),
	(4, 2, 1, 1, 2, 'Content B part 2', NULL),
	(5, 1, 1, 1, 3, 'Content C', NULL),
	(6, 1, 1, 1, 4, 'Content D', NULL);
/*!40000 ALTER TABLE `lessons` ENABLE KEYS */;

-- Dumping structure for table android_api.students
CREATE TABLE IF NOT EXISTS `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_reg_no` varchar(50) DEFAULT NULL,
  `student_email` varchar(50) DEFAULT NULL,
  `student_username` varchar(50) DEFAULT NULL,
  `student_fullname` varchar(50) DEFAULT NULL,
  `mobile_phoneno` varchar(50) DEFAULT NULL,
  `student_remark` varchar(50) DEFAULT NULL,
  `dt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.students: ~2 rows (approximately)
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` (`student_id`, `student_reg_no`, `student_email`, `student_username`, `student_fullname`, `mobile_phoneno`, `student_remark`, `dt_created`) VALUES
	(1, 'STD0000001', 'dayangkusri@gmail.com', 'dayangku', 'Dayangku Sri Arjuna', '0143635185', 'Mak', '2018-01-17 10:33:05'),
	(2, 'STD0000002', 'hanif@gmail.com', 'hanif2018', 'Muhammad Hanif', '0143635186', 'Anak Baru', '2018-01-17 10:33:05');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;

-- Dumping structure for table android_api.student_subjects
CREATE TABLE IF NOT EXISTS `student_subjects` (
  `student_subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) NOT NULL DEFAULT '0',
  `student_subject_remark` varchar(50) DEFAULT NULL,
  `dt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`student_subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- Dumping data for table android_api.student_subjects: ~1 rows (approximately)
/*!40000 ALTER TABLE `student_subjects` DISABLE KEYS */;
INSERT INTO `student_subjects` (`student_subject_id`, `student_id`, `subject_id`, `student_subject_remark`, `dt_created`) VALUES
	(1, 1, 1, NULL, '2018-01-17 10:34:54');
/*!40000 ALTER TABLE `student_subjects` ENABLE KEYS */;

-- Dumping structure for table android_api.tutors
CREATE TABLE IF NOT EXISTS `tutors` (
  `tutor_id` int(11) NOT NULL AUTO_INCREMENT,
  `tutor_registered_no` varchar(50) DEFAULT '0',
  `tutor_name` varchar(50) DEFAULT NULL,
  `short_description` varchar(50) DEFAULT NULL,
  `mobile_phoneno` varchar(50) DEFAULT NULL,
  `tutor_remark` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`tutor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.tutors: ~4 rows (approximately)
/*!40000 ALTER TABLE `tutors` DISABLE KEYS */;
INSERT INTO `tutors` (`tutor_id`, `tutor_registered_no`, `tutor_name`, `short_description`, `mobile_phoneno`, `tutor_remark`) VALUES
	(1, 'RPT100001', 'Imran Hamzah', 'Kenapa saya? Berpengalaman lebih 5 tahun dalam pen', '0183954840', 'test'),
	(2, 'RPT100002', 'Ahmad Yusoff', 'Yup belajar!', '0183954841', 'test'),
	(3, 'RPT100003', 'Muhammad Naim', 'Jom belajar ABC', '0183954842', 'test'),
	(4, 'RPT100004', 'Muhammad Hassan Husni', 'Jom belajar DEF', '0183954843', 'test');
/*!40000 ALTER TABLE `tutors` ENABLE KEYS */;

-- Dumping structure for table android_api.tutor_subjects
CREATE TABLE IF NOT EXISTS `tutor_subjects` (
  `tutor_subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `tutor_id` int(11) NOT NULL DEFAULT '0',
  `subject_id` int(11) NOT NULL DEFAULT '0',
  `tutor_subject_remark` varchar(50) DEFAULT NULL,
  `dt_created` datetime DEFAULT NULL,
  PRIMARY KEY (`tutor_subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table android_api.tutor_subjects: ~2 rows (approximately)
/*!40000 ALTER TABLE `tutor_subjects` DISABLE KEYS */;
INSERT INTO `tutor_subjects` (`tutor_subject_id`, `tutor_id`, `subject_id`, `tutor_subject_remark`, `dt_created`) VALUES
	(1, 1, 1, NULL, '2018-01-17 10:29:07'),
	(2, 1, 2, NULL, '2018-01-17 10:29:07');
/*!40000 ALTER TABLE `tutor_subjects` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
