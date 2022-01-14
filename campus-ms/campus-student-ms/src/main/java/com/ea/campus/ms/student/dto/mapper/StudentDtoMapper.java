package com.ea.campus.ms.student.dto.mapper;

import com.ea.campus.ms.student.dto.PageDto;
import com.ea.campus.ms.student.dto.StudentDto;
import com.ea.campus.ms.student.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
@RequiredArgsConstructor
@Slf4j
public abstract class StudentDtoMapper {

    public PageDto<StudentDto> toPageDto(Page<Student> page) {
        final List<StudentDto> studentDtos = toDto(page.getContent());
        return new PageDto(studentDtos, page);
    }
    public abstract List<StudentDto> toDto(List<Student> students);
    public abstract StudentDto toDto(Student student);
    public abstract Student toEntity(StudentDto studentDto);

}
