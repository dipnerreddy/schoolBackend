package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.BusStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStudentRepository extends JpaRepository<BusStudent, Long> {
    BusStudent findByStudentNameAndMobileNumber(String studentName, String mobileNumber);
    List<BusStudent> findByBusNumber(String busNumber);
}
