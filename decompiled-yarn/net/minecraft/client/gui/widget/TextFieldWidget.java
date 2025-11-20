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

public class TextFieldWidget extends AbstractButtonWidget implements Drawable, Element {
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
   private int editableColor = 14737632;
   private int uneditableColor = 7368816;
   private String suggestion;
   private Consumer<String> changedListener;
   private Predicate<String> textPredicate = Objects::nonNull;
   private BiFunction<String, Integer, OrderedText> renderTextProvider = (_snowmanx, _snowmanxx) -> OrderedText.styledString(_snowmanx, Style.EMPTY);

   public TextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, Text _snowman) {
      this(textRenderer, x, y, width, height, null, _snowman);
   }

   public TextFieldWidget(TextRenderer textRenderer, int x, int y, int width, int height, @Nullable TextFieldWidget copyFrom, Text _snowman) {
      super(x, y, width, height, _snowman);
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
      this.focusedTicks++;
   }

   @Override
   protected MutableText getNarrationMessage() {
      Text _snowman = this.getMessage();
      return new TranslatableText("gui.narrate.editBox", _snowman, this.text);
   }

   public void setText(String text) {
      if (this.textPredicate.test(text)) {
         if (text.length() > this.maxLength) {
            this.text = text.substring(0, this.maxLength);
         } else {
            this.text = text;
         }

         this.setCursorToEnd();
         this.setSelectionEnd(this.selectionStart);
         this.onChanged(text);
      }
   }

   public String getText() {
      return this.text;
   }

   public String getSelectedText() {
      int _snowman = this.selectionStart < this.selectionEnd ? this.selectionStart : this.selectionEnd;
      int _snowmanx = this.selectionStart < this.selectionEnd ? this.selectionEnd : this.selectionStart;
      return this.text.substring(_snowman, _snowmanx);
   }

   public void setTextPredicate(Predicate<String> textPredicate) {
      this.textPredicate = textPredicate;
   }

   public void write(String _snowman) {
      int _snowmanx = this.selectionStart < this.selectionEnd ? this.selectionStart : this.selectionEnd;
      int _snowmanxx = this.selectionStart < this.selectionEnd ? this.selectionEnd : this.selectionStart;
      int _snowmanxxx = this.maxLength - this.text.length() - (_snowmanx - _snowmanxx);
      String _snowmanxxxx = SharedConstants.stripInvalidChars(_snowman);
      int _snowmanxxxxx = _snowmanxxxx.length();
      if (_snowmanxxx < _snowmanxxxxx) {
         _snowmanxxxx = _snowmanxxxx.substring(0, _snowmanxxx);
         _snowmanxxxxx = _snowmanxxx;
      }

      String _snowmanxxxxxx = new StringBuilder(this.text).replace(_snowmanx, _snowmanxx, _snowmanxxxx).toString();
      if (this.textPredicate.test(_snowmanxxxxxx)) {
         this.text = _snowmanxxxxxx;
         this.setSelectionStart(_snowmanx + _snowmanxxxxx);
         this.setSelectionEnd(this.selectionStart);
         this.onChanged(this.text);
      }
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
      if (!this.text.isEmpty()) {
         if (this.selectionEnd != this.selectionStart) {
            this.write("");
         } else {
            this.eraseCharacters(this.getWordSkipPosition(wordOffset) - this.selectionStart);
         }
      }
   }

   public void eraseCharacters(int characterOffset) {
      if (!this.text.isEmpty()) {
         if (this.selectionEnd != this.selectionStart) {
            this.write("");
         } else {
            int _snowman = this.method_27537(characterOffset);
            int _snowmanx = Math.min(_snowman, this.selectionStart);
            int _snowmanxx = Math.max(_snowman, this.selectionStart);
            if (_snowmanx != _snowmanxx) {
               String _snowmanxxx = new StringBuilder(this.text).delete(_snowmanx, _snowmanxx).toString();
               if (this.textPredicate.test(_snowmanxxx)) {
                  this.text = _snowmanxxx;
                  this.setCursor(_snowmanx);
               }
            }
         }
      }
   }

   public int getWordSkipPosition(int wordOffset) {
      return this.getWordSkipPosition(wordOffset, this.getCursor());
   }

   private int getWordSkipPosition(int wordOffset, int cursorPosition) {
      return this.getWordSkipPosition(wordOffset, cursorPosition, true);
   }

   private int getWordSkipPosition(int wordOffset, int cursorPosition, boolean skipOverSpaces) {
      int _snowman = cursorPosition;
      boolean _snowmanx = wordOffset < 0;
      int _snowmanxx = Math.abs(wordOffset);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         if (!_snowmanx) {
            int _snowmanxxxx = this.text.length();
            _snowman = this.text.indexOf(32, _snowman);
            if (_snowman == -1) {
               _snowman = _snowmanxxxx;
            } else {
               while (skipOverSpaces && _snowman < _snowmanxxxx && this.text.charAt(_snowman) == ' ') {
                  _snowman++;
               }
            }
         } else {
            while (skipOverSpaces && _snowman > 0 && this.text.charAt(_snowman - 1) == ' ') {
               _snowman--;
            }

            while (_snowman > 0 && this.text.charAt(_snowman - 1) != ' ') {
               _snowman--;
            }
         }
      }

      return _snowman;
   }

   public void moveCursor(int offset) {
      this.setCursor(this.method_27537(offset));
   }

   private int method_27537(int _snowman) {
      return Util.moveCursor(this.text, this.selectionStart, _snowman);
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
      } else {
         this.selecting = Screen.hasShiftDown();
         if (Screen.isSelectAll(keyCode)) {
            this.setCursorToEnd();
            this.setSelectionEnd(0);
            return true;
         } else if (Screen.isCopy(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            return true;
         } else if (Screen.isPaste(keyCode)) {
            if (this.editable) {
               this.write(MinecraftClient.getInstance().keyboard.getClipboard());
            }

            return true;
         } else if (Screen.isCut(keyCode)) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.getSelectedText());
            if (this.editable) {
               this.write("");
            }

            return true;
         } else {
            switch (keyCode) {
               case 259:
                  if (this.editable) {
                     this.selecting = false;
                     this.erase(-1);
                     this.selecting = Screen.hasShiftDown();
                  }

                  return true;
               case 260:
               case 264:
               case 265:
               case 266:
               case 267:
               default:
                  return false;
               case 261:
                  if (this.editable) {
                     this.selecting = false;
                     this.erase(1);
                     this.selecting = Screen.hasShiftDown();
                  }

                  return true;
               case 262:
                  if (Screen.hasControlDown()) {
                     this.setCursor(this.getWordSkipPosition(1));
                  } else {
                     this.moveCursor(1);
                  }

                  return true;
               case 263:
                  if (Screen.hasControlDown()) {
                     this.setCursor(this.getWordSkipPosition(-1));
                  } else {
                     this.moveCursor(-1);
                  }

                  return true;
               case 268:
                  this.setCursorToStart();
                  return true;
               case 269:
                  this.setCursorToEnd();
                  return true;
            }
         }
      }
   }

   public boolean isActive() {
      return this.isVisible() && this.isFocused() && this.isEditable();
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      if (!this.isActive()) {
         return false;
      } else if (SharedConstants.isValidChar(chr)) {
         if (this.editable) {
            this.write(Character.toString(chr));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.isVisible()) {
         return false;
      } else {
         boolean _snowman = mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + this.height);
         if (this.focusUnlocked) {
            this.setSelected(_snowman);
         }

         if (this.isFocused() && _snowman && button == 0) {
            int _snowmanx = MathHelper.floor(mouseX) - this.x;
            if (this.focused) {
               _snowmanx -= 4;
            }

            String _snowmanxx = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
            this.setCursor(this.textRenderer.trimToWidth(_snowmanxx, _snowmanx).length() + this.firstCharacterIndex);
            return true;
         } else {
            return false;
         }
      }
   }

   public void setSelected(boolean selected) {
      super.setFocused(selected);
   }

   @Override
   public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (this.isVisible()) {
         if (this.hasBorder()) {
            int _snowman = this.isFocused() ? -1 : -6250336;
            fill(matrices, this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, _snowman);
            fill(matrices, this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
         }

         int _snowman = this.editable ? this.editableColor : this.uneditableColor;
         int _snowmanx = this.selectionStart - this.firstCharacterIndex;
         int _snowmanxx = this.selectionEnd - this.firstCharacterIndex;
         String _snowmanxxx = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), this.getInnerWidth());
         boolean _snowmanxxxx = _snowmanx >= 0 && _snowmanx <= _snowmanxxx.length();
         boolean _snowmanxxxxx = this.isFocused() && this.focusedTicks / 6 % 2 == 0 && _snowmanxxxx;
         int _snowmanxxxxxx = this.focused ? this.x + 4 : this.x;
         int _snowmanxxxxxxx = this.focused ? this.y + (this.height - 8) / 2 : this.y;
         int _snowmanxxxxxxxx = _snowmanxxxxxx;
         if (_snowmanxx > _snowmanxxx.length()) {
            _snowmanxx = _snowmanxxx.length();
         }

         if (!_snowmanxxx.isEmpty()) {
            String _snowmanxxxxxxxxx = _snowmanxxxx ? _snowmanxxx.substring(0, _snowmanx) : _snowmanxxx;
            _snowmanxxxxxxxx = this.textRenderer
               .drawWithShadow(matrices, this.renderTextProvider.apply(_snowmanxxxxxxxxx, this.firstCharacterIndex), (float)_snowmanxxxxxx, (float)_snowmanxxxxxxx, _snowman);
         }

         boolean _snowmanxxxxxxxxx = this.selectionStart < this.text.length() || this.text.length() >= this.getMaxLength();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx;
         if (!_snowmanxxxx) {
            _snowmanxxxxxxxxxx = _snowmanx > 0 ? _snowmanxxxxxx + this.width : _snowmanxxxxxx;
         } else if (_snowmanxxxxxxxxx) {
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - 1;
            _snowmanxxxxxxxx--;
         }

         if (!_snowmanxxx.isEmpty() && _snowmanxxxx && _snowmanx < _snowmanxxx.length()) {
            this.textRenderer
               .drawWithShadow(matrices, this.renderTextProvider.apply(_snowmanxxx.substring(_snowmanx), this.selectionStart), (float)_snowmanxxxxxxxx, (float)_snowmanxxxxxxx, _snowman);
         }

         if (!_snowmanxxxxxxxxx && this.suggestion != null) {
            this.textRenderer.drawWithShadow(matrices, this.suggestion, (float)(_snowmanxxxxxxxxxx - 1), (float)_snowmanxxxxxxx, -8355712);
         }

         if (_snowmanxxxxx) {
            if (_snowmanxxxxxxxxx) {
               DrawableHelper.fill(matrices, _snowmanxxxxxxxxxx, _snowmanxxxxxxx - 1, _snowmanxxxxxxxxxx + 1, _snowmanxxxxxxx + 1 + 9, -3092272);
            } else {
               this.textRenderer.drawWithShadow(matrices, "_", (float)_snowmanxxxxxxxxxx, (float)_snowmanxxxxxxx, _snowman);
            }
         }

         if (_snowmanxx != _snowmanx) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxx + this.textRenderer.getWidth(_snowmanxxx.substring(0, _snowmanxx));
            this.drawSelectionHighlight(_snowmanxxxxxxxxxx, _snowmanxxxxxxx - 1, _snowmanxxxxxxxxxxx - 1, _snowmanxxxxxxx + 1 + 9);
         }
      }
   }

   private void drawSelectionHighlight(int x1, int y1, int x2, int y2) {
      if (x1 < x2) {
         int _snowman = x1;
         x1 = x2;
         x2 = _snowman;
      }

      if (y1 < y2) {
         int _snowman = y1;
         y1 = y2;
         y2 = _snowman;
      }

      if (x2 > this.x + this.width) {
         x2 = this.x + this.width;
      }

      if (x1 > this.x + this.width) {
         x1 = this.x + this.width;
      }

      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      _snowmanx.begin(7, VertexFormats.POSITION);
      _snowmanx.vertex((double)x1, (double)y2, 0.0).next();
      _snowmanx.vertex((double)x2, (double)y2, 0.0).next();
      _snowmanx.vertex((double)x2, (double)y1, 0.0).next();
      _snowmanx.vertex((double)x1, (double)y1, 0.0).next();
      _snowman.draw();
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
      return this.visible && this.editable ? super.changeFocus(lookForwards) : false;
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.visible
         && mouseX >= (double)this.x
         && mouseX < (double)(this.x + this.width)
         && mouseY >= (double)this.y
         && mouseY < (double)(this.y + this.height);
   }

   @Override
   protected void onFocusedChanged(boolean _snowman) {
      if (_snowman) {
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

   public void setSelectionEnd(int _snowman) {
      int _snowmanx = this.text.length();
      this.selectionEnd = MathHelper.clamp(_snowman, 0, _snowmanx);
      if (this.textRenderer != null) {
         if (this.firstCharacterIndex > _snowmanx) {
            this.firstCharacterIndex = _snowmanx;
         }

         int _snowmanxx = this.getInnerWidth();
         String _snowmanxxx = this.textRenderer.trimToWidth(this.text.substring(this.firstCharacterIndex), _snowmanxx);
         int _snowmanxxxx = _snowmanxxx.length() + this.firstCharacterIndex;
         if (this.selectionEnd == this.firstCharacterIndex) {
            this.firstCharacterIndex = this.firstCharacterIndex - this.textRenderer.trimToWidth(this.text, _snowmanxx, true).length();
         }

         if (this.selectionEnd > _snowmanxxxx) {
            this.firstCharacterIndex = this.firstCharacterIndex + (this.selectionEnd - _snowmanxxxx);
         } else if (this.selectionEnd <= this.firstCharacterIndex) {
            this.firstCharacterIndex = this.firstCharacterIndex - (this.firstCharacterIndex - this.selectionEnd);
         }

         this.firstCharacterIndex = MathHelper.clamp(this.firstCharacterIndex, 0, _snowmanx);
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
      return index > this.text.length() ? this.x : this.x + this.textRenderer.getWidth(this.text.substring(0, index));
   }

   public void setX(int x) {
      this.x = x;
   }
}
