package org.sackmesser.web.rest.dto;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class LoggerDTO {

    private String name;

    private String level;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }

    @JsonCreator
    public LoggerDTO() {
    }

    @Override
    public String toString() {
        return "LoggerDTO{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
