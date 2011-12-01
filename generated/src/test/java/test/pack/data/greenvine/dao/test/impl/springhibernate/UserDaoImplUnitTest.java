package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

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
public class UserDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private UserDaoImpl userDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        User expected = UserTestUtils.createDefaultInstance();
        session.returns(expected).load(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.loadUser(UserTestUtils.getDefaultIdentity());
        session.assertInvoked().load(User.class, UserTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(User.class, UserTestUtils.getDefaultIdentity());
        userDao.loadUser(UserTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        User expected = UserTestUtils.createDefaultInstance();
        session.returns(expected).get(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.findUser(UserTestUtils.getDefaultIdentity());
        session.assertInvoked().get(User.class, UserTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.findUser(UserTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        User expected = UserTestUtils.createDefaultInstance();
        userDao.createUser(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        User expected = UserTestUtils.createDefaultInstance();
        userDao.updateUser(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        User expected = UserTestUtils.createDefaultInstance();
        session.returns(expected).load(User.class, UserTestUtils.getDefaultIdentity());
        userDao.removeUser(UserTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}