package net.sourceforge.greenvine.model.naming.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Useful methods for processing names
 * of items to apply naming conventions
 * or extract Java names from database
 * names following a supplied convention
 *
 */
public class ModelNameExtractorHelper {
    
    public boolean isCamelCase(CharSequence target) {
        Pattern camel = Pattern.compile("^([a-z]{1}[A-Za-z0-9]+)*$");
        if(camel.matcher(target).matches() ) {
            return true;
        }
        return false;        
    }
    
    public boolean isPascalCase(CharSequence target) {
        Pattern pascal = Pattern.compile("^([A-Z][a-z0-9]+)*[A-Z][a-z0-9]*");
        if(pascal.matcher(target).matches() ) {
            return true;
        }
        return false;       
    }
    
    public boolean isUpperCase(CharSequence target) {
        Pattern mixedCaseAlphabet = Pattern.compile("^([A-Z])*");
        Matcher matcher = mixedCaseAlphabet.matcher(target);
        if(matcher.matches()) {
            return true;
        }
        return false;        
    }
    
    public boolean isLowerCase(CharSequence target) {
        Pattern mixedCaseAlphabet = Pattern.compile("^([a-z])*");
        Matcher matcher = mixedCaseAlphabet.matcher(target);
        if(matcher.matches()) {
            return true;
        }
        return false;        
    }
	
	public static StringBuilder firstToLowerCase(StringBuilder builder) {
	    String lower = builder.substring(0, 1).toLowerCase();
	    builder.replace(0, 1, lower);
        //String post = string.substring(1, string.length());
        //String first = ("" + string.charAt(0)).toLowerCase();
        return builder;
    }
	
	public static String firstToUpperCase(String string) {
        String post = string.substring(1, string.length());
        String first = ("" + string.charAt(0)).toUpperCase();
        return first + post;
    }
	

}
