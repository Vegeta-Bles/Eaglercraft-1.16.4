/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.SelectMerchantTradeC2SPacket;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerData;

public class MerchantScreen
extends HandledScreen<MerchantScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/villager2.png");
    private static final Text TRADES_TEXT = new TranslatableText("merchant.trades");
    private static final Text SEPARATOR = new LiteralText(" - ");
    private static final Text DEPRECATED_TEXT = new TranslatableText("merchant.deprecated");
    private int selectedIndex;
    private final WidgetButtonPage[] offers = new WidgetButtonPage[7];
    private int indexStartOffset;
    private boolean scrolling;

    public MerchantScreen(MerchantScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 276;
        this.playerInventoryTitleX = 107;
    }

    private void syncRecipeIndex() {
        ((MerchantScreenHandler)this.handler).setRecipeIndex(this.selectedIndex);
        ((MerchantScreenHandler)this.handler).switchTo(this.selectedIndex);
        this.client.getNetworkHandler().sendPacket(new SelectMerchantTradeC2SPacket(this.selectedIndex));
    }

    @Override
    protected void init() {
        super.init();
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        _snowman = _snowman + 16 + 2;
        for (_snowman = 0; _snowman < 7; ++_snowman) {
            this.offers[_snowman] = this.addButton(new WidgetButtonPage(n + 5, _snowman, _snowman, button -> {
                if (button instanceof WidgetButtonPage) {
                    this.selectedIndex = ((WidgetButtonPage)button).getIndex() + this.indexStartOffset;
                    this.syncRecipeIndex();
                }
            }));
            _snowman += 20;
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        int n = ((MerchantScreenHandler)this.handler).getLevelProgress();
        if (n > 0 && n <= 5 && ((MerchantScreenHandler)this.handler).isLeveled()) {
            MutableText mutableText = this.title.shallowCopy().append(SEPARATOR).append(new TranslatableText("merchant.level." + n));
            int _snowman2 = this.textRenderer.getWidth(mutableText);
            int _snowman3 = 49 + this.backgroundWidth / 2 - _snowman2 / 2;
            this.textRenderer.draw(matrices, mutableText, (float)_snowman3, 6.0f, 0x404040);
        } else {
            this.textRenderer.draw(matrices, this.title, (float)(49 + this.backgroundWidth / 2 - this.textRenderer.getWidth(this.title) / 2), 6.0f, 0x404040);
        }
        this.textRenderer.draw(matrices, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY, 0x404040);
        int n2 = this.textRenderer.getWidth(TRADES_TEXT);
        this.textRenderer.draw(matrices, TRADES_TEXT, (float)(5 - n2 / 2 + 48), 6.0f, 0x404040);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        MerchantScreen.drawTexture(matrices, n, _snowman, this.getZOffset(), 0.0f, 0.0f, this.backgroundWidth, this.backgroundHeight, 256, 512);
        TradeOfferList _snowman2 = ((MerchantScreenHandler)this.handler).getRecipes();
        if (!_snowman2.isEmpty()) {
            _snowman = this.selectedIndex;
            if (_snowman < 0 || _snowman >= _snowman2.size()) {
                return;
            }
            TradeOffer tradeOffer = (TradeOffer)_snowman2.get(_snowman);
            if (tradeOffer.isDisabled()) {
                this.client.getTextureManager().bindTexture(TEXTURE);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                MerchantScreen.drawTexture(matrices, this.x + 83 + 99, this.y + 35, this.getZOffset(), 311.0f, 0.0f, 28, 21, 256, 512);
            }
        }
    }

    private void drawLevelInfo(MatrixStack matrices, int x, int y, TradeOffer tradeOffer) {
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = ((MerchantScreenHandler)this.handler).getLevelProgress();
        _snowman = ((MerchantScreenHandler)this.handler).getExperience();
        if (n >= 5) {
            return;
        }
        MerchantScreen.drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0f, 186.0f, 102, 5, 256, 512);
        _snowman = VillagerData.getLowerLevelExperience(n);
        if (_snowman < _snowman || !VillagerData.canLevelUp(n)) {
            return;
        }
        _snowman = 100;
        float _snowman2 = 100.0f / (float)(VillagerData.getUpperLevelExperience(n) - _snowman);
        _snowman = Math.min(MathHelper.floor(_snowman2 * (float)(_snowman - _snowman)), 100);
        MerchantScreen.drawTexture(matrices, x + 136, y + 16, this.getZOffset(), 0.0f, 191.0f, _snowman + 1, 5, 256, 512);
        _snowman = ((MerchantScreenHandler)this.handler).getMerchantRewardedExperience();
        if (_snowman > 0) {
            _snowman = Math.min(MathHelper.floor((float)_snowman * _snowman2), 100 - _snowman);
            MerchantScreen.drawTexture(matrices, x + 136 + _snowman + 1, y + 16 + 1, this.getZOffset(), 2.0f, 182.0f, _snowman, 3, 256, 512);
        }
    }

    private void renderScrollbar(MatrixStack matrices, int x, int y, TradeOfferList tradeOffers) {
        int n = tradeOffers.size() + 1 - 7;
        if (n > 1) {
            _snowman = 139 - (27 + (n - 1) * 139 / n);
            _snowman = 1 + _snowman / n + 139 / n;
            _snowman = 113;
            _snowman = Math.min(113, this.indexStartOffset * _snowman);
            if (this.indexStartOffset == n - 1) {
                _snowman = 113;
            }
            MerchantScreen.drawTexture(matrices, x + 94, y + 18 + _snowman, this.getZOffset(), 0.0f, 199.0f, 6, 27, 256, 512);
        } else {
            MerchantScreen.drawTexture(matrices, x + 94, y + 18, this.getZOffset(), 6.0f, 199.0f, 6, 27, 256, 512);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        TradeOfferList tradeOfferList = ((MerchantScreenHandler)this.handler).getRecipes();
        if (!tradeOfferList.isEmpty()) {
            int n = (this.width - this.backgroundWidth) / 2;
            int n2 = (this.height - this.backgroundHeight) / 2;
            _snowman = n2 + 16 + 1;
            _snowman = n + 5 + 5;
            RenderSystem.pushMatrix();
            RenderSystem.enableRescaleNormal();
            this.client.getTextureManager().bindTexture(TEXTURE);
            this.renderScrollbar(matrices, n, n2, tradeOfferList);
            _snowman = 0;
            for (TradeOffer _snowman42 : tradeOfferList) {
                if (this.canScroll(tradeOfferList.size()) && (_snowman < this.indexStartOffset || _snowman >= 7 + this.indexStartOffset)) {
                    ++_snowman;
                    continue;
                }
                ItemStack itemStack = _snowman42.getOriginalFirstBuyItem();
                ItemStack itemStack2 = _snowman42.getAdjustedFirstBuyItem();
                _snowman = _snowman42.getSecondBuyItem();
                ItemStack itemStack3 = _snowman42.getMutableSellItem();
                this.itemRenderer.zOffset = 100.0f;
                int _snowman3 = _snowman + 2;
                this.renderFirstBuyItem(matrices, itemStack2, itemStack, _snowman, _snowman3);
                if (!_snowman.isEmpty()) {
                    this.itemRenderer.renderInGui(_snowman, n + 5 + 35, _snowman3);
                    this.itemRenderer.renderGuiItemOverlay(this.textRenderer, _snowman, n + 5 + 35, _snowman3);
                }
                this.renderArrow(matrices, _snowman42, n, _snowman3);
                this.itemRenderer.renderInGui(itemStack3, n + 5 + 68, _snowman3);
                this.itemRenderer.renderGuiItemOverlay(this.textRenderer, itemStack3, n + 5 + 68, _snowman3);
                this.itemRenderer.zOffset = 0.0f;
                _snowman += 20;
                ++_snowman;
            }
            int n22 = this.selectedIndex;
            TradeOffer tradeOffer = (TradeOffer)tradeOfferList.get(n22);
            if (((MerchantScreenHandler)this.handler).isLeveled()) {
                this.drawLevelInfo(matrices, n, n2, tradeOffer);
            }
            if (tradeOffer.isDisabled() && this.isPointWithinBounds(186, 35, 22, 21, mouseX, mouseY) && ((MerchantScreenHandler)this.handler).canRefreshTrades()) {
                this.renderTooltip(matrices, DEPRECATED_TEXT, mouseX, mouseY);
            }
            for (WidgetButtonPage widgetButtonPage : this.offers) {
                if (widgetButtonPage.isHovered()) {
                    widgetButtonPage.renderToolTip(matrices, mouseX, mouseY);
                }
                widgetButtonPage.visible = widgetButtonPage.index < ((MerchantScreenHandler)this.handler).getRecipes().size();
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
            MerchantScreen.drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 25.0f, 171.0f, 10, 9, 256, 512);
        } else {
            MerchantScreen.drawTexture(matrices, x + 5 + 35 + 20, y + 3, this.getZOffset(), 15.0f, 171.0f, 10, 9, 256, 512);
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
            MerchantScreen.drawTexture(matrices, x + 7, y + 12, this.getZOffset(), 0.0f, 176.0f, 9, 2, 256, 512);
            this.setZOffset(this.getZOffset() - 300);
        }
    }

    private boolean canScroll(int listSize) {
        return listSize > 7;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        int n = ((MerchantScreenHandler)this.handler).getRecipes().size();
        if (this.canScroll(n)) {
            _snowman = n - 7;
            this.indexStartOffset = (int)((double)this.indexStartOffset - amount);
            this.indexStartOffset = MathHelper.clamp(this.indexStartOffset, 0, _snowman);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        int n = ((MerchantScreenHandler)this.handler).getRecipes().size();
        if (this.scrolling) {
            _snowman = this.y + 18;
            _snowman = _snowman + 139;
            _snowman = n - 7;
            float f = ((float)mouseY - (float)_snowman - 13.5f) / ((float)(_snowman - _snowman) - 27.0f);
            f = f * (float)_snowman + 0.5f;
            this.indexStartOffset = MathHelper.clamp((int)f, 0, _snowman);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        if (this.canScroll(((MerchantScreenHandler)this.handler).getRecipes().size()) && mouseX > (double)(n + 94) && mouseX < (double)(n + 94 + 6) && mouseY > (double)(_snowman + 18) && mouseY <= (double)(_snowman + 18 + 139 + 1)) {
            this.scrolling = true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    class WidgetButtonPage
    extends ButtonWidget {
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
            if (this.hovered && ((MerchantScreenHandler)MerchantScreen.this.handler).getRecipes().size() > this.index + MerchantScreen.this.indexStartOffset) {
                if (mouseX < this.x + 20) {
                    ItemStack itemStack = ((TradeOffer)((MerchantScreenHandler)MerchantScreen.this.handler).getRecipes().get(this.index + MerchantScreen.this.indexStartOffset)).getAdjustedFirstBuyItem();
                    MerchantScreen.this.renderTooltip(matrices, itemStack, mouseX, mouseY);
                } else if (mouseX < this.x + 50 && mouseX > this.x + 30) {
                    ItemStack itemStack = ((TradeOffer)((MerchantScreenHandler)MerchantScreen.this.handler).getRecipes().get(this.index + MerchantScreen.this.indexStartOffset)).getSecondBuyItem();
                    if (!itemStack.isEmpty()) {
                        MerchantScreen.this.renderTooltip(matrices, itemStack, mouseX, mouseY);
                    }
                } else if (mouseX > this.x + 65) {
                    ItemStack itemStack = ((TradeOffer)((MerchantScreenHandler)MerchantScreen.this.handler).getRecipes().get(this.index + MerchantScreen.this.indexStartOffset)).getMutableSellItem();
                    MerchantScreen.this.renderTooltip(matrices, itemStack, mouseX, mouseY);
                }
            }
        }
    }
}

