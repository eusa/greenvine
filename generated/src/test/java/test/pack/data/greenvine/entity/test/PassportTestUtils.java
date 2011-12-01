package test.pack.data.greenvine.entity.test;

import java.util.Date;

public class PassportTestUtils {
    
    public static Passport createDefaultInstance() {
    
        // Create new entity
        Passport create = new Passport();
                    
        // Set identity
        create.setPassportNr(getDefaultIdentity());

        // Populate simple properties
        create.setExpiryDate(new Date(1230768000000L));
                
        // Populate dependencies
        Person person = PersonTestUtils.createDefaultInstance();
        create.setPerson(person);

        // Return instance
        return create;

    }
    
    public static String getDefaultIdentity() {
    
        return "s";                
    
    }

    
    
    
    public static Passport createRandomInstance() {
    
        // create new entity
        Passport create = new Passport();
                    
        // set identity
        create.setPassportNr(getRandomIdentity());
        
        // populate simple properties
        create.setExpiryDate(new Date(1233532800000L));
                
        // populate dependencies
        Person person = PersonTestUtils.createRandomInstance();
        create.setPerson(person);

        // return instance
        return create;

    }
    
    public static String getRandomIdentity() {
    
        return "t";                
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Passport clone(Passport passport){

        Passport _passport = new Passport();
        
        if (passport.getPassportNr() != null) {
            _passport.setPassportNr(passport.getPassportNr());   
        }
        if (passport.getPerson() != null) {
            _passport.setPerson(PersonTestUtils.clone(passport.getPerson()));
        }
        if (passport.getExpiryDate() != null) {
            _passport.setExpiryDate(new Date(passport.getExpiryDate().getTime()));
        }

        return _passport;
    }
    
}