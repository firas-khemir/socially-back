package com.legion.feedservice.services.example;


import com.legion.feedservice.dto.example.CreateStudentRequest;
import com.legion.feedservice.dto.example.CreateSubjectRequest;
import com.legion.feedservice.dto.example.GetStudentsByBirthYearRequest;
import com.legion.feedservice.dto.example.UpdateStudentRequest;
import com.legion.feedservice.entities.example.Department;
import com.legion.feedservice.entities.example.IsLearningRelationship;
import com.legion.feedservice.entities.example.Student;
import com.legion.feedservice.entities.example.Subject;
import com.legion.feedservice.repositories.example.DepartmentRepository;
import com.legion.feedservice.repositories.example.StudentRepository;
import com.legion.feedservice.repositories.example.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    private final DepartmentRepository departmentRepository;

//    public Student createStudent(CreateStudentRequest createStudentRequest) {
//
//        Department department = new Department();
//        department.setDepName(createStudentRequest.getDepartment().getDepName());
//
//        departmentRepository.save(department);
//
//        List<IsLearningRelationship> isLearningRelationList =
//            new ArrayList<>();
//
//        if (createStudentRequest.getSubjectList() != null) {
//
//            for (CreateSubjectRequest createSub :
//                createStudentRequest.getSubjectList()) {
//
//                Subject subject = new Subject();
//                subject.setSubName(createSub.getSubjectName());
//
//                subjectRepository.save(subject);
//
//                IsLearningRelationship relation = new IsLearningRelationship();
//                relation.setMark(createSub.getMarks());
//                relation.setSubject(subject);
//                isLearningRelationList.add(relation);
//            }
//        }
//
//        Student student = new Student();
//        student.setName(createStudentRequest.getName());
//        student.setCountry(createStudentRequest.getCountry());
//        student.setBirthYear(createStudentRequest.getBirthYear());
//
//        student.setDepartment(department);
//        student.setIsLearningRelationshipList(isLearningRelationList);
//
//        studentRepository.save(student);
//
//        return student;
//    }

    public Student createStudent(CreateStudentRequest dto) {

        Department department = departmentRepository.findDepartmentByDepName(dto.getDepartment().getDepName()).orElse(null);
        if (department == null) {
            department = new Department();
            department.setDepName(dto.getDepartment().getDepName());
            departmentRepository.save(department);
        }


        List<IsLearningRelationship> isLearningRelationships = new ArrayList<>();

        if(dto.getSubjectList() != null) {
            for(CreateSubjectRequest elem: dto.getSubjectList()) {
                Subject subject = subjectRepository.findBySubName(elem.getSubjectName()).orElse(null);
                if(subject == null) {
                    subject = new Subject();
                    subject.setSubName(elem.getSubjectName());
                    subjectRepository.save(subject);
                }

                IsLearningRelationship isLearningRelationship = new IsLearningRelationship();
                isLearningRelationship.setMark(elem.getMarks());
                isLearningRelationship.setSubject(subject);
                isLearningRelationships.add(isLearningRelationship);
            }
        }

        Student student = new Student();

        student.setName(dto.getName());
        student.setCountry(dto.getCountry());
        student.setBirthYear(dto.getBirthYear());
        student.setDepartment(department);
        student.setIsLearningRelationshipList(isLearningRelationships);

        studentRepository.save(student);

        return  student;
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id).get();
    }

    public List<Student> getStudentsByName(String name) {
        return studentRepository.findByName(name);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent (UpdateStudentRequest updateStudentRequest) {
        Student student =
            studentRepository.findById(updateStudentRequest.getId()).get();

        student.setName(updateStudentRequest.getName());
        student.setCountry(updateStudentRequest.getCountry());
        student.setBirthYear(updateStudentRequest.getBirthYear());

        studentRepository.save(student);

        return student;
    }

    public String deleteStudent(long id) {
        studentRepository.deleteById(id);

        return "Student Deleted";
    }

    public List<Student> getStudentByNameAndBirthYear(String name, Integer birthYear) {
        //return studentRepository.findByNameAndBirthYear(name, birthYear);
        return studentRepository.getByNameAndBirthYear(name, birthYear);
    }

    public List<Student> getStudentByNameOrBirthYear(String name, Integer birthYear) {
        return studentRepository.findByNameOrBirthYear(name, birthYear);
    }

    public List<Student> getStudentByNameOrBirthYear(GetStudentsByBirthYearRequest req) {
        return studentRepository.findByBirthYearIn(req.getBirthYearList());
    }

    public List<Student> getStudentsWithPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return studentRepository.findAll(pageable).getContent();
    }

    public List<Student> getStudentsWithSorting() {
        Sort sort = Sort.by(Direction.ASC, "name");

        return studentRepository.findAll(sort);
    }

    public List<Student> getStudentsByNameLike(String name) {
        return studentRepository.findByNameLike(name);
    }

    public List<Student> getStudentsByNameStartsWith(String name) {
        return studentRepository.findByNameStartsWith(name);
    }

}

