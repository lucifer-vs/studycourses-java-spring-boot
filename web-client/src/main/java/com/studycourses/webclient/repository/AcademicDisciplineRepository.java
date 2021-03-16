package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.AcademicDiscipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicDisciplineRepository extends JpaRepository<AcademicDiscipline,Long> {
    AcademicDiscipline findByName(String name);
}
