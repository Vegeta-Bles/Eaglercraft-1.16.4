package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;

public class MerchantScreen extends HandledScreen<MerchantScreenHandler> {
   private static final Identifier TEXTURE = new Identifier("textures/gui/container/villager2.png");
   private static final Text TRADES_TEXT = new TranslatableText("merchant.trades");
   private static final Text SEPARATOR = new LiteralText(" - ");
   private static final Text DEPRECATED_TEXT = new TranslatableText("merchant.deprecated");
   private int selectedIndex;
   private final MerchantScreen.WidgetButtonPage[] offers = new MerchantScreen.WidgetButtonPage[7];
   private int indexStartOffset;
   private boolean scrolling;

   public MerchantScreen(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
      super(handler, inventory, title);
      this.backgroundWidth = 276;
      this.playerInventoryTitleX = 107;
   }

   private void syncRecipeIndex() {
      this.handler.setRecipeIndex(this.selectedIndex);
      this.handler.switchTo(this.selectedIndex);
      this.client.getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(this.selectedIndex));
   }

   @Override
   protected void init() {
      super.init();
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      int _snowmanxx = _snowmanx + 16 + 2;

      for (int _snowmanxxx = 0; _snowmanxxx < 7; _snowmanxxx++) {
         this.offers[_snowmanxxx] = this.addButton(new MerchantScreen.WidgetButtonPage(_snowman + 5, _snowmanxx, _snowmanxxx, button -> {
            if (button instanceof MerchantScreen.WidgetButtonPage) {
               this.selectedIndex = ((MerchantScreen.WidgetButtonPage)button).getIndex() + this.indexStartOffset;
               this.syncRecipeIndex();
            }
         }));
         _snowmanxx += 20;
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      int _snowman = this.handler.getLevelProgress();
      if (_snowman > 0 && _snowman <= 5 && this.handler.isLeveled()) {
         Text _snowmanx = this.title.shallowCopy().append(SEPARATOR).append(new TranslatableText("merchant.level." + _snowman));
         int _snowmanxx = this.textRenderer.getWidth(_snowmanx);
         int _snowmanxxx = 49 + this.backgroundWidth / 2 - _snowmanxx / 2;
         this.textRenderer.draw(matrices, _snowmanx, (float)_snowmanxxx, 6.0F, 4210752);
      } else {
         this.textRenderer.draw(matrices, this.title, (float)(49 + this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2), 6.0F, 4210752);
      }

      this.textRenderer.draw(matrices, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
      int _snowmanx = this.textRenderer.getWidth(TRADES_TEXT);
      this.textRenderer.draw(matrices, TRADES_TEXT, (float)(5 - _snowmanx / 2 + 48), 6.0F, 4210752);
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      drawTexture(matrices, _snowman, _snowmanx, this.getZOffset(), 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 512);
      TradeOfferList _snowmanxx = this.handler.getRecipes();
      if (!_snowmanxx.isEmpty()) {
         int _snowmanxxx = this.selectedIndex;
         if (_snowmanxxx < 0 || _snowmanxxx >= _snowmanxx.size()) {
            return;
         }

         TradeOffer _snowmanxxxx = _snowmanxx.get(_snowmanxxx);
         if (_snowmanxxxx.isDisabled()) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexture(matrices, this.x + 83 + 99, this.y + 35, this.getZOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }
   }

   private void drawLevelInfo(MatrixStack matrices, int x, int y, TradeOffer tradeOffer) {
      this.client.getTextureManager().bindTexture(TEXTURE);
      int _snowman = this.handler.getLevelProgress();
      int _snowmanx = this.handler.getExperience();
      if (_snowman < 5) {
         drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0F, 186.0F, 102, 5, 256, 512);
         int _snowmanxx = VillagerData.getLowerLevelExperience(_snowman);
         if (_snowmanx >= _snowmanxx && VillagerData.canLevelUp(_snowman)) {
            int _snowmanxxx = 100;
            float _snowmanxxxx = 100.0F / (float)(VillagerData.getUpperLevelExperience(_snowman) - _snowmanxx);
            int _snowmanxxxxx = Math.min(MathHelper.floor(_snowmanxxxx * (float)(_snowmanx - _snowmanxx)), 100);
            drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0F, 191.0F, _snowmanxxxxx + 1, 5, 256, 512);
            int _snowmanxxxxxx = this.handler.getMerchantRewardedExperience();
            if (_snowmanxxxxxx > 0) {
               int _snowmanxxxxxxx = Math.min(MathHelper.floor((float)_snowmanxxxxxx * _snowmanxxxx), 100 - _snowmanxxxxx);
               drawTexture(matrices, x + 136 + _snowmanxxxxx + 1, y + 16 + 1, this.getZOffset(), 2.0F, 182.0F, _snowmanxxxxxxx, 3, 256, 512);
            }
         }
      }
   }

   private void renderScrollbar(MatrixStack matrices, int x, int y, TradeOfferList tradeOffers) {
      int _snowman = tradeOffers.size() + 1 - 7;
      if (_snowman > 1) {
         int _snowmanx = 139 - (27 + (_snowman - 1) * 139 / _snowman);
         int _snowmanxx = 1 + _snowmanx / _snowman + 139 / _snowman;
         int _snowmanxxx = 113;
         int _snowmanxxxx = Math.min(113, this.indexStartOffset * _snowmanxx);
         if (this.indexStartOffset == _snowman - 1) {
            _snowmanxxxx = 113;
         }

         drawTexture(matrices, x + 94, y + 18 + _snowmanxxxx, this.getZOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         drawTexture(matrices, x + 94, y + 18, this.getZOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      TradeOfferList _snowman = this.handler.getRecipes();
      if (!_snowman.isEmpty()) {
         int _snowmanx = (this.width - this.backgroundWidth) / 2;
         int _snowmanxx = (this.height - this.backgroundHeight) / 2;
         int _snowmanxxx = _snowmanxx + 16 + 1;
         int _snowmanxxxx = _snowmanx + 5 + 5;
         RenderSystem.pushMatrix();
         RenderSystem.enableRescaleNormal();
         this.client.getTextureManager().bindTexture(TEXTURE);
         this.renderScrollbar(matrices, _snowmanx, _snowmanxx, _snowman);
         int _snowmanxxxxx = 0;

         for (TradeOffer _snowmanxxxxxx : _snowman) {
            if (!this.canScroll(_snowman.size()) || _snowmanxxxxx >= this.indexStartOffset && _snowmanxxxxx < 7 + this.indexStartOffset) {
               ItemStack _snowmanxxxxxxx = _snowmanxxxxxx.getOriginalFirstBuyItem();
               ItemStack _snowmanxxxxxxxx = _snowmanxxxxxx.getAdjustedFirstBuyItem();
               ItemStack _snowmanxxxxxxxxx = _snowmanxxxxxx.getSecondBuyItem();
               ItemStack _snowmanxxxxxxxxxx = _snowmanxxxxxx.getMutableSellItem();
               this.itemRenderer.zOffset = 100.0F;
               int _snowmanxxxxxxxxxxx = _snowmanxxx + 2;
               this.renderFirstBuyItem(matrices, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxxxxxxx);
               if (!_snowmanxxxxxxxxx.isEmpty()) {
                  this.itemRenderer.renderInGui(_snowmanxxxxxxxxx, _snowmanx + 5 + 35, _snowmanxxxxxxxxxxx);
                  this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowmanxxxxxxxxx, _snowmanx + 5 + 35, _snowmanxxxxxxxxxxx);
               }

               this.renderArrow(matrices, _snowmanxxxxxx, _snowmanx, _snowmanxxxxxxxxxxx);
               this.itemRenderer.renderInGui(_snowmanxxxxxxxxxx, _snowmanx + 5 + 68, _snowmanxxxxxxxxxxx);
               this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowmanxxxxxxxxxx, _snowmanx + 5 + 68, _snowmanxxxxxxxxxxx);
               this.itemRenderer.zOffset = 0.0F;
               _snowmanxxx += 20;
               _snowmanxxxxx++;
            } else {
               _snowmanxxxxx++;
            }
         }

         int _snowmanxxxxxxx = this.selectedIndex;
         TradeOffer _snowmanxxxxxxxx = _snowman.get(_snowmanxxxxxxx);
         if (this.handler.isLeveled()) {
            this.drawLevelInfo(matrices, _snowmanx, _snowmanxx, _snowmanxxxxxxxx);
         }

         if (_snowmanxxxxxxxx.isDisabled() && this.isPointWithinBounds(186, 35, 22, 21, (double)mouseX, (double)mouseY) && this.handler.canRefreshTrades()) {
            this.renderTooltip(matrices, DEPRECATED_TEXT, mouseX, mouseY);
         }

         for (MerchantScreen.WidgetButtonPage _snowmanxxxxxxxxx : this.offers) {
            if (_snowmanxxxxxxxxx.isHovered()) {
               _snowmanxxxxxxxxx.renderToolTip(matrices, mouseX, mouseY);
            }

            _snowmanxxxxxxxxx.visible = _snowmanxxxxxxxxx.index < this.handler.getRecipes().size();
         }

         RenderSystem.popMatrix();
         RenderSystem.enableDepthTest();
      }

      this.drawMouseoverTooltip(matrices, mouseX, mouseY);
   }

   private void renderArrow(MatrixStack matrices, TradeOffer tradeOffer, int x, int y) {
      RenderSystem.enableBlend();
      this.client.getTextureManager().bindTexture(TEXTURE);
      if (tradeOffer.isDisabled()) {
         drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 25.0F, 171.0F, 10, 9, 256, 512);
      } else {
         drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 15.0F, 171.0F, 10, 9, 256, 512);
      }
   }

   private void renderFirstBuyItem(MatrixStack matrices, ItemStack adjustedFirstBuyItem, ItemStack originalFirstBuyItem, int x, int y) {
      this.itemRenderer.renderInGui(adjustedFirstBuyItem, x, y);
      if (originalFirstBuyItem.getCount() == adjustedFirstBuyItem.getCount()) {
         this.itemRenderer.renderGuiItemOverlay(this.textRenderer, adjustedFirstBuyItem, x, y);
      } else {
         this.itemRenderer.renderGuiItemOverlay(this.textRenderer, originalFirstBuyItem, x, y, originalFirstBuyItem.getCount() == 1 ? "1" : null);
         this.itemRenderer.renderGuiItemOverlay(this.textRenderer, adjustedFirstBuyItem, x + 14, y, adjustedFirstBuyItem.getCount() == 1 ? "1" : null);
         this.client.getTextureManager().bindTexture(TEXTURE);
         this.setZOffset(this.getZOffset() + 300);
         drawTexture(matrices, x + 7, y + 12, this.getZOffset(), 0.0F, 176.0F, 9, 2, 256, 512);
         this.setZOffset(this.getZOffset() - 300);
      }
   }

   private boolean canScroll(int listSize) {
      return listSize > 7;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      int _snowman = this.handler.getRecipes().size();
      if (this.canScroll(_snowman)) {
         int _snowmanx = _snowman - 7;
         this.indexStartOffset = (int)((double)this.indexStartOffset - amount);
         this.indexStartOffset = MathHelper.clamp(this.indexStartOffset, 0, _snowmanx);
      }

      return true;
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      int _snowman = this.handler.getRecipes().size();
      if (this.scrolling) {
         int _snowmanx = this.y + 18;
         int _snowmanxx = _snowmanx + 139;
         int _snowmanxxx = _snowman - 7;
         float _snowmanxxxx = ((float)mouseY - (float)_snowmanx - 13.5F) / ((float)(_snowmanxx - _snowmanx) - 27.0F);
         _snowmanxxxx = _snowmanxxxx * (float)_snowmanxxx + 0.5F;
         this.indexStartOffset = MathHelper.clamp((int)_snowmanxxxx, 0, _snowmanxxx);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.scrolling = false;
      int _snowman = (this.width - this.backgroundWidth) / 2;
      int _snowmanx = (this.height - this.backgroundHeight) / 2;
      if (this.canScroll(this.handler.getRecipes().size())
         && mouseX > (double)(_snowman + 94)
         && mouseX < (double)(_snowman + 94 + 6)
         && mouseY > (double)(_snowmanx + 18)
         && mouseY <= (double)(_snowmanx + 18 + 139 + 1)) {
         this.scrolling = true;
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   class WidgetButtonPage extends ButtonWidget {
      final int index;

      public WidgetButtonPage(int x, int y, int index, ButtonWidget.PressAction onPress) {
         super(x, y, 89, 20, LiteralText.EMPTY, onPress);
         this.index = index;
         this.visible = false;
      }

      public int getIndex() {
         return this.index;
      }

      @Override
      public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
         if (this.hovered && MerchantScreen.this.handler.getRecipes().size() > this.index + MerchantScreen.this.indexStartOffset) {
            if (mouseX < this.x + 20) {
               ItemStack _snowman = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getAdjustedFirstBuyItem();
               MerchantScreen.this.renderTooltip(matrices, _snowman, mouseX, mouseY);
            } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
               ItemStack _snowman = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getSecondBuyItem();
               if (!_snowman.isEmpty()) {
                  MerchantScreen.this.renderTooltip(matrices, _snowman, mouseX, mouseY);
               }
            } else if (mouseX > this.x + 65) {
               ItemStack _snowman = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getMutableSellItem();
               MerchantScreen.this.renderTooltip(matrices, _snowman, mouseX, mouseY);
            }
         }
      }
   }
}
