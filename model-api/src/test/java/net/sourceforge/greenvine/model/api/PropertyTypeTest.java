package net.sourceforge.greenvine.model.api;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

public class PropertyTypeTest {
    
    private final PropertyType[] exceptions = new PropertyType[]{
            PropertyType.CHARACTER, PropertyType.CLOB, PropertyType.CURRENCY, PropertyType.TEXT
    };

    @Test
    public void testTypeMappingConsistent() throws ModelException {
        for (PropertyType type : PropertyType.values()) {
            ColumnType colType = type.getColumnType();
            if (!isExcluded(type)) {
                Assert.assertEquals(type, colType.getPropertyType());
            }
        }
    }
    
    private boolean isExcluded(PropertyType propertyType) {
        return Arrays.asList(exceptions).contains(propertyType);
    }
    
}
