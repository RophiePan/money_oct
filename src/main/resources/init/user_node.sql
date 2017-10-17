/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sellsep

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-17 22:21:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user_node`
-- ----------------------------
DROP TABLE IF EXISTS `user_node`;
CREATE TABLE `user_node` (
  `node_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `root_node_id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `sub_branch` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `level` int(11) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_node
-- ----------------------------
INSERT INTO `user_node` VALUES ('1', '10000', '10000', '10000', '0', '2017-10-17 22:20:36', '1', '初始人');
