SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE credentials;
TRUNCATE TABLE cities;
TRUNCATE TABLE airports;
TRUNCATE TABLE flights;
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
ALTER TABLE flights
  AUTO_INCREMENT 1;
ALTER TABLE tickets
  AUTO_INCREMENT 1;

INSERT INTO users (name, last_name, email, phone_number) VALUES
  ('Eduard', 'Eduardov', 'eduardov@gmail.com', '+380671234567'),
  ('Ivan', 'Ivanov', 'ivanov@gmail.com', '+380661234567'),
  ('Petr', 'Petrov', 'petrov@gmail.com', '+380991234567'),
  ('Ibragim', 'Ibragimov', 'ibragimov@gmail.com', '+380991234567'),
  ('Victor', 'Victorov', 'victorov@gmail.com', '+380991234567'),
  ('Robert', 'Black', 'black@gmail.com', '+380991234567'),
  ('Hong', 'Wang', 'wang@gmail.com', '+380991234567'),
  ('Abu', 'Kumar', 'kumar@gmail.com', '+380991234567'),
  ('Viola', 'White', 'white@gmail.com', '+380991234567'),
  ('Roza', 'Rozenko', 'rozenko@gmail.com', '+380991234567'),
  ('Moisha', 'Moishev', 'voishev@gmail.com', '+399241234567'),
  ('Usama', 'Naladan', 'naladan@gmail.com', '+391311234567'),
  ('Muhammad', 'Ali', 'ali@gmail.com', '+391651234567'),
  ('Kim', 'Basinger', 'kim@gmail.com', '+397361234567'),
  ('Emma', 'Watson', 'watson@gmail.com', '+3947151234567'),
  ('Ron', 'Ronov', 'ronov@gmail.com', '+440711234567'),
  ('Luke', 'Lukov', 'lukov@gmail.com', '+440721234567'),
  ('Zack', 'Zackov', 'zakov@gmail.com', '+440731234567'),
  ('Rock', 'Jonson', 'jonson@gmail.com', '+440741234567'),
  ('Lil', 'John', 'john@gmail.com', '+440751234567'),
  ('Bruce', 'Brusov', 'brusov@gmail.com', '+440761234567'),
  ('Mark', 'Markov', 'markov@gmail.com', '+440771234567');

INSERT INTO roles (role) VALUES
  ('USER'),
  ('ADMIN');

INSERT INTO credentials (login, password, role_id) VALUES
  ('eduardov@gmail.com', '1111', 1),
  ('ivanov@gmail.com', '1111', 1),
  ('petrov@gmail.com', '1111', 1),
  ('ibragimov@gmail.com', '1111', 1),
  ('victorov@gmail.com', '1111', 1),
  ('black@gmail.com', '1111', 1),
  ('wang@gmail.com', '1111', 1),
  ('kumar@gmail.com', '1111', 1),
  ('white@gmail.com', '1111', 1),
  ('rozenko@gmail.com', '1111', 1),
  ('voishev@gmail.com', '1111', 1),
  ('naladan@gmail.com', '1111', 1),
  ('ali@gmail.com', '1111', 1),
  ('kim@gmail.com', '1111', 1),
  ('watson@gmail.com', '1111', 1),
  ('ronov@gmail.com', '1111', 1),
  ('lukov@gmail.com', '1111', 1),
  ('zakov@gmail.com', '1111', 1),
  ('jonson@gmail.com', '1111', 1),
  ('john@gmail.com', '1111', 1),
  ('brusov@gmail.com', '1111', 1),
  ('markov@gmail.com', '1111', 1),
  ('admin@gmail.com', '1111', 2);


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

INSERT INTO flights (departure_airport_id, arrival_airport_id, departure_localdatetime,
                     arrival_localdatetime, start_ticket_base_price, max_ticket_base_price, total_seat_quantity)
