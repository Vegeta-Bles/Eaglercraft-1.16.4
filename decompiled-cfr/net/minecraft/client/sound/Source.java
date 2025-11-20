/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.openal.AL10
 */
package net.minecraft.client.sound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.sound.AlUtil;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.StaticSound;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

public class Source {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int pointer;
    private final AtomicBoolean playing = new AtomicBoolean(true);
    private int bufferSize = 16384;
    @Nullable
    private AudioStream stream;

    @Nullable
    static Source create() {
        int[] nArray = new int[1];
        AL10.alGenSources((int[])nArray);
        if (AlUtil.checkErrors("Allocate new source")) {
            return null;
        }
        return new Source(nArray[0]);
    }

    private Source(int pointer) {
        this.pointer = pointer;
    }

    public void close() {
        if (this.playing.compareAndSet(true, false)) {
            AL10.alSourceStop((int)this.pointer);
            AlUtil.checkErrors("Stop");
            if (this.stream != null) {
                try {
                    this.stream.close();
                }
                catch (IOException iOException) {
                    LOGGER.error("Failed to close audio stream", (Throwable)iOException);
                }
                this.removeProcessedBuffers();
                this.stream = null;
            }
            AL10.alDeleteSources((int[])new int[]{this.pointer});
            AlUtil.checkErrors("Cleanup");
        }
    }

    public void play() {
        AL10.alSourcePlay((int)this.pointer);
    }

    private int getSourceState() {
        if (!this.playing.get()) {
            return 4116;
        }
        return AL10.alGetSourcei((int)this.pointer, (int)4112);
    }

    public void pause() {
        if (this.getSourceState() == 4114) {
            AL10.alSourcePause((int)this.pointer);
        }
    }

    public void resume() {
        if (this.getSourceState() == 4115) {
            AL10.alSourcePlay((int)this.pointer);
        }
    }

    public void stop() {
        if (this.playing.get()) {
            AL10.alSourceStop((int)this.pointer);
            AlUtil.checkErrors("Stop");
        }
    }

    public boolean isStopped() {
        return this.getSourceState() == 4116;
    }

    public void setPosition(Vec3d vec3d) {
        AL10.alSourcefv((int)this.pointer, (int)4100, (float[])new float[]{(float)vec3d.x, (float)vec3d.y, (float)vec3d.z});
    }

    public void setPitch(float f) {
        AL10.alSourcef((int)this.pointer, (int)4099, (float)f);
    }

    public void setLooping(boolean bl) {
        AL10.alSourcei((int)this.pointer, (int)4103, (int)(bl ? 1 : 0));
    }

    public void setVolume(float f) {
        AL10.alSourcef((int)this.pointer, (int)4106, (float)f);
    }

    public void disableAttenuation() {
        AL10.alSourcei((int)this.pointer, (int)53248, (int)0);
    }

    public void setAttenuation(float f) {
        AL10.alSourcei((int)this.pointer, (int)53248, (int)53251);
        AL10.alSourcef((int)this.pointer, (int)4131, (float)f);
        AL10.alSourcef((int)this.pointer, (int)4129, (float)1.0f);
        AL10.alSourcef((int)this.pointer, (int)4128, (float)0.0f);
    }

    public void setRelative(boolean bl) {
        AL10.alSourcei((int)this.pointer, (int)514, (int)(bl ? 1 : 0));
    }

    public void setBuffer(StaticSound staticSound) {
        staticSound.getStreamBufferPointer().ifPresent(n -> AL10.alSourcei((int)this.pointer, (int)4105, (int)n));
    }

    public void setStream(AudioStream stream) {
        this.stream = stream;
        AudioFormat audioFormat = stream.getFormat();
        this.bufferSize = Source.getBufferSize(audioFormat, 1);
        this.method_19640(4);
    }

    private static int getBufferSize(AudioFormat format, int time) {
        return (int)((float)(time * format.getSampleSizeInBits()) / 8.0f * (float)format.getChannels() * format.getSampleRate());
    }

    private void method_19640(int n2) {
        if (this.stream != null) {
            try {
                for (_snowman = 0; _snowman < n2; ++_snowman) {
                    ByteBuffer byteBuffer = this.stream.getBuffer(this.bufferSize);
                    if (byteBuffer == null) continue;
                    new StaticSound(byteBuffer, this.stream.getFormat()).takeStreamBufferPointer().ifPresent(n -> AL10.alSourceQueueBuffers((int)this.pointer, (int[])new int[]{n}));
                }
            }
            catch (IOException iOException) {
                LOGGER.error("Failed to read from audio stream", (Throwable)iOException);
            }
        }
    }

    public void tick() {
        if (this.stream != null) {
            int n = this.removeProcessedBuffers();
            this.method_19640(n);
        }
    }

    private int removeProcessedBuffers() {
        int n = AL10.alGetSourcei((int)this.pointer, (int)4118);
        if (n > 0) {
            int[] nArray = new int[n];
            AL10.alSourceUnqueueBuffers((int)this.pointer, (int[])nArray);
            AlUtil.checkErrors("Unqueue buffers");
            AL10.alDeleteBuffers((int[])nArray);
            AlUtil.checkErrors("Remove processed buffers");
        }
        return n;
    }
}

