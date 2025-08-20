package com.maksymilianst.lightweights.todo;

import com.maksymilianst.lightweights.todo.model.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TodoDto {
    private Integer id;
    @NotBlank
    private String note;
    private Boolean done;
    private LocalDate deadline;
    private Priority priority;
}
