package net.beast462.int2204.mimir.application.interceptions;

import javafx.application.Platform;
import net.beast462.int2204.mimir.application.interfaces.IWordService;
import net.beast462.int2204.mimir.application.services.WordService;
import net.beast462.int2204.mimir.core.Logger;
import netscape.javascript.JSObject;

public class ApplicationBridge implements IWordService {
    public final IWordService words;

    public ApplicationBridge() {
        words = new WordService();
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
    public JSObject advanceSearch(String text) {
        Logger.defaultLogger.info(String.format("Searching '%s' (adv)", text));
        var result = words.advanceSearch(text);
        Logger.defaultLogger.info(
                String.format("Found %d result(s)", result.getMember("length")));
        return result;
    }
}
