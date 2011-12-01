package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.IdentityField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;


public class IdentityFieldImpl extends AbstractColumnField implements IdentityField {

    private final PropertyValueGenerationStrategy keyGenerationStrategy;
    
    IdentityFieldImpl(CharSequence name, PropertyType propertyType, PropertyValueGenerationStrategy keyGenerationStrategy, CharSequence column, ComponentIdentityImpl complexIdentity)
            throws ModelException {
        
        super(name, propertyType, true, column, complexIdentity);

        // Validate fields
        if (keyGenerationStrategy == null) {
            throw new ModelException(String.format("No key generation strategy specified for Simple Identity %s", name));
        }
        
        // Validate that the column is in the primary key
        Column pkCol = complexIdentity.getPrimaryKey().getColumnByName(column);
        
        // Check for duplicate column mapping
        if (complexIdentity.getColumns().contains(pkCol)) {
            throw new ModelException(String.format("Column %s already mapped to a property in ComponentIdentity %s", pkCol.getName(), complexIdentity.getName()));
        }
        
        // Set field
        this.keyGenerationStrategy = keyGenerationStrategy;
        
        // Add to the entity field collection
        complexIdentity.addField(this);
    }
    
    
    public PropertyValueGenerationStrategy getKeyGenerationStrategy() {
        return this.keyGenerationStrategy;
    }
}
