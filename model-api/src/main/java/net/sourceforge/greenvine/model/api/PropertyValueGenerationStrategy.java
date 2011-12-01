package net.sourceforge.greenvine.model.api;

public enum PropertyValueGenerationStrategy {
    
    ASSIGNED, AUTO, IDENTITY, SEQUENCE, TABLE;
    
    public ColumnValueGenerationStrategy getColumnValueGenerationStrategy(){
        ColumnValueGenerationStrategy strategy = null;
        switch (this) {
        case ASSIGNED:
            strategy = ColumnValueGenerationStrategy.ASSIGNED;
            break;
        case AUTO:
            strategy = ColumnValueGenerationStrategy.ASSIGNED;
            break;
        case IDENTITY:
            strategy = ColumnValueGenerationStrategy.IDENTITY;
            break;
        case SEQUENCE:
            strategy = ColumnValueGenerationStrategy.SEQUENCE;
            break;
        case TABLE:
            strategy = ColumnValueGenerationStrategy.ASSIGNED;
            break;
        }
        return strategy;
    }

}
