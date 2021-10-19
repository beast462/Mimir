package net.beast462.int2204.mimir.application.handlers;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class BaseURLHandlerFactory implements URLStreamHandlerFactory {
    public URLStreamHandler createURLStreamHandler(String protocol) {
        if (protocol.equals("mimir-resources")) {
            return new BaseURLHandler();
        }

        return null;
    }
}
