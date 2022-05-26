CREATE TABLE IF NOT EXISTS tag (
    id          IDENTITY        NOT NULL PRIMARY KEY,
    tag_name    VARCHAR(30)     NOT NULL
);

create table gift_certificate
(
    id                  identity           not null primary key,
    name                varchar(30)        not null,
    description         varchar(200)       not null,
    duration            integer            not null,
    price               numeric(8, 2)      not null,
    create_date         timestamp          not null,
    last_update_date    timestamp          not null
);

create table gift_certificate_with_tags
(
    gift_certificate_id bigint
        references gift_certificate
            on update cascade on delete cascade,
    tag_id              bigint
        references tag
            on update cascade on delete cascade
);

create table "user" (
    id      identity          not null primary key,
    name    varchar(50)     not null
);

create table "order" (
    id                      identity          not null primary key,
    cost                    numeric(8, 2)   not null,
    purchase_time           timestamp       not null,
    gift_certificate_id     bigint
        references gift_certificate
        on update cascade on delete cascade,
    user_id                 bigint
        references "user"
        on update cascade on delete cascade
)