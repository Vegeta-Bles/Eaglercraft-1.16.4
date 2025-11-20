/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.openal.AL
 *  org.lwjgl.openal.AL10
 *  org.lwjgl.openal.ALC
 *  org.lwjgl.openal.ALC10
 *  org.lwjgl.openal.ALCCapabilities
 *  org.lwjgl.openal.ALCapabilities
 *  org.lwjgl.system.MemoryStack
 */
package net.minecraft.client.sound;

import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.sound.AlUtil;
import net.minecraft.client.sound.SoundListener;
import net.minecraft.client.sound.Source;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

public class SoundEngine {
    private static final Logger LOGGER = LogManager.getLogger();
    private long devicePointer;
    private long contextPointer;
    private static final SourceSet EMPTY_SOURCE_SET = new SourceSet(){

        @Nullable
        public Source createSource() {
            return null;
        }

        public boolean release(Source source) {
            return false;
        }

        public void close() {
        }

        public int getMaxSourceCount() {
            return 0;
        }

        public int getSourceCount() {
            return 0;
        }
    };
    private SourceSet streamingSources = EMPTY_SOURCE_SET;
    private SourceSet staticSources = EMPTY_SOURCE_SET;
    private final SoundListener listener = new SoundListener();

    public void init() {
        this.devicePointer = SoundEngine.openDevice();
        ALCCapabilities aLCCapabilities = ALC.createCapabilities((long)this.devicePointer);
        if (AlUtil.checkAlcErrors(this.devicePointer, "Get capabilities")) {
            throw new IllegalStateException("Failed to get OpenAL capabilities");
        }
        if (!aLCCapabilities.OpenALC11) {
            throw new IllegalStateException("OpenAL 1.1 not supported");
        }
        this.contextPointer = ALC10.alcCreateContext((long)this.devicePointer, (IntBuffer)null);
        ALC10.alcMakeContextCurrent((long)this.contextPointer);
        int _snowman2 = this.getMonoSourceCount();
        int _snowman3 = MathHelper.clamp((int)MathHelper.sqrt(_snowman2), 2, 8);
        int _snowman4 = MathHelper.clamp(_snowman2 - _snowman3, 8, 255);
        this.streamingSources = new SourceSetImpl(_snowman4);
        this.staticSources = new SourceSetImpl(_snowman3);
        ALCapabilities _snowman5 = AL.createCapabilities((ALCCapabilities)aLCCapabilities);
        AlUtil.checkErrors("Initialization");
        if (!_snowman5.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
        }
        AL10.alEnable((int)512);
        if (!_snowman5.AL_EXT_LINEAR_DISTANCE) {
            throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
        }
        AlUtil.checkErrors("Enable per-source distance models");
        LOGGER.info("OpenAL initialized.");
    }

    private int getMonoSourceCount() {
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            int n = ALC10.alcGetInteger((long)this.devicePointer, (int)4098);
            if (AlUtil.checkAlcErrors(this.devicePointer, "Get attributes size")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            IntBuffer _snowman2 = memoryStack.mallocInt(n);
            ALC10.alcGetIntegerv((long)this.devicePointer, (int)4099, (IntBuffer)_snowman2);
            if (AlUtil.checkAlcErrors(this.devicePointer, "Get attributes")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            _snowman = 0;
            while (_snowman < n) {
                if ((_snowman = _snowman2.get(_snowman++)) == 0) {
                    break;
                }
                _snowman = _snowman2.get(_snowman++);
                if (_snowman != 4112) continue;
                int n2 = _snowman;
                return n2;
            }
        }
        return 30;
    }

    private static long openDevice() {
        for (int i = 0; i < 3; ++i) {
            long l = ALC10.alcOpenDevice((ByteBuffer)null);
            if (l == 0L || AlUtil.checkAlcErrors(l, "Open device")) continue;
            return l;
        }
        throw new IllegalStateException("Failed to open OpenAL device");
    }

    public void close() {
        this.streamingSources.close();
        this.staticSources.close();
        ALC10.alcDestroyContext((long)this.contextPointer);
        if (this.devicePointer != 0L) {
            ALC10.alcCloseDevice((long)this.devicePointer);
        }
    }

    public SoundListener getListener() {
        return this.listener;
    }

    @Nullable
    public Source createSource(RunMode mode) {
        return (mode == RunMode.STREAMING ? this.staticSources : this.streamingSources).createSource();
    }

    public void release(Source source) {
        if (!this.streamingSources.release(source) && !this.staticSources.release(source)) {
            throw new IllegalStateException("Tried to release unknown channel");
        }
    }

    public String getDebugString() {
        return String.format("Sounds: %d/%d + %d/%d", this.streamingSources.getSourceCount(), this.streamingSources.getMaxSourceCount(), this.staticSources.getSourceCount(), this.staticSources.getMaxSourceCount());
    }

    static class SourceSetImpl
    implements SourceSet {
        private final int maxSourceCount;
        private final Set<Source> sources = Sets.newIdentityHashSet();

        public SourceSetImpl(int maxSourceCount) {
            this.maxSourceCount = maxSourceCount;
        }

        @Override
        @Nullable
        public Source createSource() {
            if (this.sources.size() >= this.maxSourceCount) {
                LOGGER.warn("Maximum sound pool size {} reached", (Object)this.maxSourceCount);
                return null;
            }
            Source source = Source.create();
            if (source != null) {
                this.sources.add(source);
            }
            return source;
        }

        @Override
        public boolean release(Source source) {
            if (!this.sources.remove(source)) {
                return false;
            }
            source.close();
            return true;
        }

        @Override
        public void close() {
            this.sources.forEach(Source::close);
            this.sources.clear();
        }

        @Override
        public int getMaxSourceCount() {
            return this.maxSourceCount;
        }

        @Override
        public int getSourceCount() {
            return this.sources.size();
        }
    }

    static interface SourceSet {
        @Nullable
        public Source createSource();

        public boolean release(Source var1);

        public void close();

        public int getMaxSourceCount();

        public int getSourceCount();
    }

    public static enum RunMode {
        STATIC,
        STREAMING;

    }
}

