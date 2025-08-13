package com.maksymilianst.lightweights.todo.service;

public interface TodoAccessControlService {
    boolean hasAccess(Integer todoId, Integer userId);
}
