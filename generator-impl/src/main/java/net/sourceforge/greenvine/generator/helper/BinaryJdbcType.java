package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class BinaryJdbcType extends AbstractJdbcType {

    public BinaryJdbcType(Column column) {
        super(column);
    }

    @Override
    public String getCreateData() {
        return dataHelper.getCreateBinaryBase64();
    }

    @Override
    public String getUpdateData() {
        return dataHelper.getUpdateBinaryBase64();
    }

    @Override
    public String getRandomData() {
        return dataHelper.getRandomBinaryBase64(column.getPrecision());
    }
    
    @Override
    public Boolean getEnquoteLiterals() {
    	return true;
    }

}
