CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR NOT NULL,
    surname     VARCHAR NOT NUll,
    email       VARCHAR NOT NULL UNIQUE,
    password    VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS posts
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     VARCHAR NOT NULL,
    photo_URL       VARCHAR NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE,
    user_id         BIGINT
);


