package com.studycourses.webclient.service.impl;

import com.studycourses.webclient.model.*;
import com.studycourses.webclient.repository.*;
import com.studycourses.webclient.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GroupCoursesRepository groupCoursesRepository;

    @Autowired
    private TaskPerformanceRepository taskPerformanceRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudyGroupRepository studyGroupRepository;
    @Autowired
    private ProgressRepository progressRepository;

    @Override
    public Student getStudentByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    @Override
    public List<Course> getGroupCoursesBy(String login) {
        List<Course> coursesList=new ArrayList<>();
        List<GroupCourses> relations=groupCoursesRepository.findByStudyGroup(this.getStudentByLogin(login).getStudyGroup());
        for (GroupCourses i:
                relations) {
            coursesList.add(i.getCourse());
        }
        return coursesList;
    }

    @Override
    public List<TaskPerformance> getTaskPerformanceByStudentAndCourse(Student student, Course course) {
        List<TaskPerformance> taskPerformanceList=new ArrayList<>();
        List<TaskPerformance> byStudent=taskPerformanceRepository.findByStudent(student);
        List<Task> tasks=new ArrayList<>();
        tasks=taskRepository.findByCourse(course);

        for (TaskPerformance i:
             byStudent) {
            for (Task j:
                 tasks) {
                if(i.getTask()==j){
                    taskPerformanceList.add(i);
                }

            }

        }
        return taskPerformanceList;
    }

    @Override
    public void saveStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentByGroup(StudyGroup studyGroup) {
        return studentRepository.findByStudyGroup(studyGroup);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll(Sort.by("surname","firstname"));
    }

    @Override
    public Student getById(Long id_student) {
        return studentRepository.findById(id_student).get();
    }

    public void saveStudent(Long id_student,StudyGroup studyGroup,Student student){

        Student st=studentRepository.findById(id_student).get();
        st.setSurname(student.getSurname());
        st.setFirstname(student.getFirstname());
        st.setMiddlename(student.getMiddlename());
        st.setBirth_date(student.getBirth_date());
        st.setEmail(student.getEmail());
        st.setLogin(student.getLogin());
        st.setStudyGroup(studyGroupRepository.findById(studyGroup.getId_group()).get());
        st.setPassword(passwordEncoder.encode(student.getPassword()));
        studentRepository.save(st);

    }

    @Override
    @Transactional
    public void dropStudent(Long id_student) {
        Student student=this.getById(id_student);
        taskPerformanceRepository.deleteAllByStudent(student);
        progressRepository.deleteAllByStudent(student);
        studentRepository.deleteById(id_student);
    }

    public StudyGroup getStudyGroupById(Long id_group){
        return studyGroupRepository.findById(id_group).get();
    }
}
