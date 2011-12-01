package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.lang.Integer;
import java.math.BigDecimal;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Activity")
@Table(name = "TEST.TBL_ACTIVITY")  
public class Activity implements Comparable<Activity>, Serializable {

    private static final long serialVersionUID = -730221112538954251L;

    /**
    * Identity field
    */
    private Integer activityId;

    /**
    * description field
    */
    private String description;
    
    /**
    * hours field
    */
    private BigDecimal hours;
    
    /**
    * timesheet field
    */
    private Timesheet timesheet;    

    /**
    * Default constructor
    */
    public Activity() {
    }
    
    /**
    * Simple Property constructor
    */
    public Activity(Integer activityId, String description, BigDecimal hours) {
        this.activityId = activityId;
        this.description = description;
        this.hours = hours;
    }

    /**
    * Full Property constructor
    */
    public Activity(Integer activityId, String description, BigDecimal hours, Timesheet timesheet) {
        this.activityId = activityId;
        this.description = description;
        this.hours = hours;
        this.timesheet = timesheet;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "ACTIVITY_ID", nullable = false)
    public Integer getActivityId() {
        return this.activityId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    /**
    * Accessor for description field
    * returns the value of the description field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "DESCRIPTION",  nullable = false, columnDefinition = "VARCHAR(255)")
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
    * Accessor for hours field
    * returns the value of the hours field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "HOURS",  nullable = false, columnDefinition = "DECIMAL(4, 1)")
    public BigDecimal getHours() {
        return this.hours;
    }
          
    /**
    * Mutator for the hours field
    * @param  sets the value of the hours field
    */    
    public void setHours(BigDecimal hours) {
      this.hours = hours;
    }
          
    /**
    * Accessor for timesheet field
    * @return the value of the timesheet field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.test.Timesheet.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_DATE", referencedColumnName = "DATE",  nullable = false), 
        @JoinColumn(name = "FK_EMPLOYEE_ID", referencedColumnName = "FK_EMPLOYEE_ID",  nullable = false)
    } )
    public Timesheet getTimesheet() {
        return this.timesheet;
    }
      
    /**
    * Mutator for timesheet field
    * @param timesheet the new value for the timesheet field
    */    
    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.activity
        if ( !(that instanceof Activity) ) return false;

        // Safely cast to Test.activity
        Activity thatObj = (Activity)that;

        // Equality is based on all field values
        return
            this.getDescription() == null ? thatObj.getDescription() == null : this.getDescription().equals(thatObj.getDescription())&&
            this.getHours() == null ? thatObj.getHours() == null : this.getHours().equals(thatObj.getHours())&&
            this.getTimesheet() == null ? thatObj.getTimesheet() == null : this.getTimesheet().equals(thatObj.getTimesheet())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getDescription() ? 0 : getDescription().hashCode());
        hash = 31 * hash + (null == getHours() ? 0 : getHours().hashCode());
        hash = 31 * hash + (null == getTimesheet() ? 0 : getTimesheet().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.activity:";
        str +=  ("Identity = " + (null == activityId ? "null" : activityId.toString())) + ", ";
        str +=  ("description = " + (null == getDescription() ? "null" : getDescription().toString())) + ", ";
        str +=  ("hours = " + (null == getHours() ? "null" : getHours().toString())) + ", ";
        str +=  ("timesheet = " + (null == getTimesheet() ? "null" : getTimesheet().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Activity thatObj) {
    
        int cmp;

        cmp = this.getDescription() == null ?
                (thatObj.getDescription() == null ? 0 : -1) :
                (thatObj.getDescription() == null ? 1 : this.getDescription().compareTo(thatObj.getDescription())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getHours() == null ?
                (thatObj.getHours() == null ? 0 : -1) :
                (thatObj.getHours() == null ? 1 : this.getHours().compareTo(thatObj.getHours())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getTimesheet() == null ?
                (thatObj.getTimesheet() == null ? 0 : -1) :
                (thatObj.getTimesheet() == null ? 1 : this.getTimesheet().compareTo(thatObj.getTimesheet())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}