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

import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("UserBeforeCreateDataSet.xml")
    @ExpectedDataSet("UserAfterCreateDataSet.xml")
    public void testCreateUser() throws Exception {
    
        // Create new entity
        User create = new User();
                    
        // Set identity
        create.setUsername("s");
  
        // populate simple properties
        create.setPassword("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("UserBeforeUpdateDataSet.xml")
    @ExpectedDataSet("UserAfterUpdateDataSet.xml")
    public void testUpdateUser() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        User result = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        result.setPassword("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("UserBeforeDeleteDataSet.xml")
    @ExpectedDataSet("UserAfterDeleteDataSet.xml")
    public void testRemoveUser() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        User result = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("UserFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllUsers() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.User");
        
        // Get results 
        List<User> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("UserFindDataSet.xml")
    public void testFindUserByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        User result  = (User)session.get(User.class, UserTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getPassword());

    }
    
}