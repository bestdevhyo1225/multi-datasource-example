CREATE DATABASE `order` character set utf8mb4 collate utf8mb4_general_ci;

CREATE TABLE `order`.temp
(
    id varchar(100) PRIMARY KEY
);

-- create masteruser and grant privileges
CREATE USER masteruser@'172.16.0.%' IDENTIFIED BY 'masterpassword';
GRANT ALL PRIVILEGES ON *.* TO masteruser@'172.16.0.%' IDENTIFIED BY 'masterpassword';

-- replication
GRANT REPLICATION SLAVE ON *.* TO slaveuser@'172.16.0.%' IDENTIFIED BY 'slavepassword';
