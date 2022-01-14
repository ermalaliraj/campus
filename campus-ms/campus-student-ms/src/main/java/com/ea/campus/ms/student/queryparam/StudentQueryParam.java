package com.ea.campus.ms.student.queryparam;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQueryParam extends BaseQueryParam {
    private String name;
    private String surname;
}
