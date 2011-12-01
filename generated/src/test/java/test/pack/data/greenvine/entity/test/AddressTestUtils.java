package test.pack.data.greenvine.entity.test;


public class AddressTestUtils {
    
    public static Address createDefaultInstance() {
    
        // Create new entity
        Address create = new Address();
                    
        // Set identity
        create.setAddressId(getDefaultIdentity());

        // Set natural identity
        create.setAddressNaturalIdentity(getDefaultNaturalIdentity());

        // Populate simple properties
        create.setPostCode("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    public static AddressNaturalIdentity getDefaultNaturalIdentity() {
        AddressNaturalIdentity addressNaturalIdentity = new AddressNaturalIdentity();
        addressNaturalIdentity.setHouseNumber("s");
        addressNaturalIdentity.setStreetName("s");
        return addressNaturalIdentity;
    }
    
    
    
    public static Address createRandomInstance() {
    
        // create new entity
        Address create = new Address();
                    
        // set identity
        create.setAddressId(getRandomIdentity());

        // Set natural identity
        create.setAddressNaturalIdentity(getRandomNaturalIdentity());
        
        // populate simple properties
        create.setPostCode("t");

        // return instance
        return create;

    }
    
    public static Integer getRandomIdentity() {
    
        return Integer.valueOf(2);                
    
    }

    public static AddressNaturalIdentity getRandomNaturalIdentity() {
        AddressNaturalIdentity addressNaturalIdentity = new AddressNaturalIdentity();
        addressNaturalIdentity.setHouseNumber("t");
        addressNaturalIdentity.setStreetName("t");
        return addressNaturalIdentity;
    }
    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Address clone(Address address){

        Address _address = new Address();
        
        if (address.getAddressId() != null) {
            _address.setAddressId(address.getAddressId());   
        }
        if (address.getAddressNaturalIdentity() != null) {
            AddressNaturalIdentity addressNaturalIdentity = address.getAddressNaturalIdentity();
            AddressNaturalIdentity _addressNaturalIdentity = new AddressNaturalIdentity();
            _addressNaturalIdentity.setHouseNumber(addressNaturalIdentity.getHouseNumber());
            _addressNaturalIdentity.setStreetName(addressNaturalIdentity.getStreetName());
            _address.setAddressNaturalIdentity(_addressNaturalIdentity);
        }
        if (address.getPostCode() != null) {
            _address.setPostCode(address.getPostCode());
        }

        return _address;
    }
    
}