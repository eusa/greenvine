package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.EmployeeDao;
import test.pack.data.greenvine.entity.dbo.Employee;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class EmployeeDaoImpl extends JpaDaoSupport implements EmployeeDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#loadEmployee(Integer)
     */
    @Override
    public Employee loadEmployee(Integer employeeId) {
        return getJpaTemplate().getReference(Employee.class, employeeId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#findEmployee(Integer)
     */
    @Override
    public Employee findEmployee(Integer employeeId) {
        return getJpaTemplate().find(Employee.class, employeeId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#findAllEmployees()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Employee> findAllEmployees() {
        return getJpaTemplate().find("from dbo.Employee as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#updateEmployee(Employee)
     */
    @Override 
    public void updateEmployee(Employee employee) {
        getJpaTemplate().merge(employee);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#createEmployee(Employee)
     */
    @Override
    public void createEmployee(Employee employee) {
        getJpaTemplate().persist(employee);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#removeEmployee(Integer)
     */
    @Override
    public void removeEmployee(Integer employeeId) {
         Employee employee = getJpaTemplate().getReference(Employee.class, employeeId);
         getJpaTemplate().remove(employee);

    }

}