VALUES
  (1, 2, '2017-04-30 10:30:00', '2017-04-30 12:00:00', 30, 50, 10),
  (1, 2, '2017-04-23 10:30:00', '2017-04-23 12:00:00', 30, 50, 10),
  (2, 1, '2017-04-27 12:30:00', '2017-04-27 18:00:00', 30, 50, 10),

  (1, 3, '2017-04-22 09:30:00', '2017-04-22 09:00:00', 40, 60, 10),
  (3, 1, '2017-04-26 13:00:00', '2017-04-26 18:30:00', 40, 60, 10),

  (1, 4, '2017-04-21 11:00:00', '2017-04-21 13:00:00', 20, 40, 10),
  (4, 1, '2017-04-27 14:00:00', '2017-04-27 16:00:00', 20, 40, 10);

INSERT INTO tariffs_details (days_before_flight_price_starts_to_grow, weight_of_time_growth_factor,
                             baggage_surcharge_over_ticket_max_price, priority_registration_tariff)
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
  (2, 6, 34.00, '2017-03-23 12:30:00', 3, FALSE, FALSE),
  (2, 7, 35.00, '2017-03-23 13:30:00', 3, FALSE, FALSE),
  (2, 8, 95.00, '2017-03-23 14:30:00', 3, TRUE, TRUE),

  (3, 2, 30.00, '2017-03-23 08:30:00', 1, FALSE, FALSE),
  (3, 3, 83.00, '2017-03-23 09:30:00', 1, TRUE, FALSE),
  (3, 4, 39.00, '2017-03-23 10:30:00', 1, FALSE, TRUE),
  (3, 5, 92.00, '2017-03-23 11:30:00', 1, TRUE, TRUE),
  (3, 6, 34.00, '2017-03-23 12:30:00', 1, FALSE, FALSE),


  (4, 9,  40.00, '2017-03-22 08:30:00', 3, FALSE, FALSE),
  (4, 10, 103.00, '2017-03-22 09:30:00', 3, TRUE, FALSE),
  (4, 11, 49.00, '2017-03-22 10:30:00', 3, FALSE, TRUE),
  (4, 12, 112.00, '2017-03-22 11:30:00', 3, TRUE, TRUE),
  (4, 13, 44.00, '2017-03-22 12:30:00', 3, FALSE, FALSE),
  (4, 14, 45.00, '2017-03-22 13:30:00', 3, FALSE, FALSE),
  (4, 15, 115.00, '2017-03-22 14:30:00', 3, TRUE, TRUE),

  (5, 9,  40.00, '2017-03-22 08:30:00', 1, FALSE, FALSE),
  (5, 10, 103.00, '2017-03-22 09:30:00', 1, TRUE, FALSE),
  (5, 11, 49.00, '2017-03-22 10:30:00', 1, FALSE, TRUE),
  (5, 12, 112.00, '2017-03-22 11:30:00', 1, TRUE, TRUE),
  (5, 13, 44.00, '2017-03-22 12:30:00', 1, FALSE, FALSE),
  (5, 14, 45.00, '2017-03-22 13:30:00', 1, FALSE, FALSE),


  (6, 15,  40.00, '2017-03-20 08:30:00', 3, FALSE, FALSE),
  (6, 16, 103.00, '2017-03-20 09:30:00', 3, TRUE, FALSE),
  (6, 17, 49.00, '2017-03-20 10:30:00', 3, FALSE, TRUE),
  (6, 18, 112.00, '2017-03-20 11:30:00', 3, TRUE, TRUE),
  (6, 19, 44.00, '2017-03-20 12:30:00', 3, FALSE, FALSE),
  (6, 20, 45.00, '2017-03-20 13:30:00', 3, FALSE, FALSE),
  (6, 21, 115.00, '2017-03-20 14:30:00', 3, TRUE, TRUE),

  (7, 15,  40.00, '2017-03-20 08:30:00', 2, FALSE, FALSE),
  (7, 16, 103.00, '2017-03-20 09:30:00', 2, TRUE, FALSE),
  (7, 17, 49.00, '2017-03-20 10:30:00', 2, FALSE, TRUE),
  (7, 18, 112.00, '2017-03-20 11:30:00', 2, TRUE, TRUE),
  (7, 19, 44.00, '2017-03-20 12:30:00', 2, FALSE, FALSE),
  (7, 20, 45.00, '2017-03-20 13:30:00', 2, FALSE, FALSE);

