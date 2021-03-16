package com.studycourses.webclient.controller;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.StudyGroup;
import com.studycourses.webclient.service.*;
import com.studycourses.webclient.service.impl.CourseServiceImpl;
import com.studycourses.webclient.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    @Value("${upload.path}")
    private String folderPath;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private ReportService reportService;

    @RequestMapping ("/file/{subdocument}")
    @ResponseBody
    public void getTaskSubdocument(@PathVariable(value = "subdocument") String subdocument,
                                   HttpServletResponse response){
        String fileName=subdocument;
        if (fileName.indexOf(".doc")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".docx")>-1) response.setContentType("application/msword");
        if (fileName.indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".csv")>-1) response.setContentType("application/vnd.ms-excel");
        if (fileName.indexOf(".ppt")>-1) response.setContentType("application/ppt");
        if (fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");
        if (fileName.indexOf(".zip")>-1) response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
        response.setHeader("Content-Transfer-Encoding", "binary");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(folderPath+"/"+fileName);
            int len;
            byte[] buf = new byte[1024 ];
            while((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        }
        catch(IOException e) {
            e.printStackTrace();

        }
    }

    @PostMapping("/loadimage/{id_course}")
    public String loadImgCourse(@RequestParam("file") MultipartFile file,
                                @PathVariable (value = "id_course") Long id_course)
    throws IOException{
        if(file != null && !file.getOriginalFilename().isEmpty()){
            Course course=courseService.findById(id_course);
            File uploadDir=new File(folderPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String resultFileName;
            if(course.getImage()==null){
                String uuidFile= UUID.randomUUID().toString();
                resultFileName = uuidFile+"."+file.getOriginalFilename();
            }
            else{
                resultFileName=course.getImage();
            }

            file.transferTo(new File(folderPath+"/"+resultFileName));

            course.setImage(resultFileName);
            courseService.saveCourse(course);
        }
        return "redirect:/course-info/"+id_course;
    }

    @RequestMapping ("/file/pdf/{id_course}")
    @ResponseBody
    public void getProgress(HttpServletResponse response,
                            @PathVariable(value = "id_course") Long id_course,
                            @ModelAttribute(value = "group")StudyGroup studyGroup){

        try {
            ReportData reportData=new ReportData(courseService.getProgressByGroupAndCourse(studentService.getStudyGroupById(studyGroup.getId_group()),
                    courseService.getCourseById(id_course)));
            reportService.createReport(reportData.getReportData(),
                    studentService.getStudyGroupById(studyGroup.getId_group()),
                    courseService.getCourseById(id_course));

            String fileName=reportService.getReportPath();
            if (fileName.indexOf(".pdf")>-1) response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=" +fileName);
            response.setHeader("Content-Transfer-Encoding", "binary");
            try {
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
                FileInputStream fis = new FileInputStream(fileName);
                int len;
                byte[] buf = new byte[1024 ];
                while((len = fis.read(buf)) > 0) {
                    bos.write(buf,0,len);
                }
                bos.close();
                response.flushBuffer();
            }
            catch(IOException e) {
                e.printStackTrace();

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
