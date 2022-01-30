/*
 * Copyright (c) 2021.
 * All Rights Reserved.
 * Created by:
 * Name: Arkadiusz Remplewicz
 * Index Number: 224413
 * E-mail: arkadiusz.remplewicz@gmail.com
 * Git-Hub Username: rempek99
 */

-- USERS
INSERT INTO users (id, username, password, enabled) VALUES (-1, 'thomas', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, username, password, enabled) VALUES (-2, 'susan', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, username, password, enabled) VALUES (-3, 'markus', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, username, password, enabled) VALUES (-4, 'rosie', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', false);
INSERT INTO users (id, username, password, enabled) VALUES (-5, 'marry', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);

-- ROLES
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-1, -1, 'USER', false);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-2, -2, 'USER', true);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-3, -3, 'USER', true);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-4, -4, 'USER', true);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-5, -5, 'USER', true);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-6, -1, 'ADMIN', true);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-7, -2, 'ADMIN', false);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-8, -3, 'ADMIN', false);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-9, -4, 'ADMIN', false);
INSERT INTO user_authorities(id, user_id, authority, enabled) VALUES (-10, -5, 'ADMIN', false);

--  USER INFOS
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-1, 24, 'Thomas', 'MALE', 'Johnson', -1);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-2, 25, 'Susan', 'FEMALE', 'Breaks', -2);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-3, 26, 'Markus', 'MALE', 'Darts', -3);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-5, 28, 'Marry', 'FEMALE', 'Jane', -5);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-4, 27, 'Rosie', 'FEMALE', 'Black', -4);

-- EVENT LOCATIONS
INSERT INTO public.event_location (id, latitude, longitude, name) VALUES (-1, 52.76, 19.55, 'Mochowo');
INSERT INTO public.event_location (id, latitude, longitude, name) VALUES (-2, 54.37, 18.62, 'Gdansk');
INSERT INTO public.event_location (id, latitude, longitude, name) VALUES (-3, 51.11, 17.01, 'Wroclaw');
INSERT INTO public.event_location (id, latitude, longitude, name) VALUES (-4, 50.05, 19.95, 'Krakow');
INSERT INTO public.event_location (id, latitude, longitude, name) VALUES (-5, 51.77, 19.45, 'Lodz');

-- CROWDING EVENTS
INSERT INTO public.crowding_event(id, description, slots, event_date, title, location_id, organizer_id, version) VALUES (-1, 'Stworzyłem coś swojego', 5, '2022-03-12 12:00:00+01', 'Moje pierwsze wydarzenie', -1, -2, 1);
INSERT INTO public.crowding_event(id, description, slots, event_date, title, location_id, organizer_id, version) VALUES (-2, 'Hej! Zbieram osoby chętne na wspólny rejs statkiem, mam wolne 20 biletów. Zapraszam', 20, '2022-02-13 13:00:00+01', 'Rejs Statkiem', -2, -1, 1);
INSERT INTO public.crowding_event(id, description, slots, event_date, title, location_id, organizer_id, version) VALUES (-3, 'Cześć. Jestem nowy we Wrocławiu. Szukam ekipy, która pokaże mi kilka fajnych miejsc.', 10, '2022-04-14 14:00:00+01', 'Spacer po starówce', -3, -3, 1);
INSERT INTO public.crowding_event(id, description, slots, event_date, title, location_id, organizer_id, version) VALUES (-4, 'Witam :) Zbliża się majówka, czy jest ktoś chętny wyskoczyć na piwko?', 15, '2022-05-01 15:00:00+01', 'Piknik w plenerze', -4, -4, 1);
INSERT INTO public.crowding_event(id, description, slots, event_date, title, location_id, organizer_id, version) VALUES (-5, 'Konkursy, zdjęcia, fajna muzyka. Wszystkie marki miło widziane :)', 50, '2021-12-16 16:00:00+01', 'Zlot samochodowy', -1, -5, 1);

-- CROWDING EVENTS PARTICIPANTS
INSERT INTO public.crowding_event_participants(crowding_event_id, participants_id) VALUES (-1, -3);
INSERT INTO public.crowding_event_participants(crowding_event_id, participants_id) VALUES (-1, -4);