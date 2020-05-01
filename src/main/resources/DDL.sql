CREATE SCHEMA IF NOT EXISTS public;
--CREATING DATABASE
CREATE DATABASE COURSE_MANAGEMENT WITH OWNER postgres;
ALTER SCHEMA public OWNER TO postgres;

--SCHEMA
create table course 
	(
	id serial,
	title VARCHAR(50),
	status VARCHAR(50),
	PRIMARY KEY (id)
	);

alter table course owner to postgres;

create table users
	(
	id serial,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	email VARCHAR(50),
	user_role VARCHAR(50),
	status VARCHAR(50),
	course_id int,
	PRIMARY KEY (id),
	FOREIGN KEY (course_id)
		REFERENCES course (id)
	);

alter table users owner to postgres;

create table home_work 
	(
	id serial,
	title VARCHAR(50),
	text text,
	file_path VARCHAR(50),
	course_id int,
	PRIMARY KEY (id),
	FOREIGN KEY (course_id)
		REFERENCES course (id)
	);

alter table home_work owner to postgres;

create table solution 
	(
	id serial,
	text text,
	status VARCHAR(50),
	mark int,
	user_id int,
	home_work_id int,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id)
		REFERENCES users (id),
	FOREIGN KEY (home_work_id)
		REFERENCES home_work (id)
	);

alter table solution owner to postgres;

ALTER TABLE course ADD UNIQUE (title);

ALTER TABLE users ADD UNIQUE (email);

ALTER TABLE users ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE users ALTER COLUMN last_name SET NOT NULL;

ALTER TABLE home_work
    ALTER COLUMN title TYPE VARCHAR(200),
    ALTER COLUMN file_path TYPE VARCHAR(200);
    
    ALTER TABLE solution
    RENAME COLUMN home_work_id TO homework_id;

ALTER TABLE users
    ADD COLUMN password VARCHAR NOT NULL DEFAULT '123';

ALTER TABLE users ALTER COLUMN user_role TYPE VARCHAR(20); 
