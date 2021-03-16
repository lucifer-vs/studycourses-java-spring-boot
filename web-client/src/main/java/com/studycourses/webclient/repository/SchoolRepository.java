package com.studycourses.webclient.repository;

import com.studycourses.webclient.model.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {

    School findByName(String name);

    boolean existsByName(String name);

}
