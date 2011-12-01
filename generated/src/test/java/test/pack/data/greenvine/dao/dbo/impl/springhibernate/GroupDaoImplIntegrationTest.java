package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.junit.Assert;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.dbo.Group;
import test.pack.data.greenvine.entity.dbo.GroupTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class GroupDaoImplIntegrationTest {
    
    @TestedObject
    private GroupDaoImpl groupDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/GroupBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/GroupAfterCreateDataSet.xml")
    public void testCreateGroup() throws Exception {
    
        // Create new entity
        Group create = new Group();
                    
        // Set identity
        create.setGroupId(Integer.valueOf(1));                

        // Set natural identity
        create.setGroupname("s");

        // Create in database                    
        groupDao.createGroup(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/GroupBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/GroupAfterUpdateDataSet.xml")
    public void testUpdateGroup() throws Exception {
        
        // Load entity and modify
        Group update = groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());

        // Update entity
        groupDao.updateGroup(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/GroupBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/GroupAfterDeleteDataSet.xml")
    public void testRemoveGroup() throws Exception {

        // Delete by id
        groupDao.removeGroup(GroupTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/GroupFindAllDataSet.xml")
    public void testFindAllGroups() throws Exception {
    
        List<Group> results = groupDao.findAllGroups();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/GroupFindDataSet.xml")
    public void testLoadGroupByIdentity() throws Exception {

        Group result = groupDao.loadGroup(GroupTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);

    }

}