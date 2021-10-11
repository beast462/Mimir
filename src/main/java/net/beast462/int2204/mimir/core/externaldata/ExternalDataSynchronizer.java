package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.Main;
import net.beast462.int2204.mimir.core.DataConnection;
import net.beast462.int2204.mimir.core.Logger;
import net.beast462.int2204.mimir.core.models.Definition;
import net.beast462.int2204.mimir.core.models.DefinitionExample;
import net.beast462.int2204.mimir.core.models.Word;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ExternalDataSynchronizer {
    private final Connection connection;
    private final Logger logger;

    public ExternalDataSynchronizer() {
        connection = DataConnection.getInstance().getConnection();
        logger = Main.getLogger();
    }

    private void synchronizeWords(List<Word> words) throws SQLException {
        logger.info(String.format("Synchronizing words, found %d", words.size()));

        var statement = connection.prepareStatement(
                "INSERT INTO [words]([id], [content], [pronunciation]) VALUES(?, ?, ?)"
        );

        for (final var word : words) {
            statement.setInt(1, word.id);
            statement.setString(2, word.content);
            statement.setString(3, word.pronunciation);
            statement.addBatch();
        }

        statement.executeBatch();
        connection.commit();
        logger.info("All words synchronized");
    }

    private void synchronizeDefinitions(List<Definition> definitions) throws SQLException {
        logger.info(String.format("Synchronizing definitions, found %d", definitions.size()));

        var statement = connection.prepareStatement(
                "INSERT INTO [definitions]([id], [definition], [wordRef], [wordType]) VALUES(?, ?, ?, ?)"
        );

        for (final var definition : definitions) {
            statement.setInt(1, definition.id);
            statement.setString(2, definition.definition);
            statement.setInt(3, definition.wordRef);
            statement.setInt(4, definition.wordType);
            statement.addBatch();
        }

        statement.executeBatch();
        connection.commit();
        logger.info("All definitions synchronized");
    }

    private void synchronizeExamples(List<DefinitionExample> examples) throws SQLException {
        logger.info(String.format("Synchronizing examples, found %d", examples.size()));

        var statement = connection.prepareStatement(
                "INSERT INTO [definitionExamples]([id], [english], [vietnamese], [definitionRef]) VALUES(?, ?, ?, ?)"
        );

        for (final var example : examples) {
            statement.setInt(1, example.id);
            statement.setString(2, example.english);
            statement.setString(3, example.vietnamese);
            statement.setInt(4, example.definitionRef);
            statement.addBatch();
        }

        statement.executeBatch();
        connection.commit();
        logger.info("All examples synchronized");
    }

    public void synchronize(ExternalData data) {
        try {
            connection.setAutoCommit(false);
            synchronizeWords(data.words);
            synchronizeDefinitions(data.definitions);
            synchronizeExamples(data.examples);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
