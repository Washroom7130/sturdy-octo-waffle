-- 1. Create the database
CREATE DATABASE IF NOT EXISTS test_springboot;

-- 2. Use the database
USE test_springboot;

-- 3. Create the User table
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- 4. Create the Blog table with foreign key on username
CREATE TABLE Blog (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    content TEXT,
    FOREIGN KEY (username) REFERENCES User(username)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- 5. Create MySQL user with random password
-- Replace 'abcDEF1234' with an actual secure random password
CREATE USER 'test_springboot'@'localhost' IDENTIFIED BY 'abcDEF1234';

-- 6. Grant all privileges on the database
GRANT ALL PRIVILEGES ON test_springboot.* TO 'test_springboot'@'localhost';

-- 7. Apply the privilege changes
FLUSH PRIVILEGES;
