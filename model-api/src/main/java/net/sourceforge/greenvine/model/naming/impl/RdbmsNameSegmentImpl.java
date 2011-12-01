package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.NameValidator;
import net.sourceforge.greenvine.model.naming.RdbmsNameSegment;


public class RdbmsNameSegmentImpl extends AbstractNameSegment implements RdbmsNameSegment {
    
    public RdbmsNameSegmentImpl(CharSequence name) throws ModelException {
        super(name);
        
        // Validate name
        NameValidator<CharSequence> validator = new RdbmsNameValidatorImpl();
        validator.validateName(this);
        
    }

}
