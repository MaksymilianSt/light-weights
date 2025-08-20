package com.maksymilianst.lightweights.todo.service.impl;


import com.maksymilianst.lightweights.todo.TodoRepository;
import com.maksymilianst.lightweights.todo.service.TodoAccessControlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("todoAccessControlService")
@RequiredArgsConstructor
@Slf4j
class TodoAccessControlServiceImpl implements TodoAccessControlService {

    private final TodoRepository todoRepository;

    @Override
    public boolean hasAccess(Integer todoId, Integer userId) {
        boolean hasAccess = todoRepository.existsByIdAndUserId(todoId, userId);

        if (!hasAccess) {
            log.info("Access denied: User with id {} attempted to access todo with id {}", userId, todoId);
        }

        return hasAccess;
    }
}
