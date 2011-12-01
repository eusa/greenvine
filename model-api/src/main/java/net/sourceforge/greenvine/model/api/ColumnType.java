package net.sourceforge.greenvine.model.api;

public enum ColumnType {
    
    // Initialise with default scale and precision
    
    BIGINT(0, 0), 
    BINARY(1024, 0), 
    INTEGER(0, 0), 
    CHARACTER(1024, 0), 
    BIT(0, 0), 
    BLOB(1024, 0), 
    BOOLEAN(1, 0), 
    CLOB(0, 0), 
    DATE(0, 0), 
    DECIMAL(10, 2), 
    DOUBLE(17, 0), 
    FLOAT(10, 2), 
    LONGVARBINARY(0, 0), 
    LONGVARCHAR(0, 0), 
    NUMERIC(10, 2), 
    REAL(7, 0), 
    SMALLINT(0, 0), 
    TIME(0, 0), 
    TIMESTAMP(0, 0), 
    TINYINT(0, 0), 
    VARBINARY(0, 0), 
    VARCHAR(1024, 0);

    private final Integer defaultScale;
    private final Integer defaultPrecision;
    
    private ColumnType(Integer defaultScale, Integer defaultPrecision) {
        this.defaultScale = defaultScale;
        this.defaultPrecision = defaultPrecision;
    }
    
    public Integer getDefaultScale() {
        return this.defaultScale;
    }
    
    public Integer getDefaultPrecision() {
        return this.defaultPrecision;
    }
    
    public PropertyType getPropertyType() {
        PropertyType propType = null;
        switch (this) {
            case BIGINT:
                propType = PropertyType.LONG;
                break;
            case BINARY:
                propType = PropertyType.BINARY;
                break;
            case BIT:
                propType = PropertyType.BOOLEAN;
                break;
            case BLOB:
                propType = PropertyType.BLOB;
                break;
            case BOOLEAN:
                propType = PropertyType.BOOLEAN;
                break;
            case CHARACTER:
                propType = PropertyType.STRING;
                break;  
            case CLOB:
                propType = PropertyType.TEXT;
                break;  
            case DATE:
                propType = PropertyType.DATE;
                break;  
            case DECIMAL:
                propType = PropertyType.BIG_DECIMAL;
                break;
            case DOUBLE:
                propType = PropertyType.DOUBLE;
                break;  
            case FLOAT:
                propType = PropertyType.FLOAT;
                break;
            case INTEGER:
                propType = PropertyType.INTEGER;
                break;
            case LONGVARBINARY:
                propType = PropertyType.BLOB;
                break;  
            case LONGVARCHAR:
                propType = PropertyType.STRING;
                break;      
            case NUMERIC:
                propType = PropertyType.BIG_DECIMAL;
                break;  
            case REAL:
                propType = PropertyType.FLOAT;
                break;  
            case SMALLINT:
                propType = PropertyType.SHORT;
                break;  
            case TIME:
                propType = PropertyType.TIME;
                break;  
            case TIMESTAMP:
                propType = PropertyType.TIMESTAMP;
                break;  
            case TINYINT:
                propType = PropertyType.BYTE;
                break;
            case VARBINARY:
                propType = PropertyType.BINARY;
                break;  
            case VARCHAR:
                propType = PropertyType.STRING;
                break;  
            default:
                throw new RuntimeException("Unrecognised type");
        }
        return propType;
        
    }
}
