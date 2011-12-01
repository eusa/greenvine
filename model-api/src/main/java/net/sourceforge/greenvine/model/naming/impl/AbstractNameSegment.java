package net.sourceforge.greenvine.model.naming.impl;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.naming.NameSegment;

public abstract class AbstractNameSegment implements NameSegment {

    protected final String name;

    public AbstractNameSegment(CharSequence name) throws ModelException {
        super();
        
        // Check name is not null or empty
        if (name == null || name.length() == 0) {
            throw new ModelException("Null or empty name.");
        }
        
        // Set name
        this.name = new StringBuilder(name).toString();
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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

    public int compareTo(NameSegment obj) {
        return this.name.compareTo(obj.toString());
    }

    public char charAt(int index) {
        return this.name.charAt(index);
    }

    public int length() {
        return this.name.length();
    }

    public CharSequence subSequence(int start, int end) {
        return this.name.subSequence(start, end);
    }

}