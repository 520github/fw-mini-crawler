DROP TABLE IF EXISTS zz_official.`crawler_url_log`;
create table zz_official.crawler_url_log (
  `id` int NOT NULL AUTO_INCREMENT,
  `biz_type` varchar(128) not null COMMENT '业务类型',
  `request_id` varchar(64)  not null COMMENT '请求id--唯一--针对每次url爬取',
  `request_url` varchar(512)  not null COMMENT '请求url',
  `request_class` varchar(512)  COMMENT '请求url对应class类',
  `request_json_data` varchar(5000)  not null COMMENT '请求json数据',
  `request_extend_json_data` varchar(5000)  COMMENT '请求扩展json数据',
  `request_spider` varchar(128) not null COMMENT '请求对应处理的spider',
  `url_response_status` int  COMMENT '请求url返回状态码',
  `url_response_data` text  COMMENT '请求url返回数据',
  `crawler_result` text  COMMENT '爬取的结果数据',
  `crawler_exception` text  COMMENT '爬取异常信息',
  `crawler_step` varchar(128) COMMENT '爬取步骤',
  `crawler_start_time` datetime  COMMENT '爬取开始时间',
  `crawler_end_time` datetime  COMMENT '爬取结束时间',
  `retry_num` int not null default 0  COMMENT '重试次数',
  `sort` int not null  COMMENT '排序',
  `status` varchar(32) not null default 'init'  COMMENT '状态, init:初始化,doing:处理中,success:处理成功,fail:处理失败,exception:处理异常',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP() COMMENT '更新时间',
  PRIMARY KEY (`id`),
  unique key `unique_requestId` (`request_id`),
  index `index_bizType_requestSpider_status` (`biz_type`, `request_spider`, `status`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='爬取url日志';
;