package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.RdbmsNamedObject;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;



public class ColumnImpl implements RdbmsNamedObject, Column, Comparable<ColumnImpl> {

    private final Table container;
    private final ColumnNameImpl name;
    private final ColumnType columnType;
    private final boolean notNull;
    private final int scale;
    private final int precision;
    private final SortedSet<ColumnImpl> referencingColumns;
    private final ColumnValueGenerationStrategy valueGenerationStrategy;
    
    ColumnImpl(Table container, 
            ColumnNameImpl name, ColumnType columnType, boolean notNull, int scale, int precision, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        
        // Validate parameters
        validateFields(container, name, columnType, valueGenerationStrategy);
        
        // Assign fields
        this.container = container;
        this.name = name;
        this.columnType = columnType;
        this.notNull = notNull;
        this.scale = scale;
        this.precision = precision;
        this.valueGenerationStrategy = valueGenerationStrategy;
        this.referencingColumns = new TreeSet<ColumnImpl>();
        
    }
    
    ColumnImpl(Table container, ColumnNameImpl name,
            ColumnType type, boolean notNull, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        
        // Validate parameters
        validateFields(container, name, type, valueGenerationStrategy);
        
        // Assign fields
        this.container = container;
        this.name = name;
        this.columnType = type;
        this.notNull = notNull;
        this.scale = type.getDefaultScale();
        this.precision = type.getDefaultPrecision();
        this.valueGenerationStrategy = valueGenerationStrategy;
        this.referencingColumns = new TreeSet<ColumnImpl>();
        
    }

    private void validateFields(Table container, ColumnName name,
            ColumnType type, ColumnValueGenerationStrategy valueGenerationStrategy) throws ModelException {
        if (container == null) {
            throw new ModelException(String.format("Column cannot be created with null container."));
        }
        if (name == null) {
            throw new ModelException(String.format("Column cannot be created with null or empty name."));
        }
        if (type == null) {
            throw new ModelException(String.format("Column cannot be created with null type."));
        }
        if (valueGenerationStrategy == null) {
            throw new ModelException(String.format("Column cannot be created with null value generation strategy."));
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getDataContainer()
     */
    public Table getDataContainer() {
       return this.container;
    }

    public ColumnNameImpl getName() {
        return this.name;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getColumnType()
     */
    public ColumnType getColumnType() {
        return columnType;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getNotNull()
     */
    public boolean getNotNull() {
        return this.notNull;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getScale()
     */
    public int getScale() {
        return scale;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getPrecision()
     */
    public int getPrecision() {
        return precision;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getColumnValueGenerationStrategy()
     */
    public ColumnValueGenerationStrategy getColumnValueGenerationStrategy() {
        return this.valueGenerationStrategy;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((container == null) ? 0 : container.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + precision;
        result = prime * result + scale;
        result = prime * result + ((columnType == null) ? 0 : columnType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ColumnImpl other = (ColumnImpl) obj;
        if (container == null) {
            if (other.container != null)
                return false;
        } else if (!container.equals(other.container))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (notNull != other.notNull)
            return false;
        if (precision != other.precision)
            return false;
        if (scale != other.scale)
            return false;
        if (columnType == null) {
            if (other.columnType != null)
                return false;
        } else if (!columnType.equals(other.columnType))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#compatibleWith(net.sourceforge.greenvine.model.api.ColumnImpl)
     */
    public boolean compatibleWith(Column column) {
        if (column == null)
            return false;
        if (precision != column.getPrecision())
            return false;
        if (scale != column.getScale())
            return false;
        if (columnType == null) {
            if (column.getColumnType() != null)
                return false;
        } else if (!columnType.equals(column.getColumnType()))
            return false;
        return true;
    }
    
   
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#referencesAnotherColumn()
     */
    public boolean referencesAnotherColumn() {
        boolean isForeign = false;
        if (this.container instanceof Table) {
            Table table = (Table)this.container;
            for (ForeignKey fk: table.getImportedForeignKeys()) {
                if (fk.hasReferencingColumn(this)) {
                    isForeign = true;
                    break;
                }
            }
        }
        return isForeign;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getDirectlyReferencingColumns()
     */
    public SortedSet<ColumnImpl> getDirectlyReferencingColumns() {
        return this.referencingColumns;
    }
    
    void addReferencingColumn(ColumnImpl referencingColumn) {
        this.referencingColumns.add(referencingColumn);
        
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getAllReferencingColumns()
     */
    public List<ColumnImpl> getAllReferencingColumns() {
        List<ColumnImpl> referencing = new ArrayList<ColumnImpl>();
        recursivelyAddReferencingColumns(referencing);
        return referencing;
    }
    
    private void recursivelyAddReferencingColumns(List<ColumnImpl> referencing) {
        for (ColumnImpl column : getDirectlyReferencingColumns()) {
            referencing.add(column);
            column.recursivelyAddReferencingColumns(referencing);
        }
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#partOfPrimaryKey()
     */
    public boolean partOfPrimaryKey() {
        boolean isPrimary = false;
        if (this.container instanceof Table) {
            Table table = (Table)this.container;
            if (table.hasPrimaryKey()) {
                if (table.getPrimaryKey().contains(this)) {
                    isPrimary = true;
                }
            }
        }
        return isPrimary;
    }

    public String getQualifiedName() {
    	return this.container.getName() + "." + this.getName();
    }


    public int compareTo(ColumnImpl obj) {
        //return this.name.compareTo(obj.getName());
        return this.getQualifiedName().compareTo(obj.getQualifiedName());
    }

    @Override
    public String toString() {
        return "Column [name=" + name + ", columnType=" + columnType + "]";
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.Column#getSpecification()
     */
    public String getSpecification() {
        int scale = getScale();
        int precision = getPrecision();
        String specification = null;
        String justPrecision = "(%s)";
        String precisionAndScale = "(%s, %s)";
        switch (getColumnType()) {
        case BIGINT:
            specification = "";
            break; 
        case BIT:
            specification = "";
            break;
        case BLOB:
            specification = "";
            break;
        case BOOLEAN:
            specification = "";
            break;
        case VARBINARY:
            specification = "";   
            break;
        case CHARACTER:
            specification = String.format(justPrecision, precision);
            break; 
        case CLOB:
            specification = "";
            break;
        case DATE:
            specification = "";
            break;  
        case DECIMAL:
            specification = String.format(precisionAndScale, precision, scale);
            break;
        case DOUBLE:
            specification = String.format(justPrecision, precision);
            break;  
        case INTEGER:
            specification = "";
            break;  
        case NUMERIC:
            specification = String.format(precisionAndScale, precision, scale);
            break;  
        case REAL:
            specification = String.format(justPrecision, precision);
            break;  
        case SMALLINT:
            specification = "";
            break;  
        case TIME:
            specification = "";
            break;  
        case TIMESTAMP:
            specification = "";
            break;
        case TINYINT:
            specification = "";
            break; 
        case VARCHAR:
            specification = String.format(justPrecision, precision);
            break;  
        case LONGVARCHAR:
            specification = String.format(justPrecision, precision);
            break;  
        default:
            throw new IllegalArgumentException("Unrecognised type: " + getColumnType());
        }
        return specification;
    }

}
