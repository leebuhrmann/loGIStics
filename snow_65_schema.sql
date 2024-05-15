--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.11 (Ubuntu 14.11-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry and geography spatial types and functions';


--
-- Name: update_ugc_boundary(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.update_ugc_boundary() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    -- Handle deletions from 'boundary'
    IF TG_TABLE_NAME = 'boundary' AND TG_OP = 'DELETE' THEN
        DELETE FROM ugc_boundary WHERE boundary_id = OLD.boundary_id;

    -- Handle deletions from 'ugc_zone'
    ELSIF TG_TABLE_NAME = 'ugc_zone' AND TG_OP = 'DELETE' THEN
        DELETE FROM ugc_boundary WHERE ugc_code = OLD.ugc_code;

    -- Handle insertions or updates for 'boundary'
    ELSIF TG_TABLE_NAME = 'boundary' AND (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        -- Remove existing entries
        DELETE FROM ugc_boundary WHERE boundary_id = NEW.boundary_id;
        -- Insert new intersections
        INSERT INTO ugc_boundary(ugc_code, boundary_id)
        SELECT u.ugc_code, NEW.boundary_id
        FROM ugc_zone u
        WHERE ST_Intersects(u.the_geom, NEW.the_geom);

    -- Handle insertions or updates for 'ugc_zone'
    ELSIF TG_TABLE_NAME = 'ugc_zone' AND (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        -- Remove existing entries
        DELETE FROM ugc_boundary WHERE ugc_code = NEW.ugc_code;
        -- Insert new intersections
        INSERT INTO ugc_boundary(ugc_code, boundary_id)
        SELECT NEW.ugc_code, b.boundary_id
        FROM boundary b
        WHERE ST_Intersects(b.the_geom, NEW.the_geom);
    END IF;
    RETURN NEW;
END;
$$;


ALTER FUNCTION public.update_ugc_boundary() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: alert; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.alert (
    alert_id integer NOT NULL,
    event character varying(50),
    onset timestamp with time zone,
    expires timestamp with time zone,
    headline character varying(150),
    description character varying(5000),
    nws_id character varying(255),
    dnws_id character varying(255)
);


ALTER TABLE public.alert OWNER TO postgres;

--
-- Name: alert_alert_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.alert_alert_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.alert_alert_id_seq OWNER TO postgres;

--
-- Name: alert_alert_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.alert_alert_id_seq OWNED BY public.alert.alert_id;


--
-- Name: boundary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.boundary (
    boundary_id integer NOT NULL,
    the_geom public.geometry(MultiPolygon,3857),
    description character varying(500),
    name character varying(255),
    subscribed boolean
);


ALTER TABLE public.boundary OWNER TO postgres;

--
-- Name: boundary_boundary_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.boundary_boundary_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.boundary_boundary_id_seq OWNER TO postgres;

--
-- Name: boundary_boundary_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.boundary_boundary_id_seq OWNED BY public.boundary.boundary_id;


--
-- Name: snow_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.snow_user (
    user_id integer NOT NULL
);


ALTER TABLE public.snow_user OWNER TO postgres;

--
-- Name: snow_user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.snow_user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.snow_user_user_id_seq OWNER TO postgres;

--
-- Name: snow_user_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.snow_user_user_id_seq OWNED BY public.snow_user.user_id;


--
-- Name: ugc_alert; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ugc_alert (
    ugc_code character varying(6) NOT NULL,
    alert_id integer NOT NULL
);


ALTER TABLE public.ugc_alert OWNER TO postgres;

--
-- Name: ugc_boundary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ugc_boundary (
    ugc_code character varying(6) NOT NULL,
    boundary_id integer NOT NULL
);


ALTER TABLE public.ugc_boundary OWNER TO postgres;

--
-- Name: ugc_zone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ugc_zone (
    ugc_code character varying(6) NOT NULL,
    the_geom public.geometry(MultiPolygon,3857),
    ugc_code_address character varying(200),
    visibility integer
);


ALTER TABLE public.ugc_zone OWNER TO postgres;

--
-- Name: user_boundary; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_boundary (
    user_id integer NOT NULL,
    boundary_id integer NOT NULL
);


ALTER TABLE public.user_boundary OWNER TO postgres;

--
-- Name: alert alert_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert ALTER COLUMN alert_id SET DEFAULT nextval('public.alert_alert_id_seq'::regclass);


--
-- Name: boundary boundary_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.boundary ALTER COLUMN boundary_id SET DEFAULT nextval('public.boundary_boundary_id_seq'::regclass);


--
-- Name: snow_user user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.snow_user ALTER COLUMN user_id SET DEFAULT nextval('public.snow_user_user_id_seq'::regclass);


--
-- Name: alert alert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.alert
    ADD CONSTRAINT alert_pkey PRIMARY KEY (alert_id);


--
-- Name: boundary boundary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.boundary
    ADD CONSTRAINT boundary_pkey PRIMARY KEY (boundary_id);


--
-- Name: snow_user snow_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.snow_user
    ADD CONSTRAINT snow_user_pkey PRIMARY KEY (user_id);


--
-- Name: ugc_alert ugc_alert_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_alert
    ADD CONSTRAINT ugc_alert_pkey PRIMARY KEY (ugc_code, alert_id);


--
-- Name: ugc_boundary ugc_boundary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_boundary
    ADD CONSTRAINT ugc_boundary_pkey PRIMARY KEY (ugc_code, boundary_id);


--
-- Name: ugc_zone ugc_zone_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_zone
    ADD CONSTRAINT ugc_zone_pkey PRIMARY KEY (ugc_code);


--
-- Name: user_boundary user_boundary_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_boundary
    ADD CONSTRAINT user_boundary_pkey PRIMARY KEY (user_id, boundary_id);


--
-- Name: idx_boundary_geom; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_boundary_geom ON public.boundary USING gist (the_geom);


--
-- Name: idx_ugc_zone_geom; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_ugc_zone_geom ON public.ugc_zone USING gist (the_geom);


--
-- Name: boundary tr_boundary_delete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_boundary_delete AFTER DELETE ON public.boundary FOR EACH ROW EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: boundary tr_boundary_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_boundary_insert AFTER INSERT ON public.boundary FOR EACH ROW EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: boundary tr_boundary_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_boundary_update AFTER UPDATE ON public.boundary FOR EACH ROW WHEN ((old.the_geom IS DISTINCT FROM new.the_geom)) EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: ugc_zone tr_ugc_zone_delete; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_ugc_zone_delete AFTER DELETE ON public.ugc_zone FOR EACH ROW EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: ugc_zone tr_ugc_zone_insert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_ugc_zone_insert AFTER INSERT ON public.ugc_zone FOR EACH ROW EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: ugc_zone tr_ugc_zone_update; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER tr_ugc_zone_update AFTER UPDATE ON public.ugc_zone FOR EACH ROW WHEN ((old.the_geom IS DISTINCT FROM new.the_geom)) EXECUTE FUNCTION public.update_ugc_boundary();


--
-- Name: ugc_alert ugc_alert_alert_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_alert
    ADD CONSTRAINT ugc_alert_alert_id_fkey FOREIGN KEY (alert_id) REFERENCES public.alert(alert_id) ON DELETE CASCADE;


--
-- Name: ugc_alert ugc_alert_ugc_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_alert
    ADD CONSTRAINT ugc_alert_ugc_code_fkey FOREIGN KEY (ugc_code) REFERENCES public.ugc_zone(ugc_code) ON DELETE CASCADE;


--
-- Name: ugc_boundary ugc_boundary_boundary_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_boundary
    ADD CONSTRAINT ugc_boundary_boundary_id_fkey FOREIGN KEY (boundary_id) REFERENCES public.boundary(boundary_id) ON DELETE CASCADE;


--
-- Name: ugc_boundary ugc_boundary_ugc_code_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ugc_boundary
    ADD CONSTRAINT ugc_boundary_ugc_code_fkey FOREIGN KEY (ugc_code) REFERENCES public.ugc_zone(ugc_code) ON DELETE CASCADE;


--
-- Name: user_boundary user_boundary_boundary_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_boundary
    ADD CONSTRAINT user_boundary_boundary_id_fkey FOREIGN KEY (boundary_id) REFERENCES public.boundary(boundary_id) ON DELETE CASCADE;


--
-- Name: user_boundary user_boundary_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_boundary
    ADD CONSTRAINT user_boundary_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.snow_user(user_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--
