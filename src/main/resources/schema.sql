CREATE TABLE user(
email char(20) not null primary key,
name char(20),
gender char(10) default 'unknown',
isVIP tinyint(1) not null default 0,
password varchar(20)
);