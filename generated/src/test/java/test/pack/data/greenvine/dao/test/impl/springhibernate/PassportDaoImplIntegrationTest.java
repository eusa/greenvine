package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.util.Date;

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

import test.pack.data.greenvine.entity.test.Passport;
import test.pack.data.greenvine.entity.test.PassportTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PassportDaoImplIntegrationTest {
    
    @TestedObject
    private PassportDaoImpl passportDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PassportBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PassportAfterCreateDataSet.xml")
    public void testCreatePassport() throws Exception {
    
        // Create new entity
        Passport create = new Passport();
                    
        // Set identity
        create.setPassportNr("s");                

        // Set natural identity
                 
        // Populate simple properties
        create.setExpiryDate(new Date(1230768000000L));

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        Person person = (Person)session.load(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // Create in database                    
        passportDao.createPassport(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PassportBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PassportAfterUpdateDataSet.xml")
    public void testUpdatePassport() throws Exception {
        
        // Load entity and modify
        Passport update = passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
        update.setExpiryDate(new Date(1233532800000L));

        // Update entity
        passportDao.updatePassport(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PassportBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PassportAfterDeleteDataSet.xml")
    public void testRemovePassport() throws Exception {

        // Delete by id
        passportDao.removePassport(PassportTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PassportFindAllDataSet.xml")
    public void testFindAllPassports() throws Exception {
    
        List<Passport> results = passportDao.findAllPassports();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PassportFindDataSet.xml")
    public void testLoadPassportByIdentity() throws Exception {

        Passport result = passportDao.loadPassport(PassportTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230768000000L), result.getExpiryDate());

    }

}