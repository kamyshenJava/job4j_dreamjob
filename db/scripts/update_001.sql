CREATE TABLE if not exists post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created DATE,
    city_id INT,
    visible bool
);