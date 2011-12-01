package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Employee;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface EmployeeDao {
	
    /**
	 * Returns the Employee with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param employeeId the value of the identity
	 * @return Employee with matching identity
	 */
	public abstract Employee loadEmployee(Integer employeeId);
    
    /**
	 * Returns the Employee with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param employeeId the value of the identity
	 * @return Employee with matching identity (or null)
	 */
	public abstract Employee findEmployee(Integer employeeId);
  
	/**
	 * Retrieve all Employee objects
	 * 
	 * @return List<Employee>of all Employee 
	 * objects in the database. 
	 */
	public abstract List<Employee> findAllEmployees();
			
	/**
	 * Update a supplied Employee loaded in a separate transaction
	 * @param employee the Employee to update
	 */
	public abstract void updateEmployee(Employee employee);
	
	/**
	 * Create a supplied Employee object
	 * @param employee the Employee to create
	 */
	public abstract void createEmployee(Employee employee);
    
    /**
	 * Remove the Employee with the specified identity
	 * @param employeeId the value of the identity
	 */
	public abstract void removeEmployee(Integer employeeId);
	

}