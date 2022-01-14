package com.ea.campus.ms.student.controller;

import com.ea.campus.ms.student.dto.StudentDto;
import com.ea.campus.ms.student.dto.PageDto;
import com.ea.campus.ms.student.dto.mapper.StudentDtoMapper;
import com.ea.campus.ms.student.exception.EntityNotFoundException;
import com.ea.campus.ms.student.model.Student;
import com.ea.campus.ms.student.queryparam.StudentQueryParam;
import com.ea.campus.ms.student.service.StudentService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentDtoMapper studentDtoMapper;

    public StudentController(StudentService studentService, StudentDtoMapper studentDtoMapper) {
        this.studentService = studentService;
        this.studentDtoMapper = studentDtoMapper;
    }

    @GetMapping
    public PageDto<StudentDto> getAll(StudentQueryParam queryParam, Pageable page) {
        return studentDtoMapper.toPageDto(studentService.getAll(queryParam, page));
    }

    @GetMapping("/count")
    public long count(StudentQueryParam queryParam) {
        return studentService.count(queryParam);
    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable String id) {
        Student student = studentService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class, id));
        return studentDtoMapper.toDto(student);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        studentService.deleteById(id);
    }

    @PostMapping
    public StudentDto create(@RequestBody StudentDto studentDto) {
        Student student = studentDtoMapper.toEntity(studentDto);
        return studentDtoMapper.toDto(studentService.save(student));
    }

    @DeleteMapping
    public void deleteAll() {
        studentService.deleteAll();
    }
}
