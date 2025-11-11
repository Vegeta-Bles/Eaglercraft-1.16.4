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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class acq {
   private static final Logger a = LogManager.getLogger();
   private static boolean b;
   private final Map<String, acq.a> c = Maps.newConcurrentMap();
   private final Map<UUID, acq.a> d = Maps.newConcurrentMap();
   private final GameProfileRepository e;
   private final Gson f = new GsonBuilder().create();
   private final File g;
   private final AtomicLong h = new AtomicLong();

   public acq(GameProfileRepository var1, File var2) {
      this.e = _snowman;
      this.g = _snowman;
      Lists.reverse(this.a()).forEach(this::a);
   }

   private void a(acq.a var1) {
      GameProfile _snowman = _snowman.a();
      _snowman.a(this.d());
      String _snowmanx = _snowman.getName();
      if (_snowmanx != null) {
         this.c.put(_snowmanx.toLowerCase(Locale.ROOT), _snowman);
      }

      UUID _snowmanxx = _snowman.getId();
      if (_snowmanxx != null) {
         this.d.put(_snowmanxx, _snowman);
      }
   }

   @Nullable
   private static GameProfile a(GameProfileRepository var0, String var1) {
      final AtomicReference<GameProfile> _snowman = new AtomicReference<>();
      ProfileLookupCallback _snowmanx = new ProfileLookupCallback() {
         public void onProfileLookupSucceeded(GameProfile var1) {
            _snowman.set(_snowman);
         }

         public void onProfileLookupFailed(GameProfile var1, Exception var2x) {
            _snowman.set(null);
         }
      };
      _snowman.findProfilesByNames(new String[]{_snowman}, Agent.MINECRAFT, _snowmanx);
      GameProfile _snowmanxx = _snowman.get();
      if (!c() && _snowmanxx == null) {
         UUID _snowmanxxx = bfw.a(new GameProfile(null, _snowman));
         _snowmanxx = new GameProfile(_snowmanxxx, _snowman);
      }

      return _snowmanxx;
   }

   public static void a(boolean var0) {
      b = _snowman;
   }

   private static boolean c() {
      return b;
   }

   public void a(GameProfile var1) {
      Calendar _snowman = Calendar.getInstance();
      _snowman.setTime(new Date());
      _snowman.add(2, 1);
      Date _snowmanx = _snowman.getTime();
      acq.a _snowmanxx = new acq.a(_snowman, _snowmanx);
      this.a(_snowmanxx);
      this.b();
   }

   private long d() {
      return this.h.incrementAndGet();
   }

   @Nullable
   public GameProfile a(String var1) {
      String _snowman = _snowman.toLowerCase(Locale.ROOT);
      acq.a _snowmanx = this.c.get(_snowman);
      boolean _snowmanxx = false;
      if (_snowmanx != null && new Date().getTime() >= _snowmanx.b.getTime()) {
         this.d.remove(_snowmanx.a().getId());
         this.c.remove(_snowmanx.a().getName().toLowerCase(Locale.ROOT));
         _snowmanxx = true;
         _snowmanx = null;
      }

      GameProfile _snowmanxxx;
      if (_snowmanx != null) {
         _snowmanx.a(this.d());
         _snowmanxxx = _snowmanx.a();
      } else {
         _snowmanxxx = a(this.e, _snowman);
         if (_snowmanxxx != null) {
            this.a(_snowmanxxx);
            _snowmanxx = false;
         }
      }

      if (_snowmanxx) {
         this.b();
      }

      return _snowmanxxx;
   }

   @Nullable
   public GameProfile a(UUID var1) {
      acq.a _snowman = this.d.get(_snowman);
      if (_snowman == null) {
         return null;
      } else {
         _snowman.a(this.d());
         return _snowman.a();
      }
   }

   private static DateFormat e() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   }

   public List<acq.a> a() {
      List<acq.a> _snowman = Lists.newArrayList();

      try (Reader _snowmanx = Files.newReader(this.g, StandardCharsets.UTF_8)) {
         JsonArray _snowmanxx = (JsonArray)this.f.fromJson(_snowmanx, JsonArray.class);
         if (_snowmanxx == null) {
            return _snowman;
         }

         DateFormat _snowmanxxx = e();
         _snowmanxx.forEach(var2x -> {
            acq.a _snowmanxxxx = a(var2x, _snowman);
            if (_snowmanxxxx != null) {
               _snowman.add(_snowmanxxxx);
            }
         });
      } catch (FileNotFoundException var19) {
      } catch (JsonParseException | IOException var20) {
         a.warn("Failed to load profile cache {}", this.g, var20);
      }

      return _snowman;
   }

   public void b() {
      JsonArray _snowman = new JsonArray();
      DateFormat _snowmanx = e();
      this.a(1000).forEach(var2x -> _snowman.add(a(var2x, _snowman)));
      String _snowmanxx = this.f.toJson(_snowman);

      try (Writer _snowmanxxx = Files.newWriter(this.g, StandardCharsets.UTF_8)) {
         _snowmanxxx.write(_snowmanxx);
      } catch (IOException var17) {
      }
   }

   private Stream<acq.a> a(int var1) {
      return ImmutableList.copyOf(this.d.values()).stream().sorted(Comparator.comparing(acq.a::c).reversed()).limit((long)_snowman);
   }

   private static JsonElement a(acq.a var0, DateFormat var1) {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("name", _snowman.a().getName());
      UUID _snowmanx = _snowman.a().getId();
      _snowman.addProperty("uuid", _snowmanx == null ? "" : _snowmanx.toString());
      _snowman.addProperty("expiresOn", _snowman.format(_snowman.b()));
      return _snowman;
   }

   @Nullable
   private static acq.a a(JsonElement var0, DateFormat var1) {
      if (_snowman.isJsonObject()) {
         JsonObject _snowman = _snowman.getAsJsonObject();
         JsonElement _snowmanx = _snowman.get("name");
         JsonElement _snowmanxx = _snowman.get("uuid");
         JsonElement _snowmanxxx = _snowman.get("expiresOn");
         if (_snowmanx != null && _snowmanxx != null) {
            String _snowmanxxxx = _snowmanxx.getAsString();
            String _snowmanxxxxx = _snowmanx.getAsString();
            Date _snowmanxxxxxx = null;
            if (_snowmanxxx != null) {
               try {
                  _snowmanxxxxxx = _snowman.parse(_snowmanxxx.getAsString());
               } catch (ParseException var12) {
               }
            }

            if (_snowmanxxxxx != null && _snowmanxxxx != null && _snowmanxxxxxx != null) {
               UUID _snowmanxxxxxxx;
               try {
                  _snowmanxxxxxxx = UUID.fromString(_snowmanxxxx);
               } catch (Throwable var11) {
                  return null;
               }

               return new acq.a(new GameProfile(_snowmanxxxxxxx, _snowmanxxxxx), _snowmanxxxxxx);
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

   static class a {
      private final GameProfile a;
      private final Date b;
      private volatile long c;

      private a(GameProfile var1, Date var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public GameProfile a() {
         return this.a;
      }

      public Date b() {
         return this.b;
      }

      public void a(long var1) {
         this.c = _snowman;
      }

      public long c() {
         return this.c;
      }
   }
}
