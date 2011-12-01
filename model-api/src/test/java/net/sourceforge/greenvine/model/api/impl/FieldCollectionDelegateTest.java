package net.sourceforge.greenvine.model.api.impl;

import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Column;
import net.sourceforge.greenvine.model.api.ColumnField;
import net.sourceforge.greenvine.model.api.ColumnType;
import net.sourceforge.greenvine.model.api.ColumnValueGenerationStrategy;
import net.sourceforge.greenvine.model.api.Field;
import net.sourceforge.greenvine.model.api.FieldCollection;
import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.NamedObject;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.Table;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.FieldName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.impl.FieldNameImpl;

import org.junit.Before;
import org.junit.Test;

public class FieldCollectionDelegateTest {
    
    private DatabaseImpl database;
    private TableImpl table;
    private ColumnImpl column1;
    private ConcreteFieldCollection fieldCollection;
    private FieldCollectionDelegate delegate;
    private MyField myField;
    private MyColumnField myColumnField;
    
    
    @Before
    public void setUp() throws ModelException {
        this.database = new DatabaseImpl("TEST_DB");
        this.table = database.createTable("TBL_TEST");
        this.column1 = table.createColumn("COL1", ColumnType.CHARACTER, false, ColumnValueGenerationStrategy.ASSIGNED);
        this.fieldCollection = new ConcreteFieldCollection("test", table);
        this.delegate = new FieldCollectionDelegate(fieldCollection);
        this.myField = new MyField("field1", fieldCollection);
        this.myColumnField = new MyColumnField("colField", fieldCollection, column1.getColumnType().getPropertyType(), column1);
    }
    
    @Test
    public void testAddFieldsAndSearch() throws ModelException {
        delegate.addField(myField);
        delegate.addField(myColumnField);
        Assert.assertEquals(2, delegate.getFieldCount());
        Assert.assertEquals(2, delegate.getFieldCount(MyField.class));
        Assert.assertEquals(1, delegate.getFieldCount(MyColumnField.class));
        MyField field1 = delegate.getFieldByName(MyField.class, "field1");
        Assert.assertEquals(myField, field1);
        MyField field2 = delegate.getFieldByName(MyField.class, "colField");
        Assert.assertEquals(myColumnField, field2);
        MyField field3 = delegate.getFieldByName(MyColumnField.class, "colField");
        Assert.assertEquals(myColumnField, field3);
    }
    
    @Test
    public void testGetFieldBackedByColumn() throws ModelException {
        delegate.addField(myField);
        delegate.addField(myColumnField);
        SortedSet<Column> columns = new TreeSet<Column>();
        columns.add(column1);
        Field colField = delegate.getFieldBackedByColumns(columns);
        Assert.assertEquals(myColumnField, colField);
    }
    
    @Test(expected = ModelException.class)
    public void testAddDuplicateName() throws ModelException {
        delegate.addField(myField);
        delegate.addField(new MyField("field1", fieldCollection));
    }
    
    @Test(expected = ModelException.class)
    public void testAddDuplicateColumn() throws ModelException {
        delegate.addField(myColumnField);
        delegate.addField(new MyColumnField("colField2", fieldCollection, column1.getColumnType().getPropertyType(), column1));
    }
    
    @Test(expected = ModelException.class)
    public void testFindNonExisting() throws ModelException {
        delegate.getFieldByName("fieldxxx");
    }
    
    @Test(expected = ModelException.class)
    public void testFindNonExistingByClass() throws ModelException {
        delegate.getFieldByName(MyColumnField.class, "field1");
    }

}

/**
 * Test {@link FieldCollection}
 * @author patrick
 *
 */
class ConcreteFieldCollection implements FieldCollection {

    private final FieldNameImpl name;
    private final FieldCollectionDelegate delegate;
    private final Table table;
    
    public ConcreteFieldCollection(CharSequence name, Table table) throws ModelException {
        this.name = new FieldNameImpl(name);
        this.delegate = new FieldCollectionDelegate(this);
        this.table = table;
    }
    
    public boolean containsName(CharSequence name) throws ModelException {
        return delegate.containsFieldName(name);
    }

    public SortedSet<ColumnName> getColumnNames() {
        return this.delegate.getColumnNames();
    }

    public SortedSet<Column> getColumns() {
        return this.delegate.getColumns();
    }

    public Field getField(int index) {
        return this.delegate.getField(index);
    }

    public Field getFieldBackedByColumns(SortedSet<? extends Column> columns) {
        return this.delegate.getFieldBackedByColumns(columns);
    }

    public Field getFieldByName(CharSequence name) throws ModelException {
        return this.delegate.getFieldByName(name);
    }

    public int getFieldCount() {
        return this.delegate.getFieldCount();
    }

    public SortedSet<FieldName> getNames() {
        return this.delegate.getFieldNames();
    }

    public SortedSet<Field> getFields() {
        return this.delegate.getFields();
    }

    public Table getTable() {
        return this.table;
    }

    public Name getName() {
        return this.name;
    }
    
    public int compareTo(NamedObject arg0) {
        return 0;
    }
    
}

/**
 * Test {@link Field}
 * @author patrick
 *
 */
class MyField implements Field {

    private final FieldNameImpl name;
    private final FieldCollection fieldCollection;
    
    public MyField(CharSequence name, FieldCollection fieldCollection) throws ModelException {
        this.name = new FieldNameImpl(name);
        this.fieldCollection = fieldCollection;
    }
    
    public FieldCollection getFieldCollection() {
        return fieldCollection;
    }

    public boolean getNotNull() {
        return false;
    }

    public FieldNameImpl getName() {
        return this.name;
    }
    
    public int compareTo(NamedObject arg0) {
        return 0;
    }
}

/** 
 * Test {@link ColumnField}
 * @author patrick
 *
 */
class MyColumnField extends MyField implements ColumnField {

    private final ColumnImpl column;
    private final PropertyType propertyType;
    
    public MyColumnField(CharSequence name, FieldCollection fieldCollection, PropertyType propertyType, ColumnImpl column)
            throws ModelException {
        super(name, fieldCollection);
        this.propertyType = propertyType;
        this.column = column;
    }

    public ColumnImpl getColumn() {
        return column;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }
    
    public int compareTo(NamedObject arg0) {
        return 0;
    }

}