package com.ea.campus.ms.student.queryparam.mapper;

import com.ea.campus.ms.student.queryparam.StudentQueryParam;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class StudentQueryMapper extends BaseQueryMapper<StudentQueryParam> {

    public Query toQuery(StudentQueryParam params) {
        Query query = super.toQuery(params);
        like(query, "name", params.getName());
        like(query, "surname", params.getSurname());
        return query;
    }

}
