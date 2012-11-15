package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import java.util.List;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JavaHelper;
import net.sourceforge.greenvine.generator.helper.JavaType;
import net.sourceforge.greenvine.generator.helper.JdbcHelper;
import net.sourceforge.greenvine.generator.helper.JdbcType;
import net.sourceforge.greenvine.generator.runner.impl.SourceConfig;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnConstraint;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.Table;


public class IntegrationTestDataGenerator implements Generator {
	
    private final SourceConfig sourceConfig;
    private final JdbcHelper jdbcHelper = new JdbcHelper();
    
    public IntegrationTestDataGenerator(SourceConfig sourceConfig) {
        this.sourceConfig = sourceConfig;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
	    for (Catalog catalog : model.getCatalogs()) {
    		
	        // Create a model helper and extract the database
    		Database db = catalog.getDatabase();
    		
    		// Create java utility helper class
            JavaHelper javaHelper = new JavaHelper(sourceConfig);
    		
            // Iterate through entities and create mappings
            for (Entity entity :  catalog.getEntities()) {
            
                // Collection of dependent tables
                if (entity.getTable() instanceof Table) {
                    
                    // Entity type
                    JavaType entityType = javaHelper.getEntityType(entity);
                    
                    // Entity name for file name
                    String entityName = entityType.getClassName();
                    String directory = javaHelper.packageToFolder(entityType.getPackageName());
                    
                    // Create method data sets
                    DataSet beforeCreate = getBeforeCreate(entity);
            		createDataSetFile(template, queue, db, directory,
            		        beforeCreate, "BeforeCreate", entityName);
            		
            		DataSet afterCreate = getAfterCreate(entity);
            		createDataSetFile(template, queue, db, directory,
            		        afterCreate, "AfterCreate", entityName);
            		
            		// Update method data sets
            		DataSet beforeUpdate= getAfterCreate(entity);
                    createDataSetFile(template, queue, db, directory,
                            beforeUpdate, "BeforeUpdate", entityName);
                    
                    DataSet afterUpdate= getAfterUpdate(entity);
                    createDataSetFile(template, queue, db, directory,
                            afterUpdate, "AfterUpdate", entityName);
                    
                    // Delete method data sets
                    DataSet beforeDelete = getAfterCreate(entity);
                    createDataSetFile(template, queue, db, directory,
                           beforeDelete, "BeforeDelete", entityName);
                    
                    DataSet afterDelete = getBeforeCreate(entity);
                    createDataSetFile(template, queue, db, directory,
                            afterDelete, "AfterDelete", entityName);
                    
                    // Find method data set
                    DataSet find = getAfterCreate(entity);
                    createDataSetFile(template, queue, db, directory,
                            find, "Find", entityName);
                    
                    // Find all method data set
                    DataSet findAll = getFindAll(entity);
                    createDataSetFile(template, queue, db, directory,
                            findAll, "FindAll", entityName);
                }
            }
	    }

	}
	
    private DataSet getBeforeCreate(Entity entity) {
        
        // Create data set
        DataSet dataSet = new DataSet();
        
        // populate dependent tables with default data
        populateDendendentTables(dataSet, entity.getTable().getDependentTablesInDependencyOrder());
        
        // Return populated data set
        return dataSet;
    }
    
    private DataSet getAfterCreate(Entity entity) {
        
        // Create data set
        DataSet dataSet = new DataSet();
        
        // populate dependent tables with default data
        populateDendendentTables(dataSet, entity.getTable().getDependentTablesInDependencyOrder());
        
        // add last row for the entity table
        populateTableWithDefaultData(dataSet, entity.getTable());
        
        // Return populated data set
        return dataSet;
    }
    
    private DataSet getAfterUpdate(Entity entity) {
        
        // Create data set
        DataSet dataSet = new DataSet();
        
        // populate dependent tables with default data
        populateDendendentTables(dataSet, entity.getTable().getDependentTablesInDependencyOrder());
        
        // add last row for the entity table
        populateTableWithUpdateData(dataSet, entity.getTable());
        
        // Return populated data set
        return dataSet;
    }

    private DataSet getFindAll(Entity entity) {
        
        // Create data set
        DataSet dataSet = new DataSet();
        
        // populate dependent tables with 100 rows of random data
        for (Table table : entity.getTable().getDependentTablesInDependencyOrder()) {
            populateTableWithRandomData(dataSet, table, 100);
        }
        
        // populate entity table with random data
        populateTableWithRandomData(dataSet, entity.getTable(), 100);
        
        // Return populated data set
        return dataSet;
    }

