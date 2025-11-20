package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.util.Util;

public class SocialInteractionsManager {
   private final MinecraftClient client;
   private final Set<UUID> hiddenPlayers = Sets.newHashSet();
   private final SocialInteractionsService socialInteractionsService;
   private final Map<String, UUID> field_26927 = Maps.newHashMap();

   public SocialInteractionsManager(MinecraftClient client, SocialInteractionsService socialInteractionsService) {
      this.client = client;
      this.socialInteractionsService = socialInteractionsService;
   }

   public void hidePlayer(UUID uuid) {
      this.hiddenPlayers.add(uuid);
   }

   public void showPlayer(UUID uuid) {
      this.hiddenPlayers.remove(uuid);
   }

   public boolean method_31391(UUID _snowman) {
      return this.isPlayerHidden(_snowman) || this.isPlayerBlocked(_snowman);
   }

   public boolean isPlayerHidden(UUID uuid) {
      return this.hiddenPlayers.contains(uuid);
   }

   public boolean isPlayerBlocked(UUID uuid) {
      return this.socialInteractionsService.isBlockedPlayer(uuid);
   }

   public Set<UUID> getHiddenPlayers() {
      return this.hiddenPlayers;
   }

   public UUID method_31407(String _snowman) {
      return this.field_26927.getOrDefault(_snowman, Util.NIL_UUID);
   }

   public void method_31337(PlayerListEntry _snowman) {
      GameProfile _snowmanx = _snowman.getProfile();
      if (_snowmanx.isComplete()) {
         this.field_26927.put(_snowmanx.getName(), _snowmanx.getId());
      }

      Screen _snowmanxx = this.client.currentScreen;
      if (_snowmanxx instanceof SocialInteractionsScreen) {
         SocialInteractionsScreen _snowmanxxx = (SocialInteractionsScreen)_snowmanxx;
         _snowmanxxx.method_31353(_snowman);
      }
   }

   public void method_31341(UUID _snowman) {
      Screen _snowmanx = this.client.currentScreen;
      if (_snowmanx instanceof SocialInteractionsScreen) {
         SocialInteractionsScreen _snowmanxx = (SocialInteractionsScreen)_snowmanx;
         _snowmanxx.method_31355(_snowman);
      }
   }
}
