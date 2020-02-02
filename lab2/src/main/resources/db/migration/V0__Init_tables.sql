CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "citext";

CREATE TABLE employee
(
  id         UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT employee_pkey PRIMARY KEY,
  first_name TEXT                            NOT NULL,
  last_name  TEXT UNIQUE                     NOT NULL,
  department TEXT
);

CREATE TABLE address
(
  id      UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT address_pkey PRIMARY KEY,
  country TEXT                            NOT NULL,
  city    TEXT                            NOT NULL,
  street  TEXT                            NOT NULL,
  number  INTEGER                         NOT NULL
);

CREATE TABLE office
(
  id         UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT office_pkey PRIMARY KEY,
  address_id UUID                            NOT NULL
    CONSTRAINT office_address_id_fkey REFERENCES address (id)
);

CREATE TABLE room
(
  id           UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT room_pkey PRIMARY KEY,
  office_id    UUID                            NOT NULL
    CONSTRAINT room_office_id_fkey REFERENCES office (id),
  number       INTEGER                         NOT NULL
);

CREATE TABLE place
(
  id          UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT place_pkey PRIMARY KEY,
  employee_id UUID
    CONSTRAINT place_employee_id_fkey REFERENCES employee (id),
  room_id     UUID                            NOT NULL
    CONSTRAINT place_room_id_fkey REFERENCES room (id),
  number      INTEGER                         NOT NULL
);
