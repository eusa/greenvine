package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Vehicle;
import test.pack.data.greenvine.entity.test.VehicleTestUtils;

import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VehicleDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private VehicleDaoImpl vehicleDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        session.returns(expected).load(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        vehicleDao.loadVehicle(VehicleTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        session.returns(expected).get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.findVehicle(VehicleTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        Vehicle actual = vehicleDao.findVehicle(VehicleTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        vehicleDao.createVehicle(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        vehicleDao.updateVehicle(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Vehicle expected = VehicleTestUtils.createDefaultInstance();
        session.returns(expected).load(Vehicle.class, VehicleTestUtils.getDefaultIdentity());
        vehicleDao.removeVehicle(VehicleTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}