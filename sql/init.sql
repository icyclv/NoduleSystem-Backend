drop database if exists NoduleSystem;
create database NoduleSystem;
use NoduleSystem;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
                            `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户名',
                            `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
                            `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码，加密存储',
                            `salt` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL  DEFAULT '' COMMENT '密码加密盐',
                            `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '邮箱',
                            `type` tinyint(4) NULL DEFAULT 0 COMMENT '账户类型，0：普通用户，1：管理员',
                            `status` tinyint(4) NULL DEFAULT 0 COMMENT '账户状态，0：正常，1：禁用',
                            `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

#生成测试用例
insert into `tb_user` values (1010, 'admin', '12345678901', '123456', '123456','123@qq.com', 1, 0, '2019-01-01 00:00:00', '2019-01-01 00:00:00');

DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info`  (
                                 `user_id` bigint(20) UNSIGNED NOT NULL COMMENT '主键，用户id',
                                 `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '真实姓名',
                                 `gender` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '性别，0：男，1：女',
                                 `birthday` date NULL DEFAULT NULL COMMENT '生日',
                                `department` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门',
                                 `position` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '职位',
                                 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                 PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;



DROP TABLE IF EXISTS `tb_patient`;
CREATE TABLE `tb_patient`(
        `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
        `doctor_id` bigint(20) UNSIGNED NOT NULL COMMENT '医生Id',
        `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '病人姓名',
        `gender` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '性别，0：男，1：女',
        `birthday` date NULL DEFAULT NULL COMMENT '生日',
        `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
        `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '地址',
        `backup` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '病史',
        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
        `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
        PRIMARY KEY (`id`) USING BTREE,
        INDEX `patinet_name`(`name`) USING BTREE,
        INDEX `doctor_id`(`doctor_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

# #生成测试用例
# INSERT INTO `tb_patient` VALUES (1, 1011, '张三', 0, '1990-01-01', '12345678901', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (2, 1011, '李四', 0, '1990-01-01', '12345678902', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (3, 1011, '王五', 0, '1990-01-01', '12345678903', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (4, 1011, '赵六', 0, '1990-01-01', '12345678904', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (5, 1011, '孙七', 0, '1990-01-01', '12345678905', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (6, 1011, '周八', 0, '1990-01-01', '12345678906', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_patient` VALUES (7, 1011, '吴九', 0, '1990-01-01', '12345678907', '北京市海淀区', '无', '2019-01-01 00:00:00', '2019-01-01 00:00:00');

DROP TABLE IF EXISTS `tb_study`;
DROP TABLE IF EXISTS `tb_scan`;
CREATE TABLE `tb_scan`(
                             `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `patient_id` bigint(20) UNSIGNED NOT NULL COMMENT '病人id',
                            `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '检测时间',
                            `age` int(11) NOT NULL COMMENT '病人检测时年龄',
                            `status` int(8) UNSIGNED NULL DEFAULT 0 COMMENT '处理状态',
                            `appearance` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '影像所见',
                            `diagnosis` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '诊断意见',
                            `file_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件名',
                             `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `foreign_key_patient`(`patient_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

# #生成测试用例
# INSERT INTO `tb_scan` VALUES (1, 1, '2019-01-01 00:00:00', 29, 0, '无', '无', '1.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_scan` VALUES (2, 1, '2019-01-01 00:00:00', 29, 0, '无', '无', '2.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_scan` VALUES (3, 1, '2019-01-01 00:00:00', 29, 0, '无', '无', '3.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_scan` VALUES (4, 1, '2019-01-01 00:00:00', 29, 0, '无', '无', '4.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_scan` VALUES (5, 1, '2019-01-01 00:00:00', 29, 0, '无', '无', '5.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_scan` VALUES (6, 2, '2019-01-01 00:00:00', 29, 0, '无', '无', '6.dcm', '2019-01-01 00:00:00', '2019-01-01 00:00:00');
#


DROP TABLE IF EXISTS `tb_nodule`;

CREATE TABLE `tb_nodule`(
                           `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `scan_id` bigint(20) UNSIGNED NOT NULL COMMENT '影像id',
                           `coordinate` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '检测坐标',
                           `diameter` double(11,3)  NULL DEFAULT 0.0 COMMENT '结节直径',
                           `confidence` double(11,3)  NULL DEFAULT 0.0 COMMENT '置信度',
                           `type` int(11)  NULL DEFAULT 0 COMMENT '分类结果',
                           `classification_probability` double(11,3)  NULL  DEFAULT 0.0 COMMENT '恶性概率',
                           `deleted` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否被删除 0 未删除 1 已删除',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

                           PRIMARY KEY (`id`) USING BTREE,
                           INDEX `foreign_key_study`(`scan_id`) USING BTREE

)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
#生成测试用例
# INSERT INTO `tb_nodule` VALUES (1, 1, '111,222,333', 1.0, 0.9, 1, 0.9,0, '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_nodule` VALUES (2, 1, '111,222,333', 1.0, 0.9, 1, 0.9, 0,'2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_nodule` VALUES (3, 1, '111,222,333', 1.0, 0.9, 1, 0.9,0, '2019-01-01 00:00:00', '2019-01-01 00:00:00');
# INSERT INTO `tb_nodule` VALUES (4, 1, '111,222,333', 1.0, 0.9, 1, 0.9, 0,'2019-01-01 00:00:00', '2019-01-01 00:00:00');
#
#
