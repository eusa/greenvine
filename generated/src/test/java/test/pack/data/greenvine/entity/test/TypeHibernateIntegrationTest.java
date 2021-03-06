package test.pack.data.greenvine.entity.test;

import java.util.List;
import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;
import org.unitils.orm.hibernate.annotation.HibernateSessionFactory;

import test.pack.data.greenvine.entity.test.Type;
import test.pack.data.greenvine.entity.test.TypeTestUtils;

@HibernateSessionFactory("hibernate.cfg.xml")
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TypeHibernateIntegrationTest {
    
    @HibernateSessionFactory
    private SessionFactory factory;
   
    @Test
	@Ignore
    @DataSet("TypeBeforeCreateDataSet.xml")
    @ExpectedDataSet("TypeAfterCreateDataSet.xml")
    public void testCreateType() throws Exception {
    
        // Create new entity
        Type create = new Type();
                    
        // Set identity
        create.setType6(Long.valueOf(1L));
  
        // populate simple properties
        create.setType1(Integer.valueOf(1));
        create.setType10(new Date(61000L));
        create.setType12(new Date(1230768000000L));
        create.setType13(new Date(1230771661000L));
        create.setType14(new byte[]{116,101,115,116,49});
        create.setType16("s");
        create.setType17("s");
        create.setType18(new byte[]{116,101,115,116,49});
        create.setType19("s");
        create.setType2(Boolean.TRUE);
        create.setType20("s");
        create.setType3(Byte.valueOf("1"));
        create.setType4(Short.valueOf((short)1));
        create.setType5(Long.valueOf(1L));
        create.setType7(new BigDecimal("10000000.01"));
        create.setType8(Double.valueOf(10000000000000000.1));
        create.setType9(Float.valueOf(1000000.1F));
        // Get Hibernate Session
        Session session = factory.getCurrentSession();

        // create in database        
        session.persist(create); 

    }
    
    @Test
	@Ignore
    @DataSet("TypeBeforeUpdateDataSet.xml")
    @ExpectedDataSet("TypeAfterUpdateDataSet.xml")
    public void testUpdateType() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Load and modify
        Type result = (Type)session.get(Type.class, TypeTestUtils.getDefaultIdentity());
        result.setType1(Integer.valueOf(2));
        result.setType10(new Date(3722000L));
        result.setType12(new Date(1233532800000L));
        result.setType13(new Date(1233540122000L));
        result.setType14(new byte[]{116,101,115,116,50});
        result.setType16("t");
        result.setType17("t");
        result.setType18(new byte[]{116,101,115,116,50});
        result.setType19("t");
        result.setType2(Boolean.FALSE);
        result.setType20("t");
        result.setType3(Byte.valueOf("2"));
        result.setType4(Short.valueOf((short)2));
        result.setType5(Long.valueOf(2L));
        result.setType7(new BigDecimal("20000000.02"));
        result.setType8(Double.valueOf(20000000000000000.2));
        result.setType9(Float.valueOf(2000000.2F));
        // update
        session.save(result);

    }
    
    @Test
    @DataSet("TypeBeforeDeleteDataSet.xml")
    @ExpectedDataSet("TypeAfterDeleteDataSet.xml")
    public void testRemoveType() throws Exception {
        
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // delete
        Type result = (Type)session.get(Type.class, TypeTestUtils.getDefaultIdentity());
        session.delete(result);

    }
    
    @Test
    @DataSet("TypeFindAllDataSet.xml")
    @SuppressWarnings("unchecked")
    public void testFindAllTypes() throws Exception {

        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Create query
        Query query = session.createQuery("from test.pack.data.greenvine.entity.test.Type");
        
        // Get results 
        List<Type> results = query.list();
        
        Assert.assertNotNull(results);
        Assert.assertEquals(Integer.valueOf(100), Integer.valueOf(results.size()));
    }


	@Test
	@DataSet("TypeFindDataSet.xml")
    public void testFindTypeByIdentity() throws Exception {
    
        // Get Hibernate Session
        Session session = factory.getCurrentSession();
        
        // Get object from Hibernate
        Type result  = (Type)session.get(Type.class, TypeTestUtils.getDefaultIdentity());
        
        // Test properties
        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(1), result.getType1());
        Assert.assertEquals(new Date(61000L), result.getType10());
        Assert.assertEquals(new Date(1230768000000L), result.getType12());
        Assert.assertEquals(new Date(1230771661000L), result.getType13());
        Assert.assertArrayEquals(new byte[]{116,101,115,116,49}, result.getType14());
        Assert.assertEquals("s", result.getType16());
        Assert.assertEquals("s", result.getType17());
        Assert.assertArrayEquals(new byte[]{116,101,115,116,49}, result.getType18());
        Assert.assertEquals("s", result.getType19());
        Assert.assertEquals(Boolean.TRUE, result.getType2());
        Assert.assertEquals("s", result.getType20());
        Assert.assertEquals(Byte.valueOf("1"), result.getType3());
        Assert.assertEquals(Short.valueOf((short)1), result.getType4());
        Assert.assertEquals(Long.valueOf(1L), result.getType5());
        Assert.assertEquals(new BigDecimal("10000000.01"), result.getType7());
        Assert.assertEquals(Double.valueOf(10000000000000000.1), result.getType8());
        Assert.assertEquals(Float.valueOf(1000000.1F), result.getType9());

    }
    
}