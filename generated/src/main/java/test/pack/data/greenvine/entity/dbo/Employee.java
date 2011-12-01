package test.pack.data.greenvine.entity.dbo;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;
import java.math.BigDecimal;
import test.pack.data.greenvine.entity.test.Timesheet;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@Entity(name = "dbo.Employee")
@Table(name = "DBO.TBL_EMPLOYEE", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }), @UniqueConstraint(columnNames = { "FIRST_NAME", "LAST_NAME" }), @UniqueConstraint(columnNames = { "FK_USER_ID" }) })  
public class Employee implements Comparable<Employee>, Serializable {

    private static final long serialVersionUID = -5685535343709176907L;

    /**
    * Identity field
    */
    private Integer employeeId;

    /**
    * NaturalIdentity field
    */
    private String email;

    /**
    * dailyWorkingHours field
    */
    private BigDecimal dailyWorkingHours;
    
    /**
    * firstName field
    */
    private String firstName;
    
    /**
    * hourlyCost field
    */
    private BigDecimal hourlyCost;
    
    /**
    * lastName field
    */
    private String lastName;
    
    /**
    * user field
    */
    private User user;    

    /**
    * contract field
    */
    private Contract contract;    

    /**
    * manager field
    */
    private Employee manager;    

    /**
    * employees field
    */  
    private Collection<Employee> employees = new TreeSet<Employee>();    

    /**
    * timesheets field
    */  
    private Collection<Timesheet> timesheets = new TreeSet<Timesheet>();    

    /**
    * umbrellas field
    */  
    private Collection<Umbrella> umbrellas = new TreeSet<Umbrella>();    

    /**
    * desk field
    */
    private Desk desk;

    /**
    * employeeMentor field
    */
    private Employee employeeMentor;    

    /**
    * employeeMentorees field
    */  
    private Collection<Employee> employeeMentorees = new TreeSet<Employee>();    

    /**
    * Default constructor
    */
    public Employee() {
    }
    
    /**
    * Simple Property constructor
    */
    public Employee(Integer employeeId, String email, BigDecimal dailyWorkingHours, String firstName, BigDecimal hourlyCost, String lastName) {
        this.employeeId = employeeId;
        this.email = email;
        this.dailyWorkingHours = dailyWorkingHours;
        this.firstName = firstName;
        this.hourlyCost = hourlyCost;
        this.lastName = lastName;
    }

    /**
    * Full Property constructor
    */
    public Employee(Integer employeeId, String email, BigDecimal dailyWorkingHours, String firstName, BigDecimal hourlyCost, String lastName, User user, Contract contract, Employee manager, Collection<Employee> employees, Collection<Timesheet> timesheets, Collection<Umbrella> umbrellas, Desk desk, Employee employeeMentor, Collection<Employee> employeeMentorees) {
        this.employeeId = employeeId;
        this.email = email;
        this.dailyWorkingHours = dailyWorkingHours;
        this.firstName = firstName;
        this.hourlyCost = hourlyCost;
        this.lastName = lastName;
        this.user = user;
        this.contract = contract;
        this.manager = manager;
        this.employees = employees;
        this.timesheets = timesheets;
        this.umbrellas = umbrellas;
        this.desk = desk;
        this.employeeMentor = employeeMentor;
        this.employeeMentorees = employeeMentorees;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    @Id
    @Column(name = "EMPLOYEE_ID", nullable = false)
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
    * Accessor for the natural identity field email
    * @returns the value of the field email
    */
            // @NaturalId 
    public String getEmail() {
        return this.email;
    }
    
    /**
    * Mutator for the natural identity field email
    * @param sets the value of the email field
    */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
    * Accessor for dailyWorkingHours field
    * returns the value of the dailyWorkingHours field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "DAILY_WORKING_HOURS",  nullable = false, columnDefinition = "DECIMAL(4, 1)")
    public BigDecimal getDailyWorkingHours() {
        return this.dailyWorkingHours;
    }
          
    /**
    * Mutator for the dailyWorkingHours field
    * @param  sets the value of the dailyWorkingHours field
    */    
    public void setDailyWorkingHours(BigDecimal dailyWorkingHours) {
      this.dailyWorkingHours = dailyWorkingHours;
    }
          
