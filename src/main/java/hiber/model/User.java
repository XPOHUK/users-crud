package hiber.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "login", nullable = false, unique = true)
   private String loginName;
   private String password;
   private String firstName;
   private String lastName;
   private String email;
   private boolean isEnabled;
   private boolean isAccountNonExpired;
   private boolean isAccountNonLocked;
   private boolean isCredentialsNonExpired;

   @ManyToMany(cascade = {CascadeType.ALL})
   @JoinTable(
           name = "user_roles",
           joinColumns = { @JoinColumn(name = "user_id")},
           inverseJoinColumns = { @JoinColumn(name = "role_id")}
   )
   private Set<Role> roles;

   public User() {}

   public User(String firstName, String lastName, String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   public User(UserDto userDto){
      this.firstName = userDto.getFirstName();
      this.lastName = userDto.getLastName();
      this.loginName = userDto.getLoginName();
      this.email = userDto.getEmail();
      this.password = userDto.getPassword();
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getLoginName() {
      return loginName;
   }

   public void setLoginName(String loginName) {
      this.loginName = loginName;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public Set<Role> getRoles() {
      return roles;
   }

   public void setRoles(Set<Role> roles) {
      this.roles = roles;
   }

   public void enable(){
      isEnabled = true;
   }

   public void disable(){
      isEnabled = false;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return roles;
   }

   @Override
   public String getUsername() {
      return loginName;
   }

   public void setAccountNonExpired(boolean accountNonExpired) {
      isAccountNonExpired = accountNonExpired;
   }

   @Override
   public boolean isAccountNonExpired() {
      return isAccountNonExpired;
   }

   public void setAccountNonLocked(boolean accountNonLocked) {
      isAccountNonLocked = accountNonLocked;
   }

   @Override
   public boolean isAccountNonLocked() {
      return isAccountNonLocked;
   }

   public void setCredentialsNonExpired(boolean credentialsNonExpired) {
      isCredentialsNonExpired = credentialsNonExpired;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return isCredentialsNonExpired;
   }

   @Override
   public boolean isEnabled() {
      return isEnabled;
   }

   public void setEnabled(boolean enabled) {
      isEnabled = enabled;
   }
}
