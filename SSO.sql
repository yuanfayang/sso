/*
 Navicat MySQL Data Transfer

 Source Server         : root@111.9.116.181
 Source Server Version : 50629
 Source Host           : 111.9.116.181
 Source Database       : SSO

 Target Server Version : 50629
 File Encoding         : utf-8

 Date: 03/25/2016 14:51:27 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sso_app`
-- ----------------------------
DROP TABLE IF EXISTS `sso_app`;
CREATE TABLE `sso_app` (
  `appId` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '应用服务的UUID',
  `appName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用服务的名称',
  `host` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用服务的主机地址',
  `logoutUrl` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用服务退出的地址',
  `isSSOServer` tinyint(1) DEFAULT '0' COMMENT '是否是SSO服务本身1：是，0：不是',
  PRIMARY KEY (`appId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `sso_app`
-- ----------------------------
BEGIN;
INSERT INTO `sso_app` VALUES ('3b0d556d3add420596a9afdd58f1bc10', 'localhost app', 'http://127.0.0.1:8080/app', 'http://127.0.0.1:8080/app/logout', '0'), ('4c0ed157b84a42fd9ccffa19549398f5', '本地测试应用1', 'http://127.0.0.1:8081/app1', 'http://127.0.0.1:8081/app1/logout', '0'), ('5065b64a133743e995b576e12e6634d3', '本地测试应用2', 'http://127.0.0.1:8082/app2', 'http://127.0.0.1:8082/app2/logout', '0'), ('62cbc1b3a4dd4411bff4759bcb095a61', 'SSO Server', 'http://111.9.116.181:20160/sso', 'http://111.9.116.181:20160/sso/api/logout', '1'), ('c16136e70dde43f587b603cc52a4a3f2', 'http://127.0.0.1/app/login.html（本地测试应用）', 'http://127.0.0.1/app/', 'http://127.0.0.1/app/logout.html', '0'), ('de3489b33fd54bddad4f0c5641b1b760', '大型仪器平台', 'http://www.myyqzy.com', 'http://www.myyqzy.com/webLogout.Asp', '0'), ('f371883196e74b8aa03fcbf7013c5d60', '专利系统', 'http://search.wfyizhan.com', 'http://search.wfyizhan.com:9011/search/ssorouter/logout', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sso_key`
-- ----------------------------
DROP TABLE IF EXISTS `sso_key`;
CREATE TABLE `sso_key` (
  `keyId` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '应用服务私钥的编号',
  `appId` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '应用服务的编号',
  `value` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用服务的私钥值',
  `keyPath` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '应用服务私钥的存储路径',
  PRIMARY KEY (`keyId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
--  Records of `sso_key`
-- ----------------------------
BEGIN;
INSERT INTO `sso_key` VALUES ('1', '62cbc1b3a4dd4411bff4759bcb095a61', 'mn2dpxPuZxDNPwrm', null), ('2', '3b0d556d3add420596a9afdd58f1bc10', 'SFEvlId023gd766I', null), ('3', 'f371883196e74b8aa03fcbf7013c5d60', 'JlLYYo9fIp9ECUSn', null), ('4', 'de3489b33fd54bddad4f0c5641b1b760', 'luCZuYpgY1QSsxGC', null), ('5', 'c16136e70dde43f587b603cc52a4a3f2', '0ypSaM4ezJ9Z5hTS', null), ('6', '4c0ed157b84a42fd9ccffa19549398f5', 'DUfHYzkrwqs4Lurt', null), ('7', '5065b64a133743e995b576e12e6634d3', 'bExwSam1kMPVbCiZ', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
