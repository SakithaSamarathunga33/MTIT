package com.studentmanagement.lecturer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "lecturers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {
    @Id
    private String id;
    private String name;
    private String email;
    private String department;
}
