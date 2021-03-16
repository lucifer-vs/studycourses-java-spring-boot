package com.studycourses.webclient.controller;

import com.studycourses.webclient.XmlParsing.TestQuestion;
import com.studycourses.webclient.model.Student;
import com.studycourses.webclient.model.Task;
import com.studycourses.webclient.model.TaskPerformance;
import com.studycourses.webclient.service.XMLParserService;
import com.studycourses.webclient.service.impl.StudentServiceImpl;
import com.studycourses.webclient.service.impl.TaskServiceImpl;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class StudentController {
    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private XMLParserService xmlParserService;

    @GetMapping("/profile/student")
    public String profileStudent(@AuthenticationPrincipal User user,
                                 Model model){
        model.addAttribute("student",studentService.getStudentByLogin(user.getUsername()));
        return "student";
    }
    @PostMapping("/profile/student")
    public String loadTeacherImg(@AuthenticationPrincipal User user,
                                 @RequestParam("file") MultipartFile file,
                                 Model model)
            throws IOException {
        if(file != null && !file.getOriginalFilename().isEmpty()){
            Student student=studentService.getStudentByLogin(user.getUsername());
            File uploadDir=new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile;
            uuidFile= UUID.randomUUID().toString();

            String resultFileName;
            if(student.getFoto()==null){
                resultFileName= uuidFile+"."+file.getOriginalFilename();
            }
            else {
                resultFileName=student.getFoto();
            }

            file.transferTo(new File(uploadPath+"/"+resultFileName));

            student.setFoto(resultFileName);
            studentService.saveStudent(student);
        }
        return "redirect:/profile/student";
    }
    @GetMapping("/task-info/performance/{id_task}")
    public String taskPerformance(@PathVariable(value = "id_task") Long id_task,
                                  @AuthenticationPrincipal User user,
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
        Student student=studentService.getStudentByLogin(user.getUsername());
        Task task=taskService.getTaskById(id_task);
        model.addAttribute("performance",taskService.getPerformanceByTaskAndStudent(task,student));
        return "task-performance";
    }
    @PostMapping("/task-info/performance/{id_task}")
    public String TaskResponsePost(@ModelAttribute ("text_response") String text,
                                   @PathVariable(value = "id_task") Long id_task,
                                   @RequestParam("file") MultipartFile file,
                                    @AuthenticationPrincipal User user)
    throws IOException{

        Student student=studentService.getStudentByLogin(user.getUsername());
        Task task=taskService.getTaskById(id_task);
        TaskPerformance performance=taskService.getPerformanceByTaskAndStudent(task,student);

        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir=new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile= UUID.randomUUID().toString();

            String resultFileName= uuidFile+"."+file.getOriginalFilename();
            file.transferTo(new File(uploadPath+"/"+resultFileName));
            performance.setSubdocument(resultFileName);

        }

        performance.setCompletion(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        performance.setText_responce(text);
        taskService.saveTaskPerformance(performance);
        Long id_course=taskService.getTaskById(id_task).getCourse().getId_course();

        return "redirect:/course-info/"+id_course;
    }
}
