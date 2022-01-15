package com.ea.campus.ms.profile.model;

import com.ea.campus.ms.profile.listener.UUIDEntityListener;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, UUIDEntityListener.class})
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {

    @Id
    @Setter
    @Column(name = "uuid")
    @ToString.Include
    @EqualsAndHashCode.Include
    private String id;

    @CreatedDate
    @Column(name = "creationdate", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @LastModifiedDate
    @Column(name = "lastupdate")
    private LocalDateTime lastUpdate;

    @Override
    public String toString() {
        ReflectionToStringBuilder sb = new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        sb.setExcludeNullValues(true);
        return sb.toString();
    }

}
