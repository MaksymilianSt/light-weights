package com.maksymilianst.lightweights.todo.model;

public enum Priority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int importance;

    Priority(int importance) {
        this.importance = importance;
    }
}
