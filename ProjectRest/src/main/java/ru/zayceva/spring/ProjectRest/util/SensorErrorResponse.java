package ru.zayceva.spring.ProjectRest.util;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorErrorResponse {
    @JsonProperty
    private String message;
    @JsonProperty
    private long timestamp;

    public SensorErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }


}
