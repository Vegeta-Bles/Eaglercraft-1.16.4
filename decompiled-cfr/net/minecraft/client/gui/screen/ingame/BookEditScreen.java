/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.mutable.MutableBoolean
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.Rect2i;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;

public class BookEditScreen
extends Screen {
    private static final Text field_25893 = new TranslatableText("book.editTitle");
    private static final Text field_25894 = new TranslatableText("book.finalizeWarning");
    private static final OrderedText field_25895 = OrderedText.styledString("_", Style.EMPTY.withColor(Formatting.BLACK));
    private static final OrderedText field_25896 = OrderedText.styledString("_", Style.EMPTY.withColor(Formatting.GRAY));
    private final PlayerEntity player;
    private final ItemStack itemStack;
    private boolean dirty;
    private boolean signing;
    private int tickCounter;
    private int currentPage;
    private final List<String> pages = Lists.newArrayList();
    private String title = "";
    private final SelectionManager field_24269 = new SelectionManager(this::getCurrentPageContent, this::setPageContent, this::method_27595, this::method_27584, string -> string.length() < 1024 && this.textRenderer.getStringBoundedHeight((String)string, 114) <= 128);
    private final SelectionManager field_24270 = new SelectionManager(() -> this.title, string -> {
        this.title = string;
    }, this::method_27595, this::method_27584, string -> string.length() < 16);
    private long lastClickTime;
    private int lastClickIndex = -1;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    private ButtonWidget doneButton;
    private ButtonWidget signButton;
    private ButtonWidget finalizeButton;
    private ButtonWidget cancelButton;
    private final Hand hand;
    @Nullable
    private PageContent pageContent = PageContent.method_27599();
    private Text field_25891 = LiteralText.EMPTY;
    private final Text field_25892;

    public BookEditScreen(PlayerEntity playerEntity2, ItemStack itemStack, Hand hand) {
        super(NarratorManager.EMPTY);
        PlayerEntity playerEntity2;
        this.player = playerEntity2;
        this.itemStack = itemStack;
        this.hand = hand;
        CompoundTag compoundTag = itemStack.getTag();
        if (compoundTag != null) {
            ListTag listTag = compoundTag.getList("pages", 8).copy();
            for (int i = 0; i < listTag.size(); ++i) {
                this.pages.add(listTag.getString(i));
            }
        }
        if (this.pages.isEmpty()) {
            this.pages.add("");
        }
        this.field_25892 = new TranslatableText("book.byAuthor", playerEntity2.getName()).formatted(Formatting.DARK_GRAY);
    }

    private void method_27584(String string) {
        if (this.client != null) {
            SelectionManager.setClipboard(this.client, string);
        }
    }

    private String method_27595() {
        return this.client != null ? SelectionManager.getClipboard(this.client) : "";
    }

    private int countPages() {
        return this.pages.size();
    }

    @Override
    public void tick() {
        super.tick();
        ++this.tickCounter;
    }

    @Override
    protected void init() {
        this.invalidatePageContent();
        this.client.keyboard.setRepeatEvents(true);
        this.signButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.signButton"), buttonWidget -> {
            this.signing = true;
            this.updateButtons();
        }));
        this.doneButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.DONE, buttonWidget -> {
            this.client.openScreen(null);
            this.finalizeBook(false);
        }));
        this.finalizeButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.finalizeButton"), buttonWidget -> {
            if (this.signing) {
                this.finalizeBook(true);
                this.client.openScreen(null);
            }
        }));
        this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.CANCEL, buttonWidget -> {
            if (this.signing) {
                this.signing = false;
            }
            this.updateButtons();
        }));
        int n = (this.width - 192) / 2;
        _snowman = 2;
        this.nextPageButton = this.addButton(new PageTurnWidget(n + 116, 159, true, buttonWidget -> this.openNextPage(), true));
        this.previousPageButton = this.addButton(new PageTurnWidget(n + 43, 159, false, buttonWidget -> this.openPreviousPage(), true));
        this.updateButtons();
    }

    private void openPreviousPage() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }
        this.updateButtons();
        this.method_27872();
    }

    private void openNextPage() {
        if (this.currentPage < this.countPages() - 1) {
            ++this.currentPage;
        } else {
            this.appendNewPage();
            if (this.currentPage < this.countPages() - 1) {
                ++this.currentPage;
            }
        }
        this.updateButtons();
        this.method_27872();
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    private void updateButtons() {
        this.previousPageButton.visible = !this.signing && this.currentPage > 0;
        this.nextPageButton.visible = !this.signing;
        this.doneButton.visible = !this.signing;
        this.signButton.visible = !this.signing;
        this.cancelButton.visible = this.signing;
        this.finalizeButton.visible = this.signing;
        this.finalizeButton.active = !this.title.trim().isEmpty();
    }

    private void removeEmptyPages() {
        ListIterator<String> listIterator = this.pages.listIterator(this.pages.size());
        while (listIterator.hasPrevious() && listIterator.previous().isEmpty()) {
            listIterator.remove();
        }
    }

    private void finalizeBook(boolean signBook) {
        if (!this.dirty) {
            return;
        }
        this.removeEmptyPages();
        ListTag listTag = new ListTag();
        this.pages.stream().map(StringTag::of).forEach(listTag::add);
        if (!this.pages.isEmpty()) {
            this.itemStack.putSubTag("pages", listTag);
        }
        if (signBook) {
            this.itemStack.putSubTag("author", StringTag.of(this.player.getGameProfile().getName()));
            this.itemStack.putSubTag("title", StringTag.of(this.title.trim()));
        }
        int _snowman2 = this.hand == Hand.MAIN_HAND ? this.player.inventory.selectedSlot : 40;
        this.client.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(this.itemStack, signBook, _snowman2));
    }

    private void appendNewPage() {
        if (this.countPages() >= 100) {
            return;
        }
        this.pages.add("");
        this.dirty = true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (this.signing) {
            return this.keyPressedSignMode(keyCode, scanCode, modifiers);
        }
        boolean bl = this.method_27592(keyCode, scanCode, modifiers);
        if (bl) {
            this.invalidatePageContent();
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (super.charTyped(chr, keyCode)) {
            return true;
        }
        if (this.signing) {
            boolean bl = this.field_24270.insert(chr);
            if (bl) {
                this.updateButtons();
                this.dirty = true;
                return true;
            }
            return false;
        }
        if (SharedConstants.isValidChar(chr)) {
            this.field_24269.insert(Character.toString(chr));
            this.invalidatePageContent();
            return true;
        }
        return false;
    }

    private boolean method_27592(int n, int n2, int n3) {
        if (Screen.isSelectAll(n)) {
            this.field_24269.selectAll();
            return true;
        }
        if (Screen.isCopy(n)) {
            this.field_24269.copy();
            return true;
        }
        if (Screen.isPaste(n)) {
            this.field_24269.paste();
            return true;
        }
        if (Screen.isCut(n)) {
            this.field_24269.cut();
            return true;
        }
        switch (n) {
            case 259: {
                this.field_24269.delete(-1);
                return true;
            }
            case 261: {
                this.field_24269.delete(1);
                return true;
            }
            case 257: 
            case 335: {
                this.field_24269.insert("\n");
                return true;
            }
            case 263: {
                this.field_24269.moveCursor(-1, Screen.hasShiftDown());
                return true;
            }
            case 262: {
                this.field_24269.moveCursor(1, Screen.hasShiftDown());
                return true;
            }
            case 265: {
                this.method_27597();
                return true;
            }
            case 264: {
                this.method_27598();
                return true;
            }
            case 266: {
                this.previousPageButton.onPress();
                return true;
            }
            case 267: {
                this.nextPageButton.onPress();
                return true;
            }
            case 268: {
                this.moveCursorToTop();
                return true;
            }
            case 269: {
                this.moveCursorToBottom();
                return true;
            }
        }
        return false;
    }

    private void method_27597() {
        this.method_27580(-1);
    }

    private void method_27598() {
        this.method_27580(1);
    }

    private void method_27580(int n) {
        _snowman = this.field_24269.getSelectionStart();
        _snowman = this.getPageContent().method_27601(_snowman, n);
        this.field_24269.method_27560(_snowman, Screen.hasShiftDown());
    }

    private void moveCursorToTop() {
        int n = this.field_24269.getSelectionStart();
        _snowman = this.getPageContent().method_27600(n);
        this.field_24269.method_27560(_snowman, Screen.hasShiftDown());
    }

    private void moveCursorToBottom() {
        PageContent pageContent = this.getPageContent();
        int _snowman2 = this.field_24269.getSelectionStart();
        int _snowman3 = pageContent.method_27604(_snowman2);
        this.field_24269.method_27560(_snowman3, Screen.hasShiftDown());
    }

    private boolean keyPressedSignMode(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case 259: {
                this.field_24270.delete(-1);
                this.updateButtons();
                this.dirty = true;
                return true;
            }
            case 257: 
            case 335: {
                if (!this.title.isEmpty()) {
                    this.finalizeBook(true);
                    this.client.openScreen(null);
                }
                return true;
            }
        }
        return false;
    }

    private String getCurrentPageContent() {
        if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
            return this.pages.get(this.currentPage);
        }
        return "";
    }

    private void setPageContent(String newContent) {
        if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
            this.pages.set(this.currentPage, newContent);
            this.dirty = true;
            this.invalidatePageContent();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.setFocused(null);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(BookScreen.BOOK_TEXTURE);
        int n = (this.width - 192) / 2;
        _snowman = 2;
        this.drawTexture(matrices, n, 2, 0, 0, 192, 192);
        if (this.signing) {
            boolean bl = this.tickCounter / 6 % 2 == 0;
            OrderedText _snowman2 = OrderedText.concat(OrderedText.styledString(this.title, Style.EMPTY), bl ? field_25895 : field_25896);
            int _snowman3 = this.textRenderer.getWidth(field_25893);
            this.textRenderer.draw(matrices, field_25893, (float)(n + 36 + (114 - _snowman3) / 2), 34.0f, 0);
            int _snowman4 = this.textRenderer.getWidth(_snowman2);
            this.textRenderer.draw(matrices, _snowman2, (float)(n + 36 + (114 - _snowman4) / 2), 50.0f, 0);
            int _snowman5 = this.textRenderer.getWidth(this.field_25892);
            this.textRenderer.draw(matrices, this.field_25892, (float)(n + 36 + (114 - _snowman5) / 2), 60.0f, 0);
            this.textRenderer.drawTrimmed(field_25894, n + 36, 82, 114, 0);
        } else {
            int _snowman6 = this.textRenderer.getWidth(this.field_25891);
            this.textRenderer.draw(matrices, this.field_25891, (float)(n - _snowman6 + 192 - 44), 18.0f, 0);
            PageContent _snowman7 = this.getPageContent();
            for (Line line : _snowman7.lines) {
                this.textRenderer.draw(matrices, line.text, (float)line.x, (float)line.y, -16777216);
            }
            this.method_27588(_snowman7.field_24277);
            this.method_27581(matrices, _snowman7.position, _snowman7.field_24274);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void method_27581(MatrixStack matrixStack, Position position2, boolean bl) {
        if (this.tickCounter / 6 % 2 == 0) {
            Position position2 = this.method_27590(position2);
            if (!bl) {
                DrawableHelper.fill(matrixStack, position2.x, position2.y - 1, position2.x + 1, position2.y + this.textRenderer.fontHeight, -16777216);
            } else {
                this.textRenderer.draw(matrixStack, "_", (float)position2.x, (float)position2.y, 0);
            }
        }
    }

    private void method_27588(Rect2i[] rect2iArray) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        RenderSystem.color4f(0.0f, 0.0f, 255.0f, 255.0f);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        _snowman2.begin(7, VertexFormats.POSITION);
        for (Rect2i rect2i : rect2iArray) {
            int n = rect2i.getX();
            _snowman = rect2i.getY();
            _snowman = n + rect2i.getWidth();
            _snowman = _snowman + rect2i.getHeight();
            _snowman2.vertex(n, _snowman, 0.0).next();
            _snowman2.vertex(_snowman, _snowman, 0.0).next();
            _snowman2.vertex(_snowman, _snowman, 0.0).next();
            _snowman2.vertex(n, _snowman, 0.0).next();
        }
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    private Position method_27582(Position position) {
        return new Position(position.x - (this.width - 192) / 2 - 36, position.y - 32);
    }

    private Position method_27590(Position position) {
        return new Position(position.x + (this.width - 192) / 2 + 36, position.y + 32);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (button == 0) {
            long l = Util.getMeasuringTimeMs();
            PageContent _snowman2 = this.getPageContent();
            int _snowman3 = _snowman2.method_27602(this.textRenderer, this.method_27582(new Position((int)mouseX, (int)mouseY)));
            if (_snowman3 >= 0) {
                if (_snowman3 == this.lastClickIndex && l - this.lastClickTime < 250L) {
                    if (!this.field_24269.method_27568()) {
                        this.method_27589(_snowman3);
                    } else {
                        this.field_24269.selectAll();
                    }
                } else {
                    this.field_24269.method_27560(_snowman3, Screen.hasShiftDown());
                }
                this.invalidatePageContent();
            }
            this.lastClickIndex = _snowman3;
            this.lastClickTime = l;
        }
        return true;
    }

    private void method_27589(int n) {
        String string = this.getCurrentPageContent();
        this.field_24269.method_27548(TextHandler.moveCursorByWords(string, -1, n, false), TextHandler.moveCursorByWords(string, 1, n, false));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
            return true;
        }
        if (button == 0) {
            PageContent pageContent = this.getPageContent();
            int _snowman2 = pageContent.method_27602(this.textRenderer, this.method_27582(new Position((int)mouseX, (int)mouseY)));
            this.field_24269.method_27560(_snowman2, true);
            this.invalidatePageContent();
        }
        return true;
    }

    private PageContent getPageContent() {
        if (this.pageContent == null) {
            this.pageContent = this.createPageContent();
            this.field_25891 = new TranslatableText("book.pageIndicator", this.currentPage + 1, this.countPages());
        }
        return this.pageContent;
    }

    private void invalidatePageContent() {
        this.pageContent = null;
    }

    private void method_27872() {
        this.field_24269.moveCaretToEnd();
        this.invalidatePageContent();
    }

    private PageContent createPageContent() {
        Position _snowman10;
        String string = this.getCurrentPageContent();
        if (string.isEmpty()) {
            return PageContent.EMPTY;
        }
        int _snowman2 = this.field_24269.getSelectionStart();
        int _snowman3 = this.field_24269.getSelectionEnd();
        IntArrayList _snowman4 = new IntArrayList();
        ArrayList _snowman5 = Lists.newArrayList();
        MutableInt _snowman6 = new MutableInt();
        MutableBoolean _snowman7 = new MutableBoolean();
        TextHandler _snowman8 = this.textRenderer.getTextHandler();
        _snowman8.wrapLines(string, 114, Style.EMPTY, true, (arg_0, arg_1, arg_2) -> this.method_27586(_snowman6, string, _snowman7, (IntList)_snowman4, _snowman5, arg_0, arg_1, arg_2));
        int[] _snowman9 = _snowman4.toIntArray();
        boolean bl = _snowman = _snowman2 == string.length();
        if (_snowman && _snowman7.isTrue()) {
            _snowman10 = new Position(0, _snowman5.size() * this.textRenderer.fontHeight);
        } else {
            int n = BookEditScreen.method_27591(_snowman9, _snowman2);
            n = this.textRenderer.getWidth(string.substring(_snowman9[n], _snowman2));
            _snowman10 = new Position(n, n * this.textRenderer.fontHeight);
        }
        ArrayList arrayList = Lists.newArrayList();
        if (_snowman2 != _snowman3) {
            int n = Math.min(_snowman2, _snowman3);
            _snowman = Math.max(_snowman2, _snowman3);
            _snowman = BookEditScreen.method_27591(_snowman9, n);
            if (_snowman == (_snowman = BookEditScreen.method_27591(_snowman9, _snowman))) {
                _snowman = _snowman * this.textRenderer.fontHeight;
                _snowman = _snowman9[_snowman];
                arrayList.add(this.method_27585(string, _snowman8, n, _snowman, _snowman, _snowman));
            } else {
                _snowman = _snowman + 1 > _snowman9.length ? string.length() : _snowman9[_snowman + 1];
                arrayList.add(this.method_27585(string, _snowman8, n, _snowman, _snowman * this.textRenderer.fontHeight, _snowman9[_snowman]));
                for (_snowman = _snowman + 1; _snowman < _snowman; ++_snowman) {
                    _snowman = _snowman * this.textRenderer.fontHeight;
                    String string2 = string.substring(_snowman9[_snowman], _snowman9[_snowman + 1]);
                    int _snowman11 = (int)_snowman8.getWidth(string2);
                    arrayList.add(this.method_27583(new Position(0, _snowman), new Position(_snowman11, _snowman + this.textRenderer.fontHeight)));
                }
                arrayList.add(this.method_27585(string, _snowman8, _snowman9[_snowman], _snowman, _snowman * this.textRenderer.fontHeight, _snowman9[_snowman]));
            }
        }
        return new PageContent(string, _snowman10, _snowman, _snowman9, _snowman5.toArray(new Line[0]), arrayList.toArray(new Rect2i[0]));
    }

    private static int method_27591(int[] nArray, int n) {
        _snowman = Arrays.binarySearch(nArray, n);
        if (_snowman < 0) {
            return -(_snowman + 2);
        }
        return _snowman;
    }

    private Rect2i method_27585(String string, TextHandler textHandler, int n, int n2, int n3, int n4) {
        String string2 = string.substring(n4, n);
        _snowman = string.substring(n4, n2);
        Position _snowman2 = new Position((int)textHandler.getWidth(string2), n3);
        Position _snowman3 = new Position((int)textHandler.getWidth(_snowman), n3 + this.textRenderer.fontHeight);
        return this.method_27583(_snowman2, _snowman3);
    }

    private Rect2i method_27583(Position position, Position position2) {
        _snowman = this.method_27590(position);
        _snowman = this.method_27590(position2);
        int n = Math.min(_snowman.x, _snowman.x);
        _snowman = Math.max(_snowman.x, _snowman.x);
        _snowman = Math.min(_snowman.y, _snowman.y);
        _snowman = Math.max(_snowman.y, _snowman.y);
        return new Rect2i(n, _snowman, _snowman - n, _snowman - _snowman);
    }

    private /* synthetic */ void method_27586(MutableInt mutableInt, String string, MutableBoolean mutableBoolean, IntList intList, List list, Style style, int n, int n2) {
        _snowman = mutableInt.getAndIncrement();
        String string2 = string.substring(n, n2);
        mutableBoolean.setValue(string2.endsWith("\n"));
        _snowman = StringUtils.stripEnd((String)string2, (String)" \n");
        int _snowman2 = _snowman * this.textRenderer.fontHeight;
        Position _snowman3 = this.method_27590(new Position(0, _snowman2));
        intList.add(n);
        list.add(new Line(style, _snowman, _snowman3.x, _snowman3.y));
    }

    static class PageContent {
        private static final PageContent EMPTY = new PageContent("", new Position(0, 0), true, new int[]{0}, new Line[]{new Line(Style.EMPTY, "", 0, 0)}, new Rect2i[0]);
        private final String pageContent;
        private final Position position;
        private final boolean field_24274;
        private final int[] field_24275;
        private final Line[] lines;
        private final Rect2i[] field_24277;

        public PageContent(String pageContent, Position position, boolean bl, int[] nArray, Line[] lines, Rect2i[] rect2iArray) {
            this.pageContent = pageContent;
            this.position = position;
            this.field_24274 = bl;
            this.field_24275 = nArray;
            this.lines = lines;
            this.field_24277 = rect2iArray;
        }

        public int method_27602(TextRenderer textRenderer, Position position) {
            int n = position.y / textRenderer.fontHeight;
            if (n < 0) {
                return 0;
            }
            if (n >= this.lines.length) {
                return this.pageContent.length();
            }
            Line _snowman2 = this.lines[n];
            return this.field_24275[n] + textRenderer.getTextHandler().getTrimmedLength(_snowman2.content, position.x, _snowman2.style);
        }

        public int method_27601(int n, int n2) {
            _snowman = BookEditScreen.method_27591(this.field_24275, n);
            _snowman = _snowman + n2;
            if (0 <= _snowman && _snowman < this.field_24275.length) {
                _snowman = n - this.field_24275[_snowman];
                _snowman = this.lines[_snowman].content.length();
                _snowman = this.field_24275[_snowman] + Math.min(_snowman, _snowman);
            } else {
                _snowman = n;
            }
            return _snowman;
        }

        public int method_27600(int n) {
            _snowman = BookEditScreen.method_27591(this.field_24275, n);
            return this.field_24275[_snowman];
        }

        public int method_27604(int n) {
            _snowman = BookEditScreen.method_27591(this.field_24275, n);
            return this.field_24275[_snowman] + this.lines[_snowman].content.length();
        }
    }

    static class Line {
        private final Style style;
        private final String content;
        private final Text text;
        private final int x;
        private final int y;

        public Line(Style style, String content, int x, int y) {
            this.style = style;
            this.content = content;
            this.x = x;
            this.y = y;
            this.text = new LiteralText(content).setStyle(style);
        }
    }

    static class Position {
        public final int x;
        public final int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

