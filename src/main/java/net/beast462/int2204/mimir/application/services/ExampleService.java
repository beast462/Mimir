package net.beast462.int2204.mimir.application.services;

import net.beast462.int2204.mimir.application.interfaces.IExampleService;
import net.beast462.int2204.mimir.core.DBUtils;
import net.beast462.int2204.mimir.core.models.DefinitionExample;
import net.beast462.int2204.mimir.core.webview.JSObjectUtils;
import netscape.javascript.JSObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ExampleService implements IExampleService {
    private List<DefinitionExample> query(String query, Object[] params) {
        var result = new LinkedList<DefinitionExample>();
        var queryResult = DBUtils.query(query, params);

        try {
            while (queryResult.next()) {
                var example = new DefinitionExample();
                example.id = queryResult.getInt("id");
                example.english = queryResult.getString("english");
                example.vietnamese = queryResult.getString("vietnamese");
                example.definitionRef = queryResult.getInt("definition_ref");
                result.add(example);
            }
        } catch (SQLException ignored) {
        }

        return result;
    }

    private void insertExample(DefinitionExample example) {
        DBUtils.query(
                """
                        INSERT INTO [definition_examples](
                            [english], [vietnamese], [definition_ref]
                        ) VALUES(?,?,?)
                        """,
                new Object[]{example.english, example.vietnamese, example.definitionRef}
        );
    }

    @Override
    public JSObject getExamplesByDefinitionId(int defId) {
        var examples = query(
                """
                        SELECT * FROM [definition_examples]
                        WHERE [definition_ref] = ?
                        """,
                new Object[]{defId}
        );

        var jsExamples = JSObjectUtils.newArray();

        for (var example : examples) {
            var jsExample = JSObjectUtils.newObject();
            jsExample.setMember("id", example.id);
            jsExample.setMember("english", example.english);
            jsExample.setMember("vietnamese", example.vietnamese);

            jsExamples.call("push", jsExample);
        }

        return jsExamples;
    }

    @Override
    public void addExample(int definitionId, JSObject obj) {
        var example = new DefinitionExample();

        example.definitionRef = definitionId;
        example.english = (String) obj.getMember("english");
        example.vietnamese = (String) obj.getMember("vietnamese");

        insertExample(example);
    }
}
