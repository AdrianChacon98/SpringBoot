package com.jwt.mx.generacionJWT.Repository;

import com.jwt.mx.generacionJWT.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AuthorityRepository extends JpaRepository<Authority,Integer> {


}
