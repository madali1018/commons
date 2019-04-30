# 建表语句
create table t_wmb_record_entity(
                                  id int primary key auto_increment,#id 整形 主键 自增长
                                  infoId BIGINT COMMENT 'infoId', # infoId
                                  houseId BIGINT COMMENT 'houseId', # houseId
                                  extend VARCHAR(2048) COMMENT '扩展字段', # 扩展字段：写入json字符串，使用时再将json字符串转成json。避开MySQL的JSON类型只能使用5.7以上版本的限制。
                                  updateTime timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间'
);




