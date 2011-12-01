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

import test.pack.data.greenvine.entity.dbo.Desk;
import test.pack.data.greenvine.entity.dbo.DeskTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class DeskJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("DeskBeforeCreateDataSet.xml")
    @ExpectedDataSet("DeskAfterCreateDataSet.xml")
    public void testCreateDesk() throws Exception {
    
        // Create new entity
        Desk create = new Desk();
                    
        // Set identity
        create.setDeskId(Integer.valueOf(1));

        // Populate simple properties
        create.setCode("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("DeskBeforeUpdateDataSet.xml")
    @ExpectedDataSet("DeskAfterUpdateDataSet.xml")
    public void testUpdateDesk() throws Exception {
                    
        // Load entity and modify
        Desk result = (Desk)entityManager.find(Desk.class, DeskTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setCode("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("DeskBeforeDeleteDataSet.xml")
    @ExpectedDataSet("DeskAfterDeleteDataSet.xml")
    public void testRemoveDesk() throws Exception {
                    
        // Delete
        Desk result = (Desk)entityManager.find(Desk.class, DeskTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("DeskFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllDesk() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Desk");
                    
        // Get results 
        List<Desk> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("DeskFindDataSet.xml")
    public void testFindDeskByIdentity() throws Exception {
                    
        // Get object 
        Desk result  = (Desk)entityManager.find(Desk.class, DeskTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getCode());

    }
    
}