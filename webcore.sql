/*
Navicat MySQL Data Transfer

Source Server         : 172.18.19.100-开发库
Source Server Version : 50625
Source Host           : 172.18.19.100:3306
Source Database       : webcore

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2016-06-20 19:36:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_exectimer
-- ----------------------------
DROP TABLE IF EXISTS `sys_exectimer`;
CREATE TABLE `sys_exectimer` (
  `zid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) DEFAULT '',
  `exec_code` varchar(45) DEFAULT '' COMMENT '执行流水号',
  `exec_type` varchar(45) DEFAULT '' COMMENT '执行类型 1LD支付 2LD同步订单  3跨境通同步订单',
  `exec_info` varchar(1000) DEFAULT '' COMMENT '执行内容',
  `create_time` char(19) DEFAULT '' COMMENT '创建时间',
  `begin_time` char(19) DEFAULT '' COMMENT '开始执行时间',
  `end_time` char(19) DEFAULT '' COMMENT '执行完成时间',
  `exec_time` char(19) DEFAULT '' COMMENT '预计执行时间',
  `flag_success` int(11) DEFAULT '0' COMMENT '是否成功',
  `remark` longtext COMMENT '处理备注',
  `exec_number` int(11) DEFAULT '0' COMMENT '执行次数',
  PRIMARY KEY (`zid`)
) ENGINE=InnoDB AUTO_INCREMENT=12301 DEFAULT CHARSET=utf8 COMMENT='定时执行表';

-- ----------------------------
-- Records of sys_exectimer
-- ----------------------------

-- ----------------------------
-- Table structure for sys_lock
-- ----------------------------
DROP TABLE IF EXISTS `sys_lock`;
CREATE TABLE `sys_lock` (
  `zid` int(8) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `uid` varchar(32) NOT NULL COMMENT 'UUID',
  `keyid` varchar(32) DEFAULT NULL COMMENT 'key流水号',
  `keycode` varchar(50) NOT NULL COMMENT '锁号',
  `creator` varchar(40) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updator` varchar(40) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`zid`),
  UNIQUE KEY `key_code_unique` (`keycode`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_lock
-- ----------------------------
INSERT INTO `sys_lock` VALUES ('83', 'a5072cab1c1411e6a30300505692798f', '6c940abbcded4d5da0feca1eaf259c8e', 'SF031102', null, '2016-05-17 17:49:27', null, null);

-- ----------------------------
-- Table structure for sys_webcode
-- ----------------------------
DROP TABLE IF EXISTS `sys_webcode`;
CREATE TABLE `sys_webcode` (
  `zid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` char(32) DEFAULT '',
  `code_start` varchar(100) DEFAULT '' COMMENT '编码起始',
  `date_apply` char(6) DEFAULT '' COMMENT '日期参数',
  `min_number` int(11) DEFAULT '100000' COMMENT '最小数字',
  `now_number` int(11) DEFAULT '100000' COMMENT '当前数字',
  `code_note` varchar(45) DEFAULT '' COMMENT '备注',
  `flag_date` int(11) DEFAULT '1' COMMENT '是否日期列',
  PRIMARY KEY (`zid`),
  UNIQUE KEY `code_start_UNIQUE` (`code_start`)
) ENGINE=InnoDB AUTO_INCREMENT=299 DEFAULT CHARSET=utf8 COMMENT='系统编码表';

-- ----------------------------
-- Records of sys_webcode
-- ----------------------------
INSERT INTO `sys_webcode` VALUES ('14', '75e615befb4a11e2ac71000c298b20fc', 'test', '160419', '100000', '100002', '', '1');
INSERT INTO `sys_webcode` VALUES ('15', '77eaacbcfb5411e2ac71000c298b20fc', 'FS', '130802', '100000', '100007', '', '1');
INSERT INTO `sys_webcode` VALUES ('16', 'cbd34e62fb5711e2ac71000c298b20fc', 'FF', '140414', '100000', '100013', '', '1');

-- ----------------------------
-- Procedure structure for proc_get_unique_code
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_get_unique_code`;
DELIMITER ;;
CREATE DEFINER=`qhsy`@`%` PROCEDURE `proc_get_unique_code`(in p_code_start varchar(100))
begin

declare p_date char(6);
declare p_now char(6);
declare p_return varchar(30);

declare p_nowno int;

set p_now=DATE_FORMAt(now(), '%y%m%d') ;



set p_date=ifnull((select a.date_apply from sys_webcode a where  a.code_start=p_code_start),'');

if(p_date='')
THEN 
	INSERT INTO `sys_webcode`
	(
	`uid`,
	`code_start`,
	`date_apply`,
	`min_number`,
	`now_number`)
	VALUES
	(
		replace(uuid(),'-',''),
		p_code_start,
		p_now,
		100000,
		100000
	);
	set p_date=p_now;

end if;


 if(p_date!=p_now) then

	
	if length(p_date)!=6 then
		set p_now='';
	else
		update sys_webcode set now_number=min_number,date_apply=p_now where code_start=p_code_start and flag_date=1;
	end if;

end if;

start transaction; 
set p_return=(select now_number from sys_webcode zwwc  where zwwc.code_start=p_code_start for update);
set p_return=p_return+1;
update sys_webcode set now_number=p_return where code_start=p_code_start;

commit;

select concat(p_code_start,p_now,p_return) as webcode;



end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_lock_or_unlock_somekey
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_lock_or_unlock_somekey`;
DELIMITER ;;
CREATE DEFINER=`qhsy`@`%` PROCEDURE `proc_lock_or_unlock_somekey`(in somekey VARCHAR(1000),in keysplit VARCHAR(2),in timeoutsecond INT,in lockflag INT,in uuid VARCHAR(50))
BEGIN
	
	DECLARE i int DEFAULT 0;
	DECLARE lockid VARCHAR(32) DEFAULT '';
	DECLARE createTime VARCHAR(50) DEFAULT '';
	DECLARE lockCurrentKey VARCHAR(50) DEFAULT '';
	DECLARE lockzid INT DEFAULT 0; 
	DECLARE outFlag INT DEFAULT 2;
	

	 
	DECLARE t_error int default 0; 
	 
	DECLARE continue handler for sqlexception set t_error=1; 
	SET outFlag=2;

	
	IF lockflag =1 THEN

				
				 
				SET createTime=CONCAT(current_timestamp,'');
				
				
				
				

				DELETE FROM sys_lock where keycode=somekey and (UNIX_TIMESTAMP(createTime) - UNIX_TIMESTAMP(create_time))>timeoutsecond;


				SELECT zid INTO lockzid from sys_lock where keycode=somekey;

					IF FOUND_ROWS()<=0 THEN


						
							
								INSERT INTO sys_lock (uid,keycode,keyid,create_time)
									SELECT REPLACE(UUID(),'-',''),somekey,uuid,createTime ;

								IF ROW_COUNT()<=0 THEN
									
									set outFlag=2;

								ELSE
									
									SET outFlag=1;
								END IF;

					ELSE
						SET uuid=lockid;
						SET outFlag=2;
					END IF;

		
	
	ELSE 

			IF lockflag =2 THEN
				
				
				START TRANSACTION;
				
				 DELETE FROM sys_lock where keyid=uuid;

				
				IF t_error=1 THEN 
					SET uuid='';
					SET outFlag=2;
					ROLLBACK;
				ELSE
					SET uuid='';
					SET outFlag=1;
					COMMIT;			
				END IF;	
			ELSE
				SET uuid='';
				SET outFlag=2;

			END IF;
	END IF;

	SELECT outFlag;

END
;;
DELIMITER ;
