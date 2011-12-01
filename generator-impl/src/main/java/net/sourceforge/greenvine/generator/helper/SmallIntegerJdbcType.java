package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class SmallIntegerJdbcType extends AbstractIntegerJdbcType {

    public SmallIntegerJdbcType(Column column) {
        super(column);
    }

    public String getRandomData() {
        return dataHelper.getRandomSmallInteger();
    }

}
