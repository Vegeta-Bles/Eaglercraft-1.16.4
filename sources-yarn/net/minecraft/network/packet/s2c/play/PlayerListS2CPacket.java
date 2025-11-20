package net.minecraft.network.packet.s2c.play;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class PlayerListS2CPacket implements Packet<ClientPlayPacketListener> {
   private PlayerListS2CPacket.Action action;
   private final List<PlayerListS2CPacket.Entry> entries = Lists.newArrayList();

   public PlayerListS2CPacket() {
   }

   public PlayerListS2CPacket(PlayerListS2CPacket.Action action, ServerPlayerEntity... players) {
      this.action = action;

      for (ServerPlayerEntity lv : players) {
         this.entries.add(new PlayerListS2CPacket.Entry(lv.getGameProfile(), lv.pingMilliseconds, lv.interactionManager.getGameMode(), lv.getPlayerListName()));
      }
   }

   public PlayerListS2CPacket(PlayerListS2CPacket.Action action, Iterable<ServerPlayerEntity> iterable) {
      this.action = action;

      for (ServerPlayerEntity lv : iterable) {
         this.entries.add(new PlayerListS2CPacket.Entry(lv.getGameProfile(), lv.pingMilliseconds, lv.interactionManager.getGameMode(), lv.getPlayerListName()));
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.action = buf.readEnumConstant(PlayerListS2CPacket.Action.class);
      int i = buf.readVarInt();

      for (int j = 0; j < i; j++) {
         GameProfile gameProfile = null;
         int k = 0;
         GameMode lv = null;
         Text lv2 = null;
         switch (this.action) {
            case ADD_PLAYER:
               gameProfile = new GameProfile(buf.readUuid(), buf.readString(16));
               int l = buf.readVarInt();
               int m = 0;

               for (; m < l; m++) {
                  String string = buf.readString(32767);
                  String string2 = buf.readString(32767);
                  if (buf.readBoolean()) {
                     gameProfile.getProperties().put(string, new Property(string, string2, buf.readString(32767)));
                  } else {
                     gameProfile.getProperties().put(string, new Property(string, string2));
                  }
               }

               lv = GameMode.byId(buf.readVarInt());
               k = buf.readVarInt();
               if (buf.readBoolean()) {
                  lv2 = buf.readText();
               }
               break;
            case UPDATE_GAME_MODE:
               gameProfile = new GameProfile(buf.readUuid(), null);
               lv = GameMode.byId(buf.readVarInt());
               break;
            case UPDATE_LATENCY:
               gameProfile = new GameProfile(buf.readUuid(), null);
               k = buf.readVarInt();
               break;
            case UPDATE_DISPLAY_NAME:
               gameProfile = new GameProfile(buf.readUuid(), null);
               if (buf.readBoolean()) {
                  lv2 = buf.readText();
               }
               break;
            case REMOVE_PLAYER:
               gameProfile = new GameProfile(buf.readUuid(), null);
         }

         this.entries.add(new PlayerListS2CPacket.Entry(gameProfile, k, lv, lv2));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeEnumConstant(this.action);
      buf.writeVarInt(this.entries.size());

      for (PlayerListS2CPacket.Entry lv : this.entries) {
         switch (this.action) {
            case ADD_PLAYER:
               buf.writeUuid(lv.getProfile().getId());
               buf.writeString(lv.getProfile().getName());
               buf.writeVarInt(lv.getProfile().getProperties().size());

               for (Property property : lv.getProfile().getProperties().values()) {
                  buf.writeString(property.getName());
                  buf.writeString(property.getValue());
                  if (property.hasSignature()) {
                     buf.writeBoolean(true);
                     buf.writeString(property.getSignature());
                  } else {
                     buf.writeBoolean(false);
                  }
               }

               buf.writeVarInt(lv.getGameMode().getId());
               buf.writeVarInt(lv.getLatency());
               if (lv.getDisplayName() == null) {
                  buf.writeBoolean(false);
               } else {
                  buf.writeBoolean(true);
                  buf.writeText(lv.getDisplayName());
               }
               break;
            case UPDATE_GAME_MODE:
               buf.writeUuid(lv.getProfile().getId());
               buf.writeVarInt(lv.getGameMode().getId());
               break;
            case UPDATE_LATENCY:
               buf.writeUuid(lv.getProfile().getId());
               buf.writeVarInt(lv.getLatency());
               break;
            case UPDATE_DISPLAY_NAME:
               buf.writeUuid(lv.getProfile().getId());
               if (lv.getDisplayName() == null) {
                  buf.writeBoolean(false);
               } else {
                  buf.writeBoolean(true);
                  buf.writeText(lv.getDisplayName());
               }
               break;
            case REMOVE_PLAYER:
               buf.writeUuid(lv.getProfile().getId());
         }
      }
   }

   public void apply(ClientPlayPacketListener arg) {
      arg.onPlayerList(this);
   }

   @Environment(EnvType.CLIENT)
   public List<PlayerListS2CPacket.Entry> getEntries() {
      return this.entries;
   }

   @Environment(EnvType.CLIENT)
   public PlayerListS2CPacket.Action getAction() {
      return this.action;
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.action).add("entries", this.entries).toString();
   }

   public static enum Action {
      ADD_PLAYER,
      UPDATE_GAME_MODE,
      UPDATE_LATENCY,
      UPDATE_DISPLAY_NAME,
      REMOVE_PLAYER;

      private Action() {
      }
   }

   public class Entry {
      private final int latency;
      private final GameMode gameMode;
      private final GameProfile profile;
      private final Text displayName;

      public Entry(GameProfile profile, int latency, GameMode gameMode, @Nullable Text displayName) {
         this.profile = profile;
         this.latency = latency;
         this.gameMode = gameMode;
         this.displayName = displayName;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public int getLatency() {
         return this.latency;
      }

      public GameMode getGameMode() {
         return this.gameMode;
      }

      @Nullable
      public Text getDisplayName() {
         return this.displayName;
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("latency", this.latency)
            .add("gameMode", this.gameMode)
            .add("profile", this.profile)
            .add("displayName", this.displayName == null ? null : Text.Serializer.toJson(this.displayName))
            .toString();
      }
   }
}
