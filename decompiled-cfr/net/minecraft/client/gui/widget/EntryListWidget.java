/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.widget;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public abstract class EntryListWidget<E extends Entry<E>>
extends AbstractParentElement
implements Drawable {
    protected final MinecraftClient client;
    protected final int itemHeight;
    private final List<E> children = new Entries();
    protected int width;
    protected int height;
    protected int top;
    protected int bottom;
    protected int right;
    protected int left;
    protected boolean centerListVertically = true;
    private double scrollAmount;
    private boolean renderSelection = true;
    private boolean renderHeader;
    protected int headerHeight;
    private boolean scrolling;
    private E selected;
    private boolean field_26846 = true;
    private boolean field_26847 = true;

    public EntryListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        this.client = client;
        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.itemHeight = itemHeight;
        this.left = 0;
        this.right = width;
    }

    public void setRenderSelection(boolean renderSelection) {
        this.renderSelection = renderSelection;
    }

    protected void setRenderHeader(boolean renderHeader, int headerHeight) {
        this.renderHeader = renderHeader;
        this.headerHeight = headerHeight;
        if (!renderHeader) {
            this.headerHeight = 0;
        }
    }

    public int getRowWidth() {
        return 220;
    }

    @Nullable
    public E getSelected() {
        return this.selected;
    }

    public void setSelected(@Nullable E entry) {
        this.selected = entry;
    }

    public void method_31322(boolean bl) {
        this.field_26846 = bl;
    }

    public void method_31323(boolean bl) {
        this.field_26847 = bl;
    }

    @Nullable
    public E getFocused() {
        return (E)((Entry)super.getFocused());
    }

    public final List<E> children() {
        return this.children;
    }

    protected final void clearEntries() {
        this.children.clear();
    }

    protected void replaceEntries(Collection<E> newEntries) {
        this.children.clear();
        this.children.addAll(newEntries);
    }

    protected E getEntry(int index) {
        return (E)((Entry)this.children().get(index));
    }

    protected int addEntry(E entry) {
        this.children.add(entry);
        return this.children.size() - 1;
    }

    protected int getItemCount() {
        return this.children().size();
    }

    protected boolean isSelectedItem(int index) {
        return Objects.equals(this.getSelected(), this.children().get(index));
    }

    @Nullable
    protected final E getEntryAtPosition(double x, double y) {
        int n = this.getRowWidth() / 2;
        _snowman = this.left + this.width / 2;
        _snowman = _snowman - n;
        _snowman = _snowman + n;
        _snowman = MathHelper.floor(y - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
        _snowman = _snowman / this.itemHeight;
        if (x < (double)this.getScrollbarPositionX() && x >= (double)_snowman && x <= (double)_snowman && _snowman >= 0 && _snowman >= 0 && _snowman < this.getItemCount()) {
            return (E)((Entry)this.children().get(_snowman));
        }
        return null;
    }

    public void updateSize(int width, int height, int top, int bottom) {
        this.width = width;
        this.height = height;
        this.top = top;
        this.bottom = bottom;
        this.left = 0;
        this.right = width;
    }

    public void setLeftPos(int left) {
        this.left = left;
        this.right = left + this.width;
    }

    protected int getMaxPosition() {
        return this.getItemCount() * this.itemHeight + this.headerHeight;
    }

    protected void clickedHeader(int x, int y) {
    }

    protected void renderHeader(MatrixStack matrices, int x, int y, Tessellator tessellator) {
    }

    protected void renderBackground(MatrixStack matrices) {
    }

    protected void renderDecorations(MatrixStack matrixStack, int n, int n2) {
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int _snowman5;
        int _snowman4;
        this.renderBackground(matrices);
        int n = this.getScrollbarPositionX();
        _snowman = n + 6;
        Tessellator _snowman2 = Tessellator.getInstance();
        BufferBuilder _snowman3 = _snowman2.getBuffer();
        if (this.field_26846) {
            this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f = 32.0f;
            _snowman3.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            _snowman3.vertex(this.left, this.bottom, 0.0).texture((float)this.left / 32.0f, (float)(this.bottom + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).next();
            _snowman3.vertex(this.right, this.bottom, 0.0).texture((float)this.right / 32.0f, (float)(this.bottom + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).next();
            _snowman3.vertex(this.right, this.top, 0.0).texture((float)this.right / 32.0f, (float)(this.top + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).next();
            _snowman3.vertex(this.left, this.top, 0.0).texture((float)this.left / 32.0f, (float)(this.top + (int)this.getScrollAmount()) / 32.0f).color(32, 32, 32, 255).next();
            _snowman2.draw();
        }
        int n2 = this.getRowLeft();
        _snowman = this.top + 4 - (int)this.getScrollAmount();
        if (this.renderHeader) {
            this.renderHeader(matrices, n2, _snowman, _snowman2);
        }
        this.renderList(matrices, n2, _snowman, mouseX, mouseY, delta);
        if (this.field_26847) {
            this.client.getTextureManager().bindTexture(DrawableHelper.OPTIONS_BACKGROUND_TEXTURE);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);
            float f = 32.0f;
            _snowman4 = -100;
            _snowman3.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            _snowman3.vertex(this.left, this.top, -100.0).texture(0.0f, (float)this.top / 32.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left + this.width, this.top, -100.0).texture((float)this.width / 32.0f, (float)this.top / 32.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left + this.width, 0.0, -100.0).texture((float)this.width / 32.0f, 0.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left, 0.0, -100.0).texture(0.0f, 0.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left, this.height, -100.0).texture(0.0f, (float)this.height / 32.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left + this.width, this.height, -100.0).texture((float)this.width / 32.0f, (float)this.height / 32.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left + this.width, this.bottom, -100.0).texture((float)this.width / 32.0f, (float)this.bottom / 32.0f).color(64, 64, 64, 255).next();
            _snowman3.vertex(this.left, this.bottom, -100.0).texture(0.0f, (float)this.bottom / 32.0f).color(64, 64, 64, 255).next();
            _snowman2.draw();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ZERO, GlStateManager.DstFactor.ONE);
            RenderSystem.disableAlphaTest();
            RenderSystem.shadeModel(7425);
            RenderSystem.disableTexture();
            _snowman5 = 4;
            _snowman3.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            _snowman3.vertex(this.left, this.top + 4, 0.0).texture(0.0f, 1.0f).color(0, 0, 0, 0).next();
            _snowman3.vertex(this.right, this.top + 4, 0.0).texture(1.0f, 1.0f).color(0, 0, 0, 0).next();
            _snowman3.vertex(this.right, this.top, 0.0).texture(1.0f, 0.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(this.left, this.top, 0.0).texture(0.0f, 0.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(this.left, this.bottom, 0.0).texture(0.0f, 1.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(this.right, this.bottom, 0.0).texture(1.0f, 1.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(this.right, this.bottom - 4, 0.0).texture(1.0f, 0.0f).color(0, 0, 0, 0).next();
            _snowman3.vertex(this.left, this.bottom - 4, 0.0).texture(0.0f, 0.0f).color(0, 0, 0, 0).next();
            _snowman2.draw();
        }
        if ((_snowman = this.getMaxScroll()) > 0) {
            RenderSystem.disableTexture();
            _snowman4 = (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / (float)this.getMaxPosition());
            _snowman4 = MathHelper.clamp(_snowman4, 32, this.bottom - this.top - 8);
            _snowman5 = (int)this.getScrollAmount() * (this.bottom - this.top - _snowman4) / _snowman + this.top;
            if (_snowman5 < this.top) {
                _snowman5 = this.top;
            }
            _snowman3.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
            _snowman3.vertex(n, this.bottom, 0.0).texture(0.0f, 1.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(_snowman, this.bottom, 0.0).texture(1.0f, 1.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(_snowman, this.top, 0.0).texture(1.0f, 0.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(n, this.top, 0.0).texture(0.0f, 0.0f).color(0, 0, 0, 255).next();
            _snowman3.vertex(n, _snowman5 + _snowman4, 0.0).texture(0.0f, 1.0f).color(128, 128, 128, 255).next();
            _snowman3.vertex(_snowman, _snowman5 + _snowman4, 0.0).texture(1.0f, 1.0f).color(128, 128, 128, 255).next();
            _snowman3.vertex(_snowman, _snowman5, 0.0).texture(1.0f, 0.0f).color(128, 128, 128, 255).next();
            _snowman3.vertex(n, _snowman5, 0.0).texture(0.0f, 0.0f).color(128, 128, 128, 255).next();
            _snowman3.vertex(n, _snowman5 + _snowman4 - 1, 0.0).texture(0.0f, 1.0f).color(192, 192, 192, 255).next();
            _snowman3.vertex(_snowman - 1, _snowman5 + _snowman4 - 1, 0.0).texture(1.0f, 1.0f).color(192, 192, 192, 255).next();
            _snowman3.vertex(_snowman - 1, _snowman5, 0.0).texture(1.0f, 0.0f).color(192, 192, 192, 255).next();
            _snowman3.vertex(n, _snowman5, 0.0).texture(0.0f, 0.0f).color(192, 192, 192, 255).next();
            _snowman2.draw();
        }
        this.renderDecorations(matrices, mouseX, mouseY);
        RenderSystem.enableTexture();
        RenderSystem.shadeModel(7424);
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
    }

    protected void centerScrollOn(E entry) {
        this.setScrollAmount(this.children().indexOf(entry) * this.itemHeight + this.itemHeight / 2 - (this.bottom - this.top) / 2);
    }

    protected void ensureVisible(E entry) {
        int n = this.getRowTop(this.children().indexOf(entry));
        _snowman = n - this.top - 4 - this.itemHeight;
        if (_snowman < 0) {
            this.scroll(_snowman);
        }
        if ((_snowman = this.bottom - n - this.itemHeight - this.itemHeight) < 0) {
            this.scroll(-_snowman);
        }
    }

    private void scroll(int amount) {
        this.setScrollAmount(this.getScrollAmount() + (double)amount);
    }

    public double getScrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(double amount) {
        this.scrollAmount = MathHelper.clamp(amount, 0.0, (double)this.getMaxScroll());
    }

    public int getMaxScroll() {
        return Math.max(0, this.getMaxPosition() - (this.bottom - this.top - 4));
    }

    protected void updateScrollingState(double mouseX, double mouseY, int button) {
        this.scrolling = button == 0 && mouseX >= (double)this.getScrollbarPositionX() && mouseX < (double)(this.getScrollbarPositionX() + 6);
    }

    protected int getScrollbarPositionX() {
        return this.width / 2 + 124;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.updateScrollingState(mouseX, mouseY, button);
        if (!this.isMouseOver(mouseX, mouseY)) {
            return false;
        }
        E e = this.getEntryAtPosition(mouseX, mouseY);
        if (e != null) {
            if (e.mouseClicked(mouseX, mouseY, button)) {
                this.setFocused((Element)e);
                this.setDragging(true);
                return true;
            }
        } else if (button == 0) {
            this.clickedHeader((int)(mouseX - (double)(this.left + this.width / 2 - this.getRowWidth() / 2)), (int)(mouseY - (double)this.top) + (int)this.getScrollAmount() - 4);
            return true;
        }
        return this.scrolling;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.getFocused() != null) {
            this.getFocused().mouseReleased(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        }
        if (button != 0 || !this.scrolling) {
            return false;
        }
        if (mouseY < (double)this.top) {
            this.setScrollAmount(0.0);
        } else if (mouseY > (double)this.bottom) {
            this.setScrollAmount(this.getMaxScroll());
        } else {
            double d = Math.max(1, this.getMaxScroll());
            int _snowman2 = this.bottom - this.top;
            int _snowman3 = MathHelper.clamp((int)((float)(_snowman2 * _snowman2) / (float)this.getMaxPosition()), 32, _snowman2 - 8);
            _snowman = Math.max(1.0, d / (double)(_snowman2 - _snowman3));
            this.setScrollAmount(this.getScrollAmount() + deltaY * _snowman);
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.setScrollAmount(this.getScrollAmount() - amount * (double)this.itemHeight / 2.0);
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == 264) {
            this.moveSelection(MoveDirection.DOWN);
            return true;
        }
        if (keyCode == 265) {
            this.moveSelection(MoveDirection.UP);
            return true;
        }
        return false;
    }

    protected void moveSelection(MoveDirection direction) {
        this.moveSelectionIf(direction, entry -> true);
    }

    protected void method_30015() {
        E e = this.getSelected();
        if (e != null) {
            this.setSelected(e);
            this.ensureVisible(e);
        }
    }

    protected void moveSelectionIf(MoveDirection direction, Predicate<E> predicate) {
        int n = _snowman = direction == MoveDirection.UP ? -1 : 1;
        if (!this.children().isEmpty()) {
            int _snowman2 = this.children().indexOf(this.getSelected());
            while (_snowman2 != (_snowman = MathHelper.clamp(_snowman2 + _snowman, 0, this.getItemCount() - 1))) {
                Entry entry = (Entry)this.children().get(_snowman);
                if (predicate.test(entry)) {
                    this.setSelected(entry);
                    this.ensureVisible(entry);
                    break;
                }
                _snowman2 = _snowman;
            }
        }
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseY >= (double)this.top && mouseY <= (double)this.bottom && mouseX >= (double)this.left && mouseX <= (double)this.right;
    }

    protected void renderList(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
        int n = this.getItemCount();
        Tessellator _snowman2 = Tessellator.getInstance();
        BufferBuilder _snowman3 = _snowman2.getBuffer();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            _snowman = this.getRowTop(_snowman);
            _snowman = this.getRowBottom(_snowman);
            if (_snowman < this.top || _snowman > this.bottom) continue;
            _snowman = y + _snowman * this.itemHeight + this.headerHeight;
            _snowman = this.itemHeight - 4;
            E e = this.getEntry(_snowman);
            int _snowman4 = this.getRowWidth();
            if (this.renderSelection && this.isSelectedItem(_snowman)) {
                int n2 = this.left + this.width / 2 - _snowman4 / 2;
                _snowman = this.left + this.width / 2 + _snowman4 / 2;
                RenderSystem.disableTexture();
                float _snowman5 = this.isFocused() ? 1.0f : 0.5f;
                RenderSystem.color4f(_snowman5, _snowman5, _snowman5, 1.0f);
                _snowman3.begin(7, VertexFormats.POSITION);
                _snowman3.vertex(n2, _snowman + _snowman + 2, 0.0).next();
                _snowman3.vertex(_snowman, _snowman + _snowman + 2, 0.0).next();
                _snowman3.vertex(_snowman, _snowman - 2, 0.0).next();
                _snowman3.vertex(n2, _snowman - 2, 0.0).next();
                _snowman2.draw();
                RenderSystem.color4f(0.0f, 0.0f, 0.0f, 1.0f);
                _snowman3.begin(7, VertexFormats.POSITION);
                _snowman3.vertex(n2 + 1, _snowman + _snowman + 1, 0.0).next();
                _snowman3.vertex(_snowman - 1, _snowman + _snowman + 1, 0.0).next();
                _snowman3.vertex(_snowman - 1, _snowman - 1, 0.0).next();
                _snowman3.vertex(n2 + 1, _snowman - 1, 0.0).next();
                _snowman2.draw();
                RenderSystem.enableTexture();
            }
            n2 = this.getRowLeft();
            ((Entry)e).render(matrices, _snowman, _snowman, n2, _snowman4, _snowman, mouseX, mouseY, this.isMouseOver(mouseX, mouseY) && Objects.equals(this.getEntryAtPosition(mouseX, mouseY), e), delta);
        }
    }

    public int getRowLeft() {
        return this.left + this.width / 2 - this.getRowWidth() / 2 + 2;
    }

    public int method_31383() {
        return this.getRowLeft() + this.getRowWidth();
    }

    protected int getRowTop(int index) {
        return this.top + 4 - (int)this.getScrollAmount() + index * this.itemHeight + this.headerHeight;
    }

    private int getRowBottom(int index) {
        return this.getRowTop(index) + this.itemHeight;
    }

    protected boolean isFocused() {
        return false;
    }

    protected E remove(int index) {
        Entry entry = (Entry)this.children.get(index);
        if (this.removeEntry((Entry)this.children.get(index))) {
            return (E)entry;
        }
        return null;
    }

    protected boolean removeEntry(E entry) {
        boolean bl = this.children.remove(entry);
        if (bl && entry == this.getSelected()) {
            this.setSelected(null);
        }
        return bl;
    }

    private void method_29621(Entry<E> entry) {
        ((Entry)entry).list = this;
    }

    @Override
    @Nullable
    public /* synthetic */ Element getFocused() {
        return this.getFocused();
    }

    class Entries
    extends AbstractList<E> {
        private final List<E> entries = Lists.newArrayList();

        private Entries() {
        }

        @Override
        public E get(int n) {
            return (Entry)this.entries.get(n);
        }

        @Override
        public int size() {
            return this.entries.size();
        }

        @Override
        public E set(int n, E e) {
            Entry entry = (Entry)this.entries.set(n, e);
            EntryListWidget.this.method_29621(e);
            return entry;
        }

        @Override
        public void add(int n, E e) {
            this.entries.add(n, e);
            EntryListWidget.this.method_29621(e);
        }

        @Override
        public E remove(int n) {
            return (Entry)this.entries.remove(n);
        }

        @Override
        public /* synthetic */ Object remove(int index) {
            return this.remove(index);
        }

        @Override
        public /* synthetic */ void add(int index, Object entry) {
            this.add(index, (E)((Entry)entry));
        }

        @Override
        public /* synthetic */ Object set(int index, Object entry) {
            return this.set(index, (E)((Entry)entry));
        }

        @Override
        public /* synthetic */ Object get(int index) {
            return this.get(index);
        }
    }

    public static abstract class Entry<E extends Entry<E>>
    implements Element {
        @Deprecated
        private EntryListWidget<E> list;

        public abstract void render(MatrixStack var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10);

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return Objects.equals(this.list.getEntryAtPosition(mouseX, mouseY), this);
        }
    }

    public static enum MoveDirection {
        UP,
        DOWN;

    }
}

