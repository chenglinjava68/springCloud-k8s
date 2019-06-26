-- CREATE TABLE `cloud_properties` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   `key` varchar(255) DEFAULT NULL,
--   `value` varchar(500) DEFAULT NULL,
--   `application` varchar(50) DEFAULT NULL,
--   `profile` varchar(50) DEFAULT NULL,
--   `label` varchar(50) DEFAULT NULL,
--   PRIMARY KEY (`id`)
-- );

-- zuul-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','zuul','test','master'),
('spring.rabbitmq.username','guest','zuul','test','master'),
('spring.rabbitmq.password','guest','zuul','test','master'),
('zuul.routes.serviceProducer.serviceId','serviceProducer','zuul','test','master'),
('zuul.routes.serviceProducer.path','/serviceProducer/**','zuul','test','master');

-- gateway-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','gateway','test','master'),
('spring.rabbitmq.username','guest','gateway','test','master'),
('spring.rabbitmq.password','guest','gateway','test','master'),
('spring.cloud.gateway.routes[0].id','oauth','gateway','test','master'),
('spring.cloud.gateway.routes[0].uri','lb://oauth','gateway','test','master'),
('spring.cloud.gateway.routes[0].predicates[0]','Path=/oauth/**','gateway','test','master'),
('spring.cloud.gateway.routes[0].filters[0]','StripPrefix=1','gateway','test','master');

-- serviceProducer-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','serviceProducer','test','master'),
('spring.rabbitmq.username','guest','serviceProducer','test','master'),
('spring.rabbitmq.password','guest','serviceProducer','test','master');

-- oauth-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','oauth','test','master'),
('spring.rabbitmq.username','guest','oauth','test','master'),
('spring.rabbitmq.password','guest','oauth','test','master'),
('spring.redis.database','0','oauth','test','master'),
('spring.redis.host-name','192.168.198.128','oauth','test','master'),
('spring.redis.port','6379','oauth','test','master'),
('spring.redis.pool.max-idle','8','oauth','test','master'),
('spring.redis.poolmin-idle','0','oauth','test','master'),
('spring.redis.poolmax-active','8','oauth','test','master'),
('spring.redis.poolmax-wait','-1','oauth','test','master'),
('spring.datasource.druid.driver-class-name','com.mysql.cj.jdbc.Driver','oauth','test','master'),
('spring.datasource.druid.url','jdbc:mysql://192.168.198.128:3306/test?Unicode=true&characterEncoding=UTF-8&useSSL=false','oauth','test','master'),
('spring.datasource.druid.username','root','oauth','test','master'),
('spring.datasource.druid.password','weilus','oauth','test','master'),
('spring.datasource.druid.initial-size','10','oauth','test','master'),
('spring.datasource.druid.min-idle','10','oauth','test','master'),
('spring.datasource.druid.max-active','100','oauth','test','master'),
('spring.datasource.druid.max-wait','6000','oauth','test','master'),
('spring.datasource.druid.min-evictable-idle-time-millis','300000','oauth','test','master'),
('spring.datasource.druid.time-between-eviction-runs-millis','60000','oauth','test','master');