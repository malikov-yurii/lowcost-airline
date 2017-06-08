SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE cities;
TRUNCATE TABLE airports;
TRUNCATE TABLE aircraft_models;
TRUNCATE TABLE aircraft;
TRUNCATE TABLE flights;
TRUNCATE TABLE tariffs_details;
TRUNCATE TABLE tickets;
SET FOREIGN_KEY_CHECKS = 1;

ALTER TABLE users AUTO_INCREMENT 1;
ALTER TABLE roles AUTO_INCREMENT 1;
ALTER TABLE cities AUTO_INCREMENT 1;
ALTER TABLE airports AUTO_INCREMENT 1;
ALTER TABLE aircraft_models AUTO_INCREMENT 1;
ALTER TABLE aircraft AUTO_INCREMENT 1;
ALTER TABLE flights AUTO_INCREMENT 1;
ALTER TABLE tickets AUTO_INCREMENT 1;

INSERT INTO users (name, last_name, email, password, phone_number) VALUES
  ('Eduard', 'Eduardov', 'user@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380671234567'),
  ('Ivan', 'Ivanov', 'ivanov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380661234567'),
  ('Petr', 'Petrov', 'petrov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380911234567'),
  ('Ibragim', 'Ibragimov', 'ibragimov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380921234567'),
  ('Victor', 'Victorov', 'victorov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380931234567'),
  ('Robert', 'Black', 'black@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm',
   '+380941234567'),
  ('Hong', 'Wang', 'hong@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+380951234567'),
  ('Vsevolod', 'Vsevlastov', 'admin@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+380961234567');

INSERT INTO roles (role) VALUES
  ('ROLE_USER'),
  ('ROLE_ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1),
  (5, 1),
  (6, 1),
  (7, 1),
  (8, 1),
  (8, 2);

INSERT INTO cities (name, time_zone) VALUES
  ('Kyiv', 'Europe/Kiev'),
  ('London', 'Europe/London'),
  ('Rome', 'Europe/Rome');

INSERT INTO airports (name, city_id) VALUES
  ('Boryspil International Airport', 1),
  ('Heathrow Airport', 2),
  ('London Luton Airport', 2),
  ('Leonardo da Vinci International Airport', 3);

INSERT INTO aircraft_models (model_name, passenger_seats_quantity)
VALUES
  ('BOEING 737', 10),
  ('BOEING 767', 10);

INSERT INTO aircraft (name, model_id)
VALUES
  ('B737-1', 1),
  ('B767-2', 2),
  ('B767-3', 2);

INSERT INTO flights (departure_airport_id, arrival_airport_id, aircraft_id, departure_utc_datetime,
                     arrival_utc_datetime, initial_ticket_base_price, max_ticket_base_price, canceled)
VALUES
  (1, 2, 1, '2017-06-30 07:30', '2017-06-30 11:00', 30, 50, FALSE),

  (1, 2, 1, '2017-06-23 07:30', '2017-06-23 11:00', 30, 50, FALSE),
  (2, 1, 1, '2017-06-27 11:30', '2017-06-27 15:00', 30, 50, FALSE),
  (1, 3, 2, '2017-06-22 06:30', '2017-06-22 08:00', 40, 60, FALSE),
  (3, 1, 2, '2017-06-26 12:00', '2017-06-26 15:30', 40, 60, FALSE),
  (4, 1, 3, '2017-06-27 12:00', '2017-06-27 13:00', 20, 40, FALSE);

INSERT INTO tariffs_details (days_before_ticket_price_starts_to_grow, weight_of_time_growth_factor,
                             baggage_surcharge_over_max_base_ticket_price,
                             priority_registration_and_boarding_tariff, active)
VALUES
  (10, 0.5, 2, 7, TRUE),
  (30, 0.5, 1.11, 5.11, FALSE);

INSERT INTO tickets (flight_id, user_id, price, purchase_offsetdatetime, has_baggage, has_priority_registration,
                     passenger_name, passenger_last_name,
                     departure_airport_name, departure_city_name,
                     arrival_airport_name, arrival_city_name,
                     departure_utc_datetime, departure_time_zone,
                     arrival_offset_datetime, seat_number, status)
VALUES
  (1, 1, 30, '2017-05-30T07:30+03:00', FALSE, FALSE, 'Eduard', 'Eduardov', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-30T07:30', 'Europe/Kiev', '2017-06-30T12:00+01:00', 1, 'PAID'),

  (2, 2, 30, '2017-05-23T08:30+03:00', FALSE, FALSE, 'Ivan', 'Ivanov', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 1, 'PAID'),

  (2, 3, 83, '2017-05-23T09:30+03:00', TRUE, FALSE, 'Petr', 'Petrov', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 2, 'PAID'),

  (2, 4, 39, '2017-05-23T10:30+03:00', FALSE, TRUE, 'Ibragim', 'Ibragimov', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 3, 'PAID'),

  (2, 5, 92, '2017-05-23T11:30+03:00', TRUE, TRUE, 'Victor', 'Victorov', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 4, 'PAID'),

  (3, 2, 30, '2017-05-23T08:30+01:00', FALSE, FALSE, 'Ivan', 'Ivanov', 'Heathrow Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T11:30', 'Europe/London', '2017-06-27T18:00+03:00', 1, 'PAID'),

  (4, 6, 40, '2017-05-22T08:30+03:00', FALSE, FALSE, 'Robert', 'Black', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 1, 'PAID'),

  (4, 7, 103, '2017-05-22T09:30+03:00', TRUE, FALSE, 'Hong', 'Wang', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 2, 'PAID'),

  (5, 6, 40, '2017-05-22T08:30+01:00', FALSE, FALSE, 'Robert', 'Black', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 1, 'PAID'),

  (6, 8, 40, '2017-05-20T08:30+02:00', FALSE, FALSE, 'Abu', 'Kumar', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 1, 'PAID');
