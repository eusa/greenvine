package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.junit.Assert;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VehicleDaoImplIntegrationTest {
    
    @TestedObject
    private VehicleDaoImpl vehicleDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/VehicleBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/VehicleAfterCreateDataSet.xml")
    public void testCreateVehicle() throws Exception {
    
        // Create new entity
        Vehicle create = new Vehicle();
                    
        // Set identity
        create.setRegNumber("s");                
                 
        // Populate simple properties
        create.setModel("s");

        // Create in database                    
        vehicleDao.createVehicle(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/VehicleBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/VehicleAfterUpdateDataSet.xml")
    public void testUpdateVehicle() throws Exception {
        
        // Load entity and modify
        Vehicle update = vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
        update.setModel("t");

        // Update entity
        vehicleDao.updateVehicle(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/VehicleBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/VehicleAfterDeleteDataSet.xml")
    public void testRemoveVehicle() throws Exception {

        // Delete by id
        vehicleDao.removeVehicle(VehicleTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/VehicleFindAllDataSet.xml")
    public void testFindAllVehicles() throws Exception {
    
        List<Vehicle> results = vehicleDao.findAllVehicles();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/VehicleFindDataSet.xml")
    public void testLoadVehicleByIdentity() throws Exception {

        Vehicle result = vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getModel());

    }

}