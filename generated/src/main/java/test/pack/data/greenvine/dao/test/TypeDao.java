package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Type;
import java.lang.Long;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface TypeDao {
	
    /**
	 * Returns the Type with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param type6 the value of the identity
	 * @return Type with matching identity
	 */
	public abstract Type loadType(Long type6);
    
    /**
	 * Returns the Type with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param type6 the value of the identity
	 * @return Type with matching identity (or null)
	 */
	public abstract Type findType(Long type6);
  
	/**
	 * Retrieve all Type objects
	 * 
	 * @return List<Type>of all Type 
	 * objects in the database. 
	 */
	public abstract List<Type> findAllTypes();
			
	/**
	 * Update a supplied Type loaded in a separate transaction
	 * @param type the Type to update
	 */
	public abstract void updateType(Type type);
	
	/**
	 * Create a supplied Type object
	 * @param type the Type to create
	 */
	public abstract void createType(Type type);
    
    /**
	 * Remove the Type with the specified identity
	 * @param type6 the value of the identity
	 */
	public abstract void removeType(Long type6);
	

}