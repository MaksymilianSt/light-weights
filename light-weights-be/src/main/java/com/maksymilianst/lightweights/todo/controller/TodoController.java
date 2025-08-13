package com.maksymilianst.lightweights.todo.controller;

import com.maksymilianst.lightweights.todo.dto.TodoDto;
import com.maksymilianst.lightweights.todo.service.TodoService;
import com.maksymilianst.lightweights.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TodoController.URL)
@RequiredArgsConstructor
public class TodoController {
    public static final String URL = "/api/todos";
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(todoService.getAllForUser(user));
    }

    @PostMapping
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) {
        TodoDto created = todoService.create(todoDto, user);

        return ResponseEntity
                .created(URI.create(URL + "/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{todoId}")
    @PreAuthorize("@todoAccessControlService.hasAccess(#todoId, principal.id)")
    ResponseEntity<TodoDto> update(@PathVariable Integer todoId, @RequestBody TodoDto todoDto) {
        TodoDto updated = todoService.update(todoId, todoDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{todoId}")
    @PreAuthorize("@todoAccessControlService.hasAccess(#todoId, principal.id)")
    ResponseEntity<TodoDto> update(@PathVariable Integer todoId) {
        todoService.delete(todoId);
        return ResponseEntity.noContent().build();
    }

}
