package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PersonDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private PersonDaoImpl personDao;
    
    @Test
    public void testLoadExisting() {
    
        Person expected = PersonTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Person expected = PersonTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.findPerson(PersonTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Person.class, PersonTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Person.class, PersonTestUtils.getDefaultIdentity());
        Person actual = personDao.findPerson(PersonTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        personDao.createPerson(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        personDao.updatePerson(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Person expected = PersonTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        personDao.removePerson(PersonTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}