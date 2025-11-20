package net.minecraft.client.gui.hud.spectator;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameMode;

public class TeleportSpectatorMenu implements SpectatorMenuCommandGroup, SpectatorMenuCommand {
   private static final Ordering<PlayerListEntry> ORDERING = Ordering.from(
      (_snowman, _snowmanx) -> ComparisonChain.start().compare(_snowman.getProfile().getId(), _snowmanx.getProfile().getId()).result()
   );
   private static final Text field_26616 = new TranslatableText("spectatorMenu.teleport");
   private static final Text field_26617 = new TranslatableText("spectatorMenu.teleport.prompt");
   private final List<SpectatorMenuCommand> elements = Lists.newArrayList();

   public TeleportSpectatorMenu() {
      this(ORDERING.sortedCopy(MinecraftClient.getInstance().getNetworkHandler().getPlayerList()));
   }

   public TeleportSpectatorMenu(Collection<PlayerListEntry> entries) {
      for (PlayerListEntry _snowman : ORDERING.sortedCopy(entries)) {
         if (_snowman.getGameMode() != GameMode.SPECTATOR) {
            this.elements.add(new TeleportToSpecificPlayerSpectatorCommand(_snowman.getProfile()));
         }
      }
   }

   @Override
   public List<SpectatorMenuCommand> getCommands() {
      return this.elements;
   }

   @Override
   public Text getPrompt() {
      return field_26617;
   }

   @Override
   public void use(SpectatorMenu menu) {
      menu.selectElement(this);
   }

   @Override
   public Text getName() {
      return field_26616;
   }

   @Override
   public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
      MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
      DrawableHelper.drawTexture(_snowman, 0, 0, 0.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean isEnabled() {
      return !this.elements.isEmpty();
   }
}
