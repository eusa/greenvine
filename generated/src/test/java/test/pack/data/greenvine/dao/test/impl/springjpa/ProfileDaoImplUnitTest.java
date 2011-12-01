package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Profile;
import test.pack.data.greenvine.entity.test.ProfileTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ProfileDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private ProfileDaoImpl profileDao;
    
    @Test
    public void testLoadExisting() {
    
        Profile expected = ProfileTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Profile.class, ProfileTestUtils.getDefaultIdentity());
        profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Profile expected = ProfileTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.findProfile(ProfileTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Profile.class, ProfileTestUtils.getDefaultIdentity());
        Profile actual = profileDao.findProfile(ProfileTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        profileDao.createProfile(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        profileDao.updateProfile(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Profile expected = ProfileTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Profile.class, ProfileTestUtils.getDefaultIdentity());
        profileDao.removeProfile(ProfileTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}