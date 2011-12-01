package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Umbrella")
@Table(name = "DBO.TBL_UMBRELLA")  
public class Umbrella implements Comparable<Umbrella>, Serializable {

    private static final long serialVersionUID = -4750533387384521479L;

    /**
    * Identity field
    */
    private Integer umbrellaId;

    /**
    * colour field
    */
    private String colour;
    
    /**
    * employee field
    */
    private Employee employee;    

    /**
    * stand field
    */
    private Stand stand;    

    /**
    * Default constructor
    */
    public Umbrella() {
    }
    
    /**
    * Simple Property constructor
    */
    public Umbrella(Integer umbrellaId, String colour) {
        this.umbrellaId = umbrellaId;
        this.colour = colour;
    }

    /**
    * Full Property constructor
    */
    public Umbrella(Integer umbrellaId, String colour, Employee employee, Stand stand) {
        this.umbrellaId = umbrellaId;
        this.colour = colour;
        this.employee = employee;
        this.stand = stand;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "UMBRELLA_ID", nullable = false)
    public Integer getUmbrellaId() {
        return this.umbrellaId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setUmbrellaId(Integer umbrellaId) {
        this.umbrellaId = umbrellaId;
    }

    /**
    * Accessor for colour field
    * returns the value of the colour field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "COLOUR",  nullable = false, columnDefinition = "VARCHAR(4000)")
    public String getColour() {
        return this.colour;
    }
          
    /**
    * Mutator for the colour field
    * @param  sets the value of the colour field
    */    
    public void setColour(String colour) {
      this.colour = colour;
    }
          
    /**
    * Accessor for employee field
    * @return the value of the employee field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY,  optional = false)
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
          
    /**
    * Accessor for stand field
    * @return the value of the stand field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Stand.class, fetch = FetchType.LAZY,  optional = true)
    @JoinTable(
        name = "DBO.TBL_STAND_UMBRELLA",
        joinColumns = { @JoinColumn(name = "FK_UMBRELLA_ID", referencedColumnName = "UMBRELLA_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "FK_STAND_ID", referencedColumnName = "STAND_ID", nullable = false) },
        uniqueConstraints = {@UniqueConstraint(columnNames = { "FK_UMBRELLA_ID" })}
    )
    public Stand getStand() {
        return this.stand;
    }
      
    /**
    * Mutator for stand field
    * @param stand the new value for the stand field
    */    
    public void setStand(Stand stand) {
        this.stand = stand;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.umbrella
        if ( !(that instanceof Umbrella) ) return false;

        // Safely cast to Dbo.umbrella
        Umbrella thatObj = (Umbrella)that;

        // Equality is based on all field values
        return
            this.getColour() == null ? thatObj.getColour() == null : this.getColour().equals(thatObj.getColour())&&
            this.getEmployee() == null ? thatObj.getEmployee() == null : this.getEmployee().equals(thatObj.getEmployee())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getColour() ? 0 : getColour().hashCode());
        hash = 31 * hash + (null == getEmployee() ? 0 : getEmployee().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.umbrella:";
        str +=  ("Identity = " + (null == umbrellaId ? "null" : umbrellaId.toString())) + ", ";
        str +=  ("colour = " + (null == getColour() ? "null" : getColour().toString())) + ", ";
        str +=  ("employee = " + (null == getEmployee() ? "null" : getEmployee().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Umbrella thatObj) {
    
        int cmp;

        cmp = this.getColour() == null ?
                (thatObj.getColour() == null ? 0 : -1) :
                (thatObj.getColour() == null ? 1 : this.getColour().compareTo(thatObj.getColour())
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