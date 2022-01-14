package com.ea.campus.ms.student.exception;

import com.ea.campus.ms.student.model.BaseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(Class<? extends BaseEntity> clazz) {
        super(String.format("Cannot find entity of type [%s]", clazz.getSimpleName()));
    }

    public EntityNotFoundException(Class<? extends BaseEntity> clazz, String id) {
        this(clazz, id, null);
    }

    public EntityNotFoundException(Class<? extends BaseEntity> clazz, String id, String customMessage) {
        this(clazz, "id", id, customMessage);
    }

    public EntityNotFoundException(Class<? extends BaseEntity> clazz, String fieldName, String id, String customMessage) {
        super("Cannot find entity of type [" + clazz.getSimpleName() + "] with " + fieldName
                + " [" + (id != null ? id : "any") + "]"
                + ". "
                + (customMessage != null ? customMessage : ""));
    }

}
