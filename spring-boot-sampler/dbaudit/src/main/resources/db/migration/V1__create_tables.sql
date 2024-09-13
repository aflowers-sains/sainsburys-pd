CREATE TABLE dbaudit.audit
(
    id bigint not null generated always as identity primary key,
    message text not null,
    created_at timestamp with time zone not null
);
