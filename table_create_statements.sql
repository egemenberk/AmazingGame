CREATE TABLE `db_group_17`.`user` (
	`id` int(11) NOT NULL,
	`email` varchar(32) UNIQUE NOT NULL,
	`password_encrypted` varchar(255) NOT NULL,
    `username` varchar(32) UNIQUE NOT NULL,
	`sesion_id` varchar(255) DEFAULT NULL,
	`create_date_time` datetime DEFAULT NULL,
	`update_date_time` datetime DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `db_group_17`.`score` (
	`id` int(11) NOT NULL,
	`timestamp` datetime NOT NULL,
	`score` int(11) NOT NULL,
	`user_id` int(11) NOT NULL,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`user_id`) REFERENCES user(`id`) ON DELETE CASCADE,
	INDEX `index_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

