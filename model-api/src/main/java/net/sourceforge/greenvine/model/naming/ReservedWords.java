package net.sourceforge.greenvine.model.naming;

import java.util.Arrays;
import java.util.List;

public class ReservedWords {
	private static final String[] reserved = new String[]{
		"abstract", 
		"assert", 
		"boolean", 
		"break", 
		"byte", 
		"case", 
		"catch", 
		"char", 
		"class", 
		"const", 
		"continue", 	
		"default", 
		"do", 
		"double", 
		"else", 
		"enum", 
		"extends", 
		"false", 
		"final", 
		"finally", 
		"float", 
		"for", 		
		"goto", 
		"if", 
		"implements", 
		"import", 
		"instanceof", 
		"int", 
		"interface", 
		"long", 
		"native", 
		"new", 
		"null", 
		"package", 
		"private", 
		"protected", 
		"public", 
		"return", 
		"short", 
		"static", 
		"strictfp", 
		"super", 
		"switch", 	
		"synchronized", 
		"this", 
		"throw", 
		"throws", 
		"transient", 
		"true", 
		"try", 
		"void", 
		"volatile", 
		"while" 
	};
	
	public static boolean isReserved(CharSequence word) {
		List<String> t = Arrays.asList(reserved);
		boolean ret = false;
		if (t.contains(word.toString())) {
			ret = true;
		}
		return ret;
	}

}
