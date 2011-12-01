package net.sourceforge.greenvine.model.api;

public enum PropertyType {
    
    BIG_DECIMAL,
    BINARY,
    BOOLEAN,
    BLOB,
    BYTE, 
    CHARACTER,
    CLOB,
    CURRENCY,
    DATE,
    DOUBLE, 
    FLOAT,
    INTEGER, 
    LONG, 
    SHORT, 
    STRING, 
    TEXT, 
    TIME, 
    TIMESTAMP;
    
    // These types are shit. Fact.
  //CALENDAR, 
    //CALENDAR_DATE,
  //LOCALE, 
    //TIMEZONE, 
    //CLASS, 
  //SERIALIZABLE, 
    
    
    public ColumnType getColumnType(){
        ColumnType dbType = null;
        switch (this) {
        case BIG_DECIMAL:
            dbType = ColumnType.DECIMAL;
            break;
        case BINARY:
            dbType = ColumnType.BINARY;
            break;
        case BLOB:
            dbType = ColumnType.BLOB;
            break;
        case BOOLEAN:
            dbType = ColumnType.BOOLEAN;
            break;
        case BYTE:
            dbType = ColumnType.TINYINT;
            break;
        //case CALENDAR_DATE:
        //    dbType = ColumnType.DATE;
        //    break;
        //case CALENDAR:
        //    dbType = ColumnType.TIMESTAMP;
        //    break;
        case CHARACTER:
            dbType = ColumnType.CHARACTER;
            break;
        //case CLASS:
        //    dbType = ColumnType.VARCHAR;
        //    break;
        case CLOB:
            dbType = ColumnType.CLOB;
            break;
        case CURRENCY:
            dbType = ColumnType.VARCHAR;
            break;
        case DATE:
            dbType = ColumnType.DATE;
            break;
        case DOUBLE:
            dbType = ColumnType.DOUBLE;
            break;
        case FLOAT:
            dbType = ColumnType.FLOAT;
            break;
        case INTEGER:
            dbType = ColumnType.INTEGER;
            break;
        //case LOCALE:
        //    dbType = ColumnType.VARCHAR;
        //    break;
        case LONG:
            dbType = ColumnType.BIGINT;
            break;
        //case SERIALIZABLE:
        //    dbType = ColumnType.BLOB;
        //    break;
        case SHORT:
            dbType = ColumnType.SMALLINT;
            break;
        case STRING:
            dbType = ColumnType.VARCHAR;
            break;
        case TEXT:
            dbType = ColumnType.LONGVARCHAR;
            break;
        case TIME:
            dbType = ColumnType.TIME;
            break;
        case TIMESTAMP:
            dbType = ColumnType.TIMESTAMP;
            break;
        //case TIMEZONE:
        //    dbType = ColumnType.VARCHAR;
        //    break;
        }
        return dbType;
    }

}
