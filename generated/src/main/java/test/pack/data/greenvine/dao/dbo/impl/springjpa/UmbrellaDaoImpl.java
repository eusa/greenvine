package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.UmbrellaDao;
import test.pack.data.greenvine.entity.dbo.Umbrella;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class UmbrellaDaoImpl extends JpaDaoSupport implements UmbrellaDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#loadUmbrella(Integer)
     */
    @Override
    public Umbrella loadUmbrella(Integer umbrellaId) {
        return getJpaTemplate().getReference(Umbrella.class, umbrellaId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#findUmbrella(Integer)
     */
    @Override
    public Umbrella findUmbrella(Integer umbrellaId) {
        return getJpaTemplate().find(Umbrella.class, umbrellaId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#findAllUmbrellas()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Umbrella> findAllUmbrellas() {
        return getJpaTemplate().find("from dbo.Umbrella as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#updateUmbrella(Umbrella)
     */
    @Override 
    public void updateUmbrella(Umbrella umbrella) {
        getJpaTemplate().merge(umbrella);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#createUmbrella(Umbrella)
     */
    @Override
    public void createUmbrella(Umbrella umbrella) {
        getJpaTemplate().persist(umbrella);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.UmbrellaDao#removeUmbrella(Integer)
     */
    @Override
    public void removeUmbrella(Integer umbrellaId) {
         Umbrella umbrella = getJpaTemplate().getReference(Umbrella.class, umbrellaId);
         getJpaTemplate().remove(umbrella);

    }

}
