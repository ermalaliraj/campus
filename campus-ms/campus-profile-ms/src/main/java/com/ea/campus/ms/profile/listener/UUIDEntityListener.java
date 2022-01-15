package com.ea.campus.ms.profile.listener;

import com.ea.campus.ms.profile.model.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class UUIDEntityListener {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    @PrePersist
    public void generateId(BaseEntity entity) {
        if (entity.getId() == null) {
            LocalDateTime now = LocalDateTime.now();
            String uuid = DATE_TIME_FORMATTER.format(now);
            entity.setId(uuid);
        }
    }
}
