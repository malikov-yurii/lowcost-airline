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

ALTER TABLE users
  AUTO_INCREMENT 1;
ALTER TABLE roles
  AUTO_INCREMENT 1;
ALTER TABLE cities
AUTO_INCREMENT 1;
ALTER TABLE airports
  AUTO_INCREMENT 1;
ALTER TABLE aircraft_models
AUTO_INCREMENT 1;
ALTER TABLE aircraft
AUTO_INCREMENT 1;
ALTER TABLE flights
  AUTO_INCREMENT 1;
ALTER TABLE tickets
  AUTO_INCREMENT 1;

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
  ('Vsevolod', 'Vsevlastov', 'admin@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+380961234567'),

  ('Viola', 'White', 'white@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+380971234567'),
  ('Roza', 'Rozenko', 'rozenko@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+380981234567'),
  ('Moisha', 'Moishev', 'voishev@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+399241234567'),
  ('Usama', 'Nalad', 'nalad@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+391311234567'),
  ('Muhammad', 'Ali', 'ali@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+391651234567'),
  ('Kim', 'Basinger', 'kim@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+397361234567'),
  ('Emma', 'Watson', 'watson@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+394711234567'),
  ('Ron', 'Ronov', 'ronov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440711234567'),
  ('Luke', 'Lukov', 'lukov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440721234567'),
  ('Zack', 'Zackov', 'zakov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440731234567'),
  ('Rock', 'Jonson', 'jonson@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440741234567'),
  ('Lil', 'John', 'john@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440751234567'),
  ('Bruce', 'Brusov', 'brusov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440761234567'),
  ('Mark', 'Markov', 'markov@gmail.com', '$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm', '+440771234567');

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
  (8, 2),

  (9, 1),
  (10, 1),
  (11, 1),
  (12, 1),
  (13, 1),
  (14, 1),
  (15, 1),
  (16, 1),
  (17, 1),
  (18, 1),
  (19, 1),
  (20, 1),
  (21, 1),
  (22, 1);

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
  (4, 1, 3, '2017-06-27 12:00', '2017-06-27 13:00', 20, 40, FALSE),

  (1, 4, 3, '2017-06-21 08:00', '2017-06-21 11:00', 20, 40, FALSE),
  (1, 3, 3, '2017-06-22 08:00', '2017-06-21 11:11', 20, 40, TRUE),
  (1, 3, 3, '2017-04-22 08:00', '2017-06-21 11:11', 20, 40, FALSE )
;

INSERT INTO tariffs_details (days_before_ticket_price_starts_to_grow, weight_of_time_growth_factor,
                             baggage_surcharge_over_max_base_ticket_price,
                             priority_registration_and_boarding_tariff, active)
VALUES
  (10, 0.5, 2, 7, TRUE),
  (30, 0.5, 1.11, 5.11, FALSE);

