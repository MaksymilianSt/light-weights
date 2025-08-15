package com.maksymilianst.lightweights.todo.mapper;

import com.maksymilianst.lightweights.todo.dto.TodoDto;
import com.maksymilianst.lightweights.todo.model.Todo;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    TodoDto toDto(Todo todo);

    default List<TodoDto> toDtos(Collection<Todo> todos) {
        return todos.stream()
                .sorted(defaultTodoComparator())
                .map(this::toDto)
                .toList();
    }

    default Comparator<Todo> defaultTodoComparator() {
        return Comparator
                .comparing(
                    Todo::getCreatedAt).reversed()
                .thenComparing(
                    Comparator.comparing(Todo::getPriority).reversed())
                .thenComparing(
                    Comparator.comparing(Todo::getDone));
    }
}
