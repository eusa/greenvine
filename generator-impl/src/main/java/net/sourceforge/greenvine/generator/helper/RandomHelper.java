package net.sourceforge.greenvine.generator.helper;

import java.util.Random;

public class RandomHelper {
    
    private final static Random random = new Random();
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "123456789";
    
    public char getRandomChar() {
        return getRandomChar(ALPHABET);
    }
    
    public String getRandomNumericString(int length) {
        return generateString(NUMBERS, length);
    }
    
    public String getRandomAlphabetString(int length) {
        return generateString(ALPHABET, length);
    }

    public String getRandomBooleanString() {
        return Boolean.valueOf(random.nextBoolean()).toString();
    }
    
    private static String generateString(String characters, int length)
    {
        if (length > 10) {
            length = 10;
        }
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = getRandomChar(characters);
        }
        return new String(text);
    }

    private static char getRandomChar(String characters) {
        return characters.charAt(random.nextInt(characters.length()));
    }

    
}
