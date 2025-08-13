package com.maksymilianst.lightweights.todo.mapper;

import com.maksymilianst.lightweights.todo.dto.TodoDto;
import com.maksymilianst.lightweights.todo.model.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoDto toDto(Todo todo);
}
