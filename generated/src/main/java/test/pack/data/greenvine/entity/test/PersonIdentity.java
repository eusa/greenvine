package test.pack.data.greenvine.entity.test;

import java.io.Serializable;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class PersonIdentity implements Comparable<PersonIdentity>, Serializable {

private static final long serialVersionUID = 3344821128811521674L;

    /**
    * firstName property
    */
    private String firstName;

    /**
    * lastName property
    */
    private String lastName;

    /**
    * Accessor for firstName field
    * returns the value of the firstName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "FIRST_NAME",  nullable = false, columnDefinition = "VARCHAR(50)")
    public String getFirstName() {
        return this.firstName;
    }
              
    /**
    * Mutator for the firstName field
    * @param  sets the value of the firstName field
    */    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }    

    /**
    * Accessor for lastName field
    * returns the value of the lastName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "LAST_NAME",  nullable = false, columnDefinition = "VARCHAR(50)")
    public String getLastName() {
        return this.lastName;
    }
              
    /**
    * Mutator for the lastName field
    * @param  sets the value of the lastName field
    */    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of PersonIdentity
        if ( !(that instanceof PersonIdentity) ) return false;

        // Safely cast to PersonIdentity
        PersonIdentity thatObj = (PersonIdentity)that;

        // Equality is based on all property values
        return
            this.getFirstName() == null ? thatObj.getFirstName() == null : this.getFirstName().equals(thatObj.getFirstName())&&
            this.getLastName() == null ? thatObj.getLastName() == null : this.getLastName().equals(thatObj.getLastName())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getFirstName() ? 0 : getFirstName().hashCode());
        hash = 31 * hash + (null == getLastName() ? 0 : getLastName().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "PersonIdentity:";
        str +=  ("firstName = " + (null == getFirstName() ? "null" : getFirstName().toString())) + ", ";
        str +=  ("lastName = " + (null == getLastName() ? "null" : getLastName().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(PersonIdentity thatObj) {
    
        int cmp;

        cmp = this.getFirstName() == null ?
                (thatObj.getFirstName() == null ? 0 : -1) :
                (thatObj.getFirstName() == null ? 1 : this.getFirstName().compareTo(thatObj.getFirstName())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getLastName() == null ?
                (thatObj.getLastName() == null ? 0 : -1) :
                (thatObj.getLastName() == null ? 1 : this.getLastName().compareTo(thatObj.getLastName())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}