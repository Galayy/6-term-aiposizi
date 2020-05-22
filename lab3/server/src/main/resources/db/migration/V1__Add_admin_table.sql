CREATE TYPE role AS ENUM ('ADMIN', 'ROOT_ADMIN');

CREATE TABLE admin
(
    id       UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username TEXT UNIQUE NOT NULL,
    password TEXT UNIQUE NOT NULL,
    role     role        NOT NULL
);