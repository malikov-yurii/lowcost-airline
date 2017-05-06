SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE time_zones;
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
ALTER TABLE time_zones
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
  ('Eduard', 'Eduardov', 'eduardov@gmail.com', '1111', '+380671234567'),
  ('Ivan', 'Ivanov', 'ivanov@gmail.com', '1111', '+380661234567'),
  ('Petr', 'Petrov', 'petrov@gmail.com', '1111', '+380911234567'),
  ('Ibragim', 'Ibragimov', 'ibragimov@gmail.com', '1111', '+380921234567'),
  ('Victor', 'Victorov', 'victorov@gmail.com', '1111', '+380931234567'),
  ('Robert', 'Black', 'black@gmail.com', '1111', '+380941234567'),
  ('Hong', 'Wang', 'wang@gmail.com', '1111', '+380951234567'),
  ('Abu', 'Kumar', 'kumar@gmail.com', '1111', '+380961234567'),

  ('Viola', 'White', 'white@gmail.com', '1111', '+380971234567'),
  ('Roza', 'Rozenko', 'rozenko@gmail.com', '1111', '+380981234567'),
  ('Moisha', 'Moishev', 'voishev@gmail.com', '1111', '+399241234567'),
  ('Usama', 'Naladan', 'naladan@gmail.com', '1111', '+391311234567'),
  ('Muhammad', 'Ali', 'ali@gmail.com', '1111', '+391651234567'),
  ('Kim', 'Basinger', 'kim@gmail.com', '1111', '+397361234567'),
  ('Emma', 'Watson', 'watson@gmail.com', '1111', '+394711234567'),
  ('Ron', 'Ronov', 'ronov@gmail.com', '1111', '+440711234567'),
  ('Luke', 'Lukov', 'lukov@gmail.com', '1111', '+440721234567'),
  ('Zack', 'Zackov', 'zakov@gmail.com', '1111', '+440731234567'),
  ('Rock', 'Jonson', 'jonson@gmail.com', '1111', '+440741234567'),
  ('Lil', 'John', 'john@gmail.com', '1111', '+440751234567'),
  ('Bruce', 'Brusov', 'brusov@gmail.com', '1111', '+440761234567'),
  ('Mark', 'Markov', 'markov@gmail.com', '1111', '+440771234567');

INSERT INTO roles (role) VALUES
  ('USER'),
  ('ADMIN');

INSERT INTO user_roles (user_id, role_id) VALUES
  (1, 1),
  (2, 1),
  (3, 1),
  (4, 1),
  (5, 1),
  (6, 1),
  (7, 1),
  (7, 2),
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

INSERT INTO time_zones (time_zone_offset) VALUES
  ('+3'),
  ('+1'),
  ('+2');

INSERT INTO cities (name, time_zone_id) VALUES
  ('Kyiv', 3),
  ('London', 1),
  ('Rome', 2);

INSERT INTO airports (name, city_id) VALUES
  ('Boryspil International Airport', 1),
  ('Heathrow Airport', 2),
  ('London Luton Airport', 2),
  ('Leonardo da Vinci International Airport', 3);

INSERT INTO aircraft_models (model_name, passanger_seats_quantity)
VALUES
  ('BOEING 737', 10),
  ('BOEING 767', 10);

INSERT INTO aircraft (name, model_id)
VALUES
  ('B747-1', 1),
  ('B767-1', 2),
  ('B767-3', 2);

INSERT INTO flights (departure_airport_id, arrival_airport_id, aircraft_id, departure_localdatetime,
                     arrival_localdatetime, start_ticket_base_price, max_ticket_base_price)
VALUES
  (1, 2, 1, '2017-04-30 10:30:00', '2017-04-30 12:00:00', 30, 50),
  (1, 2, 1, '2017-04-23 10:30:00', '2017-04-23 12:00:00', 30, 50),
  (2, 1, 1, '2017-04-27 12:30:00', '2017-04-27 18:00:00', 30, 50),
  (1, 3, 2, '2017-04-22 09:30:00', '2017-04-22 09:00:00', 40, 60),
  (3, 1, 2, '2017-04-26 13:00:00', '2017-04-26 18:30:00', 40, 60),
  (4, 1, 3, '2017-04-27 14:00:00', '2017-04-27 16:00:00', 20, 40),

  (1, 4, 3, '2017-04-21 11:00:00', '2017-04-21 13:00:00', 20, 40);

