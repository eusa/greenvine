package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Profile;
import test.pack.data.greenvine.entity.test.ProfileTestUtils;

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
public class ProfileDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private ProfileDaoImpl profileDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Profile expected = ProfileTestUtils.createDefaultInstance();
        session.returns(expected).load(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Profile.class, ProfileTestUtils.getDefaultIdentity());
        profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Profile expected = ProfileTestUtils.createDefaultInstance();
        session.returns(expected).get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.findProfile(ProfileTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.findProfile(ProfileTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        profileDao.createProfile(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        profileDao.updateProfile(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        session.returns(expected).load(Profile.class, ProfileTestUtils.getDefaultIdentity());
        profileDao.removeProfile(ProfileTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}