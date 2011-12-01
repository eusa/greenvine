package test.pack.data.greenvine.entity.test;


public class UserTestUtils {
    
    public static User createDefaultInstance() {
    
        // Create new entity
        User create = new User();
                    
        // Set identity
        create.setUsername(getDefaultIdentity());

        // Populate simple properties
        create.setPassword("s");

        // Return instance
        return create;

    }
    
    public static String getDefaultIdentity() {
    
        return "s";                
    
    }

    
    
    
    public static User createRandomInstance() {
    
        // create new entity
        User create = new User();
                    
        // set identity
        create.setUsername(getRandomIdentity());
        
        // populate simple properties
        create.setPassword("t");

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
    public static User clone(User user){

        User _user = new User();
        
        if (user.getUsername() != null) {
            _user.setUsername(user.getUsername());   
        }
        if (user.getPassword() != null) {
            _user.setPassword(user.getPassword());
        }
        if (user.getProfile() != null) {
            _user.setProfile(ProfileTestUtils.clone(user.getProfile()));
        }

        return _user;
    }
    
}