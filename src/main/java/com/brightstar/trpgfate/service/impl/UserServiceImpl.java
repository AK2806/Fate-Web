package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.config.security.SaltedSha256PasswordEncoder;
import com.brightstar.trpgfate.dao.UserDAO;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private SaltedSha256PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;

    @Override
    public boolean authenticate(User user, HttpSession session, int duration) {
        UsernamePasswordAuthenticationToken authReqToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        try {
            Authentication auth = authManager.authenticate(authReqToken);
            if (auth == null) return false;
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            session.setMaxInactiveInterval(duration);
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void register(User user, int role) throws EmailExistsException {
        if (userDAO.findByEmail(user.getEmail()) != null) throw new EmailExistsException("User already exists.");
        com.brightstar.trpgfate.dao.po.User po = new com.brightstar.trpgfate.dao.po.User();
        po.setEmail(user.getEmail());
        po.setPasswdSha256Salted(Hex.decode(passwordEncoder.encode(user.getPassword())));
        po.setRole(role);
        userDAO.insert(po);
    }
}
