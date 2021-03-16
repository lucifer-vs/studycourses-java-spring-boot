package com.studycourses.webclient.controller;

import com.studycourses.webclient.model.*;
import com.studycourses.webclient.service.XMLParserService;
import com.studycourses.webclient.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class AdminController {

    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private CourseServiceImpl courseService;
    @Autowired
    private XMLParserService xmlParserService;
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private TaskServiceImpl taskService;


    @GetMapping("/students-view/")
    public String studentsView(Model model){
        model.addAttribute("students",studentService.getAllStudents());
        return "students-view";
    }

    @GetMapping("/student-info/{id_student}")
    public String studentInfo(@PathVariable(value = "id_student") Long id_student,
                              Model model){
        model.addAttribute("student",studentService.getById(id_student));
        model.addAttribute("is_admin",true);
        return "student";
    }
    @GetMapping("/student/edit/{id_student}")
    public String studentEdit(@PathVariable(value = "id_student") Long id_student,
                              Model model){

        StudyGroup studyGroup=studentService.getById(id_student).getStudyGroup();
        model.addAttribute("group",studyGroup);
        model.addAttribute("student",studentService.getById(id_student));
        model.addAttribute("study",courseService.getAllStudyGroups());
        return "student-edit";
    }

    @PostMapping("/save-student/{id_student}")
    public String saveStudent(@PathVariable(value = "id_student") Long id_student,
                  @ModelAttribute("student")Student student,

                   @ModelAttribute("group")StudyGroup studyGroup)
    {
      studentService.saveStudent(id_student,studyGroup,student);
       return "redirect:/";

    }

    @GetMapping("/drop-student/{id_student}")
    public String dropStudent(@PathVariable(value = "id_student") Long id_student){
        studentService.dropStudent(id_student);
        return "redirect:/students-view/";
    }

    @GetMapping("/teachers-view/")
    String teachersView(Model model){
        model.addAttribute("teachers",teacherService.getAllTeachers());
        return "teachers-view";
    }
    @GetMapping("/teacher-info/{id_teacher}")
    public String teacherInfo(@PathVariable(value = "id_teacher") Long id_teacher,
                              Model model){
        model.addAttribute("admin",true);
        model.addAttribute("teacher",teacherService.getTeacherById(id_teacher));
        return "teacher";
    }
    @GetMapping("/teacher/edit/{id_teacher}")
    public String teacherEdit(@PathVariable(value = "id_teacher") Long id_teacher,
                              Model model){
        model.addAttribute("teacher",teacherService.findByIdTeacher(id_teacher));
        return "teacher-edit";
    }
    @PostMapping("/save-teacher/{id_teacher}")
    public String saveTeacher(@PathVariable(value = "id_teacher") Long id_teacher,
                              @ModelAttribute("teacher")Teacher teacher){
        teacherService.saveTeacher(id_teacher,teacher);
        return "redirect:/teachers-view/";
    }
    @GetMapping("/drop-teacher/{id_teacher}")
    public String dropTeacher(@PathVariable(value = "id_teacher") Long id_teacher){
        teacherService.dropTeacher(id_teacher);
        return "redirect:/teachers-view/";
    }


    @PostMapping("/load-teachers/")
    public String loadTeachers(@RequestParam("file") MultipartFile file){
        try{
            xmlParserService.loadNewTeachers(file.getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/teachers-view/";
    }

    @PostMapping("/load-students/")
    public String loadStudents(@RequestParam("file") MultipartFile file){
        try {
            xmlParserService.loadNewStudents(file.getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/students-view/";
    }

    @GetMapping("/drop-task/{id_task}")
    public String dropTask(@PathVariable(value = "id_task") Long id_task) {

        Long id_course=courseService.getCourseByTask(id_task).getId_course();
        taskService.dropTaskById(id_task);

        return "redirect:/course-info/"+id_course;
    }

    @GetMapping("/drop-group-from-course/{id_course}/{id_group}")
    public String dropGroupFromCourse(@PathVariable(value = "id_group") Long id_group,
                                      @PathVariable(value = "id_course") Long id_course){

        courseService.dropGroup(id_group,id_course);

        return "redirect:/list-groups/"+id_course;
    }

    @GetMapping("/drop-course/{id_course}")
    public String dropCourse(@PathVariable(value = "id_course") Long id_course){

        courseService.dropCourse(id_course);

        return "redirect:/courses";
    }
    @GetMapping("/list-academic-discipline/")
    public String getListDisciplines(Model model){
        model.addAttribute("academic",adminService.getListAcademicDiscipline());
        return "academic-discipline";
    }

    @GetMapping("/academic/edit/{id_discipline}")
    public String editDiscipline(@PathVariable(value = "id_discipline") Long id_discipline,
                                 Model model){
        model.addAttribute("discipline",adminService.getAcademicDisciplineById(id_discipline));
        return "edit-academic";
    }

    @PostMapping("/academic/edit/{id_discipline}")
    public String saveDiscipline(@PathVariable(value = "id_discipline") Long id_discipline,
                                 @ModelAttribute("discipline") AcademicDiscipline academicDiscipline){
        adminService.saveAcademicDiscipline(academicDiscipline);
        return "redirect:/list-academic-discipline/";
    }

    @GetMapping("/drop-academic/{id_discipline}")
    public String dropAcademic(@PathVariable(value = "id_discipline") Long id_discipline){

        adminService.dropAcademicDiscipline(id_discipline);
        return "redirect:/list-academic-discipline/";
    }
    @GetMapping("/academic/new/")
    public String newAcademic(Model model){
        AcademicDiscipline academicDiscipline=new AcademicDiscipline();
        model.addAttribute("discipline",academicDiscipline);
        return "new-academic-discipline";
    }
    @PostMapping("/academic/new/")
    public String newAcademic(@ModelAttribute("discipline") AcademicDiscipline academicDiscipline){
        adminService.saveAcademicDiscipline(academicDiscipline);
        return "redirect:/list-academic-discipline/";
    }

    @GetMapping("/list-schools/")
    public String getListSchools(Model model){
        model.addAttribute("schools",adminService.getListSchools());
        return "school";
    }


    @GetMapping("/list-all-groups/")
    public String getAllGroups(Model model){
        model.addAttribute("groups",adminService.getAllStudyGroups());
        return "list-all-groups";
    }
    @GetMapping("/drop-group/{id_group}")
    public String dropGroup(@PathVariable(value = "id_group") Long id_group){
        adminService.dropGroupById(id_group);
        return "redirect:/list-all-groups/";
    }
}
