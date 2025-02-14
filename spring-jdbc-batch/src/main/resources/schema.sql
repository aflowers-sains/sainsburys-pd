create table if not exists customers (
 customer_id varchar not null,
 post_code varchar not null,
 c_type varchar not null,
 loading int not null,
 primary key (customer_id, post_code)
);