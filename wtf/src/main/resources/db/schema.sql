CREATE TABLE BRANCH (
                        ID BIGINT NOT NULL AUTO_INCREMENT,
                        NAME VARCHAR(100) NOT NULL,
                        PRIMARY KEY (ID)
);

CREATE TABLE ROOM (
                      ROOM_ID BIGINT NOT NULL AUTO_INCREMENT,
                      NUMBER INT NOT NULL,
                      BRANCH_ID BIGINT NOT NULL,
                      PRIMARY KEY (ROOM_ID),
                      CONSTRAINT FK_ROOM_BRANCH FOREIGN KEY (BRANCH_ID)
                          REFERENCES BRANCH (ID)
);

CREATE TABLE MOVIE (
                       ID BIGINT NOT NULL AUTO_INCREMENT,
                       NAME VARCHAR(100) NOT NULL,
                       YEAR INT NOT NULL,
                       DIRECTOR VARCHAR(100),
                       DURATION INT,
                       PRIMARY KEY (ID)
);

CREATE TABLE GENRE (
                       ID_GENRE BIGINT NOT NULL AUTO_INCREMENT,
                       PRIMARY KEY (ID_GENRE)
);

CREATE TABLE FUNCTION (
                          ID BIGINT NOT NULL AUTO_INCREMENT,
                          FORMAT VARCHAR(50),
                          SUBTITLED BOOLEAN,
                          DATE DATE,
                          TIME TIME,
                          MOVIE_ID BIGINT NOT NULL,
                          ROOM_ID BIGINT NOT NULL,
                          PRIMARY KEY (ID),
                          CONSTRAINT FK_FUNCTION_MOVIE FOREIGN KEY (MOVIE_ID)
                              REFERENCES MOVIE (ID),
                          CONSTRAINT FK_FUNCTION_ROOM FOREIGN KEY (ROOM_ID)
                              REFERENCES ROOM (ROOM_ID)
);

CREATE TABLE USER (
                      USER_NAME BIGINT NOT NULL,
                      ID BIGINT NOT NULL,
                      EMAIL VARCHAR(100) NOT NULL,
                      PASSWORD VARCHAR(100) NOT NULL,
                      NAME VARCHAR(100),
                      SURNAME VARCHAR(100),
                      BIRTH_DATE DATE,
                      PHONE_NUMBER BIGINT,
                      ADRESS VARCHAR(255),
                      CARD_ID BIGINT,
                      PRIMARY KEY (USER_NAME, ID, EMAIL),
                      CONSTRAINT FK_USER_CARD FOREIGN KEY (CARD_ID)
                          REFERENCES CARD (CARD_NUMBER)
);

CREATE TABLE CARD (
                      CARD_NUMBER BIGINT NOT NULL AUTO_INCREMENT,
                      EXPIRATION_DATE DATE,
                      CVV INT NOT NULL,
                      PRIMARY KEY (CARD_NUMBER)
);

CREATE TABLE RESERVATION (
                             ID BIGINT NOT NULL AUTO_INCREMENT,
                             STATUS VARCHAR(50),
                             PAYMENT_METHOD VARCHAR(50),
                             ROW_SEAT INT,
                             COLUMN_SEAT INT,
                             USER_NAME BIGINT NOT NULL,
                             ID_USER BIGINT NOT NULL,
                             EMAIL VARCHAR(100) NOT NULL,
                             FUNCTION_ID BIGINT NOT NULL,
                             PRIMARY KEY (ID),
                             CONSTRAINT FK_RESERVATION_USER FOREIGN KEY (USER_NAME, ID_USER, EMAIL)
                                 REFERENCES USER (USER_NAME, ID, EMAIL),
                             CONSTRAINT FK_RESERVATION_FUNCTION FOREIGN KEY (FUNCTION_ID)
                                 REFERENCES FUNCTION (ID)
);

CREATE TABLE SNACK (
                       SNACK_ID BIGINT NOT NULL AUTO_INCREMENT,
                       DESCRIPTION VARCHAR(255),
                       PRICE DECIMAL(10, 2),
                       RESERVATION_ID BIGINT,
                       PRIMARY KEY (SNACK_ID),
                       CONSTRAINT FK_SNACK_RESERVATION FOREIGN KEY (RESERVATION_ID)
                           REFERENCES RESERVATION (ID)
);
