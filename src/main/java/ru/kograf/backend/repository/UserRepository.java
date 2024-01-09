package ru.kograf.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kograf.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByPhone(String phone);

    @Query("FROM User u WHERE u.email = :email AND u.status = 'ACTIVE'")
    User findByEmailWithStatusActive(@Param("email") String email);

    @Query("FROM User u WHERE u.email = :email AND u.status = 'BANNED'")
    User findByEmailWithStatusBanned(@Param("email") String email);

    @Query("FROM User u WHERE u.email = :phone AND u.status = 'ACTIVE'")
    User findByPhoneWithStatusActive(@Param("phone") String phone);

    @Query("SELECT case when count(u)>0 then TRUE else FALSE end FROM User u "
            + "WHERE u.email = :email AND (u.status = 'ACTIVE' or u.status = 'CONFIRMATION')")
    Boolean existUserByEmail(@Param("email") String email);

    @Query("SELECT case when count(u)>0 then TRUE else FALSE end FROM User u "
            + "WHERE u.phone = :phone AND u.status = 'ACTIVE'")
    Boolean existUserByPhone(@Param("phone") String phone);

    @Query("FROM User u WHERE u.status = 'ACTIVE'")
    List<User> findAllActiveUsers();

    @Query("FROM User u WHERE u.role = 'ADMIN'")
    List<User> findAllAdmins();
}
