package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

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

import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
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
         
  
        // populate simple properties
        create.setValue(new BigDecimal("100.1"));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Vehicle vehicle = (Vehicle)session.get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        create.setVehicle(vehicle);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("ParkingPermitBeforeUpdateDataSet.xml")
    @ExpectedDataSet("ParkingPermitAfterUpdateDataSet.xml")
    public void testUpdateParkingPermit() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        ParkingPermit result = (ParkingPermit)session.get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        result.setValue(new BigDecimal("200.2"));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("ParkingPermitBeforeDeleteDataSet.xml")
    @ExpectedDataSet("ParkingPermitAfterDeleteDataSet.xml")
    public void testRemoveParkingPermit() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        ParkingPermit result = (ParkingPermit)session.get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("ParkingPermitFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllParkingPermits() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.ParkingPermit");
        
        // Get results 
        List<ParkingPermit> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("ParkingPermitFindDataSet.xml")
    public void testFindParkingPermitByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        ParkingPermit result  = (ParkingPermit)session.get(ParkingPermit.class, ParkingPermitTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getValue());

    }
    
}