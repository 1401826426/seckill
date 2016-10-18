create database seckill ;
use seckill ;

  create  table seckill(
     `seckill_id`  bigint(20)  NOT NULL AUTO_INCREMENT  COMMENT  '商品库存id',
     `name`   varchar(120)  NOT  NULL  COMMENT  '商品名称' ,
     `number`  INT NOT NULL COMMENT  '库存数量' ,
     `start_time`   TIMESTAMP  NOT NULL  COMMENT  '秒杀开始时间' ,
     `end_time`   TIMESTAMP   NOT NULL COMMENT '秒杀结束时间' ,
     `create_time`  TIMESTAMP   NOT NULL   DEFAULT  CURRENT_TIMESTAMP   COMMENT  '创建时间',
     PRIMARY KEY(seckill_id),
     key  idx_start_time(start_time),
     key  idx_end_time(end_time),
     key  idx_create_time(create_time)
  )ENGINE=InnoDB  AUTO_INCREMENT = 1000 DEFAULT  CHARSET=utf8  COMMENT  '秒杀库存表' ;

insert into
seckill(name , number , start_time , end_time)
values
('1000元秒杀iphone6' , 100 , '2015-11-11  00:00;00' , '2015-11-12  00:00;00'),
('500元秒杀ipad2' , 200 , '2015-11-11  00:00;00' , '2015-11-12  00:00;00'),
('300元秒杀小米4' , 300 , '2015-11-11  00:00;00' , '2015-11-12  00:00;00'),
 ('200元秒杀红米note' , 400 , '2015-11-11  00:00;00' , '2015-11-12  00:00;00');



CREATE TABLE success_killed(
    `seckill_id`  bigint  not null comment  '秒杀商品id' ,
     `user_phone` bigint  not null comment  '用户手机号' ,
     `state`  tinyint  not null DEFAULT  -1  comment '状态标识: -1  无效  0:成功  1:已付款' ,
     `create_time`  TIMESTAMP   not null comment  '创建时间',
     PRIMARY Key(seckill_id , user_phone),
     key  idx_create_time(create_time)
)ENGINE=InnoDB  AUTO_INCREMENT = 1000 DEFAULT  CHARSET=utf8  COMMENT  '秒杀成功明细表' ;

































