package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class BigIntegerJdbcType extends AbstractIntegerJdbcType {

    public BigIntegerJdbcType(Column column) {
        super(column);
    }

    public String getRandomData() {
        return dataHelper.getRandomLongInteger();
    }

}
