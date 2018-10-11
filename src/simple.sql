/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : simple

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2017-08-18 17:10:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tc_member
-- ----------------------------
DROP TABLE IF EXISTS `tc_member`;
CREATE TABLE `tc_member` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `bg` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `idCard` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nickname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `photo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `photoSmall` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `puid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `rid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `smsAuth` bit(1) NOT NULL,
  `currentProjectId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_58s2hjoouehrpljvrdb0ld2cb` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tc_member
-- ----------------------------
INSERT INTO `tc_member` VALUES ('1', '2017-08-18 10:52:25', null, '', 'bg-4', '2017-08-18 10:52:29', null, '13812345678', null, 'abc', 'e10adc3949ba59abbe56e057f20f883e', '', null, null, null, null, '', '1');

-- ----------------------------
-- Table structure for tc_project
-- ----------------------------
DROP TABLE IF EXISTS `tc_project`;
CREATE TABLE `tc_project` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attendanceRadius` float DEFAULT NULL,
  `auth` bit(1) DEFAULT NULL,
  `buildUnit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `completeDate` date DEFAULT NULL,
  `constructUnit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `designUnit` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastAccessInfo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `lastEmployeeUpdateTime` datetime DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `manager` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `managerPhone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pid` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `uk` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area_id` bigint(20) DEFAULT NULL,
  `member_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r5b2mks7v9402b1hwwtj6pfsf` (`pid`),
  UNIQUE KEY `UK_hbyt27xrlepg7f54b9i6n9d44` (`uk`),
  UNIQUE KEY `UK_am1o7ykwyykfd6yfxqwuwubwl` (`username`),
  KEY `FK_th3csrfcjxx1yijkhn55leuek` (`area_id`),
  KEY `FK_te389d3h7crlssuo37w8vd5ie` (`member_id`),
  CONSTRAINT `FK_te389d3h7crlssuo37w8vd5ie` FOREIGN KEY (`member_id`) REFERENCES `tc_member` (`id`),
  CONSTRAINT `FK_th3csrfcjxx1yijkhn55leuek` FOREIGN KEY (`area_id`) REFERENCES `t_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of tc_project
-- ----------------------------
INSERT INTO `tc_project` VALUES ('1', '2017-08-18 10:53:53', '2017-08-18 10:53:56', '', null, null, '', null, null, null, null, null, null, null, null, null, null, '测试工程1', null, null, null, 'b4363d0e-f8ca-4558-9bcb-c262ec232889', null, null, '1');
INSERT INTO `tc_project` VALUES ('2', '2017-08-18 11:13:11', '2017-08-18 11:13:14', '', null, null, '', null, null, null, null, null, null, null, null, null, null, '测试工程2', null, null, null, '1b31c67c-6376-4532-9885-7e161737bb28', null, null, '1');

-- ----------------------------
-- Table structure for td_category
-- ----------------------------
DROP TABLE IF EXISTS `td_category`;
CREATE TABLE `td_category` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `init` bit(1) DEFAULT NULL,
  `lv` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `uk` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `projectId` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q9p7js7b4x9m6tu610irglul8` (`uk`),
  KEY `FK_6b70c86gwml8osamd1iatki3u` (`projectId`),
  CONSTRAINT `FK_6b70c86gwml8osamd1iatki3u` FOREIGN KEY (`projectId`) REFERENCES `tc_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of td_category
-- ----------------------------
INSERT INTO `td_category` VALUES ('20170818-111018-49772965', '2017-08-18 11:10:18', null, '', '\0', '1', '资料', '5ce79438-3e69-4843-a1c7-7713c973bde5', '1');

-- ----------------------------
-- Table structure for td_yunfile
-- ----------------------------
DROP TABLE IF EXISTS `td_yunfile`;
CREATE TABLE `td_yunfile` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `extension` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `init` bit(1) DEFAULT NULL,
  `isDir` bit(1) DEFAULT NULL,
  `isFile` bit(1) DEFAULT NULL,
  `md5` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `uk` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `categoryId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memberId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9pgn2s0scv26w7x8ak1nwdhv4` (`uk`),
  KEY `FK_oew3s8r7gunsjdyx0ynaaa0ol` (`categoryId`),
  KEY `FK_lwchtv8956lw1acnghg3a88o3` (`memberId`),
  KEY `FK_8wffipd7sqjwdguq5514fbd2l` (`parentId`),
  CONSTRAINT `FK_8wffipd7sqjwdguq5514fbd2l` FOREIGN KEY (`parentId`) REFERENCES `td_yunfile` (`id`),
  CONSTRAINT `FK_lwchtv8956lw1acnghg3a88o3` FOREIGN KEY (`memberId`) REFERENCES `tc_member` (`id`),
  CONSTRAINT `FK_oew3s8r7gunsjdyx0ynaaa0ol` FOREIGN KEY (`categoryId`) REFERENCES `td_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of td_yunfile
-- ----------------------------
INSERT INTO `td_yunfile` VALUES ('20170818-111041-04530596', '2017-08-18 11:10:41', null, '', 'png', null, '\0', '', '5146fe0d286a152707f9d9e12f5d25fb', '1494828880400.png', '11', 'resource/upload/yunFile/1/5ce79438-3e69-4843-a1c7-7713c973bde5/59a2281e-0476-44af-ae66-7b7511481d85.png', '287296', '81ce6dd9-bcbd-4b64-93ab-0c2a4e57351b', '20170818-111018-49772965', '1', null);

-- ----------------------------
-- Table structure for td_yunfile_history
-- ----------------------------
DROP TABLE IF EXISTS `td_yunfile_history`;
CREATE TABLE `td_yunfile_history` (
  `id` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `extension` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isDir` bit(1) DEFAULT NULL,
  `isFile` bit(1) DEFAULT NULL,
  `md5` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `uk` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `categoryId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memberId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fileId` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7w35xn6u7tuhqkhd5ad2bc8p8` (`uk`),
  KEY `FK_1mfyoibrmp6bwqv2dt7gff67j` (`categoryId`),
  KEY `FK_2ig139lc8v5t4fico2jem6wcg` (`memberId`),
  KEY `FK_ravc8enj5o7htu0yssetlaoge` (`fileId`),
  CONSTRAINT `FK_1mfyoibrmp6bwqv2dt7gff67j` FOREIGN KEY (`categoryId`) REFERENCES `td_category` (`id`),
  CONSTRAINT `FK_2ig139lc8v5t4fico2jem6wcg` FOREIGN KEY (`memberId`) REFERENCES `tc_member` (`id`),
  CONSTRAINT `FK_ravc8enj5o7htu0yssetlaoge` FOREIGN KEY (`fileId`) REFERENCES `td_yunfile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of td_yunfile_history
-- ----------------------------

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `airPinyin` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `fullName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pinyin` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `py` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `telephone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `theCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `virtual` bit(1) DEFAULT NULL,
  `visible` bit(1) DEFAULT NULL,
  `weatherPinyin` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ddrqslrv0bd1obbf4iuspprv0` (`theCode`),
  KEY `FK_n60op9kqnwpwht4ik1omkp599` (`parentId`),
  CONSTRAINT `FK_n60op9kqnwpwht4ik1omkp599` FOREIGN KEY (`parentId`) REFERENCES `t_area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of t_area
-- ----------------------------
