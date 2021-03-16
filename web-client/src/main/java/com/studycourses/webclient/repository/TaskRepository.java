package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.Course;
import com.studycourses.webclient.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByCourse(Course course);

    void deleteAllByCourse(Course course);

}
