package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;

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

import test.pack.data.greenvine.entity.test.ParkingPermitPayment;
import test.pack.data.greenvine.entity.test.ParkingPermitPaymentTestUtils;
import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitPaymentJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("ParkingPermitPaymentBeforeCreateDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterCreateDataSet.xml")
    public void testCreateParkingPermitPayment() throws Exception {
    
        // Create new entity
        ParkingPermitPayment create = new ParkingPermitPayment();

        // Populate simple properties
        create.setPaymentDate(new Date(1230771661000L));
                
        // Populate dependencies
        ParkingPermit parkingPermit = (ParkingPermit)entityManager.getReference(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        create.setParkingPermit(parkingPermit);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("ParkingPermitPaymentBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterUpdateDataSet.xml")
    public void testUpdateParkingPermitPayment() throws Exception {
                    
        // Load entity and modify
        ParkingPermitPayment result = (ParkingPermitPayment)entityManager.find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setPaymentDate(new Date(1233540122000L));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("ParkingPermitPaymentBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ParkingPermitPaymentAfterDeleteDataSet.xml")
    public void testRemoveParkingPermitPayment() throws Exception {
                    
        // Delete
        ParkingPermitPayment result = (ParkingPermitPayment)entityManager.find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("ParkingPermitPaymentFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllParkingPermitPayment() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.ParkingPermitPayment");
                    
        // Get results 
        List<ParkingPermitPayment> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("ParkingPermitPaymentFindDataSet.xml")
    public void testFindParkingPermitPaymentByIdentity() throws Exception {
                    
        // Get object 
        ParkingPermitPayment result  = (ParkingPermitPayment)entityManager.find(ParkingPermitPayment.class, ParkingPermitPaymentTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new Date(1230771661000L), result.getPaymentDate());

    }
    
}