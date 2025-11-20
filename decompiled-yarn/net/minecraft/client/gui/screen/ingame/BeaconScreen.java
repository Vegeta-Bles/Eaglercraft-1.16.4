package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateBeaconC2SPacket;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class BeaconScreen extends HandledScreen<BeaconScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/beacon.png");
   private static final Text field_26560 = new TranslatableText("block.minecraft.beacon.primary");
   private static final Text field_26561 = new TranslatableText("block.minecraft.beacon.secondary");
   private BeaconScreen.DoneButtonWidget doneButton;
   private boolean consumeGem;
   private StatusEffect primaryEffect;
   private StatusEffect secondaryEffect;

   public BeaconScreen(BeaconScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      this.backgroundWidth = 230;
      this.backgroundHeight = 219;
      handler.addListener(new ScreenHandlerListener() {
         @Override
         public void onHandlerRegistered(ScreenHandler handler, DefaultedList<ItemStack> stacks) {
         }

         @Override
         public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
         }

         @Override
         public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
            BeaconScreen.this.primaryEffect = handler.getPrimaryEffect();
            BeaconScreen.this.secondaryEffect = handler.getSecondaryEffect();
            BeaconScreen.this.consumeGem = true;
         }
      });
   }

   @Override
   protected void init() {
      super.init();
      this.doneButton = this.addButton(new BeaconScreen.DoneButtonWidget(this.x + 164, this.y + 107));
      this.addButton(new BeaconScreen.CancelButtonWidget(this.x + 190, this.y + 107));
      this.consumeGem = true;
      this.doneButton.active = false;
   }

   @Override
   public void tick() {
      super.tick();
      int _snowman = this.handler.getProperties();
      if (this.consumeGem && _snowman >= 0) {
         this.consumeGem = false;

         for (int _snowmanx = 0; _snowmanx <= 2; _snowmanx++) {
            int _snowmanxx = BeaconBlockEntity.EFFECTS_BY_LEVEL[_snowmanx].length;
            int _snowmanxxx = _snowmanxx * 22 + (_snowmanxx - 1) * 2;

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
               StatusEffect _snowmanxxxxx = BeaconBlockEntity.EFFECTS_BY_LEVEL[_snowmanx][_snowmanxxxx];
               BeaconScreen.EffectButtonWidget _snowmanxxxxxx = new BeaconScreen.EffectButtonWidget(
                  this.x + 76 + _snowmanxxxx * 24 - _snowmanxxx / 2, this.y + 22 + _snowmanx * 25, _snowmanxxxxx, true
               );
               this.addButton(_snowmanxxxxxx);
               if (_snowmanx >= _snowman) {
                  _snowmanxxxxxx.active = false;
               } else if (_snowmanxxxxx == this.primaryEffect) {
                  _snowmanxxxxxx.setDisabled(true);
               }
            }
         }

         int _snowmanx = 3;
         int _snowmanxx = BeaconBlockEntity.EFFECTS_BY_LEVEL[3].length + 1;
         int _snowmanxxx = _snowmanxx * 22 + (_snowmanxx - 1) * 2;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx - 1; _snowmanxxxxx++) {
            StatusEffect _snowmanxxxxxx = BeaconBlockEntity.EFFECTS_BY_LEVEL[3][_snowmanxxxxx];
            BeaconScreen.EffectButtonWidget _snowmanxxxxxxx = new BeaconScreen.EffectButtonWidget(this.x + 167 + _snowmanxxxxx * 24 - _snowmanxxx / 2, this.y + 47, _snowmanxxxxxx, false);
            this.addButton(_snowmanxxxxxxx);
            if (3 >= _snowman) {
               _snowmanxxxxxxx.active = false;
            } else if (_snowmanxxxxxx == this.secondaryEffect) {
               _snowmanxxxxxxx.setDisabled(true);
            }
         }

         if (this.primaryEffect != null) {
            BeaconScreen.EffectButtonWidget _snowmanxxxxxx = new BeaconScreen.EffectButtonWidget(
               this.x + 167 + (_snowmanxx - 1) * 24 - _snowmanxxx / 2, this.y + 47, this.primaryEffect, false
            );
            this.addButton(_snowmanxxxxxx);
            if (3 >= _snowman) {
               _snowmanxxxxxx.active = false;
            } else if (this.primaryEffect == this.secondaryEffect) {
               _snowmanxxxxxx.setDisabled(true);
            }
         }
      }

      this.doneButton.active = this.handler.hasPayment() && this.primaryEffect != null;
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      drawCenteredText(matrices, this.textRenderer, field_26560, 62, 10, 14737632);
      drawCenteredText(matrices, this.textRenderer, field_26561, 169, 10, 14737632);

      for (AbstractButtonWidget _snowman : this.buttons) {
         if (_snowman.isHovered()) {
            _snowman.renderToolTip(matrices, mouseX - this.x, mouseY - this.y);
            break;
         }
      }
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      this.drawTexture(matrices, _snowman, _snowmanx, 0, 0, this.backgroundWidth, this.backgroundHeight);
      this.itemRenderer.zOffset = 100.0F;
      this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.NETHERITE_INGOT), _snowman + 20, _snowmanx + 109);
      this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.EMERALD), _snowman + 41, _snowmanx + 109);
      this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.DIAMOND), _snowman + 41 + 22, _snowmanx + 109);
      this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.GOLD_INGOT), _snowman + 42 + 44, _snowmanx + 109);
      this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.IRON_INGOT), _snowman + 42 + 66, _snowmanx + 109);
      this.itemRenderer.zOffset = 0.0F;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   abstract static class BaseButtonWidget extends AbstractPressableButtonWidget {
      private boolean disabled;

      protected BaseButtonWidget(int x, int y) {
         super(x, y, 22, 22, LiteralText.EMPTY);
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         MinecraftClient.getInstance().getTextureManager().bindTexture(BeaconScreen.TEXTURE);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowman = 219;
         int _snowmanx = 0;
         if (!this.active) {
            _snowmanx += this.width * 2;
         } else if (this.disabled) {
            _snowmanx += this.width * 1;
         } else if (this.isHovered()) {
            _snowmanx += this.width * 3;
         }

         this.drawTexture(matrices, this.x, this.y, _snowmanx, 219, this.width, this.height);
         this.renderExtra(matrices);
      }

      protected abstract void renderExtra(MatrixStack var1);

      public boolean isDisabled() {
         return this.disabled;
      }

      public void setDisabled(boolean disabled) {
         this.disabled = disabled;
      }
   }

   class CancelButtonWidget extends BeaconScreen.IconButtonWidget {
      public CancelButtonWidget(int x, int y) {
         super(x, y, 112, 220);
      }

      @Override
      public void onPress() {
         BeaconScreen.this.client
            .player
            .networkHandler
            .sendPacket(new CloseHandledScreenC2SPacket(BeaconScreen.this.client.player.currentScreenHandler.syncId));
         BeaconScreen.this.client.openScreen(null);
      }

      @Override
      public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
         BeaconScreen.this.renderTooltip(matrices, ScreenTexts.CANCEL, mouseX, mouseY);
      }
   }

   class DoneButtonWidget extends BeaconScreen.IconButtonWidget {
      public DoneButtonWidget(int x, int y) {
         super(x, y, 90, 220);
      }

      @Override
      public void onPress() {
         BeaconScreen.this.client
            .getNetworkHandler()
            .sendPacket(
               new UpdateBeaconC2SPacket(StatusEffect.getRawId(BeaconScreen.this.primaryEffect), StatusEffect.getRawId(BeaconScreen.this.secondaryEffect))
            );
         BeaconScreen.this.client
            .player
            .networkHandler
            .sendPacket(new CloseHandledScreenC2SPacket(BeaconScreen.this.client.player.currentScreenHandler.syncId));
         BeaconScreen.this.client.openScreen(null);
      }

      @Override
      public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
         BeaconScreen.this.renderTooltip(matrices, ScreenTexts.DONE, mouseX, mouseY);
      }
   }

   class EffectButtonWidget extends BeaconScreen.BaseButtonWidget {
      private final StatusEffect effect;
      private final Sprite sprite;
      private final boolean primary;
      private final Text field_26562;

      public EffectButtonWidget(int x, int y, StatusEffect statusEffect, boolean primary) {
         super(x, y);
         this.effect = statusEffect;
         this.sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(statusEffect);
         this.primary = primary;
         this.field_26562 = this.method_30902(statusEffect, primary);
      }

      private Text method_30902(StatusEffect _snowman, boolean _snowman) {
         MutableText _snowmanxx = new TranslatableText(_snowman.getTranslationKey());
         if (!_snowman && _snowman != StatusEffects.REGENERATION) {
            _snowmanxx.append(" II");
         }

         return _snowmanxx;
      }

      @Override
      public void onPress() {
         if (!this.isDisabled()) {
            if (this.primary) {
               BeaconScreen.this.primaryEffect = this.effect;
            } else {
               BeaconScreen.this.secondaryEffect = this.effect;
            }

            BeaconScreen.this.buttons.clear();
            BeaconScreen.this.children.clear();
            BeaconScreen.this.init();
            BeaconScreen.this.tick();
         }
      }

      @Override
      public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
         BeaconScreen.this.renderTooltip(matrices, this.field_26562, mouseX, mouseY);
      }

      @Override
      protected void renderExtra(MatrixStack _snowman) {
         MinecraftClient.getInstance().getTextureManager().bindTexture(this.sprite.getAtlas().getId());
         drawSprite(_snowman, this.x + 2, this.y + 2, this.getZOffset(), 18, 18, this.sprite);
      }
   }

   abstract static class IconButtonWidget extends BeaconScreen.BaseButtonWidget {
      private final int u;
      private final int v;

      protected IconButtonWidget(int x, int y, int u, int v) {
         super(x, y);
         this.u = u;
         this.v = v;
      }

      @Override
      protected void renderExtra(MatrixStack _snowman) {
         this.drawTexture(_snowman, this.x + 2, this.y + 2, this.u, this.v, 18, 18);
      }
   }
}
