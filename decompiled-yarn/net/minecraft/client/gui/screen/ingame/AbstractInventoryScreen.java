package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public abstract class AbstractInventoryScreen<T extends ScreenHandler> extends HandledScreen<T> {
   protected boolean drawStatusEffects;

   public AbstractInventoryScreen(T _snowman, PlayerInventory _snowman, Text _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected void init() {
      super.init();
      this.applyStatusEffectOffset();
   }

   protected void applyStatusEffectOffset() {
      if (this.client.player.getStatusEffects().isEmpty()) {
         this.x = (this.width - this.backgroundWidth) / 2;
         this.drawStatusEffects = false;
      } else {
         this.x = 160 + (this.width - this.backgroundWidth - 200) / 2;
         this.drawStatusEffects = true;
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      super.render(matrices, mouseX, mouseY, delta);
      if (this.drawStatusEffects) {
         this.drawStatusEffects(matrices);
      }
   }

   private void drawStatusEffects(MatrixStack _snowman) {
      int _snowmanx = this.x - 124;
      Collection<StatusEffectInstance> _snowmanxx = this.client.player.getStatusEffects();
      if (!_snowmanxx.isEmpty()) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowmanxxx = 33;
         if (_snowmanxx.size() > 5) {
            _snowmanxxx = 132 / (_snowmanxx.size() - 1);
         }

         Iterable<StatusEffectInstance> _snowmanxxxx = Ordering.natural().sortedCopy(_snowmanxx);
         this.drawStatusEffectBackgrounds(_snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
         this.drawStatusEffectSprites(_snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
         this.drawStatusEffectDescriptions(_snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
      }
   }

   private void drawStatusEffectBackgrounds(MatrixStack _snowman, int _snowman, int _snowman, Iterable<StatusEffectInstance> _snowman) {
      this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
      int _snowmanxxxx = this.y;

      for (StatusEffectInstance _snowmanxxxxx : _snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexture(_snowman, _snowman, _snowmanxxxx, 0, 166, 140, 32);
         _snowmanxxxx += _snowman;
      }
   }

   private void drawStatusEffectSprites(MatrixStack _snowman, int _snowman, int _snowman, Iterable<StatusEffectInstance> _snowman) {
      StatusEffectSpriteManager _snowmanxxxx = this.client.getStatusEffectSpriteManager();
      int _snowmanxxxxx = this.y;

      for (StatusEffectInstance _snowmanxxxxxx : _snowman) {
         StatusEffect _snowmanxxxxxxx = _snowmanxxxxxx.getEffectType();
         Sprite _snowmanxxxxxxxx = _snowmanxxxx.getSprite(_snowmanxxxxxxx);
         this.client.getTextureManager().bindTexture(_snowmanxxxxxxxx.getAtlas().getId());
         drawSprite(_snowman, _snowman + 6, _snowmanxxxxx + 7, this.getZOffset(), 18, 18, _snowmanxxxxxxxx);
         _snowmanxxxxx += _snowman;
      }
   }

   private void drawStatusEffectDescriptions(MatrixStack _snowman, int _snowman, int _snowman, Iterable<StatusEffectInstance> _snowman) {
      int _snowmanxxxx = this.y;

      for (StatusEffectInstance _snowmanxxxxx : _snowman) {
         String _snowmanxxxxxx = I18n.translate(_snowmanxxxxx.getEffectType().getTranslationKey());
         if (_snowmanxxxxx.getAmplifier() >= 1 && _snowmanxxxxx.getAmplifier() <= 9) {
            _snowmanxxxxxx = _snowmanxxxxxx + ' ' + I18n.translate("enchantment.level." + (_snowmanxxxxx.getAmplifier() + 1));
         }

         this.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxx, (float)(_snowman + 10 + 18), (float)(_snowmanxxxx + 6), 16777215);
         String _snowmanxxxxxxx = StatusEffectUtil.durationToString(_snowmanxxxxx, 1.0F);
         this.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxx, (float)(_snowman + 10 + 18), (float)(_snowmanxxxx + 6 + 10), 8355711);
         _snowmanxxxx += _snowman;
      }
   }
}
