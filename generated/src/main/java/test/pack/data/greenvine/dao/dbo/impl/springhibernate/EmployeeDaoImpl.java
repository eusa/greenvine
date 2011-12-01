package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.EmployeeDao;
import test.pack.data.greenvine.entity.dbo.Employee;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class EmployeeDaoImpl extends HibernateDaoSupport implements EmployeeDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#loadEmployee(Integer)
     */
    @Override
    public Employee loadEmployee(Integer employeeId) {
        return (Employee) getHibernateTemplate().load(Employee.class, employeeId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#findEmployee(Integer)
     */
    @Override
    public Employee findEmployee(Integer employeeId) {
        return (Employee) getHibernateTemplate().get(Employee.class, employeeId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#findAllEmployees()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Employee> findAllEmployees() {
        return (List<Employee>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Employee as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#updateEmployee(Employee)
     */
    @Override 
    public void updateEmployee(Employee employee) {
        getHibernateTemplate().merge(employee);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#createEmployee(Employee)
     */
    @Override
    public void createEmployee(Employee employee) {
        getHibernateTemplate().persist(employee);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.EmployeeDao#removeEmployee(Integer)
     */
    @Override
    public void removeEmployee(Integer employeeId) {
         Object employee = getHibernateTemplate().load(Employee.class, employeeId);
         getHibernateTemplate().delete(employee);

    }

}
