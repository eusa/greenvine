package net.sourceforge.greenvine.reveng.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Entity;
import net.sourceforge.greenvine.model.api.ForeignKey;
import net.sourceforge.greenvine.model.api.Identity;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PrimaryKey;
import net.sourceforge.greenvine.model.api.PropertyValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.api.UniqueKey;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.ComponentIdentityImplBuilder;
import net.sourceforge.greenvine.model.api.impl.ComponentNaturalIdentityImpl;
import net.sourceforge.greenvine.model.api.impl.EntityImpl;
import net.sourceforge.greenvine.model.api.impl.ForeignKeyImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.model.api.impl.PrimaryKeyImpl;
import net.sourceforge.greenvine.model.api.impl.SimplePropertyImpl;
import net.sourceforge.greenvine.model.api.impl.TableImpl;
import net.sourceforge.greenvine.model.api.impl.UniqueKeyImpl;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConventions;
import net.sourceforge.greenvine.model.naming.impl.CatalogNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.ComplexIdentityNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.EntityNameImpl;
import net.sourceforge.greenvine.model.naming.impl.EntityTableNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;
import net.sourceforge.greenvine.model.naming.impl.IdentityFieldForeignColumnNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.IdentityFieldNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.ManyToManyNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.ManyToOneAssociationNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.ManyToOneNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.NaturalIdentityNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.OneToManyAssociationNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.OneToManyNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.OneToOneAssociationNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.OneToOneChildNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.OneToOneParentNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.SimpleIdentityNameExtractor;
import net.sourceforge.greenvine.model.naming.impl.SimplePropertyNameExtractor;
import net.sourceforge.greenvine.reveng.ReverseEngineer;

public class ReverseEngineerImpl implements ReverseEngineer {
    
    private Logger logger = Logger.getLogger("ReverseEngineerImpl");
    
    private final RevengConfig revengConfig;
    
    private RdbmsNamingConventions namingConventions;
    
    public ReverseEngineerImpl(RevengConfig revengConfig) {
        this.revengConfig = revengConfig;
    }

    public ModelImpl reverseEngineer(Database database) throws ModelException {
        this.namingConventions = database.getNamingConventions();
        ModelImpl model = new ModelImpl(revengConfig.getModelName());
        addCatalog(database, model);
        return model;
        
    }
    
    public CatalogImpl addCatalog(Database database, ModelImpl model) throws ModelException {
        
        // Set up model object
        CatalogNameExtractor extractor = new CatalogNameExtractor(database.getNamingConventions());
        CatalogImpl catalog = model.createCatalog(extractor.extractName(model, database), database); 
        
        // Create the entities
        createEntities(database, catalog);

        return catalog;
    }
    
    private void createEntities(Database database,
            CatalogImpl catalog) throws ModelException {
        // Iterate through tables to create entities
        for (Table table : database.getTablesInDependencyOrder()) {
            if (!table.isAssociationTable()) {
                
                // Create entity
                EntityImpl entity = getEntityFromTable(table, catalog);
                
                // Create identity
                createIdentity(entity, catalog, database);

            }
        }
        
        // Create identities
        //createIdentities(model, db);
        
        // Create a natural identity
        createNaturalIds(catalog, database);
        
        // Create relationships
        createRelationships(catalog, database);
        
        // Create simple properties
        createSimpleProperties(catalog, database);
        

                
    }
    
    private void createSimpleProperties(CatalogImpl catalog, Database db) throws ModelException {
        for (EntityImpl entity : catalog.getDependencyGraphFromRoot()) {
            createSimpleProperties(entity);
        }
        
    }

