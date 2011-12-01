package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Customer;
import test.pack.data.greenvine.entity.test.CustomerTestUtils;

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
public class CustomerDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private CustomerDaoImpl customerDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Customer expected = CustomerTestUtils.createDefaultInstance();
        session.returns(expected).load(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Customer.class, CustomerTestUtils.getDefaultIdentity());
        customerDao.loadCustomer(CustomerTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Customer expected = CustomerTestUtils.createDefaultInstance();
        session.returns(expected).get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.findCustomer(CustomerTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Customer.class, CustomerTestUtils.getDefaultIdentity());
        Customer actual = customerDao.findCustomer(CustomerTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        customerDao.createCustomer(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        customerDao.updateCustomer(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Customer expected = CustomerTestUtils.createDefaultInstance();
        session.returns(expected).load(Customer.class, CustomerTestUtils.getDefaultIdentity());
        customerDao.removeCustomer(CustomerTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}