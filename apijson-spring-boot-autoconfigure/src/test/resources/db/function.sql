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

 Date: 23/07/2025 11:43:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for function
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `debug` tinyint NOT NULL DEFAULT 0 COMMENT '是否为 DEBUG 调试数据，只允许在开发环境使用，测试和线上环境禁用：0-否，1-是。',
  `userId` bigint NOT NULL COMMENT '管理员用户Id',
  `language` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '语言：Java(java), JavaScript(js), Lua(lua), Python(py), Ruby(ruby), PHP(php) 等，NULL 默认为 Java，JDK 1.6-11 默认支持 JavaScript，JDK 12+ 需要额外依赖 Nashron/Rhiro 等 js 引擎库，其它的语言需要依赖对应的引擎库，并在 ScriptEngineManager 中注册',
  `name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '方法名',
  `returnType` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'Object' COMMENT '返回值类型。TODO RemoteFunction 校验 type 和 back',
  `arguments` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '参数列表，每个参数的类型都是 String。\n用 , 分割的字符串 比 [JSONArray] 更好，例如 array,item ，更直观，还方便拼接函数。',
  `demo` json NOT NULL COMMENT '可用的示例。\nTODO 改成 call，和返回值示例 back 对应。',
  `detail` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '详细描述',
  `version` tinyint NOT NULL DEFAULT 0 COMMENT '允许的最低版本号，只限于GET,HEAD外的操作方法。\nTODO 使用 requestIdList 替代 version,tag,methods',
  `tag` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '允许的标签.\nnull - 允许全部\nTODO 使用 requestIdList 替代 version,tag,methods',
  `methods` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '允许的操作方法。\nnull - 允许全部\nTODO 使用 requestIdList 替代 version,tag,methods',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `return` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '返回值示例',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '远程函数。强制在启动时校验所有demo是否能正常运行通过' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of function
-- ----------------------------
INSERT INTO `function` VALUES (3, 0, 0, NULL, 'countArray', 'int', 'array', '{\"array\": [1, 2, 3]}', '获取数组长度。没写调用键值对，会自动补全 \"result()\": \"countArray(array)\"', 0, NULL, NULL, '2018-10-13 16:23:23', NULL);
INSERT INTO `function` VALUES (4, 0, 0, NULL, 'countObject', 'int', 'object', '{\"object\": {\"key0\": 1, \"key1\": 2}}', '获取对象长度。', 0, NULL, NULL, '2018-10-13 16:23:23', NULL);
INSERT INTO `function` VALUES (5, 0, 0, NULL, 'isContain', 'boolean', 'array,value', '{\"array\": [1, 2, 3], \"value\": 2}', '判断是否数组包含值。', 0, NULL, NULL, '2018-10-13 16:23:23', NULL);
INSERT INTO `function` VALUES (6, 0, 0, NULL, 'isContainKey', 'boolean', 'object,key', '{\"key\": \"id\", \"object\": {\"id\": 1}}', '判断是否对象包含键。', 0, NULL, NULL, '2018-10-13 16:30:31', NULL);
INSERT INTO `function` VALUES (7, 0, 0, NULL, 'isContainValue', 'boolean', 'object,value', '{\"value\": 1, \"object\": {\"id\": 1}}', '判断是否对象包含值。', 0, NULL, NULL, '2018-10-13 16:30:31', NULL);
INSERT INTO `function` VALUES (8, 0, 0, NULL, 'getFromArray', 'Object', 'array,position', '{\"array\": [1, 2, 3], \"result()\": \"getFromArray(array,1)\"}', '根据下标获取数组里的值。position 传数字时直接作为值，而不是从所在对象 request 中取值', 0, NULL, NULL, '2018-10-13 16:30:31', NULL);
INSERT INTO `function` VALUES (9, 0, 0, NULL, 'getFromObject', 'Object', 'object,key', '{\"key\": \"id\", \"object\": {\"id\": 1}}', '根据键获取对象里的值。', 0, NULL, NULL, '2018-10-13 16:30:31', NULL);
INSERT INTO `function` VALUES (13, 0, 0, NULL, 'getWithDefault', 'Object', 'value,defaultValue', '{\"value\": null, \"defaultValue\": 1}', '如果 value 为 null，则返回 defaultValue', 0, NULL, NULL, '2019-08-20 23:26:36', NULL);
INSERT INTO `function` VALUES (14, 0, 0, NULL, 'removeKey', 'Object', 'key', '{\"key\": \"s\", \"key2\": 2}', '从对象里移除 key', 0, NULL, NULL, '2019-08-20 23:26:36', NULL);
INSERT INTO `function` VALUES (15, 0, 0, NULL, 'getFunctionDemo', 'JSONObject', NULL, '{}', '获取远程函数的 Demo', 0, NULL, NULL, '2019-08-20 23:26:36', NULL);
INSERT INTO `function` VALUES (16, 0, 0, NULL, 'getFunctionDetail', 'String', NULL, '{}', '获取远程函数的详情', 0, NULL, NULL, '2019-08-20 23:26:36', NULL);

SET FOREIGN_KEY_CHECKS = 1;
