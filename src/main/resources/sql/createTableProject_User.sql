create table project_user
(
    user_id    int8 not null,
    project_id int8 not null,
    foreign key (user_id) references users (id)
        on delete cascade,
    foreign key (project_id) references projects (id)
        on delete cascade
);