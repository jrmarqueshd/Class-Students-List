package com.junior.courses;

import com.junior.courses.dto.CourseResponse;
import com.junior.enrollments.EnrollmentEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private String description;
    private Integer workload_hours;
    private Boolean active;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime created_at;

    @Column(name = "updated_at", insertable = true, updatable = true)
    private LocalDateTime updated_at;

    @OneToMany(mappedBy = "course")
    private List<EnrollmentEntity> enrollments;

    protected CourseEntity () {}

    public CourseEntity (
            String name,
            String code,
            String description,
            Integer workload_hours,
            Boolean active,
            LocalDateTime updated_at
    ) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.workload_hours = workload_hours;
        this.active = active;
        this.updated_at = updated_at;
    }

    public Long getId () {
        return id;
    }

    public CourseResponse get () {
        return new CourseResponse(
                this.id,
                this.name,
                this.code,
                this.description,
                this.workload_hours,
                this.active,
                this.created_at,
                this.updated_at
        );
    }

    public void update (
            String name,
            String code,
            String description,
            Integer workload_hours,
            Boolean active,
            LocalDateTime updated_at
    ) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.workload_hours = workload_hours;
        this.active = active;
        this.updated_at = updated_at;
    }
}