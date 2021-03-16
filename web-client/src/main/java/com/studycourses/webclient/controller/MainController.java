package com.studycourses.webclient.controller;

import com.studycourses.webclient.XmlParsing.TestQuestion;
import com.studycourses.webclient.model.*;
import com.studycourses.webclient.service.XMLParserService;
import com.studycourses.webclient.service.impl.CourseServiceImpl;
import com.studycourses.webclient.service.impl.StudentServiceImpl;
import com.studycourses.webclient.service.impl.TaskServiceImpl;
import com.studycourses.webclient.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private XMLParserService xmlParserService;

    @GetMapping("/")
    public String Main( Model model){
        return "main-page";
    }

    @GetMapping("/profile")
    public String Profile( Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasTeacherRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_TEACHER"));
        boolean hasStudentRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"));
        boolean hasAdminRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if ((hasTeacherRole) || (hasAdminRole)) {
            return "redirect:/profile/teacher";
        } else {
            if (hasStudentRole) {
                return "redirect:/profile/student";
            } else {
                return "redirect:/";
            }
        }
    }

    @GetMapping("/courses")
    public String Courses( Model model){
        List<Course> coursesList;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasTeacherRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_TEACHER"));
        boolean hasStudentRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"));
        boolean hasAdminRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if(hasTeacherRole){
            coursesList=teacherService.getListCourseByTeacher(auth.getName());
            model.addAttribute("courses",coursesList);
        }
        if(hasAdminRole){
            coursesList=courseService.getListCourse();
            model.addAttribute("courses",coursesList);
            model.addAttribute("is_admin",hasAdminRole);
        }
        if(hasStudentRole){
            coursesList=studentService.getGroupCoursesBy(auth.getName());
            model.addAttribute("courses",coursesList);

        }
        model.addAttribute("is_not_student",!hasStudentRole);

        return "courses";
    }
    @GetMapping("/course-info/{id_course}")
    public String CourseInfo(@PathVariable (value = "id_course") Long id_course, Model model){
        if(!courseService.existsCourseById(id_course)){
            return "redirect:/courses";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasStudentRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"));
        boolean hasAdminRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        boolean hasTeacherRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_TEACHER"));

        Course course =courseService.findById(id_course);
        if((hasAdminRole)||(hasTeacherRole)){
            List<Task> tasks=courseService.getListTaskByCourse(course);
            StudyGroup studyGroup=new StudyGroup();
            model.addAttribute("group",studyGroup);
            model.addAttribute("study",courseService.getListGroupsByIdCourse(id_course));
            model.addAttribute("tasks",tasks);
        }
        if(hasStudentRole){
            Student student=studentService.getStudentByLogin(auth.getName());
            List<TaskPerformance> performanceList=studentService.getTaskPerformanceByStudentAndCourse(student,course);
            model.addAttribute("performance",performanceList);
        }

        model.addAttribute("course",course);


        model.addAttribute("is_not_student",!hasStudentRole);
        model.addAttribute("is_student",hasStudentRole);
        model.addAttribute("is_admin",hasAdminRole);

        return "course-info";
    }
    @GetMapping("/task-info/{id_task}")
    public String TaskInfo(@PathVariable (value = "id_task") Long id_task, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasStudentRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"));

    Task task=taskService.getTaskById(id_task);
    if(taskService.IsTest(id_task)){
        try{
            List<TestQuestion> res;
            res=xmlParserService.getTest(uploadPath+"/"+task.getSubdocument());
            model.addAttribute("test",res);
            model.addAttribute("isTest",taskService.IsTest(id_task));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    else{
        model.addAttribute("isTest",taskService.IsTest(id_task));
    }
    model.addAttribute("task",task);
    model.addAttribute("is_not_student",!hasStudentRole);
    model.addAttribute("is_student",hasStudentRole);
        return "task-info";
    }

    @GetMapping("/newtask/{id_course}")
    public String loadTask(@PathVariable (value = "id_course") Long id_course,
                           Model model){
        Task task=new Task();
        model.addAttribute("task",task);
        return "new-task";
    }
    @PostMapping("/newtask/{id_course}")
    public String saveTask(@PathVariable (value="id_course") Long id_course,
                           @RequestParam("file") MultipartFile file,
                           @ModelAttribute ("task") Task taskobj,
                           @RequestParam("options") String res
                           )
    throws IOException {

       taskService.saveNewTask(taskobj,res,file,courseService.findById(id_course));

        return "redirect:/course-info/"+id_course;
    }

    @GetMapping("/task-info/edit/{id_task}")
    public String editTaskGet(@PathVariable (value = "id_task") Long id_task, Model model){
        Task task=taskService.getTaskById(id_task);
        model.addAttribute(task);
        return "edit-task";
    }
    @PostMapping("/task-info/edit/{id_task}")
    public String editTaskPost(@PathVariable(value = "id_task") Long id_task,
                               @RequestParam("file") MultipartFile file,
                               @ModelAttribute ("task") Task taskobj,
                               @RequestParam("options") String res
                               ){

        taskService.saveTask(id_task,taskobj,res,file);
        Long id_course=taskService.getTaskById(id_task).getCourse().getId_course();
        return "redirect:/course-info/"+id_course;
    }
}
