package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.VehicleDao;
import test.pack.data.greenvine.entity.test.Vehicle;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class VehicleDaoImpl extends JpaDaoSupport implements VehicleDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#loadVehicle(String)
     */
    @Override
    public Vehicle loadVehicle(String regNumber) {
        return getJpaTemplate().getReference(Vehicle.class, regNumber);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#findVehicle(String)
     */
    @Override
    public Vehicle findVehicle(String regNumber) {
        return getJpaTemplate().find(Vehicle.class, regNumber);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#findAllVehicles()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Vehicle> findAllVehicles() {
        return getJpaTemplate().find("from test.Vehicle as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#updateVehicle(Vehicle)
     */
    @Override 
    public void updateVehicle(Vehicle vehicle) {
        getJpaTemplate().merge(vehicle);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#createVehicle(Vehicle)
     */
    @Override
    public void createVehicle(Vehicle vehicle) {
        getJpaTemplate().persist(vehicle);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.VehicleDao#removeVehicle(String)
     */
    @Override
    public void removeVehicle(String regNumber) {
         Vehicle vehicle = getJpaTemplate().getReference(Vehicle.class, regNumber);
         getJpaTemplate().remove(vehicle);

    }

}
