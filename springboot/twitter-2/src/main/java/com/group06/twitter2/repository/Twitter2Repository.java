package com.group06.twitter2.repository;

import com.group06.twitter2.model.Twitter2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface Twitter2Repository extends JpaRepository<Twitter2, Integer> {

    Twitter2 findByEmail(String email);

    @Query("SELECT t FROM Twitter2 t WHERE " +
       "LOWER(t.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
       "LOWER(t.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
       "LOWER(t.personalInterests) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Twitter2> searchByUserNameOrEmailOrInterests(@Param("searchTerm") String searchTerm);
}
