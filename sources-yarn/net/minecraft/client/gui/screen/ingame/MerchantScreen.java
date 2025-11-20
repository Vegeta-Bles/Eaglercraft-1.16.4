package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      int i = (this.width - this.backgroundWidth) / 2;
      int j = (this.height - this.backgroundHeight) / 2;
      int k = j + 16 + 2;

      for (int l = 0; l < 7; l++) {
         this.offers[l] = this.addButton(new MerchantScreen.WidgetButtonPage(i + 5, k, l, button -> {
            if (button instanceof MerchantScreen.WidgetButtonPage) {
               this.selectedIndex = ((MerchantScreen.WidgetButtonPage)button).getIndex() + this.indexStartOffset;
               this.syncRecipeIndex();
            }
         }));
         k += 20;
      }
   }

   @Override
   protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
      int k = this.handler.getLevelProgress();
      if (k > 0 && k <= 5 && this.handler.isLeveled()) {
         Text lv = this.title.shallowCopy().append(SEPARATOR).append(new TranslatableText("merchant.level." + k));
         int l = this.textRenderer.getWidth(lv);
         int m = 49 + this.backgroundWidth / 2 - l / 2;
         this.textRenderer.draw(matrices, lv, (float)m, 6.0F, 4210752);
      } else {
         this.textRenderer.draw(matrices, this.title, (float)(49 + this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2), 6.0F, 4210752);
      }

      this.textRenderer.draw(matrices, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 4210752);
      int n = this.textRenderer.getWidth(TRADES_TEXT);
      this.textRenderer.draw(matrices, TRADES_TEXT, (float)(5 - n / 2 + 48), 6.0F, 4210752);
   }

   @Override
   protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(TEXTURE);
      int k = (this.width - this.backgroundWidth) / 2;
      int l = (this.height - this.backgroundHeight) / 2;
      drawTexture(matrices, k, l, this.getZOffset(), 0.0F, 0.0F, this.backgroundWidth, this.backgroundHeight, 256, 512);
      TradeOfferList lv = this.handler.getRecipes();
      if (!lv.isEmpty()) {
         int m = this.selectedIndex;
         if (m < 0 || m >= lv.size()) {
            return;
         }

         TradeOffer lv2 = lv.get(m);
         if (lv2.isDisabled()) {
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawTexture(matrices, this.x + 83 + 99, this.y + 35, this.getZOffset(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }
   }

   private void drawLevelInfo(MatrixStack matrices, int x, int y, TradeOffer tradeOffer) {
      this.client.getTextureManager().bindTexture(TEXTURE);
      int k = this.handler.getLevelProgress();
      int l = this.handler.getExperience();
      if (k < 5) {
         drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0F, 186.0F, 102, 5, 256, 512);
         int m = VillagerData.getLowerLevelExperience(k);
         if (l >= m && VillagerData.canLevelUp(k)) {
            int n = 100;
            float f = 100.0F / (float)(VillagerData.getUpperLevelExperience(k) - m);
            int o = Math.min(MathHelper.floor(f * (float)(l - m)), 100);
            drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0F, 191.0F, o + 1, 5, 256, 512);
            int p = this.handler.getMerchantRewardedExperience();
            if (p > 0) {
               int q = Math.min(MathHelper.floor((float)p * f), 100 - o);
               drawTexture(matrices, x + 136 + o + 1, y + 16 + 1, this.getZOffset(), 2.0F, 182.0F, q, 3, 256, 512);
            }
         }
      }
   }

   private void renderScrollbar(MatrixStack matrices, int x, int y, TradeOfferList tradeOffers) {
      int k = tradeOffers.size() + 1 - 7;
      if (k > 1) {
         int l = 139 - (27 + (k - 1) * 139 / k);
         int m = 1 + l / k + 139 / k;
         int n = 113;
         int o = Math.min(113, this.indexStartOffset * m);
         if (this.indexStartOffset == k - 1) {
            o = 113;
         }

         drawTexture(matrices, x + 94, y + 18 + o, this.getZOffset(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         drawTexture(matrices, x + 94, y + 18, this.getZOffset(), 6.0F, 199.0F, 6, 27, 256, 512);
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      super.render(matrices, mouseX, mouseY, delta);
      TradeOfferList lv = this.handler.getRecipes();
      if (!lv.isEmpty()) {
         int k = (this.width - this.backgroundWidth) / 2;
         int l = (this.height - this.backgroundHeight) / 2;
         int m = l + 16 + 1;
         int n = k + 5 + 5;
         RenderSystem.pushMatrix();
         RenderSystem.enableRescaleNormal();
         this.client.getTextureManager().bindTexture(TEXTURE);
         this.renderScrollbar(matrices, k, l, lv);
         int o = 0;

         for (TradeOffer lv2 : lv) {
            if (!this.canScroll(lv.size()) || o >= this.indexStartOffset && o < 7 + this.indexStartOffset) {
               ItemStack lv3 = lv2.getOriginalFirstBuyItem();
               ItemStack lv4 = lv2.getAdjustedFirstBuyItem();
               ItemStack lv5 = lv2.getSecondBuyItem();
               ItemStack lv6 = lv2.getMutableSellItem();
               this.itemRenderer.zOffset = 100.0F;
               int p = m + 2;
               this.renderFirstBuyItem(matrices, lv4, lv3, n, p);
               if (!lv5.isEmpty()) {
                  this.itemRenderer.renderInGui(lv5, k + 5 + 35, p);
                  this.itemRenderer.renderGuiItemOverlay(this.textRenderer, lv5, k + 5 + 35, p);
               }

               this.renderArrow(matrices, lv2, k, p);
               this.itemRenderer.renderInGui(lv6, k + 5 + 68, p);
               this.itemRenderer.renderGuiItemOverlay(this.textRenderer, lv6, k + 5 + 68, p);
               this.itemRenderer.zOffset = 0.0F;
               m += 20;
               o++;
            } else {
               o++;
            }
         }

         int q = this.selectedIndex;
         TradeOffer lv7 = lv.get(q);
         if (this.handler.isLeveled()) {
            this.drawLevelInfo(matrices, k, l, lv7);
         }

         if (lv7.isDisabled() && this.isPointWithinBounds(186, 35, 22, 21, (double)mouseX, (double)mouseY) && this.handler.canRefreshTrades()) {
            this.renderTooltip(matrices, DEPRECATED_TEXT, mouseX, mouseY);
         }

         for (MerchantScreen.WidgetButtonPage lv8 : this.offers) {
            if (lv8.isHovered()) {
               lv8.renderToolTip(matrices, mouseX, mouseY);
            }

            lv8.visible = lv8.index < this.handler.getRecipes().size();
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
      int i = this.handler.getRecipes().size();
      if (this.canScroll(i)) {
         int j = i - 7;
         this.indexStartOffset = (int)((double)this.indexStartOffset - amount);
         this.indexStartOffset = MathHelper.clamp(this.indexStartOffset, 0, j);
      }

      return true;
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      int j = this.handler.getRecipes().size();
      if (this.scrolling) {
         int k = this.y + 18;
         int l = k + 139;
         int m = j - 7;
         float h = ((float)mouseY - (float)k - 13.5F) / ((float)(l - k) - 27.0F);
         h = h * (float)m + 0.5F;
         this.indexStartOffset = MathHelper.clamp((int)h, 0, m);
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.scrolling = false;
      int j = (this.width - this.backgroundWidth) / 2;
      int k = (this.height - this.backgroundHeight) / 2;
      if (this.canScroll(this.handler.getRecipes().size())
         && mouseX > (double)(j + 94)
         && mouseX < (double)(j + 94 + 6)
         && mouseY > (double)(k + 18)
         && mouseY <= (double)(k + 18 + 139 + 1)) {
         this.scrolling = true;
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Environment(EnvType.CLIENT)
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
               ItemStack lv = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getAdjustedFirstBuyItem();
               MerchantScreen.this.renderTooltip(matrices, lv, mouseX, mouseY);
            } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
               ItemStack lv2 = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getSecondBuyItem();
               if (!lv2.isEmpty()) {
                  MerchantScreen.this.renderTooltip(matrices, lv2, mouseX, mouseY);
               }
            } else if (mouseX > this.x + 65) {
               ItemStack lv3 = MerchantScreen.this.handler.getRecipes().get(this.index + MerchantScreen.this.indexStartOffset).getMutableSellItem();
               MerchantScreen.this.renderTooltip(matrices, lv3, mouseX, mouseY);
            }
         }
      }
   }
}
