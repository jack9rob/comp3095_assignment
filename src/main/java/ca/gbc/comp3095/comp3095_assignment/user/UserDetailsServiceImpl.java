/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: class logins verifies and logins in users from the database
 */

package ca.gbc.comp3095.comp3095_assignment.user;

import ca.gbc.comp3095.comp3095_assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        Set< GrantedAuthority > grantedAuthorities = new HashSet< >();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                grantedAuthorities);
    }
}
