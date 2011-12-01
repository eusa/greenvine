package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class ParkingPermitIdentity implements Comparable<ParkingPermitIdentity>, Serializable {

private static final long serialVersionUID = 4485035046539776358L;

    /**
    * date property
    */
    private Date date;

    /**
    * regNumber property
    */
    private String regNumber;

    /**
    * Accessor for date field
    * returns the value of the date field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "DATE",  nullable = false, columnDefinition = "DATE")
    public Date getDate() {
        return this.date;
    }
              
    /**
    * Mutator for the date field
    * @param  sets the value of the date field
    */    
    public void setDate(Date date) {
        this.date = date;
    }    

    /**
    * Accessor for regNumber field
    * returns the value of the regNumber field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "FK_REG_NUMBER",  nullable = false, columnDefinition = "CHARACTER(7)")
    public String getRegNumber() {
        return this.regNumber;
    }
              
    /**
    * Mutator for the regNumber field
    * @param  sets the value of the regNumber field
    */    
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of ParkingPermitIdentity
        if ( !(that instanceof ParkingPermitIdentity) ) return false;

        // Safely cast to ParkingPermitIdentity
        ParkingPermitIdentity thatObj = (ParkingPermitIdentity)that;

        // Equality is based on all property values
        return
            this.getDate() == null ? thatObj.getDate() == null : this.getDate().equals(thatObj.getDate())&&
            this.getRegNumber() == null ? thatObj.getRegNumber() == null : this.getRegNumber().equals(thatObj.getRegNumber())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getDate() ? 0 : getDate().hashCode());
        hash = 31 * hash + (null == getRegNumber() ? 0 : getRegNumber().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "ParkingPermitIdentity:";
        str +=  ("date = " + (null == getDate() ? "null" : getDate().toString())) + ", ";
        str +=  ("regNumber = " + (null == getRegNumber() ? "null" : getRegNumber().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(ParkingPermitIdentity thatObj) {
    
        int cmp;

        cmp = this.getDate() == null ?
                (thatObj.getDate() == null ? 0 : -1) :
                (thatObj.getDate() == null ? 1 : this.getDate().compareTo(thatObj.getDate())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getRegNumber() == null ?
                (thatObj.getRegNumber() == null ? 0 : -1) :
                (thatObj.getRegNumber() == null ? 1 : this.getRegNumber().compareTo(thatObj.getRegNumber())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}