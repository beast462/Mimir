package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.core.models.Definition;
import net.beast462.int2204.mimir.core.models.DefinitionExample;
import net.beast462.int2204.mimir.core.models.Word;

import java.util.*;

import static java.util.Map.entry;

public class ExternalDataParser {
    public static final Map<String, Integer> typeRefs = Map.ofEntries(
            entry("danh từ", Definition.WordTypes.NOUN),
            entry("tính từ", Definition.WordTypes.ADJECTIVE),
            entry("nội động từ", Definition.WordTypes.VERB),
            entry("phó từ", Definition.WordTypes.ADVERB),
            entry("ngoại động từ", Definition.WordTypes.TRANS_VERB),
            entry("mạo từ", Definition.WordTypes.ARTICLE),
            entry("giới từ", Definition.WordTypes.PREPOSITION),
            entry("từ hạn định", Definition.WordTypes.DETERMINER),
            entry("đại từ", Definition.WordTypes.PRONOUN),
            entry("liên từ", Definition.WordTypes.CONJUNCTION),
            entry("thán từ", Definition.WordTypes.INTERJECTION),
            entry("viết tắt", Definition.WordTypes.ACRONYM),
            entry("nghĩa bóng", Definition.WordTypes.FIGURATIVE),
            entry("định ngữ", Definition.WordTypes.DEFINITION),
            entry("trợ động từ", Definition.WordTypes.AUXILIARY),
            entry("phó từ & tính từ", Definition.WordTypes.ADVERB | Definition.WordTypes.ADJECTIVE),
            entry("danh từ & phó từ", Definition.WordTypes.NOUN | Definition.WordTypes.ADVERB),
            entry("tính từ & phó từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.ADVERB),
            entry("phó từ & giới từ", Definition.WordTypes.ADVERB | Definition.WordTypes.PREPOSITION),
            entry("phó từ & liên từ", Definition.WordTypes.ADVERB | Definition.WordTypes.CONJUNCTION),
            entry("danh từ & đại từ", Definition.WordTypes.NOUN | Definition.WordTypes.PRONOUN),
            entry("nội động từ & trợ động từ", Definition.WordTypes.VERB | Definition.WordTypes.AUXILIARY),
            entry("ngoại động từ & trợ động từ", Definition.WordTypes.TRANS_VERB | Definition.WordTypes.AUXILIARY),
            entry("tính từ & danh từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.NOUN),
            entry("danh từ & ngoại động từ", Definition.WordTypes.NOUN | Definition.WordTypes.TRANS_VERB),
            entry("danh từ & nội động từ", Definition.WordTypes.NOUN | Definition.WordTypes.VERB),
            entry("tính từ & ngoại động từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.TRANS_VERB),
            entry("nội động từ & ngoại động từ", Definition.WordTypes.VERB | Definition.WordTypes.TRANS_VERB),
            entry("giới từ & liên từ", Definition.WordTypes.PREPOSITION | Definition.WordTypes.CONJUNCTION),
            entry("tính từ & đại từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.PRONOUN),
            entry("nội động từ & tính từ", Definition.WordTypes.VERB | Definition.WordTypes.ADJECTIVE),
            entry("danh từ & nội động từ & ngoại động từ", Definition.WordTypes.NOUN | Definition.WordTypes.TRANS_VERB | Definition.WordTypes.VERB),
            entry("tính từ & danh từ & phó từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.NOUN | Definition.WordTypes.ADVERB),
            entry("tính từ & phó từ & giới từ", Definition.WordTypes.ADJECTIVE | Definition.WordTypes.ADVERB | Definition.WordTypes.PREPOSITION)
    );
    private final Set<Integer> ids = new HashSet<>();
    private final Map<String, Integer> words = new HashMap<>();

    /**
     * Generate unique ID for parsed entities.
     *
     * @param prefix add prefix to randomized ID to make "categories"<br/>
     *               ie. 1xxxxx for words, 2xxxxx for definitions, ...
     * @return randomized ID
     */
    private int generateId(int prefix) {
        int result;

        do {
            result = (int) (Math.random() * 1e8) + (int) (prefix * 1e8);
        } while (ids.contains(result));

        ids.add(result);
        return result;
    }

    /**
     * Parse example which was formatted as:<br/>
     * =english_example+ vietnamese_example
     *
     * @param raw          raw string data input
     * @param definitionId ID of referenced definition
     * @param accumulator  totally accumulated data
     */
    private void parseExample(String raw, int definitionId, ExternalData accumulator) {
        if (raw.length() == 0)
            return;

        var example = new DefinitionExample();
        var tokens = raw.split("\\+");

        example.id = generateId(3);
        example.definitionRef = definitionId;
        example.english = tokens[0].trim();
        example.vietnamese = tokens[1].trim();

        accumulator.examples.add(example);
    }

    /**
     * Parse definition which was formatted as:<br/>
     * - explanation
     * ...<br/>
     * =english_example+ vietnamese_example
     *
     * @param raw         raw string data input
     * @param wordId      ID of referenced word
     * @param wordType    type of referenced word
     * @param accumulator totally accumulated data
     */
    private void parseDefinition(String raw, int wordId, int wordType, ExternalData accumulator) {
        if (raw.length() == 0)
            return;

        var definition = new Definition();
        var lines = raw.split("\n");

        definition.id = generateId(2);
        definition.wordType = wordType;
        definition.wordRef = wordId;

        definition.definition = lines[0].trim().replaceFirst("^-", "").trim();

        accumulator.definitions.add(definition);

        for (int i = 1; i < lines.length; ++i)
            if (lines[i].startsWith("="))
                parseExample(lines[i].substring(1).trim(), definition.id, accumulator);
    }

    /**
     * Parse definitions by group of word type which were formatted as:<br/>
     * type[&... type]
     * ...<br/>
     * - explanation
     * ...<br/>
     * =english_example+ vietnamese_example
     *
     * @param raw         raw string data input
     * @param wordId      ID of referenced word
     * @param accumulator totally accumulated data
     */
    private void parseDefinitionsByGroup(String raw, int wordId, ExternalData accumulator) {
        if (raw.length() == 0)
            return;

        var lines = raw.split("\n", 2);
        int wordType;

        var firstLine = lines[0].trim();

        if (firstLine.matches("^\\*?\\s*\\((.*)\\).*$")) {
            wordType = typeRefs.getOrDefault(
                    firstLine.substring(
                            1,
                            firstLine.indexOf(')')
                    ), Definition.WordTypes.UNSPECIFIED
            );
        } else {
            var firstLineTokens = firstLine.split("[,;]");
            wordType = typeRefs.getOrDefault(firstLineTokens[0], Definition.WordTypes.UNSPECIFIED);
        }

        var definitionsRaw = raw.substring(lines[0].length()).trim();
        var definitionRaws = definitionsRaw.split("(?m)^-");

        for (var definitionRaw : definitionRaws)
            if (definitionRaw.length() != 0)
                parseDefinition(definitionRaw.trim(), wordId, wordType, accumulator);
    }

    /**
     * Parse word which formatted as:<br/>
     * &#64word /pronunciation/<br/>
     * ...<br/>
     * * type[&... type]
     * ...<br/>
     * - explanation
     * ...<br/>
     * =english_example+ vietnamese_example
     *
     * @param raw         raw string data input
     * @param accumulator totally accumulated data
     */
    private void parseWord(String raw, ExternalData accumulator) {
        if (raw.trim().length() == 0)
            return;

        var lines = raw.split("\n", 2);
        var firstLineTokens = lines[0].split("\\s+/");
        var content = firstLineTokens[0].trim().replace("@", "").toLowerCase(Locale.ENGLISH);
        int wordId;

        if (words.containsKey(content)) {
            wordId = words.get(content);
        } else {
            var word = new Word();
            word.content = content;
            if (firstLineTokens.length > 1)
                word.pronunciation = firstLineTokens[1].substring(0, firstLineTokens[1].length() - 1).trim();
            else
                word.pronunciation = "";
            word.id = wordId = generateId(1);
            words.put(word.content, word.id);

            accumulator.words.add(word);
        }

        var definitionsRaw = raw.substring(lines[0].length()).trim();

        if (definitionsRaw.contains("*")) {
            // definition block may contain word type
            var definitions = definitionsRaw.split("(?m)^\\*");

            for (var definition : definitions) {
                if (definition.length() == 0) continue;

                parseDefinitionsByGroup(definition, wordId, accumulator);
            }
        } else {
            // definition block does not include word type
            parseDefinition(definitionsRaw, wordId, Definition.WordTypes.UNSPECIFIED, accumulator);
        }
    }

    public ExternalData parseRaw(String raw) {
        var result = new ExternalData();
        var words = raw.split("(?m)^@");

        for (var word : words)
            try {
                parseWord(word.trim(), result);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(word);
                System.exit(0);
            }

        ids.clear();
        return result;
    }
}
