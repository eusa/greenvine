package net.sourceforge.greenvine.model.naming.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.NameValidator;

public class CamelCaseCharSequenceValidatorImpl implements NameValidator<CharSequence> {

    public void validateName(CharSequence name) throws ModelException {
        
        // Name can't be null
        if (name == null || name.length() == 0) {
            throw new ModelException(String.format("Cannot create a model object with a null or empty name"));
        }
        // Check name is camel cased alphanumeric
        Pattern pattern = Pattern.compile("^([a-z]{1}[A-Za-z0-9]+)*$");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            throw new ModelException(String.format("Cannot create a model object with an invalid name - must be a camel cased alphanumeric string."));
        }
        
    }

}
