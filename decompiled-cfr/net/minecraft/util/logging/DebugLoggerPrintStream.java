/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.logging;

import java.io.OutputStream;
import net.minecraft.util.logging.LoggerPrintStream;

public class DebugLoggerPrintStream
extends LoggerPrintStream {
    public DebugLoggerPrintStream(String string, OutputStream outputStream) {
        super(string, outputStream);
    }

    @Override
    protected void log(String message) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        StackTraceElement _snowman2 = stackTraceElementArray[Math.min(3, stackTraceElementArray.length)];
        LOGGER.info("[{}]@.({}:{}): {}", (Object)this.name, (Object)_snowman2.getFileName(), (Object)_snowman2.getLineNumber(), (Object)message);
    }
}

