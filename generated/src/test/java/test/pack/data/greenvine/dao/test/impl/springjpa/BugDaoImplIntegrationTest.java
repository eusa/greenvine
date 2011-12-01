package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.junit.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import test.pack.data.greenvine.entity.test.Bug;
import test.pack.data.greenvine.entity.test.BugTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class BugDaoImplIntegrationTest {
    
    @TestedObject
    private BugDaoImpl bugDao = null;

    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/BugBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/BugAfterCreateDataSet.xml")
    public void testCreateBug() throws Exception {
    
        // Create new entity
        Bug create = new Bug();
                    
        // Set identity
        create.setBugId(Integer.valueOf(1));
                 
        // Populate simple properties
        create.setDescription("s");
        create.setOpen(Boolean.TRUE);
        create.setTitle("s");

        // Populate dependencies
        User reporter = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setReporter(reporter);
        User owner = (User)entityManager.getReference(User.class, UserTestUtils.getDefaultIdentity());
        create.setOwner(owner);

        // Create in database                    
        bugDao.createBug(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/BugBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/BugAfterUpdateDataSet.xml")
    public void testUpdateBug() throws Exception {
        
         // Load entity and modify
        Bug update = bugDao.loadBug(BugTestUtils.getDefaultIdentity());
        update.setDescription("t");
        update.setOpen(Boolean.FALSE);
        update.setTitle("t");

        // Update entity
        bugDao.updateBug(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/BugBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/BugAfterDeleteDataSet.xml")
    public void testRemoveBug() throws Exception {

        // Delete by id
        bugDao.removeBug(BugTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/BugFindAllDataSet.xml")
    public void testFindAllBugs() throws Exception {
    
        List<Bug> results = bugDao.findAllBugs();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/BugFindDataSet.xml")
    public void testLoadBugByIdentity() throws Exception {
    
        Bug result = bugDao.loadBug(BugTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getDescription());
        Assert.assertEquals(Boolean.TRUE, result.getOpen());
        Assert.assertEquals("s", result.getTitle());

    }

}