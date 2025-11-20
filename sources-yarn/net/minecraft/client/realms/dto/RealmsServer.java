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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.client.realms.util.RealmsUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
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
      List<String> list = Lists.newArrayList();
      int i = 0;

      for (String string : serverPlayerList.players) {
         if (!string.equals(MinecraftClient.getInstance().getSession().getUuid())) {
            String string2 = "";

            try {
               string2 = RealmsUtil.uuidToName(string);
            } catch (Exception var8) {
               LOGGER.error("Could not get name for " + string, var8);
               continue;
            }

            list.add(string2);
            i++;
         }
      }

      this.serverPing.nrOfPlayers = String.valueOf(i);
      this.serverPing.playerList = Joiner.on('\n').join(list);
   }

   public static RealmsServer parse(JsonObject node) {
      RealmsServer lv = new RealmsServer();

      try {
         lv.id = JsonUtils.getLongOr("id", node, -1L);
         lv.remoteSubscriptionId = JsonUtils.getStringOr("remoteSubscriptionId", node, null);
         lv.name = JsonUtils.getStringOr("name", node, null);
         lv.motd = JsonUtils.getStringOr("motd", node, null);
         lv.state = getState(JsonUtils.getStringOr("state", node, RealmsServer.State.CLOSED.name()));
         lv.owner = JsonUtils.getStringOr("owner", node, null);
         if (node.get("players") != null && node.get("players").isJsonArray()) {
            lv.players = parseInvited(node.get("players").getAsJsonArray());
            sortInvited(lv);
         } else {
            lv.players = Lists.newArrayList();
         }

         lv.daysLeft = JsonUtils.getIntOr("daysLeft", node, 0);
         lv.expired = JsonUtils.getBooleanOr("expired", node, false);
         lv.expiredTrial = JsonUtils.getBooleanOr("expiredTrial", node, false);
         lv.worldType = getWorldType(JsonUtils.getStringOr("worldType", node, RealmsServer.WorldType.NORMAL.name()));
         lv.ownerUUID = JsonUtils.getStringOr("ownerUUID", node, "");
         if (node.get("slots") != null && node.get("slots").isJsonArray()) {
            lv.slots = parseSlots(node.get("slots").getAsJsonArray());
         } else {
            lv.slots = getEmptySlots();
         }

         lv.minigameName = JsonUtils.getStringOr("minigameName", node, null);
         lv.activeSlot = JsonUtils.getIntOr("activeSlot", node, -1);
         lv.minigameId = JsonUtils.getIntOr("minigameId", node, -1);
         lv.minigameImage = JsonUtils.getStringOr("minigameImage", node, null);
      } catch (Exception var3) {
         LOGGER.error("Could not parse McoServer: " + var3.getMessage());
      }

      return lv;
   }

   private static void sortInvited(RealmsServer server) {
      server.players
         .sort(
            (arg, arg2) -> ComparisonChain.start()
                  .compareFalseFirst(arg2.getAccepted(), arg.getAccepted())
                  .compare(arg.getName().toLowerCase(Locale.ROOT), arg2.getName().toLowerCase(Locale.ROOT))
                  .result()
         );
   }

   private static List<PlayerInfo> parseInvited(JsonArray jsonArray) {
      List<PlayerInfo> list = Lists.newArrayList();

      for (JsonElement jsonElement : jsonArray) {
         try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            PlayerInfo lv = new PlayerInfo();
            lv.setName(JsonUtils.getStringOr("name", jsonObject, null));
            lv.setUuid(JsonUtils.getStringOr("uuid", jsonObject, null));
            lv.setOperator(JsonUtils.getBooleanOr("operator", jsonObject, false));
            lv.setAccepted(JsonUtils.getBooleanOr("accepted", jsonObject, false));
            lv.setOnline(JsonUtils.getBooleanOr("online", jsonObject, false));
            list.add(lv);
         } catch (Exception var6) {
         }
      }

      return list;
   }

   private static Map<Integer, RealmsWorldOptions> parseSlots(JsonArray json) {
      Map<Integer, RealmsWorldOptions> map = Maps.newHashMap();

      for (JsonElement jsonElement : json) {
         try {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement2 = jsonParser.parse(jsonObject.get("options").getAsString());
            RealmsWorldOptions lv;
            if (jsonElement2 == null) {
               lv = RealmsWorldOptions.getDefaults();
            } else {
               lv = RealmsWorldOptions.parse(jsonElement2.getAsJsonObject());
            }

            int i = JsonUtils.getIntOr("slotId", jsonObject, -1);
            map.put(i, lv);
         } catch (Exception var9) {
         }
      }

      for (int j = 1; j <= 3; j++) {
         if (!map.containsKey(j)) {
            map.put(j, RealmsWorldOptions.getEmptyDefaults());
         }
      }

      return map;
   }

   private static Map<Integer, RealmsWorldOptions> getEmptySlots() {
      Map<Integer, RealmsWorldOptions> map = Maps.newHashMap();
      map.put(1, RealmsWorldOptions.getEmptyDefaults());
      map.put(2, RealmsWorldOptions.getEmptyDefaults());
      map.put(3, RealmsWorldOptions.getEmptyDefaults());
      return map;
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
         RealmsServer lv = (RealmsServer)obj;
         return new EqualsBuilder()
            .append(this.id, lv.id)
            .append(this.name, lv.name)
            .append(this.motd, lv.motd)
            .append(this.state, lv.state)
            .append(this.owner, lv.owner)
            .append(this.expired, lv.expired)
            .append(this.worldType, this.worldType)
            .isEquals();
      }
   }

   public RealmsServer clone() {
      RealmsServer lv = new RealmsServer();
      lv.id = this.id;
      lv.remoteSubscriptionId = this.remoteSubscriptionId;
      lv.name = this.name;
      lv.motd = this.motd;
      lv.state = this.state;
      lv.owner = this.owner;
      lv.players = this.players;
      lv.slots = this.cloneSlots(this.slots);
      lv.expired = this.expired;
      lv.expiredTrial = this.expiredTrial;
      lv.daysLeft = this.daysLeft;
      lv.serverPing = new RealmsServerPing();
      lv.serverPing.nrOfPlayers = this.serverPing.nrOfPlayers;
      lv.serverPing.playerList = this.serverPing.playerList;
      lv.worldType = this.worldType;
      lv.ownerUUID = this.ownerUUID;
      lv.minigameName = this.minigameName;
      lv.activeSlot = this.activeSlot;
      lv.minigameId = this.minigameId;
      lv.minigameImage = this.minigameImage;
      return lv;
   }

   public Map<Integer, RealmsWorldOptions> cloneSlots(Map<Integer, RealmsWorldOptions> slots) {
      Map<Integer, RealmsWorldOptions> map2 = Maps.newHashMap();

      for (Entry<Integer, RealmsWorldOptions> entry : slots.entrySet()) {
         map2.put(entry.getKey(), entry.getValue().clone());
      }

      return map2;
   }

   public String getWorldName(int slotId) {
      return this.name + " (" + this.slots.get(slotId).getSlotName(slotId) + ")";
   }

   public ServerInfo method_31403(String string) {
      return new ServerInfo(this.name, string, false);
   }

   @Environment(EnvType.CLIENT)
   public static class McoServerComparator implements Comparator<RealmsServer> {
      private final String refOwner;

      public McoServerComparator(String owner) {
         this.refOwner = owner;
      }

      public int compare(RealmsServer arg, RealmsServer arg2) {
         return ComparisonChain.start()
            .compareTrueFirst(arg.state == RealmsServer.State.UNINITIALIZED, arg2.state == RealmsServer.State.UNINITIALIZED)
            .compareTrueFirst(arg.expiredTrial, arg2.expiredTrial)
            .compareTrueFirst(arg.owner.equals(this.refOwner), arg2.owner.equals(this.refOwner))
            .compareFalseFirst(arg.expired, arg2.expired)
            .compareTrueFirst(arg.state == RealmsServer.State.OPEN, arg2.state == RealmsServer.State.OPEN)
            .compare(arg.id, arg2.id)
            .result();
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum State {
      CLOSED,
      OPEN,
      UNINITIALIZED;

      private State() {
      }
   }

   @Environment(EnvType.CLIENT)
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
