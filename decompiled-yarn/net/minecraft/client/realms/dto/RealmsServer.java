package net.minecraft.client.realms.dto;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.client.realms.util.RealmsUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsServer extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public long id;
   public String remoteSubscriptionId;
   public String name;
   public String motd;
   public RealmsServer.State state;
   public String owner;
   public String ownerUUID;
   public List<PlayerInfo> players;
   public Map<Integer, RealmsWorldOptions> slots;
   public boolean expired;
   public boolean expiredTrial;
   public int daysLeft;
   public RealmsServer.WorldType worldType;
   public int activeSlot;
   public String minigameName;
   public int minigameId;
   public String minigameImage;
   public RealmsServerPing serverPing = new RealmsServerPing();

   public RealmsServer() {
   }

   public String getDescription() {
      return this.motd;
   }

   public String getName() {
      return this.name;
   }

   public String getMinigameName() {
      return this.minigameName;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setDescription(String description) {
      this.motd = description;
   }

   public void updateServerPing(RealmsServerPlayerList serverPlayerList) {
      List<String> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (String _snowmanxx : serverPlayerList.players) {
         if (!_snowmanxx.equals(MinecraftClient.getInstance().getSession().getUuid())) {
            String _snowmanxxx = "";

            try {
               _snowmanxxx = RealmsUtil.uuidToName(_snowmanxx);
            } catch (Exception var8) {
               LOGGER.error("Could not get name for " + _snowmanxx, var8);
               continue;
            }

            _snowman.add(_snowmanxxx);
            _snowmanx++;
         }
      }

      this.serverPing.nrOfPlayers = String.valueOf(_snowmanx);
      this.serverPing.playerList = Joiner.on('\n').join(_snowman);
   }

   public static RealmsServer parse(JsonObject node) {
      RealmsServer _snowman = new RealmsServer();

      try {
         _snowman.id = JsonUtils.getLongOr("id", node, -1L);
         _snowman.remoteSubscriptionId = JsonUtils.getStringOr("remoteSubscriptionId", node, null);
         _snowman.name = JsonUtils.getStringOr("name", node, null);
         _snowman.motd = JsonUtils.getStringOr("motd", node, null);
         _snowman.state = getState(JsonUtils.getStringOr("state", node, RealmsServer.State.CLOSED.name()));
         _snowman.owner = JsonUtils.getStringOr("owner", node, null);
         if (node.get("players") != null && node.get("players").isJsonArray()) {
            _snowman.players = parseInvited(node.get("players").getAsJsonArray());
            sortInvited(_snowman);
         } else {
            _snowman.players = Lists.newArrayList();
         }

         _snowman.daysLeft = JsonUtils.getIntOr("daysLeft", node, 0);
         _snowman.expired = JsonUtils.getBooleanOr("expired", node, false);
         _snowman.expiredTrial = JsonUtils.getBooleanOr("expiredTrial", node, false);
         _snowman.worldType = getWorldType(JsonUtils.getStringOr("worldType", node, RealmsServer.WorldType.NORMAL.name()));
         _snowman.ownerUUID = JsonUtils.getStringOr("ownerUUID", node, "");
         if (node.get("slots") != null && node.get("slots").isJsonArray()) {
            _snowman.slots = parseSlots(node.get("slots").getAsJsonArray());
         } else {
            _snowman.slots = getEmptySlots();
         }

         _snowman.minigameName = JsonUtils.getStringOr("minigameName", node, null);
         _snowman.activeSlot = JsonUtils.getIntOr("activeSlot", node, -1);
         _snowman.minigameId = JsonUtils.getIntOr("minigameId", node, -1);
         _snowman.minigameImage = JsonUtils.getStringOr("minigameImage", node, null);
      } catch (Exception var3) {
         LOGGER.error("Could not parse McoServer: " + var3.getMessage());
      }

      return _snowman;
   }

   private static void sortInvited(RealmsServer server) {
      server.players
         .sort(
            (_snowman, _snowmanx) -> ComparisonChain.start()
                  .compareFalseFirst(_snowmanx.getAccepted(), _snowman.getAccepted())
                  .compare(_snowman.getName().toLowerCase(Locale.ROOT), _snowmanx.getName().toLowerCase(Locale.ROOT))
                  .result()
         );
   }

   private static List<PlayerInfo> parseInvited(JsonArray jsonArray) {
      List<PlayerInfo> _snowman = Lists.newArrayList();

      for (JsonElement _snowmanx : jsonArray) {
         try {
            JsonObject _snowmanxx = _snowmanx.getAsJsonObject();
            PlayerInfo _snowmanxxx = new PlayerInfo();
            _snowmanxxx.setName(JsonUtils.getStringOr("name", _snowmanxx, null));
            _snowmanxxx.setUuid(JsonUtils.getStringOr("uuid", _snowmanxx, null));
            _snowmanxxx.setOperator(JsonUtils.getBooleanOr("operator", _snowmanxx, false));
            _snowmanxxx.setAccepted(JsonUtils.getBooleanOr("accepted", _snowmanxx, false));
            _snowmanxxx.setOnline(JsonUtils.getBooleanOr("online", _snowmanxx, false));
            _snowman.add(_snowmanxxx);
         } catch (Exception var6) {
         }
      }

      return _snowman;
   }

   private static Map<Integer, RealmsWorldOptions> parseSlots(JsonArray json) {
      Map<Integer, RealmsWorldOptions> _snowman = Maps.newHashMap();

      for (JsonElement _snowmanx : json) {
         try {
            JsonObject _snowmanxx = _snowmanx.getAsJsonObject();
            JsonParser _snowmanxxx = new JsonParser();
            JsonElement _snowmanxxxx = _snowmanxxx.parse(_snowmanxx.get("options").getAsString());
            RealmsWorldOptions _snowmanxxxxx;
            if (_snowmanxxxx == null) {
               _snowmanxxxxx = RealmsWorldOptions.getDefaults();
            } else {
               _snowmanxxxxx = RealmsWorldOptions.parse(_snowmanxxxx.getAsJsonObject());
            }

            int _snowmanxxxxxx = JsonUtils.getIntOr("slotId", _snowmanxx, -1);
            _snowman.put(_snowmanxxxxxx, _snowmanxxxxx);
         } catch (Exception var9) {
         }
      }

      for (int _snowmanx = 1; _snowmanx <= 3; _snowmanx++) {
         if (!_snowman.containsKey(_snowmanx)) {
            _snowman.put(_snowmanx, RealmsWorldOptions.getEmptyDefaults());
         }
      }

      return _snowman;
   }

   private static Map<Integer, RealmsWorldOptions> getEmptySlots() {
      Map<Integer, RealmsWorldOptions> _snowman = Maps.newHashMap();
      _snowman.put(1, RealmsWorldOptions.getEmptyDefaults());
      _snowman.put(2, RealmsWorldOptions.getEmptyDefaults());
      _snowman.put(3, RealmsWorldOptions.getEmptyDefaults());
      return _snowman;
   }

   public static RealmsServer parse(String json) {
      try {
         return parse(new JsonParser().parse(json).getAsJsonObject());
      } catch (Exception var2) {
         LOGGER.error("Could not parse McoServer: " + var2.getMessage());
         return new RealmsServer();
      }
   }

   private static RealmsServer.State getState(String state) {
      try {
         return RealmsServer.State.valueOf(state);
      } catch (Exception var2) {
         return RealmsServer.State.CLOSED;
      }
   }

   private static RealmsServer.WorldType getWorldType(String state) {
      try {
         return RealmsServer.WorldType.valueOf(state);
      } catch (Exception var2) {
         return RealmsServer.WorldType.NORMAL;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.id, this.name, this.motd, this.state, this.owner, this.expired);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (obj.getClass() != this.getClass()) {
         return false;
      } else {
         RealmsServer _snowman = (RealmsServer)obj;
         return new EqualsBuilder()
            .append(this.id, _snowman.id)
            .append(this.name, _snowman.name)
            .append(this.motd, _snowman.motd)
            .append(this.state, _snowman.state)
            .append(this.owner, _snowman.owner)
            .append(this.expired, _snowman.expired)
            .append(this.worldType, this.worldType)
            .isEquals();
      }
   }

   public RealmsServer clone() {
      RealmsServer _snowman = new RealmsServer();
      _snowman.id = this.id;
      _snowman.remoteSubscriptionId = this.remoteSubscriptionId;
      _snowman.name = this.name;
      _snowman.motd = this.motd;
      _snowman.state = this.state;
      _snowman.owner = this.owner;
      _snowman.players = this.players;
      _snowman.slots = this.cloneSlots(this.slots);
      _snowman.expired = this.expired;
      _snowman.expiredTrial = this.expiredTrial;
      _snowman.daysLeft = this.daysLeft;
      _snowman.serverPing = new RealmsServerPing();
      _snowman.serverPing.nrOfPlayers = this.serverPing.nrOfPlayers;
      _snowman.serverPing.playerList = this.serverPing.playerList;
      _snowman.worldType = this.worldType;
      _snowman.ownerUUID = this.ownerUUID;
      _snowman.minigameName = this.minigameName;
      _snowman.activeSlot = this.activeSlot;
      _snowman.minigameId = this.minigameId;
      _snowman.minigameImage = this.minigameImage;
      return _snowman;
   }

   public Map<Integer, RealmsWorldOptions> cloneSlots(Map<Integer, RealmsWorldOptions> slots) {
      Map<Integer, RealmsWorldOptions> _snowman = Maps.newHashMap();

      for (Entry<Integer, RealmsWorldOptions> _snowmanx : slots.entrySet()) {
         _snowman.put(_snowmanx.getKey(), _snowmanx.getValue().clone());
      }

      return _snowman;
   }

   public String getWorldName(int slotId) {
      return this.name + " (" + this.slots.get(slotId).getSlotName(slotId) + ")";
   }

   public ServerInfo method_31403(String _snowman) {
      return new ServerInfo(this.name, _snowman, false);
   }

   public static class McoServerComparator implements Comparator<RealmsServer> {
      private final String refOwner;

      public McoServerComparator(String owner) {
         this.refOwner = owner;
      }

      public int compare(RealmsServer _snowman, RealmsServer _snowman) {
         return ComparisonChain.start()
            .compareTrueFirst(_snowman.state == RealmsServer.State.UNINITIALIZED, _snowman.state == RealmsServer.State.UNINITIALIZED)
            .compareTrueFirst(_snowman.expiredTrial, _snowman.expiredTrial)
            .compareTrueFirst(_snowman.owner.equals(this.refOwner), _snowman.owner.equals(this.refOwner))
            .compareFalseFirst(_snowman.expired, _snowman.expired)
            .compareTrueFirst(_snowman.state == RealmsServer.State.OPEN, _snowman.state == RealmsServer.State.OPEN)
            .compare(_snowman.id, _snowman.id)
            .result();
      }
   }

   public static enum State {
      CLOSED,
      OPEN,
      UNINITIALIZED;

      private State() {
      }
   }

   public static enum WorldType {
      NORMAL,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;

      private WorldType() {
      }
   }
}
