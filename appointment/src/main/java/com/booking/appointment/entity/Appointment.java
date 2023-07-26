package com.booking.appointment.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
// Appointment entity class
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;

}