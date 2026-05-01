package com.junior.student;

import com.junior.courses.CourseEntity;
import com.junior.enrollments.EnrollmentEntity;
import jakarta.persistence.*;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(name = "birth_date")
    private LocalDate birth_date;

    @Column(name  = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;

    protected StudentEntity () {
    }

    public StudentEntity (String name, String email, LocalDate birth_date) {
        this.name = name;
        this.email = email;
        this.birth_date = birth_date;
    }

    public Long getId () {
        return id;
    }

    public String getName () { return name; }

    public String getEmail () { return email; }

    public LocalDate getBirthDate () { return birth_date; }

    public LocalDateTime getCreatedAt () { return createdAt; }

    public void update (String name, String email, LocalDate birth_date) {
        this.name = name;
        this.email = email;
        this.birth_date = birth_date;
    }

    public List<EnrollmentEntity> getEnrollments () {
        return enrollments;
    }
}
