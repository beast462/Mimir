package net.beast462.int2204.mimir.Core.Models;

import net.beast462.int2204.mimir.Core.Annotations.SQL.Column;
import net.beast462.int2204.mimir.Core.Annotations.SQL.Table;

@Table(name = "words")
public class Word {
    @Column(primary = true, unique = true)
    public int id;

    @Column(type = Column.Types.Varchar, size = 45, unique = true)
    public String content;

    @Column
    public String pronunciation;
}
