CREATE TABLE user(
email char(20) not null primary key,
name char(20),
gender char(10),
isVIP tinyint(1) not null,
password varchar(20)
);