package net.sourceforge.greenvine.plugin;

import java.io.File;

import net.sourceforge.greenvine.dbextractor.testutils.H2DatabaseUtil;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public class RevgenMojoTest extends AbstractMojoTestCase {
    
    protected void setUp() throws Exception {
    
        // required for mojo lookups to work
        super.setUp();
        
            }
    
    public void testMojoGoal() throws Exception
    {
        
        // Create database
        H2DatabaseUtil.createDatabase("src/test/resources/schedule_schema.sql", "SCHEDULE_DB");

        
        File testPom = new File( getBasedir(),
          "src/test/resources/pom.xml" );
        
        RevgenMojo mojo = (RevgenMojo) lookupMojo ( "revgen", testPom );
    
        assertNotNull( mojo );
        
        mojo.execute();
        
    }
}
