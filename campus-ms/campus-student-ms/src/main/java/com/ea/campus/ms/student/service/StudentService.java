package com.ea.campus.ms.student.service;

import com.ea.campus.ms.student.model.Student;
import com.ea.campus.ms.student.queryparam.StudentQueryParam;
import com.ea.campus.ms.student.queryparam.mapper.StudentQueryMapper;
import com.ea.campus.ms.student.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StudentService extends BaseService<Student, StudentRepository, StudentQueryParam, StudentQueryMapper> {

    public StudentService(StudentRepository studentRepository, StudentQueryMapper queryMapper) {
        super(studentRepository, queryMapper);
    }

    @Transactional
    public void deleteById(String id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("The id cannot be null");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        for (Student student : repository.findAll()) {
            repository.deleteById(student.getId());
        }
    }

}
