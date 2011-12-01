package net.sourceforge.greenvine.reveng;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


public class ReverseEngineerFactory {
    
    private final ReverseEngineer reverseEngineer;

    public ReverseEngineerFactory(String path) {
        
        // Set up Spring context
        ApplicationContext context = new FileSystemXmlApplicationContext(
                new String[] {path});
        
        // Get the reverse engineer impl
        this.reverseEngineer = (ReverseEngineer)context.getBean("reverseEngineer");
        
    }

    public ReverseEngineer getReverseEngineer() {
        return reverseEngineer;
    }

}
