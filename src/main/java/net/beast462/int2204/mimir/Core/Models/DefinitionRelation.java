package net.beast462.int2204.mimir.Core.Models;

import net.beast462.int2204.mimir.Core.Annotations.SQL.Column;
import net.beast462.int2204.mimir.Core.Annotations.SQL.Table;

@Table(name = "definition_relations")
public class DefinitionRelation {
    @Column(primary = true, unique = true)
    public int id;

    @Column
    public int firstEnd;

    @Column
    public int secondEnd;
}
