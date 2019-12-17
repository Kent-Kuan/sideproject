CREATE TABLE user(
    id long not null primary key auto_increment,
    email char(20) not null unique,
    name char(20) default '',
    gender char(10) default 'unknown',
    isVIP tinyint(1) not null default 0,
    password varchar(20) not null,
    balance decimal default 500
);
CREATE TABLE orders(
    id long not null auto_increment,
    stage long not null,
    user_id long not null,
    numbers varchar(50),
    create_time timestamp default now(),
    winning tinyint(1) not null default 0,
    foreign key (user_id) references user(id),
    primary key (id, user_id)
);
CREATE TABLE winning(
    stage long not null,
    numbers varchar(50),
    create_time timestamp default now(),
    checked tinyint(1) not null default 0,
    primary key (stage)
);
CREATE INDEX idx_orders_user_id ON orders(user_id);