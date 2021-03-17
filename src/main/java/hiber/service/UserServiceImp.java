package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImp implements UserService, UserDetailsService {

   @Autowired
   private UserDao userDao;

   @Override
   public void add(User user) {
      userDao.add(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      List<User> users = userDao.listUsers();
      return users;
   }

   @Override
   public void removeUser(User user) {
      userDao.removeUser(user);
   }

   @Override
   public void updateUser(User user) {
      userDao.updateUser(user);
   }

   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      return userDao.getUserByLoginName(s);
   }
}
