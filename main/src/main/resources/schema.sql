CREATE TABLE IF NOT EXISTS users(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE,
    name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS categories(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS events(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    annotation VARCHAR NOT NULL,
    category_id BIGINT NOT NULL,
    confirmed_requests INTEGER DEFAULT 0 NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    description VARCHAR NOT NULL,
    event_date TIMESTAMP WITH TIME ZONE NOT NULL,
    initiator_id BIGINT NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    paid BOOLEAN,
    participant_limit INTEGER DEFAULT 0 NOT NULL,
    published_date TIMESTAMP WITH TIME ZONE,
    request_moderation BOOLEAN DEFAULT true NOT NULL,
    state VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (initiator_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS compilations(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS participation_requests(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT constr_unique_event_requester UNIQUE (event_id, requester_id)
);
