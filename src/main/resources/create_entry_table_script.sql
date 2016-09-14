CREATE TABLE entry
(  id            BIGINT NOT NULL,
  content       CHARACTER VARYING(255),
  creation_date CHARACTER VARYING(255),
  CONSTRAINT entry_pkey PRIMARY KEY (id))
WITH (
OIDS = FALSE
);

ALTER TABLE entry
  OWNER TO postgres;