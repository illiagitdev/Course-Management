package com.courses.management.common;

import com.courses.management.common.command.util.InputString;

public interface Command {
    int COMMAND_NAME = 0;
    String command();

    void process(InputString input);

    default boolean canProcess(InputString userInput) {
        String[] splitFormat = command().split("\\|");
        String[] inputPatrameters = userInput.getParameters();
        return inputPatrameters[COMMAND_NAME].equals(splitFormat[COMMAND_NAME]);
    }
}
