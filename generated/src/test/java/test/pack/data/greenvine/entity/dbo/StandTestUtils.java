package test.pack.data.greenvine.entity.dbo;


public class StandTestUtils {
    
    public static Stand createDefaultInstance() {
    
        // Create new entity
        Stand create = new Stand();
                    
        // Set identity
        create.setStandId(getDefaultIdentity());

        // Populate simple properties
        create.setDescription("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Stand createRandomInstance() {
    
        // create new entity
        Stand create = new Stand();
                    
        // set identity
        create.setStandId(getRandomIdentity());
        
        // populate simple properties
        create.setDescription("t");

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
    public static Stand clone(Stand stand){

        Stand _stand = new Stand();
        
        if (stand.getStandId() != null) {
            _stand.setStandId(stand.getStandId());   
        }
        if (stand.getDescription() != null) {
            _stand.setDescription(stand.getDescription());
        }

        return _stand;
    }
    
}