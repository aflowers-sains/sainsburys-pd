GRANT USAGE ON SCHEMA fcrs TO psr;
GRANT ALL ON ALL SEQUENCES IN SCHEMA fcrs TO psr;
ALTER
DEFAULT PRIVILEGES IN SCHEMA fcrs
GRANT ALL ON SEQUENCES TO psr;

CREATE TABLE fcrs.route
(
    id                  BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    store_id            VARCHAR(16) NOT NULL,
    start_date_time     TIMESTAMP   NOT NULL,
    end_date_time       TIMESTAMP   NOT NULL,
    UNIQUE(store_id, start_date_time)
);

CREATE TABLE fcrs.order
(
    id            BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_number  VARCHAR(64) NOT NULL,
    fk_route_id   BIGINT      DEFAULT 0 NOT NULL,

    UNIQUE(order_number),

    FOREIGN KEY ("fk_route_id") REFERENCES fcrs.route (id)
);

ALTER TABLE fcrs.route OWNER to psr;
ALTER TABLE fcrs.order OWNER to psr;
