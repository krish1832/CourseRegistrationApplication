package com.example.Course.Registration.App.DTOs;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Schema
public class CourseDTO {

    @NotNull(message = "Course name is mandatory.")
    private String courseName;

    @Schema(example = "1")
    @Min(value = 1, message = "Capacity must be at least 1.")
    private int capacity;

    @Schema(example = "0")
    @Min(value = 0, message = "Remaining seats cannot be negative.")
    private int remainingSeats;


    @NotNull(message = "Professor ID is mandatory.")
    private Long professorId;

    @NotNull(message = "Semester ID is mandatory.")
    private String semesterName;

    @Schema(example = "4")
    @Min(value = 1, message = "Course credit must be at least 1.")
    private int courseCredit;


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public int getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(int courseCredit) {
        this.courseCredit = courseCredit;
    }
}
