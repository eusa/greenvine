package test.pack.data.greenvine.entity.test;

import java.math.BigDecimal;

public class ActivityTestUtils {
    
    public static Activity createDefaultInstance() {
    
        // Create new entity
        Activity create = new Activity();
                    
        // Set identity
        create.setActivityId(getDefaultIdentity());

        // Populate simple properties
        create.setDescription("s");
        create.setHours(new BigDecimal("100.1"));
                
        // Populate dependencies
        Timesheet timesheet = TimesheetTestUtils.createDefaultInstance();
        create.setTimesheet(timesheet);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Activity createRandomInstance() {
    
        // create new entity
        Activity create = new Activity();
                    
        // set identity
        create.setActivityId(getRandomIdentity());
        
        // populate simple properties
        create.setDescription("t");
        create.setHours(new BigDecimal("200.2"));
                
        // populate dependencies
        Timesheet timesheet = TimesheetTestUtils.createRandomInstance();
        create.setTimesheet(timesheet);

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
    public static Activity clone(Activity activity){

        Activity _activity = new Activity();
        
        if (activity.getActivityId() != null) {
            _activity.setActivityId(activity.getActivityId());   
        }
        if (activity.getDescription() != null) {
            _activity.setDescription(activity.getDescription());
        }
        if (activity.getHours() != null) {
            _activity.setHours(new BigDecimal(activity.getHours().toPlainString()));
        }
        if (activity.getTimesheet() != null) {
            _activity.setTimesheet(TimesheetTestUtils.clone(activity.getTimesheet()));
        }

        return _activity;
    }
    
}