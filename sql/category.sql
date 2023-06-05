-- USE `blog`;

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
    `id` integer primary key autoincrement,
    `name` varchar(128) DEFAULT NULL,           -- category name
    `parent_category` integer DEFAULT -1,       -- parent category id
    `description` varchar(512) DEFAULT NULL,    -- description
    `status` integer DEFAULT 0,                 -- 0 is on use, 1 is forbidden
    `create_by` integer DEFAULT NULL,           -- create user id
    `create_time` bigint DEFAULT NULL,          -- create time stamp
    `update_by` integer DEFAULT NULL,           -- update user id
    `update_time` bigint DEFAULT NULL,          -- update time stamp
    `deleted` integer DEFAULT 0                 -- 0 is deleted, 1 not deleted
);

insert  into `category`(`id`,`name`,`parent_category`,`description`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`deleted`) values (1,'java',-1,'wsd','0',NULL,NULL,NULL,NULL,0),(2,'PHP',-1,'wsd','0',NULL,NULL,NULL,NULL,0);