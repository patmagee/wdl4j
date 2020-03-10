package org.openwdl.wdl4j.v1;

import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.openwdl.wdl.v1.parser.WdlLexer;
import org.openwdl.wdl.v1.parser.WdlParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;

public class WdlV1DocumentFactory {

    public static Document from(String wdl) {
        WdlParser parser = getParser(wdl);
        WdlV1DocumentVisitor visitor = new WdlV1DocumentVisitor();
        return visitor.visitDocument(parser.document());
    }

    //    public static Document from(File file) throws IOException {
    //        return from(Files.readString(file.toPath()));
    //    }

    public static Document from(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return from(stringBuilder.toString());
        }
    }

    private static WdlParser getParser(String inp) {
        CodePointBuffer codePointBuffer = CodePointBuffer.withBytes(ByteBuffer.wrap(inp.getBytes()));
        WdlLexer lexer = new WdlLexer(CodePointCharStream.fromBuffer(codePointBuffer));
        WdlParser parser = new WdlParser(new CommonTokenStream(lexer));
        return parser;
    }
}
