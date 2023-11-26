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
  status           VARCHAR(255),
  start_date       TIMESTAMP NOT NULL,
  end_date         TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE kograf_section (
  id               BIGSERIAL,
  title            VARCHAR(255) NOT NULL,
  organization     VARCHAR(255),
  leader_id        int,
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

CREATE TABLE kograf_user_to_section (
  user_id  BIGINT NOT NULL,
  section_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, section_id),
  CONSTRAINT fk_user_user_to_section FOREIGN KEY (user_id) REFERENCES kograf_user,
  CONSTRAINT fk_section_user_to_section FOREIGN KEY (section_id) REFERENCES kograf_section
);

CREATE TABLE kograf_user_to_conference (
  user_id  BIGINT NOT NULL,
  conference_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, conference_id),
  CONSTRAINT fk_user_user_to_conference FOREIGN KEY (user_id) REFERENCES kograf_user,
  CONSTRAINT fk_conference_user_to_conference FOREIGN KEY (conference_id) REFERENCES kograf_conference
);
