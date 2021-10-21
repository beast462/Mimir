package net.beast462.int2204.mimir.application.services;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import net.beast462.int2204.mimir.application.interfaces.IDefinitionService;
import net.beast462.int2204.mimir.application.interfaces.IWordService;
import net.beast462.int2204.mimir.core.DBUtils;
import net.beast462.int2204.mimir.core.bridge.EngineContainer;
import net.beast462.int2204.mimir.core.models.Word;
import net.beast462.int2204.mimir.core.bridge.JSObjectUtils;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WordService implements IWordService {
    private static Voice voice;
    private final IDefinitionService definitionService;

    public WordService() {
        definitionService = new DefinitionService();

        if (voice == null)
            synchronized (WordService.class) {
                var manager = VoiceManager.getInstance();
                voice = manager.getVoice("kevin16");
                CompletableFuture.runAsync(() -> voice.allocate());
            }
    }

    private List<Word> query(String query, Object[] params) {
        var result = new LinkedList<Word>();
        var queryResult = DBUtils.query(query, params);

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

    private int insertWord(Word word) {
        DBUtils.query(
                """
                        INSERT INTO [words]([content], [pronunciation])
                        VALUES(?,?)
                        """,
                new Object[]{word.content, word.pronunciation}
        );

        var fetchIdResult = DBUtils.query(
                """
                        SELECT MAX([id]) AS [id] FROM [words]
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

        return (JSObject) EngineContainer.getEngine().executeScript(array.toString());
    }

    @Override
    public JSObject absoluteSearch(String text) {
        var words = query(
                "SELECT * FROM [words] WHERE [content] = ?",
                new Object[]{text}
        );

        return makeResult(words);
    }

    @Override
    public JSObject advanceSearch(String text, int limit) {
        var words = query(
                """
                        SELECT * FROM [words]
                        WHERE [content] LIKE ? || '%'
                        ORDER BY [content] ASC
                        LIMIT ?
                        """,
                new Object[]{text, limit}
        );

        return makeResult(words);
    }

    @Override
    public JSObject getWord(int wordId) {
        var words = query(
                """
                        SELECT * FROM [words]
                        WHERE [id] = ?
                        """,
                new Object[]{wordId}
        );

        if (words.size() == 0)
            return null;

        var word = words.get(0);

        var jsWord = JSObjectUtils.newObject();
        jsWord.setMember("id", word.id);
        jsWord.setMember("content", word.content);
        jsWord.setMember("pronunciation", word.pronunciation);

        var definitions = definitionService.getDefinitionsByWordId(word.id);
        jsWord.setMember("definitions", definitions);

        return jsWord;
    }

    @Override
    public int editWord(JSObject obj) {
        var wordContent = (String) obj.getMember("content");

        DBUtils.query(
                """
                        DELETE FROM [words]
                        WHERE [content] = ?
                        """,
                new Object[]{wordContent}
        );

        return addWord(obj);
    }

    @Override
    public int addWord(JSObject obj) {
        var word = new Word();
        word.pronunciation = obj.getMember("pronunciation").toString();
        word.content = obj.getMember("content").toString();

        word.id = insertWord(word);

        JSObjectUtils.forEach(
                (JSObject) obj.getMember("definitions"),
                (def) -> {
                    definitionService.addDefinition(word.id, def);

                    return null;
                }
        );

        return word.id;
    }

    @Override
    public boolean deleteWord(int wordId) {
        var queryResult = DBUtils.query(
                """
                        SELECT [id] FROM [words]
                        WHERE [id] = ?
                        """,
                new Object[]{wordId}
        );

        try {
            if (!queryResult.next()) return false;
        } catch (SQLException ignored) {
            return false;
        }

        DBUtils.query(
                """
                        DELETE FROM [words]
                        WHERE [id] = ?
                        """,
                new Object[]{wordId}
        );

        return true;
    }

    @Override
    public void speak(String word) {
        voice.speak(word);
    }
}
