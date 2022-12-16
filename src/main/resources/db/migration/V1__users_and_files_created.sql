CREATE TABLE if not exists USERS(
     id INT AUTO_INCREMENT,
     login VARCHAR(255),
     password VARCHAR(255),
     role VARCHAR(30),
     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS FILES(
     id INT AUTO_INCREMENT,
     filename VARCHAR(255),
     date DATETIME,
     size LONG,
     type VARCHAR(255),
     content LONGBLOB,
     PRIMARY KEY (id)
);

# INSERT INTO USERS (login, password,role)
# VALUES ('admin@gmail.ru', '0000','ADMIN'),
#        ('reader@gmail.ru', '1111','USER'),
#        ('editor@gmail.ru', '2222','USER');

INSERT INTO FILES (filename, date, size, type, content)
VALUES ('default','2022-03-11 12:13:14', 128, 'txt', 123),
       ('qwerty','2022-05-16 13:14:15', 128, 'txt', 123),
       ('config','2022-07-23 15:16:17', 128, 'txt', 123);