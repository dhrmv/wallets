package com.wallets.api.base.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.wallets.api.base.utils.DateUtil;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {

    public static final String SUCCESS_STATUS = "success";
    private final int status;
    private final String error;
    private final LocalDateTime timestamp = DateUtil.now();
    private final T data;

    @JsonCreator
    public ApiResponse(
            @JsonProperty("status") int status,
            @JsonProperty("error") String error,
            @JsonProperty("data") T data) {
        this.status = status;
        this.error = error;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, Throwable error, T data) {
        this(status.value(), error.getMessage(), data);
    }

    public ApiResponse(HttpStatus status, T data) {
        this(status.value(), null, data);
    }

    public static ApiResponse<String> ok() {
        return ok(SUCCESS_STATUS);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, Throwable error) {
        return new ApiResponse<>(status.value(), error.getMessage(), null);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String error) {
        return new ApiResponse<>(status.value(), error, null);
    }

    @SuppressWarnings("unchecked")
    public static <T> ApiResponse<T> fieldsError(List<FieldError> errors) {
        return new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                "fieldsError",
                new FieldsError(errors)
        );
    }

    public static <T> ApiResponse<T> fieldError(FieldError error) {
        return fieldsError(ImmutableList.of(error));
    }

    public int getStatus() {
        return status;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("status", status)
                .add("error", error)
                .add("timestamp", timestamp)
                .add("data", data)
                .toString();
    }

    public static class FieldsError {
        private final List<FieldError> fields;

        public FieldsError(List<FieldError> fields) {
            this.fields = fields;
        }

        public List<FieldError> getFields() {
            return fields;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("fields", fields)
                    .toString();
        }
    }

    public static class FieldError {
        private final String name;
        private final String error;

        public FieldError(String name, String error) {
            this.name = name;
            this.error = error;
        }

        public String getName() {
            return name;
        }

        public String getError() {
            return error;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("error", error)
                    .toString();
        }
    }
}
