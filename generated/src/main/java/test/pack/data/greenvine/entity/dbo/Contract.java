package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Contract")
@Table(name = "DBO.TBL_CONTRACT")  
public class Contract implements Comparable<Contract>, Serializable {

    private static final long serialVersionUID = 6232147886703177351L;

    /**
    * Identity field
    */
    private Integer employeeId;

    /**
    * terms field
    */
    private String terms;
    
    /**
    * employee constrained identity field
    */
    private Employee employee;
    
    /**
    * Default constructor
    */
    public Contract() {
    }
    
    /**
    * Simple Property constructor
    */
    public Contract(Integer employeeId, String terms) {
        this.employeeId = employeeId;
        this.terms = terms;
    }


    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "FK_EMPLOYEE_ID", nullable = false)
    public Integer getEmployeeId() {
        return this.employeeId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    /**
    * Accessor for terms field
    * returns the value of the terms field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "TERMS",  nullable = false, columnDefinition = "VARCHAR(4000)")
    public String getTerms() {
        return this.terms;
    }
          
    /**
    * Mutator for the terms field
    * @param  sets the value of the terms field
    */    
    public void setTerms(String terms) {
      this.terms = terms;
    }
          
    /**
    * Accessor for employee field
    * @return the value of the employee field. 
    */
    @MapsId
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID",  nullable = false)    
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

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.contract
        if ( !(that instanceof Contract) ) return false;

        // Safely cast to Dbo.contract
        Contract thatObj = (Contract)that;

        // Equality is based on all field values
        return
            this.getTerms() == null ? thatObj.getTerms() == null : this.getTerms().equals(thatObj.getTerms())&&
            this.getEmployee() == null ? thatObj.getEmployee() == null : this.getEmployee().equals(thatObj.getEmployee())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getTerms() ? 0 : getTerms().hashCode());
        hash = 31 * hash + (null == getEmployee() ? 0 : getEmployee().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.contract:";
        str +=  ("Identity = " + (null == employeeId ? "null" : employeeId.toString())) + ", ";
        str +=  ("terms = " + (null == getTerms() ? "null" : getTerms().toString())) + ", ";
        str +=  ("employee = " + (null == getEmployee() ? "null" : getEmployee().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Contract thatObj) {
    
        int cmp;

        cmp = this.getTerms() == null ?
                (thatObj.getTerms() == null ? 0 : -1) :
                (thatObj.getTerms() == null ? 1 : this.getTerms().compareTo(thatObj.getTerms())
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