package com.stefanini.springboot.app.models.service;

import com.stefanini.springboot.app.models.dao.IUserDao;
import com.stefanini.springboot.app.models.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(" ")
public class JpaUserDetailsService implements UserDetailsService{

    @Autowired
    private IUserDao personaDao;

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = personaDao.findByUsername(username);

        if(user == null) {
            logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getPermits()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
    }

}
