/*
 Navicat MySQL Data Transfer

 Source Server         : mydb
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : hegui

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 14/02/2023 11:10:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dk_compliance
-- ----------------------------
DROP TABLE IF EXISTS `dk_compliance`;
CREATE TABLE `dk_compliance`  (
  `compliance_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `compliance_basis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `compliance_duty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `compliance_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`compliance_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '合规' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_compliance
-- ----------------------------

-- ----------------------------
-- Table structure for dk_department
-- ----------------------------
DROP TABLE IF EXISTS `dk_department`;
CREATE TABLE `dk_department`  (
  `department_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `manager_id` bigint(0) NULL DEFAULT NULL,
  `department_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `deparment_parent` bigint(0) NULL DEFAULT NULL,
  `deparment_duty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`department_id`) USING BTREE,
  INDEX `FK_Reference_19`(`manager_id`) USING BTREE,
  CONSTRAINT `FK_Reference_19` FOREIGN KEY (`manager_id`) REFERENCES `dk_worker` (`worker_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_department
-- ----------------------------
INSERT INTO `dk_department` VALUES (1, NULL, 'd1', NULL, NULL);

-- ----------------------------
-- Table structure for dk_details_of_tax
-- ----------------------------
DROP TABLE IF EXISTS `dk_details_of_tax`;
CREATE TABLE `dk_details_of_tax`  (
  `details_of_tax_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `invoice_id` bigint(0) NULL DEFAULT NULL,
  `commodity_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `commodity_type` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `commodity_unit` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `commodity_num` int(0) NULL DEFAULT NULL,
  `commodity_price` double NULL DEFAULT NULL,
  `commodity_amount` double NULL DEFAULT NULL,
  `commodity_tax_rate` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `commodity_tax` double NULL DEFAULT NULL,
  PRIMARY KEY (`details_of_tax_id`) USING BTREE,
  INDEX `FK_Relationship_5`(`invoice_id`) USING BTREE,
  CONSTRAINT `FK_Relationship_5` FOREIGN KEY (`invoice_id`) REFERENCES `dk_invoice` (`invoice_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发票商品价税详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_details_of_tax
-- ----------------------------
INSERT INTO `dk_details_of_tax` VALUES (7, 4, '*化学药品制剂*格列美脲片', '2mg*60片', '盒', 1, 185.84, 185.84, '13%', 24.16);
INSERT INTO `dk_details_of_tax` VALUES (8, 4, '*化学药品制剂*达格列净片/安达唐', '10mg*10片*3板', '盒', 1, 115.75, 115.75, '13%', 15.05);
INSERT INTO `dk_details_of_tax` VALUES (9, 4, '*化学药品制剂*阿法骨化醇软胶囊/盖诺真0.25ug*', '0.25UG*40粒', '盒', NULL, 52.83, 52.83, '13%', 6.87);
INSERT INTO `dk_details_of_tax` VALUES (10, 6, '*地质地震专用仪器*管线探测仪(主含:接收机RD8100PXL一台/发射机TX10一台)', 'RD8100PXL/T10', '套', 1, 18965.517241, 18965.52, '16%', 3034.48);
INSERT INTO `dk_details_of_tax` VALUES (11, 6, '*地质地震专用仪器*A字架连线', NULL, '根', 1, 862.06896552, 862.07, '16%', 137.93);

-- ----------------------------
-- Table structure for dk_feasibility
-- ----------------------------
DROP TABLE IF EXISTS `dk_feasibility`;
CREATE TABLE `dk_feasibility`  (
  `feasibility_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(0) NULL DEFAULT NULL,
  `feasibility_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`feasibility_id`) USING BTREE,
  INDEX `FK_Reference_9`(`project_id`) USING BTREE,
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`project_id`) REFERENCES `dk_project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '可研' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_feasibility
-- ----------------------------

-- ----------------------------
-- Table structure for dk_invoice
-- ----------------------------
DROP TABLE IF EXISTS `dk_invoice`;
CREATE TABLE `dk_invoice`  (
  `invoice_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(0) NOT NULL,
  `invoice_image` longblob NULL,
  `invoice_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `invoice_type` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_code_confirm` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_num_confirm` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_date` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `check_code` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchaser_name` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT ' ',
  `purchaser_register_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchaser_address` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchaser_bank` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seller_name` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seller_register_num` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seller_address` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `seller_bank` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `total_amount` double NULL DEFAULT NULL,
  `total_tax` double NULL DEFAULT NULL,
  `amount_in_words` varchar(254) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `amount_in_figuers` double NULL DEFAULT NULL,
  `note_drawer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `machine_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `service_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_type_org` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `invoice_password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `online_pay` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `payee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `checker` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `agent` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`invoice_id`) USING BTREE,
  INDEX `FK_Relationship_7`(`project_id`) USING BTREE,
  CONSTRAINT `FK_Relationship_7` FOREIGN KEY (`project_id`) REFERENCES `dk_project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发票' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_invoice
-- ----------------------------
INSERT INTO `dk_invoice` VALUES (4, 1, NULL, NULL, '电子普通发票', '037002000311', '037002000311', '46915250', '46915250', '2021年03月30日', '85696928040078102310', '沈卫军', '', '', '', '漱玉平民大药房连锁股份有限公司', '91370100705882496U', ':济南市历城区山大北路56号0531-66898908', ':齐鲁银行济南山大北路支行000000716003100000963', 355.42, 46.08, '肆佰圆零伍角', 400.5, '王玉森', '661616318584', '其他', '山东增值税电子普通发票', NULL, '', '李明', '胡飞飞', '', '山东省', '否');
INSERT INTO `dk_invoice` VALUES (6, 1, NULL, NULL, '专用发票', '4400183130', '4400183130', '05994158', '05994158', '2018年12月04日', '', '', '', '', '', '', '111830400', '', '', 19827.59, 3172.41, '贰万叁仟圆整', 23000, '林**', '', '其他', '广东增值税专用发票', NULL, '', '', '', '', '广东省', '否');

-- ----------------------------
-- Table structure for dk_product
-- ----------------------------
DROP TABLE IF EXISTS `dk_product`;
CREATE TABLE `dk_product`  (
  `product_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `specification_id` bigint(0) NULL DEFAULT NULL,
  `product_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `product_function` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `product_performance` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `product_parameter` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `product_deploy` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `product_tech` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `product_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `product_producer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE,
  INDEX `FK_Reference_14`(`specification_id`) USING BTREE,
  CONSTRAINT `FK_Reference_14` FOREIGN KEY (`specification_id`) REFERENCES `dk_specification` (`specification_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '产品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_product
-- ----------------------------

-- ----------------------------
-- Table structure for dk_project
-- ----------------------------
DROP TABLE IF EXISTS `dk_project`;
CREATE TABLE `dk_project`  (
  `project_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(0) NULL DEFAULT NULL,
  `project_name` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `project_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `project_abstract` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `project_proval_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`project_id`) USING BTREE,
  INDEX `FK_Reference_8`(`department_id`) USING BTREE,
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`department_id`) REFERENCES `dk_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_project
-- ----------------------------
INSERT INTO `dk_project` VALUES (1, 1, 'p1', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for dk_purchase_goods
-- ----------------------------
DROP TABLE IF EXISTS `dk_purchase_goods`;
CREATE TABLE `dk_purchase_goods`  (
  `goods_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `purchase_list_id` bigint(0) NULL DEFAULT NULL,
  `goods_name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `goods_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `goods_unit` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `goods_num` int(0) NULL DEFAULT NULL,
  `goods_price` double NULL DEFAULT NULL,
  `goods_amount` double NULL DEFAULT NULL,
  `goods_producer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`goods_id`) USING BTREE,
  INDEX `FK_Reference_13`(`purchase_list_id`) USING BTREE,
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`purchase_list_id`) REFERENCES `dk_purchase_list` (`purchase_list_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '采购单物品详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_purchase_goods
-- ----------------------------

-- ----------------------------
-- Table structure for dk_purchase_list
-- ----------------------------
DROP TABLE IF EXISTS `dk_purchase_list`;
CREATE TABLE `dk_purchase_list`  (
  `purchase_list_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(0) NULL DEFAULT NULL,
  `quoted_price` double NULL DEFAULT NULL,
  `purchase_proposer` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `purchase_approver` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `arrival_time` date NULL DEFAULT NULL,
  PRIMARY KEY (`purchase_list_id`) USING BTREE,
  INDEX `FK_Reference_12`(`project_id`) USING BTREE,
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`project_id`) REFERENCES `dk_project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '采购单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_purchase_list
-- ----------------------------

-- ----------------------------
-- Table structure for dk_requirements
-- ----------------------------
DROP TABLE IF EXISTS `dk_requirements`;
CREATE TABLE `dk_requirements`  (
  `requirements_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(0) NULL DEFAULT NULL,
  `requirements_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `requirements_input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `requirements_output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `requirements_process` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `requirements_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`requirements_id`) USING BTREE,
  INDEX `FK_Reference_11`(`project_id`) USING BTREE,
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`project_id`) REFERENCES `dk_project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '需求' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_requirements
-- ----------------------------

-- ----------------------------
-- Table structure for dk_risk
-- ----------------------------
DROP TABLE IF EXISTS `dk_risk`;
CREATE TABLE `dk_risk`  (
  `risk_id` int(0) NOT NULL AUTO_INCREMENT,
  `service_id` bigint(0) NULL DEFAULT NULL,
  `responsible_department_id` bigint(0) NULL DEFAULT NULL,
  `risk_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `risk_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `risk_consequence` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `risk_rank` int(0) NULL DEFAULT NULL,
  `risk_baseline` tinyint(1) NULL DEFAULT NULL,
  `risk_control_methods` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`risk_id`) USING BTREE,
  INDEX `FK_Reference_17`(`service_id`) USING BTREE,
  INDEX `FK_Reference_20`(`responsible_department_id`) USING BTREE,
  CONSTRAINT `FK_Reference_17` FOREIGN KEY (`service_id`) REFERENCES `dk_service` (`service_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_20` FOREIGN KEY (`responsible_department_id`) REFERENCES `dk_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '风险' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_risk
-- ----------------------------

-- ----------------------------
-- Table structure for dk_risk_compliance
-- ----------------------------
DROP TABLE IF EXISTS `dk_risk_compliance`;
CREATE TABLE `dk_risk_compliance`  (
  `risk_id` int(0) NULL DEFAULT NULL,
  `compliance_id` bigint(0) NULL DEFAULT NULL,
  INDEX `FK_Reference_15`(`risk_id`) USING BTREE,
  INDEX `FK_Reference_16`(`compliance_id`) USING BTREE,
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`risk_id`) REFERENCES `dk_risk` (`risk_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_16` FOREIGN KEY (`compliance_id`) REFERENCES `dk_compliance` (`compliance_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '风险对应合规' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_risk_compliance
-- ----------------------------

-- ----------------------------
-- Table structure for dk_service
-- ----------------------------
DROP TABLE IF EXISTS `dk_service`;
CREATE TABLE `dk_service`  (
  `service_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `service_parent` bigint(0) NULL DEFAULT NULL,
  `service_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`service_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '业务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_service
-- ----------------------------

-- ----------------------------
-- Table structure for dk_specification
-- ----------------------------
DROP TABLE IF EXISTS `dk_specification`;
CREATE TABLE `dk_specification`  (
  `specification_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `project_id` bigint(0) NULL DEFAULT NULL,
  `specification_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `specification_project_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`specification_id`) USING BTREE,
  INDEX `FK_Reference_10`(`project_id`) USING BTREE,
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`project_id`) REFERENCES `dk_project` (`project_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '技术规范书' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_specification
-- ----------------------------

-- ----------------------------
-- Table structure for dk_worker
-- ----------------------------
DROP TABLE IF EXISTS `dk_worker`;
CREATE TABLE `dk_worker`  (
  `worker_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `department_id` bigint(0) NULL DEFAULT NULL,
  `job_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `worker_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `worker_sex` int(0) NULL DEFAULT NULL,
  `worker_age` int(0) NULL DEFAULT NULL,
  `worker_titile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `worker_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `worker_duty` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`worker_id`) USING BTREE,
  INDEX `FK_Reference_18`(`department_id`) USING BTREE,
  CONSTRAINT `FK_Reference_18` FOREIGN KEY (`department_id`) REFERENCES `dk_department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '员工' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dk_worker
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
