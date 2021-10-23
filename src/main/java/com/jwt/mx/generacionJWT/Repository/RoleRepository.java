package com.jwt.mx.generacionJWT.Repository;

import com.jwt.mx.generacionJWT.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role save(Role role);



}
