package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Worker findByWorkerNumber(String workerNumber);
    Worker findByWorkerNameAndWorkerNumber(String name, String workerNumber);
}
