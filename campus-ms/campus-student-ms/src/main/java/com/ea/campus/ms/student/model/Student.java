package com.ea.campus.ms.student.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Document(collection = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student extends BaseEntity {

    private String name;
    private String surname;
    private String registrationCode;
    private Date registrationDate;

}
