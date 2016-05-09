CREATE TABLE SPEAKER (
  id         BIGINT auto_increment,
  name       VARCHAR(100) NOT NULL,
  github     VARCHAR(30),
  twitter    VARCHAR(30),
  avatar_url  VARCHAR(200),
  bio        TEXT,
  PRIMARY KEY (id)
);

CREATE TABLE SUBMISSION (
  id         BIGINT auto_increment,
  speaker_id BIGINT,
  title      VARCHAR(100),
  status     INTEGER,
  summary    TEXT,
  track INT,
  PRIMARY KEY (id)
);

