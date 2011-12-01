package test.pack.data.greenvine.dao.test.impl.springhibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.UserDao;
import test.pack.data.greenvine.entity.test.User;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springhibernate.SpringHibernateDaoImplGenerator")
@Repository
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#loadUser(String)
     */
    @Override
    public User loadUser(String username) {
        return (User) getHibernateTemplate().load(User.class, username);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#findUser(String)
     */
    @Override
    public User findUser(String username) {
        return (User) getHibernateTemplate().get(User.class, username);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#findAllUsers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<User> findAllUsers() {
        return (List<User>)getHibernateTemplate().find("from test.pack.data.greenvine.entity.test.User as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#updateUser(User)
     */
    @Override 
    public void updateUser(User user) {
        getHibernateTemplate().merge(user);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#createUser(User)
     */
    @Override
    public void createUser(User user) {
        getHibernateTemplate().persist(user);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#removeUser(String)
     */
    @Override
    public void removeUser(String username) {
         Object user = getHibernateTemplate().load(User.class, username);
         getHibernateTemplate().delete(user);

    }

}
