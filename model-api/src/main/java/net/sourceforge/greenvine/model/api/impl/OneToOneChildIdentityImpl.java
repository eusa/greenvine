package net.sourceforge.greenvine.model.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.AggregationRelationChildField;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.IdentityField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToOneChildIdentity;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.naming.ColumnName;

/**
 * Models a one-to-one field
 * that is also the identity of the
 * object. It is "constrained" in that
 * the primary key can only have values
 * from the primary key of the parent table
 * as the foreign key is also the primary key.
 * @author patrick
 *
 */
public class OneToOneChildIdentityImpl extends AbstractAggregationRelationField implements Identity, AggregationRelationChildField, OneToOneChildIdentity {

    private final OneToOneIdentityRelationImpl oneToOne;
    
    public OneToOneChildIdentityImpl(CharSequence name, EntityImpl entity, OneToOneIdentityRelationImpl oneToOne)
            throws ModelException {
        super(name, entity, oneToOne);
        
        this.oneToOne = oneToOne;
        
        // Set as identity
        entity.setIdentity(this);

    }

    public Entity getFieldCollection() {
        return this.entity;
    }
    
    public AggregationRelation getRelation() {
        return this.oneToOne;
    }

    public SortedSet<? extends Column> getColumns() {
        return this.oneToOne.getForeignKey().getReferencingColumns();
    }
    
    public SortedSet<ColumnName> getColumnNames() {
        return this.oneToOne.getForeignKey().getReferencingColumnNames();
    }
    
    public boolean getNotNull() {
        return true;
    }
    
    public PrimaryKey getPrimaryKey() {
        return this.oneToOne.getPrimaryKey();
    }
    
    public Map<ColumnField, Column> getFieldToColumnMapping() throws ModelException {
        Map<ColumnField, Column> mapping = new HashMap<ColumnField, Column>();
        Identity nonConstrained = getUltimateNonConstrainedIdentity();
        if (nonConstrained instanceof SimpleIdentity) {
            SimpleIdentityImpl simple = (SimpleIdentityImpl)nonConstrained;
            Column mapped = findColumnMappedToField(simple);
            mapping.put(simple, mapped);
        } else {
            ComponentIdentityImpl complex = (ComponentIdentityImpl)nonConstrained;
            for (IdentityField field : complex.getSimpleProperties()) {
                Column mapped = findColumnMappedToField(field);
                mapping.put(field, mapped);
            }
        }
        return mapping;
    }
    
    public Column findColumnMappedToField(ColumnField simple) throws ModelException {
        Column original = simple.getColumn();
        ForeignKey foreign = getRelation().getForeignKey();
        List<? extends Column> referencing = original.getAllReferencingColumns();
        for (Column candidate : foreign.getReferencingColumns()) {
            if (referencing.contains(candidate)) {
                return candidate;
            }
        }
        throw new ModelException(String.format("Cannot find column mapped to field %s in ConstrainedIdentity %s", simple, this));
    }

    public Identity getUltimateNonConstrainedIdentity() {
        Entity related = this.getRelatedEntity();
        if (related.getIdentity() instanceof OneToOneChildIdentity) {
            return related.getConstrainedIdentity().getUltimateNonConstrainedIdentity();
        } else {
            return related.getIdentity();
        }
    }

    public boolean isOwner() {
        return false;
    }
    
    public boolean isDependency() {
        return true;
    }

    @Override
    public String toString() {
        return "OneToOneChildIdentity [" + super.toString() + "]";
    }
    
    
}
