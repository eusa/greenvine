package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.util.Date;
import test.pack.data.greenvine.entity.test.PersonIdentity;

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

import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PersonHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
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
         
  
        // populate simple properties
        create.setBirthday(new Date(1230768000000L));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
    @DataSet("PersonBeforeUpdateDataSet.xml")
    @ExpectedDataSet("PersonAfterUpdateDataSet.xml")
    public void testUpdatePerson() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Person result = (Person)session.get(Person.class, PersonTestUtils.getDefaultIdentity());
        result.setBirthday(new Date(1233532800000L));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("PersonBeforeDeleteDataSet.xml")
    @ExpectedDataSet("PersonAfterDeleteDataSet.xml")
    public void testRemovePerson() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Person result = (Person)session.get(Person.class, PersonTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("PersonFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllPersons() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Person");
        
        // Get results 
        List<Person> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("PersonFindDataSet.xml")
    public void testFindPersonByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Person result  = (Person)session.get(Person.class, PersonTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230768000000L), result.getBirthday());

    }
    
}