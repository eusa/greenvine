package test.pack.data.greenvine.entity.test;

import java.util.Date;

public class ConsignmentTestUtils {
    
    public static Consignment createDefaultInstance() {
    
        // Create new entity
        Consignment create = new Consignment();
                    
        // Set identity
        create.setConsignmentId(getDefaultIdentity());

        // Set natural identity
        create.setConsignmentNaturalIdentity(getDefaultNaturalIdentity());

        // Populate simple properties
                
        // Populate dependencies
        Address address = AddressTestUtils.createDefaultInstance();
        create.setAddress(address);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    public static ConsignmentNaturalIdentity getDefaultNaturalIdentity() {
        ConsignmentNaturalIdentity consignmentNaturalIdentity = new ConsignmentNaturalIdentity();
        consignmentNaturalIdentity.setConsignmentDate(new Date(1230768000000L));
        Customer customer = CustomerTestUtils.createDefaultInstance();
        consignmentNaturalIdentity.setCustomer(customer);
        return consignmentNaturalIdentity;
    }
    
    
    
    public static Consignment createRandomInstance() {
    
        // create new entity
        Consignment create = new Consignment();
                    
        // set identity
        create.setConsignmentId(getRandomIdentity());

        // Set natural identity
        create.setConsignmentNaturalIdentity(getRandomNaturalIdentity());
        
        // populate simple properties
                
        // populate dependencies
        Address address = AddressTestUtils.createRandomInstance();
        create.setAddress(address);

        // return instance
        return create;

    }
    
    public static Integer getRandomIdentity() {
    
        return Integer.valueOf(2);                
    
    }

    public static ConsignmentNaturalIdentity getRandomNaturalIdentity() {
        ConsignmentNaturalIdentity consignmentNaturalIdentity = new ConsignmentNaturalIdentity();
        consignmentNaturalIdentity.setConsignmentDate(new Date(1233532800000L));
        Customer customer = CustomerTestUtils.createRandomInstance();
        consignmentNaturalIdentity.setCustomer(customer);
        return consignmentNaturalIdentity;
    }
    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Consignment clone(Consignment consignment){

        Consignment _consignment = new Consignment();
        
        if (consignment.getConsignmentId() != null) {
            _consignment.setConsignmentId(consignment.getConsignmentId());   
        }
        if (consignment.getConsignmentNaturalIdentity() != null) {
            ConsignmentNaturalIdentity consignmentNaturalIdentity = consignment.getConsignmentNaturalIdentity();
            ConsignmentNaturalIdentity _consignmentNaturalIdentity = new ConsignmentNaturalIdentity();
            _consignmentNaturalIdentity.setConsignmentDate(new Date(consignmentNaturalIdentity.getConsignmentDate().getTime()));
        if (consignmentNaturalIdentity.getCustomer() != null) {
            _consignmentNaturalIdentity.setCustomer(CustomerTestUtils.clone(consignmentNaturalIdentity.getCustomer()));
        }
            _consignment.setConsignmentNaturalIdentity(_consignmentNaturalIdentity);
        }
        if (consignment.getAddress() != null) {
            _consignment.setAddress(AddressTestUtils.clone(consignment.getAddress()));
        }

        return _consignment;
    }
    
}