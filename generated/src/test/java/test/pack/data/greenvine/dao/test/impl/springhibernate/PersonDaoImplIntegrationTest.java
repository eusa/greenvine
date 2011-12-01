package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;
import java.util.Date;
import test.pack.data.greenvine.entity.test.PersonIdentity;

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

import test.pack.data.greenvine.entity.test.Person;
import test.pack.data.greenvine.entity.test.PersonTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class PersonDaoImplIntegrationTest {
    
    @TestedObject
    private PersonDaoImpl personDao = null;

    @SuppressWarnings("unused")
    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PersonBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PersonAfterCreateDataSet.xml")
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
        personDao.createPerson(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PersonBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PersonAfterUpdateDataSet.xml")
    public void testUpdatePerson() throws Exception {
        
        // Load entity and modify
        Person update = personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
        update.setBirthday(new Date(1233532800000L));

        // Update entity
        personDao.updatePerson(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PersonBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/test/PersonAfterDeleteDataSet.xml")
    public void testRemovePerson() throws Exception {

        // Delete by id
        personDao.removePerson(PersonTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PersonFindAllDataSet.xml")
    public void testFindAllPersons() throws Exception {
    
        List<Person> results = personDao.findAllPersons();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/test/PersonFindDataSet.xml")
    public void testLoadPersonByIdentity() throws Exception {

        Person result = personDao.loadPerson(PersonTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals(new Date(1230768000000L), result.getBirthday());

    }

}