CREATE TABLE enrollments (
    id BIGSERIAL PRIMARY KEY,
    status BOOLEAN NOT NULL DEFAULT TRUE,

    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,

    enrolled_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_enrollments_student
        FOREIGN KEY (student_id)
        REFERENCES students(id),

    CONSTRAINT fk_enrollments_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id),

    CONSTRAINT uk_enrollment_student_course
        UNIQUE (student_id, course_id)
);