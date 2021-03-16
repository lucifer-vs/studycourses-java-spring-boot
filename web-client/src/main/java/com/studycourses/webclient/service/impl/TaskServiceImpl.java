package com.studycourses.webclient.service.impl;

import com.studycourses.webclient.model.*;
import com.studycourses.webclient.repository.*;
import com.studycourses.webclient.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskPerformanceRepository taskPerformanceRepository;
    @Autowired
    private TaskTypeRepository taskTypeRepository;
    @Autowired
    private GroupCoursesRepository groupCoursesRepository;
    @Autowired
    private StudentServiceImpl studentService;

    @Override
    public Task getTaskById(Long id_task) {
        return taskRepository.findById(id_task).get();
    }

    @Override
    public boolean IsTest(Long id_task) {
        Task task=taskRepository.findById(id_task).get();
        if (task.getType().getName().equals("TEST")){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public TaskPerformance getTaskPerformance(Task task) {
        return taskPerformanceRepository.findByTask(task);
    }

    @Override
    public void saveTaskPerformance(TaskPerformance taskPerformance) {
        taskPerformanceRepository.save(taskPerformance);
    }

    @Override
    public void saveAllTasks(List<Task> taskList) {
        taskRepository.saveAll(taskList);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void saveNewTask(Task taskobj, String res, MultipartFile file, Course course) {
        Task task=taskobj;
        task.setCourse(course);
        task.setAcademicDiscipline(course.getAcademicDiscipline());
        TaskType type;
        if(res.equals("text")){
            type=taskTypeRepository.findByName(res.toUpperCase());
        }
        else{
            type=taskTypeRepository.findByName(res.toUpperCase());
        }
        task.setType(type);
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir=new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile= UUID.randomUUID().toString();

            String resultFileName= uuidFile+"."+file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath+"/"+resultFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            task.setSubdocument(resultFileName);

        }

        task=taskRepository.save(task);

        List<GroupCourses> gr=groupCoursesRepository.findByCourse(course);

        List<TaskPerformance> taskPerformanceList=new ArrayList<>();

        for (GroupCourses g:
            gr) {

            List<Student> students=studentService.getStudentByGroup(g.getStudyGroup());
            for (Student s:
                 students) {
                taskPerformanceList.add(new TaskPerformance(s,task));
            }

        }
        taskPerformanceRepository.saveAll(taskPerformanceList);


    }

    @Override
    public void saveTask(Long id_task, Task task, String res, MultipartFile file) {
        Task update=taskRepository.findById(id_task).get();
        update.setName(task.getName());
        update.setDescription(task.getDescription());
        update.setDeadline(task.getDeadline());


        if (res.equals("text")){
            update.setType(taskTypeRepository.findByName(res.toUpperCase()));
        }
        else{
            update.setType(taskTypeRepository.findByName(res.toUpperCase()));
        }

        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadDir=new File(uploadPath);

            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuidFile= UUID.randomUUID().toString();

            String resultFileName= uuidFile+"."+file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath+"/"+resultFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            update.setSubdocument(resultFileName);

        }

        this.saveTask(update);
    }

    @Override
    public List<TaskPerformance> getAllTasksPerformanceByTask(Task task) {
        return taskPerformanceRepository.findAllByTask(task);
    }

    @Override
    public TaskPerformance getTaskPerformanceById(Long id_performance) {
        return taskPerformanceRepository.findById(id_performance).get();
    }

    @Override
    public void saveCheckPerformance(Long id_performance, String res) {
        TaskPerformance task=taskPerformanceRepository.findById(id_performance).get();
        task.setGrade(Integer.parseInt(res));
        taskPerformanceRepository.save(task);
    }

    public TaskPerformance getPerformanceByTaskAndStudent(Task task,Student student){
        return  taskPerformanceRepository.findByTaskAndStudent(task, student);
    }

    public List<TaskPerformance> getPerformanceByTaskWhereCompletionNotNull(Task task){
        return taskPerformanceRepository.findByTaskWhereCompletionNotNull(task);
    }

    @Override
    @Transactional
    public void dropTaskById(Long id_task) {
        Task task=taskRepository.findById(id_task).get();
        taskPerformanceRepository.deleteAllByTask(task);
        taskRepository.deleteById(id_task);
    }
}
