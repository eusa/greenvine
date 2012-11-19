package net.sourceforge.greenvine.dbextractor.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

import javax.swing.text.View;

import net.sourceforge.greenvine.dbextractor.DatabaseExtractor;
import net.sourceforge.greenvine.dbextractor.DatabaseExtractorException;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.impl.DatabaseImpl;
import net.sourceforge.greenvine.model.api.impl.ForeignKeyBuilder;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;
import net.sourceforge.greenvine.model.naming.impl.DatabaseObjectNameImpl;
import net.sourceforge.greenvine.model.naming.impl.PrimaryKeyNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.UniqueKeyNameExtractor;

public class JdbcDatabaseExtractorImpl implements DatabaseExtractor {

	private static final String TABLE_NAME = "TABLE_NAME";
	private static final String TABLE_SCHEMA = "TABLE_SCHEM";
	private static final String TABLE_CAT = "TABLE_CAT";
	
	private final JdbcDatabaseExtractorConfig config;
    private RdbmsNamingConventions conventions;

	public JdbcDatabaseExtractorImpl(JdbcDatabaseExtractorConfig config, RdbmsNamingConventions conventions)  {
		this.config = config;
		this.conventions = conventions;
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.greenvine.reveng.DatabaseExtractor#extractDatabase()
	 */
	public DatabaseImpl extractDatabase() throws DatabaseExtractorException {
	    try {
    		return getDatabase();
	    }
	    catch (Exception e) {
	        throw new DatabaseExtractorException(e);
	    }
	}

	/**
	 * Create a JDBC {@link Connection}
	 * object based on the configuration 
	 * parameters
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private Connection getJDBCConnection(Catalog catalog)
			throws ClassNotFoundException, SQLException {
		Class.forName(catalog.getConnection().getJdbcDriver());
		return DriverManager.getConnection(catalog.getConnection()
				.getJdbcUrl(), catalog.getConnection().getUsername(), catalog
				.getConnection().getPassword());
	}

	/** 
	 * Get all {@link DatabaseImpl} object
	 * representing the connection supplied
	 * using the {@link DatabaseMetaData}.
	 * Iterates through tables and views
	 * included in scope and creates a
	 * {@link TableImpl} object for each. Then,
	 * foreign key relationships are extracted.
	 * All tables must be created first to 
	 * ensure that the foreign keys will
	 * be valid.
	 * @param con
	 * @return
	 * @throws SQLException
	 * @throws ModelException 
	 * @throws ClassNotFoundException 
	 */
	private DatabaseImpl getDatabase()
			throws SQLException, ModelException, ClassNotFoundException {
	    
        // Get connection
        Connection con = getJDBCConnection(config.getCatalog());
        
        // Get the metadata
        DatabaseMetaData dbmd = con.getMetaData();
        
        // Create tables and views from all databases and schemas
        Catalog catalog = config.getCatalog();
            
        // Create database object
        DatabaseImpl db = new DatabaseImpl(catalog.getName(), conventions);

        // Get tables
        extractTables(db, dbmd, catalog);
        
        // Get views
        extractViews(db, dbmd, catalog);
                
            
        // Create relationships
        createForeignKeys(db, dbmd, catalog);
	    
		
		return db;
	}

	/**
	 * Extract table metadata for tables
	 * included in the scope of the 
	 * schema specified
	 * 
	 * @param db
	 * @param dbmd
	 * @param schema
	 * @throws SQLException
	 * @throws ModelException
	 */
	private void extractTables(DatabaseImpl db, DatabaseMetaData dbmd, Catalog catalog) throws SQLException, ModelException {
        
	    // Load all tables
        ResultSet tables = dbmd.getTables(catalog.getName(), null, null, new String[] { "TABLE" });
        while (tables.next()) {
            DatabaseObjectName name = new DatabaseObjectNameImpl(tables.getString(TABLE_SCHEMA), tables.getString(TABLE_NAME));
            if (catalog.getInclusions().isIncluded(name)) {
                extractTableData(db, dbmd, tables);
            }
        }
    }
	
	/**
     * Extract view metadata for views
     * included in the scope of the 
     * schema specified
     * 
     * @param db
     * @param dbmd
     * @param catalog
     * @throws SQLException
     * @throws ModelException
     */
    private void extractViews(DatabaseImpl db, DatabaseMetaData dbmd, Catalog catalog) throws SQLException, ModelException {
		
		// Load all views
		ResultSet views = dbmd.getTables(catalog.getName(), null, null, new String[] { "VIEW" });
		while (views.next()) {
            DatabaseObjectName name = new DatabaseObjectNameImpl(views.getString(TABLE_SCHEMA), views.getString(TABLE_NAME));
            if (catalog.getInclusions().isIncluded(name)) {
                extractViewData(db, dbmd, views);
            }
        }
	}

    /**
     * Extract foreign key data
     * for all tables included
     * in the scope of the 
     * schema
     * 
     * @param db
     * @param dbmd
     * @param catalog
     * @throws SQLException
     * @throws ModelException
     */
    private void createForeignKeys(DatabaseImpl db, DatabaseMetaData dbmd, Catalog catalog) throws SQLException, ModelException {
        // Load all tables
        ResultSet tables = dbmd.getTables(catalog.getName(), null, null, new String[] { "TABLE" });
        while (tables.next()) {
            DatabaseObjectName name = new DatabaseObjectNameImpl(tables.getString(TABLE_SCHEMA), tables.getString(TABLE_NAME));
            if (catalog.getInclusions().isIncluded(name)) {
                createForeignKeysForTable(db, dbmd, tables);
            }
        }
    }

    private void createForeignKeysForTable(DatabaseImpl db, DatabaseMetaData dbmd,
            ResultSet tables) throws SQLException, ModelException {
        
        // Get table and schema names from metadata
        String catalogName = tables.getString(TABLE_CAT);
        String tableName = tables.getString(TABLE_NAME);
        String schemaName = tables.getString(TABLE_SCHEMA);
        
        // Get the imported keys metadata
        ResultSet importedKeys = dbmd.getImportedKeys(catalogName, schemaName,
                tableName);
        
        // Create foreign keys using metadata
        createForeignKeys(db, importedKeys);
        
    }
	
	/**
	 * Extract a {@link TableImpl} object from
	 * the {@link DatabaseMetaData}
	 * @param db
	 * @param dbmd
	 * @param tables
	 * @throws SQLException
	 * @throws ModelException 
	 */
	private void extractTableData(DatabaseImpl db, DatabaseMetaData dbmd,
			ResultSet tables) throws SQLException, ModelException {

		// Get table and schema names from metadata
		String tableName = tables.getString(TABLE_NAME);
		String schemaName = tables.getString(TABLE_SCHEMA);
		String catalogName = tables.getString(TABLE_CAT);
		
		// Create new table
		DatabaseObjectNameImpl name = new DatabaseObjectNameImpl(schemaName, tableName);
		TableImpl table = db.createTable(name); 

		// Get the columns
		ResultSet columns = dbmd.getColumns(catalogName, schemaName, tableName, null);
		createColumns(table, columns);

		// Get primary key metadata
		ResultSet primaryKeys = dbmd
				.getPrimaryKeys(null, schemaName, tableName);
		createPrimaryKey(table, primaryKeys);
		
		// Get the unique constraints
        ResultSet uniques = dbmd.getIndexInfo(null, schemaName, tableName,
                true, false);
        createUniqueKeys(table, uniques);
		
	}

	/**
	 * Extract a {@link View} object from
	 * the {@link DatabaseMetaData}
	 * @param db
	 * @param dbmd
	 * @param view
	 * @throws SQLException
	 * @throws ModelException 
	 */
	private void extractViewData(DatabaseImpl db, DatabaseMetaData dbmd,
			ResultSet views) throws SQLException, ModelException {

		// Get table and schema names from metadata
		String viewName = views.getString(TABLE_NAME);
		String schemaName = views.getString(TABLE_SCHEMA);
		String catalogName = views.getString(TABLE_CAT);
		
		// Create new view
		DatabaseObjectNameImpl name = new DatabaseObjectNameImpl(schemaName, viewName);
		TableImpl view = db.createTable(name, true);

		// Get the columns
		ResultSet columns = dbmd.getColumns(catalogName, schemaName, viewName, null);
		createColumns(view, columns);

	}
	
	private void createForeignKeys(DatabaseImpl db, ResultSet importedKeys)
			throws SQLException, ModelException {
		
		// Create ForeignKeys
		Map<String, ForeignKeyBuilder> foreignKeys = new HashMap<String, ForeignKeyBuilder>();
		while (importedKeys.next()) {
			String fkName = importedKeys.getString("FK_NAME");
			if (fkName != null) {
			    ForeignKeyBuilder foreignKey = foreignKeys.get(fkName);
				if (foreignKey == null) {
					foreignKey = createForeignKey(importedKeys, db);
					foreignKeys.put(fkName, foreignKey);
				} else {
				    createColumnConstraint(importedKeys, foreignKey);
				}
			}
		}
		
		// Create the ForeignKey in the database
		for (ForeignKeyBuilder fk : foreignKeys.values()) {
		    fk.buildForeignKey();
		}
		
	}
	
	private ForeignKeyBuilder createForeignKey(ResultSet importedKeys, DatabaseImpl db)
		throws SQLException, ModelException {
		String fkObjectName = importedKeys.getString("FK_NAME");
		String pkTableName = importedKeys.getString("PKTABLE_NAME");
		String pkSchemaName = importedKeys.getString("PKTABLE_SCHEM");
		String fkTableName = importedKeys.getString("FKTABLE_NAME");
		String fkSchemaName = importedKeys.getString("FKTABLE_SCHEM");
		
		// Create fully qualified table names
		DatabaseObjectNameImpl fqPkTableName = new DatabaseObjectNameImpl(pkSchemaName, pkTableName);
		DatabaseObjectNameImpl fqFkTableName = new DatabaseObjectNameImpl(fkSchemaName, fkTableName);
		
		// Note: foreign key has same schema as foreign table
		DatabaseObjectNameImpl fkName = new DatabaseObjectNameImpl(fkSchemaName, fkObjectName); 
		
		// Create ForeignKey constraint
		ForeignKeyBuilder fk = new ForeignKeyBuilder(fkName, fqFkTableName, fqPkTableName, db);
		createColumnConstraint(importedKeys, fk);
        
		return fk;
	}

	private void createColumnConstraint(ResultSet importedKeys, ForeignKeyBuilder fk) throws SQLException, ModelException {
	    String referencingColumnName = importedKeys.getString("FKCOLUMN_NAME");
	    String referencedColumnName = importedKeys.getString("PKCOLUMN_NAME");
	    fk.addColumnMapping(referencingColumnName, referencedColumnName);
	}

	private void createColumns(TableImpl table, ResultSet columns)
			throws SQLException, ModelException {
		while (columns.next()) {
			createColumn(columns, table);
		}
	}


	private void createUniqueKeys(TableImpl table, ResultSet uniques)
			throws SQLException, ModelException {
		Map<CharSequence, SortedSet<CharSequence>> uniqueKeys = new HashMap<CharSequence, SortedSet<CharSequence>>();
		while (uniques.next()) {
			String indexName = uniques.getString("INDEX_NAME");
			String columnName = uniques.getString("COLUMN_NAME");
			if (indexName != null) {
			    if (uniqueKeys.containsKey(indexName)) {
			        Collection<CharSequence> columns = uniqueKeys.get(indexName);
			        columns.add(columnName);
				} else {
				    SortedSet<CharSequence> columns = new TreeSet<CharSequence>();
					columns.add(columnName);
					uniqueKeys.put(indexName, columns);
				}
			}
		}
		
		// Add the unique key to the table
		for (Entry<CharSequence, SortedSet<CharSequence>> entries : uniqueKeys.entrySet()) {
		    
		    // Create the database object name
		    UniqueKeyNameExtractor nameHelper = new UniqueKeyNameExtractor();
            DatabaseObjectNameImpl uniqueName = nameHelper.extractName(table.getDatabase(), table);
            
            // Get the column names to compare with primary key
            SortedSet<ColumnNameImpl> cols = new TreeSet<ColumnNameImpl>();
            for (CharSequence colName: entries.getValue()) {
                cols.add(new ColumnNameImpl(colName));
            }
            
            // Add unique key if columns are not the same as primary key
            // because JDBC metadata returns PK as unique index as well
            if (!cols.equals(table.getPrimaryKey().getColumnNames())) {
                table.createUniqueKey(uniqueName, cols);
            }
		}
	}

	private void createPrimaryKey(TableImpl table, ResultSet primaryKeys)
			throws SQLException, ModelException {
	    SortedSet<CharSequence> primaryKeyColumnNames = new TreeSet<CharSequence>();
		
		// The name of this primary key
		// Set to default value of PK_<<TABLE>>
	    PrimaryKeyNameExtractor nameHelper = new PrimaryKeyNameExtractor();
		DatabaseObjectNameImpl pkName = nameHelper.extractName(table.getDatabase(), table);
		
		// Get the column names
		while (primaryKeys.next()) {
		    // NOTE: Primary key schema is same as table schema
			// TODO MySQL always calls primary keys PRIMARY
			// so we override this with the default name 
			//pkName = new DatabaseObjectNameImpl(primaryKeys.getString(TABLE_SCHEMA), primaryKeys.getString("PK_NAME"));
			String columnName = primaryKeys.getString("COLUMN_NAME");
			primaryKeyColumnNames.add(columnName);
		}
		
		// Create the PrimaryKey object
		table.createPrimaryKey(pkName, primaryKeyColumnNames);
	
	}

	private void createColumn(ResultSet columns, TableImpl container) throws SQLException, ModelException {
		String columnName = columns.getString("COLUMN_NAME");
		int columnType = columns.getInt("DATA_TYPE");
		boolean notNull = true;
		int nullability = columns.getInt("NULLABLE");
		if (nullability == DatabaseMetaData.columnNullable) {
			notNull = false;
		}
		// Retrieve column specification data
		int columnSize = columns.getInt("COLUMN_SIZE");
		int decimalDigits = columns.getInt("DECIMAL_DIGITS");
		// Create the column
		container.createColumn(columnName, getColumnType(columnType), notNull, decimalDigits, columnSize);
	}
	
	/**
     * Maps a database ColumDatatypeType to a java.sql.Types
     * data type
     * @param sqlType
     * @return
     */
    private ColumnType getColumnType(int sqlType) {
        ColumnType colType;
        switch (sqlType) {
            case Types.BIGINT:
                colType = ColumnType.BIGINT;
                break;  
            case Types.BINARY:
                colType = ColumnType.CHARACTER;
                break;
            case Types.BIT:
                colType = ColumnType.BIT;
                break;
            case Types.BLOB:
                colType = ColumnType.BLOB;
                break;
            case Types.BOOLEAN:
                colType = ColumnType.BOOLEAN;
                break;
            case Types.CHAR:
                colType = ColumnType.CHARACTER;
                break;
            case Types.CLOB:
                colType = ColumnType.CLOB;
                break;
            case Types.DATE:
                colType = ColumnType.DATE;
                break;
            case Types.DECIMAL:
                colType = ColumnType.DECIMAL;
                break;
            case Types.DOUBLE:
                colType = ColumnType.DOUBLE;
                break;
            case Types.FLOAT:
                colType = ColumnType.FLOAT;
                break;
            case Types.INTEGER:
                colType = ColumnType.INTEGER;
                break;
            case Types.LONGVARBINARY:
                colType = ColumnType.LONGVARBINARY;
                break;
            case Types.LONGVARCHAR:
                colType = ColumnType.LONGVARCHAR;
                break;
            case Types.NUMERIC:
                colType = ColumnType.NUMERIC;
                break;
            case Types.REAL:
                colType = ColumnType.REAL;
                break;
            case Types.SMALLINT:
                colType = ColumnType.SMALLINT;
                break;
            case Types.TIME:    
                colType = ColumnType.TIME;
                break;
            case Types.TIMESTAMP:
                colType = ColumnType.TIMESTAMP;
                break;
            case Types.TINYINT:
                colType = ColumnType.TINYINT;
                break;
            case Types.VARBINARY:
                colType = ColumnType.VARBINARY;
                break;
            case Types.VARCHAR:
                colType = ColumnType.VARCHAR;
                break;
            default:
                throw new IllegalArgumentException("Unrecognised type: " + sqlType);
        }
        return colType;
    }
}

    
