package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;

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

import test.pack.data.greenvine.entity.test.Passport;
import test.pack.data.greenvine.entity.test.PassportTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PassportHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
    @DataSet("PassportBeforeCreateDataSet.xml")
    @ExpectedDataSet("PassportAfterCreateDataSet.xml")
    public void testCreatePassport() throws Exception {
    
        // Create new entity
        Passport create = new Passport();
                    
        // Set identity
        create.setPassportNr("s");

        // Set natural identity
  
        // populate simple properties
        create.setExpiryDate(new Date(1230768000000L));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
                
        // populate dependencies
        Person person = (Person)session.get(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("PassportBeforeUpdateDataSet.xml")
    @ExpectedDataSet("PassportAfterUpdateDataSet.xml")
    public void testUpdatePassport() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Passport result = (Passport)session.get(Passport.class, PassportTestUtils.getDefaultIdentity());
        result.setExpiryDate(new Date(1233532800000L));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("PassportBeforeDeleteDataSet.xml")
    @ExpectedDataSet("PassportAfterDeleteDataSet.xml")
    public void testRemovePassport() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Passport result = (Passport)session.get(Passport.class, PassportTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("PassportFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllPassports() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Passport");
        
        // Get results 
        List<Passport> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("PassportFindDataSet.xml")
    public void testFindPassportByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Passport result  = (Passport)session.get(Passport.class, PassportTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230768000000L), result.getExpiryDate());

    }
    
}