package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class IntegerJdbcType extends AbstractIntegerJdbcType {

    public IntegerJdbcType(Column column) {
        super(column);
    }

    public String getRandomData() {
        return dataHelper.getRandomInteger();
    }

}
