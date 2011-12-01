package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

public interface Component extends FieldCollection {
    
        public abstract SortedSet<? extends ColumnField> getSimpleProperties();
        
        public abstract ColumnField getSimpleProperty(CharSequence name) throws ModelException;
        
        public abstract int getSimplePropertyCount();
        
        public abstract SortedSet<? extends ManyToOneAggregationField> getManyToOnes();
        
        public abstract ManyToOneAggregationField getManyToOne(CharSequence name) throws ModelException;
        
        public abstract int getManyToOneCount();
        
        public abstract Table getTable();

}
