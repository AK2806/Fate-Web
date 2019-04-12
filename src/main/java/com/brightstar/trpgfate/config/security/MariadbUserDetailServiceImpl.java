package com.brightstar.trpgfate.config.security;

import com.brightstar.trpgfate.dao.UserDAO;
import com.brightstar.trpgfate.dao.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class MariadbUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    private List<String> authorityMapping(int role) {
        ArrayList<String> authorities = new ArrayList<>();
        switch (role) {
            case com.brightstar.trpgfate.service.dto.User.ROLE_SUPERADMIN:
                authorities.add("SUPER_ADMIN");
            case com.brightstar.trpgfate.service.dto.User.ROLE_ADMIN:
                authorities.add("ADMIN");
            case com.brightstar.trpgfate.service.dto.User.ROLE_USER:
                authorities.add("USER");
            default:
        }
        return authorities;
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

        List<String> authorityNames = authorityMapping(user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String name : authorityNames) {
            authorities.add(new SimpleGrantedAuthority(name));
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                String.valueOf(Hex.encode(user.getPasswdSha256Salted())),
                user.isActive(),
                true, true, true,
                authorities);
    }
}