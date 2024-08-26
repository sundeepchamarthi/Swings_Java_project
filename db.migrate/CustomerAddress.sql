CREATE DATABASE maybank_java_assesment;

USE maybank_java_assesment;

 <!--If SCHEMA Already Exists create only table -->
CREATE TABLE Customers (
    id  INT PRIMARY KEY AUTO_INCREMENT,
    short_name VARCHAR(50) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    address1 VARCHAR(80),
    address2 VARCHAR(80),
    address3 VARCHAR(80),
    city VARCHAR(50),
    postal_code VARCHAR(20)
);