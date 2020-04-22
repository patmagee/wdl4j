package io.github.patmagee.wdl4j.v1.errors;

import java.net.URI;

public class SyntaxError {

    private final Object offendingSymbol;
    private final int line;
    private final int position;
    private final String message;
    private final URI fileName;

    SyntaxError(URI fileName, Object offendingSymbol, int line, int position, String message) {
        this.fileName = fileName;
        this.offendingSymbol = offendingSymbol;
        this.line = line;
        this.position = position;
        this.message = message;
    }

    public Object getOffendingSymbol() {
        return offendingSymbol;
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }

    public String getMessage() {
        return message;
    }

    public URI getFileName() {
        return fileName;
    }

    @Override
    public String toString() {
        String fileNameString = "";
        if (fileName != null) {
            fileNameString = fileName.toString() + " - ";

        }

        return fileNameString + "line " + line + ":" + position + " " + message;
    }
}