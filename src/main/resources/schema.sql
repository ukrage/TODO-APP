CREATE TABLE tasks (
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(256) NOT NULL,
    content VARCHAR(256),
    finished BOOLEAN
);
