package com.brightstar.trpgfate.config.security;

import com.brightstar.trpgfate.dao.UserDAO;
import com.brightstar.trpgfate.dao.po.User;
import com.brightstar.trpgfate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public final class MariadbUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                username,
                String.valueOf(Hex.encode(user.getPasswdSha256Salted())),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole() == UserService.ROLE_ADMIN ? "ADMIN" : "USER")));
    }
}