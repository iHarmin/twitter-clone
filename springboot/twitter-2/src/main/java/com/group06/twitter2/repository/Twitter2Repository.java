package com.group06.twitter2.repository;

import com.group06.twitter2.model.Twitter2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface Twitter2Repository extends JpaRepository<Twitter2, Integer> {
}
