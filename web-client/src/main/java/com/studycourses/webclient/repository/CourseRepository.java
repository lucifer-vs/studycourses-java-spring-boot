package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.AcademicDiscipline;
import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByTeacher(Teacher teacher);
    Course findByName(String name);
    boolean existsByTeacher(Teacher teacher);
    boolean existsByAcademicDiscipline(AcademicDiscipline academicDiscipline);

}
