package com.studycourses.webclient.service;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Teacher;

import java.util.List;

public interface TeacherService {
    List<Course> getListCourseByTeacher(String login);

    Teacher getTeacherByLogin(String login);

    Teacher findByIdTeacher(Long id_teacher);

    void saveTeacher(Teacher teacher);

    boolean isAdmin(Teacher teacher);

    List<Teacher> getAllTeachers();

    void dropTeacher(Long id_teacher);
}
