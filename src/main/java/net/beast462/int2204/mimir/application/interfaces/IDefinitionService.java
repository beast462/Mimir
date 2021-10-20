package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

public interface IDefinitionService {
    JSObject getDefinitionsByWordId(int wordId);

    JSObject getWordTypeReferences();

    void addDefinition(int wordId, JSObject obj);
}
