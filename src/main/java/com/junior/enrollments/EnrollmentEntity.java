package com.junior.enrollments;

import com.junior.courses.CourseEntity;
import com.junior.student.StudentEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @Column(insertable = false, updatable = false)
    private LocalDateTime enrolled_at;

    public Long getEnrollmentId () {
        return id;
    }

    protected EnrollmentEntity () {}

    public EnrollmentEntity (
            StudentEntity student,
            CourseEntity course,
            Boolean status
    ) {
        this.student = student;
        this.course = course;
        this.status = status;
    }
}
