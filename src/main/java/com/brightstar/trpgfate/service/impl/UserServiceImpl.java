package com.brightstar.trpgfate.service.impl;

import com.brightstar.trpgfate.config.security.SaltedSha256PasswordEncoder;
import com.brightstar.trpgfate.dao.UserDAO;
import com.brightstar.trpgfate.service.UserService;
import com.brightstar.trpgfate.service.dto.User;
import com.brightstar.trpgfate.service.exception.EmailExistsException;
import com.brightstar.trpgfate.service.exception.EmailDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public boolean authenticate(User user) {
        return authenticateCurrReq(user);
    }

    @Override
    public boolean authenticate(User user, HttpSession session, int durationInSeconds) {
        if (authenticateCurrReq(user)) {
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
            session.setMaxInactiveInterval(durationInSeconds);
            return true;
        } else return false;
    }

    private boolean authenticateCurrReq(User user) {
        try {
            UsernamePasswordAuthenticationToken authReqToken
                    = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication auth = authManager.authenticate(authReqToken);
            if (auth == null) return false;
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public void register(User user, int role) throws EmailExistsException {
        if (userDAO.findByEmail(user.getEmail()) != null) throw new EmailExistsException();
        com.brightstar.trpgfate.dao.po.User po = new com.brightstar.trpgfate.dao.po.User();
        po.setEmail(user.getEmail());
        po.setPasswdSha256Salted(Hex.decode(passwordEncoder.encode(user.getPassword())));
        po.setRole(role);
        userDAO.insert(po);
    }

    @Override
    public void modifyPassword(User user) throws EmailDoesntExistException {
        com.brightstar.trpgfate.dao.po.User po = userDAO.findByEmail(user.getEmail());
        if (po == null) throw new EmailDoesntExistException();
        po.setPasswdSha256Salted(Hex.decode(passwordEncoder.encode(user.getPassword())));
        userDAO.updatePasswd(po);
    }

    @Override
    public boolean userExists(String email) {
        return userDAO.findByEmail(email) != null;
    }
}
