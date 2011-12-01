package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Type;
import test.pack.data.greenvine.entity.test.TypeTestUtils;

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
public class TypeDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private TypeDaoImpl typeDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Type expected = TypeTestUtils.createDefaultInstance();
        session.returns(expected).load(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.loadType(TypeTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Type.class, TypeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Type.class, TypeTestUtils.getDefaultIdentity());
        typeDao.loadType(TypeTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Type expected = TypeTestUtils.createDefaultInstance();
        session.returns(expected).get(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.findType(TypeTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Type.class, TypeTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Type.class, TypeTestUtils.getDefaultIdentity());
        Type actual = typeDao.findType(TypeTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        typeDao.createType(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        typeDao.updateType(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Type expected = TypeTestUtils.createDefaultInstance();
        session.returns(expected).load(Type.class, TypeTestUtils.getDefaultIdentity());
        typeDao.removeType(TypeTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}