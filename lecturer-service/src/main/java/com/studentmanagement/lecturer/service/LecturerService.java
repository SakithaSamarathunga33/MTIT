package com.studentmanagement.lecturer.service;

import com.studentmanagement.lecturer.model.Lecturer;
import com.studentmanagement.lecturer.repository.LecturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    public Optional<Lecturer> getLecturerById(String id) {
        return lecturerRepository.findById(id);
    }

    public Lecturer createLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public Lecturer updateLecturer(String id, Lecturer lecturerDetails) {
        return lecturerRepository.findById(id).map(lecturer -> {
            lecturer.setName(lecturerDetails.getName());
            lecturer.setEmail(lecturerDetails.getEmail());
            lecturer.setDepartment(lecturerDetails.getDepartment());
            return lecturerRepository.save(lecturer);
        }).orElseThrow(() -> new RuntimeException("Lecturer not found with id " + id));
    }

    public void deleteLecturer(String id) {
        lecturerRepository.deleteById(id);
    }
}
