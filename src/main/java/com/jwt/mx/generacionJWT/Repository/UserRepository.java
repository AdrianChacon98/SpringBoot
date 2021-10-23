package com.jwt.mx.generacionJWT.Repository;

import com.jwt.mx.generacionJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {



    User save(User user);


    @Modifying
    @Query("UPDATE User u Set u.enabled=true WHERE u.id=?1")
    int setEnabledTrue(int id);

    @Query(name="SELECT * FROM User u WHERE u.email=:email")
    Optional<User> findByEmail(String email);





}
