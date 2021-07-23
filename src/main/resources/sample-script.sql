--CLEAN DATABASE FOR CREATING NEW
drop table if exists order_details;

drop table if exists ratings;

drop table if exists user_roles;

drop table if exists products;
drop sequence if exists products_seq;

drop table if exists orders;
drop sequence if exists orders_seq;

drop table if exists users;
drop sequence if exists users_seq;

drop table if exists categories;
drop sequence if exists categories_seq;

drop table if exists brands;
drop sequence if exists brands_seq;

drop table if exists roles;
drop sequence if exists roles_seq;

--1. CREATE TABLE 'CATEGORIES'
create table categories (
    category_id integer
        constraint category_id_nn not null,
    category_name varchar(50)
        constraint category_name_nn not null,
    category_description varchar(255)
);

alter table categories
	add constraint categories_pk primary key(category_id);

create sequence categories_seq
    start with 1
    increment by 1;

--2. CREATE TABLE 'BRANDS'
create table brands (
	brand_id integer
		constraint brand_id_nn not null,
	brand_name integer
		constraint brand_name_nn not null
);

alter table brands
	add constraint brands_pk primary key(brand_id);

create sequence brands_seq
	start with 1
	increment by 1;

--3. CREATE TABLE 'ROLES'
create table roles (
	role_id integer
		constraint role_id_nn not null
);

alter table roles
	add constraint role_pk primary key(role_id);

create sequence roles_seq
	start with 1
	increment by 1;


--4. CREATE TABLE 'USERS'
create table users (
	user_id integer
		constraint user_id_nn not null,
	username varchar(50)
		constraint username_nn not null,
	user_password varchar(255)
		constraint password_nn not null,
	user_firstname varchar(50),
	user_lastname varchar(50),
	user_gender bool,
	user_dob date,
	user_email varchar(100),
	user_phoneNumber varchar(20)
);

alter table users
	add constraint users_pk primary key(user_id);

create sequence users_seq
	start with 1
	increment by 1;

--5. CREATE TABLE 'USER_ROLES'
create table user_roles (
    user_id integer
        constraint user_id_nn not null,
    role_id integer
        constraint role_id_nn not null
);

alter table user_roles
    add constraint user_roles_pk primary key(user_id, role_id);

alter table user_roles
    add constraint user_roles_users_fk foreign key(user_id) references users(user_id);

alter table user_roles
    add constraint user_roles_roles_fk foreign key(role_id) references roles(role_id);

--6. CREATE TABLE 'ORDERS'
create table orders (
	order_id integer
		constraint order_id_nn not null,
	user_id integer
		constraint user_id_nn not null,
	order_date date
		constraint order_date_nn not null,
	shippingAddress varchar(255)
);

alter table orders
	add constraint orders_pk primary key(order_id);

alter table orders
	add constraint orders_users_fk foreign key(user_id) references users(user_id);

create sequence orders_seq
	start with 1
	increment by 1;

--7. CREATE TABLE 'PRODUCTS'
create table products (
	product_id integer
		constraint product_id_nn not null,
	category_id integer
		constraint category_id_nn not null,
	brand_id integer
		constraint brand_id_nn not null,
	product_name varchar(255)
		constraint product_name_nn not null,
	product_price real,
	product_description varchar(255),
	product_image varchar(255)
);

alter table products
	add constraint products_pk primary key(product_id);

alter table products
	add constraint products_categories_fk foreign key(category_id) references categories(category_id);

alter table products
	add constraint products_brands_fk foreign key(brand_id) references brands(brand_id);

create sequence products_seq
	start with 1
	increment by 1;


--7. CREATE TABLE 'ORDER_DETAILS'
create table order_details (
	order_id integer
		constraint order_id_nn not null,
	product_id integer
		constraint product_id_nn not null,
	quantity integer,
	discount real
);

alter table order_details
	add constraint order_details_pk primary key(order_id, product_id);

alter table order_details
	add constraint details_orders_fk foreign key(order_id) references orders(order_id);

alter table order_details
	add constraint details_products_fk foreign key(product_id) references products(product_id);

--9. CREATE TABLE 'RATINGS'
create table ratings (
	user_id integer
		constraint user_id_nn not null,
	product_id integer
		constraint product_id_nn not null,
	points real,
	rating_content varchar(255),
	createdDate date,
	updatedDate date
);

alter table ratings
	add constraint ratings_pk primary key(user_id, product_id);

alter table ratings
	add constraint ratings_users_fk foreign key(user_id) references users(user_id);

alter table ratings
	add constraint ratings_products_fk foreign key(product_id) references products(product_id);

