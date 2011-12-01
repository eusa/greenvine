package net.sourceforge.greenvine.model.api;

import java.util.SortedSet;

    public interface SimpleComponent extends Component {
        
    public abstract SortedSet<? extends ColumnField> getSimpleProperties();
    
    public abstract ColumnField getSimpleProperty(CharSequence name) throws ModelException;
    
    public abstract int getSimplePropertyCount();
    
}
