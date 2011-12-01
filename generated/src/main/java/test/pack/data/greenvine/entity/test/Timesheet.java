package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.dbo.Employee;
import test.pack.data.greenvine.entity.test.TimesheetIdentity;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Timesheet")
@Table(name = "TEST.TBL_TIMESHEET")  
public class Timesheet implements Comparable<Timesheet>, Serializable {

    private static final long serialVersionUID = 556371799597862357L;

    /**
    * Identity field
    */
    private TimesheetIdentity timesheetIdentity;

    /**
    * expectedHours field
    */
    private BigDecimal expectedHours;
    
    /**
    * employee field
    */
    private Employee employee;    

    /**
    * activities field
    */  
    private Collection<Activity> activities = new TreeSet<Activity>();    

    /**
    * Default constructor
    */
    public Timesheet() {
    }
    
    /**
    * Simple Property constructor
    */
    public Timesheet(TimesheetIdentity timesheetIdentity, BigDecimal expectedHours) {
        this.timesheetIdentity = timesheetIdentity;
        this.expectedHours = expectedHours;
    }

    /**
    * Full Property constructor
    */
    public Timesheet(TimesheetIdentity timesheetIdentity, BigDecimal expectedHours, Employee employee, Collection<Activity> activities) {
        this.timesheetIdentity = timesheetIdentity;
        this.expectedHours = expectedHours;
        this.employee = employee;
        this.activities = activities;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
	@EmbeddedId
	@AttributeOverrides( {
        @AttributeOverride(name = "date", column = @Column(name = "DATE")), 
        @AttributeOverride(name = "employeeId", column = @Column(name = "FK_EMPLOYEE_ID"))
    } )     
    public TimesheetIdentity getTimesheetIdentity() {
        return this.timesheetIdentity;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setTimesheetIdentity(TimesheetIdentity timesheetIdentity) {
        this.timesheetIdentity = timesheetIdentity;
    }

    /**
    * Accessor for expectedHours field
    * returns the value of the expectedHours field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "EXPECTED_HOURS",  nullable = false, columnDefinition = "DECIMAL(4, 1)")
    public BigDecimal getExpectedHours() {
        return this.expectedHours;
    }
          
    /**
    * Mutator for the expectedHours field
    * @param  sets the value of the expectedHours field
    */    
    public void setExpectedHours(BigDecimal expectedHours) {
      this.expectedHours = expectedHours;
    }
          
    /**
    * Accessor for employee field
    * @return the value of the employee field. 
    */
    @MapsId("employeeId")
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID",  nullable = false, insertable = false, updatable = false)
    } )
    public Employee getEmployee() {
        return this.employee;
    }
      
    /**
    * Mutator for employee field
    * @param employee the new value for the employee field
    */    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
          
    /**
    * Accessor for activities field
    * @return the value of the activities field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Activity.class, mappedBy = "timesheet", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Activity> getActivities() {
        return this.activities;
    }
      
    /**
    * Mutator for activities field
    * @param activities the new value for the activities field
    */        
    public void setActivities(Collection<Activity> activities) {
        this.activities = activities;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.timesheet
        if ( !(that instanceof Timesheet) ) return false;

        // Safely cast to Test.timesheet
        Timesheet thatObj = (Timesheet)that;

        // Equality is based on all field values
        return
            this.getExpectedHours() == null ? thatObj.getExpectedHours() == null : this.getExpectedHours().equals(thatObj.getExpectedHours())&&
            this.getEmployee() == null ? thatObj.getEmployee() == null : this.getEmployee().equals(thatObj.getEmployee())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getExpectedHours() ? 0 : getExpectedHours().hashCode());
        hash = 31 * hash + (null == getEmployee() ? 0 : getEmployee().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.timesheet:";
        str +=  ("Identity = " + (null == timesheetIdentity ? "null" : timesheetIdentity.toString())) + ", ";
        str +=  ("expectedHours = " + (null == getExpectedHours() ? "null" : getExpectedHours().toString())) + ", ";
        str +=  ("employee = " + (null == getEmployee() ? "null" : getEmployee().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Timesheet thatObj) {
    
        int cmp;

        cmp = this.getExpectedHours() == null ?
                (thatObj.getExpectedHours() == null ? 0 : -1) :
                (thatObj.getExpectedHours() == null ? 1 : this.getExpectedHours().compareTo(thatObj.getExpectedHours())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getEmployee() == null ?
                (thatObj.getEmployee() == null ? 0 : -1) :
                (thatObj.getEmployee() == null ? 1 : this.getEmployee().compareTo(thatObj.getEmployee())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}