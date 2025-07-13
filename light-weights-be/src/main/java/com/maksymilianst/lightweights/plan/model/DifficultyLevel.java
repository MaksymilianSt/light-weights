package com.maksymilianst.lightweights.plan.model;

public enum DifficultyLevel {
    UNKNOWN(0),
    BEGINNER(1),
    INTERMEDIATE(2),
    ADVANCED(3);

    private final int lvl;

    DifficultyLevel(int lvl) {
        this.lvl = lvl;
    }

    public static DifficultyLevel fromStringIgnoreCase(String input) {
        if (input == null) return UNKNOWN;

        for (DifficultyLevel level : values()) {
            if (level.name().equalsIgnoreCase(input)) {
                return level;
            }
        }
        return UNKNOWN;
    }
}
