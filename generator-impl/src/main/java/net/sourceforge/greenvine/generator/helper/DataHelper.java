package net.sourceforge.greenvine.generator.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import sun.misc.BASE64Encoder;

public class DataHelper {
    
    private static final String CREATE_BYTES_STRING = "test1";
    private static final String UPDATE_BYTES_STRING = "test2";
    
    private static final String dateFormat ="yyyy-MM-dd";
    private static final String timeFormat = "hh:mm:ss";
    private static final String dateTimeFormat = "yyyy-MM-dd hh:mm:ss";
    private final DateFormat date;
    private final DateFormat time;
    private final DateFormat dateTime;
    
    private final Date defaultDateTime;
    private final Date defaultDate;
    private final Date defaultTime;
    
    private final Date updateDateTime;
    private final Date updateDate;
    private final Date updateTime;
    
    private final static RandomHelper random = new RandomHelper();
    
    public DataHelper() {
        
        // Create default calendar
        Calendar cal = GregorianCalendar.getInstance();
        
        // Create default date time
        cal.clear();
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 1);
        this.defaultDateTime = cal.getTime();
        
        // Create default date/time
        cal.clear();
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        this.defaultDate = cal.getTime();
        
        // Create default date
        cal.clear();
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 1);
        this.defaultTime = cal.getTime();
        
        // Create update date/time
        cal.clear();
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        cal.set(Calendar.HOUR_OF_DAY, 2);
        cal.set(Calendar.MINUTE, 2);
        cal.set(Calendar.SECOND, 2);
        this.updateDateTime = cal.getTime();
        
        // Create update date/time
        cal.clear();
        cal.set(Calendar.YEAR, 2009);
        cal.set(Calendar.MONTH, Calendar.FEBRUARY);
        cal.set(Calendar.DAY_OF_MONTH, 2);
        this.updateDate = cal.getTime();
        
        // Create update date/time
        cal.clear();
        cal.set(Calendar.HOUR_OF_DAY, 2);
        cal.set(Calendar.MINUTE, 2);
        cal.set(Calendar.SECOND, 2);
        this.updateTime = cal.getTime();
        
        // Date time 
        dateTime = new SimpleDateFormat(dateTimeFormat);
        
        // Just date
        date = new SimpleDateFormat(dateFormat);
        
        // Just time
        time = new SimpleDateFormat(timeFormat);
        
    }
    
    public Date getCreateDateTime() {
        return defaultDateTime;
    }

    public Date getCreateDate() {
        return defaultDate;
    }

    public Date getCreateTime() {
        return defaultTime;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getCreateString() {
        return "s";
    }
    
    public String getUpdateString() {
        return "t";
    }
    
    public String getCreateCharacter() {
        return "'s'";
    }
    
    public String getUpdateCharacter() {
        return "'t'";
    }
    
    public String getCreateBinaryBytes() {
        return getByteStringWithCommas(CREATE_BYTES_STRING);
    }
    
    public String getUpdateBinaryBytes() {
        return getByteStringWithCommas(UPDATE_BYTES_STRING);
    }

    private String getByteStringWithCommas(String binary) {
        StringBuilder bytes = new StringBuilder();
        for (byte b : binary.getBytes()) {
            bytes.append(b);
            bytes.append(',');
        }
        bytes.deleteCharAt(bytes.lastIndexOf(","));
        return bytes.toString();
    }
    
    public String getCreateBinaryBase64() {
        return getBase64String(CREATE_BYTES_STRING);
    }
    
    public String getUpdateBinaryBase64() {
        return getBase64String(UPDATE_BYTES_STRING);
    }
    
    private String getBase64String(String binary) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(binary.getBytes());
    }
    
    public String getCreateBoolean() {
        return "true";
    }
    
    public String getUpdateBoolean() {
        return "false";
    }
    
    public String getCreateInteger() {
        return "1";
    }
    
    public String getUpdateInteger() {
        return "2";
    }
    
    public String getCreateDecimal(int scale, int precision) {
        return getDecimalString(scale, precision, 1);
    }
    
    public String getUpdateDecimal(int scale, int precision) {
        return getDecimalString(scale, precision, 2);
    }
    
    private String getDecimalString(final int scale, final int precision, final int digit) {
        final int integerDigits = precision - scale;
        String integers = getIntegerDigits(integerDigits, digit);
        String decimals = getDecimalDigits(scale, digit);
        return integers + "." + decimals;
    }
    
    private String getIntegerDigits(final int length, final int digit) {
        final StringBuilder decimals = new StringBuilder();
        decimals.append(digit);
        for (int i = 1; i < length; i++) {
            decimals.append(0);
        }
        return decimals.toString();
    }
    
    private String getDecimalDigits(final int length, final int digit) {
        final StringBuilder decimals = new StringBuilder();
        for (int i = 1; i < length; i++) {
            decimals.append(0);
        }
        decimals.append(digit);
        return decimals.toString();
    }

    public Date getRandomTime() {
        return new Date(Long.valueOf(random.getRandomNumericString(13)));
    }

    public Date getRandomDateTime() {
        return new Date(Long.valueOf(random.getRandomNumericString(13)));
    }
    
    public Date getRandomDate() {
        return new Date(Long.valueOf(random.getRandomNumericString(13)));
    }

    
    public String getRandomString(int length) {
        return random.getRandomAlphabetString(length);
    }

    public String getRandomLongInteger() {
        return getRandomInteger(8);
    }
    
    public String getRandomInteger() {
        return getRandomInteger(6);
    }
    
    public String getRandomSmallInteger() {
        return getRandomInteger(4);
    }
    
    public String getRandomTinyInteger() {
        return getRandomInteger(2);
    }
    
    private String getRandomInteger(int length) {
        return random.getRandomNumericString(length);
    }

    public String getRandomDecimal(int scale, int precision) {
        StringBuilder dec = new StringBuilder();
        dec.append(random.getRandomNumericString(precision - scale));
        dec.append('.');
        dec.append(random.getRandomNumericString(scale));
        return dec.toString();
    }

    public String getRandomBinaryBytes(int number) {
        String binary = random.getRandomAlphabetString(number);
        return getByteStringWithCommas(binary);
    }
    
    public String getRandomBoolean() {
        return random.getRandomBooleanString();
    }

    public Object getRandomCharacter() {
        return random.getRandomChar();
    }

    public String getRandomBinaryBase64(int number) {
        String binary = random.getRandomAlphabetString(number);
        return getBase64String(binary);
    }

    public String getCreateDateString() {
        return date.format(defaultDate);
    }
    
    public String getUpdateDateString() {
        return date.format(updateDate);
    }
    
    public String getRandomDateString() {
        return date.format(getRandomDate());
    }
    
    public String getCreateTimeString() {
        return time.format(defaultTime);
    }
    
    public String getUpdateTimeString() {
        return time.format(updateTime);
    }
    
    public String getRandomTimeString() {
        return time.format(getRandomDate());
    }
    
    public String getCreateTimestampString() {
        return dateTime.format(defaultDateTime);
    }
    
    public String getUpdateTimestampString() {
        return dateTime.format(updateDateTime);
    }
    
    public String getRandomTimestampString() {
        return dateTime.format(getRandomDate());
    }

}
