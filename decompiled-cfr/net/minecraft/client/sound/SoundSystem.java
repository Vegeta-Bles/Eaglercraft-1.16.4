/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashMultimap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.Marker
 *  org.apache.logging.log4j.MarkerManager
 */
package net.minecraft.client.sound;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.sound.AudioStream;
import net.minecraft.client.sound.Channel;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEngine;
import net.minecraft.client.sound.SoundExecutor;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.SoundListener;
import net.minecraft.client.sound.SoundLoader;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.Source;
import net.minecraft.client.sound.StaticSound;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.resource.ResourceManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class SoundSystem {
    private static final Marker MARKER = MarkerManager.getMarker((String)"SOUNDS");
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<Identifier> unknownSounds = Sets.newHashSet();
    private final SoundManager loader;
    private final GameOptions settings;
    private boolean started;
    private final SoundEngine soundEngine = new SoundEngine();
    private final SoundListener listener = this.soundEngine.getListener();
    private final SoundLoader soundLoader;
    private final SoundExecutor taskQueue = new SoundExecutor();
    private final Channel channel = new Channel(this.soundEngine, this.taskQueue);
    private int ticks;
    private final Map<SoundInstance, Channel.SourceManager> sources = Maps.newHashMap();
    private final Multimap<SoundCategory, SoundInstance> sounds = HashMultimap.create();
    private final List<TickableSoundInstance> tickingSounds = Lists.newArrayList();
    private final Map<SoundInstance, Integer> startTicks = Maps.newHashMap();
    private final Map<SoundInstance, Integer> soundEndTicks = Maps.newHashMap();
    private final List<SoundInstanceListener> listeners = Lists.newArrayList();
    private final List<TickableSoundInstance> soundsToPlayNextTick = Lists.newArrayList();
    private final List<Sound> preloadedSounds = Lists.newArrayList();

    public SoundSystem(SoundManager loader, GameOptions settings, ResourceManager resourceManager) {
        this.loader = loader;
        this.settings = settings;
        this.soundLoader = new SoundLoader(resourceManager);
    }

    public void reloadSounds() {
        unknownSounds.clear();
        for (SoundEvent soundEvent : Registry.SOUND_EVENT) {
            Identifier identifier = soundEvent.getId();
            if (this.loader.get(identifier) != null) continue;
            LOGGER.warn("Missing sound for event: {}", (Object)Registry.SOUND_EVENT.getId(soundEvent));
            unknownSounds.add(identifier);
        }
        this.stop();
        this.start();
    }

    private synchronized void start() {
        if (this.started) {
            return;
        }
        try {
            this.soundEngine.init();
            this.listener.init();
            this.listener.setVolume(this.settings.getSoundVolume(SoundCategory.MASTER));
            this.soundLoader.loadStatic(this.preloadedSounds).thenRun(this.preloadedSounds::clear);
            this.started = true;
            LOGGER.info(MARKER, "Sound engine started");
        }
        catch (RuntimeException runtimeException) {
            LOGGER.error(MARKER, "Error starting SoundSystem. Turning off sounds & music", (Throwable)runtimeException);
        }
    }

    private float getSoundVolume(@Nullable SoundCategory soundCategory) {
        if (soundCategory == null || soundCategory == SoundCategory.MASTER) {
            return 1.0f;
        }
        return this.settings.getSoundVolume(soundCategory);
    }

    public void updateSoundVolume(SoundCategory soundCategory, float volume) {
        if (!this.started) {
            return;
        }
        if (soundCategory == SoundCategory.MASTER) {
            this.listener.setVolume(volume);
            return;
        }
        this.sources.forEach((soundInstance, sourceManager) -> {
            float f = this.getAdjustedVolume((SoundInstance)soundInstance);
            sourceManager.run(source -> {
                if (f <= 0.0f) {
                    source.stop();
                } else {
                    source.setVolume(f);
                }
            });
        });
    }

    public void stop() {
        if (this.started) {
            this.stopAll();
            this.soundLoader.close();
            this.soundEngine.close();
            this.started = false;
        }
    }

    public void stop(SoundInstance soundInstance) {
        if (this.started && (_snowman = this.sources.get(soundInstance)) != null) {
            _snowman.run(Source::stop);
        }
    }

    public void stopAll() {
        if (this.started) {
            this.taskQueue.restart();
            this.sources.values().forEach(sourceManager -> sourceManager.run(Source::stop));
            this.sources.clear();
            this.channel.close();
            this.startTicks.clear();
            this.tickingSounds.clear();
            this.sounds.clear();
            this.soundEndTicks.clear();
            this.soundsToPlayNextTick.clear();
        }
    }

    public void registerListener(SoundInstanceListener soundInstanceListener) {
        this.listeners.add(soundInstanceListener);
    }

    public void unregisterListener(SoundInstanceListener soundInstanceListener) {
        this.listeners.remove(soundInstanceListener);
    }

    public void tick(boolean bl) {
        if (!bl) {
            this.tick();
        }
        this.channel.tick();
    }

    private void tick() {
        ++this.ticks;
        this.soundsToPlayNextTick.stream().filter(SoundInstance::canPlay).forEach(this::play);
        this.soundsToPlayNextTick.clear();
        for (TickableSoundInstance tickableSoundInstance : this.tickingSounds) {
            if (!tickableSoundInstance.canPlay()) {
                this.stop(tickableSoundInstance);
            }
            tickableSoundInstance.tick();
            if (tickableSoundInstance.isDone()) {
                this.stop(tickableSoundInstance);
                continue;
            }
            float f = this.getAdjustedVolume(tickableSoundInstance);
            float f2 = this.getAdjustedPitch(tickableSoundInstance);
            Vec3d _snowman2 = new Vec3d(tickableSoundInstance.getX(), tickableSoundInstance.getY(), tickableSoundInstance.getZ());
            Channel.SourceManager _snowman3 = this.sources.get(tickableSoundInstance);
            if (_snowman3 == null) continue;
            _snowman3.run(source -> {
                source.setVolume(f);
                source.setPitch(f2);
                source.setPosition(_snowman2);
            });
        }
        Iterator<Map.Entry<SoundInstance, Channel.SourceManager>> iterator = this.sources.entrySet().iterator();
        while (iterator.hasNext()) {
            int n;
            Map.Entry<SoundInstance, Channel.SourceManager> entry = iterator.next();
            Channel.SourceManager _snowman4 = entry.getValue();
            SoundInstance _snowman5 = entry.getKey();
            float _snowman6 = this.settings.getSoundVolume(_snowman5.getCategory());
            if (_snowman6 <= 0.0f) {
                _snowman4.run(Source::stop);
                iterator.remove();
                continue;
            }
            if (!_snowman4.isStopped() || (n = this.soundEndTicks.get(_snowman5).intValue()) > this.ticks) continue;
            if (SoundSystem.isRepeatDelayed(_snowman5)) {
                this.startTicks.put(_snowman5, this.ticks + _snowman5.getRepeatDelay());
            }
            iterator.remove();
            LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", (Object)_snowman4);
            this.soundEndTicks.remove(_snowman5);
            try {
                this.sounds.remove((Object)_snowman5.getCategory(), (Object)_snowman5);
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
            if (!(_snowman5 instanceof TickableSoundInstance)) continue;
            this.tickingSounds.remove(_snowman5);
        }
        Iterator<Map.Entry<SoundInstance, Integer>> iterator2 = this.startTicks.entrySet().iterator();
        while (iterator2.hasNext()) {
            Map.Entry<SoundInstance, Integer> _snowman7 = iterator2.next();
            if (this.ticks < _snowman7.getValue()) continue;
            SoundInstance _snowman8 = _snowman7.getKey();
            if (_snowman8 instanceof TickableSoundInstance) {
                ((TickableSoundInstance)_snowman8).tick();
            }
            this.play(_snowman8);
            iterator2.remove();
        }
    }

    private static boolean canRepeatInstantly(SoundInstance soundInstance) {
        return soundInstance.getRepeatDelay() > 0;
    }

    private static boolean isRepeatDelayed(SoundInstance soundInstance) {
        return soundInstance.isRepeatable() && SoundSystem.canRepeatInstantly(soundInstance);
    }

    private static boolean shouldRepeatInstantly(SoundInstance soundInstance) {
        return soundInstance.isRepeatable() && !SoundSystem.canRepeatInstantly(soundInstance);
    }

    public boolean isPlaying(SoundInstance soundInstance) {
        if (!this.started) {
            return false;
        }
        if (this.soundEndTicks.containsKey(soundInstance) && this.soundEndTicks.get(soundInstance) <= this.ticks) {
            return true;
        }
        return this.sources.containsKey(soundInstance);
    }

    public void play(SoundInstance soundInstance2) {
        if (!this.started) {
            return;
        }
        if (!soundInstance2.canPlay()) {
            return;
        }
        WeightedSoundSet weightedSoundSet = soundInstance2.getSoundSet(this.loader);
        Identifier _snowman2 = soundInstance2.getId();
        if (weightedSoundSet == null) {
            if (unknownSounds.add(_snowman2)) {
                LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", (Object)_snowman2);
            }
            return;
        }
        Sound _snowman3 = soundInstance2.getSound();
        if (_snowman3 == SoundManager.MISSING_SOUND) {
            if (unknownSounds.add(_snowman2)) {
                LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", (Object)_snowman2);
            }
            return;
        }
        float _snowman4 = soundInstance2.getVolume();
        float _snowman5 = Math.max(_snowman4, 1.0f) * (float)_snowman3.getAttenuation();
        SoundCategory _snowman6 = soundInstance2.getCategory();
        float _snowman7 = this.getAdjustedVolume(soundInstance2);
        float _snowman8 = this.getAdjustedPitch(soundInstance2);
        SoundInstance.AttenuationType _snowman9 = soundInstance2.getAttenuationType();
        boolean _snowman10 = soundInstance2.isLooping();
        if (_snowman7 == 0.0f && !soundInstance2.shouldAlwaysPlay()) {
            LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", (Object)_snowman3.getIdentifier());
            return;
        }
        Vec3d _snowman11 = new Vec3d(soundInstance2.getX(), soundInstance2.getY(), soundInstance2.getZ());
        if (!this.listeners.isEmpty()) {
            boolean _snowman12;
            boolean bl = _snowman12 = _snowman10 || _snowman9 == SoundInstance.AttenuationType.NONE || this.listener.getPos().squaredDistanceTo(_snowman11) < (double)(_snowman5 * _snowman5);
            if (_snowman12) {
                for (SoundInstanceListener soundInstanceListener : this.listeners) {
                    soundInstanceListener.onSoundPlayed(soundInstance2, weightedSoundSet);
                }
            } else {
                LOGGER.debug(MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", (Object)_snowman2);
            }
        }
        if (this.listener.getVolume() <= 0.0f) {
            LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", (Object)_snowman2);
            return;
        }
        boolean bl = SoundSystem.shouldRepeatInstantly(soundInstance2);
        boolean _snowman13 = _snowman3.isStreamed();
        CompletableFuture<Channel.SourceManager> completableFuture = this.channel.createSource(_snowman3.isStreamed() ? SoundEngine.RunMode.STREAMING : SoundEngine.RunMode.STATIC);
        Channel.SourceManager _snowman15 = completableFuture.join();
        if (_snowman15 == null) {
            LOGGER.warn("Failed to create new sound handle");
            return;
        }
        LOGGER.debug(MARKER, "Playing sound {} for event {}", (Object)_snowman3.getIdentifier(), (Object)_snowman2);
        this.soundEndTicks.put(soundInstance2, this.ticks + 20);
        this.sources.put(soundInstance2, _snowman15);
        this.sounds.put((Object)_snowman6, (Object)soundInstance2);
        _snowman15.run(source -> {
            source.setPitch(_snowman8);
            source.setVolume(_snowman7);
            if (_snowman9 == SoundInstance.AttenuationType.LINEAR) {
                source.setAttenuation(_snowman5);
            } else {
                source.disableAttenuation();
            }
            source.setLooping(_snowman12 && !_snowman13);
            source.setPosition(_snowman11);
            source.setRelative(_snowman10);
        });
        if (!_snowman13) {
            this.soundLoader.loadStatic(_snowman3.getLocation()).thenAccept(staticSound -> _snowman15.run(source -> {
                source.setBuffer((StaticSound)staticSound);
                source.play();
            }));
        } else {
            this.soundLoader.loadStreamed(_snowman3.getLocation(), bl).thenAccept(audioStream -> _snowman15.run(source -> {
                source.setStream((AudioStream)audioStream);
                source.play();
            }));
        }
        if (soundInstance2 instanceof TickableSoundInstance) {
            this.tickingSounds.add((TickableSoundInstance)soundInstance2);
        }
    }

    public void playNextTick(TickableSoundInstance sound) {
        this.soundsToPlayNextTick.add(sound);
    }

    public void addPreloadedSound(Sound sound) {
        this.preloadedSounds.add(sound);
    }

    private float getAdjustedPitch(SoundInstance soundInstance) {
        return MathHelper.clamp(soundInstance.getPitch(), 0.5f, 2.0f);
    }

    private float getAdjustedVolume(SoundInstance soundInstance) {
        return MathHelper.clamp(soundInstance.getVolume() * this.getSoundVolume(soundInstance.getCategory()), 0.0f, 1.0f);
    }

    public void pauseAll() {
        if (this.started) {
            this.channel.execute(stream -> stream.forEach(Source::pause));
        }
    }

    public void resumeAll() {
        if (this.started) {
            this.channel.execute(stream -> stream.forEach(Source::resume));
        }
    }

    public void play(SoundInstance sound, int delay) {
        this.startTicks.put(sound, this.ticks + delay);
    }

    public void updateListenerPosition(Camera camera) {
        if (!this.started || !camera.isReady()) {
            return;
        }
        Vec3d vec3d = camera.getPos();
        Vector3f _snowman2 = camera.getHorizontalPlane();
        Vector3f _snowman3 = camera.getVerticalPlane();
        this.taskQueue.execute(() -> {
            this.listener.setPosition(vec3d);
            this.listener.setOrientation(_snowman2, _snowman3);
        });
    }

    public void stopSounds(@Nullable Identifier identifier2, @Nullable SoundCategory soundCategory) {
        Identifier identifier2;
        if (soundCategory != null) {
            for (SoundInstance soundInstance : this.sounds.get((Object)soundCategory)) {
                if (identifier2 != null && !soundInstance.getId().equals(identifier2)) continue;
                this.stop(soundInstance);
            }
        } else if (identifier2 == null) {
            this.stopAll();
        } else {
            for (SoundInstance soundInstance : this.sources.keySet()) {
                if (!soundInstance.getId().equals(identifier2)) continue;
                this.stop(soundInstance);
            }
        }
    }

    public String getDebugString() {
        return this.soundEngine.getDebugString();
    }
}

