package com.studentmanagement.lecturer.controller;

import com.studentmanagement.lecturer.model.Lecturer;
import com.studentmanagement.lecturer.service.LecturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecture")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LecturerController {

    private final LecturerService lecturerService;

    @GetMapping
    public List<Lecturer> getAllLecturers() {
        return lecturerService.getAllLecturers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lecturer> getLecturerById(@PathVariable String id) {
        return lecturerService.getLecturerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Lecturer createLecturer(@RequestBody Lecturer lecturer) {
        return lecturerService.createLecturer(lecturer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecturer> updateLecturer(@PathVariable String id, @RequestBody Lecturer lecturer) {
        try {
            return ResponseEntity.ok(lecturerService.updateLecturer(id, lecturer));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable String id) {
        if (lecturerService.deleteLecturer(id)) {
            return ResponseEntity.ok("Deleted successfully. Lecturer with id '" + id + "' was removed.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Lecturer with id '" + id + "' was not found.");
    }
}
