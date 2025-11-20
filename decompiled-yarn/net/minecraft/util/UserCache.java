package net.minecraft.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserCache {
   private static final Logger field_25805 = LogManager.getLogger();
   private static boolean useRemote;
   private final Map<String, UserCache.Entry> byName = Maps.newConcurrentMap();
   private final Map<UUID, UserCache.Entry> byUuid = Maps.newConcurrentMap();
   private final GameProfileRepository profileRepository;
   private final Gson gson = new GsonBuilder().create();
   private final File cacheFile;
   private final AtomicLong field_25724 = new AtomicLong();

   public UserCache(GameProfileRepository profileRepository, File cacheFile) {
      this.profileRepository = profileRepository;
      this.cacheFile = cacheFile;
      Lists.reverse(this.load()).forEach(this::method_30164);
   }

   private void method_30164(UserCache.Entry _snowman) {
      GameProfile _snowmanx = _snowman.getProfile();
      _snowman.method_30171(this.method_30169());
      String _snowmanxx = _snowmanx.getName();
      if (_snowmanxx != null) {
         this.byName.put(_snowmanxx.toLowerCase(Locale.ROOT), _snowman);
      }

      UUID _snowmanxxx = _snowmanx.getId();
      if (_snowmanxxx != null) {
         this.byUuid.put(_snowmanxxx, _snowman);
      }
   }

   @Nullable
   private static GameProfile findProfileByName(GameProfileRepository repository, String name) {
      final AtomicReference<GameProfile> _snowman = new AtomicReference<>();
      ProfileLookupCallback _snowmanx = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile profile) {
            _snowman.set(profile);
         }

         public void onProfileLookupFailed(GameProfile profile, Exception exception) {
            _snowman.set(null);
         }
      };
      repository.findProfilesByNames(new String[]{name}, Agent.MINECRAFT, _snowmanx);
      GameProfile _snowmanxx = _snowman.get();
      if (!shouldUseRemote() && _snowmanxx == null) {
         UUID _snowmanxxx = PlayerEntity.getUuidFromProfile(new GameProfile(null, name));
         _snowmanxx = new GameProfile(_snowmanxxx, name);
      }

      return _snowmanxx;
   }

   public static void setUseRemote(boolean value) {
      useRemote = value;
   }

   private static boolean shouldUseRemote() {
      return useRemote;
   }

   public void add(GameProfile _snowman) {
      Calendar _snowmanx = Calendar.getInstance();
      _snowmanx.setTime(new Date());
      _snowmanx.add(2, 1);
      Date _snowmanxx = _snowmanx.getTime();
      UserCache.Entry _snowmanxxx = new UserCache.Entry(_snowman, _snowmanxx);
      this.method_30164(_snowmanxxx);
      this.save();
   }

   private long method_30169() {
      return this.field_25724.incrementAndGet();
   }

   @Nullable
   public GameProfile findByName(String _snowman) {
      String _snowmanx = _snowman.toLowerCase(Locale.ROOT);
      UserCache.Entry _snowmanxx = this.byName.get(_snowmanx);
      boolean _snowmanxxx = false;
      if (_snowmanxx != null && new Date().getTime() >= _snowmanxx.expirationDate.getTime()) {
         this.byUuid.remove(_snowmanxx.getProfile().getId());
         this.byName.remove(_snowmanxx.getProfile().getName().toLowerCase(Locale.ROOT));
         _snowmanxxx = true;
         _snowmanxx = null;
      }

      GameProfile _snowmanxxxx;
      if (_snowmanxx != null) {
         _snowmanxx.method_30171(this.method_30169());
         _snowmanxxxx = _snowmanxx.getProfile();
      } else {
         _snowmanxxxx = findProfileByName(this.profileRepository, _snowmanx);
         if (_snowmanxxxx != null) {
            this.add(_snowmanxxxx);
            _snowmanxxx = false;
         }
      }

      if (_snowmanxxx) {
         this.save();
      }

      return _snowmanxxxx;
   }

   @Nullable
   public GameProfile getByUuid(UUID _snowman) {
      UserCache.Entry _snowmanx = this.byUuid.get(_snowman);
      if (_snowmanx == null) {
         return null;
      } else {
         _snowmanx.method_30171(this.method_30169());
         return _snowmanx.getProfile();
      }
   }

   private static DateFormat method_30170() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   }

   public List<UserCache.Entry> load() {
      List<UserCache.Entry> _snowman = Lists.newArrayList();

      try (Reader _snowmanx = Files.newReader(this.cacheFile, StandardCharsets.UTF_8)) {
         JsonArray _snowmanxx = (JsonArray)this.gson.fromJson(_snowmanx, JsonArray.class);
         if (_snowmanxx == null) {
            return _snowman;
         }

         DateFormat _snowmanxxx = method_30170();
         _snowmanxx.forEach(_snowmanxxxx -> {
            UserCache.Entry _snowmanxxxxx = method_30167(_snowmanxxxx, _snowman);
            if (_snowmanxxxxx != null) {
               _snowman.add(_snowmanxxxxx);
            }
         });
      } catch (FileNotFoundException var19) {
      } catch (JsonParseException | IOException var20) {
         field_25805.warn("Failed to load profile cache {}", this.cacheFile, var20);
      }

      return _snowman;
   }

   public void save() {
      JsonArray _snowman = new JsonArray();
      DateFormat _snowmanx = method_30170();
      this.getLastAccessedEntries(1000).forEach(_snowmanxx -> _snowman.add(method_30165(_snowmanxx, _snowman)));
      String _snowmanxx = this.gson.toJson(_snowman);

      try (Writer _snowmanxxx = Files.newWriter(this.cacheFile, StandardCharsets.UTF_8)) {
         _snowmanxxx.write(_snowmanxx);
      } catch (IOException var17) {
      }
   }

   private Stream<UserCache.Entry> getLastAccessedEntries(int _snowman) {
      return ImmutableList.copyOf(this.byUuid.values()).stream().sorted(Comparator.comparing(UserCache.Entry::method_30172).reversed()).limit((long)_snowman);
   }

   private static JsonElement method_30165(UserCache.Entry _snowman, DateFormat _snowman) {
      JsonObject _snowmanxx = new JsonObject();
      _snowmanxx.addProperty("name", _snowman.getProfile().getName());
      UUID _snowmanxxx = _snowman.getProfile().getId();
      _snowmanxx.addProperty("uuid", _snowmanxxx == null ? "" : _snowmanxxx.toString());
      _snowmanxx.addProperty("expiresOn", _snowman.format(_snowman.getExpirationDate()));
      return _snowmanxx;
   }

   @Nullable
   private static UserCache.Entry method_30167(JsonElement _snowman, DateFormat _snowman) {
      if (_snowman.isJsonObject()) {
         JsonObject _snowmanxx = _snowman.getAsJsonObject();
         JsonElement _snowmanxxx = _snowmanxx.get("name");
         JsonElement _snowmanxxxx = _snowmanxx.get("uuid");
         JsonElement _snowmanxxxxx = _snowmanxx.get("expiresOn");
         if (_snowmanxxx != null && _snowmanxxxx != null) {
            String _snowmanxxxxxx = _snowmanxxxx.getAsString();
            String _snowmanxxxxxxx = _snowmanxxx.getAsString();
            Date _snowmanxxxxxxxx = null;
            if (_snowmanxxxxx != null) {
               try {
                  _snowmanxxxxxxxx = _snowman.parse(_snowmanxxxxx.getAsString());
               } catch (ParseException var12) {
               }
            }

            if (_snowmanxxxxxxx != null && _snowmanxxxxxx != null && _snowmanxxxxxxxx != null) {
               UUID _snowmanxxxxxxxxx;
               try {
                  _snowmanxxxxxxxxx = UUID.fromString(_snowmanxxxxxx);
               } catch (Throwable var11) {
                  return null;
               }

               return new UserCache.Entry(new GameProfile(_snowmanxxxxxxxxx, _snowmanxxxxxxx), _snowmanxxxxxxxx);
            } else {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   static class Entry {
      private final GameProfile profile;
      private final Date expirationDate;
      private volatile long field_25726;

      private Entry(GameProfile _snowman, Date _snowman) {
         this.profile = _snowman;
         this.expirationDate = _snowman;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public Date getExpirationDate() {
         return this.expirationDate;
      }

      public void method_30171(long _snowman) {
         this.field_25726 = _snowman;
      }

      public long method_30172() {
         return this.field_25726;
      }
   }
}
