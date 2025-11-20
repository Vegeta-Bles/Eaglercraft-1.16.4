package net.minecraft.client.util;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class SelectionManager {
   private final Supplier<String> stringGetter;
   private final Consumer<String> stringSetter;
   private final Supplier<String> clipboardGetter;
   private final Consumer<String> clipboardSetter;
   private final Predicate<String> stringFilter;
   private int selectionStart;
   private int selectionEnd;

   public SelectionManager(Supplier<String> _snowman, Consumer<String> _snowman, Supplier<String> _snowman, Consumer<String> _snowman, Predicate<String> _snowman) {
      this.stringGetter = _snowman;
      this.stringSetter = _snowman;
      this.clipboardGetter = _snowman;
      this.clipboardSetter = _snowman;
      this.stringFilter = _snowman;
      this.moveCaretToEnd();
   }

   public static Supplier<String> makeClipboardGetter(MinecraftClient client) {
      return () -> getClipboard(client);
   }

   public static String getClipboard(MinecraftClient client) {
      return Formatting.strip(client.keyboard.getClipboard().replaceAll("\\r", ""));
   }

   public static Consumer<String> makeClipboardSetter(MinecraftClient client) {
      return _snowmanx -> setClipboard(client, _snowmanx);
   }

   public static void setClipboard(MinecraftClient client, String _snowman) {
      client.keyboard.setClipboard(_snowman);
   }

   public boolean insert(char c) {
      if (SharedConstants.isValidChar(c)) {
         this.insert(this.stringGetter.get(), Character.toString(c));
      }

      return true;
   }

   public boolean handleSpecialKey(int keyCode) {
      if (Screen.isSelectAll(keyCode)) {
         this.selectAll();
         return true;
      } else if (Screen.isCopy(keyCode)) {
         this.copy();
         return true;
      } else if (Screen.isPaste(keyCode)) {
         this.paste();
         return true;
      } else if (Screen.isCut(keyCode)) {
         this.cut();
         return true;
      } else if (keyCode == 259) {
         this.delete(-1);
         return true;
      } else {
         if (keyCode == 261) {
            this.delete(1);
         } else {
            if (keyCode == 263) {
               if (Screen.hasControlDown()) {
                  this.moveCursorPastWord(-1, Screen.hasShiftDown());
               } else {
                  this.moveCursor(-1, Screen.hasShiftDown());
               }

               return true;
            }

            if (keyCode == 262) {
               if (Screen.hasControlDown()) {
                  this.moveCursorPastWord(1, Screen.hasShiftDown());
               } else {
                  this.moveCursor(1, Screen.hasShiftDown());
               }

               return true;
            }

            if (keyCode == 268) {
               this.method_27553(Screen.hasShiftDown());
               return true;
            }

            if (keyCode == 269) {
               this.method_27558(Screen.hasShiftDown());
               return true;
            }
         }

         return false;
      }
   }

   private int method_27567(int _snowman) {
      return MathHelper.clamp(_snowman, 0, this.stringGetter.get().length());
   }

   private void insert(String string, String insertion) {
      if (this.selectionEnd != this.selectionStart) {
         string = this.deleteSelectedText(string);
      }

      this.selectionStart = MathHelper.clamp(this.selectionStart, 0, string.length());
      String _snowman = new StringBuilder(string).insert(this.selectionStart, insertion).toString();
      if (this.stringFilter.test(_snowman)) {
         this.stringSetter.accept(_snowman);
         this.selectionEnd = this.selectionStart = Math.min(_snowman.length(), this.selectionStart + insertion.length());
      }
   }

   public void insert(String _snowman) {
      this.insert(this.stringGetter.get(), _snowman);
   }

   private void updateSelectionRange(boolean shiftDown) {
      if (!shiftDown) {
         this.selectionEnd = this.selectionStart;
      }
   }

   public void moveCursor(int offset, boolean shiftDown) {
      this.selectionStart = Util.moveCursor(this.stringGetter.get(), this.selectionStart, offset);
      this.updateSelectionRange(shiftDown);
   }

   public void moveCursorPastWord(int offset, boolean shiftDown) {
      this.selectionStart = TextHandler.moveCursorByWords(this.stringGetter.get(), offset, this.selectionStart, true);
      this.updateSelectionRange(shiftDown);
   }

   public void delete(int cursorOffset) {
      String _snowman = this.stringGetter.get();
      if (!_snowman.isEmpty()) {
         String _snowmanx;
         if (this.selectionEnd != this.selectionStart) {
            _snowmanx = this.deleteSelectedText(_snowman);
         } else {
            int _snowmanxx = Util.moveCursor(_snowman, this.selectionStart, cursorOffset);
            int _snowmanxxx = Math.min(_snowmanxx, this.selectionStart);
            int _snowmanxxxx = Math.max(_snowmanxx, this.selectionStart);
            _snowmanx = new StringBuilder(_snowman).delete(_snowmanxxx, _snowmanxxxx).toString();
            if (cursorOffset < 0) {
               this.selectionEnd = this.selectionStart = _snowmanxxx;
            }
         }

         this.stringSetter.accept(_snowmanx);
      }
   }

   public void cut() {
      String _snowman = this.stringGetter.get();
      this.clipboardSetter.accept(this.getSelectedText(_snowman));
      this.stringSetter.accept(this.deleteSelectedText(_snowman));
   }

   public void paste() {
      this.insert(this.stringGetter.get(), this.clipboardGetter.get());
      this.selectionEnd = this.selectionStart;
   }

   public void copy() {
      this.clipboardSetter.accept(this.getSelectedText(this.stringGetter.get()));
   }

   public void selectAll() {
      this.selectionEnd = 0;
      this.selectionStart = this.stringGetter.get().length();
   }

   private String getSelectedText(String _snowman) {
      int _snowmanx = Math.min(this.selectionStart, this.selectionEnd);
      int _snowmanxx = Math.max(this.selectionStart, this.selectionEnd);
      return _snowman.substring(_snowmanx, _snowmanxx);
   }

   private String deleteSelectedText(String _snowman) {
      if (this.selectionEnd == this.selectionStart) {
         return _snowman;
      } else {
         int _snowmanx = Math.min(this.selectionStart, this.selectionEnd);
         int _snowmanxx = Math.max(this.selectionStart, this.selectionEnd);
         String _snowmanxxx = _snowman.substring(0, _snowmanx) + _snowman.substring(_snowmanxx);
         this.selectionEnd = this.selectionStart = _snowmanx;
         return _snowmanxxx;
      }
   }

   private void method_27553(boolean _snowman) {
      this.selectionStart = 0;
      this.updateSelectionRange(_snowman);
   }

   public void moveCaretToEnd() {
      this.method_27558(false);
   }

   private void method_27558(boolean _snowman) {
      this.selectionStart = this.stringGetter.get().length();
      this.updateSelectionRange(_snowman);
   }

   public int getSelectionStart() {
      return this.selectionStart;
   }

   public void method_27560(int _snowman, boolean _snowman) {
      this.selectionStart = this.method_27567(_snowman);
      this.updateSelectionRange(_snowman);
   }

   public int getSelectionEnd() {
      return this.selectionEnd;
   }

   public void method_27548(int _snowman, int _snowman) {
      int _snowmanxx = this.stringGetter.get().length();
      this.selectionStart = MathHelper.clamp(_snowman, 0, _snowmanxx);
      this.selectionEnd = MathHelper.clamp(_snowman, 0, _snowmanxx);
   }

   public boolean method_27568() {
      return this.selectionStart != this.selectionEnd;
   }
}
