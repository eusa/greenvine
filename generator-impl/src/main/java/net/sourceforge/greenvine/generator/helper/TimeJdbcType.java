package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TimeJdbcType extends AbstractJdbcType {

    public TimeJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateTimeString();
    }

    public String getUpdateData() {
        return dataHelper.getUpdateTimeString();
    }

    public String getRandomData() {
        return dataHelper.getRandomTimeString();
    }

}
