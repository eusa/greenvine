package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

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

import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ParkingPermitBeforeCreateDataSet.xml")
    @ExpectedDataSet("ParkingPermitAfterCreateDataSet.xml")
    public void testCreateParkingPermit() throws Exception {
    
        // Create new entity
        ParkingPermit create = new ParkingPermit();

        // Set identity
        ParkingPermitIdentity parkingPermitIdentity = new ParkingPermitIdentity();
        ParkingPermitTestUtils.populateAssignedIdentityFields(parkingPermitIdentity);
        create.setParkingPermitIdentity(parkingPermitIdentity);

        // Populate simple properties
        create.setValue(new BigDecimal("100.1"));
                
        // Populate dependencies
        Vehicle vehicle = (Vehicle)entityManager.getReference(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        create.setVehicle(vehicle);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ParkingPermitBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ParkingPermitAfterUpdateDataSet.xml")
    public void testUpdateParkingPermit() throws Exception {
                    
        // Load entity and modify
        ParkingPermit result = (ParkingPermit)entityManager.find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setValue(new BigDecimal("200.2"));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ParkingPermitBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ParkingPermitAfterDeleteDataSet.xml")
    public void testRemoveParkingPermit() throws Exception {
                    
        // Delete
        ParkingPermit result = (ParkingPermit)entityManager.find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ParkingPermitFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllParkingPermit() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.ParkingPermit");
                    
        // Get results 
        List<ParkingPermit> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ParkingPermitFindDataSet.xml")
    public void testFindParkingPermitByIdentity() throws Exception {
                    
        // Get object 
        ParkingPermit result  = (ParkingPermit)entityManager.find(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new BigDecimal("100.1"), result.getValue());

    }
    
}