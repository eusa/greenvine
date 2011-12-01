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

import test.pack.data.greenvine.entity.dbo.Umbrella;
import test.pack.data.greenvine.entity.dbo.UmbrellaTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UmbrellaJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("UmbrellaBeforeCreateDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterCreateDataSet.xml")
    public void testCreateUmbrella() throws Exception {
    
        // Create new entity
        Umbrella create = new Umbrella();
                    
        // Set identity
        create.setUmbrellaId(Integer.valueOf(1));

        // Populate simple properties
        create.setColour("s");
                
        // Populate dependencies
        Employee employee = (Employee)entityManager.getReference(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("UmbrellaBeforeUpdateDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterUpdateDataSet.xml")
    public void testUpdateUmbrella() throws Exception {
                    
        // Load entity and modify
        Umbrella result = (Umbrella)entityManager.find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setColour("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("UmbrellaBeforeDeleteDataSet.xml")
    @ExpectedDataSet("UmbrellaAfterDeleteDataSet.xml")
    public void testRemoveUmbrella() throws Exception {
                    
        // Delete
        Umbrella result = (Umbrella)entityManager.find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("UmbrellaFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllUmbrella() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Umbrella");
                    
        // Get results 
        List<Umbrella> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("UmbrellaFindDataSet.xml")
    public void testFindUmbrellaByIdentity() throws Exception {
                    
        // Get object 
        Umbrella result  = (Umbrella)entityManager.find(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getColour());

    }
    
}