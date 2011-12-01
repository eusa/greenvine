package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;

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

import test.pack.data.greenvine.entity.test.Passport;
import test.pack.data.greenvine.entity.test.PassportTestUtils;
import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PassportJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("PassportBeforeCreateDataSet.xml")
    @ExpectedDataSet("PassportAfterCreateDataSet.xml")
    public void testCreatePassport() throws Exception {
    
        // Create new entity
        Passport create = new Passport();
                    
        // Set identity
        create.setPassportNr("s");

        // Set natural identity

        // Populate simple properties
        create.setExpiryDate(new Date(1230768000000L));
                
        // Populate dependencies
        Person person = (Person)entityManager.getReference(Person.class, PersonTestUtils.getDefaultIdentity());
        create.setPerson(person);

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("PassportBeforeUpdateDataSet.xml")
    @ExpectedDataSet("PassportAfterUpdateDataSet.xml")
    public void testUpdatePassport() throws Exception {
                    
        // Load entity and modify
        Passport result = (Passport)entityManager.find(Passport.class, PassportTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setExpiryDate(new Date(1233532800000L));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("PassportBeforeDeleteDataSet.xml")
    @ExpectedDataSet("PassportAfterDeleteDataSet.xml")
    public void testRemovePassport() throws Exception {
                    
        // Delete
        Passport result = (Passport)entityManager.find(Passport.class, PassportTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("PassportFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllPassport() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Passport");
                    
        // Get results 
        List<Passport> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("PassportFindDataSet.xml")
    public void testFindPassportByIdentity() throws Exception {
                    
        // Get object 
        Passport result  = (Passport)entityManager.find(Passport.class, PassportTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new Date(1230768000000L), result.getExpiryDate());

    }
    
}