package com.ashok.it.counsellorsportal.Repository;

import com.ashok.it.counsellorsportal.Model.Counsellor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
    
    Optional<Counsellor> findByEmailAndPwd(String email, String pwd);
    
    boolean existsByEmail(String email);
}
