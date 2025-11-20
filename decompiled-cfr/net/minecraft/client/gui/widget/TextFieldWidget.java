/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class TextFieldWidget
extends AbstractButtonWidget
implements Drawable,
Element {
    private final TextRenderer textRenderer;
    private String text = "";
    private int maxLength = 32;
    private int focusedTicks;
    private boolean focused = true;
    private boolean focusUnlocked = true;
    private boolean editable = true;
    private boolean selecting;
    private int firstCharacterIndex;
    private int selectionStart;
    private int selectionEnd;
    private int editableColor = 0xE0E0E0;
    private int uneditableColor = 0x707070;
    private String suggestion;
    private Consumer<String> changedListener;
    private Predicate<String> textPredicate = Objects::nonNull;
    private BiFunction<String, Integer, OrderedText> renderTextProvider = (string, n) -> OrderedText.styledString(string, Style.EMPTY);

    public TextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text text) {
        this(textRenderer, x, y, width, height, null, text);
    }

    public TextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, @Nullable TextFieldWidget copyFrom, Text text) {
        super(x, y, width, height, text);
        this.textRenderer = textRenderer;
        if (copyFrom != null) {
            this.setText(copyFrom.getText());
        }
    }

    public void setChangedListener(Consumer<String> changedListener) {
        this.changedListener = changedListener;
    }

    public void setRenderTextProvider(BiFunction<String, Integer, OrderedText> renderTextProvider) {
        this.renderTextProvider = renderTextProvider;
    }

    public void tick() {
        ++this.focusedTicks;
    }

    @Override
    protected MutableText getNarrationMessage() {
        Text text = this.getMessage();
        return new TranslatableText("gui.narrate.editBox", text, this.text);
    }

    public void setText(String text) {
        if (!this.textPredicate.test(text)) {
            return;
        }
        this.text = text.length() > this.maxLength ? text.substring(0, this.maxLength) : text;
        this.setCursorToEnd();
        this.setSelectionEnd(this.selectionStart);
        this.onChanged(text);
    }

    public String getText() {
        return this.text;
    }

    public String getSelectedText() {
        int n = this.selectionStart < this.selectionEnd ? this.selectionStart : this.selectionEnd;
        _snowman = this.selectionStart < this.selectionEnd ? this.selectionEnd : this.selectionStart;
        return this.text.substring(n, _snowman);
    }

    public void setTextPredicate(Predicate<String> textPredicate) {
        this.textPredicate = textPredicate;
    }

    public void write(String string) {
        int _snowman2;
        int n = this.selectionStart < this.selectionEnd ? this.selectionStart : this.selectionEnd;
        _snowman = this.selectionStart < this.selectionEnd ? this.selectionEnd : this.selectionStart;
        _snowman = this.maxLength - this.text.length() - (n - _snowman);
        if (_snowman < (_snowman2 = (string2 = SharedConstants.stripInvalidChars(string)).length())) {
            String string2 = string2.substring(0, _snowman);
            _snowman2 = _snowman;
        }
        if (!this.textPredicate.test(_snowman = new StringBuilder(this.text).replace(n, _snowman, string2).toString())) {
            return;
        }
        this.text = _snowman;
        this.setSelectionStart(n + _snowman2);
        this.setSelectionEnd(this.selectionStart);
        this.onChanged(this.text);
    }

    private void onChanged(String newText) {
        if (this.changedListener != null) {
            this.changedListener.accept(newText);
        }
        this.nextNarration = Util.getMeasuringTimeMs() + 500L;
    }

    private void erase(int offset) {
        if (Screen.hasControlDown()) {
            this.eraseWords(offset);
        } else {
            this.eraseCharacters(offset);
        }
    }

    public void eraseWords(int wordOffset) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.selectionEnd != this.selectionStart) {
            this.write("");
            return;
        }
        this.eraseCharacters(this.getWordSkipPosition(wordOffset) - this.selectionStart);
    }

    public void eraseCharacters(int characterOffset) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.selectionEnd != this.selectionStart) {
            this.write("");
            return;
        }
        int n = this.method_27537(characterOffset);
        _snowman = Math.min(n, this.selectionStart);
        if (_snowman == (_snowman = Math.max(n, this.selectionStart))) {
            return;
        }
        String _snowman2 = new StringBuilder(this.text).delete(_snowman, _snowman).toString();
        if (!this.textPredicate.test(_snowman2)) {
            return;
        }
        this.text = _snowman2;
        this.setCursor(_snowman);
    }

    public int getWordSkipPosition(int wordOffset) {
        return this.getWordSkipPosition(wordOffset, this.getCursor());
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition) {
        return this.getWordSkipPosition(wordOffset, cursorPosition, true);
    }

    private int getWordSkipPosition(int wordOffset, int cursorPosition, boolean skipOverSpaces) {
        int n = cursorPosition;
        boolean _snowman2 = wordOffset < 0;
        _snowman = Math.abs(wordOffset);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            if (_snowman2) {
                while (skipOverSpaces && n > 0 && this.text.charAt(n - 1) == ' ') {
                    --n;
                }
                while (n > 0 && this.text.charAt(n - 1) != ' ') {
                    --n;
                }
                continue;
            }
            _snowman = this.text.length();
            if ((n = this.text.indexOf(32, n)) == -1) {
                n = _snowman;
                continue;
            }
            while (skipOverSpaces && n < _snowman && this.text.charAt(n) == ' ') {
                ++n;
            }
        }
        return n;
    }

    public void moveCursor(int offset) {
        this.setCursor(this.method_27537(offset));
    }

    private int method_27537(int n) {
        return Util.moveCursor(this.text, this.selectionStart, n);
    }

    public void setCursor(int cursor) {
        this.setSelectionStart(cursor);
        if (!this.selecting) {
            this.setSelectionEnd(this.selectionStart);
        }
        this.onChanged(this.text);
    }

    public void setSelectionStart(int cursor) {
        this.selectionStart = MathHelper.clamp(cursor, 0, this.text.length());
    }

    public void setCursorToStart() {
        this.setCursor(0);
    }

    public void setCursorToEnd() {
        this.setCursor(this.text.length());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.isActive()) {
            return false;
        }
        this.selecting = Screen.hasShiftDown();
        if (Screen.isSelectAll(keyCode)) {
            this.setCursorToEnd();
            this.setSelectionEnd(0);
            return true;
        }
        if (Screen.isCopy(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            if (this.editable) {
                this.write(MinecraftClient.getInstance().keyboard.getClipboard());
            }
            return true;
        }
        if (Screen.isCut(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            if (this.editable) {
                this.write("");
            }
            return true;
        }
        switch (keyCode) {
            case 263: {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(-1));
                } else {
                    this.moveCursor(-1);
                }
                return true;
            }
            case 262: {
                if (Screen.hasControlDown()) {
                    this.setCursor(this.getWordSkipPosition(1));
                } else {
                    this.moveCursor(1);
                }
                return true;
            }
            case 259: {
                if (this.editable) {
                    this.selecting = false;
                    this.erase(-1);
                    this.selecting = Screen.hasShiftDown();
                }
                return true;
            }
            case 261: {
                if (this.editable) {
                    this.selecting = false;
                    this.erase(1);
                    this.selecting = Screen.hasShiftDown();
                }
                return true;
            }
            case 268: {
                this.setCursorToStart();
                return true;
            }
            case 269: {
                this.setCursorToEnd();
                return true;
            }
        }
        return false;
    }

    public boolean isActive() {
        return this.isVisible() && this.isFocused() && this.isEditable();
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (!this.isActive()) {
            return false;
        }
        if (SharedConstants.isValidChar(chr)) {
            if (this.editable) {
                this.write(Character.toString(chr));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean bl;
        if (!this.isVisible()) {
            return false;
        }
        boolean bl2 = bl = mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + this.height);
        if (this.focusUnlocked) {
            this.setSelected(bl);
        }
        if (this.isFocused() && bl && button == 0) {
            int n = MathHelper.floor(mouseX) - this.x;
            if (this.focused) {
                n -= 4;
            }
            String _snowman2 = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
            this.setCursor(this.textRenderer.trimToWidth(_snowman2, n).length() + this.firstCharacterIndex);
            return true;
        }
        return false;
    }

    public void setSelected(boolean selected) {
        super.setFocused(selected);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int _snowman5;
        int n;
        if (!this.isVisible()) {
            return;
        }
        if (this.hasBorder()) {
            n = this.isFocused() ? -1 : -6250336;
            TextFieldWidget.fill(matrices, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, n);
            TextFieldWidget.fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
        }
        n = this.editable ? this.editableColor : this.uneditableColor;
        _snowman = this.selectionStart - this.firstCharacterIndex;
        _snowman = this.selectionEnd - this.firstCharacterIndex;
        String _snowman2 = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
        boolean _snowman3 = _snowman >= 0 && _snowman <= _snowman2.length();
        boolean _snowman4 = this.isFocused() && this.focusedTicks / 6 % 2 == 0 && _snowman3;
        _snowman = this.focused ? this.x + 4 : this.x;
        _snowman = this.focused ? this.y + (this.height - 8) / 2 : this.y;
        _snowman5 = _snowman;
        if (_snowman > _snowman2.length()) {
            _snowman = _snowman2.length();
        }
        if (!_snowman2.isEmpty()) {
            String string = _snowman3 ? _snowman2.substring(0, _snowman) : _snowman2;
            _snowman5 = this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(string, this.firstCharacterIndex), (float)_snowman5, (float)_snowman, n);
        }
        boolean bl = this.selectionStart < this.text.length() || this.text.length() >= this.getMaxLength();
        int _snowman6 = _snowman5;
        if (!_snowman3) {
            _snowman6 = _snowman > 0 ? _snowman + this.width : _snowman;
        } else if (bl) {
            --_snowman6;
            --_snowman5;
        }
        if (!_snowman2.isEmpty() && _snowman3 && _snowman < _snowman2.length()) {
            this.textRenderer.drawWithShadow(matrices, this.renderTextProvider.apply(_snowman2.substring(_snowman), this.selectionStart), (float)_snowman5, (float)_snowman, n);
        }
        if (!bl && this.suggestion != null) {
            this.textRenderer.drawWithShadow(matrices, this.suggestion, (float)(_snowman6 - 1), (float)_snowman, -8355712);
        }
        if (_snowman4) {
            if (bl) {
                DrawableHelper.fill(matrices, _snowman6, _snowman - 1, _snowman6 + 1, _snowman + 1 + this.textRenderer.fontHeight, -3092272);
            } else {
                this.textRenderer.drawWithShadow(matrices, "_", (float)_snowman6, (float)_snowman, n);
            }
        }
        if (_snowman != _snowman) {
            int n2 = _snowman + this.textRenderer.getWidth(_snowman2.substring(0, _snowman));
            this.drawSelectionHighlight(_snowman6, _snowman - 1, n2 - 1, _snowman + 1 + this.textRenderer.fontHeight);
        }
    }

    private void drawSelectionHighlight(int x1, int y1, int x2, int y2) {
        int n;
        if (x1 < x2) {
            n = x1;
            x1 = x2;
            x2 = n;
        }
        if (y1 < y2) {
            n = y1;
            y1 = y2;
            y2 = n;
        }
        if (x2 > this.x + this.width) {
            x2 = this.x + this.width;
        }
        if (x1 > this.x + this.width) {
            x1 = this.x + this.width;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        RenderSystem.color4f(0.0f, 0.0f, 255.0f, 255.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        _snowman2.begin(7, VertexFormats.POSITION);
        _snowman2.vertex(x1, y2, 0.0).next();
        _snowman2.vertex(x2, y2, 0.0).next();
        _snowman2.vertex(x2, y1, 0.0).next();
        _snowman2.vertex(x1, y1, 0.0).next();
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        if (this.text.length() > maxLength) {
            this.text = this.text.substring(0, maxLength);
            this.onChanged(this.text);
        }
    }

    private int getMaxLength() {
        return this.maxLength;
    }

    public int getCursor() {
        return this.selectionStart;
    }

    private boolean hasBorder() {
        return this.focused;
    }

    public void setHasBorder(boolean hasBorder) {
        this.focused = hasBorder;
    }

    public void setEditableColor(int color) {
        this.editableColor = color;
    }

    public void setUneditableColor(int color) {
        this.uneditableColor = color;
    }

    @Override
    public boolean changeFocus(boolean lookForwards) {
        if (!this.visible || !this.editable) {
            return false;
        }
        return super.changeFocus(lookForwards);
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return this.visible && mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + this.height);
    }

    @Override
    protected void onFocusedChanged(boolean bl) {
        if (bl) {
            this.focusedTicks = 0;
        }
    }

    private boolean isEditable() {
        return this.editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public int getInnerWidth() {
        return this.hasBorder() ? this.width - 8 : this.width;
    }

    public void setSelectionEnd(int n) {
        _snowman = this.text.length();
        this.selectionEnd = MathHelper.clamp(n, 0, _snowman);
        if (this.textRenderer != null) {
            if (this.firstCharacterIndex > _snowman) {
                this.firstCharacterIndex = _snowman;
            }
            _snowman = this.getInnerWidth();
            String string = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), _snowman);
            int _snowman2 = string.length() + this.firstCharacterIndex;
            if (this.selectionEnd == this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.textRenderer.trimToWidth(this.text, _snowman, true).length();
            }
            if (this.selectionEnd > _snowman2) {
                this.firstCharacterIndex += this.selectionEnd - _snowman2;
            } else if (this.selectionEnd <= this.firstCharacterIndex) {
                this.firstCharacterIndex -= this.firstCharacterIndex - this.selectionEnd;
            }
            this.firstCharacterIndex = MathHelper.clamp(this.firstCharacterIndex, 0, _snowman);
        }
    }

    public void setFocusUnlocked(boolean focusUnlocked) {
        this.focusUnlocked = focusUnlocked;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setSuggestion(@Nullable String suggestion) {
        this.suggestion = suggestion;
    }

    public int getCharacterX(int index) {
        if (index > this.text.length()) {
            return this.x;
        }
        return this.x + this.textRenderer.getWidth(this.text.substring(0, index));
    }

    public void setX(int x) {
        this.x = x;
    }
}

