package test.pack.data.greenvine.entity.dbo;


public class UmbrellaTestUtils {
    
    public static Umbrella createDefaultInstance() {
    
        // Create new entity
        Umbrella create = new Umbrella();
                    
        // Set identity
        create.setUmbrellaId(getDefaultIdentity());

        // Populate simple properties
        create.setColour("s");
                
        // Populate dependencies
        Employee employee = EmployeeTestUtils.createDefaultInstance();
        create.setEmployee(employee);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Umbrella createRandomInstance() {
    
        // create new entity
        Umbrella create = new Umbrella();
                    
        // set identity
        create.setUmbrellaId(getRandomIdentity());
        
        // populate simple properties
        create.setColour("t");
                
        // populate dependencies
        Employee employee = EmployeeTestUtils.createRandomInstance();
        create.setEmployee(employee);

        // return instance
        return create;

    }
    
    public static Integer getRandomIdentity() {
    
        return Integer.valueOf(2);                
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Umbrella clone(Umbrella umbrella){

        Umbrella _umbrella = new Umbrella();
        
        if (umbrella.getUmbrellaId() != null) {
            _umbrella.setUmbrellaId(umbrella.getUmbrellaId());   
        }
        if (umbrella.getColour() != null) {
            _umbrella.setColour(umbrella.getColour());
        }
        if (umbrella.getEmployee() != null) {
            _umbrella.setEmployee(EmployeeTestUtils.clone(umbrella.getEmployee()));
        }

        return _umbrella;
    }
    
}