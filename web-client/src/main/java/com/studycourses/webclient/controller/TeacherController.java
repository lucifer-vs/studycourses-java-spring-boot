package com.studycourses.webclient.controller;

import com.studycourses.webclient.XmlParsing.TestQuestion;
import com.studycourses.webclient.model.*;
import com.studycourses.webclient.service.XMLParserService;
import com.studycourses.webclient.service.impl.CourseServiceImpl;
import com.studycourses.webclient.service.impl.TaskServiceImpl;
import com.studycourses.webclient.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class TeacherController {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private XMLParserService xmlParserService;
    @Autowired
    private TeacherServiceImpl teacherService;
    @Autowired
    private TaskServiceImpl taskService;
    @Autowired
    private CourseServiceImpl courseService;





    @GetMapping("/profile/teacher")
    public String profileTeacher(@AuthenticationPrincipal User user, Model model){
        Teacher teacher=teacherService.getTeacherByLogin(user.getUsername());
        model.addAttribute("teacher",teacher);
        model.addAttribute("is_admin",teacherService.isAdmin(teacher));

        return "teacher";
    }

    @PostMapping("/profile/teacher")
    public String loadTeacherImg(@AuthenticationPrincipal User user,
                                 @RequestParam("file") MultipartFile file)
    throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            Teacher teacher=teacherService.getTeacherByLogin(user.getUsername());
            File uploadDir=new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile= UUID.randomUUID().toString();

            String resultFileName;
            if(teacher.getFoto()==null){
                resultFileName= uuidFile+"."+file.getOriginalFilename();
            }
            else{
                resultFileName=teacher.getFoto();
            }

            file.transferTo(new File(uploadPath+"/"+resultFileName));

           teacher.setFoto(resultFileName);
           teacherService.saveTeacher(teacher);
        }
       return "redirect:/profile/teacher";
    }

    @PostMapping("/loadcourse")
    public String loadCourse(@AuthenticationPrincipal User user,
                             @RequestParam("file") MultipartFile file){
        try{

            xmlParserService.createNewCourse(teacherService.getTeacherByLogin(user.getUsername()),
                    file.getInputStream());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/courses";
    }

    @GetMapping("/check-task/{id_task}")
    public String getCheckTasks(@PathVariable (value = "id_task") Long id_task,
                                Model model){

        if(taskService.IsTest(id_task)){
            try{
                List<TestQuestion> res;
                res=xmlParserService.getTest(uploadPath+"/"+taskService.getTaskById(id_task).getSubdocument());
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
        model.addAttribute("task",taskService.getTaskById(id_task));
        model.addAttribute("performances",taskService.getPerformanceByTaskWhereCompletionNotNull(taskService.getTaskById(id_task)));

        return "tasks-check";
    }

    @GetMapping("/checking/{id_performance}")
    public String getChecking(@PathVariable(value = "id_performance") Long id_performance,
                              Model model)
    {
        model.addAttribute("performance",taskService.getTaskPerformanceById(id_performance));
        return "checking-task";
    }

    @PostMapping("/checking/{id_performance}")
    public String postChecking(@PathVariable(value = "id_performance") Long id_performance,
                               @RequestParam("options") String res){
        taskService.saveCheckPerformance(id_performance,res);
        Long id_task=taskService.getTaskPerformanceById(id_performance).getTask().getId_task();

        return "redirect:/check-task/"+id_task;
    }

    @GetMapping("/list-groups/{id_course}")
    public String getListGroups(@PathVariable(value = "id_course") Long id_course,
                                Model model){

        StudyGroup studyGroup=new StudyGroup();
        model.addAttribute("relation",studyGroup);
        model.addAttribute("groups",courseService.getListGroupsByIdCourse(id_course));
        model.addAttribute("study",courseService.getAllStudyGroups());

        return "list-group";
    }
    @PostMapping("/list-groups/{id_course}")
    public String addGroup(@PathVariable(value = "id_course") Long id_course,
                           @ModelAttribute("relation") StudyGroup studyGroup){
        courseService.addGroup(id_course,studyGroup);
        return "redirect:/list-groups/"+id_course;
    }

}
