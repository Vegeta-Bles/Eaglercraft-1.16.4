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
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
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
   private static final Marker MARKER = MarkerManager.getMarker("SOUNDS");
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

   public SoundSystem(SoundManager loader, GameOptions settings, ResourceManager _snowman) {
      this.loader = loader;
      this.settings = settings;
      this.soundLoader = new SoundLoader(_snowman);
   }

   public void reloadSounds() {
      unknownSounds.clear();

      for (SoundEvent _snowman : Registry.SOUND_EVENT) {
         Identifier _snowmanx = _snowman.getId();
         if (this.loader.get(_snowmanx) == null) {
            LOGGER.warn("Missing sound for event: {}", Registry.SOUND_EVENT.getId(_snowman));
            unknownSounds.add(_snowmanx);
         }
      }

      this.stop();
      this.start();
   }

   private synchronized void start() {
      if (!this.started) {
         try {
            this.soundEngine.init();
            this.listener.init();
            this.listener.setVolume(this.settings.getSoundVolume(SoundCategory.MASTER));
            this.soundLoader.loadStatic(this.preloadedSounds).thenRun(this.preloadedSounds::clear);
            this.started = true;
            LOGGER.info(MARKER, "Sound engine started");
         } catch (RuntimeException var2) {
            LOGGER.error(MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
         }
      }
   }

   private float getSoundVolume(@Nullable SoundCategory _snowman) {
      return _snowman != null && _snowman != SoundCategory.MASTER ? this.settings.getSoundVolume(_snowman) : 1.0F;
   }

   public void updateSoundVolume(SoundCategory _snowman, float volume) {
      if (this.started) {
         if (_snowman == SoundCategory.MASTER) {
            this.listener.setVolume(volume);
         } else {
            this.sources.forEach((_snowmanxxx, _snowmanxx) -> {
               float _snowmanxx = this.getAdjustedVolume(_snowmanxxx);
               _snowmanxx.run(_snowmanxxxxx -> {
                  if (_snowmanx <= 0.0F) {
                     _snowmanxxxxx.stop();
                  } else {
                     _snowmanxxxxx.setVolume(_snowmanx);
                  }
               });
            });
         }
      }
   }

   public void stop() {
      if (this.started) {
         this.stopAll();
         this.soundLoader.close();
         this.soundEngine.close();
         this.started = false;
      }
   }

   public void stop(SoundInstance _snowman) {
      if (this.started) {
         Channel.SourceManager _snowmanx = this.sources.get(_snowman);
         if (_snowmanx != null) {
            _snowmanx.run(Source::stop);
         }
      }
   }

   public void stopAll() {
      if (this.started) {
         this.taskQueue.restart();
         this.sources.values().forEach(_snowman -> _snowman.run(Source::stop));
         this.sources.clear();
         this.channel.close();
         this.startTicks.clear();
         this.tickingSounds.clear();
         this.sounds.clear();
         this.soundEndTicks.clear();
         this.soundsToPlayNextTick.clear();
      }
   }

   public void registerListener(SoundInstanceListener _snowman) {
      this.listeners.add(_snowman);
   }

   public void unregisterListener(SoundInstanceListener _snowman) {
      this.listeners.remove(_snowman);
   }

   public void tick(boolean _snowman) {
      if (!_snowman) {
         this.tick();
      }

      this.channel.tick();
   }

   private void tick() {
      this.ticks++;
      this.soundsToPlayNextTick.stream().filter(SoundInstance::canPlay).forEach(this::play);
      this.soundsToPlayNextTick.clear();

      for (TickableSoundInstance _snowman : this.tickingSounds) {
         if (!_snowman.canPlay()) {
            this.stop(_snowman);
         }

         _snowman.tick();
         if (_snowman.isDone()) {
            this.stop(_snowman);
         } else {
            float _snowmanx = this.getAdjustedVolume(_snowman);
            float _snowmanxx = this.getAdjustedPitch(_snowman);
            Vec3d _snowmanxxx = new Vec3d(_snowman.getX(), _snowman.getY(), _snowman.getZ());
            Channel.SourceManager _snowmanxxxx = this.sources.get(_snowman);
            if (_snowmanxxxx != null) {
               _snowmanxxxx.run(_snowmanxxxxx -> {
                  _snowmanxxxxx.setVolume(_snowman);
                  _snowmanxxxxx.setPitch(_snowman);
                  _snowmanxxxxx.setPosition(_snowman);
               });
            }
         }
      }

      Iterator<Entry<SoundInstance, Channel.SourceManager>> _snowman = this.sources.entrySet().iterator();

      while (_snowman.hasNext()) {
         Entry<SoundInstance, Channel.SourceManager> _snowmanx = _snowman.next();
         Channel.SourceManager _snowmanxx = _snowmanx.getValue();
         SoundInstance _snowmanxxx = _snowmanx.getKey();
         float _snowmanxxxx = this.settings.getSoundVolume(_snowmanxxx.getCategory());
         if (_snowmanxxxx <= 0.0F) {
            _snowmanxx.run(Source::stop);
            _snowman.remove();
         } else if (_snowmanxx.isStopped()) {
            int _snowmanxxxxx = this.soundEndTicks.get(_snowmanxxx);
            if (_snowmanxxxxx <= this.ticks) {
               if (isRepeatDelayed(_snowmanxxx)) {
                  this.startTicks.put(_snowmanxxx, this.ticks + _snowmanxxx.getRepeatDelay());
               }

               _snowman.remove();
               LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", _snowmanxx);
               this.soundEndTicks.remove(_snowmanxxx);

               try {
                  this.sounds.remove(_snowmanxxx.getCategory(), _snowmanxxx);
               } catch (RuntimeException var8) {
               }

               if (_snowmanxxx instanceof TickableSoundInstance) {
                  this.tickingSounds.remove(_snowmanxxx);
               }
            }
         }
      }

      Iterator<Entry<SoundInstance, Integer>> _snowmanx = this.startTicks.entrySet().iterator();

      while (_snowmanx.hasNext()) {
         Entry<SoundInstance, Integer> _snowmanxx = _snowmanx.next();
         if (this.ticks >= _snowmanxx.getValue()) {
            SoundInstance _snowmanxxx = _snowmanxx.getKey();
            if (_snowmanxxx instanceof TickableSoundInstance) {
               ((TickableSoundInstance)_snowmanxxx).tick();
            }

            this.play(_snowmanxxx);
            _snowmanx.remove();
         }
      }
   }

   private static boolean canRepeatInstantly(SoundInstance _snowman) {
      return _snowman.getRepeatDelay() > 0;
   }

   private static boolean isRepeatDelayed(SoundInstance _snowman) {
      return _snowman.isRepeatable() && canRepeatInstantly(_snowman);
   }

   private static boolean shouldRepeatInstantly(SoundInstance _snowman) {
      return _snowman.isRepeatable() && !canRepeatInstantly(_snowman);
   }

   public boolean isPlaying(SoundInstance _snowman) {
      if (!this.started) {
         return false;
      } else {
         return this.soundEndTicks.containsKey(_snowman) && this.soundEndTicks.get(_snowman) <= this.ticks ? true : this.sources.containsKey(_snowman);
      }
   }

   public void play(SoundInstance _snowman) {
      if (this.started) {
         if (_snowman.canPlay()) {
            WeightedSoundSet _snowmanx = _snowman.getSoundSet(this.loader);
            Identifier _snowmanxx = _snowman.getId();
            if (_snowmanx == null) {
               if (unknownSounds.add(_snowmanxx)) {
                  LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", _snowmanxx);
               }
            } else {
               Sound _snowmanxxx = _snowman.getSound();
               if (_snowmanxxx == SoundManager.MISSING_SOUND) {
                  if (unknownSounds.add(_snowmanxx)) {
                     LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", _snowmanxx);
                  }
               } else {
                  float _snowmanxxxx = _snowman.getVolume();
                  float _snowmanxxxxx = Math.max(_snowmanxxxx, 1.0F) * (float)_snowmanxxx.getAttenuation();
                  SoundCategory _snowmanxxxxxx = _snowman.getCategory();
                  float _snowmanxxxxxxx = this.getAdjustedVolume(_snowman);
                  float _snowmanxxxxxxxx = this.getAdjustedPitch(_snowman);
                  SoundInstance.AttenuationType _snowmanxxxxxxxxx = _snowman.getAttenuationType();
                  boolean _snowmanxxxxxxxxxx = _snowman.isLooping();
                  if (_snowmanxxxxxxx == 0.0F && !_snowman.shouldAlwaysPlay()) {
                     LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", _snowmanxxx.getIdentifier());
                  } else {
                     Vec3d _snowmanxxxxxxxxxxx = new Vec3d(_snowman.getX(), _snowman.getY(), _snowman.getZ());
                     if (!this.listeners.isEmpty()) {
                        boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx
                           || _snowmanxxxxxxxxx == SoundInstance.AttenuationType.NONE
                           || this.listener.getPos().squaredDistanceTo(_snowmanxxxxxxxxxxx) < (double)(_snowmanxxxxx * _snowmanxxxxx);
                        if (_snowmanxxxxxxxxxxxx) {
                           for (SoundInstanceListener _snowmanxxxxxxxxxxxxx : this.listeners) {
                              _snowmanxxxxxxxxxxxxx.onSoundPlayed(_snowman, _snowmanx);
                           }
                        } else {
                           LOGGER.debug(MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", _snowmanxx);
                        }
                     }

                     if (this.listener.getVolume() <= 0.0F) {
                        LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", _snowmanxx);
                     } else {
                        boolean _snowmanxxxxxxxxxxxx = shouldRepeatInstantly(_snowman);
                        boolean _snowmanxxxxxxxxxxxxx = _snowmanxxx.isStreamed();
                        CompletableFuture<Channel.SourceManager> _snowmanxxxxxxxxxxxxxx = this.channel
                           .createSource(_snowmanxxx.isStreamed() ? SoundEngine.RunMode.STREAMING : SoundEngine.RunMode.STATIC);
                        Channel.SourceManager _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.join();
                        if (_snowmanxxxxxxxxxxxxxxx == null) {
                           LOGGER.warn("Failed to create new sound handle");
                        } else {
                           LOGGER.debug(MARKER, "Playing sound {} for event {}", _snowmanxxx.getIdentifier(), _snowmanxx);
                           this.soundEndTicks.put(_snowman, this.ticks + 20);
                           this.sources.put(_snowman, _snowmanxxxxxxxxxxxxxxx);
                           this.sounds.put(_snowmanxxxxxx, _snowman);
                           _snowmanxxxxxxxxxxxxxxx.run(_snowmanxxxxxxxxxxxxxxxx -> {
                              _snowmanxxxxxxxxxxxxxxxx.setPitch(_snowman);
                              _snowmanxxxxxxxxxxxxxxxx.setVolume(_snowman);
                              if (_snowman == SoundInstance.AttenuationType.LINEAR) {
                                 _snowmanxxxxxxxxxxxxxxxx.setAttenuation(_snowman);
                              } else {
                                 _snowmanxxxxxxxxxxxxxxxx.disableAttenuation();
                              }

                              _snowmanxxxxxxxxxxxxxxxx.setLooping(_snowman && !_snowman);
                              _snowmanxxxxxxxxxxxxxxxx.setPosition(_snowman);
                              _snowmanxxxxxxxxxxxxxxxx.setRelative(_snowman);
                           });
                           if (!_snowmanxxxxxxxxxxxxx) {
                              this.soundLoader.loadStatic(_snowmanxxx.getLocation()).thenAccept(_snowmanxxxxxxxxxxxxxxxx -> _snowman.run(_snowmanxxxxxxxxxxxxxxxxx -> {
                                    _snowmanxxxxxxxxxxxxxxxxx.setBuffer(_snowmanxx);
                                    _snowmanxxxxxxxxxxxxxxxxx.play();
                                 }));
                           } else {
                              this.soundLoader.loadStreamed(_snowmanxxx.getLocation(), _snowmanxxxxxxxxxxxx).thenAccept(_snowmanxxxxxxxxxxxxxxxx -> _snowman.run(_snowmanxxxxxxxxxxxxxxxxx -> {
                                    _snowmanxxxxxxxxxxxxxxxxx.setStream(_snowmanxx);
                                    _snowmanxxxxxxxxxxxxxxxxx.play();
                                 }));
                           }

                           if (_snowman instanceof TickableSoundInstance) {
                              this.tickingSounds.add((TickableSoundInstance)_snowman);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void playNextTick(TickableSoundInstance sound) {
      this.soundsToPlayNextTick.add(sound);
   }

   public void addPreloadedSound(Sound sound) {
      this.preloadedSounds.add(sound);
   }

   private float getAdjustedPitch(SoundInstance _snowman) {
      return MathHelper.clamp(_snowman.getPitch(), 0.5F, 2.0F);
   }

   private float getAdjustedVolume(SoundInstance _snowman) {
      return MathHelper.clamp(_snowman.getVolume() * this.getSoundVolume(_snowman.getCategory()), 0.0F, 1.0F);
   }

   public void pauseAll() {
      if (this.started) {
         this.channel.execute(_snowman -> _snowman.forEach(Source::pause));
      }
   }

   public void resumeAll() {
      if (this.started) {
         this.channel.execute(_snowman -> _snowman.forEach(Source::resume));
      }
   }

   public void play(SoundInstance sound, int delay) {
      this.startTicks.put(sound, this.ticks + delay);
   }

   public void updateListenerPosition(Camera _snowman) {
      if (this.started && _snowman.isReady()) {
         Vec3d _snowmanx = _snowman.getPos();
         Vector3f _snowmanxx = _snowman.getHorizontalPlane();
         Vector3f _snowmanxxx = _snowman.getVerticalPlane();
         this.taskQueue.execute(() -> {
            this.listener.setPosition(_snowman);
            this.listener.setOrientation(_snowman, _snowman);
         });
      }
   }

   public void stopSounds(@Nullable Identifier _snowman, @Nullable SoundCategory _snowman) {
      if (_snowman != null) {
         for (SoundInstance _snowmanxx : this.sounds.get(_snowman)) {
            if (_snowman == null || _snowmanxx.getId().equals(_snowman)) {
               this.stop(_snowmanxx);
            }
         }
      } else if (_snowman == null) {
         this.stopAll();
      } else {
         for (SoundInstance _snowmanxxx : this.sources.keySet()) {
            if (_snowmanxxx.getId().equals(_snowman)) {
               this.stop(_snowmanxxx);
            }
         }
      }
   }

   public String getDebugString() {
      return this.soundEngine.getDebugString();
   }
}
