package net.beast462.int2204.mimir.application.services;

import net.beast462.int2204.mimir.application.interfaces.IRelationService;
import net.beast462.int2204.mimir.core.DBUtils;
import net.beast462.int2204.mimir.core.models.DefinitionRelation;
import net.beast462.int2204.mimir.core.bridge.JSObjectUtils;
import netscape.javascript.JSObject;

import java.sql.SQLException;

public class RelationService implements IRelationService {
    private void insertRelation(DefinitionRelation relation) {
        DBUtils.query(
                """
                        INSERT INTO [definition_relations](
                            [definition_ref], [word_ref]
                        ) VALUES(?,?)
                        """,
                new Object[]{relation.definitionRef, relation.wordRef}
        );
    }

    @Override
    public void addRelation(int defId, int wordId) {
        var relation = new DefinitionRelation();
        relation.definitionRef = defId;
        relation.wordRef = wordId;

        insertRelation(relation);
    }

    @Override
    public JSObject getRelationsByDefinitionId(int definitionId) {
        var relations = DBUtils.query(
                """
                        SELECT
                            [definition_relations].[id],
                            [words].[content],
                            [words].[id] AS [word_ref]
                        FROM [definition_relations]
                        INNER JOIN [words]
                            ON [words].[id] = [definition_relations].[word_ref]
                        WHERE [definition_ref] = ?
                        """,
                new Object[]{definitionId}
        );

        var result = JSObjectUtils.newArray();

        try {
            while (relations.next()) {
                var jsRelation = JSObjectUtils.newObject();

                jsRelation.setMember("id", relations.getInt("id"));
                jsRelation.setMember("content", relations.getString("content"));
                jsRelation.setMember("wordRef", relations.getString("word_ref"));

                result.call("push", jsRelation);
            }
        } catch (SQLException ignored) {
        }

        return result;
    }
}
