package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.AggregationRelation;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.FieldCollection;
import net.sourceforge.greenvine.model.api.FieldNamedObject;
import net.sourceforge.greenvine.model.api.ManyToOneAggregationField;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.RelationField;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;
import net.sourceforge.greenvine.model.naming.testutils.NamingConventionFactory;

import org.junit.Before;
import org.junit.Test;

public class AbstractComponentTest {

    private DatabaseImpl database;
    private TableImpl table;
    private ColumnImpl col1;
    private ColumnImpl col2;
    private ColumnImpl col3;
    private ColumnImpl col4;
    
    @Before
    public void setUp() throws ModelException {
        
        // Test items
        this.database = new DatabaseImpl("TEST_DB", NamingConventionFactory.getTestNamingConvention());
        this.table = new TableImpl("TABLE", database);
        this.col1 = table.createColumn("SIMPLE_1", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.col2 = table.createColumn("SIMPLE_2", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.col3 = table.createColumn("MANY_1", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
        this.col4 = table.createColumn("MANY_2", ColumnType.CHARACTER, true, ColumnValueGenerationStrategy.ASSIGNED);
    }
    
    @Test
    public void testGetColumns() throws ModelException {
    
        // Create component
        ConcreteComponent component = new ConcreteComponent("component", table);
        
        // Add simple properties
        component.createSimpleProperty("simple1", PropertyType.CHARACTER, true, col1.getName());
        component.createSimpleProperty("simple2", PropertyType.CHARACTER, true, col2.getName());
    
        // Add many-to-one
        SortedSet<ColumnImpl> manyCols = new TreeSet<ColumnImpl>();
        manyCols.add(col3);
        manyCols.add(col4);
        component.createManyToone("manyToOne", manyCols, component);
        
        // Test get columns
        SortedSet<? extends Column> columns = component.getColumns();
        Assert.assertNotNull(columns);
        Assert.assertEquals(4, columns.size());
        
    }
    
    @Test
    public void testGetColumnNames() throws ModelException {
    
        // Create component
        ConcreteComponent component = new ConcreteComponent("component", table);
        
        // Add simple properties
        component.createSimpleProperty("simple1", PropertyType.CHARACTER, true, col1.getName());
        component.createSimpleProperty("simple2", PropertyType.CHARACTER, true, col2.getName());
    
        // Add many-to-one
        SortedSet<ColumnImpl> manyCols = new TreeSet<ColumnImpl>();
        manyCols.add(col3);
        manyCols.add(col4);
        component.createManyToone("manyToOne", manyCols, component);
        
        // Test get columns
        SortedSet<? extends ColumnName> columns = component.getColumnNames();
        Assert.assertNotNull(columns);
        Assert.assertEquals(4, columns.size());
        
    }
    
    @Test
    public void testGetFieldBackedByColumn() throws ModelException {
    
        // Create component
        ConcreteComponent component = new ConcreteComponent("component", table);
        
        // Add simple properties
        SimplePropertyImpl simple1 = component.createSimpleProperty("simple1", PropertyType.CHARACTER, true, col1.getName());
        SimplePropertyImpl simple2 = component.createSimpleProperty("simple2", PropertyType.CHARACTER, true, col2.getName());
    
        // Add many-to-one
        SortedSet<ColumnImpl> manyCols = new TreeSet<ColumnImpl>();
        manyCols.add(col3);
        manyCols.add(col4);
        component.createManyToone("manyToOne", manyCols, component);
        
        // Test get field1
        SortedSet<Column> field1Cols = new TreeSet<Column>();
        field1Cols.add(simple1.getColumn());
        Field field1 = component.getFieldBackedByColumns(field1Cols);
        Assert.assertNotNull(field1);
        Assert.assertEquals(simple1, field1);
        
        // Test get field2
        SortedSet<Column> field2Cols = new TreeSet<Column>();
        field2Cols.add(simple2.getColumn());
        Field field2 = component.getFieldBackedByColumns(field2Cols);
        Assert.assertNotNull(field2);
        Assert.assertEquals(simple2, field2);
        
    }
    
}

class ConcreteComponent extends AbstractSimpleComponent {

    private final Table table;
    
    ConcreteComponent(CharSequence name, Table table) throws ModelException {
        super(name);
        this.table = table;
    }
    
    public Table getTable() {
        return table;
    }

    public ManyToOneAggregationField createManyToone(CharSequence name, SortedSet<ColumnImpl> manyCols,
            ConcreteComponent component) throws ModelException {
        return new DummyManyToOne(name, manyCols, component);
    }

    public SimplePropertyImpl createSimpleProperty(CharSequence name,
            PropertyType type, boolean notNull, CharSequence column) throws ModelException {
        return new SimplePropertyImpl(name, type, notNull, column, this);
    }

}

class DummyManyToOne implements ManyToOneAggregationField {
    
    private final FieldNameImpl name;
    private final SortedSet<ColumnImpl> cols;
    private final ConcreteComponent component;
    
    public DummyManyToOne(CharSequence name, SortedSet<ColumnImpl> cols, ConcreteComponent component) throws ModelException {
        this.name = new FieldNameImpl(name);
        this.cols = cols;
        this.component = component;
        component.addManyToOne(this);
    }

    public AggregationRelation getRelation() {
        return null;
    }

    public RelationField getReferencedField() {
        return null;
    }

    public boolean referencesIdentity() {
        return true;
    }

    public SortedSet<ColumnName> getColumnNames() {
        return null;
    }

    public SortedSet<ColumnImpl> getColumns() {
        return cols;
    }

    public FieldCollection getFieldCollection() {
        return component;
    }

    public boolean getNotNull() {
        return false;
    }

    public FieldNameImpl getName() {
        return name;
    }

    public EntityImpl getRelatedEntity() {
        return null;
    }

    public RelationField getRelatedField() {
        return null;
    }

    public boolean isOwner() {
        return false;
    }
    
    public boolean isDependency() {
        return true;
    }
    
    public int compareTo(FieldNamedObject arg0) {
        return 0;
    }
    
}