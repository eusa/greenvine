package test.pack.data.greenvine.dao.test.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

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
public class PersonDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private PersonDaoImpl personDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Person expected = PersonTestUtils.createDefaultInstance();
        session.returns(expected).load(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Person.class, PersonTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Person.class, PersonTestUtils.getDefaultIdentity());
        personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Person expected = PersonTestUtils.createDefaultInstance();
        session.returns(expected).get(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.findPerson(PersonTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Person.class, PersonTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.findPerson(PersonTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        personDao.createPerson(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        personDao.updatePerson(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        session.returns(expected).load(Person.class, PersonTestUtils.getDefaultIdentity());
        personDao.removePerson(PersonTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}