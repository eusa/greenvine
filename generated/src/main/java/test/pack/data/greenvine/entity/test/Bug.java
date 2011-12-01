package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Bug")
@Table(name = "TEST.TBL_BUGS")  
public class Bug implements Comparable<Bug>, Serializable {

    private static final long serialVersionUID = -6191404986977258509L;

    /**
    * Identity field
    */
    private Integer bugId;

    /**
    * description field
    */
    private String description;
    
    /**
    * open field
    */
    private Boolean open;
    
    /**
    * title field
    */
    private String title;
    
    /**
    * owner field
    */
    private User owner;    

    /**
    * reporter field
    */
    private User reporter;    

    /**
    * Default constructor
    */
    public Bug() {
    }
    
    /**
    * Simple Property constructor
    */
    public Bug(Integer bugId, String description, Boolean open, String title) {
        this.bugId = bugId;
        this.description = description;
        this.open = open;
        this.title = title;
    }

    /**
    * Full Property constructor
    */
    public Bug(Integer bugId, String description, Boolean open, String title, User owner, User reporter) {
        this.bugId = bugId;
        this.description = description;
        this.open = open;
        this.title = title;
        this.owner = owner;
        this.reporter = reporter;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "BUG_ID", nullable = false)
    public Integer getBugId() {
        return this.bugId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setBugId(Integer bugId) {
        this.bugId = bugId;
    }

    /**
    * Accessor for description field
    * returns the value of the description field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "DESCRIPTION",  nullable = false, columnDefinition = "VARCHAR(4000)")
    public String getDescription() {
        return this.description;
    }
          
    /**
    * Mutator for the description field
    * @param  sets the value of the description field
    */    
    public void setDescription(String description) {
      this.description = description;
    }
          
    /**
    * Accessor for open field
    * returns the value of the open field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "OPEN",  nullable = false, columnDefinition = "BOOLEAN")
    public Boolean getOpen() {
        return this.open;
    }
          
    /**
    * Mutator for the open field
    * @param  sets the value of the open field
    */    
    public void setOpen(Boolean open) {
      this.open = open;
    }
          
    /**
    * Accessor for title field
    * returns the value of the title field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TITLE",  nullable = false, columnDefinition = "VARCHAR(255)")
    public String getTitle() {
        return this.title;
    }
          
    /**
    * Mutator for the title field
    * @param  sets the value of the title field
    */    
    public void setTitle(String title) {
      this.title = title;
    }
          
    /**
    * Accessor for owner field
    * @return the value of the owner field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.User.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "OWNER", referencedColumnName = "USERNAME",  nullable = false)
    } )
    public User getOwner() {
        return this.owner;
    }
      
    /**
    * Mutator for owner field
    * @param owner the new value for the owner field
    */    
    public void setOwner(User owner) {
        this.owner = owner;
    }
          
    /**
    * Accessor for reporter field
    * @return the value of the reporter field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.User.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "REPORTER", referencedColumnName = "USERNAME",  nullable = false)
    } )
    public User getReporter() {
        return this.reporter;
    }
      
    /**
    * Mutator for reporter field
    * @param reporter the new value for the reporter field
    */    
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.bug
        if ( !(that instanceof Bug) ) return false;

        // Safely cast to Test.bug
        Bug thatObj = (Bug)that;

        // Equality is based on all field values
        return
            this.getDescription() == null ? thatObj.getDescription() == null : this.getDescription().equals(thatObj.getDescription())&&
            this.getOpen() == null ? thatObj.getOpen() == null : this.getOpen().equals(thatObj.getOpen())&&
            this.getTitle() == null ? thatObj.getTitle() == null : this.getTitle().equals(thatObj.getTitle())&&
            this.getOwner() == null ? thatObj.getOwner() == null : this.getOwner().equals(thatObj.getOwner())&&        
            this.getReporter() == null ? thatObj.getReporter() == null : this.getReporter().equals(thatObj.getReporter())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getDescription() ? 0 : getDescription().hashCode());
        hash = 31 * hash + (null == getOpen() ? 0 : getOpen().hashCode());
        hash = 31 * hash + (null == getTitle() ? 0 : getTitle().hashCode());
        hash = 31 * hash + (null == getOwner() ? 0 : getOwner().hashCode());
        hash = 31 * hash + (null == getReporter() ? 0 : getReporter().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.bug:";
        str +=  ("Identity = " + (null == bugId ? "null" : bugId.toString())) + ", ";
        str +=  ("description = " + (null == getDescription() ? "null" : getDescription().toString())) + ", ";
        str +=  ("open = " + (null == getOpen() ? "null" : getOpen().toString())) + ", ";
        str +=  ("title = " + (null == getTitle() ? "null" : getTitle().toString())) + ", ";
        str +=  ("owner = " + (null == getOwner() ? "null" : getOwner().toString())) + ", ";
        str +=  ("reporter = " + (null == getReporter() ? "null" : getReporter().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Bug thatObj) {
    
        int cmp;

        cmp = this.getDescription() == null ?
                (thatObj.getDescription() == null ? 0 : -1) :
                (thatObj.getDescription() == null ? 1 : this.getDescription().compareTo(thatObj.getDescription())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getOpen() == null ?
                (thatObj.getOpen() == null ? 0 : -1) :
                (thatObj.getOpen() == null ? 1 : this.getOpen().compareTo(thatObj.getOpen())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getTitle() == null ?
                (thatObj.getTitle() == null ? 0 : -1) :
                (thatObj.getTitle() == null ? 1 : this.getTitle().compareTo(thatObj.getTitle())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getOwner() == null ?
                (thatObj.getOwner() == null ? 0 : -1) :
                (thatObj.getOwner() == null ? 1 : this.getOwner().compareTo(thatObj.getOwner())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getReporter() == null ?
                (thatObj.getReporter() == null ? 0 : -1) :
                (thatObj.getReporter() == null ? 1 : this.getReporter().compareTo(thatObj.getReporter())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}