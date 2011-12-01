package test.pack.data.greenvine.entity.test;


public class CustomerTestUtils {
    
    public static Customer createDefaultInstance() {
    
        // Create new entity
        Customer create = new Customer();
                    
        // Set identity
        create.setPersonIdentity(getDefaultIdentity());

        // Populate simple properties
        create.setLoyaltyPoints(Integer.valueOf(1));
                
        // Populate dependencies
        Person person = PersonTestUtils.createDefaultInstance();
        create.setPerson(person);

        // Return instance
        return create;

    }
    
    public static PersonIdentity getDefaultIdentity() {
    
        PersonIdentity person = new PersonIdentity();
        person.setFirstName("s");
        person.setLastName("s");
        return person;
    
    }

    
    
    public static void populateAssignedIdentityFields(PersonIdentity person) {
    
        person.setFirstName("s");
        person.setLastName("s");
          
    }
    
    public static Customer createRandomInstance() {
    
        // create new entity
        Customer create = new Customer();
                    
        // set identity
        create.setPersonIdentity(getRandomIdentity());
        
        // populate simple properties
        create.setLoyaltyPoints(Integer.valueOf(2));
                
        // populate dependencies
        Person person = PersonTestUtils.createRandomInstance();
        create.setPerson(person);

        // return instance
        return create;

    }
    
    public static PersonIdentity getRandomIdentity() {
    
        PersonIdentity person = new PersonIdentity();
        person.setFirstName("t");
        person.setLastName("t");
        return person;
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Customer clone(Customer customer){

        Customer _customer = new Customer();
        
        if (customer.getPersonIdentity() != null) {
            PersonIdentity personIdentity = customer.getPersonIdentity();
            PersonIdentity _personIdentity = new PersonIdentity();
            _personIdentity.setFirstName(personIdentity.getFirstName());
            _personIdentity.setLastName(personIdentity.getLastName());
            _customer.setPersonIdentity(_personIdentity);
        }
        if (customer.getLoyaltyPoints() != null) {
            _customer.setLoyaltyPoints(customer.getLoyaltyPoints());
        }
        if (customer.getPerson() != null) {
            _customer.setPerson(PersonTestUtils.clone(customer.getPerson()));
        }

        return _customer;
    }
    
}