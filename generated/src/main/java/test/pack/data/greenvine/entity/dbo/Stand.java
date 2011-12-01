package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Stand")
@Table(name = "DBO.TBL_STAND")  
public class Stand implements Comparable<Stand>, Serializable {

    private static final long serialVersionUID = 1146347372320325653L;

    /**
    * Identity field
    */
    private Integer standId;

    /**
    * description field
    */
    private String description;
    
    /**
    * umbrellas field
    */  
    private Collection<Umbrella> umbrellas = new TreeSet<Umbrella>();    

    /**
    * Default constructor
    */
    public Stand() {
    }
    
    /**
    * Simple Property constructor
    */
    public Stand(Integer standId, String description) {
        this.standId = standId;
        this.description = description;
    }

    /**
    * Full Property constructor
    */
    public Stand(Integer standId, String description, Collection<Umbrella> umbrellas) {
        this.standId = standId;
        this.description = description;
        this.umbrellas = umbrellas;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "STAND_ID", nullable = false)
    public Integer getStandId() {
        return this.standId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setStandId(Integer standId) {
        this.standId = standId;
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
    * Accessor for umbrellas field
    * @return the value of the umbrellas field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.dbo.Umbrella.class, mappedBy = "stand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Umbrella> getUmbrellas() {
        return this.umbrellas;
    }
      
    /**
    * Mutator for umbrellas field
    * @param umbrellas the new value for the umbrellas field
    */        
    public void setUmbrellas(Collection<Umbrella> umbrellas) {
        this.umbrellas = umbrellas;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.stand
        if ( !(that instanceof Stand) ) return false;

        // Safely cast to Dbo.stand
        Stand thatObj = (Stand)that;

        // Equality is based on all field values
        return
            this.getDescription() == null ? thatObj.getDescription() == null : this.getDescription().equals(thatObj.getDescription())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getDescription() ? 0 : getDescription().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.stand:";
        str +=  ("Identity = " + (null == standId ? "null" : standId.toString())) + ", ";
        str +=  ("description = " + (null == getDescription() ? "null" : getDescription().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Stand thatObj) {
    
        int cmp;

        cmp = this.getDescription() == null ?
                (thatObj.getDescription() == null ? 0 : -1) :
                (thatObj.getDescription() == null ? 1 : this.getDescription().compareTo(thatObj.getDescription())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}