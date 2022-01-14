package com.ea.campus.ms.student.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDto {
    private String id;
    private Date creationDate;

    public BaseDto(String id) {
        this.id = id;
    }
}
