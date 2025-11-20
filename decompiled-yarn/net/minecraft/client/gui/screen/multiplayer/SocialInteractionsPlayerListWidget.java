package net.minecraft.client.gui.screen.multiplayer;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

public class SocialInteractionsPlayerListWidget extends ElementListWidget<SocialInteractionsPlayerListEntry> {
   private final SocialInteractionsScreen parent;
   private final MinecraftClient minecraftClient;
   private final List<SocialInteractionsPlayerListEntry> players = Lists.newArrayList();
   @Nullable
   private String currentSearch;

   public SocialInteractionsPlayerListWidget(
      SocialInteractionsScreen parent, MinecraftClient client, int width, int height, int top, int bottom, int itemHeight
   ) {
      super(client, width, height, top, bottom, itemHeight);
      this.parent = parent;
      this.minecraftClient = client;
      this.method_31322(false);
      this.method_31323(false);
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      double _snowman = this.minecraftClient.getWindow().getScaleFactor();
      RenderSystem.enableScissor(
         (int)((double)this.getRowLeft() * _snowman),
         (int)((double)(this.height - this.bottom) * _snowman),
         (int)((double)(this.getScrollbarPositionX() + 6) * _snowman),
         (int)((double)(this.height - (this.height - this.bottom) - this.top - 4) * _snowman)
      );
      super.render(matrices, mouseX, mouseY, delta);
      RenderSystem.disableScissor();
   }

   public void method_31393(Collection<UUID> _snowman, double _snowman) {
      this.players.clear();

      for (UUID _snowmanxx : _snowman) {
         PlayerListEntry _snowmanxxx = this.minecraftClient.player.networkHandler.getPlayerListEntry(_snowmanxx);
         if (_snowmanxxx != null) {
            this.players
               .add(
                  new SocialInteractionsPlayerListEntry(
                     this.minecraftClient, this.parent, _snowmanxxx.getProfile().getId(), _snowmanxxx.getProfile().getName(), _snowmanxxx::getSkinTexture
                  )
               );
         }
      }

      this.method_31349();
      this.players.sort((_snowmanxxx, _snowmanxxxx) -> _snowmanxxx.getName().compareToIgnoreCase(_snowmanxxxx.getName()));
      this.replaceEntries(this.players);
      this.setScrollAmount(_snowman);
   }

   private void method_31349() {
      if (this.currentSearch != null) {
         this.players.removeIf(_snowman -> !_snowman.getName().toLowerCase(Locale.ROOT).contains(this.currentSearch));
         this.replaceEntries(this.players);
      }
   }

   public void setCurrentSearch(String currentSearch) {
      this.currentSearch = currentSearch;
   }

   public boolean isEmpty() {
      return this.players.isEmpty();
   }

   public void method_31345(PlayerListEntry _snowman, SocialInteractionsScreen.Tab _snowman) {
      UUID _snowmanxx = _snowman.getProfile().getId();

      for (SocialInteractionsPlayerListEntry _snowmanxxx : this.players) {
         if (_snowmanxxx.getUuid().equals(_snowmanxx)) {
            _snowmanxxx.method_31335(false);
            return;
         }
      }

      if ((_snowman == SocialInteractionsScreen.Tab.ALL || this.minecraftClient.getSocialInteractionsManager().method_31391(_snowmanxx))
         && (Strings.isNullOrEmpty(this.currentSearch) || _snowman.getProfile().getName().toLowerCase(Locale.ROOT).contains(this.currentSearch))) {
         SocialInteractionsPlayerListEntry _snowmanxxxx = new SocialInteractionsPlayerListEntry(
            this.minecraftClient, this.parent, _snowman.getProfile().getId(), _snowman.getProfile().getName(), _snowman::getSkinTexture
         );
         this.addEntry(_snowmanxxxx);
         this.players.add(_snowmanxxxx);
      }
   }

   public void method_31347(UUID _snowman) {
      for (SocialInteractionsPlayerListEntry _snowmanx : this.players) {
         if (_snowmanx.getUuid().equals(_snowman)) {
            _snowmanx.method_31335(true);
            return;
         }
      }
   }
}
