/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class AdvancementTab
extends DrawableHelper {
    private final MinecraftClient client;
    private final AdvancementsScreen screen;
    private final AdvancementTabType type;
    private final int index;
    private final Advancement root;
    private final AdvancementDisplay display;
    private final ItemStack icon;
    private final Text title;
    private final AdvancementWidget rootWidget;
    private final Map<Advancement, AdvancementWidget> widgets = Maps.newLinkedHashMap();
    private double originX;
    private double originY;
    private int minPanX = Integer.MAX_VALUE;
    private int minPanY = Integer.MAX_VALUE;
    private int maxPanX = Integer.MIN_VALUE;
    private int maxPanY = Integer.MIN_VALUE;
    private float alpha;
    private boolean initialized;

    public AdvancementTab(MinecraftClient client, AdvancementsScreen screen, AdvancementTabType type, int index, Advancement root, AdvancementDisplay display) {
        this.client = client;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.root = root;
        this.display = display;
        this.icon = display.getIcon();
        this.title = display.getTitle();
        this.rootWidget = new AdvancementWidget(this, client, root, display);
        this.addWidget(this.rootWidget, root);
    }

    public Advancement getRoot() {
        return this.root;
    }

    public Text getTitle() {
        return this.title;
    }

    public void drawBackground(MatrixStack matrixStack, int n, int n2, boolean bl) {
        this.type.drawBackground(matrixStack, this, n, n2, bl, this.index);
    }

    public void drawIcon(int x, int y, ItemRenderer itemRenderer) {
        this.type.drawIcon(x, y, this.index, itemRenderer, this.icon);
    }

    public void render(MatrixStack matrixStack2) {
        MatrixStack matrixStack2;
        if (!this.initialized) {
            this.originX = 117 - (this.maxPanX + this.minPanX) / 2;
            this.originY = 56 - (this.maxPanY + this.minPanY) / 2;
            this.initialized = true;
        }
        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0f, 0.0f, 950.0f);
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTab.fill(matrixStack2, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0f, 0.0f, -950.0f);
        RenderSystem.depthFunc(518);
        AdvancementTab.fill(matrixStack2, 234, 113, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        Identifier identifier = this.display.getBackground();
        if (identifier != null) {
            this.client.getTextureManager().bindTexture(identifier);
        } else {
            this.client.getTextureManager().bindTexture(TextureManager.MISSING_IDENTIFIER);
        }
        int _snowman2 = MathHelper.floor(this.originX);
        int _snowman3 = MathHelper.floor(this.originY);
        int _snowman4 = _snowman2 % 16;
        int _snowman5 = _snowman3 % 16;
        for (int i = -1; i <= 15; ++i) {
            for (_snowman = -1; _snowman <= 8; ++_snowman) {
                AdvancementTab.drawTexture(matrixStack2, _snowman4 + 16 * i, _snowman5 + 16 * _snowman, 0.0f, 0.0f, 16, 16, 16, 16);
            }
        }
        this.rootWidget.renderLines(matrixStack2, _snowman2, _snowman3, true);
        this.rootWidget.renderLines(matrixStack2, _snowman2, _snowman3, false);
        this.rootWidget.renderWidgets(matrixStack2, _snowman2, _snowman3);
        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0f, 0.0f, -950.0f);
        RenderSystem.colorMask(false, false, false, false);
        AdvancementTab.fill(matrixStack2, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0f, 0.0f, 950.0f);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
    }

    public void drawWidgetTooltip(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 200.0f);
        AdvancementTab.fill(matrixStack, 0, 0, 234, 113, MathHelper.floor(this.alpha * 255.0f) << 24);
        boolean bl = false;
        int _snowman2 = MathHelper.floor(this.originX);
        int _snowman3 = MathHelper.floor(this.originY);
        if (n > 0 && n < 234 && n2 > 0 && n2 < 113) {
            for (AdvancementWidget advancementWidget : this.widgets.values()) {
                if (!advancementWidget.shouldRender(_snowman2, _snowman3, n, n2)) continue;
                bl = true;
                advancementWidget.drawTooltip(matrixStack, _snowman2, _snowman3, this.alpha, n3, n4);
                break;
            }
        }
        RenderSystem.popMatrix();
        this.alpha = bl ? MathHelper.clamp(this.alpha + 0.02f, 0.0f, 0.3f) : MathHelper.clamp(this.alpha - 0.04f, 0.0f, 1.0f);
    }

    public boolean isClickOnTab(int screenX, int screenY, double mouseX, double mouseY) {
        return this.type.isClickOnTab(screenX, screenY, this.index, mouseX, mouseY);
    }

    @Nullable
    public static AdvancementTab create(MinecraftClient minecraft, AdvancementsScreen screen, int index, Advancement root) {
        if (root.getDisplay() == null) {
            return null;
        }
        for (AdvancementTabType advancementTabType : AdvancementTabType.values()) {
            if (index >= advancementTabType.getTabCount()) {
                index -= advancementTabType.getTabCount();
                continue;
            }
            return new AdvancementTab(minecraft, screen, advancementTabType, index, root, root.getDisplay());
        }
        return null;
    }

    public void move(double offsetX, double offsetY) {
        if (this.maxPanX - this.minPanX > 234) {
            this.originX = MathHelper.clamp(this.originX + offsetX, (double)(-(this.maxPanX - 234)), 0.0);
        }
        if (this.maxPanY - this.minPanY > 113) {
            this.originY = MathHelper.clamp(this.originY + offsetY, (double)(-(this.maxPanY - 113)), 0.0);
        }
    }

    public void addAdvancement(Advancement advancement) {
        if (advancement.getDisplay() == null) {
            return;
        }
        AdvancementWidget advancementWidget = new AdvancementWidget(this, this.client, advancement, advancement.getDisplay());
        this.addWidget(advancementWidget, advancement);
    }

    private void addWidget(AdvancementWidget widget, Advancement advancement) {
        this.widgets.put(advancement, widget);
        int n = widget.getX();
        _snowman = n + 28;
        _snowman = widget.getY();
        _snowman = _snowman + 27;
        this.minPanX = Math.min(this.minPanX, n);
        this.maxPanX = Math.max(this.maxPanX, _snowman);
        this.minPanY = Math.min(this.minPanY, _snowman);
        this.maxPanY = Math.max(this.maxPanY, _snowman);
        for (AdvancementWidget advancementWidget : this.widgets.values()) {
            advancementWidget.addToTree();
        }
    }

    @Nullable
    public AdvancementWidget getWidget(Advancement advancement) {
        return this.widgets.get(advancement);
    }

    public AdvancementsScreen getScreen() {
        return this.screen;
    }
}

