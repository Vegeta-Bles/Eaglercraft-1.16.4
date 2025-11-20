package net.minecraft.network.packet.s2c.play;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;
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

      for (ServerPlayerEntity _snowman : players) {
         this.entries.add(new PlayerListS2CPacket.Entry(_snowman.getGameProfile(), _snowman.pingMilliseconds, _snowman.interactionManager.getGameMode(), _snowman.getPlayerListName()));
      }
   }

   public PlayerListS2CPacket(PlayerListS2CPacket.Action action, Iterable<ServerPlayerEntity> _snowman) {
      this.action = action;

      for (ServerPlayerEntity _snowmanx : _snowman) {
         this.entries.add(new PlayerListS2CPacket.Entry(_snowmanx.getGameProfile(), _snowmanx.pingMilliseconds, _snowmanx.interactionManager.getGameMode(), _snowmanx.getPlayerListName()));
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.action = buf.readEnumConstant(PlayerListS2CPacket.Action.class);
      int _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         GameProfile _snowmanxx = null;
         int _snowmanxxx = 0;
         GameMode _snowmanxxxx = null;
         Text _snowmanxxxxx = null;
         switch (this.action) {
            case ADD_PLAYER:
               _snowmanxx = new GameProfile(buf.readUuid(), buf.readString(16));
               int _snowmanxxxxxx = buf.readVarInt();
               int _snowmanxxxxxxx = 0;

               for (; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
                  String _snowmanxxxxxxxx = buf.readString(32767);
                  String _snowmanxxxxxxxxx = buf.readString(32767);
                  if (buf.readBoolean()) {
                     _snowmanxx.getProperties().put(_snowmanxxxxxxxx, new Property(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, buf.readString(32767)));
                  } else {
                     _snowmanxx.getProperties().put(_snowmanxxxxxxxx, new Property(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
                  }
               }

               _snowmanxxxx = GameMode.byId(buf.readVarInt());
               _snowmanxxx = buf.readVarInt();
               if (buf.readBoolean()) {
                  _snowmanxxxxx = buf.readText();
               }
               break;
            case UPDATE_GAME_MODE:
               _snowmanxx = new GameProfile(buf.readUuid(), null);
               _snowmanxxxx = GameMode.byId(buf.readVarInt());
               break;
            case UPDATE_LATENCY:
               _snowmanxx = new GameProfile(buf.readUuid(), null);
               _snowmanxxx = buf.readVarInt();
               break;
            case UPDATE_DISPLAY_NAME:
               _snowmanxx = new GameProfile(buf.readUuid(), null);
               if (buf.readBoolean()) {
                  _snowmanxxxxx = buf.readText();
               }
               break;
            case REMOVE_PLAYER:
               _snowmanxx = new GameProfile(buf.readUuid(), null);
         }

         this.entries.add(new PlayerListS2CPacket.Entry(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeEnumConstant(this.action);
      buf.writeVarInt(this.entries.size());

      for (PlayerListS2CPacket.Entry _snowman : this.entries) {
         switch (this.action) {
            case ADD_PLAYER:
               buf.writeUuid(_snowman.getProfile().getId());
               buf.writeString(_snowman.getProfile().getName());
               buf.writeVarInt(_snowman.getProfile().getProperties().size());

               for (Property _snowmanx : _snowman.getProfile().getProperties().values()) {
                  buf.writeString(_snowmanx.getName());
                  buf.writeString(_snowmanx.getValue());
                  if (_snowmanx.hasSignature()) {
                     buf.writeBoolean(true);
                     buf.writeString(_snowmanx.getSignature());
                  } else {
                     buf.writeBoolean(false);
                  }
               }

               buf.writeVarInt(_snowman.getGameMode().getId());
               buf.writeVarInt(_snowman.getLatency());
               if (_snowman.getDisplayName() == null) {
                  buf.writeBoolean(false);
               } else {
                  buf.writeBoolean(true);
                  buf.writeText(_snowman.getDisplayName());
               }
               break;
            case UPDATE_GAME_MODE:
               buf.writeUuid(_snowman.getProfile().getId());
               buf.writeVarInt(_snowman.getGameMode().getId());
               break;
            case UPDATE_LATENCY:
               buf.writeUuid(_snowman.getProfile().getId());
               buf.writeVarInt(_snowman.getLatency());
               break;
            case UPDATE_DISPLAY_NAME:
               buf.writeUuid(_snowman.getProfile().getId());
               if (_snowman.getDisplayName() == null) {
                  buf.writeBoolean(false);
               } else {
                  buf.writeBoolean(true);
                  buf.writeText(_snowman.getDisplayName());
               }
               break;
            case REMOVE_PLAYER:
               buf.writeUuid(_snowman.getProfile().getId());
         }
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onPlayerList(this);
   }

   public List<PlayerListS2CPacket.Entry> getEntries() {
      return this.entries;
   }

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
