package com.maksymilianst.lightweights.todo.service;

import com.maksymilianst.lightweights.todo.dto.TodoDto;
import com.maksymilianst.lightweights.user.User;

import java.util.List;

public interface TodoService {
    List<TodoDto> getAllForUser(User user);

    TodoDto create(TodoDto todoDto, User user);

    TodoDto update(Integer todoId, TodoDto todoDto);

    void delete(Integer todoId);

}
