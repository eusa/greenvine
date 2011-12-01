package test.pack.data.greenvine.entity.dbo;

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

import test.pack.data.greenvine.entity.dbo.Stand;
import test.pack.data.greenvine.entity.dbo.StandTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class StandJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("StandBeforeCreateDataSet.xml")
    @ExpectedDataSet("StandAfterCreateDataSet.xml")
    public void testCreateStand() throws Exception {
    
        // Create new entity
        Stand create = new Stand();
                    
        // Set identity
        create.setStandId(Integer.valueOf(1));

        // Populate simple properties
        create.setDescription("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("StandBeforeUpdateDataSet.xml")
    @ExpectedDataSet("StandAfterUpdateDataSet.xml")
    public void testUpdateStand() throws Exception {
                    
        // Load entity and modify
        Stand result = (Stand)entityManager.find(Stand.class, StandTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setDescription("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("StandBeforeDeleteDataSet.xml")
    @ExpectedDataSet("StandAfterDeleteDataSet.xml")
    public void testRemoveStand() throws Exception {
                    
        // Delete
        Stand result = (Stand)entityManager.find(Stand.class, StandTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("StandFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllStand() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Stand");
                    
        // Get results 
        List<Stand> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("StandFindDataSet.xml")
    public void testFindStandByIdentity() throws Exception {
                    
        // Get object 
        Stand result  = (Stand)entityManager.find(Stand.class, StandTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getDescription());

    }
    
}