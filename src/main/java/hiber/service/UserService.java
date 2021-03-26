package hiber.service;

import hiber.model.User;
import hiber.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

public interface UserService {
    void createUser(UserDto user);
    List<User> listUsers();
    void removeUser(User user);
    void updateUser(User user);
    User getUserById(long id);
    UserDetails loadUserByUsername(String s);
    Map<String, String> validateUser(UserDto user);
}
