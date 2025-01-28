package com.example.Course.Registration.App.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String CourseName;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int remainingSeats;

    @Column(nullable = false)
    private int CourseCredit;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "semester_id", nullable = true)
    @JsonBackReference
    private Semester semester;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "professor_id") // Foreign key column in the course table
    private Professor professor;




    //Getter and Setter


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }



    public void setProfessor(Professor professor) {
         this.professor = professor;
    }

    public Professor getProfessor(){
        return professor;
    }

    public int getCourseCredit() {
        return CourseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        CourseCredit = courseCredit;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
}
