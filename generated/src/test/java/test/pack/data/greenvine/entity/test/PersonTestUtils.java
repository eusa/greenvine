package test.pack.data.greenvine.entity.test;

import java.util.Date;

public class PersonTestUtils {
    
    public static Person createDefaultInstance() {
    
        // Create new entity
        Person create = new Person();
                    
        // Set identity
        create.setPersonIdentity(getDefaultIdentity());

        // Populate simple properties
        create.setBirthday(new Date(1230768000000L));

        // Return instance
        return create;

    }
    
    public static PersonIdentity getDefaultIdentity() {
    
        PersonIdentity personIdentity = new PersonIdentity();
        personIdentity.setFirstName("s");
        personIdentity.setLastName("s");
        return personIdentity;
    
    }

    
    
    public static void populateAssignedIdentityFields(PersonIdentity personIdentity) {
    
        personIdentity.setFirstName("s");
        personIdentity.setLastName("s");
          
    }
    
    public static Person createRandomInstance() {
    
        // create new entity
        Person create = new Person();
                    
        // set identity
        create.setPersonIdentity(getRandomIdentity());
        
        // populate simple properties
        create.setBirthday(new Date(1233532800000L));

        // return instance
        return create;

    }
    
    public static PersonIdentity getRandomIdentity() {
    
        PersonIdentity personIdentity = new PersonIdentity();
        personIdentity.setFirstName("t");
        personIdentity.setLastName("t");
        return personIdentity;
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Person clone(Person person){

        Person _person = new Person();
        
        if (person.getPersonIdentity() != null) {
            PersonIdentity personIdentity = person.getPersonIdentity();
            PersonIdentity _personIdentity = new PersonIdentity();
            _personIdentity.setFirstName(personIdentity.getFirstName());
            _personIdentity.setLastName(personIdentity.getLastName());
            _person.setPersonIdentity(_personIdentity);
        }
        if (person.getBirthday() != null) {
            _person.setBirthday(new Date(person.getBirthday().getTime()));
        }
        if (person.getCustomer() != null) {
            _person.setCustomer(CustomerTestUtils.clone(person.getCustomer()));
        }
        if (person.getPassport() != null) {
            _person.setPassport(PassportTestUtils.clone(person.getPassport()));
        }

        return _person;
    }
    
}