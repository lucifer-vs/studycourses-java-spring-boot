package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType,Long> {
    TaskType findByName(String name);
}
