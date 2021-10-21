package net.beast462.int2204.mimir.application.xapi;

import javafx.application.Platform;
import net.beast462.int2204.mimir.Main;
import net.beast462.int2204.mimir.application.interfaces.IAgreementService;
import net.beast462.int2204.mimir.application.interfaces.IDefinitionService;
import net.beast462.int2204.mimir.application.interfaces.IWordService;
import net.beast462.int2204.mimir.application.services.AgreementService;
import net.beast462.int2204.mimir.application.services.DefinitionService;
import net.beast462.int2204.mimir.application.services.WordService;
import net.beast462.int2204.mimir.core.Logger;
import net.beast462.int2204.mimir.core.bridge.JSObjectUtils;
import netscape.javascript.JSObject;

public class ApplicationBridge implements IWordService, IAgreementService, IDefinitionService {
    private final IWordService words;
    private final IAgreementService agreements;
    private final IDefinitionService definitions;

    public ApplicationBridge() {
        words = new WordService();
        agreements = new AgreementService();
        definitions = new DefinitionService();
    }

    public void exit() {
        Platform.exit();
    }

    public void log(String text) {
        Logger.defaultLogger.info(String.format("Message from webview: %s", text));
    }

    public void info(String text) {
        Logger.defaultLogger.info(String.format("Message from webview: %s", text));
    }

    public void error(String text) {
        Logger.defaultLogger.error(String.format("Message from webview: %s", text));
    }

    @Override
    public JSObject absoluteSearch(String text) {
        Logger.defaultLogger.info(String.format("Searching '%s' (abs)", text));
        var result = words.absoluteSearch(text);
        Logger.defaultLogger.info(
                String.format("Found %d result(s)", result.getMember("length")));
        return result;
    }

    @Override
    public JSObject advanceSearch(String text, int limit) {
        Logger.defaultLogger.info(String.format("Searching '%s' (adv) limit %d", text, limit));
        var result = words.advanceSearch(text, limit);
        Logger.defaultLogger.info(
                String.format("Found %d result(s)", result.getMember("length")));
        return result;
    }

    @Override
    public JSObject getWord(int wordId) {
        Logger.defaultLogger.info(String.format(
                "Fetching all info of word # %d",
                wordId
        ));

        var word = words.getWord(wordId);

        Logger.defaultLogger.info(String.format(
                "Word # %d fetched",
                wordId
        ));

        return word == null ? JSObjectUtils.newNullObject() : word;
    }

    @Override
    public int editWord(JSObject obj) {
        Main.getLogger().info(String.format(
                "Editing word %s # %d",
                obj.getMember("content"),
                obj.getMember("id")
        ));

        return words.editWord(obj);
    }

    @Override
    public int addWord(JSObject obj) {
        Main.getLogger().info(String.format(
                "Adding new word '%s'",
                obj.getMember("word")
        ));

        return words.addWord(obj);
    }

    @Override
    public boolean deleteWord(int wordId) {
        Main.getLogger().info(String.format(
                "Deleting word # %d",
                wordId
        ));

        return words.deleteWord(wordId);
    }

    @Override
    public void speak(String word) {
        words.speak(word);
    }

    @Override
    public int getAgreement(String agreement) {
        return agreements.getAgreement(agreement);
    }

    @Override
    public JSObject acceptDataRecommendation() {
        Logger.defaultLogger.info("Data recommendation accepted");
        return agreements.acceptDataRecommendation();
    }

    @Override
    public void denyDataRecommendation() {
        Logger.defaultLogger.info("Data recommendation denied");
        agreements.denyDataRecommendation();
    }

    @Override
    public JSObject getDefinitionsByWordId(int wordId) {
        return definitions.getDefinitionsByWordId(wordId);
    }

    @Override
    public JSObject getWordTypeReferences() {
        return definitions.getWordTypeReferences();
    }

    @Override
    public void addDefinition(int wordId, JSObject obj) {
        definitions.addDefinition(wordId, obj);
    }
}
