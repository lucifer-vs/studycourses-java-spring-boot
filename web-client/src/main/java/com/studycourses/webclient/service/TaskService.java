package com.studycourses.webclient.service;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Task;
import com.studycourses.webclient.model.TaskPerformance;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    Task getTaskById(Long id_task);

    boolean IsTest(Long id_task);

    TaskPerformance getTaskPerformance(Task task);

    void saveTaskPerformance(TaskPerformance taskPerformance);

    void saveAllTasks(List<Task> taskList);

    void saveTask(Task task);

    void saveTask(Long id_task, Task task,String res,MultipartFile file);

    void saveNewTask(Task task, String res, MultipartFile file, Course course);

    List<TaskPerformance> getAllTasksPerformanceByTask(Task task);

    TaskPerformance getTaskPerformanceById(Long id_performance);

    void saveCheckPerformance(Long id_performance,String res);

    void dropTaskById(Long id_task);


}
