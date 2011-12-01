package net.sourceforge.greenvine.model.naming.impl;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.DatabaseObjectName;
import net.sourceforge.greenvine.model.naming.Name;
import net.sourceforge.greenvine.model.naming.NameValidator;
import net.sourceforge.greenvine.model.naming.RdbmsNameSegment;


public final class DatabaseObjectNameImpl implements DatabaseObjectName {
    
    private final String qualifiedName;
    private final RdbmsNameSegmentImpl schemaName;
    private final RdbmsNameSegmentImpl objectName;
    private static final Character separatorChar = '.';
    
    public DatabaseObjectNameImpl(CharSequence schemaName, CharSequence objectName) throws ModelException {
        
        // Validate object name 
        validateNameComponent(objectName);
        
        // Validate schema name (if provided)
        if (schemaName != null) {
            validateNameComponent(schemaName);
        }
        // Set names
        this.objectName = new RdbmsNameSegmentImpl(objectName);
        StringBuilder qualifiedNameBuilder = new StringBuilder();
        if (schemaName != null) {
            this.schemaName = new RdbmsNameSegmentImpl(schemaName);
            qualifiedNameBuilder.append(schemaName);
            qualifiedNameBuilder.append(separatorChar);
        } else {
            this.schemaName = null;
        }
        qualifiedNameBuilder.append(objectName);
        this.qualifiedName = qualifiedNameBuilder.toString();
    }

    @Override
    public String toString() {
        return this.qualifiedName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((qualifiedName == null) ? 0 : qualifiedName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof CharSequence))
            return false;
        CharSequence other = (CharSequence) obj;
        if (other.length() != length()) {
            return false;
        } else {
            for (int i = 0; i < length(); i++) {
                if (charAt(i) != other.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int compareTo(Name obj) {
        return this.qualifiedName.compareTo(obj.toString());
    }

    public char charAt(int index) {
        return this.qualifiedName.charAt(index);
    }

    public int length() {
        return this.qualifiedName.length();
    }

    public CharSequence subSequence(int start, int end) {
        return this.qualifiedName.subSequence(start, end);
    }
    
    public RdbmsNameSegment getObjectName() {
        return this.objectName;
    }
    
    public RdbmsNameSegmentImpl getSchemaName() {
        return this.schemaName;
    }

    public static DatabaseObjectNameImpl parse(CharSequence fqName) throws ModelException {
        if (fqName == null || fqName.length() == 0) {
            throw new ModelException("Cannot parse null or empty name");
        }
        String parseMe = new StringBuilder(fqName).toString();
        String[] components = parseMe.split("\\" + separatorChar);
        if (components.length > 2) {
            throw new ModelException(String.format("Cannot parse name %s as too many period (.) characters detected.", fqName));
        } else if (components.length == 2) {
            return new DatabaseObjectNameImpl(components[0], components[1]);
        } else {
            return new DatabaseObjectNameImpl(null, components[0]);
        }
    }
    
    private void validateNameComponent(CharSequence component) throws ModelException {
        NameValidator<CharSequence> validator = new RdbmsNameValidatorImpl();
        validator.validateName(component);
    }

    public List<RdbmsNameSegmentImpl> getSegments() {
        List<RdbmsNameSegmentImpl> segments = new ArrayList<RdbmsNameSegmentImpl>();
        if (schemaName != null) {
            segments.add(schemaName);
        }
        segments.add(objectName);
        return segments;
    }

    public Character getSeparatorChar() {
        return separatorChar;
    }

}
