##################################################################
###                                                            ###
### Author: Georgios Adamidis                                  ###
### Date: March 23th, 2026                                     ###
### Version: 1.0                                               ###
### Description: SQL schema for WarehouseStockFlow application ###
###                                                            ###
##################################################################

CREATE SCHEMA IF NOT EXISTS warehouse_stockflow;

SET NAMES 'UTF8MB4';

SET TIME_ZONE = '+2:00';

USE warehouse_stockflow;

DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) DEFAULT NULL,
    address     VARCHAR(255) DEFAULT NULL,
    phone       VARCHAR(30) DEFAULT NULL,
    title       VARCHAR(50) DEFAULT NULL,
    bio         VARCHAR(255) DEFAULT NULL,
    enabled     BOOLEAN DEFAULT TRUE, -- account is active
    non_locked  BOOLEAN DEFAULT TRUE,
    using_mfa   BOOLEAN DEFAULT FALSE,
    created_at  DATETIME DEFAULT CURRENT_TIMESTAMP,
    image_url   VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

DROP TABLE IF EXISTS Roles;

CREATE TABLE Roles (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    permission  VARCHAR(255) NOT NULL, -- user:read,user:delete
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

INSERT INTO Roles (name, permission)
VALUES  ('ROLE_USER','READ:USER,READ:PRODUCT,READ:CATEGORY'),
        ('ROLE_MANAGER','READ:USER,READ:PRODUCT,READ:CATEGORY,UPDATE:USER,UPDATE:PRODUCT,UPDATE:CATEGORY'),
        ('ROLE_ADMIN','READ:USER,READ:PRODUCT,READ:CATEGORY,CREATE:USER,CREATE:PRODUCT,CREATE:CATEGORY,UPDATE:USER,UPDATE:PRODUCT,UPDATE:CATEGORY'),
        ('ROLE_SYSADMIN','READ:USER,READ:PRODUCT,READ:CATEGORY,CREATE:USER,CREATE:PRODUCT,CREATE:CATEGORY,UPDATE:USER,UPDATE:PRODUCT,UPDATE:CATEGORY,DELETE:USER,DELETE:PRODUCT,DELETE:CATEGORY');

DROP TABLE IF EXISTS UserRoles;

CREATE TABLE UserRoles (
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    role_id BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

DROP TABLE IF EXISTS Events;

CREATE TABLE Events (
    id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK (type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE', 'ACCOUNT_LOCKED', 'ACCOUNT_UNLOCKED', 'USER_CREATED', 'USER_DELETED')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

INSERT INTO Events (type, description)
VALUES
    ('LOGIN_ATTEMPT','You tried to log in to your account'),
    ('LOGIN_ATTEMPT_FAILURE','A failed login attempt was made on your account'),
    ('LOGIN_ATTEMPT_SUCCESS','A successful login attempt was made on your account'),
    ('PROFILE_UPDATE','Your profile information was updated'),
    ('PROFILE_PICTURE_UPDATE','Your profile picture was updated'),
    ('ROLE_UPDATE','Your user role was updated'),
    ('ACCOUNT_SETTINGS_UPDATE','Your account settings were updated'),
    ('PASSWORD_UPDATE','Your password was updated'),
    ('MFA_UPDATE','Your multi-factor authentication settings were updated'),
    ('ACCOUNT_LOCKED','Your account was locked due to multiple failed login attempts'),
    ('ACCOUNT_UNLOCKED','Your account was unlocked'),
    ('USER_CREATED','A new user account was created'),
    ('USER_DELETED','Your user account was deleted');

DROP TABLE IF EXISTS UserEvents;

CREATE TABLE UserEvents (
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT UNSIGNED NOT NULL,
    event_id   BIGINT UNSIGNED NOT NULL,
    device     VARCHAR(100) DEFAULT NULL,
    ip_address VARCHAR(100) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (id) ON DELETE RESTRICT ON UPDATE CASCADE
);

DROP TABLE IF EXISTS AccountVerifications;

CREATE TABLE AccountVerifications (
    id      BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    url     VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS ResetPasswordVerifications;

CREATE TABLE ResetPasswordVerifications (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    url             VARCHAR(255) NOT NULL,
    expiration_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE (url)
);

DROP TABLE IF EXISTS TwoFactorVerifications;

CREATE TABLE TwoFactorVerifications (
    id              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT UNSIGNED NOT NULL,
    code            VARCHAR(10) NOT NULL,
    expiration_date DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_TwoFactorVerifications_Code UNIQUE (code)
);












