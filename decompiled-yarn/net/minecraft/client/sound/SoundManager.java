package net.minecraft.client.sound;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.Camera;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundManager extends SinglePreparationResourceReloadListener<SoundManager.SoundList> {
   public static final Sound MISSING_SOUND = new Sound("meta:missing_sound", 1.0F, 1.0F, 1, Sound.RegistrationType.FILE, false, false, 16);
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder()
      .registerTypeHierarchyAdapter(Text.class, new Text.Serializer())
      .registerTypeAdapter(SoundEntry.class, new SoundEntryDeserializer())
      .create();
   private static final TypeToken<Map<String, SoundEntry>> TYPE = new TypeToken<Map<String, SoundEntry>>() {
   };
   private final Map<Identifier, WeightedSoundSet> sounds = Maps.newHashMap();
   private final SoundSystem soundSystem;

   public SoundManager(ResourceManager _snowman, GameOptions _snowman) {
      this.soundSystem = new SoundSystem(this, _snowman, _snowman);
   }

   protected SoundManager.SoundList prepare(ResourceManager _snowman, Profiler _snowman) {
      SoundManager.SoundList _snowmanxx = new SoundManager.SoundList();
      _snowman.startTick();

      for (String _snowmanxxx : _snowman.getAllNamespaces()) {
         _snowman.push(_snowmanxxx);

         try {
            for (Resource _snowmanxxxx : _snowman.getAllResources(new Identifier(_snowmanxxx, "sounds.json"))) {
               _snowman.push(_snowmanxxxx.getResourcePackName());

               try (
                  InputStream _snowmanxxxxx = _snowmanxxxx.getInputStream();
                  Reader _snowmanxxxxxx = new InputStreamReader(_snowmanxxxxx, StandardCharsets.UTF_8);
               ) {
                  _snowman.push("parse");
                  Map<String, SoundEntry> _snowmanxxxxxxx = JsonHelper.deserialize(GSON, _snowmanxxxxxx, TYPE);
                  _snowman.swap("register");

                  for (Entry<String, SoundEntry> _snowmanxxxxxxxx : _snowmanxxxxxxx.entrySet()) {
                     _snowmanxx.register(new Identifier(_snowmanxxx, _snowmanxxxxxxxx.getKey()), _snowmanxxxxxxxx.getValue(), _snowman);
                  }

                  _snowman.pop();
               } catch (RuntimeException var45) {
                  LOGGER.warn("Invalid sounds.json in resourcepack: '{}'", _snowmanxxxx.getResourcePackName(), var45);
               }

               _snowman.pop();
            }
         } catch (IOException var46) {
         }

         _snowman.pop();
      }

      _snowman.endTick();
      return _snowmanxx;
   }

   protected void apply(SoundManager.SoundList _snowman, ResourceManager _snowman, Profiler _snowman) {
      _snowman.addTo(this.sounds, this.soundSystem);

      for (Identifier _snowmanxxx : this.sounds.keySet()) {
         WeightedSoundSet _snowmanxxxx = this.sounds.get(_snowmanxxx);
         if (_snowmanxxxx.getSubtitle() instanceof TranslatableText) {
            String _snowmanxxxxx = ((TranslatableText)_snowmanxxxx.getSubtitle()).getKey();
            if (!I18n.hasTranslation(_snowmanxxxxx)) {
               LOGGER.debug("Missing subtitle {} for event: {}", _snowmanxxxxx, _snowmanxxx);
            }
         }
      }

      if (LOGGER.isDebugEnabled()) {
         for (Identifier _snowmanxxxx : this.sounds.keySet()) {
            if (!Registry.SOUND_EVENT.containsId(_snowmanxxxx)) {
               LOGGER.debug("Not having sound event for: {}", _snowmanxxxx);
            }
         }
      }

      this.soundSystem.reloadSounds();
   }

   private static boolean isSoundResourcePresent(Sound _snowman, Identifier _snowman, ResourceManager _snowman) {
      Identifier _snowmanxxx = _snowman.getLocation();
      if (!_snowman.containsResource(_snowmanxxx)) {
         LOGGER.warn("File {} does not exist, cannot add it to event {}", _snowmanxxx, _snowman);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   public WeightedSoundSet get(Identifier _snowman) {
      return this.sounds.get(_snowman);
   }

   public Collection<Identifier> getKeys() {
      return this.sounds.keySet();
   }

   public void playNextTick(TickableSoundInstance sound) {
      this.soundSystem.playNextTick(sound);
   }

   public void play(SoundInstance sound) {
      this.soundSystem.play(sound);
   }

   public void play(SoundInstance sound, int delay) {
      this.soundSystem.play(sound, delay);
   }

   public void updateListenerPosition(Camera _snowman) {
      this.soundSystem.updateListenerPosition(_snowman);
   }

   public void pauseAll() {
      this.soundSystem.pauseAll();
   }

   public void stopAll() {
      this.soundSystem.stopAll();
   }

   public void close() {
      this.soundSystem.stop();
   }

   public void tick(boolean _snowman) {
      this.soundSystem.tick(_snowman);
   }

   public void resumeAll() {
      this.soundSystem.resumeAll();
   }

   public void updateSoundVolume(SoundCategory category, float volume) {
      if (category == SoundCategory.MASTER && volume <= 0.0F) {
         this.stopAll();
      }

      this.soundSystem.updateSoundVolume(category, volume);
   }

   public void stop(SoundInstance _snowman) {
      this.soundSystem.stop(_snowman);
   }

   public boolean isPlaying(SoundInstance _snowman) {
      return this.soundSystem.isPlaying(_snowman);
   }

   public void registerListener(SoundInstanceListener _snowman) {
      this.soundSystem.registerListener(_snowman);
   }

   public void unregisterListener(SoundInstanceListener _snowman) {
      this.soundSystem.unregisterListener(_snowman);
   }

   public void stopSounds(@Nullable Identifier _snowman, @Nullable SoundCategory _snowman) {
      this.soundSystem.stopSounds(_snowman, _snowman);
   }

   public String getDebugString() {
      return this.soundSystem.getDebugString();
   }

   public static class SoundList {
      private final Map<Identifier, WeightedSoundSet> loadedSounds = Maps.newHashMap();

      protected SoundList() {
      }

      private void register(Identifier id, SoundEntry entry, ResourceManager resourceManager) {
         WeightedSoundSet _snowman = this.loadedSounds.get(id);
         boolean _snowmanx = _snowman == null;
         if (_snowmanx || entry.canReplace()) {
            if (!_snowmanx) {
               SoundManager.LOGGER.debug("Replaced sound event location {}", id);
            }

            _snowman = new WeightedSoundSet(id, entry.getSubtitle());
            this.loadedSounds.put(id, _snowman);
         }

         for (final Sound _snowmanxx : entry.getSounds()) {
            final Identifier _snowmanxxx = _snowmanxx.getIdentifier();
            SoundContainer<Sound> _snowmanxxxx;
            switch (_snowmanxx.getRegistrationType()) {
               case FILE:
                  if (!SoundManager.isSoundResourcePresent(_snowmanxx, id, resourceManager)) {
                     continue;
                  }

                  _snowmanxxxx = _snowmanxx;
                  break;
               case SOUND_EVENT:
                  _snowmanxxxx = new SoundContainer<Sound>() {
                     @Override
                     public int getWeight() {
                        WeightedSoundSet _snowman = SoundList.this.loadedSounds.get(_snowman);
                        return _snowman == null ? 0 : _snowman.getWeight();
                     }

                     public Sound getSound() {
                        WeightedSoundSet _snowman = SoundList.this.loadedSounds.get(_snowman);
                        if (_snowman == null) {
                           return SoundManager.MISSING_SOUND;
                        } else {
                           Sound _snowmanx = _snowman.getSound();
                           return new Sound(
                              _snowmanx.getIdentifier().toString(),
                              _snowmanx.getVolume() * _snowman.getVolume(),
                              _snowmanx.getPitch() * _snowman.getPitch(),
                              _snowman.getWeight(),
                              Sound.RegistrationType.FILE,
                              _snowmanx.isStreamed() || _snowman.isStreamed(),
                              _snowmanx.isPreloaded(),
                              _snowmanx.getAttenuation()
                           );
                        }
                     }

                     @Override
                     public void preload(SoundSystem soundSystem) {
                        WeightedSoundSet _snowman = SoundList.this.loadedSounds.get(_snowman);
                        if (_snowman != null) {
                           _snowman.preload(soundSystem);
                        }
                     }
                  };
                  break;
               default:
                  throw new IllegalStateException("Unknown SoundEventRegistration type: " + _snowmanxx.getRegistrationType());
            }

            _snowman.add(_snowmanxxxx);
         }
      }

      public void addTo(Map<Identifier, WeightedSoundSet> _snowman, SoundSystem _snowman) {
         _snowman.clear();

         for (Entry<Identifier, WeightedSoundSet> _snowmanxx : this.loadedSounds.entrySet()) {
            _snowman.put(_snowmanxx.getKey(), _snowmanxx.getValue());
            _snowmanxx.getValue().preload(_snowman);
         }
      }
   }
}
