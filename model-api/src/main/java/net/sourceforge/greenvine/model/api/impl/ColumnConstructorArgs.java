package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.naming.ColumnName;

public class ColumnConstructorArgs {
    
    private final ColumnName name;
    private final ColumnType columnType;
    private final boolean notNull;
    private final int scale;
    private final int precision;
    private final ColumnValueGenerationStrategy columnValueGenerationStrategy;
    
    public ColumnConstructorArgs(ColumnName name, ColumnType columnType,
            boolean notNull, int scale, int precision,
            ColumnValueGenerationStrategy columnValueGenerationStrategy) {
        super();
        this.name = name;
        this.columnType = columnType;
        this.notNull = notNull;
        this.scale = scale;
        this.precision = precision;
        this.columnValueGenerationStrategy = columnValueGenerationStrategy;
    }
    
   
    
    public ColumnName getName() {
        return name;
    }
    public ColumnType getColumnType() {
        return columnType;
    }
    public boolean isNotNull() {
        return notNull;
    }
    public int getScale() {
        return scale;
    }
    public int getPrecision() {
        return precision;
    }
    public ColumnValueGenerationStrategy getColumnValueGenerationStrategy() {
        return columnValueGenerationStrategy;
    }
    
}
