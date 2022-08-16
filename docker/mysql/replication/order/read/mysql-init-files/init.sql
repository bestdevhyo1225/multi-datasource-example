CREATE DATABASE `order` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE `order`.temp
(
    id varchar(100) PRIMARY KEY
);

CREATE USER slaveuser@'%' IDENTIFIED BY 'slavepassword';
GRANT ALL PRIVILEGES ON `order`.* TO slaveuser@'%' IDENTIFIED BY 'slavepassword';
