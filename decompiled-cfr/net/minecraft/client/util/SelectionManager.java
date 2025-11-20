/*
 * Decompiled with CFR 0.152.
 */
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

    public SelectionManager(Supplier<String> supplier, Consumer<String> consumer, Supplier<String> supplier2, Consumer<String> consumer2, Predicate<String> predicate) {
        this.stringGetter = supplier;
        this.stringSetter = consumer;
        this.clipboardGetter = supplier2;
        this.clipboardSetter = consumer2;
        this.stringFilter = predicate;
        this.moveCaretToEnd();
    }

    public static Supplier<String> makeClipboardGetter(MinecraftClient client) {
        return () -> SelectionManager.getClipboard(client);
    }

    public static String getClipboard(MinecraftClient client) {
        return Formatting.strip(client.keyboard.getClipboard().replaceAll("\\r", ""));
    }

    public static Consumer<String> makeClipboardSetter(MinecraftClient client) {
        return string -> SelectionManager.setClipboard(client, string);
    }

    public static void setClipboard(MinecraftClient client, String string) {
        client.keyboard.setClipboard(string);
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
        }
        if (Screen.isCopy(keyCode)) {
            this.copy();
            return true;
        }
        if (Screen.isPaste(keyCode)) {
            this.paste();
            return true;
        }
        if (Screen.isCut(keyCode)) {
            this.cut();
            return true;
        }
        if (keyCode == 259) {
            this.delete(-1);
            return true;
        }
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

    private int method_27567(int n) {
        return MathHelper.clamp(n, 0, this.stringGetter.get().length());
    }

    private void insert(String string, String insertion) {
        if (this.selectionEnd != this.selectionStart) {
            string = this.deleteSelectedText(string);
        }
        this.selectionStart = MathHelper.clamp(this.selectionStart, 0, string.length());
        String string2 = new StringBuilder(string).insert(this.selectionStart, insertion).toString();
        if (this.stringFilter.test(string2)) {
            this.stringSetter.accept(string2);
            this.selectionEnd = this.selectionStart = Math.min(string2.length(), this.selectionStart + insertion.length());
        }
    }

    public void insert(String string) {
        this.insert(this.stringGetter.get(), string);
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
        String string = this.stringGetter.get();
        if (!string.isEmpty()) {
            String _snowman2;
            if (this.selectionEnd != this.selectionStart) {
                _snowman2 = this.deleteSelectedText(string);
            } else {
                int n = Util.moveCursor(string, this.selectionStart, cursorOffset);
                _snowman = Math.min(n, this.selectionStart);
                _snowman = Math.max(n, this.selectionStart);
                _snowman2 = new StringBuilder(string).delete(_snowman, _snowman).toString();
                if (cursorOffset < 0) {
                    this.selectionEnd = this.selectionStart = _snowman;
                }
            }
            this.stringSetter.accept(_snowman2);
        }
    }

    public void cut() {
        String string = this.stringGetter.get();
        this.clipboardSetter.accept(this.getSelectedText(string));
        this.stringSetter.accept(this.deleteSelectedText(string));
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

    private String getSelectedText(String string) {
        int n = Math.min(this.selectionStart, this.selectionEnd);
        _snowman = Math.max(this.selectionStart, this.selectionEnd);
        return string.substring(n, _snowman);
    }

    private String deleteSelectedText(String string) {
        if (this.selectionEnd == this.selectionStart) {
            return string;
        }
        int n = Math.min(this.selectionStart, this.selectionEnd);
        _snowman = Math.max(this.selectionStart, this.selectionEnd);
        String _snowman2 = string.substring(0, n) + string.substring(_snowman);
        this.selectionEnd = this.selectionStart = n;
        return _snowman2;
    }

    private void method_27553(boolean bl) {
        this.selectionStart = 0;
        this.updateSelectionRange(bl);
    }

    public void moveCaretToEnd() {
        this.method_27558(false);
    }

    private void method_27558(boolean bl) {
        this.selectionStart = this.stringGetter.get().length();
        this.updateSelectionRange(bl);
    }

    public int getSelectionStart() {
        return this.selectionStart;
    }

    public void method_27560(int n, boolean bl) {
        this.selectionStart = this.method_27567(n);
        this.updateSelectionRange(bl);
    }

    public int getSelectionEnd() {
        return this.selectionEnd;
    }

    public void method_27548(int n, int n2) {
        _snowman = this.stringGetter.get().length();
        this.selectionStart = MathHelper.clamp(n, 0, _snowman);
        this.selectionEnd = MathHelper.clamp(n2, 0, _snowman);
    }

    public boolean method_27568() {
        return this.selectionStart != this.selectionEnd;
    }
}

