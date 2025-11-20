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

   private void method_30164(UserCache.Entry arg) {
      GameProfile gameProfile = arg.getProfile();
      arg.method_30171(this.method_30169());
      String string = gameProfile.getName();
      if (string != null) {
         this.byName.put(string.toLowerCase(Locale.ROOT), arg);
      }

      UUID uUID = gameProfile.getId();
      if (uUID != null) {
         this.byUuid.put(uUID, arg);
      }
   }

   @Nullable
   private static GameProfile findProfileByName(GameProfileRepository repository, String name) {
      final AtomicReference<GameProfile> atomicReference = new AtomicReference<>();
      ProfileLookupCallback profileLookupCallback = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile profile) {
            atomicReference.set(profile);
         }

         public void onProfileLookupFailed(GameProfile profile, Exception exception) {
            atomicReference.set(null);
         }
      };
      repository.findProfilesByNames(new String[]{name}, Agent.MINECRAFT, profileLookupCallback);
      GameProfile gameProfile = atomicReference.get();
      if (!shouldUseRemote() && gameProfile == null) {
         UUID uUID = PlayerEntity.getUuidFromProfile(new GameProfile(null, name));
         gameProfile = new GameProfile(uUID, name);
      }

      return gameProfile;
   }

   public static void setUseRemote(boolean value) {
      useRemote = value;
   }

   private static boolean shouldUseRemote() {
      return useRemote;
   }

   public void add(GameProfile gameProfile) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(2, 1);
      Date date = calendar.getTime();
      UserCache.Entry lv = new UserCache.Entry(gameProfile, date);
      this.method_30164(lv);
      this.save();
   }

   private long method_30169() {
      return this.field_25724.incrementAndGet();
   }

   @Nullable
   public GameProfile findByName(String string) {
      String string2 = string.toLowerCase(Locale.ROOT);
      UserCache.Entry lv = this.byName.get(string2);
      boolean bl = false;
      if (lv != null && new Date().getTime() >= lv.expirationDate.getTime()) {
         this.byUuid.remove(lv.getProfile().getId());
         this.byName.remove(lv.getProfile().getName().toLowerCase(Locale.ROOT));
         bl = true;
         lv = null;
      }

      GameProfile gameProfile;
      if (lv != null) {
         lv.method_30171(this.method_30169());
         gameProfile = lv.getProfile();
      } else {
         gameProfile = findProfileByName(this.profileRepository, string2);
         if (gameProfile != null) {
            this.add(gameProfile);
            bl = false;
         }
      }

      if (bl) {
         this.save();
      }

      return gameProfile;
   }

   @Nullable
   public GameProfile getByUuid(UUID uUID) {
      UserCache.Entry lv = this.byUuid.get(uUID);
      if (lv == null) {
         return null;
      } else {
         lv.method_30171(this.method_30169());
         return lv.getProfile();
      }
   }

   private static DateFormat method_30170() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   }

   public List<UserCache.Entry> load() {
      List<UserCache.Entry> list = Lists.newArrayList();

      try (Reader reader = Files.newReader(this.cacheFile, StandardCharsets.UTF_8)) {
         JsonArray jsonArray = (JsonArray)this.gson.fromJson(reader, JsonArray.class);
         if (jsonArray == null) {
            return list;
         }

         DateFormat dateFormat = method_30170();
         jsonArray.forEach(jsonElement -> {
            UserCache.Entry lv = method_30167(jsonElement, dateFormat);
            if (lv != null) {
               list.add(lv);
            }
         });
      } catch (FileNotFoundException var19) {
      } catch (JsonParseException | IOException var20) {
         field_25805.warn("Failed to load profile cache {}", this.cacheFile, var20);
      }

      return list;
   }

   public void save() {
      JsonArray jsonArray = new JsonArray();
      DateFormat dateFormat = method_30170();
      this.getLastAccessedEntries(1000).forEach(arg -> jsonArray.add(method_30165(arg, dateFormat)));
      String string = this.gson.toJson(jsonArray);

      try (Writer writer = Files.newWriter(this.cacheFile, StandardCharsets.UTF_8)) {
         writer.write(string);
      } catch (IOException var17) {
      }
   }

   private Stream<UserCache.Entry> getLastAccessedEntries(int i) {
      return ImmutableList.copyOf(this.byUuid.values()).stream().sorted(Comparator.comparing(UserCache.Entry::method_30172).reversed()).limit((long)i);
   }

   private static JsonElement method_30165(UserCache.Entry arg, DateFormat dateFormat) {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("name", arg.getProfile().getName());
      UUID uUID = arg.getProfile().getId();
      jsonObject.addProperty("uuid", uUID == null ? "" : uUID.toString());
      jsonObject.addProperty("expiresOn", dateFormat.format(arg.getExpirationDate()));
      return jsonObject;
   }

   @Nullable
   private static UserCache.Entry method_30167(JsonElement jsonElement, DateFormat dateFormat) {
      if (jsonElement.isJsonObject()) {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         JsonElement jsonElement2 = jsonObject.get("name");
         JsonElement jsonElement3 = jsonObject.get("uuid");
         JsonElement jsonElement4 = jsonObject.get("expiresOn");
         if (jsonElement2 != null && jsonElement3 != null) {
            String string = jsonElement3.getAsString();
            String string2 = jsonElement2.getAsString();
            Date date = null;
            if (jsonElement4 != null) {
               try {
                  date = dateFormat.parse(jsonElement4.getAsString());
               } catch (ParseException var12) {
               }
            }

            if (string2 != null && string != null && date != null) {
               UUID uUID;
               try {
                  uUID = UUID.fromString(string);
               } catch (Throwable var11) {
                  return null;
               }

               return new UserCache.Entry(new GameProfile(uUID, string2), date);
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

      private Entry(GameProfile gameProfile, Date date) {
         this.profile = gameProfile;
         this.expirationDate = date;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public Date getExpirationDate() {
         return this.expirationDate;
      }

      public void method_30171(long l) {
         this.field_25726 = l;
      }

      public long method_30172() {
         return this.field_25726;
      }
   }
}
