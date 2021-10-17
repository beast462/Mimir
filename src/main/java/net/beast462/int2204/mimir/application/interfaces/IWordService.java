package net.beast462.int2204.mimir.application.interfaces;

import netscape.javascript.JSObject;

import java.util.List;

public interface IWordService {
    JSObject absoluteSearch(String text);
    JSObject advanceSearch(String text);
}
