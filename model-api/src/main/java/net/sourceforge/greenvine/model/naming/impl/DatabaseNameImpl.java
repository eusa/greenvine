package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.DatabaseName;
import net.sourceforge.greenvine.model.naming.NameValidator;


public class DatabaseNameImpl extends AbstractName implements DatabaseName {
    
    public DatabaseNameImpl(CharSequence name) throws ModelException {
        super(name);
        
        // Validate name
        NameValidator<CharSequence> validator = new RdbmsNameValidatorImpl();
        validator.validateName(this);
        
    }

}
