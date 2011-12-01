package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.StandDao;
import test.pack.data.greenvine.entity.dbo.Stand;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class StandDaoImpl extends JpaDaoSupport implements StandDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#loadStand(Integer)
     */
    @Override
    public Stand loadStand(Integer standId) {
        return getJpaTemplate().getReference(Stand.class, standId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#findStand(Integer)
     */
    @Override
    public Stand findStand(Integer standId) {
        return getJpaTemplate().find(Stand.class, standId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#findAllStands()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Stand> findAllStands() {
        return getJpaTemplate().find("from dbo.Stand as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#updateStand(Stand)
     */
    @Override 
    public void updateStand(Stand stand) {
        getJpaTemplate().merge(stand);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#createStand(Stand)
     */
    @Override
    public void createStand(Stand stand) {
        getJpaTemplate().persist(stand);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.StandDao#removeStand(Integer)
     */
    @Override
    public void removeStand(Integer standId) {
         Stand stand = getJpaTemplate().getReference(Stand.class, standId);
         getJpaTemplate().remove(stand);

    }

}
