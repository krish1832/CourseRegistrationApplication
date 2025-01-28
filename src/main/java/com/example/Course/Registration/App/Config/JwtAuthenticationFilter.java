package com.example.Course.Registration.App.Config;

import com.example.Course.Registration.App.Entity.Admin;
import com.example.Course.Registration.App.Entity.Professor;
import com.example.Course.Registration.App.Entity.Student;
import com.example.Course.Registration.App.Repository.AdminRepository;
import com.example.Course.Registration.App.Repository.ProfessorRepository;
import com.example.Course.Registration.App.Repository.StudentRepository;
import com.example.Course.Registration.App.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                String username = jwtUtil.extractUsername(token);
                System.out.println("Username is"+username);

                if (username != null && jwtUtil.validateToken(token, username)) {
                    // Fetch the user details from one of the three repositories
                    Optional<Admin> admin = adminRepository.findByEmail(username);
                    Optional<Student> student = studentRepository.findByEmail(username);
                    Optional<Professor> professor = professorRepository.findByEmail(username);

                    String role = null;

                    if (admin.isPresent()) {
                        role = "ADMIN";
                    } else if (student.isPresent()) {
                        role = "STUDENT";
                    } else if (professor.isPresent()) {
                        role = "PROFESSOR";
                    }

                    System.out.println("role is"+role);

                    if (role != null) {
                        // Create authorities based on the role
                        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                        System.out.println("Authorities "+authorities);
                        // Set authentication
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                username,
                                null,  // No credentials needed for JWT
                                authorities
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                System.out.println("JWT validation failed: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
