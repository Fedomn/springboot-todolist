CREATE TABLE `users`
(
    `id`   bigint(20) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `todos`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `context` varchar(255) DEFAULT NULL,
    `user_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_user_id` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


INSERT INTO `users` (`id`, `name`)
VALUES (1, 'user1');


INSERT INTO `todos` (`id`, `context`, `user_id`)
VALUES (1, 'todo1', 1),
       (2, 'todo2', 1);


ALTER TABLE `users`
    ADD COLUMN `created_date` datetime DEFAULT NULL,
    ADD COLUMN `last_modified_date` datetime DEFAULT NULL,
    ADD COLUMN `created_by` varchar(255) DEFAULT NULL,
    ADD COLUMN `last_modified_by` varchar(255) DEFAULT NULL;

ALTER TABLE `todos`
    ADD COLUMN `created_date` datetime DEFAULT NULL,
    ADD COLUMN `last_modified_date` datetime DEFAULT NULL,
    ADD COLUMN `created_by` varchar(255) DEFAULT NULL,
    ADD COLUMN `last_modified_by` varchar(255) DEFAULT NULL;


CREATE TABLE `operator`
(
    `id`   varchar(255) NOT NULL,
    `name` varchar(255) NOT NULL,
    `role` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `idx_operator_role` (`role`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;