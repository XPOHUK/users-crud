package hiber.dao;

import hiber.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void add(User user) {
      entityManager.persist(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=entityManager.createQuery("select u from User u", User.class);
      return query.getResultList();
   }

   @Override
   public void updateUser(User user){
      entityManager.merge(user);
   }

   @Override
   public void removeUser(User user){
      entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
   }

   @Override
   public User getUserById(long id){
      TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class).setParameter("id", id);
      return query.getSingleResult();
   }


}
