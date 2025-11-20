package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.SharedConstants;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
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

@Environment(EnvType.CLIENT)
public class BookEditScreen extends Screen {
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
   private final SelectionManager field_24269 = new SelectionManager(
      this::getCurrentPageContent,
      this::setPageContent,
      this::method_27595,
      this::method_27584,
      string -> string.length() < 1024 && this.textRenderer.getStringBoundedHeight(string, 114) <= 128
   );
   private final SelectionManager field_24270 = new SelectionManager(
      () -> this.title, string -> this.title = string, this::method_27595, this::method_27584, string -> string.length() < 16
   );
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
   private BookEditScreen.PageContent pageContent = BookEditScreen.PageContent.EMPTY;
   private Text field_25891 = LiteralText.EMPTY;
   private final Text field_25892;

   public BookEditScreen(PlayerEntity arg, ItemStack itemStack, Hand hand) {
      super(NarratorManager.EMPTY);
      this.player = arg;
      this.itemStack = itemStack;
      this.hand = hand;
      CompoundTag lv = itemStack.getTag();
      if (lv != null) {
         ListTag lv2 = lv.getList("pages", 8).copy();

         for (int i = 0; i < lv2.size(); i++) {
            this.pages.add(lv2.getString(i));
         }
      }

      if (this.pages.isEmpty()) {
         this.pages.add("");
      }

      this.field_25892 = new TranslatableText("book.byAuthor", arg.getName()).formatted(Formatting.DARK_GRAY);
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
      this.tickCounter++;
   }

