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

    /**
     * Get list of all students
     *
     * REST call:
     * GET http://localhost:8085/students
     */
    @GetMapping
    public PageDto<StudentDto> getAll(StudentQueryParam queryParam, Pageable page) {
        return studentDtoMapper.toPageDto(studentService.getAll(queryParam, page));
    }

    /**
     * Get students count
     *
     * REST call:
     * GET http://localhost:8085/students
     */
    @GetMapping("/count")
    public long count(StudentQueryParam queryParam) {
        return studentService.count(queryParam);
    }

    /**
     * Get student by ID
     *
     * REST call:
     * GET http://localhost:8085/students/123
     */
    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable String id) {
        Student student = studentService.getById(id)
                .orElseThrow(() -> new EntityNotFoundException(Student.class, id));
        return studentDtoMapper.toDto(student);
    }

    /**
     * Create a new student
     *
     * REST call:
     * POST http://localhost:8085/students/123
     * Body: { name:"Ermal" , "surname" : "Aliraj"}
     */
    @PostMapping
    public StudentDto create(@RequestBody StudentDto studentDto) {
        Student student = studentDtoMapper.toEntity(studentDto);
        return studentDtoMapper.toDto(studentService.save(student));
    }

    /**
     * Update student with id passed in parameter
     *
     * REST call:
     * PUT http://localhost:8085/students/123
     * Body: { name:"Ermal2" , "surname" : "Aliraj222"}
     */
    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody StudentDto studentDto) {
        Student student = studentDtoMapper.toEntity(studentDto);
        studentService.save(student);
    }

    /**
     * Delete all students
     *
     * REST call:
     * DELETE http://localhost:8085/students
     */
    @DeleteMapping
    public void deleteAll() {
        studentService.deleteAll();
    }

    /**
     * Delete the student with ID passed in parameter
     * REST call:
     * DELETE http://localhost:8085/students/123
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        studentService.deleteById(id);
    }

}
