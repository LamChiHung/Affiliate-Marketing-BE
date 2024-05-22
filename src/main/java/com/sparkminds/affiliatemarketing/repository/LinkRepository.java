package com.sparkminds.affiliatemarketing.repository;

import com.sparkminds.affiliatemarketing.entity.Link;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinkRepository extends JpaRepository<Link, Integer> {

  @Query("SELECT l FROM  Link as l WHERE l.productName LIKE :productName AND l.userId = :userId")
  Optional<Link> findByProductNameAndAndUserId(String productName, Integer userId);

  @Query("select l FROM Link as l WHERE  l.isDeleted = :isDeleted AND l.userId = :userId ORDER BY l.createdDate DESC , l.id DESC")
  Page<Link> findAllByIsDeletedAndUserId(boolean isDeleted, Integer userId, Pageable pageable);

  Optional<Link> findByIdAndUserId(Integer linkId, Integer userId);

}
