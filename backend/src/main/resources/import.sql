-- USERS
INSERT INTO users (id, full_name, username, password, enabled) VALUES (-1, 'Thomas', 'thomas', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, full_name, username, password, enabled) VALUES (-2, 'Susan', 'susan', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, full_name, username, password, enabled) VALUES (-3, 'Markus', 'markus', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);
INSERT INTO users (id, full_name, username, password, enabled) VALUES (-4, 'Rosie', 'rosie', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', false);
INSERT INTO users (id, full_name, username, password, enabled) VALUES (-5, 'Marry', 'marry', '$2a$12$NytSAaNJDoS1PryzVEniV.YHV2AooDapCkFU2E/Ppsvom.7uee/Hq', true);

-- ROLES
INSERT INTO user_authorities(id,user_id,authority) VALUES (-1,-1,'ADMIN');
INSERT INTO user_authorities(id,user_id,authority) VALUES (-2,-2,'USER');
INSERT INTO user_authorities(id,user_id,authority) VALUES (-3,-3,'USER');
INSERT INTO user_authorities(id,user_id,authority) VALUES (-4,-4,'USER');
INSERT INTO user_authorities(id,user_id,authority) VALUES (-5,-5,'USER')