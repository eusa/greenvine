package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.CamelCaseNameSegment;
import net.sourceforge.greenvine.model.naming.NameValidator;

public class CamelCaseNameSegmentImpl extends AbstractNameSegment implements CamelCaseNameSegment {
    
    public CamelCaseNameSegmentImpl(CharSequence name) throws ModelException {
        super(name);
        
        // Validate name 
        NameValidator<CharSequence> validator = new CamelCaseCharSequenceValidatorImpl();
        validator.validateName(this);
        
    }

}
