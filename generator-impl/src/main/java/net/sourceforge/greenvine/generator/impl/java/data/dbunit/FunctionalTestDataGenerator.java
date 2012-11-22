package net.sourceforge.greenvine.generator.impl.java.data.dbunit;

import java.util.Collection;

import net.sourceforge.greenvine.generator.Generator;
import net.sourceforge.greenvine.generator.helper.JdbcHelper;
import net.sourceforge.greenvine.generator.helper.JdbcType;
import net.sourceforge.greenvine.generator.task.TemplateTaskQueue;
import net.sourceforge.greenvine.generator.template.Template;
import net.sourceforge.greenvine.generator.template.TemplateContext;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnConstraint;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.Table;

// TODO this is in the wrong package!
public class FunctionalTestDataGenerator implements Generator {

    private final JdbcHelper jdbcHelper = new JdbcHelper();
    
    private final int rowsPerTable;
    
    public FunctionalTestDataGenerator(final int rowsPerTable) {
        this.rowsPerTable = rowsPerTable;
    }
    
	public void generate(Model model, Template template, TemplateTaskQueue queue) throws Exception {
		
	    for (Catalog catalog : model.getCatalogs()) {
    		
	        // Create a model helper and extract the database
    		Database db = catalog.getDatabase();
    		
            // Find all method data set
            DataSet data = getDataSet(db.getTablesInDependencyOrder());
            createDataSetFile(template, queue, db,
                    data);
        }
	}
	
    private DataSet getDataSet(Collection<? extends Table> tables) {
        
        // Create data set
        DataSet dataSet = new DataSet();
        
        // populate dependent tables with 100 rows of random data
        for (Table table : tables) {
            populateTableWithRandomData(dataSet, table, rowsPerTable); 
        }
        
        // Return populated data set
        return dataSet;
    }

    

    
    
    private void populateTableWithRandomData(DataSet dataSet, Table table, int rows) {
        TableData tableData = dataSet.createTable(table);
        while (tableData.getRowCount() < rows) {
            RowData row = getRandomRowData(tableData);
            tableData.addRow(row);
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
            Database database,
            DataSet dataSet) {
        
        // Create TemplateContext
        TemplateContext context = new TemplateContext();
        context.put("dataSet", dataSet);
        context.put("database", database);
        context.put("jdbcHelper", jdbcHelper);
        
        // Merge the template
        queue.enqueue(template, context, null, "data.sql");
    }
}
