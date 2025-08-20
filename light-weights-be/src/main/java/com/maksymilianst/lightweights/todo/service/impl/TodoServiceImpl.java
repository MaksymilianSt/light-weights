package com.maksymilianst.lightweights.todo.service.impl;


import com.maksymilianst.lightweights.todo.TodoDto;
import com.maksymilianst.lightweights.todo.TodoMapper;
import com.maksymilianst.lightweights.todo.TodoRepository;
import com.maksymilianst.lightweights.todo.model.Priority;
import com.maksymilianst.lightweights.todo.model.Todo;
import com.maksymilianst.lightweights.todo.service.TodoService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class TodoServiceImpl implements TodoService {
    final static Priority DEFAULT_PRIORITY = Priority.LOW;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    @Override
    public List<TodoDto> getAllForUser(User user) {
        return todoMapper.toDtos(todoRepository.findAllByUserId(user.getId()));
    }

    @Override
    public TodoDto create(TodoDto todoDto, User user) {
        Todo toCreate = new Todo();

        setupTodo(todoDto, toCreate);
        toCreate.setUser(user);

        Todo created = todoRepository.save(toCreate);
        return todoMapper.toDto(created);
    }

    @Override
    public TodoDto update(Integer todoId, TodoDto todoDto) {
        Todo toUpdate = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found"));

        setupTodo(todoDto, toUpdate);

        Todo updated = todoRepository.save(toUpdate);
        return todoMapper.toDto(updated);
    }

    @Override
    public void delete(Integer todoId) {
        todoRepository.deleteById(todoId);
    }

    private void setupTodo(TodoDto src, Todo dest) {
        dest.setNote(src.getNote());
        dest.setDone(src.getDone());
        dest.setPriority(
                src.getPriority() != null ?
                        src.getPriority()
                        : DEFAULT_PRIORITY
        );
        dest.setDeadline(src.getDeadline());
    }
}
