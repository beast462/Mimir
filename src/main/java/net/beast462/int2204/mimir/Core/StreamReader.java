package net.beast462.int2204.mimir.Core;

import java.io.InputStream;
import java.util.Scanner;

public class StreamReader {
    private final InputStream stream;

    public StreamReader(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var scanner = new Scanner(stream);

        while (scanner.hasNextLine())
            builder.append(scanner.nextLine());

        return builder.toString();
    }
}
