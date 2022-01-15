package com.ea.campus.ms.profile.queryparam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserQueryParam extends BaseQueryParam {
    private String searchText;
    private Boolean enabled;
}
