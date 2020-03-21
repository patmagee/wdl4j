package io.github.patmagee.wdl4j.v1.imports;

import io.github.patmagee.wdl4j.v1.api.WdlResolver;
import io.github.patmagee.wdl4j.v1.exception.WdlResolutionException;
import io.github.patmagee.wdl4j.v1.util.UriUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class UrlWdlResolver implements WdlResolver {

    @Override
    public boolean canResolve(URI resource) {
        return UriUtils.isUrl(resource);
    }

    @Override
    public String resolveWdlToString(URI resource) throws IOException {
        return resolveWdlToString(resource, null);
    }

    @Override
    public String resolveWdlToString(URI resource, URI context) throws IOException {
        if (resource == null) {
            throw new WdlResolutionException("Could not resolve a null import");
        }
        try {
            URL urlToResolve = resource.toURL();
            if (context != null) {
                urlToResolve = context.resolve(resource).toURL();
            }
            URLConnection connection = urlToResolve.openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                return stringBuilder.toString();
            }
        } catch (MalformedURLException e) {
            throw new WdlResolutionException(e.getMessage(), e);
        }
    }
}
