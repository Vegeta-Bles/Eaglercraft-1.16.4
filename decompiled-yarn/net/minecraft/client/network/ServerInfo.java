package net.minecraft.client.network;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ServerInfo {
   public String name;
   public String address;
   public Text playerCountLabel;
   public Text label;
   public long ping;
   public int protocolVersion = SharedConstants.getGameVersion().getProtocolVersion();
   public Text version = new LiteralText(SharedConstants.getGameVersion().getName());
   public boolean online;
   public List<Text> playerListSummary = Collections.emptyList();
   private ServerInfo.ResourcePackState resourcePackState = ServerInfo.ResourcePackState.PROMPT;
   @Nullable
   private String icon;
   private boolean local;

   public ServerInfo(String name, String address, boolean local) {
      this.name = name;
      this.address = address;
      this.local = local;
   }

   public CompoundTag serialize() {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("name", this.name);
      _snowman.putString("ip", this.address);
      if (this.icon != null) {
         _snowman.putString("icon", this.icon);
      }

      if (this.resourcePackState == ServerInfo.ResourcePackState.ENABLED) {
         _snowman.putBoolean("acceptTextures", true);
      } else if (this.resourcePackState == ServerInfo.ResourcePackState.DISABLED) {
         _snowman.putBoolean("acceptTextures", false);
      }

      return _snowman;
   }

   public ServerInfo.ResourcePackState getResourcePack() {
      return this.resourcePackState;
   }

   public void setResourcePackState(ServerInfo.ResourcePackState _snowman) {
      this.resourcePackState = _snowman;
   }

   public static ServerInfo deserialize(CompoundTag tag) {
      ServerInfo _snowman = new ServerInfo(tag.getString("name"), tag.getString("ip"), false);
      if (tag.contains("icon", 8)) {
         _snowman.setIcon(tag.getString("icon"));
      }

      if (tag.contains("acceptTextures", 1)) {
         if (tag.getBoolean("acceptTextures")) {
            _snowman.setResourcePackState(ServerInfo.ResourcePackState.ENABLED);
         } else {
            _snowman.setResourcePackState(ServerInfo.ResourcePackState.DISABLED);
         }
      } else {
         _snowman.setResourcePackState(ServerInfo.ResourcePackState.PROMPT);
      }

      return _snowman;
   }

   @Nullable
   public String getIcon() {
      return this.icon;
   }

   public void setIcon(@Nullable String _snowman) {
      this.icon = _snowman;
   }

   public boolean isLocal() {
      return this.local;
   }

   public void copyFrom(ServerInfo _snowman) {
      this.address = _snowman.address;
      this.name = _snowman.name;
      this.setResourcePackState(_snowman.getResourcePack());
      this.icon = _snowman.icon;
      this.local = _snowman.local;
   }

   public static enum ResourcePackState {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final Text name;

      private ResourcePackState(String name) {
         this.name = new TranslatableText("addServer.resourcePack." + name);
      }

      public Text getName() {
         return this.name;
      }
   }
}
