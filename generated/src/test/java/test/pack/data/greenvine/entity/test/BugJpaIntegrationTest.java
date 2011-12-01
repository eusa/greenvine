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

import test.pack.data.greenvine.entity.test.Bug;
import test.pack.data.greenvine.entity.test.BugTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BugJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("BugBeforeCreateDataSet.xml")
    @ExpectedDataSet("BugAfterCreateDataSet.xml")
    public void testCreateBug() throws Exception {
    
        // Create new entity
        Bug create = new Bug();
                    
        // Set identity
        create.setBugId(Integer.valueOf(1));

        // Populate simple properties
        create.setDescription("s");
        create.setOpen(Boolean.TRUE);
        create.setTitle("s");
                
        // Populate dependencies
        User reporter = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setReporter(reporter);
        User owner = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setOwner(owner);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("BugBeforeUpdateDataSet.xml")
    @ExpectedDataSet("BugAfterUpdateDataSet.xml")
    public void testUpdateBug() throws Exception {
                    
        // Load entity and modify
        Bug result = (Bug)entityManager.find(Bug.class, BugTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setDescription("t");
        result.setOpen(Boolean.FALSE);
        result.setTitle("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("BugBeforeDeleteDataSet.xml")
    @ExpectedDataSet("BugAfterDeleteDataSet.xml")
    public void testRemoveBug() throws Exception {
                    
        // Delete
        Bug result = (Bug)entityManager.find(Bug.class, BugTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("BugFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllBug() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Bug");
                    
        // Get results 
        List<Bug> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("BugFindDataSet.xml")
    public void testFindBugByIdentity() throws Exception {
                    
        // Get object 
        Bug result  = (Bug)entityManager.find(Bug.class, BugTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(Boolean.TRUE, result.getOpen());
        Assert.assertEquals("s", result.getTitle());

    }
    
}