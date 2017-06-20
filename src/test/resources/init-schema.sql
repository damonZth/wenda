
CREATE TABLE question (
  id            INT          NOT NULL,
  title         VARCHAR(255) NOT NULL,
  content       TEXT         NULL,
  user_id       INT          NOT NULL,
  created_date  DATETIME     NOT NULL,
  comment_count INT          NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE people (
  id int  NOT NULL ,
  name varchar(64) NOT NULL DEFAULT '',
  password varchar(128) NOT NULL DEFAULT '',
  salt varchar(32) NOT NULL DEFAULT '',
  head_url varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
);

