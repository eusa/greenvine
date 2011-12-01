package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.VehicleDao;
import test.pack.data.greenvine.entity.test.Vehicle;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class VehicleDaoImpl extends HibernateDaoSupport implements VehicleDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#loadVehicle(String)
     */
    @Override
    public Vehicle loadVehicle(String regNumber) {
        return (Vehicle) getHibernateTemplate().load(Vehicle.class, regNumber);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#findVehicle(String)
     */
    @Override
    public Vehicle findVehicle(String regNumber) {
        return (Vehicle) getHibernateTemplate().get(Vehicle.class, regNumber);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#findAllVehicles()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Vehicle> findAllVehicles() {
        return (List<Vehicle>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.Vehicle as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#updateVehicle(Vehicle)
     */
    @Override 
    public void updateVehicle(Vehicle vehicle) {
        getHibernateTemplate().merge(vehicle);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#createVehicle(Vehicle)
     */
    @Override
    public void createVehicle(Vehicle vehicle) {
        getHibernateTemplate().persist(vehicle);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#removeVehicle(String)
     */
    @Override
    public void removeVehicle(String regNumber) {
         Object vehicle = getHibernateTemplate().load(Vehicle.class, regNumber);
         getHibernateTemplate().delete(vehicle);

    }

}
