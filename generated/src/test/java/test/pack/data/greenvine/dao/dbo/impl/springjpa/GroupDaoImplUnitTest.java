package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Group;
import test.pack.data.greenvine.entity.dbo.GroupTestUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.mock.Mock;

@RunWith(UnitilsJUnit4TestClassRunner.class)
public class GroupDaoImplUnitTest {

    @InjectInto(property="entityManager")
    private Mock<EntityManager> entityManager;
    
    @TestedObject
    private GroupDaoImpl groupDao;
    
    @Test
    public void testLoadExisting() {
    
        Group expected = GroupTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().getReference(Group.class, GroupTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        entityManager.raises(EntityNotFoundException.class).getReference(Group.class, GroupTestUtils.getDefaultIdentity());
        groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Group expected = GroupTestUtils.createDefaultInstance();
        entityManager.returns(expected).find(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.findGroup(GroupTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().find(Group.class, GroupTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        entityManager.returns(null).find(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.findGroup(GroupTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        groupDao.createGroup(expected);
        entityManager.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        groupDao.updateGroup(expected);
        entityManager.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        entityManager.returns(expected).getReference(Group.class, GroupTestUtils.getDefaultIdentity());
        groupDao.removeGroup(GroupTestUtils.getDefaultIdentity());
        entityManager.assertInvoked().remove(expected);
                
    }
   
}