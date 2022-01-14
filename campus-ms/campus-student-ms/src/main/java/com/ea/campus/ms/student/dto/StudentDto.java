package com.ea.campus.ms.student.dto;

import lombok.*;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StudentDto extends BaseDto {

    private String name;
    private String surname;
    private String registrationCode;
    private Date registrationDate;

}
