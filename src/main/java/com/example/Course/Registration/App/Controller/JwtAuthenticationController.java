package com.example.Course.Registration.App.Controller;


import com.example.Course.Registration.App.Entity.Admin;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.AdminRepository;
import com.example.Course.Registration.App.Repository.ProfessorRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import com.example.Course.Registration.App.Util.JwtRequest;
import com.example.Course.Registration.App.Util.JwtResponse;
import com.example.Course.Registration.App.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-login")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping
    public JwtResponse login(@RequestBody JwtRequest request) {
        authenticateUser(request.getEmail(), request.getPassword());
        String role = getRoleByEmail(request.getEmail());
        String token = jwtUtil.generateToken(request.getEmail(), role);
        return new JwtResponse(token,role);
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }

    private String getRoleByEmail(String email) {
        if (adminRepository.findByEmail(email).isPresent()) {
            return "ADMIN";
        } else if (studentRepository.findByEmail(email).isPresent()) {
            return "STUDENT";
        } else if (professorRepository.findByEmail(email).isPresent()) {
            return "PROFESSOR";
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
