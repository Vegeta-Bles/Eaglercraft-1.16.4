/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.openal.AL10
 */
package net.minecraft.client.sound;

import java.nio.ByteBuffer;
import java.util.OptionalInt;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sound.AlUtil;
import org.lwjgl.openal.AL10;

public class StaticSound {
    @Nullable
    private ByteBuffer sample;
    private final AudioFormat format;
    private boolean hasBuffer;
    private int streamBufferPointer;

    public StaticSound(ByteBuffer sample, AudioFormat format) {
        this.sample = sample;
        this.format = format;
    }

    OptionalInt getStreamBufferPointer() {
        if (!this.hasBuffer) {
            if (this.sample == null) {
                return OptionalInt.empty();
            }
            int n = AlUtil.getFormatId(this.format);
            int[] _snowman2 = new int[1];
            AL10.alGenBuffers((int[])_snowman2);
            if (AlUtil.checkErrors("Creating buffer")) {
                return OptionalInt.empty();
            }
            AL10.alBufferData((int)_snowman2[0], (int)n, (ByteBuffer)this.sample, (int)((int)this.format.getSampleRate()));
            if (AlUtil.checkErrors("Assigning buffer data")) {
                return OptionalInt.empty();
            }
            this.streamBufferPointer = _snowman2[0];
            this.hasBuffer = true;
            this.sample = null;
        }
        return OptionalInt.of(this.streamBufferPointer);
    }

    public void close() {
        if (this.hasBuffer) {
            AL10.alDeleteBuffers((int[])new int[]{this.streamBufferPointer});
            if (AlUtil.checkErrors("Deleting stream buffers")) {
                return;
            }
        }
        this.hasBuffer = false;
    }

    public OptionalInt takeStreamBufferPointer() {
        OptionalInt optionalInt = this.getStreamBufferPointer();
        this.hasBuffer = false;
        return optionalInt;
    }
}

