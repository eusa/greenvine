package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TimestampJdbcType extends AbstractJdbcType {

    public TimestampJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateTimestampString();
    }

    public String getUpdateData() {
        return dataHelper.getUpdateTimestampString();
    }

    public String getRandomData() {
        return dataHelper.getRandomTimestampString();
    }

}
