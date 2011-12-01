package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Umbrella;
import test.pack.data.greenvine.entity.dbo.UmbrellaTestUtils;

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
public class UmbrellaDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private UmbrellaDaoImpl umbrellaDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        session.returns(expected).load(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        session.returns(expected).get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.findUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        Umbrella actual = umbrellaDao.findUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        umbrellaDao.createUmbrella(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        umbrellaDao.updateUmbrella(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Umbrella expected = UmbrellaTestUtils.createDefaultInstance();
        session.returns(expected).load(Umbrella.class, UmbrellaTestUtils.getDefaultIdentity());
        umbrellaDao.removeUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}