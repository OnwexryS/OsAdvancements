package com.minerival.develop.osadvancements.LoggerTypes;

public enum LogCauses {
    SYSTEM ("&c[SYSTEM]"),
    LOG ("&7[LOG]"),
    ERROR ("&c[ERROR]"),
    DEBUG ("&e[DEBUG]");
    private final String type;
    LogCauses(String type) {
        this.type = type;
    }
    @Override
    public String toString(){
        return this.type;
    }

}
