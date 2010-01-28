-- @copyright 2009 City of Bloomington, Indiana
-- @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
-- @author Cliff Ingham <inghamn@bloomington.in.gov>
create table people (
	id int unsigned not null primary key auto_increment,
	firstname varchar(128) not null,
	lastname varchar(128) not null,
	email varchar(255) not null
) engine=InnoDB;

create table users (
	id int unsigned not null primary key auto_increment,
	person_id int unsigned not null unique,
	username varchar(30) not null unique,
	password varchar(32),
	authenticationMethod varchar(40) not null default 'LDAP',
	foreign key (person_id) references people(id)
) engine=InnoDB;

create table roles (
	id int unsigned not null primary key auto_increment,
	name varchar(30) not null unique
) engine=InnoDB;
insert roles values(1,'Administrator');
insert roles values(2,'Editor');

create table user_roles (
	user_id int unsigned not null,
	role_id int unsigned not null,
	primary key (user_id,role_id),
	foreign key(user_id) references users (id),
	foreign key(role_id) references roles (id)
) engine=InnoDB;

create table cemeteries (
	id int unsigned not null primary key auto_increment,
	name varchar(128) not null,
	googleMapURL varchar(255)
) engine=InnoDB;

create table sections (
	id int unsigned not null primary key auto_increment,
	cemetery_id int unsigned not null,
	code varchar(5) not null,
	name varchar(128),
	foreign key (cemetery_id) references cemeteries(id)
) engine=InnoDB;

create table deeds (
	id int unsigned not null primary key auto_increment,
	section_id int unsigned,
	lot varchar(5),
	lastname1 varchar(20),
	firstname1 varchar(20),
	middleInitial1 varchar(20),
	lastname2 varchar(20),
	firstname2 varchar(20),
	middleInitial2 varchar(20),
	issueDate date,
	notes text,
	lot2 char(5),
	cemetery_id int unsigned,
	foreign key (section_id) references sections(id),
	foreign key (cemetery_id) references cemeteries(id)
) engine=InnoDB;

create table interments (
	id int(11) unsigned not null primary key auto_increment,
	section_id int unsigned,
	lot varchar(5),
	book varchar(4),
	pageNumber varchar(5),
	deceasedDate date,
	lastname varchar(20),
	firstname varchar(20),
	middleInitial varchar(20),
	birthPlace varchar(20),
	lastResidence varchar(20),
	age int unsigned,
	sex enum('M','F'),
	cemetery_id int unsigned,
	notes text,
	lot2 varchar(5),
	foreign key (section_id) references sections(id),
	foreign key (cemetery_id) references cemeteries(id)
) engine=InnoDB;
