package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.ParkingPermitIdentity;

import org.junit.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.ParkingPermit;
import test.pack.data.greenvine.entity.test.ParkingPermitTestUtils;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ParkingPermitDaoImplIntegrationTest {
    
    @TestedObject
    private ParkingPermitDaoImpl parkingPermitDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterCreateDataSet.xml")
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
        Session session = sessionFactory.getCurrentSession();
        
        Vehicle vehicle = (Vehicle)session.load(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        create.setVehicle(vehicle);

        // Create in database                    
        parkingPermitDao.createParkingPermit(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterUpdateDataSet.xml")
    public void testUpdateParkingPermit() throws Exception {
        
        // Load entity and modify
        ParkingPermit update = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        update.setValue(new BigDecimal("200.2"));

        // Update entity
        parkingPermitDao.updateParkingPermit(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ParkingPermitAfterDeleteDataSet.xml")
    public void testRemoveParkingPermit() throws Exception {

        // Delete by id
        parkingPermitDao.removeParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitFindAllDataSet.xml")
    public void testFindAllParkingPermits() throws Exception {
    
        List<ParkingPermit> results = parkingPermitDao.findAllParkingPermits();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ParkingPermitFindDataSet.xml")
    public void testLoadParkingPermitByIdentity() throws Exception {

        ParkingPermit result = parkingPermitDao.loadParkingPermit(ParkingPermitTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new BigDecimal("100.1"), result.getValue());

    }

}