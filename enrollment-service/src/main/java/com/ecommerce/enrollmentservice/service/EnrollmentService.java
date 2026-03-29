package com.ecommerce.enrollmentservice.service;

import com.ecommerce.enrollmentservice.model.Enrollment;
import com.ecommerce.enrollmentservice.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(String id) {
        return enrollmentRepository.findById(id);
    }

    public List<Enrollment> getEnrollmentsByUserId(String userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    public List<Enrollment> getEnrollmentsByStatus(String status) {
        return enrollmentRepository.findByStatus(status);
    }

    public Enrollment createEnrollment(Enrollment enrollment) {
        enrollment.setEnrollmentDate(LocalDateTime.now());
        enrollment.setUpdatedAt(LocalDateTime.now());
        enrollment.setStatus("PENDING");

        // Calculate total amount from items
        if (enrollment.getItems() != null && !enrollment.getItems().isEmpty()) {
            BigDecimal total = enrollment.getItems().stream()
                    .map(item -> {
                        BigDecimal subtotal = item.getUnitPrice().multiply(
                                BigDecimal.valueOf(item.getQuantity()));
                        item.setSubtotal(subtotal);
                        return subtotal;
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            enrollment.setTotalAmount(total);
        }

        return enrollmentRepository.save(enrollment);
    }

    public Optional<Enrollment> updateEnrollmentStatus(String id, String status) {
        return enrollmentRepository.findById(id).map(existing -> {
            existing.setStatus(status);
            existing.setUpdatedAt(LocalDateTime.now());
            return enrollmentRepository.save(existing);
        });
    }

    public Optional<Enrollment> updateEnrollment(String id, Enrollment updatedEnrollment) {
        return enrollmentRepository.findById(id).map(existing -> {
            existing.setItems(updatedEnrollment.getItems());
            existing.setShippingAddress(updatedEnrollment.getShippingAddress());
            existing.setStatus(updatedEnrollment.getStatus());
            existing.setUpdatedAt(LocalDateTime.now());
            return enrollmentRepository.save(existing);
        });
    }

    public boolean deleteEnrollment(String id) {
        if (enrollmentRepository.existsById(id)) {
            enrollmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

