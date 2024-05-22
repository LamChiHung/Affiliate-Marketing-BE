package com.sparkminds.affiliatemarketing.config.security;


import com.sparkminds.affiliatemarketing.entity.User;
import com.sparkminds.affiliatemarketing.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userCredential = userRepository.findByEmail(username);
    return userCredential.map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
  }
}
