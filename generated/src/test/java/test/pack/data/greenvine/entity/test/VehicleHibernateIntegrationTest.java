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

import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VehicleHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("VehicleBeforeCreateDataSet.xml")
    @ExpectedDataSet("VehicleAfterCreateDataSet.xml")
    public void testCreateVehicle() throws Exception {
    
        // Create new entity
        Vehicle create = new Vehicle();
                    
        // Set identity
        create.setRegNumber("s");
  
        // populate simple properties
        create.setModel("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("VehicleBeforeUpdateDataSet.xml")
    @ExpectedDataSet("VehicleAfterUpdateDataSet.xml")
    public void testUpdateVehicle() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Vehicle result = (Vehicle)session.get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        result.setModel("t");
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("VehicleBeforeDeleteDataSet.xml")
    @ExpectedDataSet("VehicleAfterDeleteDataSet.xml")
    public void testRemoveVehicle() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Vehicle result = (Vehicle)session.get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("VehicleFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllVehicles() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Vehicle");
        
        // Get results 
        List<Vehicle> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("VehicleFindDataSet.xml")
    public void testFindVehicleByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Vehicle result  = (Vehicle)session.get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getModel());

    }
    
}