package com.studycourses.webclient.service;

import com.studycourses.webclient.model.Student;
import com.studycourses.webclient.model.Teacher;
import com.studycourses.webclient.repository.StudentRepository;
import com.studycourses.webclient.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
      boolean checkPosition=true;
      Teacher teacher=teacherRepository.findByLogin(login);
      Student student=studentRepository.findByLogin(login);


       if (login == null) {
            throw new UsernameNotFoundException("Unknown user: "+login);
        }
        if(!teacherRepository.existsByLogin(login)){
            checkPosition=true;
            student=studentRepository.findByLogin(login);
        }
        else{
            checkPosition=false;
            teacher=teacherRepository.findByLogin(login);
        }

        if (checkPosition){
          UserDetails stud = User.builder()
                    .username(student.getLogin())
                    .password(student.getPassword())
                    .roles(student.getRole().getName())
                    .build();
            return stud;
         }
       else{
           UserDetails teach = User.builder()
                    .username(teacher.getLogin())
                    .password(teacher.getPassword())
                    .roles(teacher.getRole().getName())
                    .build();
            return teach;
        }

    }
}
