package hiber.dao;

import hiber.model.User;

import java.util.List;

public interface UserDao {
   void add(User user);
   List<User> listUsers();
   User getUserById(long id);
   void updateUser(User user);
   void removeUser(User user);
   User getUserByLoginName(String login);
}
