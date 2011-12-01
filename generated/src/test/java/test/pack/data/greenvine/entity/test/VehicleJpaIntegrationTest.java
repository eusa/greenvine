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

import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VehicleJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("VehicleBeforeCreateDataSet.xml")
    @ExpectedDataSet("VehicleAfterCreateDataSet.xml")
    public void testCreateVehicle() throws Exception {
    
        // Create new entity
        Vehicle create = new Vehicle();
                    
        // Set identity
        create.setRegNumber("s");

        // Populate simple properties
        create.setModel("s");

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("VehicleBeforeUpdateDataSet.xml")
    @ExpectedDataSet("VehicleAfterUpdateDataSet.xml")
    public void testUpdateVehicle() throws Exception {
                    
        // Load entity and modify
        Vehicle result = (Vehicle)entityManager.find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setModel("t");

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("VehicleBeforeDeleteDataSet.xml")
    @ExpectedDataSet("VehicleAfterDeleteDataSet.xml")
    public void testRemoveVehicle() throws Exception {
                    
        // Delete
        Vehicle result = (Vehicle)entityManager.find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("VehicleFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllVehicle() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Vehicle");
                    
        // Get results 
        List<Vehicle> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("VehicleFindDataSet.xml")
    public void testFindVehicleByIdentity() throws Exception {
                    
        // Get object 
        Vehicle result  = (Vehicle)entityManager.find(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals("s", result.getModel());

    }
    
}