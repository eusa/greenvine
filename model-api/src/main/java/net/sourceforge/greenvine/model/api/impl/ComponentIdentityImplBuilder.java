package net.sourceforge.greenvine.model.api.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.NameContainer;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

public class ComponentIdentityImplBuilder implements NameContainer {

    private CharSequence name;
    
    private EntityImpl entity;
    
    private Collection<IdentityFieldImplBuilder> simpleProperties = new ArrayList<IdentityFieldImplBuilder>();
    
    private Collection<ManyToOneComponentRelationImplBuilder> manyToOnes = new ArrayList<ManyToOneComponentRelationImplBuilder>();
    
    public ComponentIdentityImpl build() throws ModelException {
        return new ComponentIdentityImpl(this);
    }

    public CharSequence getName() {
        return name;
    }

    public ComponentIdentityImplBuilder setName(CharSequence name) {
        this.name = name;
        return this;
    }

    public EntityImpl getEntity() {
        return entity;
    }

    public ComponentIdentityImplBuilder setEntity(EntityImpl entity) {
        this.entity = entity;
        return this;
    }

    public Collection<IdentityFieldImplBuilder> getSimpleProperties() {
        return simpleProperties;
    }

    public ComponentIdentityImplBuilder addSimpleProperty(CharSequence name, CharSequence columnName, PropertyType propertyType, PropertyValueGenerationStrategy strategy) throws ModelException {
        IdentityFieldImplBuilder builder = new IdentityFieldImplBuilder();
        builder.setName(name).setColumnName(columnName).setPropertyType(propertyType);
        builder.setPropertyValueGenerationStrategy(strategy);
        this.simpleProperties.add(builder);
        return this;
    }

    public Collection<ManyToOneComponentRelationImplBuilder> getManyToOnes() {
        return manyToOnes;
    }

    public ComponentIdentityImplBuilder addManyToOne(CharSequence ownerField,
            CharSequence inverseField, CharSequence foreignKey) throws ModelException {
        ManyToOneComponentRelationImplBuilder builder = new ManyToOneComponentRelationImplBuilder();
        builder.setOwnerField(ownerField).setInverseField(inverseField).setForeignKey(foreignKey);
        this.manyToOnes.add(builder);
        return this;
    }

    public boolean containsName(CharSequence name) throws ModelException {
        return getNames().contains(new FieldNameImpl(name));
    }

    public SortedSet<? extends Name> getNames() {
        SortedSet<Name> names = new TreeSet<Name>();
        for (IdentityFieldImplBuilder simple : simpleProperties) {
            FieldName name = simple.getName();
            names.add(name);
        }
        for (ManyToOneComponentRelationImplBuilder many : manyToOnes) {
            FieldName name = many.getInverseField();
            names.add(name);
        }
        return names;
    }
    
}
