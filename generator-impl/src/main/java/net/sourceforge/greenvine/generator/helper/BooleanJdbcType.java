package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class BooleanJdbcType extends AbstractJdbcType {

    public BooleanJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateBoolean();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateBoolean();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomBoolean();
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return false;
    }

}
