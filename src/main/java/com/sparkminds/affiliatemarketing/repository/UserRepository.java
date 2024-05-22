package com.sparkminds.affiliatemarketing.repository;

import com.sparkminds.affiliatemarketing.entity.User;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u FROM User AS u WHERE u.email LIKE :email")
  Optional<User> findByEmail(String email);

  boolean existsByRefferalCode(String refferalCode);

  Optional<User> findByRefferalCode(String refferalCode);

  @Query("select u.id FROM User as u WHERE u.refferalCode LIKE :refferalCode")
  Optional<Integer> findIdByRefferalCode(String refferalCode);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<User> findWithLockingById(Integer id);

}
