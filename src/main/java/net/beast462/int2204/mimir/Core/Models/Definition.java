package net.beast462.int2204.mimir.Core.Models;

import net.beast462.int2204.mimir.Core.Annotations.SQL.Column;
import net.beast462.int2204.mimir.Core.Annotations.SQL.Table;

@Table(name = "definitions")
public class Definition {
    @Column(primary = true)
    public int id;

    @Column
    public String definition;

    @Column(name = "word_ref")
    public int wordRef;

    @Column(name = "word_type")
    public int wordType;

    @Column
    public String example;
}
