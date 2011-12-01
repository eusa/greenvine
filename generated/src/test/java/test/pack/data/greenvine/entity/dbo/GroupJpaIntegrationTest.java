package test.pack.data.greenvine.entity.dbo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.dbo.Group;
import test.pack.data.greenvine.entity.dbo.GroupTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class GroupJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

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

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("GroupBeforeUpdateDataSet.xml")
    @ExpectedDataSet("GroupAfterUpdateDataSet.xml")
    public void testUpdateGroup() throws Exception {
                    
        // Load entity and modify
        Group result = (Group)entityManager.find(Group.class, GroupTestUtils.getDefaultIdentity());

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("GroupBeforeDeleteDataSet.xml")
    @ExpectedDataSet("GroupAfterDeleteDataSet.xml")
    public void testRemoveGroup() throws Exception {
                    
        // Delete
        Group result = (Group)entityManager.find(Group.class, GroupTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("GroupFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllGroup() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from dbo.Group");
                    
        // Get results 
        List<Group> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("GroupFindDataSet.xml")
    public void testFindGroupByIdentity() throws Exception {
                    
        // Get object 
        Group result  = (Group)entityManager.find(Group.class, GroupTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

    }
    
}