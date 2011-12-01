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

import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("UserBeforeCreateDataSet.xml")
    @ExpectedDataSet("UserAfterCreateDataSet.xml")
    public void testCreateUser() throws Exception {
    
        // Create new entity
        User create = new User();
                    
        // Set identity
        create.setUsername("s");

        // Populate simple properties
        create.setPassword("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("UserBeforeUpdateDataSet.xml")
    @ExpectedDataSet("UserAfterUpdateDataSet.xml")
    public void testUpdateUser() throws Exception {
                    
        // Load entity and modify
        User result = (User)entityManager.find(User.class, UserTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setPassword("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("UserBeforeDeleteDataSet.xml")
    @ExpectedDataSet("UserAfterDeleteDataSet.xml")
    public void testRemoveUser() throws Exception {
                    
        // Delete
        User result = (User)entityManager.find(User.class, UserTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("UserFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllUser() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.User");
                    
        // Get results 
        List<User> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("UserFindDataSet.xml")
    public void testFindUserByIdentity() throws Exception {
                    
        // Get object 
        User result  = (User)entityManager.find(User.class, UserTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getPassword());

    }
    
}