INSERT INTO tickets (flight_id, user_id, price, purchase_offsetdatetime, with_baggage, with_priority_registration,
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
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 1, 'PAID'),


  (2, 9, 34, '2017-05-23T12:30+03:00', FALSE, FALSE, 'Viola', 'White', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 5, 'PAID'),

  (2, 10, 35, '2017-05-23T13:30+03:00', FALSE, FALSE, 'Roza', 'Rozenko', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 6, 'PAID'),

  (2, 15, 95, '2017-05-23T14:30+03:00', TRUE, TRUE, 'Emma', 'Watson', 'Boryspil International Airport', 'Kyiv',
      'Heathrow Airport', 'London', '2017-06-23T07:30', 'Europe/Kiev', '2017-06-23T12:00+01:00', 7, 'PAID'),

  (3, 3, 83, '2017-05-23T09:30+01:00', TRUE, FALSE, 'Petr', 'Petrov', 'Heathrow Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T11:30', 'Europe/London', '2017-06-27T18:00+03:00', 2, 'PAID'),

  (3, 4, 39, '2017-05-23T10:30+01:00', FALSE, TRUE, 'Ibragim', 'Ibragimov', 'Heathrow Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T11:30', 'Europe/London', '2017-06-27T18:00+03:00', 3, 'PAID'),

  (3, 5, 92, '2017-05-23T11:30+01:00', TRUE, TRUE, 'Victor', 'Victorov', 'Heathrow Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T11:30', 'Europe/London', '2017-06-27T18:00+03:00', 4, 'PAID'),

  (3, 9, 34, '2017-05-23T12:30+01:00', FALSE, FALSE, 'Viola', 'White', 'Heathrow Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T11:30', 'Europe/London', '2017-06-27T18:00+03:00', 5, 'PAID'),

  (4, 11, 49, '2017-05-22T10:30+03:00', FALSE, TRUE, 'Moisha', 'Moishev', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 3, 'PAID'),

  (4, 12, 112, '2017-05-22T11:30+03:00', TRUE, TRUE, 'Usama', 'Nalad', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 4, 'PAID'),

  (4, 13, 44, '2017-05-22T12:30+03:00', FALSE, FALSE, 'Muhammad', 'Ali', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 5, 'PAID'),

  (4, 14, 45, '2017-05-22T13:30+03:00', FALSE, FALSE, 'Kim', 'Basinger', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 6, 'PAID'),

  (4, 15, 115, '2017-05-22T14:30+03:00', TRUE, TRUE, 'Emma', 'Watson', 'Boryspil International Airport', 'Kyiv',
      'London Luton Airport', 'London', '2017-06-22T06:30', 'Europe/Kiev', '2017-06-22T09:00+01:00', 7, 'PAID'),

  (5, 7, 103, '2017-05-22T09:30+01:00', TRUE, FALSE, 'Hong', 'Wang', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 2, 'PAID'),

  (5, 11, 49, '2017-05-22T10:30+01:00', FALSE, TRUE, 'Moisha', 'Moishev', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 3, 'PAID'),

  (5, 12, 112, '2017-05-22T11:30+01:00', TRUE, TRUE, 'Usama', 'Naladan', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 4, 'PAID'),

  (5, 13, 44, '2017-05-22T12:30+01:00', FALSE, FALSE, 'Muhammad', 'Ali', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 5, 'PAID'),

  (5, 14, 45, '2017-05-22T13:30+01:00', FALSE, FALSE, 'Kim', 'Basinger', 'London Luton Airport', 'London',
      'Boryspil International Airport', 'Kyiv', '2017-06-26T12:00', 'Europe/London', '2017-06-26T18:30+03:00', 6, 'PAID'),

  (6, 16, 103, '2017-05-20T09:30+02:00', TRUE, FALSE, 'Ron', 'Ronov', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 2, 'PAID'),

  (6, 17, 49, '2017-05-20T10:30+02:00', FALSE, TRUE, 'Luke', 'Lukov', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 3, 'PAID'),

  (6, 18, 112, '2017-05-20T11:30+02:00', TRUE, TRUE, 'Zack', 'Zackov', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 4, 'PAID'),

  (6, 19, 44, '2017-05-20T12:30+02:00', FALSE, FALSE, 'Rock', 'Jonson', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 5, 'PAID'),

  (6, 20, 45, '2017-05-20T13:30+02:00', FALSE, FALSE, 'Lil', 'John', 'Leonardo da Vinci International Airport', 'Rome',
      'Boryspil International Airport', 'Kyiv', '2017-06-27T12:00', 'Europe/Rome', '2017-06-27T16:00+03:00', 6, 'PAID'),

  (7, 8, 40, '2017-05-20T08:30+01:00', FALSE, FALSE, 'Abu', 'Kumar', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 1, 'PAID'),

  (7, 16, 103, '2017-05-20T09:30+03:00', TRUE, FALSE, 'Ron', 'Ronov', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 2, 'PAID'),

  (7, 17, 49, '2017-05-20T10:30+03:00', FALSE, TRUE, 'Luke', 'Lukov', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 3, 'PAID'),

  (7, 18, 112, '2017-05-20T11:30+03:00', TRUE, TRUE, 'Zack', 'Zackov', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 4, 'PAID'),

  (7, 19, 44, '2017-05-20T12:30+03:00', FALSE, FALSE, 'Rock', 'Jonson', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 5, 'PAID'),

  (7, 20, 45, '2017-05-20T13:30+03:00', FALSE, FALSE, 'Lil', 'John', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 6, 'PAID'),

  (7, 21, 115, '2017-05-20T14:30+03:00', TRUE, TRUE, 'Bruce', 'Brusov', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 7, 'PAID'),

  (7, 1, 115, '2017-05-20T14:30+03:00', TRUE, TRUE, 'Viola', 'Eduardova', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-06-21T08:00', 'Europe/Kiev', '2017-06-21T13:00+02:00', 8, 'PAID'),

  (8, 1, 115, '2017-03-20T14:30+03:00', TRUE, TRUE, 'Viola', 'Eduardova', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-05-21T08:00', 'Europe/Kiev', '2017-05-21T13:00+02:00', 1, 'PAID'),

  (8, 1, 115, '2017-03-20T14:30+03:00', TRUE, TRUE, 'Eduard', 'Eduardov', 'Boryspil International Airport', 'Kyiv',
      'Leonardo da Vinci International Airport', 'Rome', '2017-05-21T08:00', 'Europe/Kiev', '2017-05-21T13:00+02:00', 1, 'PAID')

;

