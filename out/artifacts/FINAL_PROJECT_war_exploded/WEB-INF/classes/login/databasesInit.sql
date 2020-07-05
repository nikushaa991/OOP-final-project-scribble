USE SCRIBBLE;

DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       ID INT NOT NULL,
                       USERNAME VARCHAR(32) NOT NULL,
                       PASSWORD VARCHAR(256) NOT NULL
);

