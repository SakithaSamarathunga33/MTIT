package com.ecommerce.enrollmentservice.repository;

import com.ecommerce.enrollmentservice.model.Enrollment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends MongoRepository<Enrollment, String> {

    List<Enrollment> findByUserId(String userId);

    List<Enrollment> findByStatus(String status);

    List<Enrollment> findByUserIdAndStatus(String userId, String status);
}

