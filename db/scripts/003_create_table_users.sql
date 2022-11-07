CREATE TABLE Users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT,
    email    TEXT UNIQUE,
    password TEXT
);