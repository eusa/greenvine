package test.pack.data.greenvine.entity.test;


public class VehicleTestUtils {
    
    public static Vehicle createDefaultInstance() {
    
        // Create new entity
        Vehicle create = new Vehicle();
                    
        // Set identity
        create.setRegNumber(getDefaultIdentity());

        // Populate simple properties
        create.setModel("s");

        // Return instance
        return create;

    }
    
    public static String getDefaultIdentity() {
    
        return "s";                
    
    }

    
    
    
    public static Vehicle createRandomInstance() {
    
        // create new entity
        Vehicle create = new Vehicle();
                    
        // set identity
        create.setRegNumber(getRandomIdentity());
        
        // populate simple properties
        create.setModel("t");

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
    public static Vehicle clone(Vehicle vehicle){

        Vehicle _vehicle = new Vehicle();
        
        if (vehicle.getRegNumber() != null) {
            _vehicle.setRegNumber(vehicle.getRegNumber());   
        }
        if (vehicle.getModel() != null) {
            _vehicle.setModel(vehicle.getModel());
        }

        return _vehicle;
    }
    
}