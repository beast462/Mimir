package net.beast462.int2204.mimir.core.models;

public class Definition {
    public static class WordTypes {
        public static final int UNSPECIFIED = 0;
        public static final int NOUN = 1;
        public static final int ADJECTIVE = 2;
        public static final int VERB = 4;
        public static final int ADVERB = 6;
        public static final int TRANS_VERB = 8;
        public static final int ARTICLE = 16;
        public static final int PREPOSITION = 32;
        public static final int DETERMINER = 64;
        public static final int PRONOUN = 128;
        public static final int CONJUNCTION = 256;
        public static final int INTERJECTION = 512;
    }

    public int id;

    public String definition;

    public String note;

    public int wordRef;

    public int wordType;
}
