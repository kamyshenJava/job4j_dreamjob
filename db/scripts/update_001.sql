CREATE TABLE if not exists post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATE,
    city_id INT,
    visible bool
);

CREATE TABLE if not exists candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATE,
    photo BYTEA
);

CREATE TABLE if not exists users (
   id SERIAL PRIMARY KEY,
   email VARCHAR(100) UNIQUE,
   password TEXT
);