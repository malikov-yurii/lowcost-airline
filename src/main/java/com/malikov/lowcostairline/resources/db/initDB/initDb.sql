SET FOREIGN_KEY_CHECKS = 0;
drop table if exists roles;
drop table if exists users;
drop table if exists credentials;
drop table if exists cities;
drop table if exists airports;
drop table if exists flights;
drop table if exists tickets;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  email        VARCHAR(255) NOT NULL,
  phone_number VARCHAR(255) NOT NULL

);

CREATE TABLE roles (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(255) NOT NULL
);

CREATE TABLE credentials (
  login    VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role_id  BIGINT          NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id)
    ON DELETE CASCADE
);

CREATE TABLE cities (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  time_zone VARCHAR(9)   NOT NULL
);

CREATE TABLE airports (
  id      BIGINT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(255) NOT NULL,
  city_id BIGINT NOT NULL,
  role_id BIGINT          NOT NULL,
  FOREIGN KEY (city_id) REFERENCES cities (id)
);

CREATE TABLE flights (
  id                   BIGINT PRIMARY KEY AUTO_INCREMENT,
  departire_timestamp  TIMESTAMP     NOT NULL,
  arrival_timestamp    TIMESTAMP     NOT NULL,
  departure_airport_id BIGINT        NOT NULL,
  arrival_airport_id   BIGINT        NOT NULL,
  start_ticket_price   DECIMAL(8, 2) NOT NULL,
  end_ticket_price     DECIMAL(8, 2) NOT NULL,
  total_seat_quantity  SMALLINT      NOT NULL,
  free_seat_quantity   SMALLINT      NOT NULL,
  FOREIGN KEY (departure_airport_id) REFERENCES airports (id),
  FOREIGN KEY (arrival_airport_id) REFERENCES airports (id)
);

CREATE TABLE tickets (
  id                    BIGINT PRIMARY KEY AUTO_INCREMENT,
  user_id               BIGINT        NOT NULL,
  flight_id             BIGINT        NOT NULL,
  price                 DECIMAL(8, 2) NOT NULL,
  baggage_included      BOOLEAN       NOT NULL,
  priority_registration BOOLEAN       NOT NULL,
  FOREIGN KEY (flight_id) REFERENCES flights (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);






