drop table if exists questions;

create table Questions
(
    id int not null primary key,
    question varchar(255) null,
    answers varchar(255) null,
    correct_answer varchar(255) null,
    difficulty varchar(50) null
);