package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Consignment;
import test.pack.data.greenvine.entity.test.ConsignmentTestUtils;

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
public class ConsignmentDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ConsignmentDaoImpl consignmentDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        session.returns(expected).load(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        consignmentDao.loadConsignment(ConsignmentTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        session.returns(expected).get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.findConsignment(ConsignmentTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        Consignment actual = consignmentDao.findConsignment(ConsignmentTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        consignmentDao.createConsignment(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        consignmentDao.updateConsignment(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Consignment expected = ConsignmentTestUtils.createDefaultInstance();
        session.returns(expected).load(Consignment.class, ConsignmentTestUtils.getDefaultIdentity());
        consignmentDao.removeConsignment(ConsignmentTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}