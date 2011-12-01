package test.pack.data.greenvine.entity.dbo;

import java.math.BigDecimal;

public class EmployeeTestUtils {
    
    public static Employee createDefaultInstance() {
    
        // Create new entity
        Employee create = new Employee();
                    
        // Set identity
        create.setEmployeeId(getDefaultIdentity());
        
        // Set natural identity
        create.setEmail(getDefaultNaturalIdentity());

        // Populate simple properties
        create.setDailyWorkingHours(new BigDecimal("100.1"));
        create.setFirstName("s");
        create.setHourlyCost(new BigDecimal("100000000000000.0001"));
        create.setLastName("s");
                
        // Populate dependencies
        User user = UserTestUtils.createDefaultInstance();
        create.setUser(user);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    public static String getDefaultNaturalIdentity() {
        return "s"; 
    }               
    
    
    
    public static Employee createRandomInstance() {
    
        // create new entity
        Employee create = new Employee();
                    
        // set identity
        create.setEmployeeId(getRandomIdentity());
        
        // Set natural identity
        create.setEmail(getRandomNaturalIdentity());
        
        // populate simple properties
        create.setDailyWorkingHours(new BigDecimal("200.2"));
        create.setFirstName("t");
        create.setHourlyCost(new BigDecimal("200000000000000.0002"));
        create.setLastName("t");
                
        // populate dependencies
        User user = UserTestUtils.createRandomInstance();
        create.setUser(user);

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
    public static Employee clone(Employee employee){

        Employee _employee = new Employee();
        
        if (employee.getEmployeeId() != null) {
            _employee.setEmployeeId(employee.getEmployeeId());   
        }
        if (employee.getEmail() != null) {
            _employee.setEmail(employee.getEmail());   
        }
        if (employee.getDailyWorkingHours() != null) {
            _employee.setDailyWorkingHours(new BigDecimal(employee.getDailyWorkingHours().toPlainString()));
        }
        if (employee.getFirstName() != null) {
            _employee.setFirstName(employee.getFirstName());
        }
        if (employee.getHourlyCost() != null) {
            _employee.setHourlyCost(new BigDecimal(employee.getHourlyCost().toPlainString()));
        }
        if (employee.getLastName() != null) {
            _employee.setLastName(employee.getLastName());
        }
        if (employee.getUser() != null) {
            _employee.setUser(UserTestUtils.clone(employee.getUser()));
        }
        if (employee.getContract() != null) {
            _employee.setContract(ContractTestUtils.clone(employee.getContract()));
        }
        if (employee.getManager() != null) {
            _employee.setManager(EmployeeTestUtils.clone(employee.getManager()));
        }

        return _employee;
    }
    
}