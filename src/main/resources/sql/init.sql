CREATE TABLE IF NOT EXISTS words(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    content VARCHAR(45) UNIQUE,
    pronunciation TEXT
);

CREATE TABLE IF NOT EXISTS definitions(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    definition TEXT,
    word_ref INTEGER NOT NULL,
    word_type INTEGER NOT NULL,
    CONSTRAINT fk_word_ref
        FOREIGN KEY(word_ref) REFERENCES words(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS definition_examples(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    english TEXT,
    vietnamese TEXT,
    definition_ref INTEGER NOT NULL,
    CONSTRAINT fk_definition_ref
        FOREIGN KEY(definition_ref) REFERENCES definitions(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS definition_relations(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    definition_ref INTEGER,
    word_ref INTEGER,
    CONSTRAINT fk_definition_ref
        FOREIGN KEY(definition_ref) REFERENCES definitions(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_word_ref
    FOREIGN KEY(word_ref) REFERENCES words(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS agreements(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    agreement VARCHAR(15) UNIQUE,
    content INTEGER
);

INSERT OR IGNORE INTO [agreements]([agreement], [content]) VALUES('external_data', 0);