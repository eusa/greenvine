package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class BooleanJdbcType extends AbstractJdbcType {

    public BooleanJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateBoolean();
    }

    public String getUpdateData() {
        return dataHelper.getUpdateBoolean();
    }

    public String getRandomData() {
        return dataHelper.getRandomBoolean();
    }

}
