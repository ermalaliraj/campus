package com.ea.campus.ms.profile.queryparam;

import lombok.Data;

@Data
public class BaseQueryParam {
    private String id;
    private Long creationDateFrom;
    private Long creationDateTo;
}
