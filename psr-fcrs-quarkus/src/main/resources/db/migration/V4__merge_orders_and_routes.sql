DROP TABLE fcrs.order;

DELETE FROM fcrs.route;

ALTER TABLE fcrs.route ADD COLUMN order_number VARCHAR(64) NOT NULL;

ALTER TABLE fcrs.route DROP CONSTRAINT route_store_id_start_date_time_key;

ALTER TABLE fcrs.route ADD CONSTRAINT route_item_unique_1 UNIQUE(store_id, start_date_time, order_number);

ALTER TABLE fcrs.route RENAME TO route_item;
