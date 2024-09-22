package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    SchoolClass findByClassName(String className);
}
