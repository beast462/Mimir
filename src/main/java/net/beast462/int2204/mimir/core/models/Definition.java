package net.beast462.int2204.mimir.core.models;

import java.util.Map;

public class Definition {
    public int id;
    public String definition;
    public int wordRef;
    public int wordType;

    public static class WordTypes {
        public static final int UNSPECIFIED = 0;
        public static final int NOUN = 1;
        public static final int ADJECTIVE = 2;
        public static final int VERB = 4;
        public static final int ADVERB = 8;
        public static final int TRANS_VERB = 16;
        public static final int ARTICLE = 32;
        public static final int PREPOSITION = 64;
        public static final int DETERMINER = 128;
        public static final int PRONOUN = 256;
        public static final int CONJUNCTION = 512;
        public static final int INTERJECTION = 1024;
        public static final int ACRONYM = 2048;
        public static final int FIGURATIVE = 4096;
        public static final int DEFINITION = 8192;
        public static final int AUXILIARY = 16384;

        public static final Map<Integer, String> typeRefs = Map.ofEntries(
                Map.entry(UNSPECIFIED, "không"),
                Map.entry(NOUN, "danh từ"),
                Map.entry(ADJECTIVE, "tính từ"),
                Map.entry(VERB, "nội động từ"),
                Map.entry(ADVERB, "trạng từ"),
                Map.entry(TRANS_VERB, "ngoại động từ"),
                Map.entry(ARTICLE, "mạo từ"),
                Map.entry(PREPOSITION, "giới từ"),
                Map.entry(DETERMINER, "từ hạn định"),
                Map.entry(PRONOUN, "đại từ"),
                Map.entry(CONJUNCTION, "liên từ"),
                Map.entry(INTERJECTION, "thán từ"),
                Map.entry(ACRONYM, "viết tắt"),
                Map.entry(FIGURATIVE, "nghĩa bóng"),
                Map.entry(DEFINITION, "định nghĩa"),
                Map.entry(AUXILIARY, "trợ từ")
        );
    }
}
