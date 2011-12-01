package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TinyIntegerJdbcType extends AbstractIntegerJdbcType {

    public TinyIntegerJdbcType(Column column) {
        super(column);
    }

    public String getRandomData() {
        return dataHelper.getRandomTinyInteger();
    }

}
