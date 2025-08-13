package com.maksymilianst.lightweights.todo.dto;

import com.maksymilianst.lightweights.todo.model.Priority;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TodoDto {
    private Integer id;
    @NotEmpty
    private String note;
    private Boolean done;
    private LocalDate deadline;
    private Priority priority;
}
