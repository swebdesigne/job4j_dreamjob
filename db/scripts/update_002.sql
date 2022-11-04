CREATE TABLE Candidate
(
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    city_id INTEGER,
    photo bytea,
    created TIMESTAMP
);