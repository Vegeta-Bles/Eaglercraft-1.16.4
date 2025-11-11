import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class q implements GameVersion {
   private static final Logger b = LogManager.getLogger();
   public static final GameVersion a = new q();
   private final String c;
   private final String d;
   private final boolean e;
   private final int f;
   private final int g;
   private final int h;
   private final Date i;
   private final String j;

   private q() {
      this.c = UUID.randomUUID().toString().replaceAll("-", "");
      this.d = "1.16.4";
      this.e = true;
      this.f = 2584;
      this.g = w.b();
      this.h = 6;
      this.i = new Date();
      this.j = "1.16.4";
   }

   private q(JsonObject var1) {
      this.c = afd.h(_snowman, "id");
      this.d = afd.h(_snowman, "name");
      this.j = afd.h(_snowman, "release_target");
      this.e = afd.j(_snowman, "stable");
      this.f = afd.n(_snowman, "world_version");
      this.g = afd.n(_snowman, "protocol_version");
      this.h = afd.n(_snowman, "pack_version");
      this.i = Date.from(ZonedDateTime.parse(afd.h(_snowman, "build_time")).toInstant());
   }

   public static GameVersion a() {
      try (InputStream _snowman = q.class.getResourceAsStream("/version.json")) {
         if (_snowman == null) {
            b.warn("Missing version information!");
            return a;
         } else {
            q var4;
            try (InputStreamReader _snowmanx = new InputStreamReader(_snowman)) {
               var4 = new q(afd.a(_snowmanx));
            }

            return var4;
         }
      } catch (JsonParseException | IOException var34) {
         throw new IllegalStateException("Game version information is corrupt", var34);
      }
   }

   public String getId() {
      return this.c;
   }

   public String getName() {
      return this.d;
   }

   public String getReleaseTarget() {
      return this.j;
   }

   public int getWorldVersion() {
      return this.f;
   }

   public int getProtocolVersion() {
      return this.g;
   }

   public int getPackVersion() {
      return this.h;
   }

   public Date getBuildTime() {
      return this.i;
   }

   public boolean isStable() {
      return this.e;
   }
}
