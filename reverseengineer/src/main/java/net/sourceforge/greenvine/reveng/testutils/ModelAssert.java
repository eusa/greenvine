package net.sourceforge.greenvine.reveng.testutils;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Catalog;
import net.sourceforge.greenvine.model.api.ComponentIdentity;
import net.sourceforge.greenvine.model.api.ComponentNaturalIdentity;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.ManyToManyAssociationField;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ManyToOneAssociationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.OneToManyAssociationField;
import net.sourceforge.greenvine.model.api.OneToManyField;
import net.sourceforge.greenvine.model.api.OneToOneAssociationField;
import net.sourceforge.greenvine.model.api.OneToOneChildField;
import net.sourceforge.greenvine.model.api.OneToOneChildIdentity;
import net.sourceforge.greenvine.model.api.OneToOneChildNaturalIdentity;
import net.sourceforge.greenvine.model.api.OneToOneParentField;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.RelationField;
import net.sourceforge.greenvine.model.api.SimpleIdentity;
import net.sourceforge.greenvine.model.api.SimpleNaturalIdentity;
import net.sourceforge.greenvine.model.api.SimpleProperty;

public class ModelAssert {
    
    private final Catalog catalog;
    
    public ModelAssert(final Catalog catalog) {
        this.catalog = catalog;
    }
    
    public void assertEntityExists(final CharSequence entity) {
        try {
            this.catalog.getEntityByName(entity);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertSimpleProperty(final CharSequence entity, final CharSequence property, final PropertyType type) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final SimpleProperty simple = ent.getSimpleProperty(property);
            Assert.assertEquals(simple.getPropertyType(), type);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertSimpleIdentity(final CharSequence entity, final CharSequence property, final PropertyType type) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getIdentity());
            Assert.assertNotNull(ent.getSimpleIdentity());
            final SimpleIdentity field = ent.getSimpleIdentity();
            Assert.assertEquals(field.getName().toString(), property);
            Assert.assertEquals(field.getPropertyType(), type);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertComponentIdentity(final CharSequence entity, final CharSequence ...properties) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getIdentity());
            Assert.assertNotNull(ent.getComponentIdentity());
            final ComponentIdentity field = ent.getComponentIdentity();
            assertIdentityComponent(field, properties);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertSimpleNaturalIdentity(final CharSequence entity, final CharSequence property, final PropertyType type) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getNaturalIdentity());
            Assert.assertNotNull(ent.getSimpleNaturalIdentity());
            final SimpleNaturalIdentity field = ent.getSimpleNaturalIdentity();
            Assert.assertEquals(field.getName().toString(), property);
            Assert.assertEquals(field.getPropertyType(), type);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public void assertComponentNaturalIdentity(final CharSequence entity, final CharSequence ...properties) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getNaturalIdentity());
            Assert.assertNotNull(ent.getComponentNaturalIdentity());
            final ComponentNaturalIdentity field = ent.getComponentNaturalIdentity();
            assertIdentityComponent(field, properties);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    private void assertIdentityComponent(final ComponentIdentity field,
            final CharSequence... properties) throws AssertionError {
        List<CharSequence> props = Arrays.asList(properties);
        for (Field prop : field.getFields()) {
            if(!props.contains(prop.getName())) {
                throw new AssertionError(String.format("Field %s not found in component %s", prop.getName(), field.getName()));
            }
        }
    }
    
    public void assertOneToOneIdentity(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getIdentity());
            Assert.assertNotNull(ent.getConstrainedIdentity());
            final OneToOneChildIdentity field = ent.getConstrainedIdentity();
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertOneToOneNaturalIdentity(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            Assert.assertNotNull(ent.getNaturalIdentity());
            Assert.assertNotNull(ent.getOneToOneNaturalIdentity());
            final OneToOneChildNaturalIdentity field = ent.getOneToOneNaturalIdentity();
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertOneToOneParent(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final OneToOneParentField field = ent.getOneToOneParent(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertOneToOneChild(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final OneToOneChildField field = ent.getOneToOneChild(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertOneToMany(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final OneToManyField field = ent.getOneToMany(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertManyToOne(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final ManyToOneAggregationField field = ent.getManyToOne(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public void assertOneToOneAssociation(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final OneToOneAssociationField field = ent.getOneToOneAssociation(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public void assertManyToOneAssociation(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final ManyToOneAssociationField field = ent.getManyToOneAssociation(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertOneToManyAssociation(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final OneToManyAssociationField field = ent.getOneToManyAssociation(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void assertManyToManyAssociation(final CharSequence entity, final CharSequence property,
            final CharSequence relatedEntity) {
        try {
            final Entity ent = this.catalog.getEntityByName(entity);
            final ManyToManyAssociationField field = ent.getManyToMany(property);
            assertRelationField(property, relatedEntity, field);
        } catch (final ModelException e) {
            throw new AssertionError(e.getMessage());
        }
    }
    
    private void assertRelationField(final CharSequence property,
            final CharSequence relatedEntity,
            final RelationField field) {
        Assert.assertEquals(field.getName().toString(), property);
        Assert.assertEquals(field.getRelatedEntity().getName().toString(), relatedEntity);
    }

}
