drop table if exists questions;

create table Questions
(
    id int not null auto_increment primary key,
    question varchar(1000) null,
    answers varchar(1000) null,
    correct_answer varchar(1000) null,
    difficulty varchar(50) null
);
