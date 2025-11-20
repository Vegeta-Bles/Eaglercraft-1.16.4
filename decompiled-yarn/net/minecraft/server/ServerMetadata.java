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

      public ServerMetadata deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "status");
         ServerMetadata _snowmanxxxx = new ServerMetadata();
         if (_snowmanxxx.has("description")) {
            _snowmanxxxx.setDescription((Text)_snowman.deserialize(_snowmanxxx.get("description"), Text.class));
         }

         if (_snowmanxxx.has("players")) {
            _snowmanxxxx.setPlayers((ServerMetadata.Players)_snowman.deserialize(_snowmanxxx.get("players"), ServerMetadata.Players.class));
         }

         if (_snowmanxxx.has("version")) {
            _snowmanxxxx.setVersion((ServerMetadata.Version)_snowman.deserialize(_snowmanxxx.get("version"), ServerMetadata.Version.class));
         }

         if (_snowmanxxx.has("favicon")) {
            _snowmanxxxx.setFavicon(JsonHelper.getString(_snowmanxxx, "favicon"));
         }

         return _snowmanxxxx;
      }

      public JsonElement serialize(ServerMetadata _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         if (_snowman.getDescription() != null) {
            _snowmanxxx.add("description", _snowman.serialize(_snowman.getDescription()));
         }

         if (_snowman.getPlayers() != null) {
            _snowmanxxx.add("players", _snowman.serialize(_snowman.getPlayers()));
         }

         if (_snowman.getVersion() != null) {
            _snowmanxxx.add("version", _snowman.serialize(_snowman.getVersion()));
         }

         if (_snowman.getFavicon() != null) {
            _snowmanxxx.addProperty("favicon", _snowman.getFavicon());
         }

         return _snowmanxxx;
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

         public ServerMetadata.Players deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
            JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "players");
            ServerMetadata.Players _snowmanxxxx = new ServerMetadata.Players(JsonHelper.getInt(_snowmanxxx, "max"), JsonHelper.getInt(_snowmanxxx, "online"));
            if (JsonHelper.hasArray(_snowmanxxx, "sample")) {
               JsonArray _snowmanxxxxx = JsonHelper.getArray(_snowmanxxx, "sample");
               if (_snowmanxxxxx.size() > 0) {
                  GameProfile[] _snowmanxxxxxx = new GameProfile[_snowmanxxxxx.size()];

                  for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxx++) {
                     JsonObject _snowmanxxxxxxxx = JsonHelper.asObject(_snowmanxxxxx.get(_snowmanxxxxxxx), "player[" + _snowmanxxxxxxx + "]");
                     String _snowmanxxxxxxxxx = JsonHelper.getString(_snowmanxxxxxxxx, "id");
                     _snowmanxxxxxx[_snowmanxxxxxxx] = new GameProfile(UUID.fromString(_snowmanxxxxxxxxx), JsonHelper.getString(_snowmanxxxxxxxx, "name"));
                  }

                  _snowmanxxxx.setSample(_snowmanxxxxxx);
               }
            }

            return _snowmanxxxx;
         }

         public JsonElement serialize(ServerMetadata.Players _snowman, Type _snowman, JsonSerializationContext _snowman) {
            JsonObject _snowmanxxx = new JsonObject();
            _snowmanxxx.addProperty("max", _snowman.getPlayerLimit());
            _snowmanxxx.addProperty("online", _snowman.getOnlinePlayerCount());
            if (_snowman.getSample() != null && _snowman.getSample().length > 0) {
               JsonArray _snowmanxxxx = new JsonArray();

               for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman.getSample().length; _snowmanxxxxx++) {
                  JsonObject _snowmanxxxxxx = new JsonObject();
                  UUID _snowmanxxxxxxx = _snowman.getSample()[_snowmanxxxxx].getId();
                  _snowmanxxxxxx.addProperty("id", _snowmanxxxxxxx == null ? "" : _snowmanxxxxxxx.toString());
                  _snowmanxxxxxx.addProperty("name", _snowman.getSample()[_snowmanxxxxx].getName());
                  _snowmanxxxx.add(_snowmanxxxxxx);
               }

               _snowmanxxx.add("sample", _snowmanxxxx);
            }

            return _snowmanxxx;
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

         public ServerMetadata.Version deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
            JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "version");
            return new ServerMetadata.Version(JsonHelper.getString(_snowmanxxx, "name"), JsonHelper.getInt(_snowmanxxx, "protocol"));
         }

         public JsonElement serialize(ServerMetadata.Version _snowman, Type _snowman, JsonSerializationContext _snowman) {
            JsonObject _snowmanxxx = new JsonObject();
            _snowmanxxx.addProperty("name", _snowman.getGameVersion());
            _snowmanxxx.addProperty("protocol", _snowman.getProtocolVersion());
            return _snowmanxxx;
         }
      }
   }
}
