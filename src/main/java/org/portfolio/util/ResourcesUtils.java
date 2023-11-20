package org.portfolio.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourcesUtils {
    private ResourcesUtils() {
    }

    public static File fetchResource(String relativeFilePath) throws URISyntaxException {
        URL resource = ResourcesUtils.class.getClassLoader().getResource(relativeFilePath);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return new File(resource.toURI());
        }
    }
}
