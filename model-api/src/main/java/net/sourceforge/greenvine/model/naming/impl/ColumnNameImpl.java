package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.ColumnName;
import net.sourceforge.greenvine.model.naming.NameValidator;


public class ColumnNameImpl extends AbstractName implements ColumnName {
    
    public ColumnNameImpl(CharSequence name) throws ModelException {
        super(name);
        
        // Validate name 
        NameValidator<CharSequence> validator = new RdbmsNameValidatorImpl();
        validator.validateName(this);
        
    }

}
