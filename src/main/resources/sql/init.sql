CREATE TABLE IF NOT EXISTS words(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    content VARCHAR(45) UNIQUE,
    pronunciation TEXT
);

CREATE TABLE IF NOT EXISTS definitions(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    definition TEXT,
    wordRef INTEGER NOT NULL,
    wordType INTEGER NOT NULL,
    FOREIGN KEY(wordRef) REFERENCES words(id)
);

CREATE TABLE IF NOT EXISTS definitionExamples(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    english TEXT,
    vietnamese TEXT,
    definitionRef INTEGER NOT NULL,
    FOREIGN KEY(definitionRef) REFERENCES definitions(id)
);

CREATE TABLE IF NOT EXISTS definition_relations(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    first_end INTEGER,
    second_end INTEGER
);

CREATE TABLE IF NOT EXISTS agreements(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    agreement VARCHAR(15),
    content INTEGER
);