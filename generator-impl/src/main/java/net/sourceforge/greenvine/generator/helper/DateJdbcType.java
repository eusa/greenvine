package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class DateJdbcType extends AbstractJdbcType {

    public DateJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateDateString();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomDateString();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateDateString();
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return true;
    }

}
