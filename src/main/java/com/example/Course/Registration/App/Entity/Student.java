package com.example.Course.Registration.App.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Student {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String name;

     @Column(unique = true,nullable = false)
     private String email;

     @Column(nullable = false)
     private String password;

    @ManyToMany
     @JoinTable(
             name = "student_courses",
             joinColumns = @JoinColumn(name="student_id"),
             inverseJoinColumns = @JoinColumn(name = "course_id")
     )
     private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