    private EntityImpl getEntityFromTable(Table table, CatalogImpl catalog) throws ModelException {
        EntityTableNameExtractor nameExtractor = new EntityTableNameExtractor(namingConventions);
        EntityNameImpl name = nameExtractor.extractName(catalog, table);
        return catalog.createEntity(name.getNamespace(), name.getObjectName(), table.getName());

    }
    
    
    /**
     * Create the {@link Identity} for an
     * {@link EntityImpl} based on the
     * primary key in the {@link TableImpl}
     * 
     * @param entity
     * @param table
     * @throws ModelException 
     */
    private void createIdentity(EntityImpl entity, CatalogImpl catalog, Database database) throws ModelException {
        
        // Get the table primary key
        if (!entity.getTable().isView()) {
            TableImpl table = (TableImpl)entity.getTable();
            PrimaryKeyImpl pk = table.getPrimaryKey();
            if (pk == null || pk.getColumnCount() == 0) {
                throw new ModelException(String.format("Table %s does not have a primary key.", table.getName()));
            } 
            
            if (pk.isForeignKey()) {
                ForeignKeyImpl foreignKey = pk.getForeignKey();
                TableImpl parentTable = foreignKey.getReferencedTable();
                Entity parentEntity = catalog.getEntityByTable(parentTable);
                Entity childEntity = catalog.getEntityByTable(table);
                logger.log(Level.FINE, "Constrained one-to-one primary identity, parent: " + parentEntity.getName() + ", child: " + childEntity.getName());
                OneToOneChildNameExtractor childExtractor = new OneToOneChildNameExtractor(namingConventions);
                OneToOneParentNameExtractor parentExtractor = new OneToOneParentNameExtractor(namingConventions);
                FieldNameImpl childName = childExtractor.extractName(parentEntity, foreignKey);
                FieldNameImpl parentName = parentExtractor.extractName(childEntity, foreignKey);
                catalog.createBiDirectionalConstrainedOneToOneRelationship(parentName, childName, foreignKey.getName());
            
            } 
            else {
            
                // Create either a simple or complex
                // identity depending on the number
                // of columns in the primary key
                if (pk.getColumnCount() == 1) {
                    Column column = pk.getColumn(0);
                    SimpleIdentityNameExtractor extractor = new SimpleIdentityNameExtractor(entity.getTable().getDatabase().getNamingConventions());
                    entity.createSimpleIdentity(extractor.extractName(entity, column), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, column.getName());
                } 
                else if (pk.getColumnCount() > 1){
                    // Create a complex identity
                    createComponentIdentity(entity);
                }
            }
        }
        else {
            Column column = entity.getTable().getColumn(0);
            SimpleIdentityNameExtractor extractor = new SimpleIdentityNameExtractor(entity.getTable().getDatabase().getNamingConventions());
            entity.createSimpleIdentity(extractor.extractName(entity, column), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, column.getName());
        }
    }

    /** 
     * Create a {@link ComponentIdentityImpl} for 
     * an {@link EntityImpl} backed by a table
     * with a compount primary key
     * @param entity
     * @param table
     * @param pkCols
     * @return
     * @throws ModelException 
     */
    private void createComponentIdentity(EntityImpl entity) throws ModelException {
        Table table = entity.getTable();
        PrimaryKey pk = table.getPrimaryKey();
        //Collection<Column> pkCols = pk.getColumns();
        ComplexIdentityNameExtractor identityExtractor = new ComplexIdentityNameExtractor();
        //ComponentIdentityImpl identity = entity.createComponentIdentity(identityExtractor.extractName(entity, entity));
        
        ComponentIdentityImplBuilder builder = new ComponentIdentityImplBuilder();
        builder.setName(identityExtractor.extractName(entity, entity));
        builder.setEntity(entity);
        
        // Add many-to-one fields
        // NOTE - this causes problems with exotic
        // relationships, specifically a constrained one-to-one
        // where the component identity consists of a many-to-one
        // and a simple property - hence, only support if 
        // if specified in configuration
        if (revengConfig.getCreateManyToOneInComponentIdentities()) {
            for (ForeignKey foreignKey : pk.getForeignKeys()) {
                logger.log(Level.FINE, "ComponentIdentity: Many-to-one component field");
                Table relatedTable = foreignKey.getReferencedTable();
                Entity relatedEntity = entity.getCatalog().getEntityByTable(relatedTable);
                ManyToOneNameExtractor childExtractor = new ManyToOneNameExtractor(namingConventions);
                OneToManyNameExtractor parentExtractor = new OneToManyNameExtractor(namingConventions);
                FieldNameImpl childName = childExtractor.extractName(entity, foreignKey);
                FieldNameImpl parentName = parentExtractor.extractName(relatedEntity, foreignKey);
                //identity.createManyToOneRelationship(parentName, childName, foreignKey.getName());
                builder.addManyToOne(parentName, childName, foreignKey.getName());
            }
        } else {
            IdentityFieldForeignColumnNameExtractor fieldExtractor = new IdentityFieldForeignColumnNameExtractor(namingConventions);
            for (ForeignKey foreignKey : pk.getForeignKeys()) {
                logger.log(Level.FINE, "ComponentIdentity: Many-to-one component field [as simple property]");
                for (Column column : foreignKey.getReferencingColumns()) {
                    //identity.createSimpleProperty(fieldExtractor.extractName(identity, column), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, column.getName());
                    builder.addSimpleProperty(fieldExtractor.extractName(builder, column), column.getName(), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED);
                }
            }
        }
        
        // Add simple property fields
        IdentityFieldNameExtractor fieldExtractor = new IdentityFieldNameExtractor(namingConventions);
        for (Column column : pk.getNonForeignColumns()) {
        //for (Column column : pkCols) {
          //  if (!identity.getColumns().contains(column)) {
                //identity.createSimpleProperty(fieldExtractor.extractName(identity, column), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED, column.getName());
                builder.addSimpleProperty(fieldExtractor.extractName(builder, column), column.getName(), column.getColumnType().getPropertyType(), PropertyValueGenerationStrategy.ASSIGNED);
                
            //}
        }
        
        // Build the ComponentIdentity
        builder.build();
    }
    
    
    /**
     * Create {@link SimplePropertyImpl} based on the
     * data columns of the {@link TableImpl}, which
     * are not involved in foreign keys or
     * primary keys
     * @param entity
     * @param table
     * @param constraints 
     * @throws ModelException 
     */
    private void createSimpleProperties(EntityImpl entity) throws ModelException {
        logger.log(Level.FINE, "Adding simple properties to entity: " + entity.getName() + " with columns: " + entity.getColumns());
        for (Column column : entity.getTable().getColumns()) {
            if (!entity.getColumns().contains(column)) {
                logger.log(Level.FINE, "Adding property for col: " + column);
                createSimpleProperty(column, entity);
            }
        }
    }
    
