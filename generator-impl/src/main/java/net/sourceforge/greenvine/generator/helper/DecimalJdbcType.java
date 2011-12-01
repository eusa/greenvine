package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class DecimalJdbcType extends AbstractJdbcType {

    public DecimalJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateDecimal(column.getScale(), column.getPrecision());
    }

    public String getRandomData() {
        return dataHelper.getRandomDecimal(column.getScale(), column.getPrecision());
    }

    public String getUpdateData() {
        return dataHelper.getUpdateDecimal(column.getScale(), column.getPrecision());
    }

}
