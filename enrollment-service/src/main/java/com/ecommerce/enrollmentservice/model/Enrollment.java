package com.ecommerce.enrollmentservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "enrollments")
public class Enrollment {

    @Id
    private String id;

    private String userId;
    private List<EnrollmentItem> items;
    private BigDecimal totalAmount;
    private String status; // PENDING, CONFIRMED, ACTIVE, COMPLETED, CANCELLED
    private String shippingAddress;
    private LocalDateTime enrollmentDate;
    private LocalDateTime updatedAt;
}

