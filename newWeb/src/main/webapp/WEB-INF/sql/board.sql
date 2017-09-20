/*GRANT CONNECT,RESOURCE,UNLIMITED TABLESPACE TO boardadmin IDENTIFIED BY boardadmin;
ALTER USER boardadmin DEFAULT TABLESPACE USERS;
ALTER USER boardadmin TEMPORARY TABLESPACE TEMP;
CONNECT boardadmin/boardadmin */
/* 여기서 부터 시작 */ 

DROP TABLE MEMBER CASCADE CONSTRAINTS;
DROP TABLE COMMENTS CASCADE CONSTRAINTS;
DROP TABLE BOARD CASCADE CONSTRAINTS;
DROP TABLE AUTHORITIES CASCADE CONSTRAINTS;
DROP TABLE ATTACHFILE CASCADE CONSTRAINTS;
DROP TABLE ARTICLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_ATTACHFILE; 
DROP SEQUENCE SEQ_COMMENTS; 
DROP SEQUENCE SEQ_ARTICLE; 

DROP INDEX ix_authorities; 

commit; 

create table member (
    email varchar2(60) PRIMARY KEY,
    passwd varchar2(200) NOT NULL,
    name varchar2(20) NOT NULL,
    mobile varchar2(20), 
    regdate date
);

create table board (
    boardcd varchar2(20),
    boardnm varchar2(40) NOT NULL,
    boardnm_ko varchar2(40),
    constraint PK_BOARD PRIMARY KEY(boardcd)
);

create table article (
    articleno number,
    boardcd varchar2(20),
    title varchar2(200) NOT NULL,
    content clob NOT NULL,
    email varchar2(60),
    hit number,    
    regdate date,
    constraint PK_ARTICLE PRIMARY KEY(articleno),
    constraint FK_ARTICLE FOREIGN KEY(boardcd) REFERENCES board(boardcd)
);

create sequence SEQ_ARTICLE
increment by 1
start with 1;

create table comments (
    commentno number,
    articleno number,    
    email varchar2(60),    
    memo varchar2(4000) NOT NULL,
    regdate date, 
    constraint PK_COMMENTS PRIMARY KEY(commentno)
);

create sequence SEQ_COMMENTS
    increment by 1
    start with 1;

create table attachfile (
    attachfileno number,
    filename varchar2(50) NOT NULL,
    filetype varchar2(30),
    filesize number,
    articleno number,
    email varchar2(60),
    constraint PK_ATTACHFILE PRIMARY KEY(attachfileno)
);

create sequence SEQ_ATTACHFILE
increment by 1
start with 1;

CREATE TABLE authorities (
  email VARCHAR2(60) NOT NULL,
  authority VARCHAR2(20) NOT NULL,
  CONSTRAINT fk_authorities FOREIGN KEY(email) REFERENCES member(email)
);

CREATE UNIQUE INDEX ix_authorities ON authorities(email, authority); 

-- for test records  
insert into board values ('free', 'Free', '자유게시판');
INSERT INTO board VALUES ('member', 'Member', '회원게시판');
INSERT INTO board VALUES ('qna', 'QnA', 'Q&A');
INSERT INTO board VALUES ('ref', 'Reference', '자료실');


INSERT INTO member VALUES ('hong@gmail.org','1111','홍길동','010-1111-1111');
INSERT INTO member VALUES ('im@gmail.org','1111','임꺽정','010-1111-2222');

INSERT INTO authorities VALUES ('hong@gmail.org','ROLE_USER');
INSERT INTO authorities VALUES ('hong@gmail.org','ROLE_ADMIN');
INSERT INTO authorities VALUES ('im@gmail.org','ROLE_USER');

commit;