package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TimeJdbcType extends AbstractJdbcType {

    public TimeJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateTimeString();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateTimeString();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomTimeString();
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return true;
    }

}
