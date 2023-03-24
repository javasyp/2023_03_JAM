# DB 생성
DROP DATABASE IF EXISTS `JAM`;
CREATE DATABASE `JAM`;
USE `JAM`;

# article 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SHOW TABLES;
DESC article;

# drop table article;

# article 테스트데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목 ',RAND()),
`body` = CONCAT('내용 ',RAND());

SELECT * FROM article;

# member 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(200) NOT NULL,
    `name` CHAR(100) NOT NULL
);

# drop table `member`;

SHOW TABLES;
DESC `member`;

# member 테스트데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = CONCAT('TestId ',RAND()),
loginPw = CONCAT('TestPw ',RAND()),
`name` = CONCAT('TestName ',RAND());

SELECT * FROM `member`;

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = 'test1';

SELECT COUNT(*) >= 1
FROM article
WHERE id = 1;

SELECT COUNT(*) > 0
FROM `member`
WHERE loginId = 'test2';

INSERT INTO article SET regDate = NOW() , updateDate = NOW() , title = 'aedsfg' , `body = 'cvbnawter';