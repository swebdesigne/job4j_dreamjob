CREATE TABLE Post
(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    city_id INTEGER,
    visible BOOLEAN,
    created TIMESTAMP
);