--create database kograf;
--create role postgres login;
--alter role postgres password 'postgres';
--grant connect on database kograf to postgres;
--grant all privileges on database kograf to postgres;


CREATE TABLE kograf_user (
  id               BIGSERIAL,
  full_name        VARCHAR(255) NOT NULL,
  email            VARCHAR(255) NOT NULL,
  phone            VARCHAR(255) NOT NULL,
  organization     VARCHAR(255) NOT NULL,
  academic_degree  VARCHAR(255),
  academic_title   VARCHAR(255),
  orc_id           VARCHAR(255),
  rinc_id          VARCHAR(255),
  password         VARCHAR(255),
  profile_picture  VARCHAR(255),
  role             VARCHAR(255),
  status           VARCHAR(255),
  PRIMARY KEY (id)
  --CONSTRAINT sqm_user_login_key UNIQUE (login)
);

CREATE TABLE kograf_conference (
  id               BIGSERIAL,
  title            VARCHAR(255) NOT NULL,
  organization     VARCHAR(255),
  description      VARCHAR(255),
  status           VARCHAR(255),
  start_date       TIMESTAMP NOT NULL,
  end_date         TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE kograf_section (
  id               BIGSERIAL,
  title            VARCHAR(255) NOT NULL,
  organization     VARCHAR(255),
  leader_name      VARCHAR(255),
  conference_id    int,
  PRIMARY KEY (id)
);

CREATE TABLE kograf_job (
  id               BIGSERIAL,
  title            VARCHAR(255) NOT NULL,
  description      VARCHAR(255),
  co_authors       VARCHAR(255),
  user_id          int,
  conference_id    int,
  section_id       int,
  date_time        TIMESTAMP NOT NULL,
  source_file      VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE kograf_comment (
  id               BIGSERIAL,
  job_id           int NOT NULL,
  user_id          int NOT NULL,
  message          VARCHAR(255) NOT NULL,
  date_time        TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE kograf_user_to_conference (
  user_id  BIGINT NOT NULL,
  conference_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, conference_id),
  CONSTRAINT fk_user_user_to_conference FOREIGN KEY (user_id) REFERENCES kograf_user,
  CONSTRAINT fk_conference_user_to_conference FOREIGN KEY (conference_id) REFERENCES kograf_conference
);

INSERT INTO kograf_user(full_name, email, phone, organization, academic_degree, academic_title, orc_id, rinc_id, password, profile_picture, role, status)
                VALUES ('fullname1', 'email1@email.com', '78901234455', 'organization', '', '', '', '', '$2a$12$5dyqBt.AnK0ti323LVRZzeHLSD5MYPx5TBhIwJPs.D8D8CJRY6eR6', '', 'MEMBER', 'ACTIVE');
INSERT INTO kograf_user(full_name, email, phone, organization, academic_degree, academic_title, orc_id, rinc_id, password, profile_picture, role, status)
                VALUES ('fullname2', 'email2@email.com', '78901234455', 'organization', '', '', '', '', '$2a$12$5dyqBt.AnK0ti323LVRZzeHLSD5MYPx5TBhIwJPs.D8D8CJRY6eR6', '', 'MEMBER', 'ACTIVE');
INSERT INTO kograf_user(full_name, email, phone, organization, academic_degree, academic_title, orc_id, rinc_id, password, profile_picture, role, status)
                VALUES ('fullname3', 'admin@email.com', '78901234455', 'organization', '', '', '', '', '$2a$12$5dyqBt.AnK0ti323LVRZzeHLSD5MYPx5TBhIwJPs.D8D8CJRY6eR6', '', 'ADMIN', 'ACTIVE');

INSERT INTO kograf_conference(title, organization, description, status, start_date, end_date)
                VALUES ('title1', 'organization', 'description', 'ON_HOLD', now(), now() + interval '14' day);
INSERT INTO kograf_conference(title, organization, description, status, start_date, end_date)
                VALUES ('title2', 'organization', 'description', 'ON_HOLD', now(), now() + interval '14' day);
INSERT INTO kograf_conference(title, organization, description, status, start_date, end_date)
                VALUES ('title3', 'organization', 'description', 'ON_HOLD', now(), now() + interval '14' day);

INSERT INTO kograf_user_to_conference(user_id, conference_id)
                VALUES (1, 1);
INSERT INTO kograf_user_to_conference(user_id, conference_id)
                VALUES (2, 1);
INSERT INTO kograf_user_to_conference(user_id, conference_id)
                VALUES (3, 1);

INSERT INTO kograf_section(title, organization, leader_name, conference_id)
                VALUES ('title1', 'organization', 'ЕкатСерг', 1);
INSERT INTO kograf_section(title, organization, leader_name, conference_id)
                VALUES ('title2', 'organization', 'ЕкатСерг', 1);
INSERT INTO kograf_section(title, organization, leader_name, conference_id)
                VALUES ('title3', 'organization', 'ЕкатСерг', 1);

INSERT INTO kograf_job(title, description, co_authors, user_id, conference_id, section_id, date_time, source_file)
                VALUES ('title1', 'description1', 'co_authors', 1, 1, 1, now(), 'sourcefile.');
INSERT INTO kograf_job(title, description, co_authors, user_id, conference_id, section_id, date_time, source_file)
                VALUES ('title2', 'description2', 'co_authors', 1, 1, 1, now(), 'sourcefile.');
INSERT INTO kograf_job(title, description, co_authors, user_id, conference_id, section_id, date_time, source_file)
                VALUES ('title2', 'description2', 'co_authors', 2, 1, 1, now(), 'sourcefile.');
INSERT INTO kograf_job(title, description, co_authors, user_id, conference_id, section_id, date_time, source_file)
                VALUES ('title2', 'description2', 'co_authors', 3, 1, 1, now(), 'sourcefile.');