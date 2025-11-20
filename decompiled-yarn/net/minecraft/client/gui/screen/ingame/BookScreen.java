package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
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
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BookScreen extends Screen {
   public static final BookScreen.Contents EMPTY_PROVIDER = new BookScreen.Contents() {
      @Override
      public int getPageCount() {
         return 0;
      }

      @Override
      public StringVisitable getPageUnchecked(int index) {
         return StringVisitable.EMPTY;
      }
   };
   public static final Identifier BOOK_TEXTURE = new Identifier("textures/gui/book.png");
   private BookScreen.Contents contents;
   private int pageIndex;
   private List<OrderedText> cachedPage = Collections.emptyList();
   private int cachedPageIndex = -1;
   private Text pageIndexText = LiteralText.EMPTY;
   private PageTurnWidget nextPageButton;
   private PageTurnWidget previousPageButton;
   private final boolean pageTurnSound;

   public BookScreen(BookScreen.Contents pageProvider) {
      this(pageProvider, true);
   }

   public BookScreen() {
      this(EMPTY_PROVIDER, false);
   }

   private BookScreen(BookScreen.Contents _snowman, boolean playPageTurnSound) {
      super(NarratorManager.EMPTY);
      this.contents = _snowman;
      this.pageTurnSound = playPageTurnSound;
   }

   public void setPageProvider(BookScreen.Contents pageProvider) {
      this.contents = pageProvider;
      this.pageIndex = MathHelper.clamp(this.pageIndex, 0, pageProvider.getPageCount());
      this.updatePageButtons();
      this.cachedPageIndex = -1;
   }

   public boolean setPage(int index) {
      int _snowman = MathHelper.clamp(index, 0, this.contents.getPageCount() - 1);
      if (_snowman != this.pageIndex) {
         this.pageIndex = _snowman;
         this.updatePageButtons();
         this.cachedPageIndex = -1;
         return true;
      } else {
         return false;
      }
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
      this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, _snowman -> this.client.openScreen(null)));
   }

   protected void addPageButtons() {
      int _snowman = (this.width - 192) / 2;
      int _snowmanx = 2;
      this.nextPageButton = this.addButton(new PageTurnWidget(_snowman + 116, 159, true, _snowmanxx -> this.goToNextPage(), this.pageTurnSound));
      this.previousPageButton = this.addButton(new PageTurnWidget(_snowman + 43, 159, false, _snowmanxx -> this.goToPreviousPage(), this.pageTurnSound));
      this.updatePageButtons();
   }

   private int getPageCount() {
      return this.contents.getPageCount();
   }

   protected void goToPreviousPage() {
      if (this.pageIndex > 0) {
         this.pageIndex--;
      }

      this.updatePageButtons();
   }

   protected void goToNextPage() {
      if (this.pageIndex < this.getPageCount() - 1) {
         this.pageIndex++;
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
      } else {
         switch (keyCode) {
            case 266:
               this.previousPageButton.onPress();
               return true;
            case 267:
               this.nextPageButton.onPress();
               return true;
            default:
               return false;
         }
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.renderBackground(matrices);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(BOOK_TEXTURE);
      int _snowman = (this.width - 192) / 2;
      int _snowmanx = 2;
      this.drawTexture(matrices, _snowman, 2, 0, 0, 192, 192);
      if (this.cachedPageIndex != this.pageIndex) {
         StringVisitable _snowmanxx = this.contents.getPage(this.pageIndex);
         this.cachedPage = this.textRenderer.wrapLines(_snowmanxx, 114);
         this.pageIndexText = new TranslatableText("book.pageIndicator", this.pageIndex + 1, Math.max(this.getPageCount(), 1));
      }

      this.cachedPageIndex = this.pageIndex;
      int _snowmanxx = this.textRenderer.getWidth(this.pageIndexText);
      this.textRenderer.draw(matrices, this.pageIndexText, (float)(_snowman - _snowmanxx + 192 - 44), 18.0F, 0);
      int _snowmanxxx = Math.min(128 / 9, this.cachedPage.size());

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         OrderedText _snowmanxxxxx = this.cachedPage.get(_snowmanxxxx);
         this.textRenderer.draw(matrices, _snowmanxxxxx, (float)(_snowman + 36), (float)(32 + _snowmanxxxx * 9), 0);
      }

      Style _snowmanxxxx = this.getTextAt((double)mouseX, (double)mouseY);
      if (_snowmanxxxx != null) {
         this.renderTextHoverEffect(matrices, _snowmanxxxx, mouseX, mouseY);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         Style _snowman = this.getTextAt(mouseX, mouseY);
         if (_snowman != null && this.handleTextClick(_snowman)) {
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean handleTextClick(Style style) {
      ClickEvent _snowman = style.getClickEvent();
      if (_snowman == null) {
         return false;
      } else if (_snowman.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String _snowmanx = _snowman.getValue();

         try {
            int _snowmanxx = Integer.parseInt(_snowmanx) - 1;
            return this.jumpToPage(_snowmanxx);
         } catch (Exception var5) {
            return false;
         }
      } else {
         boolean _snowmanx = super.handleTextClick(style);
         if (_snowmanx && _snowman.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.client.openScreen(null);
         }

         return _snowmanx;
      }
   }

   @Nullable
   public Style getTextAt(double x, double y) {
      if (this.cachedPage.isEmpty()) {
         return null;
      } else {
         int _snowman = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0);
         int _snowmanx = MathHelper.floor(y - 2.0 - 30.0);
         if (_snowman >= 0 && _snowmanx >= 0) {
            int _snowmanxx = Math.min(128 / 9, this.cachedPage.size());
            if (_snowman <= 114 && _snowmanx < 9 * _snowmanxx + _snowmanxx) {
               int _snowmanxxx = _snowmanx / 9;
               if (_snowmanxxx >= 0 && _snowmanxxx < this.cachedPage.size()) {
                  OrderedText _snowmanxxxx = this.cachedPage.get(_snowmanxxx);
                  return this.client.textRenderer.getTextHandler().getStyleAt(_snowmanxxxx, _snowman);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static List<String> readPages(CompoundTag tag) {
      ListTag _snowman = tag.getList("pages", 8).copy();
      Builder<String> _snowmanx = ImmutableList.builder();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         _snowmanx.add(_snowman.getString(_snowmanxx));
      }

      return _snowmanx.build();
   }

   public interface Contents {
      int getPageCount();

      StringVisitable getPageUnchecked(int index);

      default StringVisitable getPage(int index) {
         return index >= 0 && index < this.getPageCount() ? this.getPageUnchecked(index) : StringVisitable.EMPTY;
      }

      static BookScreen.Contents create(ItemStack stack) {
         Item _snowman = stack.getItem();
         if (_snowman == Items.WRITTEN_BOOK) {
            return new BookScreen.WrittenBookContents(stack);
         } else {
            return (BookScreen.Contents)(_snowman == Items.WRITABLE_BOOK ? new BookScreen.WritableBookContents(stack) : BookScreen.EMPTY_PROVIDER);
         }
      }
   }

   public static class WritableBookContents implements BookScreen.Contents {
      private final List<String> pages;

      public WritableBookContents(ItemStack stack) {
         this.pages = getPages(stack);
      }

      private static List<String> getPages(ItemStack stack) {
         CompoundTag _snowman = stack.getTag();
         return (List<String>)(_snowman != null ? BookScreen.readPages(_snowman) : ImmutableList.of());
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

   public static class WrittenBookContents implements BookScreen.Contents {
      private final List<String> pages;

      public WrittenBookContents(ItemStack stack) {
         this.pages = getPages(stack);
      }

      private static List<String> getPages(ItemStack stack) {
         CompoundTag _snowman = stack.getTag();
         return (List<String>)(_snowman != null && WrittenBookItem.isValid(_snowman)
            ? BookScreen.readPages(_snowman)
            : ImmutableList.of(Text.Serializer.toJson(new TranslatableText("book.invalid.tag").formatted(Formatting.DARK_RED))));
      }

      @Override
      public int getPageCount() {
         return this.pages.size();
      }

      @Override
      public StringVisitable getPageUnchecked(int index) {
         String _snowman = this.pages.get(index);

         try {
            StringVisitable _snowmanx = Text.Serializer.fromJson(_snowman);
            if (_snowmanx != null) {
               return _snowmanx;
            }
         } catch (Exception var4) {
         }

         return StringVisitable.plain(_snowman);
      }
   }
}
