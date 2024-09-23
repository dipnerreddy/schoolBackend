package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.BusDetails;
import com.dipner.SMS.Entity.BusStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusStudentRepository extends JpaRepository<BusStudent, Long> {
    List<BusStudent> findByBusDetails(BusDetails busDetails);
}
