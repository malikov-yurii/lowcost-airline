SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS airports;
DROP TABLE IF EXISTS aircraft_models;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS tariffs_details;
DROP TABLE IF EXISTS tickets;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
  id           BIGINT PRIMARY KEY AUTO_INCREMENT,
  name         VARCHAR(50)  ,
  last_name    VARCHAR(50)  ,
  email        VARCHAR(255)  UNIQUE,
  password     VARCHAR(255) ,
  phone_number VARCHAR(15)   UNIQUE
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE roles (
  id   BIGINT PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(255) UNIQUE
);

CREATE TABLE user_roles (
  user_id BIGINT ,
  role_id BIGINT ,
  CONSTRAINT user_rolex_idx UNIQUE (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE,
  FOREIGN KEY (role_id) REFERENCES roles (id)
    ON DELETE CASCADE
);

CREATE TABLE cities (
  id        BIGINT PRIMARY KEY AUTO_INCREMENT,
  name      VARCHAR(70) ,
  time_zone VARCHAR(50)
);

CREATE TABLE airports (
  id      BIGINT PRIMARY KEY AUTO_INCREMENT,
  name    VARCHAR(255) ,
  city_id BIGINT,
  FOREIGN KEY (city_id) REFERENCES cities (id)
);
CREATE UNIQUE INDEX airports_unique_name_idx
  ON airports (name);

CREATE TABLE aircraft_models (
  id                       BIGINT PRIMARY KEY AUTO_INCREMENT,
  model_name               VARCHAR(255) ,
  passenger_seats_quantity SMALLINT
);

CREATE TABLE aircraft (
  id       BIGINT PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(255) ,
  model_id BIGINT       ,
  FOREIGN KEY (model_id) REFERENCES aircraft_models (id)
);

/*
 * initial_ticket_base_price is base price for first ticket without any growth factors impact.
 * max_ticket_base_price indicates how much two growth factors (plane filling and time before departure) can impact the ticket price.
 */
CREATE TABLE flights (
  id                        BIGINT PRIMARY KEY AUTO_INCREMENT,
  departure_airport_id      BIGINT        ,
  arrival_airport_id        BIGINT        ,
  aircraft_id               BIGINT        ,
  departure_utc_datetime    DATETIME      ,
  arrival_utc_datetime      DATETIME      ,
  initial_ticket_base_price DECIMAL(8, 2) ,
  max_ticket_base_price     DECIMAL(8, 2) ,
  canceled                  BOOLEAN       ,
  FOREIGN KEY (departure_airport_id) REFERENCES airports (id),
  FOREIGN KEY (arrival_airport_id) REFERENCES airports (id),
  FOREIGN KEY (aircraft_id) REFERENCES aircraft (id)
);

/*
 * That is general airline pricing policy.
 * -days_before_ticket_price_starts_to_grows show us how soon before departure ticket price starts dto grow (days quantity).
 * -weight_of_time_growth_factor shows weight rate of time growth factor (0-100%).
 * -baggage_surcharge_over_ticket_max_base_ticket_price should be added dto max_ticket_base_price dto get baggage tariff (EUR)
 * -priority_registration_tariff is fee for priority registration(EUR)
 *
 *  Example:
 *  initial_ticket_base_price                            100 EUR
 *  max_ticket_base_price                                200 EUR
 *  total_seat_quantity                                  61 seats
 *  departure date                                       10.04.2017
 *  days_before_ticket_price_starts_to_grow              40
 *  weight_of_time_growth_factor                         0.4
 *  baggage_surcharge_over_ticket_max_base_ticket_price  2 EUR
 *  priority_registration_tariff                         7 EUR
 *
 *  Total growth potential for time and plane filling factors:  200 EUR - 100 EUR      = 100 EUR
 *  Growth potential for time factor                         :  100 EUR * 40%          = 40  EUR
 *  Growth potential for plane filling factor                :  100 EUR * (1 - 0.4) = 60  EUR
 *  Fixed baggage tariff for purchase                          :  200 EUR + 2 EUR        = 202 EUR
 *
 *  If departure day is on 10.04.2017 than price would start dto grow on 1.03.2017 (40 days before departure).
 *  Each day price would grow by  40 EUR / 40 days = 1 EUR. (Thus on 14.03.2017 price would be 114 EUR)
 *  With each single ticket purchased price would grow by 60EUR / (61 seats-1 seat) seats = 1 EUR (Ninth ticket price 108 EUR).
 *
 *  Thus ninth ticket price with baggage and priority registration on 14.03.2017 would be:
 *  + 100 EUR (start_ticket_base_price)
 *  + 8   EUR (because eight ticket already have bought)
 *  + 14  EUR (since 1.03.2017  dto 14.03.2017 price has been growing by 1 EUR each day)
 *  + 202 EUR (fixed baggage tariff
 *  = 314 UER
 **/
CREATE TABLE tariffs_details (
  id                        BIGINT PRIMARY KEY AUTO_INCREMENT,
  days_before_ticket_price_starts_to_grow             SMALLINT      ,
  weight_of_time_growth_factor                        DECIMAL(4, 4) ,
  baggage_surcharge_over_max_base_ticket_price DECIMAL(8, 2) ,
  priority_registration_and_boarding_tariff                        DECIMAL(8, 2) ,
  active               BOOLEAN
);


CREATE TABLE tickets (
  id                        BIGINT PRIMARY KEY AUTO_INCREMENT,
  flight_id                 BIGINT        ,
  user_id                   BIGINT        ,
  price                     DECIMAL(8, 2) ,
  purchase_offsetdatetime   VARCHAR(22)   ,
  with_baggage               BOOLEAN DEFAULT FALSE,
  with_priority_registration BOOLEAN DEFAULT FALSE,
  passenger_name         VARCHAR(50)  ,
  passenger_last_name    VARCHAR(50)  ,
  departure_airport_name    VARCHAR(255)  ,
  arrival_airport_name      VARCHAR(255)  ,
  departure_city_name       VARCHAR(70)   ,
  arrival_city_name         VARCHAR(70)   ,
  departure_utc_datetime    DATETIME      ,
  departure_time_zone                 VARCHAR(50)   ,
  arrival_offset_datetime    VARCHAR(22)   ,
  seat_number               SMALLINT ,
  status            VARCHAR(10),
  CONSTRAINT flight_seat_unique_constraint UNIQUE (flight_id, seat_number)
);






