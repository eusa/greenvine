package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.User")
@Table(name = "DBO.TBL_USER", uniqueConstraints = { @UniqueConstraint(columnNames = { "USERNAME" }) })  
public class User implements Comparable<User>, Serializable {

    private static final long serialVersionUID = -225345068812449138L;

    /**
    * Identity field
    */
    private Integer userId;

    /**
    * NaturalIdentity field
    */
    private String username;

    /**
    * password field
    */
    private String password;
    
    /**
    * employee field
    */
    private Employee employee;    

    /**
    * groups field
    */
    private Collection<Group> groups = new TreeSet<Group>();

    /**
    * Default constructor
    */
    public User() {
    }
    
    /**
    * Simple Property constructor
    */
    public User(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
    * Full Property constructor
    */
    public User(Integer userId, String username, String password, Employee employee, Collection<Group> groups) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.employee = employee;
        this.groups = groups;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "USER_ID", nullable = false)
    public Integer getUserId() {
        return this.userId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
    * Accessor for the natural identity field username
    * @returns the value of the field username
    */
            // @NaturalId 
    public String getUsername() {
        return this.username;
    }
    
    /**
    * Mutator for the natural identity field username
    * @param sets the value of the username field
    */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
    * Accessor for password field
    * returns the value of the password field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "PASSWORD",  nullable = false, columnDefinition = "VARCHAR(255)")
    public String getPassword() {
        return this.password;
    }
          
    /**
    * Mutator for the password field
    * @param  sets the value of the password field
    */    
    public void setPassword(String password) {
      this.password = password;
    }
          
    /**
    * Accessor for employee field
    * @return the value of the employee field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
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
    * Accessor for groups field
    * @return the value of the groups field. 
    */
    @ManyToMany(targetEntity = test.pack.data.greenvine.entity.dbo.Group.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "DBO.TBL_GROUP_USER",
        joinColumns = { @JoinColumn(name = "FK_USER_ID", referencedColumnName = "USER_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "FK_GROUP_ID", referencedColumnName = "GROUP_ID", nullable = false) }
    )
    public Collection<Group> getGroups() {
        return this.groups;
    }
      
      /**
      * Mutator for groups field
      * @param groups the new value for the groups field
      */        
      public void setGroups(Collection<Group> groups) {
        this.groups = groups;
      }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.user
        if ( !(that instanceof User) ) return false;

        // Safely cast to Dbo.user
        User thatObj = (User)that;

        // Equality is based on natural id
        return
            this.getUsername() == null ? thatObj.getUsername() == null : this.getUsername().equals(thatObj.getUsername()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getUsername() ? 0 : getUsername().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.user:";
        str +=  ("Identity = " + (null == userId ? "null" : userId.toString())) + ", ";
        str +=  ("password = " + (null == getPassword() ? "null" : getPassword().toString())) + ", ";
        str +=  ("username = " + (null == getUsername() ? "null" : getUsername().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(User thatObj) {
    
        int cmp;

        cmp = this.getUsername().compareTo(thatObj.getUsername());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}