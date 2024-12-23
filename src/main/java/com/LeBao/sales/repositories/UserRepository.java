package com.LeBao.sales.repositories;

import com.LeBao.sales.DTO.PersonalInfoDTO;
import com.LeBao.sales.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select new com.LeBao.sales.DTO.PersonalInfoDTO(concat(u.firstName,' ',u.lastName), u.email,  " +
            "(select count(o) from Order o where o.user.userId = u.userId and o.orderStatus = 'DELIVERING' group by o.user.userId)) " +
            "from User u where u.email = :email")
    PersonalInfoDTO getPersonalInfo(@Param("email") String email);
}