    /**
    * Accessor for firstName field
    * returns the value of the firstName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "FIRST_NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    public String getFirstName() {
        return this.firstName;
    }
          
    /**
    * Mutator for the firstName field
    * @param  sets the value of the firstName field
    */    
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }
          
    /**
    * Accessor for hourlyCost field
    * returns the value of the hourlyCost field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "HOURLY_COST",  nullable = false, columnDefinition = "DECIMAL(19, 4)")
    public BigDecimal getHourlyCost() {
        return this.hourlyCost;
    }
          
    /**
    * Mutator for the hourlyCost field
    * @param  sets the value of the hourlyCost field
    */    
    public void setHourlyCost(BigDecimal hourlyCost) {
      this.hourlyCost = hourlyCost;
    }
          
    /**
    * Accessor for lastName field
    * returns the value of the lastName field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "LAST_NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    public String getLastName() {
        return this.lastName;
    }
          
    /**
    * Mutator for the lastName field
    * @param  sets the value of the lastName field
    */    
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
          
    /**
    * Accessor for user field
    * @return the value of the user field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.User.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumns( {
        @JoinColumn(name = "FK_USER_ID", referencedColumnName = "USER_ID",  nullable = false)    } )
    public User getUser() {
        return this.user;
    }
      
    /**
    * Mutator for user field
    * @param user the new value for the user field
    */    
    public void setUser(User user) {
        this.user = user;
    }
          
    /**
    * Accessor for contract field
    * @return the value of the contract field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Contract.class, fetch = FetchType.LAZY, optional = true, cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "employee")
    public Contract getContract() {
        return this.contract;
    }
      
    /**
    * Mutator for contract field
    * @param contract the new value for the contract field
    */    
    public void setContract(Contract contract) {
        this.contract = contract;
    }
          
    /**
    * Accessor for manager field
    * @return the value of the manager field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY,  optional = true)
    @JoinColumns( {
        @JoinColumn(name = "FK_MANAGER_ID", referencedColumnName = "EMPLOYEE_ID",  nullable = true)
    } )
    public Employee getManager() {
        return this.manager;
    }
      
    /**
    * Mutator for manager field
    * @param manager the new value for the manager field
    */    
    public void setManager(Employee manager) {
        this.manager = manager;
    }
          
    /**
    * Accessor for employees field
    * @return the value of the employees field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, mappedBy = "manager", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Employee> getEmployees() {
        return this.employees;
    }
      
    /**
    * Mutator for employees field
    * @param employees the new value for the employees field
    */        
    public void setEmployees(Collection<Employee> employees) {
        this.employees = employees;
    }
          
    /**
    * Accessor for timesheets field
    * @return the value of the timesheets field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.test.Timesheet.class, mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Timesheet> getTimesheets() {
        return this.timesheets;
    }
      
    /**
    * Mutator for timesheets field
    * @param timesheets the new value for the timesheets field
    */        
    public void setTimesheets(Collection<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }
          
    /**
    * Accessor for umbrellas field
    * @return the value of the umbrellas field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.dbo.Umbrella.class, mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
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
          

    /**
    * Accessor for desk field
    * @return the value of the desk field. 
    */
    @OneToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Desk.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "DBO.TBL_DESK_EMPLOYEE",
        joinColumns = { @JoinColumn(name = "FK_EMPLOYEE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "FK_DESK_ID", referencedColumnName = "DESK_ID", nullable = false) }, 
        uniqueConstraints = { @UniqueConstraint(columnNames = { "FK_EMPLOYEE_ID" }), @UniqueConstraint(columnNames = { "FK_DESK_ID" }) }
    )
    public Desk getDesk() {
        return this.desk;
    }
      
      /**
      * Mutator for desk field
      * @param desk the new value for the desk field
      */        
      public void setDesk(Desk desk) {
        this.desk = desk;
      }
          
    /**
    * Accessor for employeeMentor field
    * @return the value of the employeeMentor field. 
    */
    @ManyToOne(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, fetch = FetchType.LAZY,  optional = true)
    @JoinTable(
        name = "DBO.TBL_EMPLOYEE_MENTOR",
        joinColumns = { @JoinColumn(name = "MENTOREE_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "MENTOR_ID", referencedColumnName = "EMPLOYEE_ID", nullable = false) },
        uniqueConstraints = {@UniqueConstraint(columnNames = { "MENTOREE_ID" })}
    )
    public Employee getEmployeeMentor() {
        return this.employeeMentor;
    }
      
    /**
    * Mutator for employeeMentor field
    * @param employeeMentor the new value for the employeeMentor field
    */    
    public void setEmployeeMentor(Employee employeeMentor) {
        this.employeeMentor = employeeMentor;
    }
          
    /**
    * Accessor for employeeMentorees field
    * @return the value of the employeeMentorees field. 
    */
    @OneToMany(targetEntity = test.pack.data.greenvine.entity.dbo.Employee.class, mappedBy = "employeeMentor", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Collection<Employee> getEmployeeMentorees() {
        return this.employeeMentorees;
    }
      
    /**
    * Mutator for employeeMentorees field
    * @param employeeMentorees the new value for the employeeMentorees field
    */        
    public void setEmployeeMentorees(Collection<Employee> employeeMentorees) {
        this.employeeMentorees = employeeMentorees;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Dbo.employee
        if ( !(that instanceof Employee) ) return false;

        // Safely cast to Dbo.employee
        Employee thatObj = (Employee)that;

        // Equality is based on natural id
        return
            this.getEmail() == null ? thatObj.getEmail() == null : this.getEmail().equals(thatObj.getEmail()) && 
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on natural id
        hash = 31 * hash + (null == getEmail() ? 0 : getEmail().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Dbo.employee:";
        str +=  ("Identity = " + (null == employeeId ? "null" : employeeId.toString())) + ", ";
        str +=  ("dailyWorkingHours = " + (null == getDailyWorkingHours() ? "null" : getDailyWorkingHours().toString())) + ", ";
        str +=  ("firstName = " + (null == getFirstName() ? "null" : getFirstName().toString())) + ", ";
        str +=  ("hourlyCost = " + (null == getHourlyCost() ? "null" : getHourlyCost().toString())) + ", ";
        str +=  ("lastName = " + (null == getLastName() ? "null" : getLastName().toString())) + ", ";
        str +=  ("email = " + (null == getEmail() ? "null" : getEmail().toString())) + ", ";
        str +=  ("user = " + (null == getUser() ? "null" : getUser().toString())) + ", ";
        str +=  ("manager = " + (null == getManager() ? "null" : getManager().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Employee thatObj) {
    
        int cmp;

        cmp = this.getEmail().compareTo(thatObj.getEmail());
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}