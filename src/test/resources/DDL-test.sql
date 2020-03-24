--SCHEMA
create table if not exists course
	(
	id serial,
	title VARCHAR(50),
	status VARCHAR(50),
	PRIMARY KEY (id)
	);

create table if not exists users
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

create table if not exists home_work
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

create table if not exists solution
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

ALTER TABLE course ADD UNIQUE (title);

ALTER TABLE users ADD UNIQUE (email);

ALTER TABLE users ALTER COLUMN first_name SET NOT NULL;
ALTER TABLE users ALTER COLUMN last_name SET NOT NULL;