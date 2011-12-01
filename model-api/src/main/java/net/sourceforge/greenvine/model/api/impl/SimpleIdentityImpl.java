package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.naming.impl.ColumnNameImpl;

public class SimpleIdentityImpl extends AbstractColumnField implements SimpleIdentity {

    private final EntityImpl entity;
    private final PropertyValueGenerationStrategy keyGenerationStrategy;
    private final PrimaryKey primaryKey;
    
    /**
     * Create a SimpleIdentity by reverse-engineering
     * it from the underlying table.
     * If it is a table, a PrimaryKey is used
     * or if there isn't one it is created.
     * If it is a view, an identity is created 
     * without a primary key.
     * @param keyGenerationStrategy
     * @param entity
     * @throws ModelException
     */
    SimpleIdentityImpl(CharSequence name, PropertyType propertyType, PropertyValueGenerationStrategy keyGenerationStrategy, CharSequence column,
            EntityImpl entity) throws ModelException {
        super(name, propertyType, true, column, entity);
        
        // Validate parameters
        if (propertyType == null) {
            throw new ModelException(String.format("Cannot create a SimpleIdentity %s with a null PropertyType.", name));
        }
        if (entity == null) {
            throw new ModelException("Cannot create a SimpleIdentity for a null entity.");
        }
        if (keyGenerationStrategy == null) {
            throw new ModelException(String.format("No key generation strategy specified for SimpleIdentity in entity %s", entity.getName()));
        }
        
        // Check primary key if not a view
        if (!entity.getTable().isView()) {
            TableImpl table = (TableImpl)entity.getTable();
            PrimaryKeyImpl primaryKey = table.getPrimaryKey();
            if (primaryKey == null) {
                throw new ModelException(String.format("Cannot create SimpleIdentity with null primary key in table %s specified for entity %s", table.getName(), entity.getName()));
            }
            if (primaryKey.getColumnCount() > 1) {
                throw new ModelException(String.format("Cannot create SimpleIdentity with compound primary key in table %s specified for entity %s", table.getName(), entity.getName()));
            }
            if (!primaryKey.getColumnNames().contains(new ColumnNameImpl(column))) {
                throw new ModelException(String.format("Cannot create SimpleIdentity as column %s is not the primary column in table %s, entity %s", column, table.getName(), entity.getName()));
            }
            this.primaryKey = primaryKey;
        } else {
            this.primaryKey = null; 
        }
       
        // Set fields
        this.keyGenerationStrategy = keyGenerationStrategy;
        this.entity = entity;
        // Set as the identity
        this.entity.setIdentity(this);
        
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.SimpleIdentity#getKeyGenerationStrategy()
     */
    public PropertyValueGenerationStrategy getKeyGenerationStrategy() {
        return this.keyGenerationStrategy;
    }

    public EntityImpl getFieldCollection() {
        return this.entity;
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.SimpleIdentity#getPrimaryKey()
     */
    public PrimaryKey getPrimaryKey() {
        return this.primaryKey;
    }
    
    @Override
    public String toString() {
        return "SimpleIdentity [name=" + name + ", propertyType="
                + getPropertyType() + ", column=" + getColumn().getName()
                + ", keyGenerationStrategy=" + keyGenerationStrategy + "]";
    }

    public SortedSet<Column> getColumns() {
        SortedSet<Column> columns = new TreeSet<Column>();
        columns.add(getColumn());
        return columns;
    }
    

}
