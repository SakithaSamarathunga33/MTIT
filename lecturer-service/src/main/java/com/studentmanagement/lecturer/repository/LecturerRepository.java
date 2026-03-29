package com.studentmanagement.lecturer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.studentmanagement.lecturer.model.Lecturer;

public interface LecturerRepository extends MongoRepository<Lecturer, String> {
}
