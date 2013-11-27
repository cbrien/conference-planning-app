INSERT INTO conf_location (id, name, description, address) VALUES (10001, 'test location 1', 'this is the test location 1', 'this is the address 1\nline 2\nline3');
INSERT INTO conf_location (id, name, description, address) VALUES (10002, 'test location 2', 'this is the test location 2', 'this is the address 2\nline 2\nline3');

INSERT INTO conf_room (id, name, description, capacity, location_id) VALUES (10001, 'Room 1.1', 'this is room 1 at location 1', 20, 10001);
INSERT INTO conf_room (id, name, description, capacity, location_id) VALUES (10002, 'Room 1.2', 'this is room 2 at location 1', 40, 10001);
INSERT INTO conf_room (id, name, description, capacity, location_id) VALUES (10003, 'Room 2.1', 'this is room 1 at location 2', 30, 10002);
INSERT INTO conf_room (id, name, description, capacity, location_id) VALUES (10004, 'Room 2.2', 'this is room 2 at location 2', 50, 10002);
   
INSERT INTO conf_org (id, name, description) VALUES (10001, 'Organization 1', 'The Organization 1');
INSERT INTO conf_org (id, name, description) VALUES (10002, 'Organization 2', 'The Organization 1');

INSERT INTO conf_user (id, email, firstname, lastname, description, address, dateOfBirth, org_id) VALUES (10001, 'user1@testing.local', 'Testuser', 'One', 'The test user one', 'address 1', '2012-10-01', 10001);
INSERT INTO conf_user (id, email, firstname, lastname, description, address, dateOfBirth, org_id) VALUES (10002, 'user2@testing.local', 'Testuser', 'Two', 'The test user two', 'address 2', '2012-10-02', 10001);
INSERT INTO conf_user (id, email, firstname, lastname, description, address, dateOfBirth, org_id) VALUES (10003, 'user3@testing.local', 'Testuser', 'Three', 'The test user three', 'address 3', '2012-10-03', 10002);
INSERT INTO conf_user (id, email, firstname, lastname, description, address, dateOfBirth, org_id) VALUES (10004, 'user4@testing.local', 'Testuser', 'Four', 'The test user four', 'address 4', '2012-10-04', 10002);
   
INSERT INTO conf_conference (id, name, description, startDate, endDate, location_id) VALUES (10001, 'Conference 1.1', 'this is conference 1 of location 1', '2012-10-01', '2012-10-10', 10001);
INSERT INTO conf_conference (id, name, description, startDate, endDate, location_id) VALUES (10002, 'Conference 1.2', 'this is conference 2 of location 1', '2012-11-01', '2012-11-10', 10001);
INSERT INTO conf_conference (id, name, description, startDate, endDate, location_id) VALUES (10003, 'Conference 2.1', 'this is conference 1 of location 2', '2012-10-01', '2012-10-10', 10002);
INSERT INTO conf_conference (id, name, description, startDate, endDate, location_id) VALUES (10004, 'Conference 2.2', 'this is conference 2 of location 2', '2012-11-01', '2012-11-10', 10002);
   
INSERT INTO conf_talk (id, name, description, startDate, length, conference_id, room_id) VALUES (10001, 'Talk 1.1', 'this is talk 1 of conference 1.1', '2012-10-02', 60, 10001, 10001);
INSERT INTO conf_talk (id, name, description, startDate, length, conference_id, room_id) VALUES (10002, 'Talk 1.2', 'this is talk 2 of conference 1.1', '2012-10-02', 60, 10001, 10002);
INSERT INTO conf_talk (id, name, description, startDate, length, conference_id, room_id) VALUES (10003, 'Talk 2.1', 'this is talk 1 of conference 2.1', '2012-10-02', 60, 10003, 10003);
INSERT INTO conf_talk (id, name, description, startDate, length, conference_id, room_id) VALUES (10004, 'Talk 2.2', 'this is talk 2 of conference 2.1', '2012-10-02', 60, 10003, 10004);
   
INSERT INTO conf_assign (id, speaker_id, talk_id) VALUES (10001, 10001, 10001);
INSERT INTO conf_assign (id, speaker_id, talk_id) VALUES (10002, 10002, 10002);
INSERT INTO conf_assign (id, speaker_id, talk_id) VALUES (10003, 10003, 10003);
INSERT INTO conf_assign (id, speaker_id, talk_id) VALUES (10004, 10004, 10004);
