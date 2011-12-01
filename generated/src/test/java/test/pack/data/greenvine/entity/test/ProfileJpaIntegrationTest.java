package test.pack.data.greenvine.entity.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Profile;
import test.pack.data.greenvine.entity.test.ProfileTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ProfileJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ProfileBeforeCreateDataSet.xml")
    @ExpectedDataSet("ProfileAfterCreateDataSet.xml")
    public void testCreateProfile() throws Exception {
    
        // Create new entity
        Profile create = new Profile();
                    
        // Set identity
        create.setProfileId(Integer.valueOf(1));

        // Set natural identity

        // Populate simple properties
        create.setScreenName("s");
                
        // Populate dependencies
        User user = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ProfileBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ProfileAfterUpdateDataSet.xml")
    public void testUpdateProfile() throws Exception {
                    
        // Load entity and modify
        Profile result = (Profile)entityManager.find(Profile.class, ProfileTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setScreenName("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ProfileBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ProfileAfterDeleteDataSet.xml")
    public void testRemoveProfile() throws Exception {
                    
        // Delete
        Profile result = (Profile)entityManager.find(Profile.class, ProfileTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ProfileFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllProfile() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Profile");
                    
        // Get results 
        List<Profile> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ProfileFindDataSet.xml")
    public void testFindProfileByIdentity() throws Exception {
                    
        // Get object 
        Profile result  = (Profile)entityManager.find(Profile.class, ProfileTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getScreenName());

    }
    
}