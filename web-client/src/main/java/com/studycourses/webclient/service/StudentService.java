package com.studycourses.webclient.service;


import com.studycourses.webclient.model.*;

import java.util.List;

public interface StudentService {

    Student getStudentByLogin(String login);

    List<Course> getGroupCoursesBy(String login);

    List<TaskPerformance> getTaskPerformanceByStudentAndCourse(Student student, Course course);

    void saveStudent(Student student);

    List<Student> getStudentByGroup(StudyGroup studyGroup);

    List<Student> getAllStudents();

    Student getById(Long id_student);

    void dropStudent(Long id_student);
}
