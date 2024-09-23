package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.BusDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusDetailsRepository extends JpaRepository<BusDetails, Long> {
    BusDetails findByBusNumber(String busNumber);
}
