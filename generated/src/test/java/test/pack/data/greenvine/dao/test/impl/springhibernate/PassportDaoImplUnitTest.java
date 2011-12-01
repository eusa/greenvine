package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Passport;
import test.pack.data.greenvine.entity.test.PassportTestUtils;

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
public class PassportDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private PassportDaoImpl passportDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Passport expected = PassportTestUtils.createDefaultInstance();
        session.returns(expected).load(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Passport.class, PassportTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Passport.class, PassportTestUtils.getDefaultIdentity());
        passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Passport expected = PassportTestUtils.createDefaultInstance();
        session.returns(expected).get(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.findPassport(PassportTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Passport.class, PassportTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.findPassport(PassportTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        passportDao.createPassport(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        passportDao.updatePassport(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        session.returns(expected).load(Passport.class, PassportTestUtils.getDefaultIdentity());
        passportDao.removePassport(PassportTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}