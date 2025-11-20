package net.minecraft.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCloseCallback;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class SpectatorHud extends DrawableHelper implements SpectatorMenuCloseCallback {
   private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
   public static final Identifier SPECTATOR_TEXTURE = new Identifier("textures/gui/spectator_widgets.png");
   private final MinecraftClient client;
   private long lastInteractionTime;
   private SpectatorMenu spectatorMenu;

   public SpectatorHud(MinecraftClient client) {
      this.client = client;
   }

   public void selectSlot(int slot) {
      this.lastInteractionTime = Util.getMeasuringTimeMs();
      if (this.spectatorMenu != null) {
         this.spectatorMenu.useCommand(slot);
      } else {
         this.spectatorMenu = new SpectatorMenu(this);
      }
   }

   private float getSpectatorMenuHeight() {
      long _snowman = this.lastInteractionTime - Util.getMeasuringTimeMs() + 5000L;
      return MathHelper.clamp((float)_snowman / 2000.0F, 0.0F, 1.0F);
   }

   public void render(MatrixStack _snowman, float _snowman) {
      if (this.spectatorMenu != null) {
         float _snowmanxx = this.getSpectatorMenuHeight();
         if (_snowmanxx <= 0.0F) {
            this.spectatorMenu.close();
         } else {
            int _snowmanxxx = this.client.getWindow().getScaledWidth() / 2;
            int _snowmanxxxx = this.getZOffset();
            this.setZOffset(-90);
            int _snowmanxxxxx = MathHelper.floor((float)this.client.getWindow().getScaledHeight() - 22.0F * _snowmanxx);
            SpectatorMenuState _snowmanxxxxxx = this.spectatorMenu.getCurrentState();
            this.renderSpectatorMenu(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
            this.setZOffset(_snowmanxxxx);
         }
      }
   }

   protected void renderSpectatorMenu(MatrixStack _snowman, float _snowman, int _snowman, int _snowman, SpectatorMenuState _snowman) {
      RenderSystem.enableRescaleNormal();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowman);
      this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
      this.drawTexture(_snowman, _snowman - 91, _snowman, 0, 0, 182, 22);
      if (_snowman.getSelectedSlot() >= 0) {
         this.drawTexture(_snowman, _snowman - 91 - 1 + _snowman.getSelectedSlot() * 20, _snowman - 1, 0, 22, 24, 22);
      }

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 9; _snowmanxxxxx++) {
         this.renderSpectatorCommand(_snowman, _snowmanxxxxx, this.client.getWindow().getScaledWidth() / 2 - 90 + _snowmanxxxxx * 20 + 2, (float)(_snowman + 3), _snowman, _snowman.getCommand(_snowmanxxxxx));
      }

      RenderSystem.disableRescaleNormal();
      RenderSystem.disableBlend();
   }

   private void renderSpectatorCommand(MatrixStack _snowman, int _snowman, int _snowman, float _snowman, float _snowman, SpectatorMenuCommand _snowman) {
      this.client.getTextureManager().bindTexture(SPECTATOR_TEXTURE);
      if (_snowman != SpectatorMenu.BLANK_COMMAND) {
         int _snowmanxxxxxx = (int)(_snowman * 255.0F);
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)_snowman, _snowman, 0.0F);
         float _snowmanxxxxxxx = _snowman.isEnabled() ? 1.0F : 0.25F;
         RenderSystem.color4f(_snowmanxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxx, _snowman);
         _snowman.renderIcon(_snowman, _snowmanxxxxxxx, _snowmanxxxxxx);
         RenderSystem.popMatrix();
         if (_snowmanxxxxxx > 3 && _snowman.isEnabled()) {
            Text _snowmanxxxxxxxx = this.client.options.keysHotbar[_snowman].getBoundKeyLocalizedText();
            this.client
               .textRenderer
               .drawWithShadow(_snowman, _snowmanxxxxxxxx, (float)(_snowman + 19 - 2 - this.client.textRenderer.getWidth(_snowmanxxxxxxxx)), _snowman + 6.0F + 3.0F, 16777215 + (_snowmanxxxxxx << 24));
         }
      }
   }

   public void render(MatrixStack _snowman) {
      int _snowmanx = (int)(this.getSpectatorMenuHeight() * 255.0F);
      if (_snowmanx > 3 && this.spectatorMenu != null) {
         SpectatorMenuCommand _snowmanxx = this.spectatorMenu.getSelectedCommand();
         Text _snowmanxxx = _snowmanxx == SpectatorMenu.BLANK_COMMAND ? this.spectatorMenu.getCurrentGroup().getPrompt() : _snowmanxx.getName();
         if (_snowmanxxx != null) {
            int _snowmanxxxx = (this.client.getWindow().getScaledWidth() - this.client.textRenderer.getWidth(_snowmanxxx)) / 2;
            int _snowmanxxxxx = this.client.getWindow().getScaledHeight() - 35;
            RenderSystem.pushMatrix();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.client.textRenderer.drawWithShadow(_snowman, _snowmanxxx, (float)_snowmanxxxx, (float)_snowmanxxxxx, 16777215 + (_snowmanx << 24));
            RenderSystem.disableBlend();
            RenderSystem.popMatrix();
         }
      }
   }

   @Override
   public void close(SpectatorMenu menu) {
      this.spectatorMenu = null;
      this.lastInteractionTime = 0L;
   }

   public boolean isOpen() {
      return this.spectatorMenu != null;
   }

   public void cycleSlot(double offset) {
      int _snowman = this.spectatorMenu.getSelectedSlot() + (int)offset;

      while (_snowman >= 0 && _snowman <= 8 && (this.spectatorMenu.getCommand(_snowman) == SpectatorMenu.BLANK_COMMAND || !this.spectatorMenu.getCommand(_snowman).isEnabled())) {
         _snowman = (int)((double)_snowman + offset);
      }

      if (_snowman >= 0 && _snowman <= 8) {
         this.spectatorMenu.useCommand(_snowman);
         this.lastInteractionTime = Util.getMeasuringTimeMs();
      }
   }

   public void useSelectedCommand() {
      this.lastInteractionTime = Util.getMeasuringTimeMs();
      if (this.isOpen()) {
         int _snowman = this.spectatorMenu.getSelectedSlot();
         if (_snowman != -1) {
            this.spectatorMenu.useCommand(_snowman);
         }
      } else {
         this.spectatorMenu = new SpectatorMenu(this);
      }
   }
}
