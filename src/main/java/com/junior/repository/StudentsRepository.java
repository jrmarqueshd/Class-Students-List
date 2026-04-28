package com.junior.repository;

import com.junior.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentsRepository extends JpaRepository<StudentEntity, Long>{
}
