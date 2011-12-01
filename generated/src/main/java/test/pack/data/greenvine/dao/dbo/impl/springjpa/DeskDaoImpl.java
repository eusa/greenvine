package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.DeskDao;
import test.pack.data.greenvine.entity.dbo.Desk;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class DeskDaoImpl extends JpaDaoSupport implements DeskDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#loadDesk(Integer)
     */
    @Override
    public Desk loadDesk(Integer deskId) {
        return getJpaTemplate().getReference(Desk.class, deskId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#findDesk(Integer)
     */
    @Override
    public Desk findDesk(Integer deskId) {
        return getJpaTemplate().find(Desk.class, deskId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#findAllDesks()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Desk> findAllDesks() {
        return getJpaTemplate().find("from dbo.Desk as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#updateDesk(Desk)
     */
    @Override 
    public void updateDesk(Desk desk) {
        getJpaTemplate().merge(desk);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#createDesk(Desk)
     */
    @Override
    public void createDesk(Desk desk) {
        getJpaTemplate().persist(desk);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#removeDesk(Integer)
     */
    @Override
    public void removeDesk(Integer deskId) {
         Desk desk = getJpaTemplate().getReference(Desk.class, deskId);
         getJpaTemplate().remove(desk);

    }

}
