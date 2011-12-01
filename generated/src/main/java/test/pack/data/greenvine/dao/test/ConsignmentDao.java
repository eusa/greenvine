package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Consignment;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ConsignmentDao {
	
    /**
	 * Returns the Consignment with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param consignmentId the value of the identity
	 * @return Consignment with matching identity
	 */
	public abstract Consignment loadConsignment(Integer consignmentId);
    
    /**
	 * Returns the Consignment with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param consignmentId the value of the identity
	 * @return Consignment with matching identity (or null)
	 */
	public abstract Consignment findConsignment(Integer consignmentId);
  
	/**
	 * Retrieve all Consignment objects
	 * 
	 * @return List<Consignment>of all Consignment 
	 * objects in the database. 
	 */
	public abstract List<Consignment> findAllConsignments();
			
	/**
	 * Update a supplied Consignment loaded in a separate transaction
	 * @param consignment the Consignment to update
	 */
	public abstract void updateConsignment(Consignment consignment);
	
	/**
	 * Create a supplied Consignment object
	 * @param consignment the Consignment to create
	 */
	public abstract void createConsignment(Consignment consignment);
    
    /**
	 * Remove the Consignment with the specified identity
	 * @param consignmentId the value of the identity
	 */
	public abstract void removeConsignment(Integer consignmentId);
	

}