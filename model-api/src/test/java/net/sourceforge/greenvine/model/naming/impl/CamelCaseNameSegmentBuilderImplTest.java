package net.sourceforge.greenvine.model.naming.impl;

import junit.framework.Assert;
import net.sourceforge.greenvine.model.api.ModelException;

import org.junit.Test;

public class CamelCaseNameSegmentBuilderImplTest {
    
    @Test
    public void testAppendCamelCase() throws ModelException {
        CamelCaseNameSegmentBuilderImpl test = new CamelCaseNameSegmentBuilderImpl("test");
        test.appendCamelCase("test");
        Assert.assertEquals("testTest", test.toString());
        
    }
    
    @Test
    public void testPluralise() throws ModelException {
        
        // Check double s ending
        CamelCaseNameSegmentBuilderImpl dress = new CamelCaseNameSegmentBuilderImpl("dress");
        dress.pluralise();
        Assert.assertEquals("dresses", dress.toString());
        
        // Check "x" ending
        CamelCaseNameSegmentBuilderImpl box = new CamelCaseNameSegmentBuilderImpl("box");
        box.pluralise();
        Assert.assertEquals("boxes", box.toString());
        
        // Check "y" ending
        CamelCaseNameSegmentBuilderImpl activity = new CamelCaseNameSegmentBuilderImpl("activity");
        activity.pluralise();
        Assert.assertEquals("activities", activity.toString());
        
        // Check "ch" ending
        CamelCaseNameSegmentBuilderImpl church = new CamelCaseNameSegmentBuilderImpl("church");
        church.pluralise();
        Assert.assertEquals("churches", church.toString());
        
        // Check "sh" ending
        CamelCaseNameSegmentBuilderImpl brush = new CamelCaseNameSegmentBuilderImpl("brush");
        brush.pluralise();
        Assert.assertEquals("brushes", brush.toString());
    }
    
    @Test
    public void testDepluralise() throws ModelException {
        
        // Check double s ending
        CamelCaseNameSegmentBuilderImpl dress = new CamelCaseNameSegmentBuilderImpl("dress");
        dress.depluralise();
        Assert.assertEquals("dress", dress.toString());
        
        // Check "sses" ending
        CamelCaseNameSegmentBuilderImpl dresses = new CamelCaseNameSegmentBuilderImpl("dresses");
        dresses.depluralise();
        Assert.assertEquals("dress", dresses.toString());
        
        // Check "xes" ending
        CamelCaseNameSegmentBuilderImpl foxes = new CamelCaseNameSegmentBuilderImpl("foxes");
        foxes.depluralise();
        Assert.assertEquals("fox", foxes.toString());
        
        // Check "ies" ending
        CamelCaseNameSegmentBuilderImpl activities = new CamelCaseNameSegmentBuilderImpl("activities");
        activities.depluralise();
        Assert.assertEquals("activity", activities.toString());
        
        // Check "s" ending
        CamelCaseNameSegmentBuilderImpl bats = new CamelCaseNameSegmentBuilderImpl("bats");
        bats.depluralise();
        Assert.assertEquals("bat", bats.toString());
        
        // Check "ses" ending
        CamelCaseNameSegmentBuilderImpl gases = new CamelCaseNameSegmentBuilderImpl("gases");
        gases.depluralise();
        Assert.assertEquals("gas", gases.toString());
        
        // Check non-plural "s" ending
        CamelCaseNameSegmentBuilderImpl gas = new CamelCaseNameSegmentBuilderImpl("gas");
        gas.depluralise();
        Assert.assertEquals("gas", gas.toString());
        
        // Check non-plural "s" ending
        CamelCaseNameSegmentBuilderImpl gaseous = new CamelCaseNameSegmentBuilderImpl("gaseous");
        gaseous.depluralise();
        Assert.assertEquals("gaseous", gaseous.toString());
        
        // Check non-plural "s" ending
        CamelCaseNameSegmentBuilderImpl vicious = new CamelCaseNameSegmentBuilderImpl("vicious");
        vicious.depluralise();
        Assert.assertEquals("vicious", vicious.toString());
        
        // Check "ches" ending
        CamelCaseNameSegmentBuilderImpl churches = new CamelCaseNameSegmentBuilderImpl("churches");
        churches.depluralise();
        Assert.assertEquals("church", churches.toString());
        
        // Check "shes" ending
        CamelCaseNameSegmentBuilderImpl rushes = new CamelCaseNameSegmentBuilderImpl("rushes");
        rushes.depluralise();
        Assert.assertEquals("rush", rushes.toString());
        
        // Check "ees" ending
        CamelCaseNameSegmentBuilderImpl bees = new CamelCaseNameSegmentBuilderImpl("bees");
        bees.depluralise();
        Assert.assertEquals("bee", bees.toString());
        
        // Check "ypes" ending
        CamelCaseNameSegmentBuilderImpl types = new CamelCaseNameSegmentBuilderImpl("types");
        types.depluralise();
        Assert.assertEquals("type", types.toString());
    }
    
    

}
