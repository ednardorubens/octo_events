create table issue (
	id bigint not null,
    number bigint not null,
    title varchar(255) not null,
    body varchar(255) not null,
    created_at timestamp not null,
    closed_at timestamp,
    updated_at timestamp,
    primary key (id),
    constraint UK_ISSUE_NUMBER unique (number)
);