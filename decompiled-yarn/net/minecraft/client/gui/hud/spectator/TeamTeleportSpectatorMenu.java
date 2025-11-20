package net.minecraft.client.gui.hud.spectator;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TeamTeleportSpectatorMenu implements SpectatorMenuCommandGroup, SpectatorMenuCommand {
   private static final Text field_26618 = new TranslatableText("spectatorMenu.team_teleport");
   private static final Text field_26619 = new TranslatableText("spectatorMenu.team_teleport.prompt");
   private final List<SpectatorMenuCommand> commands = Lists.newArrayList();

   public TeamTeleportSpectatorMenu() {
      MinecraftClient _snowman = MinecraftClient.getInstance();

      for (Team _snowmanx : _snowman.world.getScoreboard().getTeams()) {
         this.commands.add(new TeamTeleportSpectatorMenu.TeleportToSpecificTeamCommand(_snowmanx));
      }
   }

   @Override
   public List<SpectatorMenuCommand> getCommands() {
      return this.commands;
   }

   @Override
   public Text getPrompt() {
      return field_26619;
   }

   @Override
   public void use(SpectatorMenu menu) {
      menu.selectElement(this);
   }

   @Override
   public Text getName() {
      return field_26618;
   }

   @Override
   public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
      MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
      DrawableHelper.drawTexture(_snowman, 0, 0, 16.0F, 0.0F, 16, 16, 256, 256);
   }

   @Override
   public boolean isEnabled() {
      for (SpectatorMenuCommand _snowman : this.commands) {
         if (_snowman.isEnabled()) {
            return true;
         }
      }

      return false;
   }

   class TeleportToSpecificTeamCommand implements SpectatorMenuCommand {
      private final Team team;
      private final Identifier skinId;
      private final List<PlayerListEntry> scoreboardEntries;

      public TeleportToSpecificTeamCommand(Team var2) {
         this.team = _snowman;
         this.scoreboardEntries = Lists.newArrayList();

         for (String _snowman : _snowman.getPlayerList()) {
            PlayerListEntry _snowmanx = MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry(_snowman);
            if (_snowmanx != null) {
               this.scoreboardEntries.add(_snowmanx);
            }
         }

         if (this.scoreboardEntries.isEmpty()) {
            this.skinId = DefaultSkinHelper.getTexture();
         } else {
            String _snowmanx = this.scoreboardEntries.get(new Random().nextInt(this.scoreboardEntries.size())).getProfile().getName();
            this.skinId = AbstractClientPlayerEntity.getSkinId(_snowmanx);
            AbstractClientPlayerEntity.loadSkin(this.skinId, _snowmanx);
         }
      }

      @Override
      public void use(SpectatorMenu menu) {
         menu.selectElement(new TeleportSpectatorMenu(this.scoreboardEntries));
      }

      @Override
      public Text getName() {
         return this.team.getDisplayName();
      }

      @Override
      public void renderIcon(MatrixStack _snowman, float _snowman, int _snowman) {
         Integer _snowmanxxx = this.team.getColor().getColorValue();
         if (_snowmanxxx != null) {
            float _snowmanxxxx = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxxx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
            DrawableHelper.fill(_snowman, 1, 1, 15, 15, MathHelper.packRgb(_snowmanxxxx * _snowman, _snowmanxxxxx * _snowman, _snowmanxxxxxx * _snowman) | _snowman << 24);
         }

         MinecraftClient.getInstance().getTextureManager().bindTexture(this.skinId);
         RenderSystem.color4f(_snowman, _snowman, _snowman, (float)_snowman / 255.0F);
         DrawableHelper.drawTexture(_snowman, 2, 2, 12, 12, 8.0F, 8.0F, 8, 8, 64, 64);
         DrawableHelper.drawTexture(_snowman, 2, 2, 12, 12, 40.0F, 8.0F, 8, 8, 64, 64);
      }

      @Override
      public boolean isEnabled() {
         return !this.scoreboardEntries.isEmpty();
      }
   }
}
