package test.pack.data.greenvine.entity.dbo;


public class ContractTestUtils {
    
    public static Contract createDefaultInstance() {
    
        // Create new entity
        Contract create = new Contract();
                    
        // Set identity
        create.setEmployeeId(getDefaultIdentity());

        // Populate simple properties
        create.setTerms("s");
                
        // Populate dependencies
        Employee employee = EmployeeTestUtils.createDefaultInstance();
        create.setEmployee(employee);

        // Return instance
        return create;

    }
    
    public static Integer getDefaultIdentity() {
    
        return Integer.valueOf(1);                
    
    }

    
    
    
    public static Contract createRandomInstance() {
    
        // create new entity
        Contract create = new Contract();
                    
        // set identity
        create.setEmployeeId(getRandomIdentity());
        
        // populate simple properties
        create.setTerms("t");
                
        // populate dependencies
        Employee employee = EmployeeTestUtils.createRandomInstance();
        create.setEmployee(employee);

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
    public static Contract clone(Contract contract){

        Contract _contract = new Contract();
        
        if (contract.getEmployeeId() != null) {
            _contract.setEmployeeId(contract.getEmployeeId());   
        }
        if (contract.getTerms() != null) {
            _contract.setTerms(contract.getTerms());
        }
        if (contract.getEmployee() != null) {
            _contract.setEmployee(EmployeeTestUtils.clone(contract.getEmployee()));
        }

        return _contract;
    }
    
}