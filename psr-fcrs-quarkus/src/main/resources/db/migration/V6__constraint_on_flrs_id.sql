ALTER TABLE fcrs.route_item DROP CONSTRAINT route_item_unique_1;

ALTER TABLE fcrs.route_item ADD CONSTRAINT route_item_unique_2 UNIQUE(flrs_id, start_date_time, order_number);
