package com.ashok.it.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ashok.it.usermanagement.entity.StateEntity;

@Repository
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
    
    @Query("SELECT s FROM StateEntity s WHERE s.country.countryId = :countryId")
    List<StateEntity> findByCountryId(@Param("countryId") Integer countryId);
}
