package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.DeskDao;
import test.pack.data.greenvine.entity.dbo.Desk;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class DeskDaoImpl extends HibernateDaoSupport implements DeskDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#loadDesk(Integer)
     */
    @Override
    public Desk loadDesk(Integer deskId) {
        return (Desk) getHibernateTemplate().load(Desk.class, deskId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#findDesk(Integer)
     */
    @Override
    public Desk findDesk(Integer deskId) {
        return (Desk) getHibernateTemplate().get(Desk.class, deskId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#findAllDesks()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Desk> findAllDesks() {
        return (List<Desk>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Desk as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#updateDesk(Desk)
     */
    @Override 
    public void updateDesk(Desk desk) {
        getHibernateTemplate().merge(desk);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#createDesk(Desk)
     */
    @Override
    public void createDesk(Desk desk) {
        getHibernateTemplate().persist(desk);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.DeskDao#removeDesk(Integer)
     */
    @Override
    public void removeDesk(Integer deskId) {
         Object desk = getHibernateTemplate().load(Desk.class, deskId);
         getHibernateTemplate().delete(desk);

    }

}
