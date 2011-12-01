package net.sourceforge.greenvine.model.reveng;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.PropertyType;
import net.sourceforge.greenvine.model.api.impl.CatalogImpl;
import net.sourceforge.greenvine.model.api.impl.ModelImpl;
import net.sourceforge.greenvine.reveng.impl.RevengConfig;
import net.sourceforge.greenvine.reveng.impl.ReverseEngineerImpl;
import net.sourceforge.greenvine.reveng.testutils.ModelAssert;
import net.sourceforge.greenvine.reveng.testutils.TestDatabaseExtractor;

import org.junit.Test;

public class ReverseEngineerImplIntegrationTest {
    
       
    @Test
    public void testReverseEngineer() throws Exception {
        
        Database dbs = TestDatabaseExtractor.extractTestDatase("../database-extractor/src/test/resources/test-schema-h2.sql");
        
        // Set up RevengConfig
        RevengConfig revengConfig = new RevengConfig();
        revengConfig.setModelName("model");
        
        // Create ReverseEngineer
        ReverseEngineerImpl reveng = new ReverseEngineerImpl(revengConfig);
        
        // Reverse engineer
        ModelImpl model = reveng.reverseEngineer(dbs);
        
        // Validate model
        Assert.assertNotNull(model);
        Assert.assertNotNull(model.getCatalogs());
        Assert.assertEquals(1, model.getCatalogCount());
        
        // Validate catalog
        CatalogImpl catalog = model.getCatalog(0);
        Assert.assertEquals("greenvine", catalog.getName().toString());
        Assert.assertEquals(21, catalog.getEntityCount());
        
        // Create ModelAssert object to test entities
        ModelAssert mAssert = new ModelAssert(catalog);
        
        // 1. Validate contract
        mAssert.assertEntityExists("dbo.contract");
        mAssert.assertOneToOneIdentity("dbo.contract", "employee", "dbo.employee");
        mAssert.assertSimpleProperty("dbo.contract", "terms", PropertyType.STRING);
        
        // 2. Validate desk
        mAssert.assertEntityExists("dbo.desk");
        mAssert.assertSimpleIdentity("dbo.desk", "deskId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.desk", "code", PropertyType.STRING);
        mAssert.assertOneToOneAssociation("dbo.desk", "employee", "dbo.employee");
        
        // 3. Validate employee
        mAssert.assertEntityExists("dbo.employee");
        mAssert.assertSimpleIdentity("dbo.employee", "employeeId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.employee", "email", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "firstName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "lastName", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.employee", "hourlyCost", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("dbo.employee", "dailyWorkingHours", PropertyType.BIG_DECIMAL);
        mAssert.assertOneToOneAssociation("dbo.employee", "desk", "dbo.desk");
        mAssert.assertOneToOneParent("dbo.employee", "contract", "dbo.contract");
        mAssert.assertOneToOneChild("dbo.employee", "user", "dbo.user");
        mAssert.assertManyToOne("dbo.employee", "manager", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "employees", "dbo.employee");
        mAssert.assertOneToMany("dbo.employee", "umbrellas", "dbo.umbrella");
        mAssert.assertManyToOneAssociation("dbo.employee", "employeeMentor", "dbo.employee"); 
        mAssert.assertOneToManyAssociation("dbo.employee", "employeeMentorees", "dbo.employee"); 
        
        // 4. Validate group
        mAssert.assertEntityExists("dbo.group");
        mAssert.assertSimpleIdentity("dbo.group", "groupId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.group", "groupname", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.group", "users", "dbo.user");
        
        // 5. Validate stand
        mAssert.assertEntityExists("dbo.stand");
        mAssert.assertSimpleIdentity("dbo.stand", "standId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.stand", "description", PropertyType.STRING);
        mAssert.assertOneToManyAssociation("dbo.stand", "umbrellas", "dbo.umbrella");
        
        // 6. Validate umbrella
        mAssert.assertEntityExists("dbo.umbrella");
        mAssert.assertSimpleIdentity("dbo.umbrella", "umbrellaId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("dbo.umbrella", "colour", PropertyType.STRING);
        mAssert.assertManyToOneAssociation("dbo.umbrella", "stand", "dbo.stand");
        
        // 7. Validate user
        mAssert.assertEntityExists("dbo.user");
        mAssert.assertSimpleIdentity("dbo.user", "userId", PropertyType.INTEGER);
        mAssert.assertSimpleNaturalIdentity("dbo.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("dbo.user", "password", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("dbo.user", "groups", "dbo.group");
        
        // 8. Validate activity
        mAssert.assertEntityExists("test.activity");
        mAssert.assertSimpleIdentity("test.activity", "activityId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("test.activity", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.activity", "hours", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("test.activity", "timesheet", "test.timesheet");
        
        // 9. Validate address
        mAssert.assertEntityExists("test.address");
        mAssert.assertSimpleIdentity("test.address", "addressId", PropertyType.INTEGER);
        mAssert.assertComponentNaturalIdentity("test.address", "houseNumber", "streetName");
        mAssert.assertSimpleProperty("test.address", "postCode", PropertyType.STRING);
        mAssert.assertOneToMany("test.address", "consignments", "test.consignment");
        
        // 10. Validate consignment
        mAssert.assertEntityExists("test.consignment");
        mAssert.assertSimpleIdentity("test.consignment", "consignmentId", PropertyType.INTEGER);
        mAssert.assertComponentNaturalIdentity("test.consignment", "customer", "consignmentDate");
        mAssert.assertManyToOne("test.consignment", "address", "test.address");
        
        // 11. Validate customer
        mAssert.assertEntityExists("test.customer");
        mAssert.assertOneToOneIdentity("test.customer", "person", "test.person");
        mAssert.assertSimpleProperty("test.customer", "loyaltyPoints", PropertyType.INTEGER);
        mAssert.assertOneToMany("test.customer", "consignments", "test.consignment");
        
        // 12. Validate parkingPermit
        mAssert.assertEntityExists("test.parkingPermit");
        mAssert.assertComponentIdentity("test.parkingPermit", "regNumber", "date"); 
        mAssert.assertSimpleProperty("test.parkingPermit", "value", PropertyType.BIG_DECIMAL);
        mAssert.assertManyToOne("test.parkingPermit", "vehicle", "test.vehicle");
        mAssert.assertOneToOneParent("test.parkingPermit", "parkingPermitPayment", "test.parkingPermitPayment");

        // 13. Validate parkingPermitPayment
        mAssert.assertEntityExists("test.parkingPermitPayment");
        mAssert.assertOneToOneIdentity("test.parkingPermitPayment", "parkingPermit", "test.parkingPermit");
        mAssert.assertSimpleProperty("test.parkingPermitPayment", "paymentDate", PropertyType.TIMESTAMP);

        // 14. Validate passport
        mAssert.assertEntityExists("test.passport");
        mAssert.assertSimpleIdentity("test.passport", "passportNr", PropertyType.STRING);
        mAssert.assertOneToOneNaturalIdentity("test.passport", "person", "test.person");
        mAssert.assertSimpleProperty("test.passport", "expiryDate", PropertyType.DATE);
        
        // 15. Validate person
        mAssert.assertEntityExists("test.person");
        mAssert.assertComponentIdentity("test.person", "firstName", "lastName");
        mAssert.assertOneToOneParent("test.person", "customer", "test.customer");
        mAssert.assertOneToOneParent("test.person", "passport", "test.passport");
        mAssert.assertSimpleProperty("test.person", "birthday", PropertyType.DATE);

        // 16. Validate profile
        mAssert.assertEntityExists("test.profile");
        mAssert.assertSimpleIdentity("test.profile", "profileId", PropertyType.INTEGER);
        mAssert.assertOneToOneNaturalIdentity("test.profile", "user", "test.user");
        mAssert.assertSimpleProperty("test.profile", "screenName", PropertyType.STRING);
        mAssert.assertManyToManyAssociation("test.profile", "friendRequesters", "test.profile");
        mAssert.assertManyToManyAssociation("test.profile", "friendRequestees", "test.profile");
        mAssert.assertOneToOneAssociation("test.profile", "spouseTo", "test.profile");
        mAssert.assertOneToOneAssociation("test.profile", "spouseFrom", "test.profile");
        
        // 17. Validate timesheet
        mAssert.assertEntityExists("test.timesheet");
        mAssert.assertComponentIdentity("test.timesheet", "employeeId", "date");
        mAssert.assertOneToMany("test.timesheet", "activities", "test.activity");
        mAssert.assertSimpleProperty("test.timesheet", "expectedHours", PropertyType.BIG_DECIMAL);
     
        // 18. Validate types
        mAssert.assertEntityExists("test.type");
        mAssert.assertSimpleIdentity("test.type", "type6", PropertyType.LONG);
        mAssert.assertSimpleProperty("test.type", "type1", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("test.type", "type2", PropertyType.BOOLEAN);
        mAssert.assertSimpleProperty("test.type", "type3", PropertyType.BYTE);
        mAssert.assertSimpleProperty("test.type", "type4", PropertyType.SHORT);
        mAssert.assertSimpleProperty("test.type", "type5", PropertyType.LONG);
        mAssert.assertSimpleProperty("test.type", "type7", PropertyType.BIG_DECIMAL);
        mAssert.assertSimpleProperty("test.type", "type8", PropertyType.DOUBLE);
        mAssert.assertSimpleProperty("test.type", "type9", PropertyType.FLOAT);
        mAssert.assertSimpleProperty("test.type", "type10", PropertyType.TIME);
        //mAssert.assertSimpleProperty("test.type", "type11", PropertyType.LONG);
        mAssert.assertSimpleProperty("test.type", "type12", PropertyType.DATE);
        mAssert.assertSimpleProperty("test.type", "type13", PropertyType.TIMESTAMP);
        mAssert.assertSimpleProperty("test.type", "type14", PropertyType.BINARY);
        //mAssert.assertSimpleProperty("test.type", "type15", PropertyType.LONG);
        mAssert.assertSimpleProperty("test.type", "type16", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.type", "type17", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.type", "type18", PropertyType.BLOB);
        mAssert.assertSimpleProperty("test.type", "type19", PropertyType.TEXT);
        mAssert.assertSimpleProperty("test.type", "type20", PropertyType.STRING);

        // 19. Validate user
        mAssert.assertEntityExists("test.user");
        mAssert.assertSimpleIdentity("test.user", "username", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.user", "password", PropertyType.STRING);
        mAssert.assertOneToOneParent("test.user", "profile", "test.profile");
        mAssert.assertOneToMany("test.user", "ownerBugs", "test.bug"); 
        mAssert.assertOneToMany("test.user", "reporterBugs", "test.bug");
    
        // 20. Validate vehicle
        mAssert.assertEntityExists("test.vehicle");
        mAssert.assertSimpleIdentity("test.vehicle", "regNumber", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.vehicle", "model", PropertyType.STRING);
        mAssert.assertOneToMany("test.vehicle", "parkingPermits", "test.parkingPermit");
    
        // 21. Validate bugs
        mAssert.assertEntityExists("test.bug");
        mAssert.assertSimpleIdentity("test.bug", "bugId", PropertyType.INTEGER);
        mAssert.assertSimpleProperty("test.bug", "title", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.bug", "description", PropertyType.STRING);
        mAssert.assertSimpleProperty("test.bug", "open", PropertyType.BOOLEAN);
        mAssert.assertManyToOne("test.bug", "owner", "test.user"); 
        mAssert.assertManyToOne("test.bug", "reporter", "test.user");
    }
    
}
