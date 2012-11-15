package net.sourceforge.greenvine.model.naming.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.greenvine.model.api.ModelException;
import net.sourceforge.greenvine.model.api.RdbmsNamedObject;
import net.sourceforge.greenvine.model.naming.CamelCaseNameSegmentBuilder;
import net.sourceforge.greenvine.model.naming.LowerCaseNameSegmentBuilder;
import net.sourceforge.greenvine.model.naming.RdbmsName;
import net.sourceforge.greenvine.model.naming.RdbmsNameSegment;
import net.sourceforge.greenvine.model.naming.RdbmsNamingConvention;

public class RdbmsNameBuilderHelper {

	private RdbmsNamingConvention convention;

	public RdbmsNameBuilderHelper(RdbmsNamingConvention convention) throws ModelException {
		if (convention == null) {
			throw new ModelException("Null RdbmsNamingConvention in RdbmsNameBuilderHelper");
		}
		this.convention = convention;
	}

	private CharSequence removeSuffix(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		String loweredName = builder.toString().toLowerCase();
		if (convention.hasSuffix()) {
			String suffix = convention.getSuffix();
			if (loweredName.endsWith(suffix.toLowerCase())) {
				builder.delete(loweredName.length() - suffix.length(), loweredName.length());
			}
		}
		return builder;
	}

	private CharSequence removePrefix(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		String loweredName = builder.toString().toLowerCase();
		if (convention.hasPrefix()) {
			String prefix = convention.getPrefix();
			if (loweredName.startsWith(prefix.toLowerCase())) {
				builder.delete(0, prefix.length());
			}
		}
		return builder;
	}

	public CharSequence removePrefixIncludingSeparator(CharSequence target) {
		CharSequence noPrefix = removePrefix(target);
		CharSequence noSep = removeLeadingSeparator(noPrefix);
		return noSep;
	}

	public CharSequence removeSuffixIncludingSeparator(CharSequence target) {
		CharSequence noSuffix = removeSuffix(target);
		CharSequence noSep = removeTrailingSeparator(noSuffix);
		return noSep;
	}

	public CharSequence removeSuffixPrefixIncludingSeparators(CharSequence target) {
		CharSequence noSuffix = removeSuffixIncludingSeparator(target);
		CharSequence noPrefix = removePrefixIncludingSeparator(noSuffix);
		return noPrefix;
	}

	public CharSequence removeLeadingSeparator(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		if (convention.hasSeparator()) {
			String separator = convention.getSeparator();
			if (builder.toString().startsWith(separator)) {
				builder.delete(0, separator.length());
			}
		}
		return builder;
	}

	public CharSequence removeTrailingSeparator(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		if (convention.hasSeparator()) {
			String separator = convention.getSeparator();
			if (builder.toString().endsWith(separator)) {
				builder.delete(builder.length() - separator.length(), builder.length());
			}
		}
		return builder;
	}

	public CharSequence surroundWithSuffixAndPrefix(CharSequence target) {
		//StringBuilder builder = new StringBuilder(target);
		target = prependPrefix(target);
		target = appendSuffix(target);
		//return builder;
		return target;
	}

	public CharSequence prependPrefix(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		builder.insert(0, convention.getPrefixAndSeparator());
		return builder;
	}

	public CharSequence appendSuffix(CharSequence target) {
		StringBuilder builder = new StringBuilder(target);
		builder.append(convention.getSuffixAndSeparator());
		return builder;
	}

	public CharSequence stripAllNonAlphanumeric(CharSequence target) {
		// Build regex
		String alphanumerics = "A-Za-z0-9";
		if (convention.hasSeparator()) {
			alphanumerics = alphanumerics + convention.getSeparator();
		}
		String regex = "[" + alphanumerics + "]";

		// Remove non-matching characters
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		StringBuffer replace = new StringBuffer();
		while(matcher.find()) {
			String match = matcher.group();
			//matcher.appendReplacement(replace, match);
			replace.append(match);
		}
		//matcher.appendTail(replace);
		return replace;
	}

	public CharSequence convertToPascalCase(CharSequence target) {
		String pascalCase = new StringBuilder(target).toString();
		// only apply transform if separators supplied
		if (convention.hasSeparator() && pascalCase.contains(convention.getSeparator())) {
			return convertToPascalCaseSeparated(target);
		} else {
			return convertToPascalCaseUnseparated(target);
		}
	}

	public CharSequence convertToCamelCase(CharSequence target) {
		CharSequence pascal = convertToPascalCase(target);
		StringBuilder builder = new StringBuilder(pascal);
		builder.replace(0, 1, builder.substring(0,1).toLowerCase());
		return builder;
	}

