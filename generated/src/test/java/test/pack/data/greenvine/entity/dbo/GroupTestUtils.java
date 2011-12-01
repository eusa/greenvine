package test.pack.data.greenvine.entity.dbo;


public class GroupTestUtils {
    
    public static Group createDefaultInstance() {
    
        // Create new entity
        Group create = new Group();
                    
        // Set identity
        create.setGroupId(getDefaultIdentity());
        
        // Set natural identity
        create.setGroupname(getDefaultNaturalIdentity());

        // Populate simple properties

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    public static String getDefaultNaturalIdentity() {
        return "s"; 
    }               
    
    
    
    public static Group createRandomInstance() {
    
        // create new entity
        Group create = new Group();
                    
        // set identity
        create.setGroupId(getRandomIdentity());
        
        // Set natural identity
        create.setGroupname(getRandomNaturalIdentity());
        
        // populate simple properties

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
    public static Group clone(Group group){

        Group _group = new Group();
        
        if (group.getGroupId() != null) {
            _group.setGroupId(group.getGroupId());   
        }
        if (group.getGroupname() != null) {
            _group.setGroupname(group.getGroupname());   
        }

        return _group;
    }
    
}