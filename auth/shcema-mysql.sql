
DROP TABLE if EXISTS oauth_client_details;
DROP TABLE if EXISTS users;
DROP TABLE if EXISTS authorities;
DROP TABLE if EXISTS groups;
DROP TABLE if EXISTS group_members;
DROP TABLE if EXISTS group_authorities;


CREATE TABLE oauth_client_details(
client_id VARCHAR(50) PRIMARY KEY,
client_secret VARCHAR(50),
resource_ids VARCHAR(50),
scope VARCHAR(50),
authorized_grant_types VARCHAR(255),
web_server_redirect_uri VARCHAR(255),
authorities VARCHAR(255),
access_token_validity INT(5),
refresh_token_validity INT(5),
additional_information VARCHAR(255),
autoapprove VARCHAR(255)
);

-- @spring-security-core-4.2.8.RELEASE.jar!org.springframework.security.core.userdetails.jdbc.users.ddl
create table users(
username VARCHAR(50) not null primary key,
password VARCHAR(500) not null,
enabled boolean not null
);

create table authorities (
username VARCHAR(50) not null,
authority VARCHAR(50) not null,
constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

-- 角色表
CREATE TABLE groups(
id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
group_name VARCHAR(50)
);

-- 用户-角色 中间表
CREATE TABLE group_members(
id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50),
group_id INT(11)
);

-- 角色-权限 中间表
CREATE TABLE group_authorities(
id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
group_id INT(11),
authority VARCHAR(50)
);

-- 授权码记录表
CREATE TABLE oauth_code(
code varchar(50) DEFAULT NULL,
authentication blob
);
