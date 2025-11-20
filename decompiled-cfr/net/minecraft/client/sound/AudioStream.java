/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.sound;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;

public interface AudioStream
extends Closeable {
    public AudioFormat getFormat();

    public ByteBuffer getBuffer(int var1) throws IOException;
}

