/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.advancement;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

enum AdvancementTabType {
    ABOVE(0, 0, 28, 32, 8),
    BELOW(84, 0, 28, 32, 8),
    LEFT(0, 64, 32, 28, 5),
    RIGHT(96, 64, 32, 28, 5);

    private final int u;
    private final int v;
    private final int width;
    private final int height;
    private final int tabCount;

    private AdvancementTabType(int u, int v, int width, int height, int tabCount) {
        this.u = u;
        this.v = v;
        this.width = width;
        this.height = height;
        this.tabCount = tabCount;
    }

    public int getTabCount() {
        return this.tabCount;
    }

    public void drawBackground(MatrixStack matrixStack, DrawableHelper drawableHelper, int n, int n2, boolean bl, int n3) {
        _snowman = this.u;
        if (n3 > 0) {
            _snowman += this.width;
        }
        if (n3 == this.tabCount - 1) {
            _snowman += this.width;
        }
        _snowman = bl ? this.v + this.height : this.v;
        drawableHelper.drawTexture(matrixStack, n + this.getTabX(n3), n2 + this.getTabY(n3), _snowman, _snowman, this.width, this.height);
    }

    public void drawIcon(int x, int y, int index, ItemRenderer itemRenderer, ItemStack icon) {
        int n = x + this.getTabX(index);
        _snowman = y + this.getTabY(index);
        switch (this) {
            case ABOVE: {
                n += 6;
                _snowman += 9;
                break;
            }
            case BELOW: {
                n += 6;
                _snowman += 6;
                break;
            }
            case LEFT: {
                n += 10;
                _snowman += 5;
                break;
            }
            case RIGHT: {
                n += 6;
                _snowman += 5;
            }
        }
        itemRenderer.renderInGui(icon, n, _snowman);
    }

    public int getTabX(int index) {
        switch (this) {
            case ABOVE: {
                return (this.width + 4) * index;
            }
            case BELOW: {
                return (this.width + 4) * index;
            }
            case LEFT: {
                return -this.width + 4;
            }
            case RIGHT: {
                return 248;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + (Object)((Object)this));
    }

    public int getTabY(int index) {
        switch (this) {
            case ABOVE: {
                return -this.height + 4;
            }
            case BELOW: {
                return 136;
            }
            case LEFT: {
                return this.height * index;
            }
            case RIGHT: {
                return this.height * index;
            }
        }
        throw new UnsupportedOperationException("Don't know what this tab type is!" + (Object)((Object)this));
    }

    public boolean isClickOnTab(int screenX, int screenY, int index, double mouseX, double mouseY) {
        int n = screenX + this.getTabX(index);
        _snowman = screenY + this.getTabY(index);
        return mouseX > (double)n && mouseX < (double)(n + this.width) && mouseY > (double)_snowman && mouseY < (double)(_snowman + this.height);
    }
}

