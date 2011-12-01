package test.pack.data.greenvine.dao.dbo.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.dbo.GroupDao;
import test.pack.data.greenvine.entity.dbo.Group;
import java.lang.Integer;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class GroupDaoImpl extends JpaDaoSupport implements GroupDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#loadGroup(Integer)
     */
    @Override
    public Group loadGroup(Integer groupId) {
        return getJpaTemplate().getReference(Group.class, groupId);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#findGroup(Integer)
     */
    @Override
    public Group findGroup(Integer groupId) {
        return getJpaTemplate().find(Group.class, groupId);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#findAllGroups()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<Group> findAllGroups() {
        return getJpaTemplate().find("from dbo.Group as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#updateGroup(Group)
     */
    @Override 
    public void updateGroup(Group group) {
        getJpaTemplate().merge(group);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#createGroup(Group)
     */
    @Override
    public void createGroup(Group group) {
        getJpaTemplate().persist(group);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.dbo.GroupDao#removeGroup(Integer)
     */
    @Override
    public void removeGroup(Integer groupId) {
         Group group = getJpaTemplate().getReference(Group.class, groupId);
         getJpaTemplate().remove(group);

    }

}
