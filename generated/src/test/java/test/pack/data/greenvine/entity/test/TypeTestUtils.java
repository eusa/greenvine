package test.pack.data.greenvine.entity.test;

import java.math.BigDecimal;
import java.util.Date;

public class TypeTestUtils {
    
    public static Type createDefaultInstance() {
    
        // Create new entity
        Type create = new Type();
                    
        // Set identity
        create.setType6(getDefaultIdentity());

        // Populate simple properties
        create.setType1(Integer.valueOf(1));
        create.setType10(new Date(61000L));
        create.setType12(new Date(1230768000000L));
        create.setType13(new Date(1230771661000L));
        create.setType14(new byte[]{116,101,115,116,49});
        create.setType16("s");
        create.setType17("s");
        create.setType18(new byte[]{116,101,115,116,49});
        create.setType19("s");
        create.setType2(Boolean.TRUE);
        create.setType20("s");
        create.setType3(Byte.valueOf("1"));
        create.setType4(Short.valueOf((short)1));
        create.setType5(Long.valueOf(1L));
        create.setType7(new BigDecimal("10000000.01"));
        create.setType8(Double.valueOf(10000000000000000.1));
        create.setType9(Float.valueOf(1000000.1F));

        // Return instance
        return create;

    }
    
    public static Long getDefaultIdentity() {
    
        return Long.valueOf(1L);                
    
    }

    
    
    
    public static Type createRandomInstance() {
    
        // create new entity
        Type create = new Type();
                    
        // set identity
        create.setType6(getRandomIdentity());
        
        // populate simple properties
        create.setType1(Integer.valueOf(2));
        create.setType10(new Date(3722000L));
        create.setType12(new Date(1233532800000L));
        create.setType13(new Date(1233540122000L));
        create.setType14(new byte[]{116,101,115,116,50});
        create.setType16("t");
        create.setType17("t");
        create.setType18(new byte[]{116,101,115,116,50});
        create.setType19("t");
        create.setType2(Boolean.FALSE);
        create.setType20("t");
        create.setType3(Byte.valueOf("2"));
        create.setType4(Short.valueOf((short)2));
        create.setType5(Long.valueOf(2L));
        create.setType7(new BigDecimal("20000000.02"));
        create.setType8(Double.valueOf(20000000000000000.2));
        create.setType9(Float.valueOf(2000000.2F));

        // return instance
        return create;

    }
    
    public static Long getRandomIdentity() {
    
        return Long.valueOf(2L);                
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Type clone(Type type){

        Type _type = new Type();
        
        if (type.getType6() != null) {
            _type.setType6(type.getType6());   
        }
        if (type.getType1() != null) {
            _type.setType1(type.getType1());
        }
        if (type.getType10() != null) {
            _type.setType10(new Date(type.getType10().getTime()));
        }
        if (type.getType12() != null) {
            _type.setType12(new Date(type.getType12().getTime()));
        }
        if (type.getType13() != null) {
            _type.setType13(new Date(type.getType13().getTime()));
        }
        if (type.getType14() != null) {
            _type.setType14(type.getType14());
        }
        if (type.getType16() != null) {
            _type.setType16(type.getType16());
        }
        if (type.getType17() != null) {
            _type.setType17(type.getType17());
        }
        if (type.getType18() != null) {
            _type.setType18(type.getType18());
        }
        if (type.getType19() != null) {
            _type.setType19(type.getType19());
        }
        if (type.getType2() != null) {
            _type.setType2(type.getType2());
        }
        if (type.getType20() != null) {
            _type.setType20(type.getType20());
        }
        if (type.getType3() != null) {
            _type.setType3(type.getType3());
        }
        if (type.getType4() != null) {
            _type.setType4(type.getType4());
        }
        if (type.getType5() != null) {
            _type.setType5(type.getType5());
        }
        if (type.getType7() != null) {
            _type.setType7(new BigDecimal(type.getType7().toPlainString()));
        }
        if (type.getType8() != null) {
            _type.setType8(type.getType8());
        }
        if (type.getType9() != null) {
            _type.setType9(type.getType9());
        }

        return _type;
    }
    
}