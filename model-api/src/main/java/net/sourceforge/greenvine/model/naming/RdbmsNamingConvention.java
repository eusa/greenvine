package net.sourceforge.greenvine.model.naming;

import java.util.regex.Pattern;


public class RdbmsNamingConvention  {

    private String separator = null;
    private String prefix = null;
    private String suffix = null;
    private Pattern validatorRegex = Pattern.compile("^([A-Za-z_0-9\\.]+)*$");
    private CaseStrategy caseStrategy = CaseStrategy.UPPERCASE;

    public RdbmsNamingConvention() {
        
    }
    
    public RdbmsNamingConvention(String separator, String prefix, String suffix, String validatorRegex, CaseStrategy caseStrategy) {
        this.separator = separator;
        this.prefix = prefix;
        this.suffix = suffix;
        this.validatorRegex = Pattern.compile(validatorRegex);
        this.caseStrategy = caseStrategy;
    }

    public String getSeparator() {
        return this.separator;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }
    
    public CaseStrategy getCaseStrategy() {
        return this.caseStrategy;
    }

    public Pattern getValidatorRegex() {
        return this.validatorRegex;
    }
    
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setValidatorRegex(Pattern validatorRegex) {
        this.validatorRegex = validatorRegex;
    }

    public void setCaseStrategy(CaseStrategy caseStrategy) {
        this.caseStrategy = caseStrategy;
    }
    
    public boolean hasPrefix() {
        return !isStringNullOrEmpty(this.prefix);
    }
    
    public boolean hasSuffix() {
        return !isStringNullOrEmpty(this.suffix);
    }
    
    public boolean hasSeparator() {
        return !isStringNullOrEmpty(this.separator);
    }
    
    private boolean isStringNullOrEmpty(String target) {
        if (target == null || target.length() == 0) {
            return true;
        }
        return false;
    }
    
    public String getPrefixAndSeparator() {
        String prefix = "";
        if (this.hasPrefix()) {
            prefix = this.getPrefix() + this.getSeparator();
        }
        return prefix;
    }
    
    public String getSuffixAndSeparator() {
        String suffix = "";
        if (this.hasSuffix()) {
            suffix = this.getSeparator() + this.getSuffix();
        }
        return suffix;
    }
    
    public static RdbmsNamingConvention getDefault() {
        return new RdbmsNamingConvention(null, null, null, "^([A-Za-z_0-9\\.]+)*$", CaseStrategy.UPPERCASE);
    }
    
}
