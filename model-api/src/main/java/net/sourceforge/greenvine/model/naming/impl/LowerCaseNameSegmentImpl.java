package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.LowerCaseNameSegment;
import net.sourceforge.greenvine.model.naming.NameValidator;

public class LowerCaseNameSegmentImpl extends AbstractNameSegment implements LowerCaseNameSegment {
    
    public LowerCaseNameSegmentImpl(CharSequence name) throws ModelException {
        super(name);
        
        // Validate name 
        NameValidator<CharSequence> validator = new LowerCaseCharSequenceValidatorImpl();
        validator.validateName(this);
        
    }

}
