package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

public interface IRelationService {
    JSObject getRelationsByDefinitionId(int definitionId);

    void addRelation(int defId, int wordId);
}
