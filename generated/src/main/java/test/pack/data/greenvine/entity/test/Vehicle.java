package test.pack.data.greenvine.entity.test;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.String;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "test.Vehicle")
@Table(name = "TEST.TBL_VEHICLE")  
public class Vehicle implements Comparable<Vehicle>, Serializable {

    private static final long serialVersionUID = 1401447467389895182L;

    /**
    * Identity field
    */
    private String regNumber;

    /**
    * model field
    */
    private String model;
    
    /**
    * parkingPermits field
    */  
    private Collection<ParkingPermit> parkingPermits = new TreeSet<ParkingPermit>();    

    /**
    * Default constructor
    */
    public Vehicle() {
    }
    
    /**
    * Simple Property constructor
    */
    public Vehicle(String regNumber, String model) {
        this.regNumber = regNumber;
        this.model = model;
    }

    /**
    * Full Property constructor
    */
    public Vehicle(String regNumber, String model, Collection<ParkingPermit> parkingPermits) {
        this.regNumber = regNumber;
        this.model = model;
        this.parkingPermits = parkingPermits;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "REG_NUMBER", nullable = false)
    public String getRegNumber() {
        return this.regNumber;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    /**
    * Accessor for model field
    * returns the value of the model field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "MODEL",  nullable = false, columnDefinition = "VARCHAR(400)")
    public String getModel() {
        return this.model;
    }
          
    /**
    * Mutator for the model field
    * @param  sets the value of the model field
    */    
    public void setModel(String model) {
      this.model = model;
    }
          
    /**
    * Accessor for parkingPermits field
    * @return the value of the parkingPermits field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.ParkingPermit.class, mappedBy = "vehicle", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<ParkingPermit> getParkingPermits() {
        return this.parkingPermits;
    }
      
    /**
    * Mutator for parkingPermits field
    * @param parkingPermits the new value for the parkingPermits field
    */        
    public void setParkingPermits(Collection<ParkingPermit> parkingPermits) {
        this.parkingPermits = parkingPermits;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Test.vehicle
        if ( !(that instanceof Vehicle) ) return false;

        // Safely cast to Test.vehicle
        Vehicle thatObj = (Vehicle)that;

        // Equality is based on all field values
        return
            this.getModel() == null ? thatObj.getModel() == null : this.getModel().equals(thatObj.getModel())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getModel() ? 0 : getModel().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Test.vehicle:";
        str +=  ("Identity = " + (null == regNumber ? "null" : regNumber.toString())) + ", ";
        str +=  ("model = " + (null == getModel() ? "null" : getModel().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Vehicle thatObj) {
    
        int cmp;

        cmp = this.getModel() == null ?
                (thatObj.getModel() == null ? 0 : -1) :
                (thatObj.getModel() == null ? 1 : this.getModel().compareTo(thatObj.getModel())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}