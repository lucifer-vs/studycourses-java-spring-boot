package com.studycourses.webclient.service.impl;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Role;
import com.studycourses.webclient.model.Teacher;
import com.studycourses.webclient.repository.CourseRepository;
import com.studycourses.webclient.repository.RoleRepository;
import com.studycourses.webclient.repository.TeacherRepository;
import com.studycourses.webclient.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Course> getListCourseByTeacher(String login) {
        return courseRepository.findByTeacher(teacherRepository.findByLogin(login));
    }

    @Override
    public Teacher getTeacherByLogin(String login) {
        return teacherRepository.findByLogin(login);
    }

    @Override
    public Teacher findByIdTeacher(Long id_teacher) {
        return teacherRepository.findById(id_teacher).get();
    }

    @Override
    public void saveTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Override
    public boolean isAdmin(Teacher teacher) {
        return teacher.getRole().getName().equals("ADMIN");
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll(Sort.by("surname","firstname"));
    }

    public Teacher getTeacherById(Long id_teacher){
        return teacherRepository.findById(id_teacher).get();
    }

    public void saveTeacher(Long id_teacher, Teacher teach){
        Teacher teacher=this.findByIdTeacher(id_teacher);

        teacher.setSurname(teach.getSurname());
        teacher.setFirstname(teach.getFirstname());
        teacher.setMiddlename(teach.getMiddlename());
        teacher.setQualification(teach.getQualification());
        teacher.setEmail(teach.getEmail());
        teacher.setLogin(teach.getLogin());
        teacher.setPassword(passwordEncoder.encode(teach.getPassword()));

        teacherRepository.save(teacher);
    }

    @Override
    public void dropTeacher(Long id_teacher) {
        if(!courseRepository.existsByTeacher(teacherRepository.findById(id_teacher).get())) {
            teacherRepository.deleteById(id_teacher);
        }

    }


}
