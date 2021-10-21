package net.beast462.int2204.mimir.application.services;

import net.beast462.int2204.mimir.application.interfaces.IDefinitionService;
import net.beast462.int2204.mimir.application.interfaces.IExampleService;
import net.beast462.int2204.mimir.application.interfaces.IRelationService;
import net.beast462.int2204.mimir.core.DBUtils;
import net.beast462.int2204.mimir.core.bridge.EngineContainer;
import net.beast462.int2204.mimir.core.models.Definition;
import net.beast462.int2204.mimir.core.bridge.JSObjectUtils;
import netscape.javascript.JSObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DefinitionService implements IDefinitionService {
    private final IExampleService exampleService;
    private final IRelationService relationService;

    public DefinitionService() {
        exampleService = new ExampleService();
        relationService = new RelationService();
    }

    private List<Definition> query(String query, Object[] params) {
        var result = new LinkedList<Definition>();
        var queryResult = DBUtils.query(query, params);

        try {
            while (queryResult.next()) {
                var definition = new Definition();
                definition.id = queryResult.getInt("id");
                definition.definition = queryResult.getString("definition");
                definition.wordRef = queryResult.getInt("word_ref");
                definition.wordType = queryResult.getInt("word_type");
                result.add(definition);
            }
        } catch (SQLException ignored) {
        }

        return result;
    }

    private int insertDefinition(Definition definition) {
        DBUtils.query(
                """
                        INSERT INTO [definitions]([definition], [word_ref], [word_type])
                        VALUES(?,?,?)
                        """,
                new Object[]{definition.definition, definition.wordRef, definition.wordType}
        );

        var fetchIdResult = DBUtils.query(
                """
                        SELECT MAX([id]) AS [id] FROM [definitions]
                        """,
                new Object[]{}
        );

        if (fetchIdResult == null) return -1;

        try {
            if (fetchIdResult.next())
                return fetchIdResult.getInt("id");
            else
                return -1;
        } catch (SQLException ignored) {
            return -1;
        }
    }

    @Override
    public JSObject getDefinitionsByWordId(int wordId) {
        var engine = EngineContainer.getEngine();

        var definitions = query(
                """
                        SELECT * FROM [definitions]
                        WHERE [word_ref] = ?
                        """,
                new Object[]{wordId}
        );

        var jsDefinitions = JSObjectUtils.newArray();

        for (var definition : definitions) {
            var jsDefinition = JSObjectUtils.newObject();
            jsDefinition.setMember("id", definition.id);
            jsDefinition.setMember("definition", definition.definition);
            jsDefinition.setMember("wordType", definition.wordType);
            jsDefinition.setMember(
                    "examples", exampleService.getExamplesByDefinitionId(definition.id));
            jsDefinition.setMember(
                    "relations", relationService.getRelationsByDefinitionId(definition.id));

            jsDefinitions.call("push", jsDefinition);
        }

        return jsDefinitions;
    }

    @Override
    public JSObject getWordTypeReferences() {
        var result = JSObjectUtils.newObject();

        for (var entry : Definition.WordTypes.typeRefs.entrySet())
            result.setMember(entry.getKey().toString(), entry.getValue());

        return result;
    }

    @Override
    public void addDefinition(int wordId, JSObject obj) {
        var definition = new Definition();
        definition.wordRef = wordId;
        definition.definition = (String) obj.getMember("definition");
        definition.wordType = (Integer) obj.getMember("wordType");

        definition.id = insertDefinition(definition);

        JSObjectUtils.forEach(
                (JSObject) obj.getMember("examples"),
                (exm) -> {
                    exampleService.addExample(definition.id, exm);

                    return null;
                }
        );

        JSObjectUtils.forEach(
                (JSObject) obj.getMember("relations"),
                (rel) -> {
                    relationService.addRelation(
                            definition.id,
                            (Integer) rel.getMember("id")
                    );

                    return null;
                }
        );
    }
}
