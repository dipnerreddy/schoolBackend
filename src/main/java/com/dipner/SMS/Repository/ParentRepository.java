package com.dipner.SMS.Repository;

import com.dipner.SMS.Entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParentRepository extends JpaRepository<Parent, Long> {
    Parent findByParentName(String parentName);
    Parent findByPhoneNumber(String phoneNumber);  // Add this method

}
