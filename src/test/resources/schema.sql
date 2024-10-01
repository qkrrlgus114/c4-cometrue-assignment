CREATE TABLE member
(	member_pk BIGINT NOT NULL AUTO_INCREMENT,
     id	VARCHAR(30)	NOT NULL UNIQUE,
     password VARCHAR(100)	NOT NULL,
     email VARCHAR(30) NOT NULL  UNIQUE,
     phone_number VARCHAR(13) NOT NULL UNIQUE,
     nickname	VARCHAR(20)	NOT NULL UNIQUE,
     age	INTEGER NOT NULL,
     profile_image VARCHAR(200) NOT NULL,
     regist_date DATETIME,
     PRIMARY KEY (member_pk));

CREATE TABLE login_history
(	login_history_pk BIGINT NOT NULL AUTO_INCREMENT,
     member_pk BIGINT NOT NULL,
     login_date DATETIME NOT NULL,
     ip VARCHAR(20) NOT NULL,
     PRIMARY KEY (login_history_pk),
     FOREIGN KEY (member_pk) REFERENCES member(member_pk));