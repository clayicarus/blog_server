-- CREATE DATABASE `blog`;
-- USE `blog`;

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
    `id` integer primary key autoincrement,
    `username` varchar(64) NOT NULL DEFAULT 'unknown',
    `nickname` varchar(64) NOT NULL DEFAULT 'unknown',
    `password` varchar(64) NOT NULL DEFAULT 'unknown',  -- encrypted password
    `type` integer DEFAULT 0,                           -- 0 is standard user, 1 is administrator
    `status` integer DEFAULT 0,                         -- 0 is in use, 1 is frozen
    `email` varchar(128) DEFAULT NULL,
    `phone` varchar(32) DEFAULT NULL,
    `gender` integer DEFAULT 0,                         -- 0 is unknown, 1 is male, 2 is female
    `avatar` varchar(128) DEFAULT NULL,                 -- avatar key in obs
    `create_by` integer DEFAULT NULL,
    `create_time` bigint DEFAULT NULL,
    `update_by` integer DEFAULT NULL,
    `update_time` bigint DEFAULT NULL,
    `deleted` integer DEFAULT 0
);

insert  into `sys_user`
(`id`,`username`,`nickname`,`password`,`type`,`status`,`email`,`phone`,`gender`,`avatar`,`create_by`,`create_time`,`update_by`,`update_time`,`deleted`)
values (1, 'test', 'test_name',
        '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy',
        1, 0, 'test@abc.com', '1145141919810', 1, null, null, 1685948992, null, null, 0);

-- insert  into `sys_user`
--     (`id`,`username`,`nickname`,`password`,`type`,`status`,`email`,`phone`,`gender`,`avatar`,`create_by`,`create_time`,`update_by`,`update_time`,`deleted`)
-- values (1,'sg','sg333','$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy',1,0,'23412332@qq.com','18888888888', 1,'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi',NULL,'2022-01-05 09:01:56',1,'2022-01-30 15:37:03',0),
--        (3,'sg3','weqe','$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O',1,1,NULL,NULL,0,NULL,NULL,'2022-01-05 13:28:43',NULL,'2022-01-05 13:28:43',0),
--        (4,'sg2','dsadd','$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he',1,0,'23412332@qq.com','19098790742',0,NULL,NULL,NULL,NULL,NULL,0),
--        (5,'sg2233','tteqe','',1,0,NULL,'18246845873',1,NULL,NULL,'2022-01-06 03:51:13',NULL,'2022-01-06 07:00:50',0),
--        (6,'sangeng','sangeng','$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy',1,0,'2312321','17777777777',0,NULL,NULL,'2022-01-16 06:54:26',NULL,'2022-01-16 07:06:34',0),
--        (14787164048662,'weixin','weixin','$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC',0,0,'weixin@qq.com',NULL,NULL,NULL,-1,'2022-01-30 17:18:44',-1,'2022-01-30 17:18:44',0);
