package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Student;
import com.studycourses.webclient.model.Task;
import com.studycourses.webclient.model.TaskPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskPerformanceRepository extends JpaRepository<TaskPerformance,Long> {

    List<TaskPerformance> findByStudent(Student student);

    TaskPerformance findByTask(Task task);

    List<TaskPerformance> findAllByTask(Task task);

    TaskPerformance findByTaskAndStudent(Task task,Student student);

    @Query("select per from TaskPerformance as per where per.task=:task and per.completion is not NULL")
    List<TaskPerformance> findByTaskWhereCompletionNotNull(Task task);

    void deleteAllByStudent(Student student);

    void deleteAllByTask(Task task);

    void deleteByTaskAndStudent(Task task, Student student);

    boolean existsByTaskAndStudent(Task task,Student student);


}
