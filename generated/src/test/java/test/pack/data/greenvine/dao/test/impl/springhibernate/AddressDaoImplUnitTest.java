package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Address;
import test.pack.data.greenvine.entity.test.AddressTestUtils;

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
public class AddressDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private AddressDaoImpl addressDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Address expected = AddressTestUtils.createDefaultInstance();
        session.returns(expected).load(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Address.class, AddressTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Address.class, AddressTestUtils.getDefaultIdentity());
        addressDao.loadAddress(AddressTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Address expected = AddressTestUtils.createDefaultInstance();
        session.returns(expected).get(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.findAddress(AddressTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Address.class, AddressTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Address.class, AddressTestUtils.getDefaultIdentity());
        Address actual = addressDao.findAddress(AddressTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        addressDao.createAddress(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        addressDao.updateAddress(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Address expected = AddressTestUtils.createDefaultInstance();
        session.returns(expected).load(Address.class, AddressTestUtils.getDefaultIdentity());
        addressDao.removeAddress(AddressTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}