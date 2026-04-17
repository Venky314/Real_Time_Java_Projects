package com.ashok.it.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ashok.it.usermanagement.entity.CityEntity;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    
    @Query("SELECT c FROM CityEntity c WHERE c.state.stateId = :stateId")
    List<CityEntity> findByStateId(@Param("stateId") Integer stateId);
}
