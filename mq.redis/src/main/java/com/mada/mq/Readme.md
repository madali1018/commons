## redis列表使用技巧 ##
1. lpush+lpop=Stack(栈)
2. lpush+rpop=Queue（队列）
3. lpush+ltrim=Capped Collection（有限集合）
4. lpush+brpop=Message Queue（消息队列）
5. 

## 其他
1. 生产/消费，发布/订阅的区别：
	1. 生产一条消息，只有一个消费者消费
	2. 发布一条消息，N个订阅者订阅
	3. 
