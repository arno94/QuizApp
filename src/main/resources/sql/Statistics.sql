drop table if exists statistics;

create table Statistics
(
    id int not null primary key,
    user_id int not null,
    answered_questions int,
    correct_answers int,
    finished_test int,
    score int default 0,
    foreign key (user_id) references user(id)
);