/*
 Navicat Premium Dump SQL

 Source Server         : localhost-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80405 (8.4.5)
 Source Host           : localhost:3306
 Source Schema         : apijson

 Target Server Type    : MySQL
 Target Server Version : 80405 (8.4.5)
 File Encoding         : 65001

 Date: 23/07/2025 11:44:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for script
-- ----------------------------
DROP TABLE IF EXISTS `script`;
CREATE TABLE `script`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL DEFAULT 0 COMMENT '用户 id',
  `testAccountId` bigint NOT NULL DEFAULT 0 COMMENT '测试账号 id',
  `chainGroupId` bigint NOT NULL DEFAULT 0,
  `chainId` bigint NOT NULL DEFAULT 0,
  `documentId` bigint NOT NULL DEFAULT 0 COMMENT '测试用例 id',
  `simple` tinyint NOT NULL DEFAULT 0 COMMENT '是否为可直接执行的简单代码段：0-否 1-是',
  `ahead` tinyint NOT NULL DEFAULT 0 COMMENT '是否为前置脚本',
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '函数名',
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '名称',
  `script` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `detail` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_UNIQUE`(`id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1680660620760 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '脚本，前置预处理脚本、后置断言和恢复脚本等' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of script
-- ----------------------------
INSERT INTO `script` VALUES (1, 0, 0, 0, 0, 0, 0, 0, NULL, 'getType', 'function getType(curObj, key) {\n    var val = curObj == null ? null : curObj[key];\n    return val instanceof Array ? \"array\" : typeof val;\n}', '2022-11-17 00:01:23', NULL);
INSERT INTO `script` VALUES (2, 0, 0, 0, 0, 0, 0, 0, NULL, 'isContain', 'function isContain(curObj, arrKey, valKey) {\n    var arr = curObj == null ? null : curObj[arrKey];\n    var val = curObj == null ? null : curObj[valKey];\n    return arr != null && arr.indexOf(val) >=0;\n}', '2022-11-17 00:02:48', NULL);
INSERT INTO `script` VALUES (3, 0, 0, 0, 0, 0, 1, 0, NULL, 'init', 'var i = 1;\n\"init done \"  + i;', '2022-11-17 00:41:35', NULL);
INSERT INTO `script` VALUES (4, 0, 0, 0, 0, 0, 0, 0, NULL, 'length', 'function length(curObj, key) {\n    var val = curObj == null ? null : curObj[key];\n    return val == null ? 0 : val.length;\n}', '2022-11-17 01:18:43', NULL);
INSERT INTO `script` VALUES (1670877914051, 82001, 0, 0, 0, 0, 1, 1, '执行脚本 2022-12-13 04:44', '', 'function assert(assertion, msg) {\n     if (assertion === true) {\n         return\n     }\n     if (msg == null) {\n         msg = \'assert failed! assertion = \' + assertion\n     }\n\n     if (isTest) {\n         console.log(msg)\n         alert(msg)\n     } else {\n         throw new Error(msg)\n     } \n}  \n\nif (isTest) {\n     assert(true)\n     assert(false)\n     assert(true, \'ok\')\n     assert(false, \'data.User shoule not be null!\') \n}\n\nfunction getCurAccount() {\n  return App.getCurrentAccount()\n}', '2022-12-13 04:45:14', NULL);
INSERT INTO `script` VALUES (1670878495619, 82001, 82002, 0, 0, 0, 1, 0, '执行脚本 2022-12-13 04:54', '', 'function getCurAccount() {\n  return App.getCurrentAccount()\n}', '2022-12-13 04:54:55', NULL);
INSERT INTO `script` VALUES (1670878529042, 82001, 82001, 0, 0, 0, 1, 1, '执行脚本 2022-12-13 04:55', '', 'function getCurAccount() {\n  return App.getCurrentAccount()\n}', '2022-12-13 04:55:29', NULL);
INSERT INTO `script` VALUES (1670878622401, 82001, 82003, 0, 0, 0, 1, 0, '执行脚本 2022-12-13 04:57', '', 'if (isPre) {\n  header[\'my-header\'] = \'test\'\n}', '2022-12-13 04:57:02', NULL);
INSERT INTO `script` VALUES (1670885503909, 82001, 0, 0, 0, 1657045372046, 1, 1, '执行脚本 2022-12-13 06:51', '', 'if (isPre) {\n  req.User.id = 82005\n}', '2022-12-13 06:51:43', NULL);
INSERT INTO `script` VALUES (1670887211207, 82001, 0, 0, 0, 1657045372046, 1, 0, '执行脚本 2022-12-13 07:20', '', '', '2022-12-13 07:20:11', NULL);
INSERT INTO `script` VALUES (1676368454070, 82001, 0, 0, 0, 1546414192830, 1, 0, NULL, 'casePost1546414192830', '', '2023-02-14 17:54:14', NULL);
INSERT INTO `script` VALUES (1679282174670, 82001, 0, 0, 0, 0, 1, 1, NULL, '到店系统查询', '', '2023-03-20 11:16:14', NULL);
INSERT INTO `script` VALUES (1680660620759, 82001, 0, 0, 0, 1511796155276, 1, 1, NULL, 'casePre1511796155276', 'if (isPre) {\n    console.log(\'test pre script 4 /post/verify\')    \n}\n', '2024-05-03 11:55:14', NULL);

SET FOREIGN_KEY_CHECKS = 1;
