package test.pack.data.greenvine.entity.dbo;


public class DeskTestUtils {
    
    public static Desk createDefaultInstance() {
    
        // Create new entity
        Desk create = new Desk();
                    
        // Set identity
        create.setDeskId(getDefaultIdentity());

        // Populate simple properties
        create.setCode("s");

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Desk createRandomInstance() {
    
        // create new entity
        Desk create = new Desk();
                    
        // set identity
        create.setDeskId(getRandomIdentity());
        
        // populate simple properties
        create.setCode("t");

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
    public static Desk clone(Desk desk){

        Desk _desk = new Desk();
        
        if (desk.getDeskId() != null) {
            _desk.setDeskId(desk.getDeskId());   
        }
        if (desk.getCode() != null) {
            _desk.setCode(desk.getCode());
        }

        return _desk;
    }
    
}