   @Override
   protected void init() {
      this.invalidatePageContent();
      this.client.keyboard.setRepeatEvents(true);
      this.signButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.signButton"), arg -> {
         this.signing = true;
         this.updateButtons();
      }));
      this.doneButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.DONE, arg -> {
         this.client.openScreen(null);
         this.finalizeBook(false);
      }));
      this.finalizeButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.finalizeButton"), arg -> {
         if (this.signing) {
            this.finalizeBook(true);
            this.client.openScreen(null);
         }
      }));
      this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.CANCEL, arg -> {
         if (this.signing) {
            this.signing = false;
         }

         this.updateButtons();
      }));
      int i = (this.width - 192) / 2;
      int j = 2;
      this.nextPageButton = this.addButton(new PageTurnWidget(i + 116, 159, true, arg -> this.openNextPage(), true));
      this.previousPageButton = this.addButton(new PageTurnWidget(i + 43, 159, false, arg -> this.openPreviousPage(), true));
      this.updateButtons();
   }

   private void openPreviousPage() {
      if (this.currentPage > 0) {
         this.currentPage--;
      }

      this.updateButtons();
      this.method_27872();
   }

   private void openNextPage() {
      if (this.currentPage < this.countPages() - 1) {
         this.currentPage++;
      } else {
         this.appendNewPage();
         if (this.currentPage < this.countPages() - 1) {
            this.currentPage++;
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
      if (this.dirty) {
         this.removeEmptyPages();
         ListTag lv = new ListTag();
         this.pages.stream().map(StringTag::of).forEach(lv::add);
         if (!this.pages.isEmpty()) {
            this.itemStack.putSubTag("pages", lv);
         }

         if (signBook) {
            this.itemStack.putSubTag("author", StringTag.of(this.player.getGameProfile().getName()));
            this.itemStack.putSubTag("title", StringTag.of(this.title.trim()));
         }

         int i = this.hand == Hand.MAIN_HAND ? this.player.inventory.selectedSlot : 40;
         this.client.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(this.itemStack, signBook, i));
      }
   }

   private void appendNewPage() {
      if (this.countPages() < 100) {
         this.pages.add("");
         this.dirty = true;
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (this.signing) {
         return this.keyPressedSignMode(keyCode, scanCode, modifiers);
      } else {
         boolean bl = this.method_27592(keyCode, scanCode, modifiers);
         if (bl) {
            this.invalidatePageContent();
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      if (super.charTyped(chr, keyCode)) {
         return true;
      } else if (this.signing) {
         boolean bl = this.field_24270.insert(chr);
         if (bl) {
            this.updateButtons();
            this.dirty = true;
            return true;
         } else {
            return false;
         }
      } else if (SharedConstants.isValidChar(chr)) {
         this.field_24269.insert(Character.toString(chr));
         this.invalidatePageContent();
         return true;
      } else {
         return false;
      }
   }

   private boolean method_27592(int i, int j, int k) {
      if (Screen.isSelectAll(i)) {
         this.field_24269.selectAll();
         return true;
      } else if (Screen.isCopy(i)) {
         this.field_24269.copy();
         return true;
      } else if (Screen.isPaste(i)) {
         this.field_24269.paste();
         return true;
      } else if (Screen.isCut(i)) {
         this.field_24269.cut();
         return true;
      } else {
         switch (i) {
            case 257:
            case 335:
               this.field_24269.insert("\n");
               return true;
            case 259:
               this.field_24269.delete(-1);
               return true;
            case 261:
               this.field_24269.delete(1);
               return true;
            case 262:
               this.field_24269.moveCursor(1, Screen.hasShiftDown());
               return true;
            case 263:
               this.field_24269.moveCursor(-1, Screen.hasShiftDown());
               return true;
            case 264:
               this.method_27598();
               return true;
            case 265:
               this.method_27597();
               return true;
            case 266:
               this.previousPageButton.onPress();
               return true;
            case 267:
               this.nextPageButton.onPress();
               return true;
            case 268:
               this.moveCursorToTop();
               return true;
            case 269:
               this.moveCursorToBottom();
               return true;
            default:
               return false;
         }
      }
   }

   private void method_27597() {
      this.method_27580(-1);
   }

   private void method_27598() {
      this.method_27580(1);
   }

   private void method_27580(int i) {
      int j = this.field_24269.getSelectionStart();
      int k = this.getPageContent().method_27601(j, i);
      this.field_24269.method_27560(k, Screen.hasShiftDown());
   }

   private void moveCursorToTop() {
      int i = this.field_24269.getSelectionStart();
      int j = this.getPageContent().method_27600(i);
      this.field_24269.method_27560(j, Screen.hasShiftDown());
   }

   private void moveCursorToBottom() {
      BookEditScreen.PageContent lv = this.getPageContent();
      int i = this.field_24269.getSelectionStart();
      int j = lv.method_27604(i);
      this.field_24269.method_27560(j, Screen.hasShiftDown());
   }

   private boolean keyPressedSignMode(int keyCode, int scanCode, int modifiers) {
      switch (keyCode) {
         case 257:
         case 335:
            if (!this.title.isEmpty()) {
               this.finalizeBook(true);
               this.client.openScreen(null);
            }

            return true;
         case 259:
            this.field_24270.delete(-1);
            this.updateButtons();
            this.dirty = true;
            return true;
         default:
            return false;
      }
   }

   private String getCurrentPageContent() {
      return this.currentPage >= 0 && this.currentPage < this.pages.size() ? this.pages.get(this.currentPage) : "";
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
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(BookScreen.BOOK_TEXTURE);
      int k = (this.width - 192) / 2;
      int l = 2;
      this.drawTexture(matrices, k, 2, 0, 0, 192, 192);
      if (this.signing) {
         boolean bl = this.tickCounter / 6 % 2 == 0;
         OrderedText lv = OrderedText.concat(OrderedText.styledString(this.title, Style.EMPTY), bl ? field_25895 : field_25896);
         int m = this.textRenderer.getWidth(field_25893);
         this.textRenderer.draw(matrices, field_25893, (float)(k + 36 + (114 - m) / 2), 34.0F, 0);
         int n = this.textRenderer.getWidth(lv);
         this.textRenderer.draw(matrices, lv, (float)(k + 36 + (114 - n) / 2), 50.0F, 0);
         int o = this.textRenderer.getWidth(this.field_25892);
         this.textRenderer.draw(matrices, this.field_25892, (float)(k + 36 + (114 - o) / 2), 60.0F, 0);
         this.textRenderer.drawTrimmed(field_25894, k + 36, 82, 114, 0);
      } else {
         int p = this.textRenderer.getWidth(this.field_25891);
         this.textRenderer.draw(matrices, this.field_25891, (float)(k - p + 192 - 44), 18.0F, 0);
         BookEditScreen.PageContent lv2 = this.getPageContent();

         for (BookEditScreen.Line lv3 : lv2.lines) {
            this.textRenderer.draw(matrices, lv3.text, (float)lv3.x, (float)lv3.y, -16777216);
         }

         this.method_27588(lv2.field_24277);
         this.method_27581(matrices, lv2.position, lv2.field_24274);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void method_27581(MatrixStack arg, BookEditScreen.Position arg2, boolean bl) {
      if (this.tickCounter / 6 % 2 == 0) {
         arg2 = this.method_27590(arg2);
         if (!bl) {
            DrawableHelper.fill(arg, arg2.x, arg2.y - 1, arg2.x + 1, arg2.y + 9, -16777216);
         } else {
            this.textRenderer.draw(arg, "_", (float)arg2.x, (float)arg2.y, 0);
         }
      }
   }

   private void method_27588(Rect2i[] args) {
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      lv2.begin(7, VertexFormats.POSITION);

      for (Rect2i lv3 : args) {
         int i = lv3.getX();
         int j = lv3.getY();
         int k = i + lv3.getWidth();
         int l = j + lv3.getHeight();
         lv2.vertex((double)i, (double)l, 0.0).next();
         lv2.vertex((double)k, (double)l, 0.0).next();
         lv2.vertex((double)k, (double)j, 0.0).next();
         lv2.vertex((double)i, (double)j, 0.0).next();
      }

      lv.draw();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   private BookEditScreen.Position method_27582(BookEditScreen.Position arg) {
      return new BookEditScreen.Position(arg.x - (this.width - 192) / 2 - 36, arg.y - 32);
   }

   private BookEditScreen.Position method_27590(BookEditScreen.Position arg) {
      return new BookEditScreen.Position(arg.x + (this.width - 192) / 2 + 36, arg.y + 32);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         if (button == 0) {
            long l = Util.getMeasuringTimeMs();
            BookEditScreen.PageContent lv = this.getPageContent();
            int j = lv.method_27602(this.textRenderer, this.method_27582(new BookEditScreen.Position((int)mouseX, (int)mouseY)));
            if (j >= 0) {
               if (j != this.lastClickIndex || l - this.lastClickTime >= 250L) {
                  this.field_24269.method_27560(j, Screen.hasShiftDown());
               } else if (!this.field_24269.method_27568()) {
                  this.method_27589(j);
               } else {
                  this.field_24269.selectAll();
               }

               this.invalidatePageContent();
            }

            this.lastClickIndex = j;
            this.lastClickTime = l;
         }

         return true;
      }
   }

   private void method_27589(int i) {
      String string = this.getCurrentPageContent();
      this.field_24269.method_27548(TextHandler.moveCursorByWords(string, -1, i, false), TextHandler.moveCursorByWords(string, 1, i, false));
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
         return true;
      } else {
         if (button == 0) {
            BookEditScreen.PageContent lv = this.getPageContent();
            int j = lv.method_27602(this.textRenderer, this.method_27582(new BookEditScreen.Position((int)mouseX, (int)mouseY)));
            this.field_24269.method_27560(j, true);
            this.invalidatePageContent();
         }

         return true;
      }
   }

   private BookEditScreen.PageContent getPageContent() {
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

   private BookEditScreen.PageContent createPageContent() {
      String string = this.getCurrentPageContent();
      if (string.isEmpty()) {
         return BookEditScreen.PageContent.EMPTY;
      } else {
         int i = this.field_24269.getSelectionStart();
         int j = this.field_24269.getSelectionEnd();
         IntList intList = new IntArrayList();
         List<BookEditScreen.Line> list = Lists.newArrayList();
         MutableInt mutableInt = new MutableInt();
         MutableBoolean mutableBoolean = new MutableBoolean();
         TextHandler lv = this.textRenderer.getTextHandler();
         lv.wrapLines(string, 114, Style.EMPTY, true, (arg, ix, jx) -> {
            int k = mutableInt.getAndIncrement();
            String string2x = string.substring(ix, jx);
            mutableBoolean.setValue(string2x.endsWith("\n"));
            String string3 = StringUtils.stripEnd(string2x, " \n");
            int l = k * 9;
            BookEditScreen.Position lvx = this.method_27590(new BookEditScreen.Position(0, l));
            intList.add(ix);
            list.add(new BookEditScreen.Line(arg, string3, lvx.x, lvx.y));
         });
         int[] is = intList.toIntArray();
         boolean bl = i == string.length();
         BookEditScreen.Position lv2;
         if (bl && mutableBoolean.isTrue()) {
            lv2 = new BookEditScreen.Position(0, list.size() * 9);
         } else {
            int k = method_27591(is, i);
            int l = this.textRenderer.getWidth(string.substring(is[k], i));
            lv2 = new BookEditScreen.Position(l, k * 9);
         }

         List<Rect2i> list2 = Lists.newArrayList();
         if (i != j) {
            int m = Math.min(i, j);
            int n = Math.max(i, j);
            int o = method_27591(is, m);
            int p = method_27591(is, n);
            if (o == p) {
               int q = o * 9;
               int r = is[o];
               list2.add(this.method_27585(string, lv, m, n, q, r));
            } else {
               int s = o + 1 > is.length ? string.length() : is[o + 1];
               list2.add(this.method_27585(string, lv, m, s, o * 9, is[o]));

               for (int t = o + 1; t < p; t++) {
                  int u = t * 9;
                  String string2 = string.substring(is[t], is[t + 1]);
                  int v = (int)lv.getWidth(string2);
                  list2.add(this.method_27583(new BookEditScreen.Position(0, u), new BookEditScreen.Position(v, u + 9)));
               }

               list2.add(this.method_27585(string, lv, is[p], n, p * 9, is[p]));
            }
         }

         return new BookEditScreen.PageContent(string, lv2, bl, is, list.toArray(new BookEditScreen.Line[0]), list2.toArray(new Rect2i[0]));
      }
   }

   private static int method_27591(int[] is, int i) {
      int j = Arrays.binarySearch(is, i);
      return j < 0 ? -(j + 2) : j;
   }

   private Rect2i method_27585(String string, TextHandler arg, int i, int j, int k, int l) {
      String string2 = string.substring(l, i);
      String string3 = string.substring(l, j);
      BookEditScreen.Position lv = new BookEditScreen.Position((int)arg.getWidth(string2), k);
      BookEditScreen.Position lv2 = new BookEditScreen.Position((int)arg.getWidth(string3), k + 9);
      return this.method_27583(lv, lv2);
   }

   private Rect2i method_27583(BookEditScreen.Position arg, BookEditScreen.Position arg2) {
      BookEditScreen.Position lv = this.method_27590(arg);
      BookEditScreen.Position lv2 = this.method_27590(arg2);
      int i = Math.min(lv.x, lv2.x);
      int j = Math.max(lv.x, lv2.x);
      int k = Math.min(lv.y, lv2.y);
      int l = Math.max(lv.y, lv2.y);
      return new Rect2i(i, k, j - i, l - k);
   }

   @Environment(EnvType.CLIENT)
   static class Line {
      private final Style style;
      private final String content;
      private final Text text;
      private final int x;
      private final int y;

      public Line(Style arg, String content, int x, int y) {
         this.style = arg;
         this.content = content;
         this.x = x;
         this.y = y;
         this.text = new LiteralText(content).setStyle(arg);
      }
   }

   @Environment(EnvType.CLIENT)
   static class PageContent {
      private static final BookEditScreen.PageContent EMPTY = new BookEditScreen.PageContent(
         "", new BookEditScreen.Position(0, 0), true, new int[]{0}, new BookEditScreen.Line[]{new BookEditScreen.Line(Style.EMPTY, "", 0, 0)}, new Rect2i[0]
      );
      private final String pageContent;
      private final BookEditScreen.Position position;
      private final boolean field_24274;
      private final int[] field_24275;
      private final BookEditScreen.Line[] lines;
      private final Rect2i[] field_24277;

      public PageContent(String pageContent, BookEditScreen.Position arg, boolean bl, int[] is, BookEditScreen.Line[] lines, Rect2i[] args2) {
         this.pageContent = pageContent;
         this.position = arg;
         this.field_24274 = bl;
         this.field_24275 = is;
         this.lines = lines;
         this.field_24277 = args2;
      }

      public int method_27602(TextRenderer arg, BookEditScreen.Position arg2) {
         int i = arg2.y / 9;
         if (i < 0) {
            return 0;
         } else if (i >= this.lines.length) {
            return this.pageContent.length();
         } else {
            BookEditScreen.Line lv = this.lines[i];
            return this.field_24275[i] + arg.getTextHandler().getTrimmedLength(lv.content, arg2.x, lv.style);
         }
      }

      public int method_27601(int i, int j) {
         int k = BookEditScreen.method_27591(this.field_24275, i);
         int l = k + j;
         int o;
         if (0 <= l && l < this.field_24275.length) {
            int m = i - this.field_24275[k];
            int n = this.lines[l].content.length();
            o = this.field_24275[l] + Math.min(m, n);
         } else {
            o = i;
         }

         return o;
      }

      public int method_27600(int i) {
         int j = BookEditScreen.method_27591(this.field_24275, i);
         return this.field_24275[j];
      }

      public int method_27604(int i) {
         int j = BookEditScreen.method_27591(this.field_24275, i);
         return this.field_24275[j] + this.lines[j].content.length();
      }
   }

   @Environment(EnvType.CLIENT)
   static class Position {
      public final int x;
      public final int y;

      Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   }
}
