CREATE TABLE `drones` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `serial_number` varchar(100) UNIQUE NOT NULL,
  `model` ENUM ('Lightweight', 'Middleweight', 'Cruiserweight', 'Heavyweight') NOT NULL,
  `weight_limit` float NOT NULL,
  `battery_level` float NOT NULL,
  `state` ENUM ('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE `medications` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `weight` float NOT NULL,
  `code` varchar(255) UNIQUE NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE `drone_deliveries` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `drone_id` bigint NOT NULL,
  `status` ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETE') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE `drone_delivery_items` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `drone_id` bigint NOT NULL,
  `medication_id` bigint NOT NULL,
  `drone_delivery_id` bigint NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT (now()),
  `updated_at` timestamp NOT NULL DEFAULT (now())
);

CREATE INDEX `drone_deliveries_drone_id_index` ON `drone_deliveries` (`drone_id`);

CREATE INDEX `drone_delivery_items_drone_id_index` ON `drone_delivery_items` (`drone_id`);

CREATE INDEX `drone_delivery_items_medication_id_index` ON `drone_delivery_items` (`medication_id`);

CREATE INDEX `drone_delivery_items_drone_delivery_id_index` ON `drone_delivery_items` (`drone_delivery_id`);

ALTER TABLE `drone_deliveries` ADD FOREIGN KEY (`drone_id`) REFERENCES `drones` (`id`);

ALTER TABLE `drone_delivery_items` ADD FOREIGN KEY (`drone_id`) REFERENCES `drones` (`id`);

ALTER TABLE `drone_delivery_items` ADD FOREIGN KEY (`medication_id`) REFERENCES `medications` (`id`);

ALTER TABLE `drone_delivery_items` ADD FOREIGN KEY (`drone_delivery_id`) REFERENCES `drone_deliveries` (`id`);

INSERT INTO drones(serial_number, model, weight_limit, battery_level, state)
VALUES ('2049-4589', 'Lightweight', 200, 0.9, 'LOADING'),
       ('5432-0251', 'Middleweight', 300, 0.8, 'LOADING'),
       ('7890-3452', 'Heavyweight', 500, 0.9, 'LOADING'),
       ('2038-1024', 'Lightweight', 500, 0.6, 'LOADING'),
       ('8349-3940', 'Middleweight', 500, 0.2, 'IDLE');

INSERT INTO medications(name, weight, code, image_url)
VALUES ('Morphine', 140, 'MOR_1UE', 'https://picsum.photos/id/870/200/300'),
       ('Ampicillin', 120, 'AMP_5QE', 'https://picsum.photos/id/10/2500/1667'),
       ('Ibuprofen', 150, 'IBU_9UN', 'https://picsum.photos/id/29/4000/2670'),
       ('Aspirin', 100, 'ASP_U4I', 'https://picsum.photos/id/732/5000/3333');