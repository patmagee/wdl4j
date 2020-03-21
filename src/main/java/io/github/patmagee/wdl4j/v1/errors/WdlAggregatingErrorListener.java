package io.github.patmagee.wdl4j.v1.errors;

import com.sun.jndi.toolkit.url.Uri;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WdlAggregatingErrorListener extends BaseErrorListener {

    private List<SyntaxError> errors = new ArrayList<>();

    private URI context;


    public WdlAggregatingErrorListener(URI context){
        this.context = context;
    }

    public WdlAggregatingErrorListener(){
        this.context = null;
    }



    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        SyntaxError error = new SyntaxError(context,offendingSymbol, line, charPositionInLine, msg);
        errors.add(error);
    }

    public List<SyntaxError> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return errors != null && errors.size() > 0;
    }


}