    /**
     * Create a {@link SimplePropertyImpl} based on
     * a column
     * @param dataColumn
     * @return
     * @throws ModelException 
     */
    private void createSimpleProperty(Column column, EntityImpl entity) throws ModelException {
        SimplePropertyNameExtractor extractor = new SimplePropertyNameExtractor(namingConventions);
        entity.createSimpleProperty(extractor.extractName(entity, column), column.getColumnType().getPropertyType(), column.getNotNull(), column.getName());
    }
    
    /**
     * Create relationship between entities
     * There are two main types of relationship - 
     * direct or mediated through an association table
     * Within each type there are several possible cardinalities
     * depending on the uniqueness of the foreign key column
     * Association table relationship cardinalities are
     * 1) One-to-one via association table with unique key on both foreign keys
     * 2) One-to-many via association table with unique key on one foreign key
     * 2) Many-to-many via an association table with no unique constraints
     * Direct association cardinalities are:
     * 1) One-to-one via foreign key linked to non-primary key field with unique constraint
     * 2) One-to-one via foreign key to primary key
     * 3) One-to-many via normal foreign key
     * In all cases, self-relations are possible. In other words,
     * the relationship points back to the entity.
     * 
     * @param entities
     * @throws ModelException 
     */
    public void createRelationships(CatalogImpl catalog, Database db) throws ModelException {
        
        // Iterate through all tables to create relationships
        for (Table table : db.getTables()) {
            
            // Is the table an association/join table?
            boolean isJoinTable = table.isAssociationTable();
            
            // Determine the relationship cardinality.
            // The first thing to determine is how the 
            // relationship is realised in database terms.
            // If an association/join table is used
            // there are 3 relations to consider depending
            // on whether there are unique keys on the FKs
            // in the association table
            if (isJoinTable) {
                ForeignKey leftConstraint = table.getImportedForeignKey(0);
                ForeignKey rightConstraint = table.getImportedForeignKey(1);
                Table leftTable = leftConstraint.getReferencedTable();
                Table rightTable = rightConstraint.getReferencedTable();
                Entity leftEntity = catalog.getEntityByTable(leftTable);
                Entity rightEntity = catalog.getEntityByTable(rightTable);
                
                // Could be either a many-to-many. one-to-many or a one-to-one
                // This depends on the existence of unique constraints
                boolean leftSideFKUnique = leftConstraint.areReferencingColumnsUniqueKey();
                boolean rightSideFKUnique = rightConstraint.areReferencingColumnsUniqueKey();
                
                if (!leftSideFKUnique && !rightSideFKUnique) {
                    // This must be a many-to-many
                    logger.log(Level.FINE, "Many-to-many (via association), parent: " + leftEntity.getName() + ", child: " + rightEntity.getName());
                    ManyToManyNameExtractor extractor = new ManyToManyNameExtractor(namingConventions);
                    FieldName leftName = extractor.extractName(leftEntity, rightConstraint);
                    FieldName rightName = extractor.extractName(rightEntity, leftConstraint);
                    catalog.createBiDirectionalManyToManyRelationship(leftName, leftConstraint.getName(), rightName, rightConstraint.getName());
                }
                else if (!leftSideFKUnique && rightSideFKUnique) {
                    // This must be a one-to-many
                    // Left hand is many-to-one (there can be many lefts belonging to one right)
                    // Right hand is one-to-many
                    logger.log(Level.FINE, "One-to-many (via association), parent: " + rightEntity.getName() + ", child: " + leftEntity.getName());
                    OneToManyAssociationNameExtractor oneToManyExtractor = new OneToManyAssociationNameExtractor(namingConventions);
                    FieldName oneToManyName = oneToManyExtractor.extractName(rightEntity, rightConstraint);
                    ManyToOneAssociationNameExtractor manyToOneExtractor = new ManyToOneAssociationNameExtractor(namingConventions);
                    FieldName manyToOneName = manyToOneExtractor.extractName(leftEntity, leftConstraint);
                    catalog.createBiDirectionalManyToOneRelationshipViaAssociation(oneToManyName, leftConstraint.getName(), manyToOneName, rightConstraint.getName()); 
                } 
                else if (leftSideFKUnique && !rightSideFKUnique) {
                    // This must be a one-to-many
                    // Left is one-to-many (there can be many rights belonging to one left)
                    // Right side is many-to-one
                    logger.log(Level.FINE, "One-to-many (via association), parent: " + leftEntity.getName() + ", child: " + rightEntity.getName());
                    OneToManyAssociationNameExtractor oneToManyExtractor = new OneToManyAssociationNameExtractor(namingConventions);
                    FieldNameImpl oneToManyName = oneToManyExtractor.extractName(leftEntity, rightConstraint);
                    ManyToOneAssociationNameExtractor manyToOneExtractor = new ManyToOneAssociationNameExtractor(namingConventions);
                    FieldNameImpl manyToOneName = manyToOneExtractor.extractName(rightEntity, leftConstraint);
                    catalog.createBiDirectionalManyToOneRelationshipViaAssociation(oneToManyName, rightConstraint.getName(), manyToOneName, leftConstraint.getName()); 
                }
                else {
                    // This must be a one-to-one
                    logger.log(Level.FINE, "One-to-one (via association), parent: " + leftEntity.getName() + ", child: " + rightEntity.getName());
                    OneToOneAssociationNameExtractor extractor = new OneToOneAssociationNameExtractor(namingConventions);
                    FieldNameImpl leftName = extractor.extractName(leftEntity, rightConstraint);
                    FieldNameImpl rightName = extractor.extractName(rightEntity, leftConstraint);
                    catalog.createBiDirectionalOneToOneRelationshipViaAssociation(leftName, leftConstraint.getName(), rightName, rightConstraint.getName()); 
                }
            } else {
                for (ForeignKey foreignKey : table.getImportedForeignKeys()) {
                    Table parentTable = foreignKey.getReferencedTable();
                    Entity parentEntity = catalog.getEntityByTable(parentTable);
                    Entity childEntity = catalog.getEntityByTable(table);
                    
                    // Create relationship if the entity
                    // doesn't already contain the columns
                    // unless it is the identity (as the 
                    // identity might have a simple property
                    // that is also mapped as a many-to-one
                    if (!childEntity.getColumns().containsAll(foreignKey.getReferencingColumns()) || childEntity.getIdentity().getColumns().containsAll(foreignKey.getReferencingColumns())) {
                    
                        // Check whether the foreign column is 
                        // unique or primary - if so, this
                        // is a one-to-one relationship
                        boolean isForeignColumnUnique = foreignKey.areReferencingColumnsUniqueKey();
                        boolean isForeignColumnPrimary = foreignKey.areReferencingColumnsPrimaryKey();
                        
                        if (isForeignColumnPrimary) {
                            // It is a constrained one to one
                            // Therefore, already created during identity creation
                        } else if (isForeignColumnUnique && childEntity.getNaturalIdentity() != null) {
                            // it is a one-to-one
                            logger.log(Level.FINE, "One-to-one, parent: " + parentEntity.getName() + ", child: " + childEntity.getName());
                            OneToOneChildNameExtractor childExtractor = new OneToOneChildNameExtractor(namingConventions);
                            OneToOneParentNameExtractor parentExtractor = new OneToOneParentNameExtractor(namingConventions);
                            FieldNameImpl childName = childExtractor.extractName(parentEntity, foreignKey);
                            FieldNameImpl parentName = parentExtractor.extractName(childEntity, foreignKey);
                            catalog.createBiDirectionalOneToOneRelationship(parentName, childName, foreignKey.getName());
                        } else if (isForeignColumnUnique && childEntity.getNaturalIdentity() == null) {
                            // it is a one-to-one
                            logger.log(Level.FINE, "One-to-one natural identity, parent: " + parentEntity.getName() + ", child: " + childEntity.getName());
                            OneToOneChildNameExtractor childExtractor = new OneToOneChildNameExtractor(namingConventions);
                            OneToOneParentNameExtractor parentExtractor = new OneToOneParentNameExtractor(namingConventions);
                            FieldNameImpl childName = childExtractor.extractName(parentEntity, foreignKey);
                            FieldNameImpl parentName = parentExtractor.extractName(childEntity, foreignKey);
                            catalog.createNaturalIdentityOneToOneRelation(parentName, childName, foreignKey.getName(), childEntity.getTable().getUniqueKey(0).getName());
                        } else {
                            // it is a one-to-many
                            logger.log(Level.FINE, "One-to-many, parent: " + parentEntity.getName() + ", child: " + childEntity.getName());
                            ManyToOneNameExtractor childExtractor = new ManyToOneNameExtractor(namingConventions);
                            OneToManyNameExtractor parentExtractor = new OneToManyNameExtractor(namingConventions);
                            FieldNameImpl childName = childExtractor.extractName(childEntity, foreignKey);
                            FieldNameImpl parentName = parentExtractor.extractName(parentEntity, foreignKey);
                            catalog.createBiDirectionalManyToOneRelationship(parentName, childName, foreignKey.getName());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Create any {@link ComponentNaturalIdentityImpl} for the
     * entity based on a {@link UniqueKeyImpl}
     * @param catalog
     * @param database
     * @throws ModelException
     */
    private void createNaturalIds(CatalogImpl catalog, Database database) throws ModelException {
        for (EntityImpl entity : catalog.getEntities()) {
            Table table = entity.getTable();
            if (table.getUniqueKeys() != null && table.getUniqueKeyCount() > 0) {
                UniqueKey uniqueKey = table.getUniqueKey(0);
                
                if (uniqueKey.isForeignKey()) {
                    // Create OneToOneNaturalIdentity
                    // NOTE this is done in the relationships section
                } else {
                    if (uniqueKey.getColumnCount() == 1) {
                        // Create SimpleNaturalIdentity
                        createSimpleNaturalIdentity(entity, uniqueKey);
                    } else {
                        // Create ComponentNaturalIdentity
                        createComponentNaturalIdentity(entity, uniqueKey);
                    }
                }
            }
        }
    }

    private void createSimpleNaturalIdentity(EntityImpl entity,
            UniqueKey uniqueKey) throws ModelException {
        SimplePropertyNameExtractor extractor = new SimplePropertyNameExtractor(namingConventions);
        Column column = uniqueKey.getColumn(0);
        entity.createSimpleNaturalIdentity(extractor.extractName(entity, column), column.getColumnType().getPropertyType(), column.getName(), uniqueKey);
        
    }

    private void createComponentNaturalIdentity(EntityImpl entity,
            UniqueKey uniqueKey) throws ModelException {
        NaturalIdentityNameExtractor extractor = new NaturalIdentityNameExtractor();
        IdentityFieldNameExtractor fieldExtractor = new IdentityFieldNameExtractor(namingConventions);
        ComponentNaturalIdentityImpl identity = entity.createNaturalIdentity(extractor.extractName(entity, entity), uniqueKey); 
        
        // Add many-to-one fields
        for (ForeignKey foreignKey : uniqueKey.getForeignKeys()) {
            logger.log(Level.FINE, "ComponentNaturalIdentity: Many-to-one component field");
            Table relatedTable = foreignKey.getReferencedTable();
            Entity relatedEntity = entity.getCatalog().getEntityByTable(relatedTable);
            ManyToOneNameExtractor childExtractor = new ManyToOneNameExtractor(namingConventions);
            OneToManyNameExtractor parentExtractor = new OneToManyNameExtractor(namingConventions);
            FieldNameImpl childName = childExtractor.extractName(entity, foreignKey);
            FieldNameImpl parentName = parentExtractor.extractName(relatedEntity, foreignKey);
            identity.createManyToOneRelationship(parentName, childName, foreignKey.getName());
        }
        
        // Add simple property fields
        for (Column column : uniqueKey.getColumns()) {
            if (!identity.getColumns().contains(column)) {
                identity.createSimpleProperty(fieldExtractor.extractName(identity, column), column.getColumnType().getPropertyType(), column.getName());
            }
        }
    }
    
}
