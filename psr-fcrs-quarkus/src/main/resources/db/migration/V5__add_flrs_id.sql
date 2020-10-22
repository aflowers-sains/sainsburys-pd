DELETE FROM fcrs.route_item;

ALTER TABLE fcrs.route_item ADD COLUMN flrs_id VARCHAR(128) NOT NULL;
