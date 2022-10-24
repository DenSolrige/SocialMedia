# Updoot SocialMedia App

Schema for the PostgreSQL Database:

create table account(
    accountId serial primary key,
    username varchar(40) not null unique,
    password varchar(40) not null
);

create table post(
    postId serial primary key,
    accountId int references account(accountid),
    title varchar(40) not null,
    content varchar(300) not null,
    date int not NULL,
    edited boolean DEFAULT false
);

create table reply(
    replyId serial primary key,
    postId int references post(postId),
    accountId int references account(accountId),
    content varchar(300) not null,
    date int not null,
    edited boolean DEFAULT false
); 

create table liked_post(
	accountId int references account(accountId),
	postId int references post(postId)
);

create table liked_reply(
	accountId int references account(accountId),
	replyId int references reply(replyId)
);

create table disliked_post(
	accountId int references account(accountId),
	postId int references post(postId)
);

create table disliked_reply(
	accountId int references account(accountId),
	replyId int references reply(replyId)
);
