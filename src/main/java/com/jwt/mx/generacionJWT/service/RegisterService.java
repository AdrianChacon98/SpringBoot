package com.jwt.mx.generacionJWT.service;

import com.jwt.mx.generacionJWT.Email.EmailServiceImp;
import com.jwt.mx.generacionJWT.Repository.AuthorityRepository;
import com.jwt.mx.generacionJWT.Repository.RoleRepository;
import com.jwt.mx.generacionJWT.Repository.UserRepository;
import com.jwt.mx.generacionJWT.model.Authority;
import com.jwt.mx.generacionJWT.model.Role;
import com.jwt.mx.generacionJWT.model.User;
import com.jwt.mx.generacionJWT.model.UserModel.UserModel;
import com.jwt.mx.generacionJWT.permisos.Authorities;
import com.sun.org.apache.xerces.internal.impl.xs.util.LSInputListImpl;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.jwt.mx.generacionJWT.permisos.Role.USER;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class RegisterService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    private final String USER_NOT_FOUND="User not found";

    private final Logger LOGGER = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private EmailServiceImp emailServiceImp;


    public RegisterService(UserRepository userRepository, RoleRepository roleRepository,AuthorityRepository authorityRepository,BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.authorityRepository=authorityRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }


    @Async
    @Transactional
    public String register(UserModel userModel)throws Exception
    {

        //comparar que el email sea correcto
        boolean isEmail=isValid(userModel.getEmail());


        if(isEmail==false)
            return "El correo no es correcto";

        //buscar si existe un usuario con ese email
        boolean exist = userRepository.findByEmail(userModel.getEmail()).isPresent();

        LOGGER.info(""+exist);


        if(exist){
            return "El usuario ya existe";
        }else{

            User newUser = new User();

            newUser.setName(userModel.getName());
            newUser.setLastname(userModel.getLastname());
            newUser.setEmail(userModel.getEmail());
            newUser.setPassword(generateHash(userModel.getPassword()));
            newUser.setLocked(true);
            newUser.setEnabled(true);
            newUser.setTimeCreated(LocalDateTime.now());




            Collection<GrantedAuthority> collection = USER.getAuthorities()
                    .stream()
                    .map(item-> new SimpleGrantedAuthority(item.name()))
                    .collect(Collectors.toList());


            userRepository.save(newUser);

            Role role = new Role();

            role.setRole(USER.name());
            role.setRoleUser(newUser);

            roleRepository.save(role);

            for ( GrantedAuthority list : collection){
                Authority authority = new Authority();

                authority.setAuthority(list.toString());
                authority.setUser(newUser);

                authorityRepository.save(authority);
            }


            return "Registro exitoso";

        }

    }

    public boolean isValid(String email){


        Pattern pattern = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$");

        Matcher matcher = pattern.matcher(email);



        if(matcher.find())
            return true;
        else{
            return false;
        }

    }




    private String generateHash(String password){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2d);
        return argon2.hash(1,1024*1,2, password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }




}
