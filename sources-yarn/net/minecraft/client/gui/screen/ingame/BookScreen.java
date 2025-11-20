package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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

   private BookScreen(BookScreen.Contents arg, boolean playPageTurnSound) {
      super(NarratorManager.EMPTY);
      this.contents = arg;
      this.pageTurnSound = playPageTurnSound;
   }

   public void setPageProvider(BookScreen.Contents pageProvider) {
      this.contents = pageProvider;
      this.pageIndex = MathHelper.clamp(this.pageIndex, 0, pageProvider.getPageCount());
      this.updatePageButtons();
      this.cachedPageIndex = -1;
   }

   public boolean setPage(int index) {
      int j = MathHelper.clamp(index, 0, this.contents.getPageCount() - 1);
      if (j != this.pageIndex) {
         this.pageIndex = j;
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
      this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 200, 20, ScreenTexts.DONE, arg -> this.client.openScreen(null)));
   }

   protected void addPageButtons() {
      int i = (this.width - 192) / 2;
      int j = 2;
      this.nextPageButton = this.addButton(new PageTurnWidget(i + 116, 159, true, arg -> this.goToNextPage(), this.pageTurnSound));
      this.previousPageButton = this.addButton(new PageTurnWidget(i + 43, 159, false, arg -> this.goToPreviousPage(), this.pageTurnSound));
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
      int k = (this.width - 192) / 2;
      int l = 2;
      this.drawTexture(matrices, k, 2, 0, 0, 192, 192);
      if (this.cachedPageIndex != this.pageIndex) {
         StringVisitable lv = this.contents.getPage(this.pageIndex);
         this.cachedPage = this.textRenderer.wrapLines(lv, 114);
         this.pageIndexText = new TranslatableText("book.pageIndicator", this.pageIndex + 1, Math.max(this.getPageCount(), 1));
      }

      this.cachedPageIndex = this.pageIndex;
      int m = this.textRenderer.getWidth(this.pageIndexText);
      this.textRenderer.draw(matrices, this.pageIndexText, (float)(k - m + 192 - 44), 18.0F, 0);
      int n = Math.min(128 / 9, this.cachedPage.size());

      for (int o = 0; o < n; o++) {
         OrderedText lv2 = this.cachedPage.get(o);
         this.textRenderer.draw(matrices, lv2, (float)(k + 36), (float)(32 + o * 9), 0);
      }

      Style lv3 = this.getTextAt((double)mouseX, (double)mouseY);
      if (lv3 != null) {
         this.renderTextHoverEffect(matrices, lv3, mouseX, mouseY);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         Style lv = this.getTextAt(mouseX, mouseY);
         if (lv != null && this.handleTextClick(lv)) {
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   @Override
   public boolean handleTextClick(Style style) {
      ClickEvent lv = style.getClickEvent();
      if (lv == null) {
         return false;
      } else if (lv.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String string = lv.getValue();

         try {
            int i = Integer.parseInt(string) - 1;
            return this.jumpToPage(i);
         } catch (Exception var5) {
            return false;
         }
      } else {
         boolean bl = super.handleTextClick(style);
         if (bl && lv.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.client.openScreen(null);
         }

         return bl;
      }
   }

   @Nullable
   public Style getTextAt(double x, double y) {
      if (this.cachedPage.isEmpty()) {
         return null;
      } else {
         int i = MathHelper.floor(x - (double)((this.width - 192) / 2) - 36.0);
         int j = MathHelper.floor(y - 2.0 - 30.0);
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / 9, this.cachedPage.size());
            if (i <= 114 && j < 9 * k + k) {
               int l = j / 9;
               if (l >= 0 && l < this.cachedPage.size()) {
                  OrderedText lv = this.cachedPage.get(l);
                  return this.client.textRenderer.getTextHandler().getStyleAt(lv, i);
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
      ListTag lv = tag.getList("pages", 8).copy();
      Builder<String> builder = ImmutableList.builder();

      for (int i = 0; i < lv.size(); i++) {
         builder.add(lv.getString(i));
      }

      return builder.build();
   }

   @Environment(EnvType.CLIENT)
   public interface Contents {
      int getPageCount();

      StringVisitable getPageUnchecked(int index);

      default StringVisitable getPage(int index) {
         return index >= 0 && index < this.getPageCount() ? this.getPageUnchecked(index) : StringVisitable.EMPTY;
      }

      static BookScreen.Contents create(ItemStack stack) {
         Item lv = stack.getItem();
         if (lv == Items.WRITTEN_BOOK) {
            return new BookScreen.WrittenBookContents(stack);
         } else {
            return (BookScreen.Contents)(lv == Items.WRITABLE_BOOK ? new BookScreen.WritableBookContents(stack) : BookScreen.EMPTY_PROVIDER);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   public static class WritableBookContents implements BookScreen.Contents {
      private final List<String> pages;

      public WritableBookContents(ItemStack stack) {
         this.pages = getPages(stack);
      }

      private static List<String> getPages(ItemStack stack) {
         CompoundTag lv = stack.getTag();
         return (List<String>)(lv != null ? BookScreen.readPages(lv) : ImmutableList.of());
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

   @Environment(EnvType.CLIENT)
   public static class WrittenBookContents implements BookScreen.Contents {
      private final List<String> pages;

      public WrittenBookContents(ItemStack stack) {
         this.pages = getPages(stack);
      }

      private static List<String> getPages(ItemStack stack) {
         CompoundTag lv = stack.getTag();
         return (List<String>)(lv != null && WrittenBookItem.isValid(lv)
            ? BookScreen.readPages(lv)
            : ImmutableList.of(Text.Serializer.toJson(new TranslatableText("book.invalid.tag").formatted(Formatting.DARK_RED))));
      }

      @Override
      public int getPageCount() {
         return this.pages.size();
      }

      @Override
      public StringVisitable getPageUnchecked(int index) {
         String string = this.pages.get(index);

         try {
            StringVisitable lv = Text.Serializer.fromJson(string);
            if (lv != null) {
               return lv;
            }
         } catch (Exception var4) {
         }

         return StringVisitable.plain(string);
      }
   }
}
