package com.courses.management.common.command.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputString {
    private static final Logger LOG = LogManager.getLogger(InputString.class);
    private String command;

    public InputString(String command) {
        this.command = command;
    }

    public void validateParameters(String inputString){
        int inputLength = getParametersSize(inputString);
        int commandLength = getLength();
        if (inputLength != getLength()) {
            String message = String.format("Invalid number of parameters separated by |, expected %s, but was %s.",
                    commandLength, inputLength);
            LOG.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    public int getLength(){
        return getParametersSize(command);
    }

    public String[] getParameters() {
        return command.split("\\|");
    }

    private int getParametersSize(String command) {
        return command.split("\\|").length;
    }
}
