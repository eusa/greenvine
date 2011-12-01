package test.pack.data.greenvine.entity.dbo;

import java.util.List;

import org.junit.Assert;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Group;
import test.pack.data.greenvine.entity.dbo.GroupTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class GroupHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("GroupBeforeCreateDataSet.xml")
    @ExpectedDataSet("GroupAfterCreateDataSet.xml")
    public void testCreateGroup() throws Exception {
    
        // Create new entity
        Group create = new Group();
                    
        // Set identity
        create.setGroupId(Integer.valueOf(1));

        // Set natural identity
        create.setGroupname("s");
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("GroupBeforeUpdateDataSet.xml")
    @ExpectedDataSet("GroupAfterUpdateDataSet.xml")
    public void testUpdateGroup() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Group result = (Group)session.get(Group.class, GroupTestUtils.getDefaultIdentity());
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("GroupBeforeDeleteDataSet.xml")
    @ExpectedDataSet("GroupAfterDeleteDataSet.xml")
    public void testRemoveGroup() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Group result = (Group)session.get(Group.class, GroupTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("GroupFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllGroups() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.dbo.Group");
        
        // Get results 
        List<Group> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("GroupFindDataSet.xml")
    public void testFindGroupByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Group result  = (Group)session.get(Group.class, GroupTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);

    }
    
}