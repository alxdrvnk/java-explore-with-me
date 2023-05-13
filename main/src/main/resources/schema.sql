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

CREATE TABLE IF NOT EXISTS compilations_events(
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    PRIMARY KEY (compilation_id, event_id),
    FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE,
    FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS admin_event_comments(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    event_id BIGINT NOT NULL,
    text VARCHAR NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE INDEX IF NOT EXISTS idx_category_name ON categories (name);
CREATE INDEX IF NOT EXISTS idx_compilations_pinned ON compilations (pinned);
CREATE INDEX IF NOT EXISTS idx_event_initiator ON events (initiator_id);
CREATE INDEX IF NOT EXISTS idx_event_state ON events (state);
CREATE INDEX IF NOT EXISTS idx_event_event_date ON events (event_date);
