CREATE TABLE "user"
(
   id           SERIAL PRIMARY KEY,
   username     VARCHAR (32) UNIQUE NOT NULL,
   password     VARCHAR (128),
   active       BOOLEAN NOT NULL DEFAULT TRUE, 
   creation_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now() 
);

CREATE TABLE "role"
(
   id     SERIAL PRIMARY KEY,
   name   VARCHAR (32) UNIQUE NOT NULL
);

CREATE TABLE user_role
(
   user_id   INTEGER,
   role_id   INTEGER,
   CONSTRAINT ck_user FOREIGN KEY (user_id)
   REFERENCES "user" (id) ON DELETE RESTRICT,
   CONSTRAINT ck_role FOREIGN KEY (role_id)
   REFERENCES "role" (id) ON DELETE RESTRICT
);

CREATE INDEX idx_user_creation_time
   ON "user" (creation_time DESC);

CREATE INDEX fk_user_role_user
   ON user_role (user_id);

CREATE INDEX fk_user_role_role
   ON user_role (role_id);

INSERT into role (name) VALUES ('ADMINISTRATOR');
INSERT into role (name) VALUES ('DEVELOPER');
INSERT into role (name) VALUES ('TESTER');
INSERT into "user" (username, password) 
	VALUES ('admin', '$2a$10$rtBiNfjOVx1HoCZ8yx.e7.IUbWhgF5xTQC.VbHDTEWYZGtrKjQwWq');
INSERT INTO user_role (user_id, role_id) VALUES(
	(select id from "user" where username = 'admin'),
	(select id from "role" where name = 'ADMINISTRATOR')
);

CREATE TABLE bug_state (id SERIAL PRIMARY KEY, name VARCHAR(32) UNIQUE NOT NULL);
INSERT INTO bug_state (name) values('OPEN'); 
INSERT INTO bug_state (name) values('WORKING'); 
INSERT INTO bug_state (name) values('TESTING'); 
INSERT INTO bug_state (name) values('CLOSE'); 

create table bug (id SERIAL PRIMARY KEY, bug_number VARCHAR(32) NOT NULL,
	 description VARCHAR(256), author INTEGER, executor INTEGER, modifier INTEGER, state INTEGER NOT NULL, 
	 parent INTEGER, creation_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
	 CONSTRAINT ck_author FOREIGN KEY (author) REFERENCES "user" (id) ON DELETE RESTRICT,  
	 CONSTRAINT ck_executor FOREIGN KEY (executor) REFERENCES "user" (id) ON DELETE RESTRICT,
	 CONSTRAINT ck_parent FOREIGN KEY (parent) REFERENCES bug (id) ON DELETE CASCADE,
	 CONSTRAINT ck_state FOREIGN KEY (state) REFERENCES bug_state (id) ON DELETE RESTRICT,
	 CONSTRAINT ck_modifier FOREIGN KEY (modifier) REFERENCES "user" (id) ON DELETE RESTRICT);

CREATE INDEX fk_bug_user_author ON bug (author); 	 
CREATE INDEX fk_bug_user_executor ON bug (executor);
CREATE INDEX fk_bug_user_modifier ON bug (modifier);
CREATE INDEX fk_bug_parent ON bug (parent); 	  	 
CREATE INDEX idx_bug_creation ON bug (creation_time DESC);
CREATE INDEX fk_bug_state ON bug (state);

create table attachment (id SERIAL PRIMARY KEY, bug_id INTEGER, author INTEGER,
	type VARCHAR(32), attachment BYTEA, creation_time TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
	CONSTRAINT ck_bug_id FOREIGN KEY (bug_id) REFERENCES bug (id) ON DELETE RESTRICT);

CREATE INDEX idx_attachment_creation ON attachment (creation_time DESC);
CREATE INDEX fk_attachment_bug ON attachment (bug_id);
CREATE INDEX fk_attacment_user_author ON attachment (author); 	 
CREATE TABLE state_role (id SERIAL PRIMARY KEY, state_id INTEGER NOT NULL, role_id INTEGER NOT NULL,
	CONSTRAINT ck_state_id FOREIGN KEY (state_id) REFERENCES bug_state (id) ON DELETE RESTRICT,
	CONSTRAINT ck_state_role FOREIGN KEY (role_id) REFERENCES role (id));
INSERT INTO state_role (state_id, role_id) values(
	(select id from bug_state where name = 'WORKING'), 
	(select id from role where name = 'DEVELOPER')  
);
INSERT INTO state_role (state_id, role_id) values(
	(select id from bug_state where name = 'TESTING'), 
	(select id from role where name = 'TESTER')  
);
INSERT INTO state_role (state_id, role_id) values(
	(select id from bug_state where name = 'CLOSE'), 
	(select id from role where name = 'TESTER')  
);
INSERT INTO state_role (state_id, role_id) values(
	(select id from bug_state where name = 'CLOSE'), 
	(select id from role where name = 'ADMINISTRATOR')  
);
CREATE INDEX fk_state_role_state ON state_role (state_id);
CREATE INDEX fk_state_role_role ON state_role (role_id);

CREATE TABLE state_trace (id SERIAL PRIMARY KEY, state_id INTEGER NOT NULL,
	next_state INTEGER NOT NULL,
	CONSTRAINT ck_state_trace_state FOREIGN KEY (state_id) REFERENCES bug_state (id),
	CONSTRAINT ck_state_trace_next FOREIGN KEY (next_state) REFERENCES bug_state (id));	
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'OPEN'), 
	(select id from bug_state where name = 'WORKING') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'OPEN'), 
	(select id from bug_state where name = 'TESTING') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'OPEN'), 
	(select id from bug_state where name = 'CLOSE') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'WORKING'), 
	(select id from bug_state where name = 'TESTING') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'WORKING'), 
	(select id from bug_state where name = 'CLOSE') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'TESTING'), 
	(select id from bug_state where name = 'WORKING') 
);
INSERT INTO state_trace (state_id, next_state) values(
	(select id from bug_state where name = 'TESTING'), 
	(select id from bug_state where name = 'CLOSE') 
);
CREATE INDEX fk_state_trace_state ON state_trace (state_id);
CREATE INDEX fk_state_trace_next ON state_trace (next_state);

	
