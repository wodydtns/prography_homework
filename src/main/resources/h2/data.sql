DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS UserRoom;

CREATE TABLE USER
 (
        id  INT  PRIMARY KEY,
        faker_id  INT NOT NULL,
        name    VARCHAR(50)    NOT NULL,
        email   VARCHAR(100)   NOT NULL,
        status  VARCHAR(10)  NOT NULL,
        created_at  TIMESTAMP,
        updated_at  TIMESTAMP
 );

CREATE TABLE ROOM
 (
        id  INT  PRIMARY KEY,
        title VARCHAR(255),
        host INT,
        room_type VARCHAR(6)
        status VARCHAR(8)
        created_at TIMESTAMP,
        updated_at TIMESTAMP,
        FOREIGN KEY (host) REFERENCES User(id)
 );

CREATE TABLE UserRoom (
    id INT PRIMARY KEY,
    room_id INT,
    user_id INT,
    team VARCHAR(4) CHECK (team IN ('RED', 'BLUE')),
    FOREIGN KEY (room_id) REFERENCES Room(id),
    FOREIGN KEY (user_id) REFERENCES User(id)
);