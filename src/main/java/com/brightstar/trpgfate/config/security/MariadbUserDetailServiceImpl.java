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

    private String authorityMapping(int role) {
        switch (role) {
            case com.brightstar.trpgfate.service.dto.User.ROLE_USER:
                return "USER";
            case com.brightstar.trpgfate.service.dto.User.ROLE_ADMIN:
                return "ADMIN";
            case com.brightstar.trpgfate.service.dto.User.ROLE_SUPERADMIN:
                return "SUPER_ADMIN";
            default:
                return "USER";
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int id;
        try {
            id = Integer.parseInt(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username format is incorrect", e);
        }
        User user = userDAO.getById(id);
        if (user == null) throw new UsernameNotFoundException("User not found");

        return new org.springframework.security.core.userdetails.User(
                username,
                String.valueOf(Hex.encode(user.getPasswdSha256Salted())),
                user.isActive(), true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(authorityMapping(user.getRole()))));
    }
}