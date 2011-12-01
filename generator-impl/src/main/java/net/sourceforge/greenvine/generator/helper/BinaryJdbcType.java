package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public class BinaryJdbcType extends AbstractJdbcType {

    public BinaryJdbcType(Column column) {
        super(column);
    }

    public String getCreateData() {
        return dataHelper.getCreateBinaryBase64();
    }

    public String getUpdateData() {
        return dataHelper.getUpdateBinaryBase64();
    }

    public String getRandomData() {
        return dataHelper.getRandomBinaryBase64(column.getPrecision());
    }

}
