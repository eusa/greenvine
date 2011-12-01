package test.pack.data.greenvine.dao.dbo.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.GroupDao;
import test.pack.data.greenvine.entity.dbo.Group;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class GroupDaoImpl extends HibernateDaoSupport implements GroupDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#loadGroup(Integer)
     */
    @Override
    public Group loadGroup(Integer groupId) {
        return (Group) getHibernateTemplate().load(Group.class, groupId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#findGroup(Integer)
     */
    @Override
    public Group findGroup(Integer groupId) {
        return (Group) getHibernateTemplate().get(Group.class, groupId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#findAllGroups()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Group> findAllGroups() {
        return (List<Group>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.dbo.Group as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#updateGroup(Group)
     */
    @Override 
    public void updateGroup(Group group) {
        getHibernateTemplate().merge(group);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#createGroup(Group)
     */
    @Override
    public void createGroup(Group group) {
        getHibernateTemplate().persist(group);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#removeGroup(Integer)
     */
    @Override
    public void removeGroup(Integer groupId) {
         Object group = getHibernateTemplate().load(Group.class, groupId);
         getHibernateTemplate().delete(group);

    }

}
