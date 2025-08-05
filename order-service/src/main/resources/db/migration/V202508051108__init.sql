CREATE TABLE `orders`
(
    `id` bigint(20) PRIMARY KEY AUTO_INCREMENT,
    `order_number` varchar(255) DEFAULT NULL UNIQUE,
    `sku_code` varchar(255),
    `price` decimal(19, 2),
    `quantity` int(11)
);