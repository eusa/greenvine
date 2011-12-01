package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;
import test.pack.data.greenvine.entity.test.PersonIdentity;

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

import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@JpaEntityManagerFactory(persistenceUnit = "greenvine")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PersonJpaIntegrationTest {
    
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DataSet("PersonBeforeCreateDataSet.xml")
    @ExpectedDataSet("PersonAfterCreateDataSet.xml")
    public void testCreatePerson() throws Exception {
    
        // Create new entity
        Person create = new Person();

        // Set identity
        PersonIdentity personIdentity = new PersonIdentity();
        PersonTestUtils.populateAssignedIdentityFields(personIdentity);
        create.setPersonIdentity(personIdentity);

        // Populate simple properties
        create.setBirthday(new Date(1230768000000L));

        // Create in database                    
        entityManager.persist(create); 

    }
    
    @Test
    @DataSet("PersonBeforeUpdateDataSet.xml")
    @ExpectedDataSet("PersonAfterUpdateDataSet.xml")
    public void testUpdatePerson() throws Exception {
                    
        // Load entity and modify
        Person result = (Person)entityManager.find(Person.class, PersonTestUtils.getDefaultIdentity());

        // Set simple properties
        result.setBirthday(new Date(1233532800000L));

        // Update
        entityManager.merge(result);

    }
    
    @Test
    @DataSet("PersonBeforeDeleteDataSet.xml")
    @ExpectedDataSet("PersonAfterDeleteDataSet.xml")
    public void testRemovePerson() throws Exception {
                    
        // Delete
        Person result = (Person)entityManager.find(Person.class, PersonTestUtils.getDefaultIdentity());
        entityManager.remove(result);

    }
    
    @Test
    @DataSet("PersonFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllPerson() throws Exception {
                     
        // Create query
        Query query = entityManager.createQuery("from test.Person");
                    
        // Get results 
        List<Person> results = query.getResultList();
                    
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }
    
	@Test
	@DataSet("PersonFindDataSet.xml")
    public void testFindPersonByIdentity() throws Exception {
                    
        // Get object 
        Person result  = (Person)entityManager.find(Person.class, PersonTestUtils.getDefaultIdentity());
                    
        // Test result
        Assert.assertNotNull(result);        

        // Test properties        
        Assert.assertEquals(new Date(1230768000000L), result.getBirthday());

    }
    
}