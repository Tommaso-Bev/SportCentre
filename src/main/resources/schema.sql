DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS fields;
DROP TABLE IF EXISTS memberships;
DROP TABLE IF EXISTS sportscentres;
DROP TABLE IF EXISTS staff;
DROP TABLE IF EXISTS users;

-- Table: sportsCentres
CREATE TABLE IF NOT EXISTS sportsCentres(
    ID INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    houseNumber TEXT NOT NULL,
    flat TEXT,
    CAP TEXT NOT NULL,
    type TEXT NOT NULL
);

-- Table: staff
CREATE TABLE IF NOT EXISTS staff(
    ID INTEGER PRIMARY KEY,
    codFisc TEXT NOT NULL,
    firstName TEXT NOT NULL,
    surname TEXT NOT NULL,
    hiringDate TEXT NOT NULL,
    task TEXT,
    salary INTEGER NOT NULL,
    sportsCentresID INTEGER NOT NULL,
    FOREIGN KEY (sportsCentresID) REFERENCES sportsCentres(ID) ON DELETE CASCADE
);

-- Table: memberships
CREATE TABLE IF NOT EXISTS memberships(
    name TEXT PRIMARY KEY,
    cost INTEGER NOT NULL,
    description TEXT NOT NULL,
    expirationDate TEXT NOT NULL,
    timeBeforeReserve TEXT NOT NULL,
    timeBeforeDelete TEXT NOT NULL
);

-- Table: users
CREATE TABLE IF NOT EXISTS users(
    ID INTEGER PRIMARY KEY,
    codFisc TEXT NOT NULL,
    firstName TEXT NOT NULL,
    surname TEXT NOT NULL,
    iscriptionDate TEXT NOT NULL,
    membershipsName TEXT NOT NULL,
    FOREIGN KEY (membershipsName) REFERENCES memberships(name) ON UPDATE CASCADE
);

-- Table: fields
CREATE TABLE IF NOT EXISTS fields(
    ID INTEGER PRIMARY KEY,
    sport TEXT NOT NULL,
    minimumPeopleRequired INTEGER NOT NULL,
    maximumPeopleRequired INTEGER NOT NULL,
    fineph INTEGER NOT NULL,
    availability INTEGER NOT NULL CHECK(availability IN (0, 1)), -- 0 = not available, 1 = available
    sportCentre INTEGER NOT NULL,
    FOREIGN KEY (sportCentre) REFERENCES sportsCentres(ID) ON DELETE CASCADE
);

-- Table: bookings
CREATE TABLE IF NOT EXISTS bookings(
    ID INTEGER PRIMARY KEY,
    date TEXT NOT NULL,
    period TEXT NOT NULL,
    users INTEGER NOT NULL,
    field INTEGER NOT NULL,
    FOREIGN KEY(users) REFERENCES users(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(field) REFERENCES fields(ID) ON DELETE CASCADE ON UPDATE CASCADE
);

