CREATE TABLE SPEAKER (
  id         BIGINT auto_increment,
  first_name VARCHAR(50) NOT NULL,
  last_name  VARCHAR(50) NOT NULL,
  twitter    VARCHAR(30),
  bio        TEXT,
  PRIMARY KEY (id)
);

CREATE TABLE SUBMISSION (
  id      BIGINT auto_increment,
  title   VARCHAR(100),
  status  INTEGER,
  notes   TEXT,
  summary TEXT,
  PRIMARY KEY (id)
);

INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Jürgen', 'Höller', 'springjuergen', 'Polishing code since 2002. Spring Framework gatekeeper. Taught Chuck Norris how to code.');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Josh', 'Long', 'starbuxman', 'Josh Long is the Spring developer advocate at Pivotal. Josh is the lead author on 4 books and instructor in one of Safari''s best-selling video series, all on Spring. Josh likes solutions that push the boundaries of the technologies that enable them. His interests include cloud-computing, business-process management, big-data and high availability, mobile computing and smart systems. He blogs on spring.io or on his personal blog and on Twitter (@starbuxman).');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Dave', 'Syer', 'david_syer', 'Founder and contributor to Spring Batch, lead of Spring Security OAuth, and an active contributor to Spring Integration, Spring Framework, Spring AMQP, Spring Security. Experienced, delivery-focused architect and development manager. Has designed and built successful enterprise software solutions using Spring, and implemented them in major institutions worldwide.');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Rob', 'Winch', 'rob_winch', 'Open source enthusiast, @SpringSecurity Project Lead, @SpringLDAP Project Lead, @SpringFramework commiter, Employed by @pivotal, Author');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Andy', 'Wilkinson', 'ankinson', 'Husband. Dad. Fair weather mountain biker. Developer at Pivotal/SpringSource by day, iOS developer @deftmethods by night.');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Brian', 'Clozel', 'bclozel', 'Spring Framework committer, Pivotal inc.');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Sébastien', 'Deleuze', 'sdeleuze', 'Spring framework commiter @ Pivotal and Dart Google Developer Expert');
INSERT INTO SPEAKER(FIRST_NAME, LAST_NAME, TWITTER, BIO) values ('Stéphane', 'Nicoll', 'snicoll', 'Stéphane has a thing for code quality and robustness. He''s been spreading the word for more than ten years while developing large scale Java enterprise applications in the geospatial, financial, or logistics sectors. An Apache Maven PMC member since 2006, he joined the core Spring Framework development team early 2014, being one of the main contributors to both Spring Framework and Spring Boot since. During his free time, he loves traveling around the world');
