package net.sourceforge.greenvine.model.naming;

public enum CaseStrategy {
    
    LOWERCASE, UPPERCASE, ANYCASE;
    
    public String toCase(CharSequence target) {
        String cased = new StringBuilder(target).toString();
        switch (this) {
            case LOWERCASE:
                return cased.toLowerCase();
            case UPPERCASE:
                return cased.toUpperCase();
            default:
                return cased;
        }
        
    }

}
