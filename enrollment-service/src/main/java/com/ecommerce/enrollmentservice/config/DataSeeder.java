package com.ecommerce.enrollmentservice.config;

import com.ecommerce.enrollmentservice.model.Enrollment;
import com.ecommerce.enrollmentservice.model.EnrollmentItem;
import com.ecommerce.enrollmentservice.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final EnrollmentRepository enrollmentRepository;

    @Override
    public void run(String... args) {
        log.info("Dropping existing enrollment data and re-seeding...");
        enrollmentRepository.deleteAll();

        // Enrollment 1 - Kasun enrolled in CS and DBMS - COMPLETED
        EnrollmentItem item1 = new EnrollmentItem("course-cs-001", "Introduction to Computer Science", 1,
                new BigDecimal("15000.00"), new BigDecimal("15000.00"));
        EnrollmentItem item2 = new EnrollmentItem("course-dbms-002", "Database Management Systems", 1,
                new BigDecimal("18000.00"), new BigDecimal("18000.00"));
        Enrollment enrollment1 = new Enrollment(null, "student-kasun-001", List.of(item1, item2),
                new BigDecimal("33000.00"), "COMPLETED",
                "SLIIT Malabe Campus",
                LocalDateTime.now().minusDays(30), LocalDateTime.now().minusDays(20));

        // Enrollment 2 - Nimali enrolled in SE - ACTIVE
        EnrollmentItem item3 = new EnrollmentItem("course-se-003", "Software Engineering", 1,
                new BigDecimal("20000.00"), new BigDecimal("20000.00"));
        Enrollment enrollment2 = new Enrollment(null, "student-nimali-002", List.of(item3),
                new BigDecimal("20000.00"), "ACTIVE",
                "SLIIT Kandy Campus",
                LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(5));

        // Enrollment 3 - Sunil enrolled in AI - PENDING
        EnrollmentItem item4 = new EnrollmentItem("course-ai-004", "Artificial Intelligence", 1,
                new BigDecimal("22000.00"), new BigDecimal("22000.00"));
        Enrollment enrollment3 = new Enrollment(null, "student-sunil-003", List.of(item4),
                new BigDecimal("22000.00"), "PENDING",
                "SLIIT Malabe Campus",
                LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(5));

        // Enrollment 4 - Piyumi enrolled in Web Tech - CONFIRMED
        EnrollmentItem item5 = new EnrollmentItem("course-wt-005", "Web Technologies", 1,
                new BigDecimal("16000.00"), new BigDecimal("16000.00"));
        Enrollment enrollment4 = new Enrollment(null, "student-piyumi-004", List.of(item5),
                new BigDecimal("16000.00"), "CONFIRMED",
                "SLIIT Malabe Campus",
                LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1));

        enrollmentRepository.saveAll(List.of(enrollment1, enrollment2, enrollment3, enrollment4));
        log.info("Enrollment data seeded successfully! 4 enrollments added.");
    }
}

