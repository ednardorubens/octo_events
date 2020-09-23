create table event (
	id bigint identity(1,1) not null,
    action integer not null,
    id_issue bigint not null,
    primary key (id),
    constraint FK_ISSUE_ID__EVENT foreign key (id_issue) references issue
);