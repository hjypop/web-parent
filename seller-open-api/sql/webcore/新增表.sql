DROP TABLE
IF EXISTS wc_sellerinfo;

CREATE TABLE `wc_sellerinfo` (
	`zid` INT (11) PRIMARY KEY AUTO_INCREMENT,
	`uid` CHAR (32) NOT NULL,
	`seller_code` VARCHAR (45) NOT NULL UNIQUE COMMENT '商家编号',
	`seller_name` VARCHAR (450) NOT NULL COMMENT '商家名称',
	`seller_descrption` text COMMENT '商家描述',
	`seller_telephone` VARCHAR (50) NOT NULL COMMENT '联系电话',
	`seller_email` VARCHAR (50) COMMENT '商家信箱',
	`status` INT DEFAULT 0 COMMENT '商户状态 0 未开通 1 已开通 2 已禁用',
	 price_type INT DEFAULT 0 COMMENT '价格类型 0 成本价 1 销售价',
	 commission text DEFAULT NULL COMMENT '佣金比例 json存储',
	`creator` VARCHAR (40) NOT NULL COMMENT '创建人',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`updator` VARCHAR (40) NOT NULL COMMENT '修改人',
	`update_time` datetime NOT NULL COMMENT '修改时间'
) COMMENT = '商家信息';

DROP TABLE
IF EXISTS wc_openapi;

CREATE TABLE wc_openapi (
	`zid` INT (11) PRIMARY KEY AUTO_INCREMENT,
	`uid` CHAR (32) NOT NULL,
	api_code VARCHAR (50) NOT NULL UNIQUE COMMENT '接口编号',
	api_name VARCHAR (50) NOT NULL COMMENT '接口中文名称',
	method VARCHAR (100) NOT NULL UNIQUE COMMENT '接口方法名称',
	`status` INT DEFAULT 0 COMMENT '接口状态 0 未开通 1 已开通 2 已禁用',
	description text COMMENT '接口描述',
	is_deleted INT DEFAULT 0 COMMENT '是否已删除 0 未删除 1 已删除',
	`creator` VARCHAR (40) NOT NULL COMMENT '创建人',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`updator` VARCHAR (40) NOT NULL COMMENT '修改人',
	`update_time` datetime NOT NULL COMMENT '修改时间'
) COMMENT 'openApi接口表';

DROP TABLE
IF EXISTS wc_seller_api;

CREATE TABLE wc_seller_api (
	`zid` INT (11) PRIMARY KEY AUTO_INCREMENT,
	`uid` CHAR (32) NOT NULL,
	seller_code VARCHAR (50) NOT NULL COMMENT '商户编码',
	api_code VARCHAR (50) NOT NULL COMMENT '接口编码',
	`creator` VARCHAR (40) NOT NULL COMMENT '创建人',
	`create_time` datetime NOT NULL COMMENT '创建时间'
) COMMENT '商户接口关系表';

ALTER TABLE wc_seller_api ADD UNIQUE KEY seller_api_uniq (seller_code, api_code);