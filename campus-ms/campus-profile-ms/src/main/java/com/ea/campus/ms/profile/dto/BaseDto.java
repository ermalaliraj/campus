package com.ea.campus.ms.profile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDto {
    private String id;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdate;

    public BaseDto(String id) {
        this.id = id;
    }
}
