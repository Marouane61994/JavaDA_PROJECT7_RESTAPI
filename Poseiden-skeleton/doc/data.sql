DROP TABLE IF EXISTS Users, RuleName, Rating, CurvePoint, Trade, BidList;

CREATE TABLE BidList (
  bid_list_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bid_quantity DOUBLE,
  ask_quantity DOUBLE,
  bid DOUBLE,
  ask DOUBLE,
  benchmark VARCHAR(125),
  bid_list_date TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (bid_list_id)
);

CREATE TABLE Trade (
  trade_id INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buy_quantity DOUBLE,
  sell_quantity DOUBLE,
  buy_price DOUBLE,
  sell_price DOUBLE,
  trade_date TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creation_name VARCHAR(125),
  creation_date TIMESTAMP,
  revision_name VARCHAR(125),
  revision_date TIMESTAMP,
  deal_name VARCHAR(125),
  deal_type VARCHAR(125),
  source_list_id VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (trade_id)
);

CREATE TABLE CurvePoint (
  id INT NOT NULL AUTO_INCREMENT,
  Curve_id INT,
  as_of_date TIMESTAMP,
  term DOUBLE,
  value DOUBLE,
  creation_date TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE Rating (
  id INT NOT NULL AUTO_INCREMENT,
  moodys_rating VARCHAR(125),
  sandprating VARCHAR(125),
  fitch_rating VARCHAR(125),
  order_number INT,
  PRIMARY KEY (id)
);

CREATE TABLE RuleName (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sql_str VARCHAR(125),
  sql_part VARCHAR(125),
  PRIMARY KEY (id)
);

CREATE TABLE Users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(125) unique not NULL,
  password VARCHAR(125) not NULL,
  fullname VARCHAR(125) not NULL,
  role VARCHAR(125) not NULL,
  PRIMARY KEY (id)
);

INSERT INTO Users(fullname, username, password, role)
VALUES ("Administrator", "admin", "$2a$10$Wo2NwoQNdXTtV4KoJwcaQ.w8W5b1ZRcJGcZaPHpZ/O3E3kjEmNRTS", "ADMIN");-- Mot de passe : Password1@ --

INSERT INTO Users(fullname, username, password, role)
VALUES ("User", "user", "$2a$10$Wo2NwoQNdXTtV4KoJwcaQ.w8W5b1ZRcJGcZaPHpZ/O3E3kjEmNRTS", "USER");-- Mot de passe : Password1@ --