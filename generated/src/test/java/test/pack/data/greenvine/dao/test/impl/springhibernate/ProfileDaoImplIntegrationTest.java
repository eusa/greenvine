package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.junit.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.inject.annotation.InjectInto;
import org.unitils.inject.annotation.TestedObject;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Profile;
import test.pack.data.greenvine.entity.test.ProfileTestUtils;
import test.pack.data.greenvine.entity.test.User;
import test.pack.data.greenvine.entity.test.UserTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ProfileDaoImplIntegrationTest {
    
    @TestedObject
    private ProfileDaoImpl profileDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ProfileBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ProfileAfterCreateDataSet.xml")
    public void testCreateProfile() throws Exception {
    
        // Create new entity
        Profile create = new Profile();
                    
        // Set identity
        create.setProfileId(Integer.valueOf(1));                

        // Set natural identity
                 
        // Populate simple properties
        create.setScreenName("s");

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        User user = (User)session.load(User.class, UserTestUtils.getDefaultIdentity());
        create.setUser(user);

        // Create in database                    
        profileDao.createProfile(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ProfileBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ProfileAfterUpdateDataSet.xml")
    public void testUpdateProfile() throws Exception {
        
        // Load entity and modify
        Profile update = profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
        update.setScreenName("t");

        // Update entity
        profileDao.updateProfile(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ProfileBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/ProfileAfterDeleteDataSet.xml")
    public void testRemoveProfile() throws Exception {

        // Delete by id
        profileDao.removeProfile(ProfileTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ProfileFindAllDataSet.xml")
    public void testFindAllProfiles() throws Exception {
    
        List<Profile> results = profileDao.findAllProfiles();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/ProfileFindDataSet.xml")
    public void testLoadProfileByIdentity() throws Exception {

        Profile result = profileDao.loadProfile(ProfileTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getScreenName());

    }

}