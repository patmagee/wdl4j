package io.github.patmagee.wdl4j.v1.api;

import java.io.IOException;
import java.net.URI;

public interface WdlResolver {

    boolean canResolve(URI resource);

    String resolveWdlToString(URI resource) throws IOException;

    String resolveWdlToString(URI resource, URI context) throws IOException;

}
