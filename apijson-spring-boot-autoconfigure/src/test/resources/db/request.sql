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

 Date: 23/07/2025 11:41:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `debug` tinyint NOT NULL DEFAULT 0 COMMENT '是否为 DEBUG 调试数据，只允许在开发环境使用，测试和线上环境禁用：0-否，1-是。',
  `version` tinyint NOT NULL DEFAULT 1 COMMENT 'GET,HEAD可用任意结构访问任意开放内容，不需要这个字段。\n其它的操作因为写入了结构和内容，所以都需要，按照不同的version选择对应的structure。\n\n自动化版本管理：\nRequest JSON最外层可以传  “version”:Integer 。\n1.未传或 <= 0，用最新版。 “@order”:”version-“\n2.已传且 > 0，用version以上的可用版本的最低版本。 “@order”:”version+”, “version{}”:”>={version}”',
  `method` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT 'GETS' COMMENT '只限于GET,HEAD外的操作方法。',
  `tag` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '标签',
  `structure` json NOT NULL COMMENT '结构。\nTODO 里面的 PUT 改为 UPDATE，避免和请求 PUT 搞混。',
  `detail` varchar(10000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '详细说明',
  `date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1658229984300 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '请求参数校验配置(必须)。\n最好编辑完后删除主键，这样就是只读状态，不能随意更改。需要更改就重新加上主键。\n\n每次启动服务器时加载整个表到内存。\n这个表不可省略，model内注解的权限只是客户端能用的，其它可以保证即便服务端代码错误时也不会误删数据。' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request
-- ----------------------------
INSERT INTO `request` VALUES (1, 0, 1, 'POST', 'register', '{\"User\": {\"MUST\": \"name\", \"REFUSE\": \"id\", \"UPDATE\": {\"id@\": \"Privacy/id\"}}, \"Privacy\": {\"MUST\": \"_password,phone\", \"REFUSE\": \"id\", \"UNIQUE\": \"phone\", \"VERIFY\": {\"phone~\": \"PHONE\"}}}', 'UNIQUE校验phone是否已存在。VERIFY校验phone是否符合手机号的格式', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (4, 0, 1, 'PUT', 'User', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"phone\"}', '必须传id，不允许传phone。INSERT当没传@role时用OWNER补全', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (8, 0, 1, 'PUT', 'User-phone', '{\"User\": {\"MUST\": \"id,phone,_password\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\", \"UPDATE\": {\"@combine\": \"_password\"}}}', '! 表示其它所有，这里指necessary所有未包含的字段', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (14, 0, 1, 'POST', 'Verify', '{\"MUST\": \"phone,verify\", \"REFUSE\": \"!\"}', '必须传phone,verify，其它都不允许传', '2017-02-18 22:20:43');
INSERT INTO `request` VALUES (15, 0, 1, 'GETS', 'Verify', '{\"MUST\": \"phone\"}', '必须传phone', '2017-02-18 22:20:43');
INSERT INTO `request` VALUES (16, 0, 1, 'HEADS', 'Verify', '{}', '允许任意内容', '2017-02-18 22:20:43');
INSERT INTO `request` VALUES (21, 0, 1, 'HEADS', 'Login', '{\"MUST\": \"userId,type\", \"REFUSE\": \"!\"}', NULL, '2017-02-18 22:20:43');
INSERT INTO `request` VALUES (22, 0, 1, 'GETS', 'User', '{}', '允许传任何内容，除了表对象', '2017-02-18 22:20:43');
INSERT INTO `request` VALUES (23, 0, 1, 'PUT', 'Privacy', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', 'INSERT当没传@role时用OWNER补全', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (29, 0, 1, 'GETS', 'login', '{\"Privacy\": {\"MUST\": \"phone,_password\", \"REFUSE\": \"id\"}}', NULL, '2017-10-15 18:04:52');
INSERT INTO `request` VALUES (30, 0, 1, 'PUT', 'balance+', '{\"Privacy\": {\"MUST\": \"id,balance+\", \"REFUSE\": \"!\", \"VERIFY\": {\"balance+&{}\": \">=1,<=100000\"}}}', '验证balance+对应的值是否满足>=1且<=100000', '2017-10-21 16:48:34');
INSERT INTO `request` VALUES (31, 0, 1, 'PUT', 'balance-', '{\"Privacy\": {\"MUST\": \"id,balance-,_password\", \"REFUSE\": \"!\", \"UPDATE\": {\"@combine\": \"_password\"}, \"VERIFY\": {\"balance-&{}\": \">=1,<=10000\"}}}', 'UPDATE强制把_password作为WHERE条件', '2017-10-21 16:48:34');
INSERT INTO `request` VALUES (32, 0, 2, 'GETS', 'Privacy', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"_password,_payPassword\"}', NULL, '2017-06-13 00:05:51');
INSERT INTO `request` VALUES (33, 0, 2, 'GETS', 'Privacy-CIRCLE', '{\"Privacy\": {\"MUST\": \"id\", \"REFUSE\": \"!\", \"UPDATE\": {\"@role\": \"CIRCLE\", \"@column\": \"phone\"}}}', NULL, '2017-06-13 00:05:51');
INSERT INTO `request` VALUES (35, 0, 2, 'POST', 'Document', '{\"Document\": {\"MUST\": \"name,url,request\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}, \"TestRecord\": {\"MUST\": \"response\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id,documentId\", \"UPDATE\": {\"documentId@\": \"Document/id\"}}}', NULL, '2017-11-26 16:34:41');
INSERT INTO `request` VALUES (36, 1, 2, 'PUT', 'Document', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"userId\"}', NULL, '2017-11-26 16:35:15');
INSERT INTO `request` VALUES (37, 1, 2, 'DELETE', 'Document', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\", \"UPDATE\": {\"Random\": {\"@try\": true, \"@role\": \"OWNER\", \"documentId@\": \"Method/id\"}, \"TestRecord\": {\"@try\": true, \"@role\": \"OWNER\", \"documentId@\": \"Document/id\"}}}', NULL, '2017-11-26 08:36:20');
INSERT INTO `request` VALUES (38, 0, 2, 'POST', 'TestRecord', '{\"MUST\": \"documentId,response\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2018-06-17 07:44:36');
INSERT INTO `request` VALUES (39, 1, 2, 'POST', 'Method', '{\"Method\": {\"MUST\": \"method,package\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}, \"TestRecord\": {\"MUST\": \"response\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id,documentId\", \"UPDATE\": {\"documentId@\": \"Method/id\"}}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (40, 1, 2, 'PUT', 'Method', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"userId\"}', NULL, '2017-11-26 08:35:15');
INSERT INTO `request` VALUES (41, 1, 2, 'DELETE', 'Method', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\"}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (42, 0, 2, 'POST', 'Random', '{\"INSERT\": {\"@role\": \"OWNER\"}, \"Random\": {\"MUST\": \"documentId,name,config\"}, \"TestRecord\": {\"UPDATE\": {\"randomId@\": \"/Random/id\", \"documentId@\": \"/Random/documentId\"}}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (43, 1, 2, 'PUT', 'Random', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"userId\"}', NULL, '2017-11-26 08:35:15');
INSERT INTO `request` VALUES (44, 1, 2, 'DELETE', 'Random', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"UPDATE\": {\"@try\": true, \"TestRecord\": {\"@role\": \"OWNER\", \"randomId@\": \"/id\"}}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (45, 0, 2, 'POST', 'Comment:[]', '{\"TYPE\": {\"Comment[]\": \"OBJECT[]\"}, \"INSERT\": {\"@role\": \"OWNER\"}, \"Comment[]\": []}', NULL, '2020-03-01 13:40:04');
INSERT INTO `request` VALUES (46, 0, 2, 'POST', 'Moment:[]', '{\"INSERT\": {\"@role\": \"OWNER\"}, \"Moment[]\": []}', NULL, '2020-03-01 13:41:42');
INSERT INTO `request` VALUES (47, 0, 2, 'PUT', 'Comment:[]', '{\"INSERT\": {\"@role\": \"OWNER\"}, \"Comment[]\": []}', NULL, '2020-03-01 13:40:04');
INSERT INTO `request` VALUES (48, 1, 2, 'DELETE', 'TestRecord', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (49, 0, 2, 'POST', 'Input', '{\"MUST\": \"deviceId,x,y\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (50, 0, 2, 'POST', 'Device', '{\"MUST\": \"brand,model\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (51, 0, 2, 'POST', 'System', '{\"MUST\": \"type,versionCode,versionName\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (52, 0, 2, 'POST', 'Flow', '{\"MUST\": \"deviceId,systemId,name\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (53, 0, 4, 'GETS', 'Privacy', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\"}', NULL, '2017-06-13 00:05:51');
INSERT INTO `request` VALUES (54, 0, 2, 'POST', 'Output', '{\"MUST\": \"inputId\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"id\"}', NULL, '2018-06-17 07:44:36');
INSERT INTO `request` VALUES (55, 0, 2, 'DELETE', 'Output', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (56, 0, 3, 'DELETE', 'Method', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\"}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (57, 0, 4, 'GETS', 'User[]', '{\"User\": {\"INSERT\": {\"@role\": \"CIRCLE\"}}, \"REFUSE\": \"query\"}', NULL, '2021-10-22 00:29:32');
INSERT INTO `request` VALUES (58, 0, 1, 'PUT', 'Moment-praiseUserIdList+', '{\"Moment\": {\"MUST\": \"id\", \"REFUSE\": \"!\", \"UPDATE\": {\"@role\": \"CIRCLE\", \"newListWithCurUserId-()\": \"newListWithCurUserId(praiseUserIdList+)\"}}}', '单独针对 Moment 点赞设置校验规则（允许圈子成员操作自己的数据）', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (59, 0, 1, 'PUT', 'Moment-praiseUserIdList-', '{\"Moment\": {\"MUST\": \"id\", \"REFUSE\": \"!\", \"UPDATE\": {\"@role\": \"CIRCLE\", \"praiseUserIdList--()\": \"newListWithCurUserId()\"}}}', '单独针对 Moment 取消点赞设置校验规则（允许圈子成员操作自己的数据）', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (61, 1, 1, 'POST', 'Request', '{\"MUST\": \"method,tag,structure\", \"INSERT\": {\"@role\": \"LOGIN\"}, \"REFUSE\": \"!detail,!\"}', NULL, '2022-05-03 03:07:37');
INSERT INTO `request` VALUES (1651614346391, 0, 1, 'GET', 'momentList', '{\"MUST\": \"Moment[].page\", \"TYPE\": {\"format\": \"BOOLEAN\", \"Moment[].page\": \"NUMBER\", \"Moment[].count\": \"NUMBER\"}, \"REFUSE\": \"!Moment[].count,!format,!\"}', '查询动态列表类 RESTful 简单接口', '2022-05-04 05:46:04');
INSERT INTO `request` VALUES (1657562189773, 0, 1, 'GET', 'moments', '{\"MUST\": \"Moment[].page\", \"TYPE\": {\"format\": \"BOOLEAN\", \"Moment[].page\": \"NUMBER\", \"Moment[].count\": \"NUMBER\"}, \"REFUSE\": \"!Moment[].count,!format,!\"}', '查动态列表类 RESTful 简单接口', '2022-07-12 01:56:32');
INSERT INTO `request` VALUES (1657793230364, 0, 1, 'GET', 'User[]', '{\"MUST\": \"\", \"TYPE\": {}, \"REFUSE\": \"!\"}', '随机配置 2022-07-14 18:07', '2022-07-14 18:07:10');
INSERT INTO `request` VALUES (1658229984265, 0, 1, 'GET', 'user', '{\"MUST\": \"\", \"TYPE\": {}, \"REFUSE\": \"!\"}', '随机配置 2022-07-19 19:26', '2022-07-19 19:26:24');
INSERT INTO `request` VALUES (1658229984266, 0, 5, 'POST', 'Activity', '{}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984267, 0, 5, 'POST', 'Fragment', '{}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984268, 0, 5, 'POST', 'View', '{}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984269, 0, 5, 'POST', 'Activity:[]', '{\"TYPE\": {\"Activity[]\": \"OBJECT[]\"}, \"INSERT\": {\"@role\": \"OWNER\"}, \"Activity[]\": []}', NULL, '2020-03-01 13:40:04');
INSERT INTO `request` VALUES (1658229984270, 0, 5, 'POST', 'Fragment:[]', '{\"TYPE\": {\"Fragment[]\": \"OBJECT[]\"}, \"INSERT\": {\"@role\": \"OWNER\"}, \"Fragment[]\": []}', NULL, '2020-03-01 13:40:04');
INSERT INTO `request` VALUES (1658229984271, 0, 5, 'POST', 'View:[]', '{\"TYPE\": {\"View[]\": \"OBJECT[]\"}, \"INSERT\": {\"@role\": \"OWNER\"}, \"View[]\": []}', NULL, '2020-03-01 13:40:04');
INSERT INTO `request` VALUES (1658229984274, 0, 5, 'PUT', 'Activity', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:35:15');
INSERT INTO `request` VALUES (1658229984275, 0, 5, 'PUT', 'Fragment', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:35:15');
INSERT INTO `request` VALUES (1658229984276, 0, 5, 'PUT', 'View', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:35:15');
INSERT INTO `request` VALUES (1658229984277, 0, 5, 'DELETE', 'Activity', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (1658229984278, 0, 5, 'DELETE', 'Fragment', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (1658229984279, 0, 5, 'DELETE', 'View', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (1658229984280, 0, 5, 'DELETE', 'View[]', '{\"View\": {\"MUST\": \"id{}\", \"INSERT\": {\"@role\": \"OWNER\"}}}', 'DISALLOW没必要用于DELETE', '2017-02-01 19:19:51');
INSERT INTO `request` VALUES (1658229984282, 0, 5, 'POST', 'Data', '{}', NULL, '2022-12-11 04:58:27');
INSERT INTO `request` VALUES (1658229984283, 0, 5, 'PUT', 'Data', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2022-12-11 04:58:27');
INSERT INTO `request` VALUES (1658229984284, 0, 5, 'DELETE', 'Data', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2022-12-11 04:58:27');
INSERT INTO `request` VALUES (1658229984285, 0, 1, 'POST', 'Script', '{\"MUST\": \"name,script\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984286, 0, 1, 'PUT', 'Script', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984287, 0, 1, 'DELETE', 'Script', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984289, 1, 5, 'PUT', 'Document-group', '{\"Document\": {\"MUST\": \"url{},group{},group\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\", \"UPDATE\": {\"@key\": \"url:substr(url,1,length(url)-length(substring_index(url,\'/\',-1))-1)\", \"@raw\": \"@key\"}, \"IS_ID_CONDITION_MUST\": false}}', NULL, '2017-11-26 16:35:15');
INSERT INTO `request` VALUES (1658229984290, 1, 5, 'PUT', 'Method-group', '{\"Method\": {\"MUST\": \"package{},group{},group\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\", \"IS_ID_CONDITION_MUST\": false}}', NULL, '2017-11-26 16:35:15');
INSERT INTO `request` VALUES (1658229984291, 0, 2, 'DELETE', 'Flow', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"UPDATE\": {\"Input\": {\"@try\": true, \"@role\": \"OWNER\", \"flowId@\": \"/id\"}, \"Output\": {\"@try\": true, \"@role\": \"OWNER\", \"flowId@\": \"/id\"}}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (1658229984292, 0, 2, 'DELETE', 'Input', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}, \"UPDATE\": {\"Output\": {\"@try\": true, \"@role\": \"OWNER\", \"inputId@\": \"/id\"}}}', NULL, '2017-11-26 00:36:20');
INSERT INTO `request` VALUES (1658229984293, 0, 1, 'POST', 'Chain', '{\"MUST\": \"groupId,groupName,documentId\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984294, 0, 1, 'PUT', 'Chain', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984295, 0, 1, 'DELETE', 'Chain', '{\"MUST\": \"id\", \"INSERT\": {\"@role\": \"OWNER\"}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984296, 0, 1, 'POST', 'Chain-group', '{\"Chain\": {\"MUST\": \"groupName\", \"INSERT\": {\"@role\": \"OWNER\"}}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984297, 0, 1, 'PUT', 'Chain-group', '{\"Chain\": {\"MUST\": \"groupId,groupName\", \"INSERT\": {\"@role\": \"OWNER\"}, \"IS_ID_CONDITION_MUST\": false}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984298, 0, 1, 'DELETE', 'Chain-group', '{\"Chain\": {\"MUST\": \"groupId\", \"INSERT\": {\"@role\": \"OWNER\"}, \"IS_ID_CONDITION_MUST\": false}}', NULL, '2017-11-26 08:34:41');
INSERT INTO `request` VALUES (1658229984299, 1, 5, 'PUT', 'Document-project[]', '{\"Document\": {\"MUST\": \"id{},project{},project\", \"INSERT\": {\"@role\": \"OWNER\"}, \"REFUSE\": \"!\"}}', NULL, '2017-11-26 16:35:15');

SET FOREIGN_KEY_CHECKS = 1;
