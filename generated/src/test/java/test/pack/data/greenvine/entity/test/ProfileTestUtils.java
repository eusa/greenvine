package test.pack.data.greenvine.entity.test;


public class ProfileTestUtils {
    
    public static Profile createDefaultInstance() {
    
        // Create new entity
        Profile create = new Profile();
                    
        // Set identity
        create.setProfileId(getDefaultIdentity());

        // Populate simple properties
        create.setScreenName("s");
                
        // Populate dependencies
        User user = UserTestUtils.createDefaultInstance();
        create.setUser(user);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Profile createRandomInstance() {
    
        // create new entity
        Profile create = new Profile();
                    
        // set identity
        create.setProfileId(getRandomIdentity());
        
        // populate simple properties
        create.setScreenName("t");
                
        // populate dependencies
        User user = UserTestUtils.createRandomInstance();
        create.setUser(user);

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
    public static Profile clone(Profile profile){

        Profile _profile = new Profile();
        
        if (profile.getProfileId() != null) {
            _profile.setProfileId(profile.getProfileId());   
        }
        if (profile.getUser() != null) {
            _profile.setUser(UserTestUtils.clone(profile.getUser()));
        }
        if (profile.getScreenName() != null) {
            _profile.setScreenName(profile.getScreenName());
        }

        return _profile;
    }
    
}