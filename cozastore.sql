cozastoreDROP DATABASE IF EXISTS cozastore;
CREATE DATABASE cozastore;
USE cozastore;

CREATE TABLE users(

      id int auto_increment,
      email varchar(255),
      password varchar(255),
      id_role int,
      
      primary key(id)
);

CREATE TABLE role(
	
    id int auto_increment,
    name varchar(255),
    
    primary key(id)
);

CREATE TABLE color(

	id int auto_increment,
    name varchar(255),
    
    primary key(id)
	
);

CREATE TABLE size(
	
	id int auto_increment,
    name varchar(255),
    
    primary key(id)
);

CREATE TABLE tag(
	
	id int auto_increment,
    name varchar(255),
    
    primary key(id)
);

CREATE TABLE country(
	
	id int auto_increment,
    name varchar(255),
    
    primary key(id)
);

CREATE TABLE category(
	
	id int auto_increment,
    name varchar(255),
    
    primary key(id)
);

CREATE TABLE productdetail(
	
	id_product_detail int auto_increment,
	id_color int,
	id_product int,
	id_size int,
	quantity int,
	description varchar(255),
	
	primary key(id_product_detail)
);

CREATE TABLE product(

	id int auto_increment,
	images varchar(255),
	title varchar(255),
	price double,
	id_category int,
	tags varchar(255),
	
	primary key(id)
);

CREATE TABLE orders(
	
	id int auto_increment,
	id_user int,
	total_price double,
	create_date datetime,
	
	primary key(id)
);

CREATE TABLE orderdetail(
	
	id_order_detail int auto_increment,
	id_product_detail int,
	id_user int,
	price double,
	quantity int,
	create_date datetime,
	
	primary key(id_order_detail)
);

CREATE TABLE comment(
	
	id int auto_increment,
	name varchar(255),
	content varchar(255),
	email varchar(255),
	website varchar(255),
	id_blog int,
	
	primary key(id)
);

CREATE TABLE blog(
	
	id int auto_increment,
	title varchar(255),
	content varchar(255),
	image varchar(255),
	create_date datetime,
	id_user int,
	tags varchar(255),
	
	primary key(id)
);

 
ALTER TABLE productdetail ADD CONSTRAINT FK_id_color_ProductDetail
FOREIGN KEY (id_color) REFERENCES color(id);

ALTER TABLE productdetail ADD CONSTRAINT FK_id_product_ProductDetail
FOREIGN KEY (id_product) REFERENCES product(id);

ALTER TABLE productdetail ADD CONSTRAINT FK_id_size_ProductDetail
FOREIGN KEY (id_size) REFERENCES size(id);

ALTER TABLE product ADD CONSTRAINT FK_id_category_Product
FOREIGN KEY (id_category) REFERENCES category(id);

ALTER TABLE orders ADD CONSTRAINT FK_id_user_Orders
FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE orderdetail ADD CONSTRAINT FK_id_product_detail_OrderDetail
FOREIGN KEY (id_product_detail) REFERENCES productdetail(id_product_detail);

ALTER TABLE orderdetail ADD CONSTRAINT FK_id_user_OrderDetail
FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE comment ADD CONSTRAINT FK_id_blog_Comment
FOREIGN KEY (id_blog) REFERENCES blog(id);

ALTER TABLE blog ADD CONSTRAINT FK_id_user_Blog
FOREIGN KEY (id_user) REFERENCES users(id);

ALTER TABLE users ADD CONSTRAINT FK_id_role_User
FOREIGN KEY (id_role) REFERENCES role(id);