package com.booking.appointment.service;


import com.booking.appointment.entity.Appointment;
import com.booking.appointment.repository.AppointmentRepository;
import com.doctor.doctor.entity.Doctor;
import com.patient.entity.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final RestTemplate restTemplate;


    public AppointmentService(AppointmentRepository appointmentRepository, RestTemplate restTemplate) {
        this.appointmentRepository = appointmentRepository;
        this.restTemplate = restTemplate;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found with id: " + id));
    }

    public Appointment createAppointment(Appointment appointment) {
        validatePatientAndDoctor(appointment);
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        validatePatientAndDoctor(appointmentDetails);

        Appointment existingAppointment = getAppointmentById(id);
        existingAppointment.setPatientId(appointmentDetails.getPatientId());
        existingAppointment.setDoctorId(appointmentDetails.getDoctorId());
        existingAppointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        // Update other fields as needed

        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = getAppointmentById(id);
        appointmentRepository.delete(appointment);
    }

    private void validatePatientAndDoctor(Appointment appointment) {
        Long patientId = appointment.getPatientId();
        Long doctorId = appointment.getDoctorId();

        ResponseEntity<Patient> patientResponse = restTemplate.getForEntity
                ("http://localhost:1000/patients/"+appointment.getPatientId(), Patient.class, patientId);
        if (!patientResponse.getStatusCode().is2xxSuccessful() || patientResponse.getBody() == null) {
            throw new NotFoundException("Patient not found with id: " + patientId);
        }

        ResponseEntity<Doctor> doctorResponse = restTemplate.getForEntity
                ("http://localhost:1001/doctors/"+appointment.getDoctorId(), Doctor.class, doctorId);
        if (!doctorResponse.getStatusCode().is2xxSuccessful() || doctorResponse.getBody() == null) {
            throw new NotFoundException("Doctor not found with id: " + doctorId);
        }
    }
}

