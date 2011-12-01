package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Desk;
import test.pack.data.greenvine.entity.dbo.DeskTestUtils;

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
public class DeskDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private DeskDaoImpl deskDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Desk expected = DeskTestUtils.createDefaultInstance();
        session.returns(expected).load(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Desk.class, DeskTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Desk.class, DeskTestUtils.getDefaultIdentity());
        deskDao.loadDesk(DeskTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Desk expected = DeskTestUtils.createDefaultInstance();
        session.returns(expected).get(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.findDesk(DeskTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Desk.class, DeskTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Desk.class, DeskTestUtils.getDefaultIdentity());
        Desk actual = deskDao.findDesk(DeskTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        deskDao.createDesk(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        deskDao.updateDesk(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Desk expected = DeskTestUtils.createDefaultInstance();
        session.returns(expected).load(Desk.class, DeskTestUtils.getDefaultIdentity());
        deskDao.removeDesk(DeskTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}