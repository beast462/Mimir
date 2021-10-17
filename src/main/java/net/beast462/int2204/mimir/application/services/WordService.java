package net.beast462.int2204.mimir.application.services;

import net.beast462.int2204.mimir.application.controllers.RootController;
import net.beast462.int2204.mimir.application.interfaces.IWordService;
import net.beast462.int2204.mimir.core.DataConnection;
import net.beast462.int2204.mimir.core.models.Word;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class WordService implements IWordService {
    private List<Word> query(String query, Object[] params) {
        var result = new LinkedList<Word>();
        var connection = DataConnection.getInstance().getConnection();
        ResultSet queryResult = null;

        try {
            var statement = connection.prepareStatement(
                    query
            );

            for (int i = 0; i < params.length; ++i) {
                var param = params[i];

                if (param instanceof String)
                    statement.setString(i + 1, (String) param);

                if (param instanceof Integer)
                    statement.setInt(i + 1, (Integer) param);

                if (param instanceof Double)
                    statement.setDouble(i + 1, (Double) param);
            }
            queryResult = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assert queryResult != null;

        try {
            while (queryResult.next()) {
                var word = new Word();
                word.id = queryResult.getInt("id");
                word.content = queryResult.getString("content");
                word.pronunciation = queryResult.getString("pronunciation");
                result.add(word);
            }
        } catch (SQLException ignored) {
        }

        return result;
    }

    private JSObject makeResult(List<Word> words) {
        var array = new JSONArray();

        for (var word : words) {
            var obj = new JSONObject();

            for (var field : Word.class.getFields()) {
                try {
                    obj.put(field.getName(), field.get(word));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            array.put(obj);
        }

        return (JSObject) RootController.getEngine().executeScript(array.toString());
    }

    public JSObject absoluteSearch(String text) {
        var words = query(
                "SELECT * FROM [words] WHERE content = ?",
                new Object[]{text}
        );

        return makeResult(words);
    }

    public JSObject advanceSearch(String text) {
        var words = query(
                """
                        SELECT * FROM [words]
                        WHERE content LIKE ? || '%'
                        ORDER BY content ASC
                        LIMIT ?
                        """,
                new Object[]{text, 20}
        );

        return makeResult(words);
    }
}
