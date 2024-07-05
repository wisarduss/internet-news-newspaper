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

CREATE TABLE IF NOT EXISTS comments
(
    id                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text                varchar(255),
    post_id             bigint,
    user_id             bigint,
    created_comment     timestamp without time zone,
    CONSTRAINT fk_comments_to_posts FOREIGN KEY (post_id) REFERENCES posts (id),
    CONSTRAINT fk_comments_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (id)
);

CREATE TABLE IF NOT EXISTS likes
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT,
    post_id     BIGINT,
    CONSTRAINT fk_likes_to_posts FOREIGN KEY (post_id) REFERENCES posts (id),
    CONSTRAINT fk_likes_to_users FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (id)
);


