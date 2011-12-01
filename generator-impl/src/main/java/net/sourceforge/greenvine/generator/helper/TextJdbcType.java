package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class TextJdbcType extends AbstractJdbcType {

    public TextJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateString();
    }
    
    public String getUpdateData() {
        return dataHelper.getUpdateString();
    }

    public String getRandomData() {
        return dataHelper.getRandomString(column.getPrecision());
    }

}
