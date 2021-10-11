package net.beast462.int2204.mimir.core.externaldata;

import net.beast462.int2204.mimir.core.models.Definition;
import net.beast462.int2204.mimir.core.models.DefinitionExample;
import net.beast462.int2204.mimir.core.models.Word;

import java.util.LinkedList;
import java.util.List;

public class ExternalData {
    public List<Word> words = new LinkedList<>();
    public List<Definition> definitions = new LinkedList<>();
    public List<DefinitionExample> examples = new LinkedList<>();
}
