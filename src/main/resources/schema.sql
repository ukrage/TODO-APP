CREATE TABLE tasks (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(64) NOT NULL,
    content VARCHAR(256),
    finishedFlg BOOLEAN
);