INSERT INTO tariffs_details (days_before_ticket_price_starts_to_grow, weight_of_time_growth_factor,
                             baggage_surcharge_over_ticket_max_base_ticket_price, priority_registration_tariff)
VALUES
  (10, 50, 2, 7);

INSERT INTO tickets (flight_id, user_id, price, purchase_localdatetime, time_zone_id,
                     baggage, priority_registration)
VALUES
  (1, 1, 30.00, '2017-03-30 07:30:00', 3, FALSE, FALSE),
  (2, 2, 30.00, '2017-03-23 08:30:00', 3, FALSE, FALSE),
  (2, 3, 83.00, '2017-03-23 09:30:00', 3, TRUE, FALSE),
  (2, 4, 39.00, '2017-03-23 10:30:00', 3, FALSE, TRUE),
  (2, 5, 92.00, '2017-03-23 11:30:00', 3, TRUE, TRUE),
  (3, 2, 30.00, '2017-03-23 08:30:00', 1, FALSE, FALSE),
  (4, 6, 40.00, '2017-03-22 08:30:00', 3, FALSE, FALSE),
  (4, 7, 103.00, '2017-03-22 09:30:00', 3, TRUE, FALSE),
  (5, 6, 40.00, '2017-03-22 08:30:00', 1, FALSE, FALSE),
  (6, 8, 40.00, '2017-03-20 08:30:00', 2, FALSE, FALSE),

  (2, 9, 34.00, '2017-03-23 12:30:00', 3, FALSE, FALSE),
  (2, 10, 35.00, '2017-03-23 13:30:00', 3, FALSE, FALSE),
  (2, 15, 95.00, '2017-03-23 14:30:00', 3, TRUE, TRUE),
  (3, 3, 83.00, '2017-03-23 09:30:00', 1, TRUE, FALSE),
  (3, 4, 39.00, '2017-03-23 10:30:00', 1, FALSE, TRUE),
  (3, 5, 92.00, '2017-03-23 11:30:00', 1, TRUE, TRUE),
  (3, 9, 34.00, '2017-03-23 12:30:00', 1, FALSE, FALSE),
  (4, 11, 49.00, '2017-03-22 10:30:00', 3, FALSE, TRUE),
  (4, 12, 112.00, '2017-03-22 11:30:00', 3, TRUE, TRUE),
  (4, 13, 44.00, '2017-03-22 12:30:00', 3, FALSE, FALSE),
  (4, 14, 45.00, '2017-03-22 13:30:00', 3, FALSE, FALSE),
  (4, 15, 115.00, '2017-03-22 14:30:00', 3, TRUE, TRUE),
  (5, 7, 103.00, '2017-03-22 09:30:00', 1, TRUE, FALSE),
  (5, 11, 49.00, '2017-03-22 10:30:00', 1, FALSE, TRUE),
  (5, 12, 112.00, '2017-03-22 11:30:00', 1, TRUE, TRUE),
  (5, 13, 44.00, '2017-03-22 12:30:00', 1, FALSE, FALSE),
  (5, 14, 45.00, '2017-03-22 13:30:00', 1, FALSE, FALSE),
  (6, 16, 103.00, '2017-03-20 09:30:00', 2, TRUE, FALSE),
  (6, 17, 49.00, '2017-03-20 10:30:00', 2, FALSE, TRUE),
  (6, 18, 112.00, '2017-03-20 11:30:00', 2, TRUE, TRUE),
  (6, 19, 44.00, '2017-03-20 12:30:00', 2, FALSE, FALSE),
  (6, 20, 45.00, '2017-03-20 13:30:00', 2, FALSE, FALSE),
  (7, 8, 40.00, '2017-03-20 08:30:00', 3, FALSE, FALSE),
  (7, 16, 103.00, '2017-03-20 09:30:00', 3, TRUE, FALSE),
  (7, 17, 49.00, '2017-03-20 10:30:00', 3, FALSE, TRUE),
  (7, 18, 112.00, '2017-03-20 11:30:00', 3, TRUE, TRUE),
  (7, 19, 44.00, '2017-03-20 12:30:00', 3, FALSE, FALSE),
  (7, 20, 45.00, '2017-03-20 13:30:00', 3, FALSE, FALSE),
  (7, 21, 115.00, '2017-03-20 14:30:00', 3, TRUE, TRUE);
