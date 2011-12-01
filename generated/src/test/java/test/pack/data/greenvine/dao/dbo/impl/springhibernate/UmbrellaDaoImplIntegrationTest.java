package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

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

import test.pack.data.greenvine.entity.dbo.Umbrella;
import test.pack.data.greenvine.entity.dbo.UmbrellaTestUtils;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.dbo.EmployeeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class UmbrellaDaoImplIntegrationTest {
    
    @TestedObject
    private UmbrellaDaoImpl umbrellaDao = null;

    @HibernateSessionFactory
    @InjectInto(property = "sessionFactory")
    private SessionFactory sessionFactory;
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaBeforeCreateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaAfterCreateDataSet.xml")
    public void testCreateUmbrella() throws Exception {
    
        // Create new entity
        Umbrella create = new Umbrella();
                    
        // Set identity
        create.setUmbrellaId(Integer.valueOf(1));                
                 
        // Populate simple properties
        create.setColour("s");

        // Populate dependencies
        Session session = sessionFactory.getCurrentSession();
        
        Employee employee = (Employee)session.load(Employee.class, EmployeeTestUtils.getDefaultIdentity());
        create.setEmployee(employee);

        // Create in database                    
        umbrellaDao.createUmbrella(create);
    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaBeforeUpdateDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaAfterUpdateDataSet.xml")
    public void testUpdateUmbrella() throws Exception {
        
        // Load entity and modify
        Umbrella update = umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        update.setColour("t");

        // Update entity
        umbrellaDao.updateUmbrella(update);
                    
    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaBeforeDeleteDataSet.xml")
    @ExpectedDataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaAfterDeleteDataSet.xml")
    public void testRemoveUmbrella() throws Exception {

        // Delete by id
        umbrellaDao.removeUmbrella(UmbrellaTestUtils.getDefaultIdentity());

    }
    
    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaFindAllDataSet.xml")
    public void testFindAllUmbrellas() throws Exception {
    
        List<Umbrella> results = umbrellaDao.findAllUmbrellas();
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
        
    }


    @Test
    @DataSet("/test/pack/data/greenvine/entity/dbo/UmbrellaFindDataSet.xml")
    public void testLoadUmbrellaByIdentity() throws Exception {

        Umbrella result = umbrellaDao.loadUmbrella(UmbrellaTestUtils.getDefaultIdentity());
        Assert.assertNotNull(result);
        Assert.assertEquals("s", result.getColour());

    }

}