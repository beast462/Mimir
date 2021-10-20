package net.beast462.int2204.mimir.core.webview;

import net.beast462.int2204.mimir.core.bridge.EngineContainer;
import netscape.javascript.JSObject;

import java.util.function.Function;

public class JSObjectUtils {
    public static JSObject newNullObject() {
        return (JSObject) EngineContainer.getEngine().executeScript("null");
    }

    public static JSObject newObject() {
        return (JSObject) EngineContainer.getEngine().executeScript("new Object()");
    }

    public static JSObject newArray() {
        return (JSObject) EngineContainer.getEngine().executeScript("Array(0)");
    }

    public static void forEach(JSObject obj, Function<JSObject, Void> callback) {
        var length = (Integer) obj.getMember("length");

        if (length == null) return;

        for (var i = 0; i < length; ++i)
            callback.apply((JSObject) obj.getSlot(i));
    }

    public static boolean hasKey(JSObject obj, String member) {
        return (boolean) obj.call("hasOwnProperty", member);
    }
}
