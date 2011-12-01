package net.sourceforge.greenvine.reveng;

import net.sourceforge.greenvine.model.api.Database;
import net.sourceforge.greenvine.model.api.Model;
import net.sourceforge.greenvine.model.api.ModelException;

public interface ReverseEngineer {
    
    public abstract Model reverseEngineer(Database database) throws ModelException;

}
