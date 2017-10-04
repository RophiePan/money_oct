/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sellsep

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-04 22:29:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(20) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `id_card` varchar(255) NOT NULL,
  `bank_card` varchar(255) NOT NULL COMMENT '银行卡号',
  `bank_name` varchar(255) NOT NULL COMMENT '开户银行',
  `phone_number` varchar(255) NOT NULL,
  `recommend_id` int(11) NOT NULL,
  `user_status` int(11) NOT NULL DEFAULT '1' COMMENT '用户状态(1、未审核  2、已审核)',
  `register_date` date NOT NULL,
  `audit_date` date DEFAULT NULL COMMENT '审核日期',
  `comment` varchar(255) DEFAULT '',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('00000000000000010000', '刘志功', '111111', '210203196303240017', '2222222', '中国农业银行', '1552464118', '10000', '0', '2016-12-23', null, '初始投资人');
