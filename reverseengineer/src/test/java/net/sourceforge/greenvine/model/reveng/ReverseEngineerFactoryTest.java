package net.sourceforge.greenvine.model.reveng;

import junit.framework.Assert;
import net.sourceforge.greenvine.reveng.ReverseEngineer;
import net.sourceforge.greenvine.reveng.ReverseEngineerFactory;

import org.junit.Test;

public class ReverseEngineerFactoryTest {
    
    @Test
    public void testLoadConfiguration() throws Exception {
        ReverseEngineerFactory factory = new ReverseEngineerFactory("src/test/resources/reveng-ctx.xml");
        ReverseEngineer reveng = factory.getReverseEngineer();
        Assert.assertNotNull(reveng);
    }

}
