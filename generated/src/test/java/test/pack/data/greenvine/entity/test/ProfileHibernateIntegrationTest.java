package test.pack.data.greenvine.entity.test;

import java.util.List;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Profile;
import test.pack.data.greenvine.entity.test.ProfileTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ProfileHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("ProfileBeforeCreateDataSet.xml")
    @ExpectedDataSet("ProfileAfterCreateDataSet.xml")
    public void testCreateProfile() throws Exception {
    
        // Create new entity
        Profile create = new Profile();
                    
        // Set identity
        create.setProfileId(Integer.valueOf(1));

        // Set natural identity
  
        // populate simple properties
        create.setScreenName("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        User user = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ProfileBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ProfileAfterUpdateDataSet.xml")
    public void testUpdateProfile() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Profile result = (Profile)session.get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        result.setScreenName("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ProfileBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ProfileAfterDeleteDataSet.xml")
    public void testRemoveProfile() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Profile result = (Profile)session.get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ProfileFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllProfiles() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Profile");
        
        // Get results 
        List<Profile> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ProfileFindDataSet.xml")
    public void testFindProfileByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Profile result  = (Profile)session.get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getScreenName());

    }
    
}