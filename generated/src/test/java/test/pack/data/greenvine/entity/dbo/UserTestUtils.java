package test.pack.data.greenvine.entity.dbo;


public class UserTestUtils {
    
    public static User createDefaultInstance() {
    
        // Create new entity
        User create = new User();
                    
        // Set identity
        create.setUserId(getDefaultIdentity());
        
        // Set natural identity
        create.setUsername(getDefaultNaturalIdentity());

        // Populate simple properties
        create.setPassword("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    public static String getDefaultNaturalIdentity() {
        return "s"; 
    }               
    
    
    
    public static User createRandomInstance() {
    
        // create new entity
        User create = new User();
                    
        // set identity
        create.setUserId(getRandomIdentity());
        
        // Set natural identity
        create.setUsername(getRandomNaturalIdentity());
        
        // populate simple properties
        create.setPassword("t");

        // return instance
        return create;

    }
    
    public static Integer getRandomIdentity() {
    
        return Integer.valueOf(2);                
    
    }

    public static String getRandomNaturalIdentity() {
        return "t"; 
    }               
    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static User clone(User user){

        User _user = new User();
        
        if (user.getUserId() != null) {
            _user.setUserId(user.getUserId());   
        }
        if (user.getUsername() != null) {
            _user.setUsername(user.getUsername());   
        }
        if (user.getPassword() != null) {
            _user.setPassword(user.getPassword());
        }
        if (user.getEmployee() != null) {
            _user.setEmployee(EmployeeTestUtils.clone(user.getEmployee()));
        }

        return _user;
    }
    
}