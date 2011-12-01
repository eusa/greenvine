package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Desk")
@Table(name = "DBO.TBL_DESK")  
public class Desk implements Comparable<Desk>, Serializable {

    private static final long serialVersionUID = -3413875733683957670L;

    /**
    * Identity field
    */
    private Integer deskId;

    /**
    * code field
    */
    private String code;
    
    /**
    * employee field
    */
    private Employee employee;

    /**
    * Default constructor
    */
    public Desk() {
    }
    
    /**
    * Simple Property constructor
    */
    public Desk(Integer deskId, String code) {
        this.deskId = deskId;
        this.code = code;
    }

    /**
    * Full Property constructor
    */
    public Desk(Integer deskId, String code, Employee employee) {
        this.deskId = deskId;
        this.code = code;
        this.employee = employee;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "DESK_ID", nullable = false)
    public Integer getDeskId() {
        return this.deskId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setDeskId(Integer deskId) {
        this.deskId = deskId;
    }

    /**
    * Accessor for code field
    * returns the value of the code field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "CODE",  nullable = false, columnDefinition = "VARCHAR(5)")
    public String getCode() {
        return this.code;
    }
          
    /**
    * Mutator for the code field
    * @param  sets the value of the code field
    */    
    public void setCode(String code) {
      this.code = code;
    }
          

    /**
    * Accessor for employee field
    * @return the value of the employee field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, mappedBy = "desk", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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

        // Check candidate is an instance of Dbo.desk
        if ( !(that instanceof Desk) ) return false;

        // Safely cast to Dbo.desk
        Desk thatObj = (Desk)that;

        // Equality is based on all field values
        return
            this.getCode() == null ? thatObj.getCode() == null : this.getCode().equals(thatObj.getCode())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getCode() ? 0 : getCode().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.desk:";
        str +=  ("Identity = " + (null == deskId ? "null" : deskId.toString())) + ", ";
        str +=  ("code = " + (null == getCode() ? "null" : getCode().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Desk thatObj) {
    
        int cmp;

        cmp = this.getCode() == null ?
                (thatObj.getCode() == null ? 0 : -1) :
                (thatObj.getCode() == null ? 1 : this.getCode().compareTo(thatObj.getCode())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}