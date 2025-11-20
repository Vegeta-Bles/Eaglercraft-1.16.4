package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftVersion implements GameVersion {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final GameVersion field_25319 = new MinecraftVersion();
   private final String id;
   private final String name;
   private final boolean stable;
   private final int worldVersion;
   private final int protocolVersion;
   private final int packVersion;
   private final Date buildTime;
   private final String releaseTarget;

   private MinecraftVersion() {
      this.id = UUID.randomUUID().toString().replaceAll("-", "");
      this.name = "1.16.4";
      this.stable = true;
      this.worldVersion = 2584;
      this.protocolVersion = SharedConstants.method_31372();
      this.packVersion = 6;
      this.buildTime = new Date();
      this.releaseTarget = "1.16.4";
   }

   private MinecraftVersion(JsonObject _snowman) {
      this.id = JsonHelper.getString(_snowman, "id");
      this.name = JsonHelper.getString(_snowman, "name");
      this.releaseTarget = JsonHelper.getString(_snowman, "release_target");
      this.stable = JsonHelper.getBoolean(_snowman, "stable");
      this.worldVersion = JsonHelper.getInt(_snowman, "world_version");
      this.protocolVersion = JsonHelper.getInt(_snowman, "protocol_version");
      this.packVersion = JsonHelper.getInt(_snowman, "pack_version");
      this.buildTime = Date.from(ZonedDateTime.parse(JsonHelper.getString(_snowman, "build_time")).toInstant());
   }

   public static GameVersion create() {
      try (InputStream _snowman = MinecraftVersion.class.getResourceAsStream("/version.json")) {
         if (_snowman == null) {
            LOGGER.warn("Missing version information!");
            return field_25319;
         } else {
            MinecraftVersion var4;
            try (InputStreamReader _snowmanx = new InputStreamReader(_snowman)) {
               var4 = new MinecraftVersion(JsonHelper.deserialize(_snowmanx));
            }

            return var4;
         }
      } catch (JsonParseException | IOException var34) {
         throw new IllegalStateException("Game version information is corrupt", var34);
      }
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getReleaseTarget() {
      return this.releaseTarget;
   }

   public int getWorldVersion() {
      return this.worldVersion;
   }

   public int getProtocolVersion() {
      return this.protocolVersion;
   }

   public int getPackVersion() {
      return this.packVersion;
   }

   public Date getBuildTime() {
      return this.buildTime;
   }

   public boolean isStable() {
      return this.stable;
   }
}
