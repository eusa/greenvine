package net.sourceforge.greenvine.generator.helper;

import net.sourceforge.greenvine.model.api.Column;

public abstract class AbstractJdbcType implements JdbcType {

    protected final static DataHelper dataHelper = new DataHelper();
    
    protected final Column column;
    
    public AbstractJdbcType(Column column) {
        this.column = column;
    }

}
