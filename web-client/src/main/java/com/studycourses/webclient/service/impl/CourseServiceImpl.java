package com.studycourses.webclient.service.impl;

import com.studycourses.webclient.model.*;
import com.studycourses.webclient.repository.*;
import com.studycourses.webclient.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Access;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private GroupCoursesRepository groupCoursesRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;
    
    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private TaskPerformanceRepository taskPerformanceRepository;

    @Autowired
    private ProgressRepository progressRepository;

    @Override
    public List<Course> getListCourse() {
        return courseRepository.findAll();
    }

    @Override
    public List<Task> getListTaskByCourse(Course course) {
        return taskRepository.findByCourse(course);
    }

    @Override
    public Course findById(Long id_course) {
        return courseRepository.findById(id_course).get();
    }

    @Override
    public boolean existsCourseById(Long id_course) {
        return courseRepository.existsById(id_course);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public List<GroupCourses> getListGroupsByIdCourse(Long id_course) {
        return groupCoursesRepository.findByCourse(courseRepository.findById(id_course).get());
    }

    @Override
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupRepository.findAll();
    }

    @Override
    @Transactional
    public void addGroup(Long id_course, StudyGroup studyGroup) {

        GroupCourses groupCourses=new GroupCourses();
        groupCourses.setStudyGroup(studyGroupRepository.findById(studyGroup.getId_group()).get());
        groupCourses.setCourse(this.findById(id_course));
        groupCoursesRepository.save(groupCourses);

        List<TaskPerformance> taskPerformanceList=new ArrayList<>();
        List<Student> students;
        List<Task> tasks;

        tasks=this.getListTaskByCourse(groupCourses.getCourse());
        students=studentService.getStudentByGroup(groupCourses.getStudyGroup());


            for (Student i:
                 students) {
                for (Task j:
                     tasks) {
                    taskPerformanceList.add(new TaskPerformance(i,j));
                }
            }

        List<Progress> progresses=new ArrayList<>();
            Course course =this.findById(id_course);

        for (Student i:
             students) {
            progresses.add(new Progress(i,course));
        }


            progressRepository.saveAll(progresses);
            taskPerformanceRepository.saveAll(taskPerformanceList);

    }

    @Override
    public boolean existsByTeacher(Teacher teacher) {
        return courseRepository.existsByTeacher(teacher);
    }

    public Course getCourseByTask(Long id_task){
        return taskRepository.findById(id_task).get().getCourse();
    }

    @Override
    @Transactional
    public void dropGroup(Long id_group, Long id_course) {

        List<Student> students=studentService.getStudentByGroup(studyGroupRepository.findById(id_group).get());
        List<Task> tasks=taskRepository.findByCourse(this.findById(id_course));

        for (Student st:
             students) {
            for (Task t:
                 tasks) {
                if(taskPerformanceRepository.existsByTaskAndStudent(t,st)){
                    taskPerformanceRepository.deleteByTaskAndStudent(t,st);
                }
            }
        }

        groupCoursesRepository.deleteByCourseAndStudyGroup(this.findById(id_course),studyGroupRepository.findById(id_group).get());

    }

    @Override
    @Transactional
    public void dropCourse(Long id_course) {

        List<Task> tasks=taskRepository.findByCourse(this.findById(id_course));

        for (Task i:
             tasks) {
            taskPerformanceRepository.deleteAllByTask(i);
        }

        taskRepository.deleteAllByCourse(this.findById(id_course));

        progressRepository.deleteAllByCourse(this.findById(id_course));

        groupCoursesRepository.deleteAllByCourse(this.findById(id_course));

        courseRepository.deleteById(id_course);
    }
    @Override
    public List<Progress> getProgressByGroupAndCourse(StudyGroup studyGroup,Course course) {
        List<Progress> progresses=new ArrayList<>();

        for (Student i:
             studentService.getStudentByGroup(studyGroup)) {
            progresses.add(progressRepository.findByStudentAndCourse(i,course));
        }
        return progresses;
    }

    public Course getCourseById(Long id_course){
        return courseRepository.findById(id_course).get();
    }
}
