package net.sourceforge.greenvine.model.api;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class ColumnTypeTest {
    
    private final ColumnType[] exceptions = new ColumnType[]{
            ColumnType.CHARACTER, ColumnType.BIT, ColumnType.CLOB, ColumnType.LONGVARBINARY, ColumnType.LONGVARCHAR, ColumnType.NUMERIC, ColumnType.REAL, ColumnType.VARBINARY
    };
    
    @Test
    public void testTypeMappingConsistent() throws ModelException {
        for (ColumnType type : ColumnType.values()) {
            PropertyType propType = type.getPropertyType();
            if (!isExcluded(type)) {
                Assert.assertEquals(type, propType.getColumnType());
            }
        }
    }
    
    public boolean isExcluded(ColumnType columnType) {
        return Arrays.asList(exceptions).contains(columnType);
    }

}
