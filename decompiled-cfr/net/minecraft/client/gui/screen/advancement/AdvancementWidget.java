/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.advancement;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementObtainedStatus;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;

public class AdvancementWidget
extends DrawableHelper {
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/advancements/widgets.png");
    private static final int[] field_24262 = new int[]{0, 10, -10, 25, -25};
    private final AdvancementTab tab;
    private final Advancement advancement;
    private final AdvancementDisplay display;
    private final OrderedText title;
    private final int width;
    private final List<OrderedText> description;
    private final MinecraftClient client;
    private AdvancementWidget parent;
    private final List<AdvancementWidget> children = Lists.newArrayList();
    private AdvancementProgress progress;
    private final int xPos;
    private final int yPos;

    public AdvancementWidget(AdvancementTab tab, MinecraftClient client, Advancement advancement, AdvancementDisplay display) {
        int n;
        this.tab = tab;
        this.advancement = advancement;
        this.display = display;
        this.client = client;
        this.title = Language.getInstance().reorder(client.textRenderer.trimToWidth(display.getTitle(), 163));
        this.xPos = MathHelper.floor(display.getX() * 28.0f);
        this.yPos = MathHelper.floor(display.getY() * 27.0f);
        int n2 = advancement.getRequirementCount();
        _snowman = String.valueOf(n2).length();
        _snowman = n2 > 1 ? client.textRenderer.getWidth("  ") + client.textRenderer.getWidth("0") * _snowman * 2 + client.textRenderer.getWidth("/") : 0;
        n = 29 + client.textRenderer.getWidth(this.title) + _snowman;
        this.description = Language.getInstance().reorder(this.wrapDescription(Texts.setStyleIfAbsent(display.getDescription().shallowCopy(), Style.EMPTY.withColor(display.getFrame().getTitleFormat())), n));
        for (OrderedText orderedText : this.description) {
            n = Math.max(n, client.textRenderer.getWidth(orderedText));
        }
        this.width = n + 3 + 5;
    }

    private static float method_27572(TextHandler textHandler, List<StringVisitable> list) {
        return (float)list.stream().mapToDouble(textHandler::getWidth).max().orElse(0.0);
    }

    private List<StringVisitable> wrapDescription(Text text, int width) {
        TextHandler textHandler = this.client.textRenderer.getTextHandler();
        List<StringVisitable> _snowman2 = null;
        float _snowman3 = Float.MAX_VALUE;
        for (int n : field_24262) {
            List<StringVisitable> list = textHandler.wrapLines(text, width - n, Style.EMPTY);
            float _snowman4 = Math.abs(AdvancementWidget.method_27572(textHandler, list) - (float)width);
            if (_snowman4 <= 10.0f) {
                return list;
            }
            if (!(_snowman4 < _snowman3)) continue;
            _snowman3 = _snowman4;
            _snowman2 = list;
        }
        return _snowman2;
    }

    @Nullable
    private AdvancementWidget getParent(Advancement advancement) {
        while ((advancement = advancement.getParent()) != null && advancement.getDisplay() == null) {
        }
        if (advancement == null || advancement.getDisplay() == null) {
            return null;
        }
        return this.tab.getWidget(advancement);
    }

    public void renderLines(MatrixStack matrixStack, int n, int n2, boolean bl) {
        if (this.parent != null) {
            int n3 = n + this.parent.xPos + 13;
            _snowman = n + this.parent.xPos + 26 + 4;
            _snowman = n2 + this.parent.yPos + 13;
            _snowman = n + this.xPos + 13;
            _snowman = n2 + this.yPos + 13;
            int n4 = _snowman = bl ? -16777216 : -1;
            if (bl) {
                this.drawHorizontalLine(matrixStack, _snowman, n3, _snowman - 1, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman + 1, n3, _snowman, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman, n3, _snowman + 1, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman, _snowman - 1, _snowman - 1, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman, _snowman - 1, _snowman, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman, _snowman - 1, _snowman + 1, _snowman);
                this.drawVerticalLine(matrixStack, _snowman - 1, _snowman, _snowman, _snowman);
                this.drawVerticalLine(matrixStack, _snowman + 1, _snowman, _snowman, _snowman);
            } else {
                this.drawHorizontalLine(matrixStack, _snowman, n3, _snowman, _snowman);
                this.drawHorizontalLine(matrixStack, _snowman, _snowman, _snowman, _snowman);
                this.drawVerticalLine(matrixStack, _snowman, _snowman, _snowman, _snowman);
            }
        }
        for (AdvancementWidget advancementWidget : this.children) {
            advancementWidget.renderLines(matrixStack, n, n2, bl);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void renderWidgets(MatrixStack matrixStack, int n, int n2) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            void var5_8;
            float f;
            float f2 = f = this.progress == null ? 0.0f : this.progress.getProgressBarPercentage();
            if (f >= 1.0f) {
                AdvancementObtainedStatus advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            } else {
                AdvancementObtainedStatus advancementObtainedStatus = AdvancementObtainedStatus.UNOBTAINED;
            }
            this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
            this.drawTexture(matrixStack, n + this.xPos + 3, n2 + this.yPos, this.display.getFrame().getTextureV(), 128 + var5_8.getSpriteIndex() * 26, 26, 26);
            this.client.getItemRenderer().renderInGui(this.display.getIcon(), n + this.xPos + 8, n2 + this.yPos + 5);
        }
        for (AdvancementWidget advancementWidget : this.children) {
            advancementWidget.renderWidgets(matrixStack, n, n2);
        }
    }

    public void setProgress(AdvancementProgress progress) {
        this.progress = progress;
    }

    public void addChild(AdvancementWidget widget) {
        this.children.add(widget);
    }

    public void drawTooltip(MatrixStack matrixStack, int n, int n2, float f, int y, int n3) {
        AdvancementObtainedStatus advancementObtainedStatus;
        boolean bl = y + n + this.xPos + this.width + 26 >= this.tab.getScreen().width;
        String _snowman2 = this.progress == null ? null : this.progress.getProgressBarFraction();
        int _snowman3 = _snowman2 == null ? 0 : this.client.textRenderer.getWidth(_snowman2);
        _snowman = 113 - n2 - this.yPos - 26 <= 6 + this.description.size() * this.client.textRenderer.fontHeight;
        float _snowman4 = this.progress == null ? 0.0f : this.progress.getProgressBarPercentage();
        int _snowman5 = MathHelper.floor(_snowman4 * (float)this.width);
        if (_snowman4 >= 1.0f) {
            _snowman5 = this.width / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            _snowman = AdvancementObtainedStatus.OBTAINED;
            _snowman = AdvancementObtainedStatus.OBTAINED;
        } else if (_snowman5 < 2) {
            _snowman5 = this.width / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.UNOBTAINED;
            _snowman = AdvancementObtainedStatus.UNOBTAINED;
            _snowman = AdvancementObtainedStatus.UNOBTAINED;
        } else if (_snowman5 > this.width - 2) {
            _snowman5 = this.width / 2;
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            _snowman = AdvancementObtainedStatus.OBTAINED;
            _snowman = AdvancementObtainedStatus.UNOBTAINED;
        } else {
            advancementObtainedStatus = AdvancementObtainedStatus.OBTAINED;
            _snowman = AdvancementObtainedStatus.UNOBTAINED;
            _snowman = AdvancementObtainedStatus.UNOBTAINED;
        }
        int n4 = this.width - _snowman5;
        this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        _snowman = n2 + this.yPos;
        _snowman = bl ? n + this.xPos - this.width + 26 + 6 : n + this.xPos;
        _snowman = 32 + this.description.size() * this.client.textRenderer.fontHeight;
        if (!this.description.isEmpty()) {
            if (_snowman) {
                this.method_2324(matrixStack, _snowman, _snowman + 26 - _snowman, this.width, _snowman, 10, 200, 26, 0, 52);
            } else {
                this.method_2324(matrixStack, _snowman, _snowman, this.width, _snowman, 10, 200, 26, 0, 52);
            }
        }
        this.drawTexture(matrixStack, _snowman, _snowman, 0, advancementObtainedStatus.getSpriteIndex() * 26, _snowman5, 26);
        this.drawTexture(matrixStack, _snowman + _snowman5, _snowman, 200 - n4, _snowman.getSpriteIndex() * 26, n4, 26);
        this.drawTexture(matrixStack, n + this.xPos + 3, n2 + this.yPos, this.display.getFrame().getTextureV(), 128 + _snowman.getSpriteIndex() * 26, 26, 26);
        if (bl) {
            this.client.textRenderer.drawWithShadow(matrixStack, this.title, (float)(_snowman + 5), (float)(n2 + this.yPos + 9), -1);
            if (_snowman2 != null) {
                this.client.textRenderer.drawWithShadow(matrixStack, _snowman2, (float)(n + this.xPos - _snowman3), (float)(n2 + this.yPos + 9), -1);
            }
        } else {
            this.client.textRenderer.drawWithShadow(matrixStack, this.title, (float)(n + this.xPos + 32), (float)(n2 + this.yPos + 9), -1);
            if (_snowman2 != null) {
                this.client.textRenderer.drawWithShadow(matrixStack, _snowman2, (float)(n + this.xPos + this.width - _snowman3 - 5), (float)(n2 + this.yPos + 9), -1);
            }
        }
        if (_snowman) {
            for (_snowman = 0; _snowman < this.description.size(); ++_snowman) {
                this.client.textRenderer.draw(matrixStack, this.description.get(_snowman), (float)(_snowman + 5), (float)(_snowman + 26 - _snowman + 7 + _snowman * this.client.textRenderer.fontHeight), -5592406);
            }
        } else {
            for (_snowman = 0; _snowman < this.description.size(); ++_snowman) {
                this.client.textRenderer.draw(matrixStack, this.description.get(_snowman), (float)(_snowman + 5), (float)(n2 + this.yPos + 9 + 17 + _snowman * this.client.textRenderer.fontHeight), -5592406);
            }
        }
        this.client.getItemRenderer().renderInGui(this.display.getIcon(), n + this.xPos + 8, n2 + this.yPos + 5);
    }

    protected void method_2324(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        this.drawTexture(matrixStack, n, n2, n8, n9, n5, n5);
        this.method_2321(matrixStack, n + n5, n2, n3 - n5 - n5, n5, n8 + n5, n9, n6 - n5 - n5, n7);
        this.drawTexture(matrixStack, n + n3 - n5, n2, n8 + n6 - n5, n9, n5, n5);
        this.drawTexture(matrixStack, n, n2 + n4 - n5, n8, n9 + n7 - n5, n5, n5);
        this.method_2321(matrixStack, n + n5, n2 + n4 - n5, n3 - n5 - n5, n5, n8 + n5, n9 + n7 - n5, n6 - n5 - n5, n7);
        this.drawTexture(matrixStack, n + n3 - n5, n2 + n4 - n5, n8 + n6 - n5, n9 + n7 - n5, n5, n5);
        this.method_2321(matrixStack, n, n2 + n5, n5, n4 - n5 - n5, n8, n9 + n5, n6, n7 - n5 - n5);
        this.method_2321(matrixStack, n + n5, n2 + n5, n3 - n5 - n5, n4 - n5 - n5, n8 + n5, n9 + n5, n6 - n5 - n5, n7 - n5 - n5);
        this.method_2321(matrixStack, n + n3 - n5, n2 + n5, n5, n4 - n5 - n5, n8 + n6 - n5, n9 + n5, n6, n7 - n5 - n5);
    }

    protected void method_2321(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        for (_snowman = 0; _snowman < n3; _snowman += n7) {
            _snowman = n + _snowman;
            _snowman = Math.min(n7, n3 - _snowman);
            for (_snowman = 0; _snowman < n4; _snowman += n8) {
                _snowman = n2 + _snowman;
                _snowman = Math.min(n8, n4 - _snowman);
                this.drawTexture(matrixStack, _snowman, _snowman, n5, n6, _snowman, _snowman);
            }
        }
    }

    public boolean shouldRender(int originX, int originY, int mouseX, int mouseY) {
        if (this.display.isHidden() && (this.progress == null || !this.progress.isDone())) {
            return false;
        }
        int n = originX + this.xPos;
        _snowman = n + 26;
        _snowman = originY + this.yPos;
        _snowman = _snowman + 26;
        return mouseX >= n && mouseX <= _snowman && mouseY >= _snowman && mouseY <= _snowman;
    }

    public void addToTree() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getParent(this.advancement);
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }
    }

    public int getY() {
        return this.yPos;
    }

    public int getX() {
        return this.xPos;
    }
}

