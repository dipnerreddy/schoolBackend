package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.BusStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStudentRepository extends JpaRepository<BusStudent, Long> {
    // You can define custom query methods here if needed
}
