package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

public interface IExampleService {
    JSObject getExamplesByDefinitionId(int defId);

    void addExample(int definitionId, JSObject obj);
}
