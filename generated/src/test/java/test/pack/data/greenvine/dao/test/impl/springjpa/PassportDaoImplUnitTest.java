package test.pack.data.greenvine.dao.test.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.test.Passport;
import test.pack.data.greenvine.entity.test.PassportTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PassportDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private PassportDaoImpl passportDao;
    
    @Test
    public void testLoadExisting() {
    
        Passport expected = PassportTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Passport.class, PassportTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Passport.class, PassportTestUtils.getDefaultIdentity());
        passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Passport expected = PassportTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.findPassport(PassportTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Passport.class, PassportTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Passport.class, PassportTestUtils.getDefaultIdentity());
        Passport actual = passportDao.findPassport(PassportTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        passportDao.createPassport(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        passportDao.updatePassport(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Passport expected = PassportTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Passport.class, PassportTestUtils.getDefaultIdentity());
        passportDao.removePassport(PassportTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}