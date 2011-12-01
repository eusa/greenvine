package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private UserDaoImpl userDao;
    
    @Test
    public void testLoadExisting() {
    
        User expected = UserTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.loadUser(UserTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(User.class, UserTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(User.class, UserTestUtils.getDefaultIdentity());
        userDao.loadUser(UserTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        User expected = UserTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.findUser(UserTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(User.class, UserTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(User.class, UserTestUtils.getDefaultIdentity());
        User actual = userDao.findUser(UserTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        User expected = UserTestUtils.createDefaultInstance();
        userDao.createUser(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        User expected = UserTestUtils.createDefaultInstance();
        userDao.updateUser(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        User expected = UserTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(User.class, UserTestUtils.getDefaultIdentity());
        userDao.removeUser(UserTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}