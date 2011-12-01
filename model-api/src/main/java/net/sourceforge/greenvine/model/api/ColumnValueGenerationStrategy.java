package net.sourceforge.greenvine.model.api;

public enum ColumnValueGenerationStrategy {
    IDENTITY, SEQUENCE, FUNCTION, ASSIGNED;
    
    public PropertyValueGenerationStrategy getPropertyKeyGenerationStrategy(){
        PropertyValueGenerationStrategy strategy = null;
        switch (this) {
        case ASSIGNED:
            strategy = PropertyValueGenerationStrategy.ASSIGNED;
            break;
        case IDENTITY:
            strategy = PropertyValueGenerationStrategy.IDENTITY;
            break;
        case SEQUENCE:
            strategy = PropertyValueGenerationStrategy.SEQUENCE;
            break;
        case FUNCTION:
            strategy = PropertyValueGenerationStrategy.ASSIGNED;
            break;
        }
        return strategy;
    }
}
