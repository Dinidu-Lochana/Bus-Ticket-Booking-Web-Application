package com.app.bus.booking.service;

import com.app.bus.booking.domain.user.User;
import com.app.bus.booking.domain.user.UserRole;
import com.app.bus.booking.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService,UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private UserDetailsService userDetailsService;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return new org.springframework.security.core.userdetails.User(
            user.getEmail(), user.getPassword(), new ArrayList<>());
  }

  @Override
  public User signupClient(User user) {
    user.setRole(UserRole.CLIENT);
    return userRepository.save(user);
  }

  @Override
  public User signupCompany(User user) {
    user.setRole(UserRole.COMPANY);
    return userRepository.save(user);
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
