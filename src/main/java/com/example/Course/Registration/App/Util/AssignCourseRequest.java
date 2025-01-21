package com.example.Course.Registration.App.Util;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
@Data
@Entity

public class AssignCourseRequest {

    @Id
    private Long studentId;
    private List<Long> courseIds;


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public void setCourseIds(List<Long> courseIds) {
        this.courseIds = courseIds;
    }

    public List<Long> getCourseIds(){
        return courseIds;
    }
}
