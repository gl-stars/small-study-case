/*
 Navicat MySQL Data Transfer

 Source Server         : www.it307.top
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : www.it307.top:3306
 Source Schema         : workflow

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 29/08/2020 19:12:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for business_tables
-- ----------------------------
DROP TABLE IF EXISTS `business_tables`;
CREATE TABLE `business_tables`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '表名称',
  `additional_notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核的业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of business_tables
-- ----------------------------
INSERT INTO `business_tables` VALUES (1298942891664154626, 'leave_info', NULL, NULL);
INSERT INTO `business_tables` VALUES (1298943671972442113, 'go_out', NULL, NULL);

-- ----------------------------
-- Table structure for go_out
-- ----------------------------
DROP TABLE IF EXISTS `go_out`;
CREATE TABLE `go_out`  (
  `id` int(0) NOT NULL,
  `data_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '出差事情',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '出差表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of go_out
-- ----------------------------
INSERT INTO `go_out` VALUES (1, '今天我要出差');
INSERT INTO `go_out` VALUES (2, '去北京签合同');
INSERT INTO `go_out` VALUES (3, 'go to 新加坡');

-- ----------------------------
-- Table structure for leave_info
-- ----------------------------
DROP TABLE IF EXISTS `leave_info`;
CREATE TABLE `leave_info`  (
  `id` int(0) NOT NULL COMMENT '编号',
  `content_info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请假内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '请假表（测试表，与审批无关）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave_info
-- ----------------------------
INSERT INTO `leave_info` VALUES (1, '老板，今天我请假，肚子饿了');
INSERT INTO `leave_info` VALUES (2, '老总，我想去逛街，请假');
INSERT INTO `leave_info` VALUES (3, '老大，我要去商场，请假呗。');

-- ----------------------------
-- Table structure for review_personnel
-- ----------------------------
DROP TABLE IF EXISTS `review_personnel`;
CREATE TABLE `review_personnel`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `review_role_id` bigint(0) NULL DEFAULT NULL COMMENT '审核角色编号',
  `review_user_nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核人员昵称',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `review_role`(`user_id`, `review_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核流程人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_personnel
-- ----------------------------
INSERT INTO `review_personnel` VALUES (1298934619586531333, 12, 1298934619586531329, '银色的世界');
INSERT INTO `review_personnel` VALUES (1298934619586531334, 23423, 1298934619586531330, '小清新');
INSERT INTO `review_personnel` VALUES (1298934619586531335, 4353, 1298934619586531331, '小坏蛋一个');
INSERT INTO `review_personnel` VALUES (1298934619586531336, 2314234, 1298934619586531332, '脑洞大开');

-- ----------------------------
-- Table structure for review_record
-- ----------------------------
DROP TABLE IF EXISTS `review_record`;
CREATE TABLE `review_record`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `business_id` bigint(0) NULL DEFAULT NULL COMMENT '业务ID',
  `business_tables_id` bigint(0) NULL DEFAULT NULL COMMENT '审核的业务表',
  `business_role` bigint(0) NULL DEFAULT NULL COMMENT '审核角色',
  `handle_order` int(0) NULL DEFAULT NULL COMMENT '处理顺序',
  `handler` bigint(0) NULL DEFAULT NULL COMMENT '审核人(默认为：0，-1：该当前角色审核)',
  `pass_or_not` int(0) NULL DEFAULT NULL COMMENT '是否审核通过（1：通过，0：不通过）',
  `return_level` int(0) NULL DEFAULT NULL COMMENT '返回第几级重新审核',
  `again_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重新审核类型，oneByOne、ascription',
  `additional_notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `effective_is` int(0) NULL DEFAULT 1 COMMENT '是否有效（0：取消，1：有效，2：已完成）',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '创建人',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `processing_time` datetime(0) NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `record_business`(`handle_order`, `handler`, `pass_or_not`, `return_level`, `again_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_record
-- ----------------------------
INSERT INTO `review_record` VALUES (1298942892477849601, 1298939157571805185, 1298942891664154626, 1298934619586531329, 1, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-07-01 15:01:36', '2020-08-27 11:18:13');
INSERT INTO `review_record` VALUES (1298943341968826370, 1298942875625136142, 1298942891664154626, 1298934619586531329, 1, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:20:01');
INSERT INTO `review_record` VALUES (1298943672773554177, 1298938072987701269, 1298943671972442113, 1298934619586531330, 1, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:21:19');
INSERT INTO `review_record` VALUES (1298943697280872450, 1298938072987701268, 1298943671972442113, 1298934619586531331, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-06-02 15:01:36', '2020-08-27 11:21:25');
INSERT INTO `review_record` VALUES (1298943742415777793, 1298938072987701267, 1298943671972442113, 1298934619586531332, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-05-01 15:01:36', '2020-08-27 11:21:36');
INSERT INTO `review_record` VALUES (1298943872258846722, 1298938072987701266, 1298943671972442113, 1298934619586531331, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-06-19 15:01:36', '2020-08-27 11:22:07');
INSERT INTO `review_record` VALUES (1298943907373559809, 1298938072987701252, 1298943671972442113, 1298934619586531332, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:22:15');
INSERT INTO `review_record` VALUES (1298944002903027713, 1298938072987701253, 1298942891664154626, 1298934619586531329, 2, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:22:38');
INSERT INTO `review_record` VALUES (1298944024440778753, 1298938072987701255, 1298942891664154626, 1298934619586531330, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:22:43');
INSERT INTO `review_record` VALUES (1298944049573048322, 1298938072987701254, 1298942891664154626, 1298934619586531331, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:22:49');
INSERT INTO `review_record` VALUES (1298944108112949249, 1298938072987701256, 1298942891664154626, 1298934619586531332, 0, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:23:03');
INSERT INTO `review_record` VALUES (1298944131525554178, 1298938072987701258, 1298942891664154626, 1298934619586531329, 2, NULL, NULL, NULL, 'oneByOne', NULL, 1, NULL, '2020-08-28 15:01:36', '2020-08-27 11:23:09');

-- ----------------------------
-- Table structure for review_record_failed
-- ----------------------------
DROP TABLE IF EXISTS `review_record_failed`;
CREATE TABLE `review_record_failed`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `business_id` bigint(0) NULL DEFAULT NULL COMMENT '业务id',
  `business_tables_id` bigint(0) NULL DEFAULT NULL COMMENT '审核的业务表',
  `handler` bigint(0) NULL DEFAULT NULL COMMENT '审核人',
  `again_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '重新审核类型，onebyone、ascription',
  `current_level` int(0) NULL DEFAULT NULL COMMENT '当前级别',
  `return_level` int(0) NULL DEFAULT NULL COMMENT '返回第几级重新审核',
  `additional_notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '说明',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `review_record_failed`(`business_id`, `business_tables_id`, `handler`, `current_level`, `return_level`, `insert_time`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核失败的记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for review_role
-- ----------------------------
DROP TABLE IF EXISTS `review_role`;
CREATE TABLE `review_role`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '审核角色名称',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父id',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_role
-- ----------------------------
INSERT INTO `review_role` VALUES (1298934619586531329, '人事部', -1, '2020-08-27 18:46:08', NULL);
INSERT INTO `review_role` VALUES (1298934619586531330, '项目部', NULL, '2020-08-27 18:46:28', NULL);
INSERT INTO `review_role` VALUES (1298934619586531331, '市场部', NULL, '2020-08-27 18:46:47', NULL);
INSERT INTO `review_role` VALUES (1298934619586531332, '企划部', 1298934619586531331, '2020-08-27 18:47:09', NULL);

-- ----------------------------
-- Table structure for review_type
-- ----------------------------
DROP TABLE IF EXISTS `review_type`;
CREATE TABLE `review_type`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `flow_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流程名称',
  `founder` int(0) NULL DEFAULT NULL COMMENT '创建人',
  `analysis` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `insert_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `tenant_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_type
-- ----------------------------
INSERT INTO `review_type` VALUES (1298934619586531337, '请假申请审核', 123, NULL, '2020-08-27 18:52:59', NULL);
INSERT INTO `review_type` VALUES (1298934619586531338, '放假申请审核', 32423, NULL, '2020-08-27 18:53:16', NULL);
INSERT INTO `review_type` VALUES (1298934619586531339, '出差策划案审核', 234, NULL, '2020-08-27 18:53:35', NULL);

-- ----------------------------
-- Table structure for review_type_role
-- ----------------------------
DROP TABLE IF EXISTS `review_type_role`;
CREATE TABLE `review_type_role`  (
  `id` bigint(0) NOT NULL COMMENT '编号',
  `handle_order` int(0) NULL DEFAULT NULL COMMENT '审核顺序',
  `review_type_id` bigint(0) NULL DEFAULT NULL COMMENT '审核类型编号',
  `review_role_id` bigint(0) NULL DEFAULT NULL COMMENT '审核角色编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `review_type`(`review_type_id`, `review_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '审核类型与审核角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of review_type_role
-- ----------------------------
INSERT INTO `review_type_role` VALUES (1298934619586531340, 1, 1298934619586531337, 1298934619586531329);
INSERT INTO `review_type_role` VALUES (1298934619586531341, 2, 1298934619586531337, 1298934619586531330);
INSERT INTO `review_type_role` VALUES (1298934619586531342, 3, 1298934619586531337, 1298934619586531332);
INSERT INTO `review_type_role` VALUES (1298934619586531343, 4, 1298934619586531337, 1298934619586531331);
INSERT INTO `review_type_role` VALUES (1298934619586531344, 1, 1298934619586531338, 1298934619586531329);
INSERT INTO `review_type_role` VALUES (1298934619586531345, 2, 1298934619586531338, 1298934619586531330);
INSERT INTO `review_type_role` VALUES (1298934619586531346, 1, 1298934619586531339, 1298934619586531329);
INSERT INTO `review_type_role` VALUES (1298934619586531347, 2, 1298934619586531339, 1298934619586531330);

SET FOREIGN_KEY_CHECKS = 1;
