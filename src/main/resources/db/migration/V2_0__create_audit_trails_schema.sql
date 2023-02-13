CREATE TABLE `audit_trails` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `log` varchar(255) NOT NULL,
  `entry_date` timestamp NOT NULL DEFAULT (now())
);
