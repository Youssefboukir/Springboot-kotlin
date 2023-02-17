CREATE TABLE IF NOT EXISTS `products`
(
    `sku`         VARCHAR(16)     NOT NULL
        CONSTRAINT pk_product_id PRIMARY KEY,
    `name`        VARCHAR(125)    NOT NULL,
    `description` VARCHAR(125),
    `price`       DECIMAL           NOT NULL,
    `created_at`  TIMESTAMP     NOT NULL,
    `updated_at`  TIMESTAMP     NOT NULL
);



