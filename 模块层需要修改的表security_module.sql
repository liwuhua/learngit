/*
Navicat MySQL Data Transfer

Source Server         : 本机mysql 127.0.0.1
Source Server Version : 50527
Source Host           : 127.0.0.1:3306
Source Database       : dbview

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2016-11-04 11:12:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for security_module
-- ----------------------------
DROP TABLE IF EXISTS `security_module`;
CREATE TABLE `security_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(32) NOT NULL,
  `priority` int(11) NOT NULL,
  `sn` varchar(32) NOT NULL,
  `url` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6510844BB3395F9` (`parent_id`),
  CONSTRAINT `security_module_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `security_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=425 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of security_module
-- ----------------------------
INSERT INTO `security_module` VALUES ('1', '所有模块的根节点，不能删除。', '根模块', '1', 'Root', '#', null);
INSERT INTO `security_module` VALUES ('2', '安全管理:包含权限管理、模块管理等。', '安全管理', '99', 'Security', '#', '1');
INSERT INTO `security_module` VALUES ('3', '', '用户管理', '99', 'User', '/management/security/user/list', '2');
INSERT INTO `security_module` VALUES ('4', '', '角色管理', '99', 'Role', '/management/security/role/list', '2');
INSERT INTO `security_module` VALUES ('5', '', '模块管理', '99', 'Module', '/management/security/module/tree_list', '2');
INSERT INTO `security_module` VALUES ('18', '', '组织管理', '99', 'Organization', '/management/security/organization/tree_list', '2');
INSERT INTO `security_module` VALUES ('24', '', '缓存管理', '99', 'CacheManage', '/management/security/cacheManage/index', '2');
INSERT INTO `security_module` VALUES ('59', '', '日志管理', '99', 'logEntity', '/management/security/logEntity/list', '2');
INSERT INTO `security_module` VALUES ('398', '', '访问量统计', '99', 'MwdSession', '#', '1');
INSERT INTO `security_module` VALUES ('399', '', '综合浏览量统计', '99', 'MwdPv', '#', '1');
INSERT INTO `security_module` VALUES ('400', '', '基础流量概览', '1', 'MwdSessionVisitsOverview', 'SessionVisits/overview', '398');
INSERT INTO `security_module` VALUES ('401', '', '基础流量概览', '99', 'MwdPvVisitsOverview', 'PvVisits/overview', '399');
INSERT INTO `security_module` VALUES ('402', '', '基础流量维度统计', '2', 'MwdSessionVisitsView', 'SessionVisits/view', '398');
INSERT INTO `security_module` VALUES ('403', '', '访问者维度统计', '4', 'MwdSessionUvView', 'SessionUv/view', '398');
INSERT INTO `security_module` VALUES ('404', '', '访问者概览', '3', 'MwdSessionUvOverview', 'SessionUv/overview', '398');
INSERT INTO `security_module` VALUES ('405', '', '手机归属地', '5', 'MwdSessionVisitsPhone', 'SessionVisitsPhone/view', '398');
INSERT INTO `security_module` VALUES ('408', '', '上网地域', '6', 'MwdSessionVisitsGeo', 'SessionVisitsGeo/view', '398');
INSERT INTO `security_module` VALUES ('411', '', '系统环境', '99', 'MwdSpvVisitsView', 'PvVisits/view', '399');
INSERT INTO `security_module` VALUES ('412', '', 'test', '99', 'mwd', '#', '1');
INSERT INTO `security_module` VALUES ('413', '', '手机归属地', '99', 'mwd1', 'SessionVisitsPhone/view', '412');
INSERT INTO `security_module` VALUES ('415', '', '属性字典管理', '99', 'DictProp', 'chart/dictProp/list', '2');
INSERT INTO `security_module` VALUES ('416', '', '上网地域', '99', 'mwd2', 'SessionVisitsGeo/view', '412');
INSERT INTO `security_module` VALUES ('417', '', '手机归属地-世界地图', '99', 'mwd3', 'SessionVisitsPhoneWorld/view', '412');
INSERT INTO `security_module` VALUES ('418', '', '上网地域-世界地图', '99', 'mwd4', 'SessionVisitsGeoWorld/view', '412');
INSERT INTO `security_module` VALUES ('420', '', '号段管理', '99', 'phoneGeo', '/chart/phoneGeo/list', '2');
INSERT INTO `security_module` VALUES ('421', '', '流量来源统计', '99', 'mwd5', 'SessionVisitsSource/view', '412');
INSERT INTO `security_module` VALUES ('422', '', '流量来源正则表达式', '99', 'regData', '/chart/regData/list', '2');
INSERT INTO `security_module` VALUES ('423', '', '上网地域管理', '99', 'ipLocation', '/chart/ipLocation/list', '2');
INSERT INTO `security_module` VALUES ('424', '', 'yojj111', '99', 'nn', '#', '398');
