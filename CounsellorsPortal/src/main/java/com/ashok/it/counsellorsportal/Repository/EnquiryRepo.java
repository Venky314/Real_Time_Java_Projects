package com.ashok.it.counsellorsportal.Repository;

import com.ashok.it.counsellorsportal.Model.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {
    
    List<Enquiry> findByCounsellorId(Integer counsellorId);
    
    @Query("SELECT e FROM Enquiry e WHERE e.counsellorId = :counsellorId " +
           "AND (:courseId IS NULL OR e.courseId = :courseId) " +
           "AND (:enqStatus IS NULL OR e.enqStatus = :enqStatus) " +
           "AND (:classMode IS NULL OR e.classMode = :classMode)")
    List<Enquiry> findByFilters(@Param("counsellorId") Integer counsellorId,
                                @Param("courseId") Integer courseId,
                                @Param("enqStatus") String enqStatus,
                                @Param("classMode") String classMode);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.counsellorId = :counsellorId")
    Long countTotalEnquiries(@Param("counsellorId") Integer counsellorId);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.counsellorId = :counsellorId AND e.enqStatus = 'New'")
    Long countOpenEnquiries(@Param("counsellorId") Integer counsellorId);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.counsellorId = :counsellorId AND e.enqStatus = 'Enrolled'")
    Long countEnrolledEnquiries(@Param("counsellorId") Integer counsellorId);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.counsellorId = :counsellorId AND e.enqStatus = 'Lost'")
    Long countLostEnquiries(@Param("counsellorId") Integer counsellorId);
}
