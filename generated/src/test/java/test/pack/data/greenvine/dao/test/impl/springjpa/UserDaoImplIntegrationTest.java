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

import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UserDaoImplIntegrationTest {
    
    @TestedObject
    private UserDaoImpl userDao = null;

    @SuppressWarnings("unused")
    @PersistenceContext
    @InjectInto(property="entityManager")
    private EntityManager entityManager;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/UserBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/UserAfterCreateDataSet.xml")
    public void testCreateUser() throws Exception {
    
        // Create new entity
        User create = new User();
                    
        // Set identity
        create.setUsername("s");
                 
        // Populate simple properties
        create.setPassword("s");

        // Create in database                    
        userDao.createUser(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/UserBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/UserAfterUpdateDataSet.xml")
    public void testUpdateUser() throws Exception {
        
         // Load entity and modify
        User update = userDao.loadUser(UserTestUtils.getDefaultIdentity());
        update.setPassword("t");

        // Update entity
        userDao.updateUser(update);

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/UserBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/UserAfterDeleteDataSet.xml")
    public void testRemoveUser() throws Exception {

        // Delete by id
        userDao.removeUser(UserTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/UserFindAllDataSet.xml")
    public void testFindAllUsers() throws Exception {
    
        List<User> results = userDao.findAllUsers();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/UserFindDataSet.xml")
    public void testLoadUserByIdentity() throws Exception {
    
        User result = userDao.loadUser(UserTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getPassword());

    }

}