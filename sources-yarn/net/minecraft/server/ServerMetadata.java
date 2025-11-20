package net.minecraft.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;

public class ServerMetadata {
   private Text description;
   private ServerMetadata.Players players;
   private ServerMetadata.Version version;
   private String favicon;

   public ServerMetadata() {
   }

   public Text getDescription() {
      return this.description;
   }

   public void setDescription(Text description) {
      this.description = description;
   }

   public ServerMetadata.Players getPlayers() {
      return this.players;
   }

   public void setPlayers(ServerMetadata.Players players) {
      this.players = players;
   }

   public ServerMetadata.Version getVersion() {
      return this.version;
   }

   public void setVersion(ServerMetadata.Version version) {
      this.version = version;
   }

   public void setFavicon(String favicon) {
      this.favicon = favicon;
   }

   public String getFavicon() {
      return this.favicon;
   }

   public static class Deserializer implements JsonDeserializer<ServerMetadata>, JsonSerializer<ServerMetadata> {
      public Deserializer() {
      }

      public ServerMetadata deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = JsonHelper.asObject(jsonElement, "status");
         ServerMetadata lv = new ServerMetadata();
         if (jsonObject.has("description")) {
            lv.setDescription((Text)jsonDeserializationContext.deserialize(jsonObject.get("description"), Text.class));
         }

         if (jsonObject.has("players")) {
            lv.setPlayers((ServerMetadata.Players)jsonDeserializationContext.deserialize(jsonObject.get("players"), ServerMetadata.Players.class));
         }

         if (jsonObject.has("version")) {
            lv.setVersion((ServerMetadata.Version)jsonDeserializationContext.deserialize(jsonObject.get("version"), ServerMetadata.Version.class));
         }

         if (jsonObject.has("favicon")) {
            lv.setFavicon(JsonHelper.getString(jsonObject, "favicon"));
         }

         return lv;
      }

      public JsonElement serialize(ServerMetadata arg, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject jsonObject = new JsonObject();
         if (arg.getDescription() != null) {
            jsonObject.add("description", jsonSerializationContext.serialize(arg.getDescription()));
         }

         if (arg.getPlayers() != null) {
            jsonObject.add("players", jsonSerializationContext.serialize(arg.getPlayers()));
         }

         if (arg.getVersion() != null) {
            jsonObject.add("version", jsonSerializationContext.serialize(arg.getVersion()));
         }

         if (arg.getFavicon() != null) {
            jsonObject.addProperty("favicon", arg.getFavicon());
         }

         return jsonObject;
      }
   }

   public static class Players {
      private final int max;
      private final int online;
      private GameProfile[] sample;

      public Players(int max, int online) {
         this.max = max;
         this.online = online;
      }

      public int getPlayerLimit() {
         return this.max;
      }

      public int getOnlinePlayerCount() {
         return this.online;
      }

      public GameProfile[] getSample() {
         return this.sample;
      }

      public void setSample(GameProfile[] sample) {
         this.sample = sample;
      }

      public static class Deserializer implements JsonDeserializer<ServerMetadata.Players>, JsonSerializer<ServerMetadata.Players> {
         public Deserializer() {
         }

         public ServerMetadata.Players deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "players");
            ServerMetadata.Players lv = new ServerMetadata.Players(JsonHelper.getInt(jsonObject, "max"), JsonHelper.getInt(jsonObject, "online"));
            if (JsonHelper.hasArray(jsonObject, "sample")) {
               JsonArray jsonArray = JsonHelper.getArray(jsonObject, "sample");
               if (jsonArray.size() > 0) {
                  GameProfile[] gameProfiles = new GameProfile[jsonArray.size()];

                  for (int i = 0; i < gameProfiles.length; i++) {
                     JsonObject jsonObject2 = JsonHelper.asObject(jsonArray.get(i), "player[" + i + "]");
                     String string = JsonHelper.getString(jsonObject2, "id");
                     gameProfiles[i] = new GameProfile(UUID.fromString(string), JsonHelper.getString(jsonObject2, "name"));
                  }

                  lv.setSample(gameProfiles);
               }
            }

            return lv;
         }

         public JsonElement serialize(ServerMetadata.Players arg, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("max", arg.getPlayerLimit());
            jsonObject.addProperty("online", arg.getOnlinePlayerCount());
            if (arg.getSample() != null && arg.getSample().length > 0) {
               JsonArray jsonArray = new JsonArray();

               for (int i = 0; i < arg.getSample().length; i++) {
                  JsonObject jsonObject2 = new JsonObject();
                  UUID uUID = arg.getSample()[i].getId();
                  jsonObject2.addProperty("id", uUID == null ? "" : uUID.toString());
                  jsonObject2.addProperty("name", arg.getSample()[i].getName());
                  jsonArray.add(jsonObject2);
               }

               jsonObject.add("sample", jsonArray);
            }

            return jsonObject;
         }
      }
   }

   public static class Version {
      private final String gameVersion;
      private final int protocolVersion;

      public Version(String gameVersion, int protocolVersion) {
         this.gameVersion = gameVersion;
         this.protocolVersion = protocolVersion;
      }

      public String getGameVersion() {
         return this.gameVersion;
      }

      public int getProtocolVersion() {
         return this.protocolVersion;
      }

      public static class Serializer implements JsonDeserializer<ServerMetadata.Version>, JsonSerializer<ServerMetadata.Version> {
         public Serializer() {
         }

         public ServerMetadata.Version deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, "version");
            return new ServerMetadata.Version(JsonHelper.getString(jsonObject, "name"), JsonHelper.getInt(jsonObject, "protocol"));
         }

         public JsonElement serialize(ServerMetadata.Version arg, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", arg.getGameVersion());
            jsonObject.addProperty("protocol", arg.getProtocolVersion());
            return jsonObject;
         }
      }
   }
}
