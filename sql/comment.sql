-- USE `blog`;

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` integer primary key AUTOINCREMENT,
  `type` INTEGER DEFAULT 0,                     -- comment type, 0 is article comment, 1 is link comment
  `article_id` integer DEFAULT NULL,            -- article id
  `root_id` integer DEFAULT -1,                 -- root comment id
  `content` varchar(512) DEFAULT NULL,          -- comment content
  `reply_to_user` bigint DEFAULT -1,                 -- reply to whose id
  `reply_to_comment` bigint DEFAULT -1,            -- reply to which comment id
  `create_by` integer DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  `update_by` integer DEFAULT NULL,
  `update_time` bigint DEFAULT NULL,
  `deleted` INTEGER DEFAULT 0
);

insert  into `comment`
    (`id`, `type`, `article_id`, `root_id`, `content`, `reply_to_user`, `reply_to_comment`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`)
values
    (1,0,1,-1,'asS',-1,-1,1,1685948992,1,1685948992,0),
    (2,0,1,-1,'[哈哈]SDAS',-1,-1,1,1685948992,1,1685948992,0),
    (3,0,1,-1,'是大多数',-1,-1,1,1685948992,1,1685948992,0),
    (4,0,1,-1,'撒大声地',-1,-1,1,1685948992,1,1685948992,0),
    (5,0,1,-1,'你再说什么',-1,-1,1,1685948992,1,1685948992,0),
    (6,0,1,-1,'hffd',-1,-1,1,1685948992,1,1685948992,0),
    (9,0,1,2,'你说什么',1,2,1,1685948992,1,1685948992,0),
    (10,0,1,2,'哈哈哈哈[哈哈]',1,9,1,1685948992,1,1685948992,0),
    (11,0,1,2,'we全文',1,10,3,1685948992,1,1685948992,0),
    (12,0,1,-1,'王企鹅',-1,-1,1,1685948992,1,1685948992,0),
    (13,0,1,-1,'什么阿是',-1,-1,1,1685948992,1,1685948992,0),
    (14,0,1,-1,'新平顶山',-1,-1,1,1685948992,1,1685948992,0),
    (15,0,1,-1,'2222',-1,-1,1,1685948992,1,1685948992,0),
    (16,0,1,2,'3333',1,11,1,1685948992,1,1685948992,0);