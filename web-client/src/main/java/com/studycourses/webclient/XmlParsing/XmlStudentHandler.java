package com.studycourses.webclient.XmlParsing;

import com.studycourses.webclient.model.Student;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class XmlStudentHandler  extends DefaultHandler {

    private static final String STUDENTS="students";
    private static final String STUDENT="student";
    private static final String SURNAME="surname";
    private static final String FIRSTNAME="firstname";
    private static final String MIDDLENAME="middlename";
    private static final String BIRTHDATE="birthdate";
    private static final String EMAIL="email";
    private static final String LOGIN="login";
    private static final String PASSWORD="password";
    private static final String SCHOOL="school";
    private static final String CLASS="class";

    private List<Student> students;
    private List<String> studyGroups;
    private List<String> schools;
    private String elementValue;

    @Override
    public void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException {

        switch (qName) {
            case STUDENTS:
                students=new ArrayList<>();
                schools=new ArrayList<>();
                studyGroups=new ArrayList<>();
                break;
            case STUDENT:
                    students.add(new Student());
                break;

        }
    }
    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        switch (qName) {

            case SURNAME:
                latestStudent().setSurname(elementValue);
                break;
            case FIRSTNAME:
                latestStudent().setFirstname(elementValue);
                break;
            case MIDDLENAME:
                latestStudent().setMiddlename(elementValue);
                break;
            case BIRTHDATE:
                latestStudent().setBirth_date(elementValue);
                break;
            case EMAIL:
                latestStudent().setEmail(elementValue);
                break;
            case LOGIN:
                latestStudent().setLogin(elementValue);
                break;
            case PASSWORD:
                latestStudent().setPassword(elementValue);
                break;
            case SCHOOL:
                schools.add(elementValue);
                break;
            case CLASS:
                studyGroups.add(elementValue);
                break;

        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        elementValue = new String(ch, start, length);
    }

    private Student latestStudent() {
        int latestStudentIndex = students.size() - 1;
        return students.get(latestStudentIndex);
    }

    public List<Student> getStudents() {
        return students;
    }
    public List<String> getSchools(){return schools;}
    public List<String> getStudyGroups(){return studyGroups;}
}
