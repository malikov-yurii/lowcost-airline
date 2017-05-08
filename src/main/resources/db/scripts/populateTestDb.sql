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
  ('Eduard', 'Eduardov', 'eduardov@gmail.com', '1111', '+380671234567'),
  ('Ivan', 'Ivanov', 'ivanov@gmail.com', '1111', '+380661234567'),
  ('Petr', 'Petrov', 'petrov@gmail.com', '1111', '+380911234567'),
  ('Ibragim', 'Ibragimov', 'ibragimov@gmail.com', '1111', '+380921234567'),
  ('Victor', 'Victorov', 'victorov@gmail.com', '1111', '+380931234567'),
  ('Robert', 'Black', 'black@gmail.com', '1111', '+380941234567'),
  ('Hong', 'Wang', 'wang@gmail.com', '1111', '+380951234567'),
  ('Abu', 'Kumar', 'kumar@gmail.com', '1111', '+380961234567');

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
  ('B767-1', 2),
  ('B767-2', 2);

INSERT INTO flights (departure_airport_id, arrival_airport_id, aircraft_id, departure_localdatetime,
                     arrival_localdatetime, start_ticket_base_price, max_ticket_base_price)
VALUES
  (1, 2, 1, '2017-04-30 10:30', '2017-04-30 12:00', 30, 50),
  (1, 2, 1, '2017-04-23 10:30', '2017-04-23 12:00', 30, 50),
  (2, 1, 1, '2017-04-27 12:30', '2017-04-27 18:00', 30, 50),
  (1, 3, 2, '2017-04-22 09:30', '2017-04-22 09:00', 40, 60),
  (3, 1, 2, '2017-04-26 13:00', '2017-04-26 18:30', 40, 60),
  (4, 1, 3, '2017-04-27 14:00', '2017-04-27 16:00', 20, 40);

INSERT INTO tariffs_details (days_before_ticket_price_starts_to_grow, weight_of_time_growth_factor,
                             baggage_surcharge_over_ticket_max_base_ticket_price, priority_registration_tariff)
VALUES
  (10, 50, 2, 7);

INSERT INTO tickets (flight_id, user_id, price, purchase_offsetdatetime,
                     has_baggage, has_priority_registration,
                     passanger_full_name, departure_airport_full_name, arrival_airport_full_name,
                     departure_offsetdatetime, arrival_offsetdatetime, seat_number)
VALUES
  (1, 1, 30, '2017-03-30T07:30+03:00', FALSE, FALSE, 'Eduard Eduardov', 'Boryspil International Airport (Kyiv)',
      'Heathrow Airport (London)', '2017-04-30T10:30+03:00', '2017-04-30T12:00+01:00', 1),

  (2, 2, 30, '2017-03-23T08:30+03:00', FALSE, FALSE, 'Ivan Ivanov', 'Boryspil International Airport (Kyiv)',
      'Heathrow Airport (London)', '2017-04-23T10:30+03:00', '2017-04-23T12:00+01:00', 1),

  (2, 3, 83, '2017-03-23T09:30+03:00', TRUE, FALSE, 'Petr Petrov', 'Boryspil International Airport (Kyiv)',
      'Heathrow Airport (London)', '2017-04-23T10:30+03:00', '2017-04-23T12:00+01:00', 2),

  (2, 4, 39, '2017-03-23T10:30+03:00', FALSE, TRUE, 'Ibragim Ibragimov', 'Boryspil International Airport (Kyiv)',
      'Heathrow Airport (London)', '2017-04-23T10:30+03:00', '2017-04-23T12:00+01:00', 3),

  (2, 5, 92, '2017-03-23T11:30+03:00', TRUE, TRUE, 'Victor Victorov', 'Boryspil International Airport (Kyiv)',
      'Heathrow Airport (London)', '2017-04-23T10:30+03:00', '2017-04-23T12:00+01:00', 4),

  (3, 2, 30, '2017-03-23T08:30+01:00', FALSE, FALSE, 'Ivan Ivanov', 'Heathrow Airport (London)',
      'Boryspil International Airport (Kyiv)', '2017-04-27T12:30+01:00', '2017-04-27T18:00+03:00', 1),

  (4, 6, 40, '2017-03-22T08:30+03:00', FALSE, FALSE, 'Robert Black', 'Boryspil International Airport (Kyiv)',
      'London Luton Airport (London)', '2017-04-22T09:30+03:00', '2017-04-22T09:00+01:00', 1),

  (4, 7, 103, '2017-03-22T09:30+03:00', TRUE, FALSE, 'Hong Wang', 'Boryspil International Airport (Kyiv)',
      'London Luton Airport (London)', '2017-04-22T09:30+03:00', '2017-04-22T09:00+01:00', 2),

  (5, 6, 40, '2017-03-22T08:30+01:00', FALSE, FALSE, 'Robert Black', 'London Luton Airport (London)',
      'Boryspil International Airport (Kyiv)', '2017-04-26T13:00+01:00', '2017-04-26T18:30+03:00', 1),

  (6, 8, 40, '2017-03-20T08:30+02:00', FALSE, FALSE, 'Abu Kumar', 'Leonardo da Vinci International Airport (Rome)',
      'Boryspil International Airport (Kyiv)', '2017-04-27T14:00+02:00', '2017-04-27T16:00+03:00', 1);
