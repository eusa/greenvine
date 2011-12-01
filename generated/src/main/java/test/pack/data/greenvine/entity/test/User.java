package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.String;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.User")
@Table(name = "TEST.TBL_USER")  
public class User implements Comparable<User>, Serializable {

    private static final long serialVersionUID = 6512095133245247916L;

    /**
    * Identity field
    */
    private String username;

    /**
    * password field
    */
    private String password;
    
    /**
    * profile field
    */
    private Profile profile;    

    /**
    * ownerBugs field
    */  
    private Collection<Bug> ownerBugs = new TreeSet<Bug>();    

    /**
    * reporterBugs field
    */  
    private Collection<Bug> reporterBugs = new TreeSet<Bug>();    

    /**
    * Default constructor
    */
    public User() {
    }
    
    /**
    * Simple Property constructor
    */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
    * Full Property constructor
    */
    public User(String username, String password, Profile profile, Collection<Bug> ownerBugs, Collection<Bug> reporterBugs) {
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.ownerBugs = ownerBugs;
        this.reporterBugs = reporterBugs;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "USERNAME", nullable = false)
    public String getUsername() {
        return this.username;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Accessor for password field
    * returns the value of the password field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "PASSWORD",  nullable = false, columnDefinition = "VARCHAR(50)")
    public String getPassword() {
        return this.password;
    }
          
    /**
    * Mutator for the password field
    * @param  sets the value of the password field
    */    
    public void setPassword(String password) {
      this.password = password;
    }
          
    /**
    * Accessor for profile field
    * @return the value of the profile field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.test.Profile.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    public Profile getProfile() {
        return this.profile;
    }
      
    /**
    * Mutator for profile field
    * @param profile the new value for the profile field
    */    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }
          
    /**
    * Accessor for ownerBugs field
    * @return the value of the ownerBugs field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Bug.class, mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Bug> getOwnerBugs() {
        return this.ownerBugs;
    }
      
    /**
    * Mutator for ownerBugs field
    * @param ownerBugs the new value for the ownerBugs field
    */        
    public void setOwnerBugs(Collection<Bug> ownerBugs) {
        this.ownerBugs = ownerBugs;
    }
          
    /**
    * Accessor for reporterBugs field
    * @return the value of the reporterBugs field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Bug.class, mappedBy = "reporter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Bug> getReporterBugs() {
        return this.reporterBugs;
    }
      
    /**
    * Mutator for reporterBugs field
    * @param reporterBugs the new value for the reporterBugs field
    */        
    public void setReporterBugs(Collection<Bug> reporterBugs) {
        this.reporterBugs = reporterBugs;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.user
        if ( !(that instanceof User) ) return false;

        // Safely cast to Test.user
        User thatObj = (User)that;

        // Equality is based on all field values
        return
            this.getPassword() == null ? thatObj.getPassword() == null : this.getPassword().equals(thatObj.getPassword())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getPassword() ? 0 : getPassword().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.user:";
        str +=  ("Identity = " + (null == username ? "null" : username.toString())) + ", ";
        str +=  ("password = " + (null == getPassword() ? "null" : getPassword().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(User thatObj) {
    
        int cmp;

        cmp = this.getPassword() == null ?
                (thatObj.getPassword() == null ? 0 : -1) :
                (thatObj.getPassword() == null ? 1 : this.getPassword().compareTo(thatObj.getPassword())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}