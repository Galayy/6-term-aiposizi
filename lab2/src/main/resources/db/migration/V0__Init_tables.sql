CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "citext";

CREATE TABLE employee
(
  id         UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT employee_pkey PRIMARY KEY,
  first_name TEXT                            NOT NULL,
  last_name  TEXT UNIQUE                     NOT NULL,
  speciality TEXT                            NOT NULL
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
  id           UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT office_pkey PRIMARY KEY,
  address_id   UUID                            NOT NULL
    CONSTRAINT office_address_id_fkey REFERENCES address (id) ON DELETE CASCADE,
  company_name TEXT UNIQUE                     NOT NULL
);

CREATE TABLE room
(
  id        UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT room_pkey PRIMARY KEY,
  office_id UUID                            NOT NULL
    CONSTRAINT room_office_id_fkey REFERENCES office (id) ON DELETE CASCADE,
  number    INTEGER                         NOT NULL
);

CREATE TABLE place
(
  id          UUID DEFAULT uuid_generate_v4() NOT NULL
    CONSTRAINT place_pkey PRIMARY KEY,
  employee_id UUID UNIQUE
    CONSTRAINT place_employee_id_fkey REFERENCES employee (id),
  room_id     UUID                            NOT NULL
    CONSTRAINT place_room_id_fkey REFERENCES room (id) ON DELETE CASCADE,
  number      INTEGER                         NOT NULL
);

CREATE VIEW room_view AS
SELECT room.*,
       (SELECT count(*) FROM place WHERE room.id = place.room_id AND employee_id IS NULL) AS free_places
FROM room;
