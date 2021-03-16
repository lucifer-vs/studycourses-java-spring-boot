package com.studycourses.webclient.service;

import com.studycourses.webclient.XmlParsing.*;
import com.studycourses.webclient.model.*;
import com.studycourses.webclient.repository.AcademicDisciplineRepository;
import com.studycourses.webclient.repository.RoleRepository;
import com.studycourses.webclient.repository.SchoolRepository;
import com.studycourses.webclient.repository.StudyGroupRepository;
import com.studycourses.webclient.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Service
public class XMLParserService {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private AcademicDisciplineRepository academ;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudyGroupRepository studyGroupRepository;
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private RoleRepository roleRepository;

    private SAXParserFactory factory;
    private SAXParser saxParser;

    private XmlCourseHandler xmlCourseHandler;
    private XmlTestHandler xmlTestHandler;

    private XmlTeacherHandler xmlTeacherHandler;

    private XmlStudentHandler xmlStudentHandler;

    public XMLParserService(){
        try{
            this.factory=SAXParserFactory.newInstance();
            this.saxParser=factory.newSAXParser();
            this.xmlCourseHandler=new XmlCourseHandler();
            this.xmlTestHandler=new XmlTestHandler();
            this.xmlTeacherHandler=new XmlTeacherHandler();
            this.xmlStudentHandler=new XmlStudentHandler();
        }
       catch (Exception e){
           e.printStackTrace();
       }
    }

    public List<TestQuestion> getTest(String path) throws SAXException, IOException {
        this.saxParser.parse(path,xmlTestHandler);
        return xmlTestHandler.getTest();
    }

    @Transactional
    public void createNewCourse(Teacher teacher, InputStream is) throws SAXException,IOException{
        this.saxParser.parse(is,xmlCourseHandler);
        AcademicDiscipline academicDiscipline=academ.findByName(xmlCourseHandler.getAcademicDiscipline().getName());
        Course course=xmlCourseHandler.getCourse();
        course.setAcademicDiscipline(academicDiscipline);
        course.setTeacher(teacher);
        courseService.saveCourse(course);
        course=courseService.getCourseByName(course.getName());
        for (Task i:
             xmlCourseHandler.getTasks()) {
            Task task=i;
            task.setAcademicDiscipline(academicDiscipline);
            task.setCourse(course);
            taskService.saveTask(task);
        }
    }

    @Transactional
    public void loadNewTeachers(InputStream is) throws SAXException,IOException{
        this.saxParser.parse(is,xmlTeacherHandler);
        List<Teacher> teachers=xmlTeacherHandler.getTeachers();
        for (Teacher i:
                teachers) {
            i.setPassword(passwordEncoder.encode(i.getPassword()));
            teacherService.saveTeacher(i);
        }
    }
    @Transactional
    public void loadNewStudents(InputStream is) throws SAXException,IOException{
        this.saxParser.parse(is,xmlStudentHandler);
        List<Student> students=xmlStudentHandler.getStudents();
        List<String> schools=xmlStudentHandler.getSchools();
        List<String> groups=xmlStudentHandler.getStudyGroups();
        School school;


        for(int i=0;i<students.size();i++){


            if(schoolRepository.existsByName(schools.get(i))){
                school=schoolRepository.findByName(schools.get(i));
            }
            else{
                school=schoolRepository.save(new School(schools.get(i)));
            }

            if(studyGroupRepository.existsStudyGroupByNameAndSchool(groups.get(i),school)){
                students.get(i).setStudyGroup(studyGroupRepository.getStudyGroupByNameAndAndSchool(groups.get(i),school));
            }
            else{
                students.get(i).setStudyGroup(studyGroupRepository.save(new StudyGroup(groups.get(i),school)));
            }
            students.get(i).setRole(roleRepository.findByName("STUDENT"));
            studentService.saveStudent(students.get(i));
        }


    }
}
