package io.github.patmagee.wdl4j.v1.util;

import io.github.patmagee.wdl4j.v1.exception.NamespaceException;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class UriUtils {

    private final static List<String> VALID_URL_SCHEMES = Arrays.asList("http", "https", "ftp");

    public static boolean isUrl(URI uri) {
        return uri.getScheme() != null && VALID_URL_SCHEMES.contains(uri.getScheme());
    }

    public static boolean isFile(URI uri) {
        if (uri.getScheme() == null || uri.getScheme().equals("file")) {
            File local = new File(uri);
            return local.exists() && (local.isFile() || local.isDirectory());
        }
        return false;
    }

    public static String getImportNamepsaceFromUri(URI uri) {
        if (isFile(uri)) {
            File file = new File(uri);
            String name = file.getName();
            return name.substring(0, name.indexOf("."));
        } else if (isUrl(uri)) {
            String path = uri.getPath();
            if (path != null && !"/".equals(path)) {
                if (path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                String name = path.substring(path.lastIndexOf("/"));
                return name.substring(0, name.indexOf("."));
            }

        }
        throw new NamespaceException("Could not extract namespace from import: " + uri.toString());
    }

}
