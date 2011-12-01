package test.pack.data.greenvine.dao.test.impl.springjpa;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import test.pack.data.greenvine.dao.test.UserDao;
import test.pack.data.greenvine.entity.test.User;
import java.lang.String;

import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.dao.springjpa.SpringJpaDaoImplGenerator")
@Repository
public class UserDaoImpl extends JpaDaoSupport implements UserDao {

     /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#loadUser(String)
     */
    @Override
    public User loadUser(String username) {
        return getJpaTemplate().getReference(User.class, username);
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#findUser(String)
     */
    @Override
    public User findUser(String username) {
        return getJpaTemplate().find(User.class, username);
    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#findAllUsers()
     */
    @Override
    @SuppressWarnings("unchecked") 
    public List<User> findAllUsers() {
        return getJpaTemplate().find("from test.User as o order by o.id");
    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#updateUser(User)
     */
    @Override 
    public void updateUser(User user) {
        getJpaTemplate().merge(user);

    }
    
    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#createUser(User)
     */
    @Override
    public void createUser(User user) {
        getJpaTemplate().persist(user);

    }

    /* (non-Javadoc)
     * @see test.pack.data.greenvine.dao.test.UserDao#removeUser(String)
     */
    @Override
    public void removeUser(String username) {
         User user = getJpaTemplate().getReference(User.class, username);
         getJpaTemplate().remove(user);

    }

}
