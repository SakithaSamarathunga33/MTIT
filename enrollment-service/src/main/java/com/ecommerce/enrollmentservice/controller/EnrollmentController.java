package com.ecommerce.enrollmentservice.controller;

import com.ecommerce.enrollmentservice.model.Enrollment;
import com.ecommerce.enrollmentservice.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment API", description = "CRUD operations for Enrollments")
@CrossOrigin(origins = "*")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Operation(summary = "Get all enrollments")
    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentService.getAllEnrollments());
    }

    @Operation(summary = "Get an enrollment by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Enrollment found"),
        @ApiResponse(responseCode = "404", description = "Enrollment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable("id") String id) {
        return enrollmentService.getEnrollmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get enrollments by User ID")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByUserId(userId));
    }

    @Operation(summary = "Get enrollments by Status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStatus(status));
    }

    @Operation(summary = "Create a new enrollment")
    @ApiResponse(responseCode = "201", description = "Enrollment created successfully")
    @PostMapping
    public ResponseEntity<Enrollment> createEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment created = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update enrollment status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Enrollment> updateEnrollmentStatus(
            @PathVariable("id") String id,
            @RequestBody Map<String, String> statusRequest) {
        String status = statusRequest.get("status");
        return enrollmentService.updateEnrollmentStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an enrollment")
    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable("id") String id, @RequestBody Enrollment enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete an enrollment")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") String id) {
        if (enrollmentService.deleteEnrollment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

