package net.beast462.int2204.mimir.application.handlers;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class BaseURLHandler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL url) throws IOException
    {
        return new BaseURLConnection(url);
    }
}
