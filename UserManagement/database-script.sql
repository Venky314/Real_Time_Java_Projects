-- Database Script for User Management System
-- Create database if not exists
CREATE DATABASE IF NOT EXISTS jrtp;
USE jrtp;

-- Create Country Master Table
CREATE TABLE IF NOT EXISTS country_master (
    country_id INT PRIMARY KEY,
    country_name VARCHAR(100) NOT NULL
);

-- Create States Master Table
CREATE TABLE IF NOT EXISTS states_master (
    state_id INT PRIMARY KEY,
    state_name VARCHAR(100) NOT NULL,
    country_id INT,
    FOREIGN KEY (country_id) REFERENCES country_master(country_id)
);

-- Create Cities Master Table
CREATE TABLE IF NOT EXISTS cities_master (
    city_id INT PRIMARY KEY,
    city_name VARCHAR(100) NOT NULL,
    state_id INT,
    FOREIGN KEY (state_id) REFERENCES states_master(state_id)
);

-- Create User Master Table
CREATE TABLE IF NOT EXISTS user_master (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    uname VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    pwd VARCHAR(255) NOT NULL,
    pwd_updated VARCHAR(3) DEFAULT 'NO',
    phno VARCHAR(20),
    country_id INT,
    state_id INT,
    city_id INT,
    created_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (country_id) REFERENCES country_master(country_id),
    FOREIGN KEY (state_id) REFERENCES states_master(state_id),
    FOREIGN KEY (city_id) REFERENCES cities_master(city_id)
);

-- Insert Static Data into Country Master
INSERT IGNORE INTO country_master VALUES(1, 'India');
INSERT IGNORE INTO country_master VALUES(2, 'USA');

-- Insert Static Data into States Master
INSERT IGNORE INTO states_master(state_id, state_name, country_id) VALUES(1, 'AP', 1);
INSERT IGNORE INTO states_master(state_id, state_name, country_id) VALUES(2, 'TG', 1);
INSERT IGNORE INTO states_master(state_id, state_name, country_id) VALUES(3, 'RI', 2);
INSERT IGNORE INTO states_master(state_id, state_name, country_id) VALUES(4, 'NJ', 2);

-- Insert Static Data into Cities Master
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (1, 'Guntur', 1);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (2, 'Ongole', 1);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (3, 'Hyderabad', 2);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (4, 'Warangal', 2);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (5, 'Providence', 3);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (6, 'New Port', 3);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (7, 'Trenton', 4);
INSERT IGNORE INTO cities_master(city_id, city_name, state_id) VALUES (8, 'Newark', 4);
