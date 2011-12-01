package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Contract;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ContractDao {
	
    /**
	 * Returns the Contract with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param employee the value of the identity
	 * @return Contract with matching identity
	 */
	public abstract Contract loadContract(Integer employee);
    
    /**
	 * Returns the Contract with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param employee the value of the identity
	 * @return Contract with matching identity (or null)
	 */
	public abstract Contract findContract(Integer employee);
  
	/**
	 * Retrieve all Contract objects
	 * 
	 * @return List<Contract>of all Contract 
	 * objects in the database. 
	 */
	public abstract List<Contract> findAllContracts();
			
	/**
	 * Update a supplied Contract loaded in a separate transaction
	 * @param contract the Contract to update
	 */
	public abstract void updateContract(Contract contract);
	
	/**
	 * Create a supplied Contract object
	 * @param contract the Contract to create
	 */
	public abstract void createContract(Contract contract);
    
    /**
	 * Remove the Contract with the specified identity
	 * @param employee the value of the identity
	 */
	public abstract void removeContract(Integer employee);
	

}