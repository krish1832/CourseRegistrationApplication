package com.example.Course.Registration.App.Controller;

import com.example.Course.Registration.App.DTOs.ProfessorDTO;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Service.ProfessorService;
import com.example.Course.Registration.App.Util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Professor")
@PreAuthorize("hasRole('PROFESSOR')")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/details/{id}")
    public ResponseEntity<?> viewProfessorDetails(@PathVariable Long id,@RequestParam String token){
        String username = jwtUtil.extractUsername(token);
        Professor existingProfessor = professorService.getProfessorById(id);

        if (existingProfessor==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }


        // Check if the extracted username matches the student's username
        if (!existingProfessor.getEmail().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to view this Professor");
        }
        return ResponseEntity.ok(professorService.getProfessorById(id));
    }


    @PutMapping("/update-professor/{id}")

    public ResponseEntity<?> updateProfessorDetails(@PathVariable Long id, @RequestBody @Valid ProfessorDTO professorDTO, @RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        Professor existingProfessor = professorService.getProfessorById(id);

        if (existingProfessor==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }


        // Check if the extracted username matches the student's username
        if (!existingProfessor.getEmail().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to update this Professor");
        }
        Professor professor = professorService.mapToEntity(professorDTO);
        return ResponseEntity.ok(professorService.updateProfessorDetails(id,professor));
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> viewAssignedCourses(@PathVariable Long id,@RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        Professor existingProfessor = professorService.getProfessorById(id);

        if (existingProfessor==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found");
        }


        // Check if the extracted username matches the student's username
        if (!existingProfessor.getEmail().equals(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to view Requested Professor's Courses");
        }

        return ResponseEntity.ok(professorService.getAssignedCourses(id));
    }






}
