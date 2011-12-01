package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class TimesheetIdentity implements Comparable<TimesheetIdentity>, Serializable {

private static final long serialVersionUID = -6249557784776226856L;

    /**
    * date property
    */
    private Date date;

    /**
    * employeeId property
    */
    private Integer employeeId;

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
    * Accessor for employeeId field
    * returns the value of the employeeId field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "FK_EMPLOYEE_ID",  nullable = false, columnDefinition = "INTEGER")
    public Integer getEmployeeId() {
        return this.employeeId;
    }
              
    /**
    * Mutator for the employeeId field
    * @param  sets the value of the employeeId field
    */    
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of TimesheetIdentity
        if ( !(that instanceof TimesheetIdentity) ) return false;

        // Safely cast to TimesheetIdentity
        TimesheetIdentity thatObj = (TimesheetIdentity)that;

        // Equality is based on all property values
        return
            this.getDate() == null ? thatObj.getDate() == null : this.getDate().equals(thatObj.getDate())&&
            this.getEmployeeId() == null ? thatObj.getEmployeeId() == null : this.getEmployeeId().equals(thatObj.getEmployeeId())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getDate() ? 0 : getDate().hashCode());
        hash = 31 * hash + (null == getEmployeeId() ? 0 : getEmployeeId().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "TimesheetIdentity:";
        str +=  ("date = " + (null == getDate() ? "null" : getDate().toString())) + ", ";
        str +=  ("employeeId = " + (null == getEmployeeId() ? "null" : getEmployeeId().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(TimesheetIdentity thatObj) {
    
        int cmp;

        cmp = this.getDate() == null ?
                (thatObj.getDate() == null ? 0 : -1) :
                (thatObj.getDate() == null ? 1 : this.getDate().compareTo(thatObj.getDate())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getEmployeeId() == null ?
                (thatObj.getEmployeeId() == null ? 0 : -1) :
                (thatObj.getEmployeeId() == null ? 1 : this.getEmployeeId().compareTo(thatObj.getEmployeeId())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}