package dev.hbeck.kdl.objects;

import dev.hbeck.kdl.print.PrintConfig;
import dev.hbeck.kdl.print.PrintUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

/**
 * An object presenting a key=value pair in a KDL document. Only used during parsing.
 */
public record KDLProperty(String key, KDLValue<?> value) implements KDLObject {
    public KDLProperty(String key, KDLValue<?> value) {
        this.key = Objects.requireNonNull(key);
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public void writeKDL(Writer writer, PrintConfig printConfig) throws IOException {
        if (value instanceof KDLNull && !printConfig.shouldPrintNullProps()) {
            return;
        }

        PrintUtil.writeStringQuotedAppropriately(writer, key, true, printConfig);
        writer.write('=');
        value.writeKDL(writer, printConfig);
    }

    @Override
    public String toString() {
        return "KDLProperty{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KDLProperty(String key1, KDLValue<?> value1))) return false;
        return Objects.equals(key, key1) && Objects.equals(value, value1);
    }

}
