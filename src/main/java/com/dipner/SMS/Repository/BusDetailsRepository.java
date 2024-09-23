package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.BusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusDetailsRepository extends JpaRepository<BusDetails, Long> {
    // You can define custom query methods here if needed
}
