DROP TABLE
IF EXISTS wc_sellerinfo;

CREATE TABLE `wc_sellerinfo` (
	`zid` INT (11) PRIMARY KEY AUTO_INCREMENT,
	`uid` CHAR (32) NOT NULL,
	`seller_code` VARCHAR (45) NOT NULL UNIQUE COMMENT '商家编号',
	`seller_name` VARCHAR (450) NOT NULL COMMENT '商家名称',
	`seller_short_name` VARCHAR (450) DEFAULT '' COMMENT '商家简称',
	`seller_descrption` VARCHAR (5000) DEFAULT '' COMMENT '商家描述',
	`seller_status` VARCHAR (45) DEFAULT '' COMMENT '商家状态',
	`seller_area` VARCHAR (100) NOT NULL COMMENT '所在地',
	`seller_telephone` VARCHAR (50) NOT NULL COMMENT '联系电话',
	`seller_return_address` VARCHAR (200) NOT NULL COMMENT '退货地址',
	`seller_return_postcode` VARCHAR (6) NOT NULL COMMENT '退货邮编',
	`seller_return_contact` VARCHAR (200) NOT NULL COMMENT '退货联系人地址',
	`seller_return_telephone` VARCHAR (50) NOT NULL COMMENT '退货联系人电话',
	`seller_company_name` VARCHAR (200) NOT NULL COMMENT '公司名称',
	`seller_email` VARCHAR (50) NOT NULL COMMENT '商家信箱',
	`seller_type` VARCHAR (45) DEFAULT '449746390001' COMMENT '店铺类型',
	`out_date` VARCHAR (19) NOT NULL COMMENT '合作到期时间',
	`creator` VARCHAR (40) NOT NULL COMMENT '创建人',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`updator` VARCHAR (40) NOT NULL COMMENT '修改人',
	`update_time` datetime NOT NULL COMMENT '修改时间',
	`flow_status` VARCHAR (45) DEFAULT '4497172300140001' COMMENT '审核是否通过:4497172300140001(待合同专员审批)'
) COMMENT = '商家信息';

#测试数据添加
INSERT INTO wc_sellerinfo (
	zid,
	uid,
	seller_code,
	seller_name,
	seller_short_name,
	seller_descrption,
	seller_status,
	seller_area,
	seller_telephone,
	seller_return_address,
	seller_return_postcode,
	seller_return_contact,
	seller_return_telephone,
	seller_company_name,
	seller_email,
	seller_type,
	out_date,
	creator,
	create_time,
	updator,
	update_time,
	flow_status
)(
	SELECT
		zid,
		uid,
		seller_code,
		seller_name,
		seller_short_name,
		seller_descrption,
		seller_status,
		seller_area,
		seller_telephone,
		seller_return_address,
		seller_return_postcode,
		seller_return_contact,
		seller_return_telephone,
		seller_company_name,
		seller_email,
		seller_type,
		out_date,
		'system',
		now(),
		'system',
		now(),
		flow_status
	FROM
		usercenter.uc_sellerinfo
	LIMIT 15
);