	public CharSequence convertToLowerCase(CharSequence target) {
		// Build regex
		String alphanumerics = "A-Za-z0-9";
		String regex = "[" + alphanumerics + "]";

		// Remove non-matching characters
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		StringBuffer replace = new StringBuffer();
		while(matcher.find()) {
			String match = matcher.group();
			replace.append(match);
		}
		return replace.toString().toLowerCase();
	}

	/**
	 * Convert a string with separator characters (e.g. underscore)
	 * to Pascal case assuming that each separator represents
	 * a word boundary where the first letter should be capitalised
	 * @param target
	 * @return
	 */
	private CharSequence convertToPascalCaseSeparated(CharSequence target) {
		if (convention.hasSeparator()) {
			String separator = convention.getSeparator();
			StringBuilder builder = new StringBuilder(target);
			String[] parts = builder.toString().split(separator);
			StringBuilder replace = new StringBuilder();
			for (String part : parts) {
				replace.append(convertSegmentToPropercase(part));
			}
			target = replace;
		}
		return target;
	}

	public CharSequence convertToPascalCaseUnseparated(CharSequence target) {
		String regex = "[A-Z]{3,}[0-9]{0,1}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		StringBuffer buf = new StringBuffer();
		while(matcher.find()) {
			String match = matcher.group();
			int length = match.length();
			String proc = match.substring(0, 1) + match.substring(1, length-1).toLowerCase();
			if (matcher.end() != target.length()) {
				proc = proc + match.substring(length -1, length);
			} else {
				proc = proc + match.substring(length -1, length).toLowerCase();
			}
			matcher.appendReplacement(buf, proc);
		}
		matcher.appendTail(buf);
		buf.replace(0, 1, buf.substring(0,1).toUpperCase());
		return buf;
	}

	private CharSequence convertSegmentToPropercase(CharSequence target) {
		String s = new StringBuilder(target).toString();
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public CharSequence insertSeparatorAtCapitals(CharSequence target) {
		if (convention.hasSeparator()) {
			String separator = convention.getSeparator();
			String regex = "[A-Z]";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(target);
			StringBuffer buf = new StringBuffer();
			while (matcher.find()) {
				//int start = matcher.start();
				//int end = matcher.end();
				//String match = builder.substring(start, end);
				String match = matcher.group();
				String replace = separator + match;
				matcher.appendReplacement(buf, replace);
			}
			matcher.appendTail(buf);
			target = buf;
		}
		return target;
	}

	public CharSequence toCase(CharSequence target) {
		return convention.getCaseStrategy().toCase(target);
	}

	public <T extends RdbmsNamedObject> CharSequence extractModelName(T target) throws ModelException {

		// Remove all non-alphanumeric characters (except separator)
		CharSequence strip = stripAllNonAlphanumeric(target.getName());

		// Strip off the prefix, suffix and separator
		strip = removeSuffixPrefixIncludingSeparators(strip);

		// Convert to camel case
		strip = convertToCamelCase(strip);

		// Return the result
		return strip;
	}

	public <T extends RdbmsNameSegment> CamelCaseNameSegmentBuilder extractModelName(T target) throws ModelException {

		// Remove all non-alphanumeric characters (except separator)
		CharSequence strip = stripAllNonAlphanumeric(target);

		// Strip off the prefix, suffix and separator
		strip = removeSuffixPrefixIncludingSeparators(strip);

		// Convert to camel case
		strip = convertToCamelCase(strip);

		// Return the result
		return new CamelCaseNameSegmentBuilderImpl(strip);
	}

	public <T extends RdbmsNameSegment> LowerCaseNameSegmentBuilder extractNameSpace(T target) throws ModelException {

		// Remove all non-alphanumeric characters (except separator)
		CharSequence strip = stripAllNonAlphanumeric(target);

		// Strip off the prefix, suffix and separator
		strip = removeSuffixPrefixIncludingSeparators(strip);

		// Convert to camel case
		strip = convertToLowerCase(strip);

		// Return the result
		return new LowerCaseNameSegmentBuilderImpl(strip);
	}

	public <T extends RdbmsName> CamelCaseNameSegmentBuilder extractModelName(T target) throws ModelException {

		// Remove all non-alphanumeric characters (except separator)
		CharSequence strip = stripAllNonAlphanumeric(target);

		// Strip off the prefix, suffix and separator
		strip = removeSuffixPrefixIncludingSeparators(strip);

		// Convert to camel case
		strip = convertToCamelCase(strip);

		// Return the result
		return new CamelCaseNameSegmentBuilderImpl(strip);
	}

	public void validateName(RdbmsName name) throws ModelException {
		// Name can't be null
		if (name == null || name.length() == 0) {
			throw new ModelException(String.format("Invalid name: null or empty."));
		}
		// Check name matches validator regular expression
		Pattern pattern = convention.getValidatorRegex();
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			throw new ModelException(String.format("Invalid name %s doesn't match regular expression %s.", name, pattern));
		}

	}

}
