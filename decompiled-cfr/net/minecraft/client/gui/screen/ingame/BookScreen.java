/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BookScreen
extends Screen {
    public static final Contents EMPTY_PROVIDER = new Contents(){

        public int getPageCount() {
            return 0;
        }

        public StringVisitable getPageUnchecked(int index) {
            return StringVisitable.EMPTY;
        }
    };
    public static final Identifier BOOK_TEXTURE = new Identifier("textures/gui/book.png");
    private Contents contents;
    private int pageIndex;
    private List<OrderedText> cachedPage = Collections.emptyList();
    private int cachedPageIndex = -1;
    private Text pageIndexText = LiteralText.EMPTY;
    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    private final boolean pageTurnSound;

    public BookScreen(Contents pageProvider) {
        this(pageProvider, true);
    }

    public BookScreen() {
        this(EMPTY_PROVIDER, false);
    }

    private BookScreen(Contents contents, boolean playPageTurnSound) {
        super(NarratorManager.EMPTY);
        this.contents = contents;
        this.pageTurnSound = playPageTurnSound;
    }

    public void setPageProvider(Contents pageProvider) {
        this.contents = pageProvider;
        this.pageIndex = MathHelper.clamp(this.pageIndex, 0, pageProvider.getPageCount());
        this.updatePageButtons();
        this.cachedPageIndex = -1;
    }

    public boolean setPage(int index) {
        int n = MathHelper.clamp(index, 0, this.contents.getPageCount() - 1);
        if (n != this.pageIndex) {
            this.pageIndex = n;
            this.updatePageButtons();
            this.cachedPageIndex = -1;
            return true;
        }
        return false;
    }

    protected boolean jumpToPage(int page) {
        return this.setPage(page);
    }

    @Override
    protected void init() {
        this.addCloseButton();
        this.addPageButtons();
    }

    protected void addCloseButton() {
        this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, buttonWidget -> this.client.openScreen(null)));
    }

    protected void addPageButtons() {
        int n = (this.width - 192) / 2;
        _snowman = 2;
        this.nextPageButton = this.addButton(new PageTurnWidget(n + 116, 159, true, buttonWidget -> this.goToNextPage(), this.pageTurnSound));
        this.previousPageButton = this.addButton(new PageTurnWidget(n + 43, 159, false, buttonWidget -> this.goToPreviousPage(), this.pageTurnSound));
        this.updatePageButtons();
    }

    private int getPageCount() {
        return this.contents.getPageCount();
    }

    protected void goToPreviousPage() {
        if (this.pageIndex > 0) {
            --this.pageIndex;
        }
        this.updatePageButtons();
    }

    protected void goToNextPage() {
        if (this.pageIndex < this.getPageCount() - 1) {
            ++this.pageIndex;
        }
        this.updatePageButtons();
    }

    private void updatePageButtons() {
        this.nextPageButton.visible = this.pageIndex < this.getPageCount() - 1;
        this.previousPageButton.visible = this.pageIndex > 0;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        switch (keyCode) {
            case 266: {
                this.previousPageButton.onPress();
                return true;
            }
            case 267: {
                this.nextPageButton.onPress();
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(BOOK_TEXTURE);
        int n = (this.width - 192) / 2;
        _snowman = 2;
        this.drawTexture(matrices, n, 2, 0, 0, 192, 192);
        if (this.cachedPageIndex != this.pageIndex) {
            StringVisitable stringVisitable = this.contents.getPage(this.pageIndex);
            this.cachedPage = this.textRenderer.wrapLines(stringVisitable, 114);
            this.pageIndexText = new TranslatableText("book.pageIndicator", this.pageIndex + 1, Math.max(this.getPageCount(), 1));
        }
        this.cachedPageIndex = this.pageIndex;
        int n2 = this.textRenderer.getWidth(this.pageIndexText);
        this.textRenderer.draw(matrices, this.pageIndexText, (float)(n - n2 + 192 - 44), 18.0f, 0);
        _snowman = Math.min(128 / this.textRenderer.fontHeight, this.cachedPage.size());
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            OrderedText orderedText = this.cachedPage.get(_snowman);
            this.textRenderer.draw(matrices, orderedText, (float)(n + 36), (float)(32 + _snowman * this.textRenderer.fontHeight), 0);
        }
        Style style = this.getTextAt(mouseX, mouseY);
        if (style != null) {
            this.renderTextHoverEffect(matrices, style, mouseX, mouseY);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Style style;
        if (button == 0 && (style = this.getTextAt(mouseX, mouseY)) != null && this.handleTextClick(style)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean handleTextClick(Style style) {
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent == null) {
            return false;
        }
        if (clickEvent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String string = clickEvent.getValue();
            try {
                int n = Integer.parseInt(string) - 1;
                return this.jumpToPage(n);
            }
            catch (Exception exception) {
                return false;
            }
        }
        boolean bl = super.handleTextClick(style);
        if (bl && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.client.openScreen(null);
        }
        return bl;
    }

    @Nullable
    public Style getTextAt(double x, double y) {
        if (this.cachedPage.isEmpty()) {
            return null;
        }
        int n = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0);
        _snowman = MathHelper.floor(y - 2.0 - 30.0);
        if (n < 0 || _snowman < 0) {
            return null;
        }
        _snowman = Math.min(128 / this.textRenderer.fontHeight, this.cachedPage.size());
        if (n <= 114 && _snowman < this.client.textRenderer.fontHeight * _snowman + _snowman) {
            _snowman = _snowman / this.client.textRenderer.fontHeight;
            if (_snowman >= 0 && _snowman < this.cachedPage.size()) {
                OrderedText orderedText = this.cachedPage.get(_snowman);
                return this.client.textRenderer.getTextHandler().getStyleAt(orderedText, n);
            }
            return null;
        }
        return null;
    }

    public static List<String> readPages(CompoundTag tag) {
        ListTag listTag = tag.getList("pages", 8).copy();
        ImmutableList.Builder _snowman2 = ImmutableList.builder();
        for (int i = 0; i < listTag.size(); ++i) {
            _snowman2.add((Object)listTag.getString(i));
        }
        return _snowman2.build();
    }

    public static class WritableBookContents
    implements Contents {
        private final List<String> pages;

        public WritableBookContents(ItemStack stack) {
            this.pages = WritableBookContents.getPages(stack);
        }

        private static List<String> getPages(ItemStack stack) {
            CompoundTag compoundTag = stack.getTag();
            return compoundTag != null ? BookScreen.readPages(compoundTag) : ImmutableList.of();
        }

        @Override
        public int getPageCount() {
            return this.pages.size();
        }

        @Override
        public StringVisitable getPageUnchecked(int index) {
            return StringVisitable.plain(this.pages.get(index));
        }
    }

    public static class WrittenBookContents
    implements Contents {
        private final List<String> pages;

        public WrittenBookContents(ItemStack stack) {
            this.pages = WrittenBookContents.getPages(stack);
        }

        private static List<String> getPages(ItemStack stack) {
            CompoundTag compoundTag = stack.getTag();
            if (compoundTag != null && WrittenBookItem.isValid(compoundTag)) {
                return BookScreen.readPages(compoundTag);
            }
            return ImmutableList.of((Object)Text.Serializer.toJson(new TranslatableText("book.invalid.tag").formatted(Formatting.DARK_RED)));
        }

        @Override
        public int getPageCount() {
            return this.pages.size();
        }

        @Override
        public StringVisitable getPageUnchecked(int index) {
            String string = this.pages.get(index);
            try {
                MutableText mutableText = Text.Serializer.fromJson(string);
                if (mutableText != null) {
                    return mutableText;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            return StringVisitable.plain(string);
        }
    }

    public static interface Contents {
        public int getPageCount();

        public StringVisitable getPageUnchecked(int var1);

        default public StringVisitable getPage(int index) {
            if (index >= 0 && index < this.getPageCount()) {
                return this.getPageUnchecked(index);
            }
            return StringVisitable.EMPTY;
        }

        public static Contents create(ItemStack stack) {
            Item item = stack.getItem();
            if (item == Items.WRITTEN_BOOK) {
                return new WrittenBookContents(stack);
            }
            if (item == Items.WRITABLE_BOOK) {
                return new WritableBookContents(stack);
            }
            return EMPTY_PROVIDER;
        }
    }
}

