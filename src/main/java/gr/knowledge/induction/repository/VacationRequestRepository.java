package gr.knowledge.induction.repository;

import gr.knowledge.induction.domain.Employee;
import gr.knowledge.induction.domain.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    @Query("SELECT COUNT(vr) FROM VacationRequest vr WHERE employee.id =: employeeId AND status IN (:statuses) AND startDate <=: endDate AND endDate >=: startDate" )
    Integer countOfOverlappingRequests(@Param("employeeId") Long employeeId,
                                       @Param("statuses") List VacationStatus,
                                       @Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate);



}
