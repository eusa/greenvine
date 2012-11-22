package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TimestampJdbcType extends AbstractJdbcType {

    public TimestampJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateTimestampString();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateTimestampString();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomTimestampString();
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return true;
    }

}
