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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inject PasswordEncoder

    @PostMapping
    public JwtResponse login(@RequestBody JwtRequest request) {
        // Authenticate the user
        authenticateUser(request.getEmail(), request.getPassword());

        // Fetch the role based on the email
        String role = getRoleByEmail(request.getEmail());
        System.out.println("Fetch Role"+role);

        // Generate the JWT token with the role as a list
        String token = jwtUtil.generateToken(request.getEmail(), Arrays.asList(role));

        // Return the token and email (username) in the response
        return new JwtResponse(token, request.getEmail());
    }

    private void authenticateUser(String email, String password) {
        // Try to find the user in all three repositories
        Optional<Admin> admin = adminRepository.findByEmail(email);
        Optional<Student> student = studentRepository.findByEmail(email);
        Optional<Professor> professor = professorRepository.findByEmail(email);

        // Authenticate based on where the user is found
        if (admin.isPresent()) {
            if (passwordEncoder.matches(password, admin.get().getPassword())) {  // Use matches to compare encoded password
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
            } else {
                throw new BadCredentialsException("Invalid password for Admin");
            }
        } else if (student.isPresent()) {
            System.out.println("Student is Present");
            if (passwordEncoder.matches(password, student.get().getPassword())) {  // Use matches to compare encoded password
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
            } else {
                throw new BadCredentialsException("Invalid password for Student");
            }
        } else if (professor.isPresent()) {
            if (passwordEncoder.matches(password, professor.get().getPassword())) {  // Use matches to compare encoded password
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password)
                );
            } else {
                throw new BadCredentialsException("Invalid password for Professor");
            }
        } else {
            throw new BadCredentialsException("User not found");
        }
    }

    private String getRoleByEmail(String email) {
        // Check each repository to determine the role of the user based on email
        Optional<Admin> admin = adminRepository.findByEmail(email);
        Optional<Student> student = studentRepository.findByEmail(email);
        Optional<Professor> professor = professorRepository.findByEmail(email);

        if (admin.isPresent()) {
            return "ADMIN"; // Return "ADMIN" if the user is found in the Admin repository
        } else if (student.isPresent()) {
            return "STUDENT"; // Return "STUDENT" if the user is found in the Student repository
        } else if (professor.isPresent()) {
            return "PROFESSOR"; // Return "PROFESSOR" if the user is found in the Professor repository
        } else {
            // Throw exception if no user is found with the given email
            throw new RuntimeException("User not found");
        }
    }
}
