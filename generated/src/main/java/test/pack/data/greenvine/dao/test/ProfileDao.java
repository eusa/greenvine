package test.pack.data.greenvine.dao.test;

import java.util.List;
import test.pack.data.greenvine.entity.test.Profile;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.DaoGenerator")
public interface ProfileDao {
	
    /**
	 * Returns the Profile with the specified identity
	 * or throws an unchecked {@link RuntimeException}  
	 * if an entity with matching identity not found
	 *	 
	 * @param profileId the value of the identity
	 * @return Profile with matching identity
	 */
	public abstract Profile loadProfile(Integer profileId);
    
    /**
	 * Returns the Profile with the specified identity
	 * or null if an entity with matching identity not found
     *
	 * @param profileId the value of the identity
	 * @return Profile with matching identity (or null)
	 */
	public abstract Profile findProfile(Integer profileId);
  
	/**
	 * Retrieve all Profile objects
	 * 
	 * @return List<Profile>of all Profile 
	 * objects in the database. 
	 */
	public abstract List<Profile> findAllProfiles();
			
	/**
	 * Update a supplied Profile loaded in a separate transaction
	 * @param profile the Profile to update
	 */
	public abstract void updateProfile(Profile profile);
	
	/**
	 * Create a supplied Profile object
	 * @param profile the Profile to create
	 */
	public abstract void createProfile(Profile profile);
    
    /**
	 * Remove the Profile with the specified identity
	 * @param profileId the value of the identity
	 */
	public abstract void removeProfile(Integer profileId);
	

}