package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

public interface IWordService {
    JSObject absoluteSearch(String text);

    JSObject advanceSearch(String text, int limit);

    JSObject getWord(int wordId);

    int editWord(JSObject obj);

    int addWord(JSObject obj);

    boolean deleteWord(int wordId);

    void speak(String word);
}
