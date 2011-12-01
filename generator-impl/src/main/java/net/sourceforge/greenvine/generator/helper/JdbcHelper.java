package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;

public class JdbcHelper {
    
    public JdbcType getJdbcTypeForColumn(Column column) {
        ColumnType colType = column.getColumnType();
        JdbcType testData = null;
        switch (colType) {
            case BIGINT:
                testData = new BigIntegerJdbcType(column);
            break;
            case BIT:
                testData = new BooleanJdbcType(column);
                break;
            case BLOB:
                testData = new BinaryJdbcType(column);
                break;
            case BOOLEAN:
                testData = new BooleanJdbcType(column);
                break;
            case VARBINARY:
                testData = new BinaryJdbcType(column);
                break;
            case CHARACTER:
                testData = new TextJdbcType(column);
                break;  
            case CLOB:
                testData = new TextJdbcType(column);
                break;
            case DATE:
                testData = new DateJdbcType(column);
                break;  
            case DECIMAL:
                testData = new DecimalJdbcType(column);
                break;
            case DOUBLE:
                testData = new DecimalJdbcType(column);
                break;  
            case INTEGER:
                testData = new IntegerJdbcType(column);
                break;          
            case NUMERIC:
                testData = new DecimalJdbcType(column);
                break;  
            case REAL:
                testData = new DecimalJdbcType(column);
                break;  
            case SMALLINT:
                testData = new SmallIntegerJdbcType(column);
                break;  
            case TIME:
                testData = new TimeJdbcType(column); 
                break;  
            case TIMESTAMP:
                testData = new TimestampJdbcType(column); 
                break; 
            case TINYINT:
                testData = new TinyIntegerJdbcType(column);
                break; 
            case VARCHAR:
                testData = new TextJdbcType(column);
                break;
            case LONGVARCHAR:
                testData = new TextJdbcType(column);
                break;  
            default:
                throw new IllegalArgumentException("Unrecognised type: " + colType);
        }
        return testData;
    }
    
}
