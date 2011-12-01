package net.sourceforge.greenvine.model.api.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.UniqueKey;


public class SimpleNaturalIdentityImpl extends AbstractColumnField implements SimpleNaturalIdentity {
    
    private final EntityImpl entity;
    private final UniqueKey uniqueKey;

    SimpleNaturalIdentityImpl(CharSequence name, PropertyType propertyType, CharSequence column, UniqueKey uniqueKey, EntityImpl entity) throws ModelException {
        super(name, propertyType, true, column, entity);
        
        // Validate UniqueKey
        if (uniqueKey == null || uniqueKey.getColumnCount() != 1 || !uniqueKey.contains(this.getColumn())) {
            throw new ModelException(String.format("Can't create SimpleNaturalIdentity %s with invalid unique key", name));
        }
        
        // Set fields
        this.entity = entity;
        this.uniqueKey = uniqueKey;
        
        // Add to containing field collection
        entity.setNaturalIdentity(this);
    }

    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.SimpleNaturalIdentity#getFieldCollection()
     */
    public EntityImpl getFieldCollection() {
        return this.entity;
    }
    
    /* (non-Javadoc)
     * @see net.sourceforge.greenvine.model.api.SimpleNaturalIdentity#getUniqueKey()
     */
    public UniqueKey getUniqueKey() {
        return this.uniqueKey;
    }
   
}
