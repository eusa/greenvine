package test.pack.data.greenvine.entity.test;

import java.math.BigDecimal;
import java.util.Date;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

public class TimesheetTestUtils {
    
    public static Timesheet createDefaultInstance() {
    
        // Create new entity
        Timesheet create = new Timesheet();
                    
        // Set identity
        create.setTimesheetIdentity(getDefaultIdentity());

        // Populate simple properties
        create.setExpectedHours(new BigDecimal("100.1"));
                
        // Populate dependencies
        Employee employee = EmployeeTestUtils.createDefaultInstance();
        create.setEmployee(employee);

        // Return instance
        return create;

    }
    
    public static TimesheetIdentity getDefaultIdentity() {
    
        TimesheetIdentity timesheetIdentity = new TimesheetIdentity();
        timesheetIdentity.setDate(new Date(1230768000000L));
        timesheetIdentity.setEmployeeId(Integer.valueOf(1));
        return timesheetIdentity;
    
    }

    
    
    public static void populateAssignedIdentityFields(TimesheetIdentity timesheetIdentity) {
    
        timesheetIdentity.setDate(new Date(1230768000000L));
        timesheetIdentity.setEmployeeId(Integer.valueOf(1));
          
    }
    
    public static Timesheet createRandomInstance() {
    
        // create new entity
        Timesheet create = new Timesheet();
                    
        // set identity
        create.setTimesheetIdentity(getRandomIdentity());
        
        // populate simple properties
        create.setExpectedHours(new BigDecimal("200.2"));
                
        // populate dependencies
        Employee employee = EmployeeTestUtils.createRandomInstance();
        create.setEmployee(employee);

        // return instance
        return create;

    }
    
    public static TimesheetIdentity getRandomIdentity() {
    
        TimesheetIdentity timesheetIdentity = new TimesheetIdentity();
        timesheetIdentity.setDate(new Date(1233532800000L));
        timesheetIdentity.setEmployeeId(Integer.valueOf(2));
        return timesheetIdentity;
    
    }

    /**
    * Creates a deep copy
    * of the object including 
    * all dependent fields. 
    */
    public static Timesheet clone(Timesheet timesheet){

        Timesheet _timesheet = new Timesheet();
        
        if (timesheet.getTimesheetIdentity() != null) {
            TimesheetIdentity timesheetIdentity = timesheet.getTimesheetIdentity();
            TimesheetIdentity _timesheetIdentity = new TimesheetIdentity();
            _timesheetIdentity.setDate(new Date(timesheetIdentity.getDate().getTime()));
            _timesheetIdentity.setEmployeeId(timesheetIdentity.getEmployeeId());
            _timesheet.setTimesheetIdentity(_timesheetIdentity);
        }
        if (timesheet.getExpectedHours() != null) {
            _timesheet.setExpectedHours(new BigDecimal(timesheet.getExpectedHours().toPlainString()));
        }
        if (timesheet.getEmployee() != null) {
            _timesheet.setEmployee(EmployeeTestUtils.clone(timesheet.getEmployee()));
        }

        return _timesheet;
    }
    
}