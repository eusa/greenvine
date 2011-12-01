package test.pack.data.greenvine.entity.test;


public class BugTestUtils {
    
    public static Bug createDefaultInstance() {
    
        // Create new entity
        Bug create = new Bug();
                    
        // Set identity
        create.setBugId(getDefaultIdentity());

        // Populate simple properties
        create.setDescription("s");
        create.setOpen(Boolean.TRUE);
        create.setTitle("s");
                
        // Populate dependencies
        User reporter = UserTestUtils.createDefaultInstance();
        create.setReporter(reporter);
        User owner = UserTestUtils.createDefaultInstance();
        create.setOwner(owner);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Bug createRandomInstance() {
    
        // create new entity
        Bug create = new Bug();
                    
        // set identity
        create.setBugId(getRandomIdentity());
        
        // populate simple properties
        create.setDescription("t");
        create.setOpen(Boolean.FALSE);
        create.setTitle("t");
                
        // populate dependencies
        User reporter = UserTestUtils.createRandomInstance();
        create.setReporter(reporter);
        User owner = UserTestUtils.createRandomInstance();
        create.setOwner(owner);

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
    public static Bug clone(Bug bug){

        Bug _bug = new Bug();
        
        if (bug.getBugId() != null) {
            _bug.setBugId(bug.getBugId());   
        }
        if (bug.getDescription() != null) {
            _bug.setDescription(bug.getDescription());
        }
        if (bug.getOpen() != null) {
            _bug.setOpen(bug.getOpen());
        }
        if (bug.getTitle() != null) {
            _bug.setTitle(bug.getTitle());
        }
        if (bug.getOwner() != null) {
            _bug.setOwner(UserTestUtils.clone(bug.getOwner()));
        }
        if (bug.getReporter() != null) {
            _bug.setReporter(UserTestUtils.clone(bug.getReporter()));
        }

        return _bug;
    }
    
}