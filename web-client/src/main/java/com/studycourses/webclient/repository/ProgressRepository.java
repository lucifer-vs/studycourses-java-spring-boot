package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Progress;
import com.studycourses.webclient.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress,Long> {

    List<Progress> findByCourse(Course course);

    Progress findByStudentAndCourse(Student student,Course course);

    Progress findByStudent(Student student);

    void deleteAllByStudent(Student student);

    void deleteAllByCourse(Course course);

}
