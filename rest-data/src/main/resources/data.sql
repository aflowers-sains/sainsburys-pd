insert into route(id, name, date_time) values(1, 'one', now());
insert into route(id, name, date_time) values(2, 'two', PARSEDATETIME('01-04-2020 08:00', 'dd-MM-yyyy HH:mm'));
insert into route(id, name, date_time) values(3, 'three', PARSEDATETIME('01-08-2020 08:00', 'dd-MM-yyyy HH:mm'));
insert into route(id, name, date_time) values(4, 'four', PARSEDATETIME('01-11-2020 08:00', 'dd-MM-yyyy HH:mm'));
insert into route(id, name, date_time) values(5, 'five', PARSEDATETIME('01-04-2021 08:00', 'dd-MM-yyyy HH:mm'));

insert into stop(id, details, van_number, route_id) values(1, 'r2s1', 1, 2);
insert into stop(id, details, van_number, route_id) values(2, 'r2s2', 2, 2);
insert into stop(id, details, van_number, route_id) values(3, 'r2s3', 3, 2);
insert into stop(id, details, van_number, route_id) values(4, 'r2s4', 4, 2);
insert into stop(id, details, van_number, route_id) values(5, 'r2s5', 5, 2);