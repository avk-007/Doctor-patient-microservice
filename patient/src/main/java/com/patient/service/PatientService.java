package com.patient.service;

import com.patient.entity.Patient;
import com.patient.exception.NotFoundException;
import com.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient patientDetails) {
        Patient existingPatient = getPatientById(id);
        existingPatient.setName(patientDetails.getName());
        existingPatient.setAge(patientDetails.getAge());
        // Update other fields as needed
        return patientRepository.save(existingPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }
}
