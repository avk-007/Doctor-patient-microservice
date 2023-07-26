package com.booking.appointment.repository;

import com.booking.appointment.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Additional custom queries can be defined here if needed
    // ...
}