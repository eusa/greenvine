package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class DateJdbcType extends AbstractJdbcType {

    public DateJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateDateString();
    }

    public String getRandomData() {
        return dataHelper.getRandomDateString();
    }

    public String getUpdateData() {
        return dataHelper.getUpdateDateString();
    }

}
