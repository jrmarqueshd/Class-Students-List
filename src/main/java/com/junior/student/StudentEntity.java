package com.junior.student;

import com.junior.enrollments.EnrollmentEntity;
import com.junior.student.dto.StudentResponse;
import jakarta.persistence.*;

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

    public void updateStudentData (String name, String email, LocalDate birth_date) {
        this.name = name;
        this.email = email;
        this.birth_date = birth_date;
    }

    public StudentResponse getStudentData () {
        return new StudentResponse(
                this.id,
                this.name,
                this.email,
                this.birth_date,
                this.createdAt
        );
    }

    public List<EnrollmentEntity> getEnrollments () {
        return enrollments;
    }
}
