package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TextJdbcType extends AbstractJdbcType {

    public TextJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateString();
    }
    
    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateString();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomString(column.getPrecision());
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return true;
    }

}
