package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.Entity.Course;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Professor")
//@PreAuthorize("hasRole('PROFESSOR')")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/{id}")
    public ResponseEntity<Professor> viewProfessorDetails(@PathVariable Long id){
        return ResponseEntity.ok(professorService.getProfessorDetails(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessorDetails(@PathVariable Long id, @RequestBody Professor updatedDetails) {
        return ResponseEntity.ok(professorService.updateProfessorDetails(id, updatedDetails));
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> viewAssignedCourses(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.getAssignedCourses(id));
    }






}
