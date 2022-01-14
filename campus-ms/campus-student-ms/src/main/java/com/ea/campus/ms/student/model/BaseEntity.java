package com.ea.campus.ms.student.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public abstract class BaseEntity {
    @Id
    private String id;
    @CreatedDate
    private LocalDateTime creationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        ReflectionToStringBuilder sb = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        sb.setExcludeNullValues(true);
        return sb.toString();
    }

}
