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
    address TEXT NOT NULL,
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
    cost REAL NOT NULL,
    description TEXT NOT NULL,
    expirationDate TEXT NOT NULL,
    timeBeforeReserve INTEGER NOT NULL,
    timeBeforeDelete INTEGER NOT NULL,
    discount INTEGER NOT NULL
);

-- Table: users
CREATE TABLE IF NOT EXISTS users(
    ID INTEGER PRIMARY KEY,
    codFisc TEXT NOT NULL,
    firstName TEXT NOT NULL,
    surname TEXT NOT NULL,
    inscriptionDate TEXT NOT NULL,
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
    availability INTEGER NOT NULL CHECK(availability IN (0, 1)), -- 0 = not available, 1 = available  TODO verificare che non dia problemi in seguito con la conversione da int a bool
    sportCentre INTEGER NOT NULL,
    FOREIGN KEY (sportCentre) REFERENCES sportsCentres(ID) ON DELETE CASCADE
);

-- Table: bookings
CREATE TABLE IF NOT EXISTS bookings(
    ID INTEGER PRIMARY KEY,
    date TEXT NOT NULL,
    time TEXT NOT NULL ,
    payed INTEGER NOT NULL CHECK(payed IN (0, 1)),
    users INTEGER NOT NULL,
    field INTEGER NOT NULL,
    FOREIGN KEY(users) REFERENCES users(ID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY(field) REFERENCES fields(ID) ON DELETE CASCADE ON UPDATE CASCADE
);