package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import junit.framework.Assert;
import test.pack.data.greenvine.entity.dbo.Group;
import test.pack.data.greenvine.entity.dbo.GroupTestUtils;

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
public class GroupDaoImplUnitTest {

    private Mock<Session> session;
    
    @InjectInto(property="sessionFactory")
    private Mock<SessionFactory> sessionFactory;
    
    @TestedObject
    private GroupDaoImpl groupDao;
    
    @Before
    public void setUp() {
        
        // Prime the SessionFactor to return mock Session
        session.returns(FlushMode.COMMIT).getFlushMode();
        sessionFactory.returns(session.getMock()).openSession();
        
    }
    
    @Test
    public void testLoadExisting() {
    
        Group expected = GroupTestUtils.createDefaultInstance();
        session.returns(expected).load(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());
        session.assertInvoked().load(Group.class, GroupTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    
    @Test (expected=RuntimeException.class)
    public void testLoadNonExisting() {

        session.raises(HibernateObjectRetrievalFailureException.class).load(Group.class, GroupTestUtils.getDefaultIdentity());
        groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());
    
    }
    
    @Test
    public void testFindExisting() {
    
        Group expected = GroupTestUtils.createDefaultInstance();
        session.returns(expected).get(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.findGroup(GroupTestUtils.getDefaultIdentity());
        session.assertInvoked().get(Group.class, GroupTestUtils.getDefaultIdentity());
        Assert.assertEquals(expected, actual);
    
    }
    
    @Test
    public void testFindNonExisting() {

        session.returns(null).get(Group.class, GroupTestUtils.getDefaultIdentity());
        Group actual = groupDao.findGroup(GroupTestUtils.getDefaultIdentity());
        Assert.assertNull(actual);
    
    }
    
    @Test
    public void testCreate() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        groupDao.createGroup(expected);
        session.assertInvoked().persist(expected);
        
    }
    
    @Test
    public void testUpdate() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        groupDao.updateGroup(expected);
        session.assertInvoked().merge(expected);
                
    }
    
    @Test
    public void testDelete() {
        
        Group expected = GroupTestUtils.createDefaultInstance();
        session.returns(expected).load(Group.class, GroupTestUtils.getDefaultIdentity());
        groupDao.removeGroup(GroupTestUtils.getDefaultIdentity());
        session.assertInvoked().delete(expected);
                
    }
   
}