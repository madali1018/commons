# Description: 工具库[持续更新]

## jdk版本：jdk1.8.0_102

## 项目结构

* #### com.mada.es目录 ####
	* es增删改，批量增删
	* es获取索引分片，副本信息
	* es获取索引所有的字段及类型
	* es查询：in, not in；多字段sort；query；filter；function_score等
	* 详情见csdn专栏：[https://blog.csdn.net/mada26/column/info/29898](https://blog.csdn.net/mada26/column/info/29898 "ElasticSearch-Java服务化开发")
	

* #### com.mada.commons目录 ####
	* annotation：注解
	* configuration：配置
	* dao：dao层
	* demo：测试包
	* designpattern：设计模式demo
	* entity：实体类
	* enumeration：枚举
	* service：service层
	* util(java工具类包，其中包括部分demo)
		* 负载均衡算法
		* collection工具类
		* 枚举工具类
		* 文件压缩解压缩工具类
		* hibernate工具类
		* http工具类
		* MD5和一致性hash算法
		* 日志处理工具类(log4j2,cligb+logback,jdk proxy+logback)
		* MQ工具类(kafka+redis)
		* 数字处理工具类
		* 对象转换和深拷贝工具类
		* redis工具类
		* 线程工具类
		* 时间工具类
		* Zookeeper工具类

* #### resource目录 ####
	* 配置文件

* #### test目录 ####
	* 测试类

