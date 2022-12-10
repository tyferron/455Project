--CREATE DATABASE "ChatroomDB"
--    WITH
--    OWNER = postgres
--    ENCODING = 'UTF8'
--    LC_COLLATE = 'English_United States.1252'
--    LC_CTYPE = 'English_United States.1252'
--    TABLESPACE = pg_default
--    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public."ChatRoom"
(
    "RoomID" SERIAL,
    "RoomName" text COLLATE pg_catalog."default" NOT NULL,
    "Password" text COLLATE pg_catalog."default" NOT NULL,
    "MessageHistory" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "ChatRoom_pkey" PRIMARY KEY ("RoomID")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."ChatRoom"
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."Users"
(
    "UserID" SERIAL,
    "UserName" text COLLATE pg_catalog."default" NOT NULL,
    "Password" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT "Pk_Users" PRIMARY KEY ("UserID")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Users"
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS public."UserToChatrooms"
(
    "UserID" integer NOT NULL,
    "RoomID" integer NOT NULL,
    CONSTRAINT "Pk_combined" PRIMARY KEY ("UserID", "RoomID"),
    CONSTRAINT "FK_ChatRoom" FOREIGN KEY ("RoomID")
        REFERENCES public."ChatRoom" ("RoomID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT "FK_User" FOREIGN KEY ("UserID")
        REFERENCES public."Users" ("UserID") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."UserToChatrooms"
    OWNER to postgres;