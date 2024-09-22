package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.SchoolClass;
import com.dipner.SMS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findBySchoolClass(SchoolClass schoolClass);
}

