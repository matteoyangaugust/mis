create database mis;

use mis;

create table system_user(
	sn int primary key auto_increment,
    name varchar(20) not null default '',
	username varchar(20) not null default '',
	password varchar(20) not null default '',
	role varchar(15) not null default 'ROLE_UNKNOWN'
);

create table process_category(
	sn int primary key auto_increment,
	name varchar(40) not null default '',
	relation varchar(100) not null default ''
);

create table material(
	sn int primary key auto_increment,
	identifier varchar(20) not null default '',
	name varchar(60) not null default '',
	quantity Double not null default 0,
	price Double not null default 0,
	last_update_time varchar(12) not null default '',
	unit int not null default 0,
	supplier int not null default 0,
	active int not null default 1
);

create table material_flowing_history(
	sn int primary key auto_increment,
	material_sn int not null default 0,
	buying_time varchar(8) not null default '',
	update_time varchar(12) not null default '',
	supplier_sn int not null default 0,
	quantity Double not null default 0,
	remain Double not null default 0,
	fee Double not null default 0
);

create table material_supplier(
	sn int primary key auto_increment,
	name varchar(100) not null default ''
);

create table component(
	sn int primary key auto_increment,
	process_category_sn int not null default 0,
	identifier varchar(20) not null default '',
	name varchar(30) not null default '',
	process_duration int not null default 0,
	process_yield int not null default 0,
	purchase_fee Double not null default 0,
	remain int not null default 0,
	active int not null default 1
);

create table component_flowing_history(
	sn int primary key auto_increment,
	component_sn int not null default 0,
	process_category_sn int not null default 0,
	process_amount int not null default 1,
	amount int not null default 0,
	update_time varchar(12) not null default '',
	purchase_fee Double not null default 0,
	total_duration int not null default 0,
	remain int not null default 0
);

create table product(
	sn int primary key auto_increment,
	identifier varchar(20) not null default '',
	name varchar(30) not null default '',
	remain int not null default 0,
	active int not null default 1
);

create table elements_of_component(
	sn int primary key auto_increment,
	material_sn int not null default 0,
	component_sn int not null default 0,
	material_amount double not null default 0
);

create table component_of_product(
	sn int primary key auto_increment,
	component_sn int not null default 0,
	product_sn int not null default 0,
	component_amount int not null default 0
);

create table system_main_menu(
	sn int primary key auto_increment,
	name varchar(10) not null default '',
	category varchar(10) not null default '',
	is_default int not null default 0
);

create table system_sub_menu(
	sn int primary key auto_increment,
	main_menu_sn int not null default 0,
	name varchar(10) not null default '',
	url varchar(200) not null default '',
	roles varchar(200) not null default '',
	is_default int not null default 0
);

create table product_flowing_history(
	sn int primary key auto_increment,
	product_sn int not null default 0,
	amount int not null default 0,
	update_time varchar(12) not null default '',
	remain int not null default 0,
	action int not null default 0
);
