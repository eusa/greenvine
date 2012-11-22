package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public abstract class AbstractIntegerJdbcType extends AbstractJdbcType {

    public AbstractIntegerJdbcType(Column column) {
        super(column);
    }
    
    @Override
    public String getCreateData() {
        return dataHelper.getCreateInteger();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateInteger();
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return false;
    }

}
