SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS credentials;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS airports;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS tariffs_details;
DROP TABLE IF EXISTS tickets;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  last_name    VARCHAR(255) NOT NULL,
  email        VARCHAR(255) NOT NULL UNIQUE ,
  phone_number VARCHAR(255) NOT NULL UNIQUE

);

CREATE TABLE roles (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(255) NOT NULL
);

CREATE TABLE credentials (
  login    VARCHAR(255) NOT NULL UNIQUE ,
  password VARCHAR(255) NOT NULL,
  role_id  BIGINT       NOT NULL,
  FOREIGN KEY (role_id) REFERENCES roles (id)
    ON DELETE CASCADE
);

CREATE TABLE time_zones (
  id               TINYINT PRIMARY KEY AUTO_INCREMENT,
  time_zone_offset VARCHAR(9) NOT NULL
);

CREATE TABLE cities (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(255) NOT NULL,
  time_zone_id TINYINT   NOT NULL,
  FOREIGN KEY (time_zone_id) REFERENCES time_zones (id)
);


CREATE TABLE airports (
  id      BIGINT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(255) NOT NULL,
  city_id BIGINT       NOT NULL,
  FOREIGN KEY (city_id) REFERENCES cities (id)
);

CREATE TABLE flights (
  id                      BIGINT PRIMARY KEY AUTO_INCREMENT,
  departure_airport_id    BIGINT        NOT NULL,
  arrival_airport_id      BIGINT        NOT NULL,
  departure_localdatetime DATETIME      NOT NULL,
  arrival_localdatetime   DATETIME      NOT NULL,
  start_ticket_base_price DECIMAL(8, 2) NOT NULL,
  max_ticket_base_price   DECIMAL(8, 2) NOT NULL,
  total_seat_quantity     SMALLINT      NOT NULL,
  FOREIGN KEY (departure_airport_id) REFERENCES airports (id),
  FOREIGN KEY (arrival_airport_id) REFERENCES airports (id)
);

CREATE TABLE tariffs_details (
  days_before_flight_price_starts_to_grow SMALLINT      NOT NULL,
  weight_of_time_growth_factor            DECIMAL(4, 2) NOT NULL,
  baggage_surcharge_over_ticket_max_price DECIMAL(8, 2) NOT NULL,
  priority_registration_tariff            DECIMAL(8, 2) NOT NULL
);


CREATE TABLE tickets (
  id                     BIGINT PRIMARY KEY AUTO_INCREMENT,
  flight_id              BIGINT        NOT NULL,
  user_id                BIGINT        NOT NULL,
  price                  DECIMAL(8, 2) NOT NULL,
  purchase_localdatetime DATETIME      NOT NULL,
  time_zone_id              TINYINT    NOT NULL,
  baggage                BOOLEAN       NOT NULL,
  priority_registration  BOOLEAN       NOT NULL,
  FOREIGN KEY (flight_id) REFERENCES flights (id),
  FOREIGN KEY (user_id) REFERENCES users (id)
);






