package test.pack.data.greenvine.dao.dbo;

import java.util.List;
import test.pack.data.greenvine.entity.dbo.Desk;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface DeskDao {
	
    /**
	 * Returns the Desk with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param deskId the value of the identity
	 * @return Desk with matching identity
	 */
	public abstract Desk loadDesk(Integer deskId);
    
    /**
	 * Returns the Desk with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param deskId the value of the identity
	 * @return Desk with matching identity (or null)
	 */
	public abstract Desk findDesk(Integer deskId);
  
	/**
	 * Retrieve all Desk objects
	 * 
	 * @return List<Desk>of all Desk 
	 * objects in the database. 
	 */
	public abstract List<Desk> findAllDesks();
			
	/**
	 * Update a supplied Desk loaded in a separate transaction
	 * @param desk the Desk to update
	 */
	public abstract void updateDesk(Desk desk);
	
	/**
	 * Create a supplied Desk object
	 * @param desk the Desk to create
	 */
	public abstract void createDesk(Desk desk);
    
    /**
	 * Remove the Desk with the specified identity
	 * @param deskId the value of the identity
	 */
	public abstract void removeDesk(Integer deskId);
	

}