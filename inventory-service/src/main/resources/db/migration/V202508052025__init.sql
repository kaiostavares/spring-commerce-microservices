CREATE TABLE `inventory` (
    `id` bigint(21) PRIMARY KEY AUTO_INCREMENT,
    `sku_code` varchar(255) DEFAULT NULL,
    `quantity` int(11) DEFAULT NULL
);

INSERT INTO inventory (quantity, sku_code) VALUES
    (100, 'iphone_15_blue'),
    (200, 'xiaomi_redmi_note_12'),
    (150, 'samsung_galaxy_s24'),
    (250, 'motorola_edge_40');