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
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-1,-1,'ADMIN', true);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-2,-2,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-3,-3,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-4,-4,'ADMIN', false);
INSERT INTO user_authorities(id,user_id,authority,enabled) VALUES (-5,-5,'ADMIN', false)