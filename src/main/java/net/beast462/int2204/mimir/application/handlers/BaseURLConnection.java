package net.beast462.int2204.mimir.application.handlers;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class BaseURLConnection extends URLConnection {
    public BaseURLConnection(URL url) {
        super(url);
    }

    @Override
    public void connect() throws IOException {

    }
}