    private void populateDendendentTables(DataSet dataSet, List<? extends Table> tables) {
    	for (Table table : tables) {
            populateTableWithDefaultData(dataSet, table);
        }
        
    }

    private void populateTableWithDefaultData(DataSet dataSet, Table table) {
        TableData tableData = dataSet.createTable(table);
        RowData row = getDefaultRowData(tableData);
        tableData.addRow(row);
    }
    
    private void populateTableWithUpdateData(DataSet dataSet, Table table) {
        TableData tableData = dataSet.createTable(table);
        RowData row = getUpdateRowData(tableData);
        tableData.addRow(row);
    }
    
    private void populateTableWithRandomData(DataSet dataSet, Table table, int rows) {
        TableData tableData = dataSet.createTable(table);
        while (tableData.getRowCount() < rows) {
            RowData row = getRandomRowData(tableData);
            tableData.addRow(row);
        }
    }

    private RowData getDefaultRowData(TableData table) {
        JdbcHelper jdbcHelper = new JdbcHelper();
        RowData row = new RowData(table.getMetadata());
        for (Column column : table.getColumns()) {
            JdbcType type = jdbcHelper.getJdbcTypeForColumn(column);
            String value = type.getCreateData();
            row.put(column.getName(), value);
        }
        return row;
    }
    
    private RowData getUpdateRowData(TableData table) {
        RowData row = new RowData(table.getMetadata());
        
        // Set foreign columns
        for (Column column : table.getForeignKeyColumns()) {
            putCreateData(row, column);
        }
        
        // Set immutable primary columns (if not set already)
        for (Column column : table.getPrimaryKeyColumns()) {
            putCreateDataIfAbsent(row, column);
        }
        
        // Set immutable natural key columns (if not set already)
        for (Column column : table.getNaturalKeyColumns()) {
            // TODO this breaks if NaturalIdentities are not enabled
        	//putCreateDataIfAbsent(row, column);
        	putUpdateDataIfAbsent(row, column);
        }
        
        // Set other columns (if not set already)
        for (Column column : table.getColumns()) {
            putUpdateDataIfAbsent(row, column);
        }
        return row;
    }

    private void putCreateData(RowData row, Column column) {
        if (column.getNotNull()) {
            JdbcType type = jdbcHelper.getJdbcTypeForColumn(column);
            row.put(column.getName(), type.getCreateData());
        }
    }
    
    private void putUpdateData(RowData row, Column column) {
        if (column.getNotNull()) {
            JdbcType type = jdbcHelper.getJdbcTypeForColumn(column);
            row.put(column.getName(), type.getUpdateData());
        }
    }
    
    private void putCreateDataIfAbsent(RowData row, Column column) {
        if (column.getNotNull()) {
            if (!row.hasValue(column.getName())) {
                putCreateData(row, column);
            }
        }
    }
    
    private void putUpdateDataIfAbsent(RowData row, Column column) {
        if (column.getNotNull()) {
            if (!row.hasValue(column.getName())) {
                putUpdateData(row, column);
            }
        }
    }
    
    private RowData getRandomRowData(TableData table) {
        RowData row = new RowData(table.getMetadata());
        DataSet dataSet = table.getDataSet();
        for (ForeignKey foreign : table.getForeignKeys()) {
            TableData referenced = dataSet.getTable(foreign.getReferencedTable().getName()); 
            for (ColumnConstraint constraint : foreign.getColumnConstraints()) {
                Column referencingCol = constraint.getReferencingColumn();
                if (referencingCol.getNotNull()) {
                    Column referencedCol = constraint.getReferencedColumn();
                    String value = referenced.getRandomValue(referencedCol.getName());
                    row.put(referencingCol.getName(), value);
                }
            }
        }
        for (Column col : table.getColumns()) {
            if (col.getNotNull()) {
                if (!row.hasValue(col.getName())) {
                    JdbcType type = jdbcHelper.getJdbcTypeForColumn(col);
                    row.put(col.getName(), type.getRandomData());
                }
            }
        }
        
        return row;
    }

    private void createDataSetFile(Template template, TemplateTaskQueue queue,
            Database database, String directory,
            DataSet dataSet, String dataSetName, String entityName) throws ModelException {
        
        // Create TemplateContext
        TemplateContext context = new TemplateContext();
        context.put("dataSet", dataSet);
        context.put("jdbcHelper", new JdbcHelper());
        context.put("database", database);
        
        // Merge the template
        queue.enqueue(template, context, directory, entityName + dataSetName + "DataSet.xml");
    }
}
