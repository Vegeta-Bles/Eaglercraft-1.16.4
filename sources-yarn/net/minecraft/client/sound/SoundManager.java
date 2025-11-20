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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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

   public SoundManager(ResourceManager arg, GameOptions arg2) {
      this.soundSystem = new SoundSystem(this, arg2, arg);
   }

   protected SoundManager.SoundList prepare(ResourceManager arg, Profiler arg2) {
      SoundManager.SoundList lv = new SoundManager.SoundList();
      arg2.startTick();

      for (String string : arg.getAllNamespaces()) {
         arg2.push(string);

         try {
            for (Resource lv2 : arg.getAllResources(new Identifier(string, "sounds.json"))) {
               arg2.push(lv2.getResourcePackName());

               try (
                  InputStream inputStream = lv2.getInputStream();
                  Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
               ) {
                  arg2.push("parse");
                  Map<String, SoundEntry> map = JsonHelper.deserialize(GSON, reader, TYPE);
                  arg2.swap("register");

                  for (Entry<String, SoundEntry> entry : map.entrySet()) {
                     lv.register(new Identifier(string, entry.getKey()), entry.getValue(), arg);
                  }

                  arg2.pop();
               } catch (RuntimeException var45) {
                  LOGGER.warn("Invalid sounds.json in resourcepack: '{}'", lv2.getResourcePackName(), var45);
               }

               arg2.pop();
            }
         } catch (IOException var46) {
         }

         arg2.pop();
      }

      arg2.endTick();
      return lv;
   }

   protected void apply(SoundManager.SoundList arg, ResourceManager arg2, Profiler arg3) {
      arg.addTo(this.sounds, this.soundSystem);

      for (Identifier lv : this.sounds.keySet()) {
         WeightedSoundSet lv2 = this.sounds.get(lv);
         if (lv2.getSubtitle() instanceof TranslatableText) {
            String string = ((TranslatableText)lv2.getSubtitle()).getKey();
            if (!I18n.hasTranslation(string)) {
               LOGGER.debug("Missing subtitle {} for event: {}", string, lv);
            }
         }
      }

      if (LOGGER.isDebugEnabled()) {
         for (Identifier lv3 : this.sounds.keySet()) {
            if (!Registry.SOUND_EVENT.containsId(lv3)) {
               LOGGER.debug("Not having sound event for: {}", lv3);
            }
         }
      }

      this.soundSystem.reloadSounds();
   }

   private static boolean isSoundResourcePresent(Sound arg, Identifier arg2, ResourceManager arg3) {
      Identifier lv = arg.getLocation();
      if (!arg3.containsResource(lv)) {
         LOGGER.warn("File {} does not exist, cannot add it to event {}", lv, arg2);
         return false;
      } else {
         return true;
      }
   }

   @Nullable
   public WeightedSoundSet get(Identifier arg) {
      return this.sounds.get(arg);
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

   public void updateListenerPosition(Camera arg) {
      this.soundSystem.updateListenerPosition(arg);
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

   public void tick(boolean bl) {
      this.soundSystem.tick(bl);
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

   public void stop(SoundInstance arg) {
      this.soundSystem.stop(arg);
   }

   public boolean isPlaying(SoundInstance arg) {
      return this.soundSystem.isPlaying(arg);
   }

   public void registerListener(SoundInstanceListener arg) {
      this.soundSystem.registerListener(arg);
   }

   public void unregisterListener(SoundInstanceListener arg) {
      this.soundSystem.unregisterListener(arg);
   }

   public void stopSounds(@Nullable Identifier arg, @Nullable SoundCategory arg2) {
      this.soundSystem.stopSounds(arg, arg2);
   }

   public String getDebugString() {
      return this.soundSystem.getDebugString();
   }

   @Environment(EnvType.CLIENT)
   public static class SoundList {
      private final Map<Identifier, WeightedSoundSet> loadedSounds = Maps.newHashMap();

      protected SoundList() {
      }

      private void register(Identifier id, SoundEntry entry, ResourceManager resourceManager) {
         WeightedSoundSet lv = this.loadedSounds.get(id);
         boolean bl = lv == null;
         if (bl || entry.canReplace()) {
            if (!bl) {
               SoundManager.LOGGER.debug("Replaced sound event location {}", id);
            }

            lv = new WeightedSoundSet(id, entry.getSubtitle());
            this.loadedSounds.put(id, lv);
         }

         for (final Sound lv2 : entry.getSounds()) {
            final Identifier lv3 = lv2.getIdentifier();
            SoundContainer<Sound> lv5;
            switch (lv2.getRegistrationType()) {
               case FILE:
                  if (!SoundManager.isSoundResourcePresent(lv2, id, resourceManager)) {
                     continue;
                  }

                  lv5 = lv2;
                  break;
               case SOUND_EVENT:
                  lv5 = new SoundContainer<Sound>() {
                     @Override
                     public int getWeight() {
                        WeightedSoundSet lv = SoundList.this.loadedSounds.get(lv3);
                        return lv == null ? 0 : lv.getWeight();
                     }

                     public Sound getSound() {
                        WeightedSoundSet lv = SoundList.this.loadedSounds.get(lv3);
                        if (lv == null) {
                           return SoundManager.MISSING_SOUND;
                        } else {
                           Sound lv2 = lv.getSound();
                           return new Sound(
                              lv2.getIdentifier().toString(),
                              lv2.getVolume() * lv2.getVolume(),
                              lv2.getPitch() * lv2.getPitch(),
                              lv2.getWeight(),
                              Sound.RegistrationType.FILE,
                              lv2.isStreamed() || lv2.isStreamed(),
                              lv2.isPreloaded(),
                              lv2.getAttenuation()
                           );
                        }
                     }

                     @Override
                     public void preload(SoundSystem soundSystem) {
                        WeightedSoundSet lv = SoundList.this.loadedSounds.get(lv3);
                        if (lv != null) {
                           lv.preload(soundSystem);
                        }
                     }
                  };
                  break;
               default:
                  throw new IllegalStateException("Unknown SoundEventRegistration type: " + lv2.getRegistrationType());
            }

            lv.add(lv5);
         }
      }

      public void addTo(Map<Identifier, WeightedSoundSet> map, SoundSystem arg) {
         map.clear();

         for (Entry<Identifier, WeightedSoundSet> entry : this.loadedSounds.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
            entry.getValue().preload(arg);
         }
      }
   }
}
