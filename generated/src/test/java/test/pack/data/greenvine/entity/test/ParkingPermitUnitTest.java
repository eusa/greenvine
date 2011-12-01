package test.pack.data.greenvine.entity.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ParkingPermitUnitTest {
    
    private ParkingPermit defaultInstance;
    private ParkingPermit randomInstance;
    private ParkingPermit clone;
    private ParkingPermit unitialized;
    
    @Before
    public void doBefore() {
    
        this.defaultInstance = ParkingPermitTestUtils.createDefaultInstance();
        this.randomInstance = ParkingPermitTestUtils.createRandomInstance();
        this.clone = ParkingPermitTestUtils.clone(defaultInstance);
        this.unitialized = new ParkingPermit();
        
    }
    
    @Test
    public void testEquals() {
        
        // Test equals 
        Assert.assertTrue(defaultInstance.equals(clone));
        Assert.assertTrue(clone.equals(defaultInstance));
        
        Assert.assertFalse(defaultInstance.equals(randomInstance));
        Assert.assertFalse(randomInstance.equals(defaultInstance));
        
        // test null safety of equals
        Assert.assertFalse(defaultInstance.equals(unitialized));
        Assert.assertFalse(unitialized.equals(defaultInstance));
        
    }
    
    @Test
    public void testHashcode() {
        
        // test hashcodes
        Assert.assertNotSame(defaultInstance.hashCode(), randomInstance.hashCode());
        Assert.assertEquals(defaultInstance.hashCode(), clone.hashCode());
        
        // test null safety of hashcode
        Assert.assertNotSame(unitialized.hashCode(), defaultInstance.hashCode());
        Assert.assertNotSame(defaultInstance.hashCode(), unitialized.hashCode());
        
    }
    
    @Test
    public void testCompare() { 
       
        // test compare (consistency with equals)
        Assert.assertNotSame(defaultInstance.compareTo(randomInstance), 0);
        Assert.assertEquals(defaultInstance.compareTo(clone), 0);

    }
    
    @Test
    public void testToString() {  
      
        Assert.assertEquals(defaultInstance.toString(), clone.toString());
        Assert.assertNotSame(defaultInstance.toString(), randomInstance.toString());

    }
    
}