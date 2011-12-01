package net.sourceforge.greenvine.model.naming;



public class RdbmsNamingConventions {

    private RdbmsNamingConvention database;
    private RdbmsNamingConvention table;
    private RdbmsNamingConvention view;
    private RdbmsNamingConvention primaryColumn;
    private RdbmsNamingConvention foreignColumn;
    private RdbmsNamingConvention dataColumn;
    private RdbmsNamingConvention foreignKey;
    private RdbmsNamingConvention primaryKey;
    private RdbmsNamingConvention uniqueKey;
    
    
    public RdbmsNamingConvention getTable() {
        return this.table;
    }

    public RdbmsNamingConvention getView() {
        return this.view;
    }

    public RdbmsNamingConvention getPrimaryColumn() {
        return this.primaryColumn;
    }

    public RdbmsNamingConvention getForeignColumn() {
        return this.foreignColumn;
    }

    public RdbmsNamingConvention getDataColumn() {
        return this.dataColumn;
    }
    
    public RdbmsNamingConvention getForeignKey() {
        return this.foreignKey;
    }
    
    public RdbmsNamingConvention getPrimaryKey() {
        return this.primaryKey;
    }
    
    public RdbmsNamingConvention getUniqueKey() {
        return this.uniqueKey;
    }
    
    public RdbmsNamingConvention getDatabase() {
        return this.database;
    }

    public void setTable(RdbmsNamingConvention table) {
        this.table = table;
    }
    
    public void setView(RdbmsNamingConvention view) {
        this.view = view;
    }
    
    public void setPrimaryColumn(RdbmsNamingConvention primaryColumn) {
        this.primaryColumn = primaryColumn;
    }
    
    public void setForeignColumn(RdbmsNamingConvention foreignColumn) {
        this.foreignColumn = foreignColumn;
    }
    public void setDataColumn(RdbmsNamingConvention dataColumn) {
        this.dataColumn = dataColumn;
    }
    
    public void setForeignKey(RdbmsNamingConvention foreignKey) {
        this.foreignKey = foreignKey;
    }
    
    public void setUniqueKey(RdbmsNamingConvention uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
    
    public void setPrimaryKey(RdbmsNamingConvention primaryKey) {
        this.primaryKey = primaryKey;
    }
    
    public void setDatabase(RdbmsNamingConvention database) {
        this.database = database;
    }

    public static RdbmsNamingConventions getDefault() {
        RdbmsNamingConventions nc = new RdbmsNamingConventions();
        nc.setDatabase(RdbmsNamingConvention.getDefault());
        nc.setTable(RdbmsNamingConvention.getDefault());
        nc.setView(RdbmsNamingConvention.getDefault());
        nc.setPrimaryColumn(RdbmsNamingConvention.getDefault());
        nc.setForeignColumn(RdbmsNamingConvention.getDefault());
        nc.setDataColumn(RdbmsNamingConvention.getDefault());
        nc.setForeignKey(RdbmsNamingConvention.getDefault());
        nc.setPrimaryKey(RdbmsNamingConvention.getDefault());
        nc.setUniqueKey(RdbmsNamingConvention.getDefault());
        return nc;
    }

}
