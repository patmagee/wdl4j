package io.github.patmagee.wdl4j.v1.errors;

import lombok.Getter;

import java.net.URI;

@Getter
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


    @Override
    public String toString() {
        String fileNameString = "";
        if (fileName != null){
            fileNameString = fileName.toString() + " - ";

        }

        return fileNameString + "line " + line + ":" + position + " " + message;
    }
}