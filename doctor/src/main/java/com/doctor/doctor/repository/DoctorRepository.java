package com.doctor.doctor.repository;

import com.doctor.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Additional custom queries can be defined here if needed
    // ...
}