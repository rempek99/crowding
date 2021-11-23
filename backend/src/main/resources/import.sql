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
INSERT INTO users (id,  username, password, enabled) VALUES (-1,  'thomas', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id,  username, password, enabled) VALUES (-2,  'susan', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id,  username, password, enabled) VALUES (-3,  'markus', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id,  username, password, enabled) VALUES (-4,  'rosie', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', false);
INSERT INTO users (id, username, password, enabled) VALUES (-5,  'marry', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);

-- ROLES
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-1,-1,'USER', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-2,-2,'USER', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-3,-3,'USER', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-4,-4,'USER', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-5,-5,'USER', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-6,-1,'ADMIN', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-7,-2,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-8,-3,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-9,-4,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-10,-5,'ADMIN', false);

--  USER INFOS
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-1, 24, 'Thomas', 'MALE', 'Johnson', -1);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-2, 25, 'Susan', 'FEMALE', 'Breaks', -2);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-3, 26, 'Markus', 'MALE', 'Darts', -3);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-5, 28, 'Marry', 'FEMALE', 'Jane', -5);
INSERT INTO public.user_info (id, age, firstname, gender, surname, user_id) VALUES (-4, 27, 'Rosie', 'FEMALE', 'Black', -4);