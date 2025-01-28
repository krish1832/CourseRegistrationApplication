package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.DTOs.ProfessorDTO;
import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Professor")
@PreAuthorize("hasRole('PROFESSOR')")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/details/{id}")
    public ResponseEntity<Professor> viewProfessorDetails(@PathVariable Long id){
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }


    @PutMapping("/update-professor/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Professor> updateProfessorDetails(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO) {
        Professor professor= professorService.mapToEntity(professorDTO);
        return ResponseEntity.ok(professorService.updateProfessorDetails(id, professor));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<List<Course>> viewAssignedCourses(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getAssignedCourses(id));
    }






}
