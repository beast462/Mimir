package net.beast462.int2204.mimir.application.handlers;

import net.beast462.int2204.mimir.Main;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class BaseURLHandler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL url) throws IOException {
        var resourcePath = "/" + url.getHost() + url.getPath();
        var resourceUrl = Main.class.getResource(resourcePath);

        if (resourceUrl == null)
            throw new IOException("Resource not found: " + resourcePath);

        return resourceUrl.openConnection();
    }
}
