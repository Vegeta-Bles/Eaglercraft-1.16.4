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
      _snowmanx -> _snowmanx.length() < 1024 && this.textRenderer.getStringBoundedHeight(_snowmanx, 114) <= 128
   );
   private final SelectionManager field_24270 = new SelectionManager(
      () -> this.title, _snowmanx -> this.title = _snowmanx, this::method_27595, this::method_27584, _snowmanx -> _snowmanx.length() < 16
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

   public BookEditScreen(PlayerEntity _snowman, ItemStack itemStack, Hand hand) {
      super(NarratorManager.EMPTY);
      this.player = _snowman;
      this.itemStack = itemStack;
      this.hand = hand;
      CompoundTag _snowmanx = itemStack.getTag();
      if (_snowmanx != null) {
         ListTag _snowmanxx = _snowmanx.getList("pages", 8).copy();

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            this.pages.add(_snowmanxx.getString(_snowmanxxx));
         }
      }

      if (this.pages.isEmpty()) {
         this.pages.add("");
      }

      this.field_25892 = new TranslatableText("book.byAuthor", _snowman.getName()).formatted(Formatting.DARK_GRAY);
   }

   private void method_27584(String _snowman) {
      if (this.client != null) {
         SelectionManager.setClipboard(this.client, _snowman);
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
      this.signButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.signButton"), _snowman -> {
         this.signing = true;
         this.updateButtons();
      }));
      this.doneButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.DONE, _snowman -> {
         this.client.openScreen(null);
         this.finalizeBook(false);
      }));
      this.finalizeButton = this.addButton(new ButtonWidget(this.width / 2 - 100, 196, 98, 20, new TranslatableText("book.finalizeButton"), _snowman -> {
         if (this.signing) {
            this.finalizeBook(true);
            this.client.openScreen(null);
         }
      }));
      this.cancelButton = this.addButton(new ButtonWidget(this.width / 2 + 2, 196, 98, 20, ScreenTexts.CANCEL, _snowman -> {
         if (this.signing) {
            this.signing = false;
         }

         this.updateButtons();
      }));
      int _snowman = (this.width - 192) / 2;
      int _snowmanx = 2;
      this.nextPageButton = this.addButton(new PageTurnWidget(_snowman + 116, 159, true, _snowmanxx -> this.openNextPage(), true));
      this.previousPageButton = this.addButton(new PageTurnWidget(_snowman + 43, 159, false, _snowmanxx -> this.openPreviousPage(), true));
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
      ListIterator<String> _snowman = this.pages.listIterator(this.pages.size());

      while (_snowman.hasPrevious() && _snowman.previous().isEmpty()) {
         _snowman.remove();
      }
   }

   private void finalizeBook(boolean signBook) {
      if (this.dirty) {
         this.removeEmptyPages();
         ListTag _snowman = new ListTag();
         this.pages.stream().map(StringTag::of).forEach(_snowman::add);
         if (!this.pages.isEmpty()) {
            this.itemStack.putSubTag("pages", _snowman);
         }

         if (signBook) {
            this.itemStack.putSubTag("author", StringTag.of(this.player.getGameProfile().getName()));
            this.itemStack.putSubTag("title", StringTag.of(this.title.trim()));
         }

         int _snowmanx = this.hand == Hand.MAIN_HAND ? this.player.inventory.selectedSlot : 40;
         this.client.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(this.itemStack, signBook, _snowmanx));
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
         boolean _snowman = this.method_27592(keyCode, scanCode, modifiers);
         if (_snowman) {
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
         boolean _snowman = this.field_24270.insert(chr);
         if (_snowman) {
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

   private boolean method_27592(int _snowman, int _snowman, int _snowman) {
      if (Screen.isSelectAll(_snowman)) {
         this.field_24269.selectAll();
         return true;
      } else if (Screen.isCopy(_snowman)) {
         this.field_24269.copy();
         return true;
      } else if (Screen.isPaste(_snowman)) {
         this.field_24269.paste();
         return true;
      } else if (Screen.isCut(_snowman)) {
         this.field_24269.cut();
         return true;
      } else {
         switch (_snowman) {
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

   private void method_27580(int _snowman) {
      int _snowmanx = this.field_24269.getSelectionStart();
      int _snowmanxx = this.getPageContent().method_27601(_snowmanx, _snowman);
      this.field_24269.method_27560(_snowmanxx, Screen.hasShiftDown());
   }

   private void moveCursorToTop() {
      int _snowman = this.field_24269.getSelectionStart();
      int _snowmanx = this.getPageContent().method_27600(_snowman);
      this.field_24269.method_27560(_snowmanx, Screen.hasShiftDown());
   }

   private void moveCursorToBottom() {
      BookEditScreen.PageContent _snowman = this.getPageContent();
      int _snowmanx = this.field_24269.getSelectionStart();
      int _snowmanxx = _snowman.method_27604(_snowmanx);
      this.field_24269.method_27560(_snowmanxx, Screen.hasShiftDown());
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
      int _snowman = (this.width - 192) / 2;
      int _snowmanx = 2;
      this.drawTexture(matrices, _snowman, 2, 0, 0, 192, 192);
      if (this.signing) {
         boolean _snowmanxx = this.tickCounter / 6 % 2 == 0;
         OrderedText _snowmanxxx = OrderedText.concat(OrderedText.styledString(this.title, Style.EMPTY), _snowmanxx ? field_25895 : field_25896);
         int _snowmanxxxx = this.textRenderer.getWidth(field_25893);
         this.textRenderer.draw(matrices, field_25893, (float)(_snowman + 36 + (114 - _snowmanxxxx) / 2), 34.0F, 0);
         int _snowmanxxxxx = this.textRenderer.getWidth(_snowmanxxx);
         this.textRenderer.draw(matrices, _snowmanxxx, (float)(_snowman + 36 + (114 - _snowmanxxxxx) / 2), 50.0F, 0);
         int _snowmanxxxxxx = this.textRenderer.getWidth(this.field_25892);
         this.textRenderer.draw(matrices, this.field_25892, (float)(_snowman + 36 + (114 - _snowmanxxxxxx) / 2), 60.0F, 0);
         this.textRenderer.drawTrimmed(field_25894, _snowman + 36, 82, 114, 0);
      } else {
         int _snowmanxx = this.textRenderer.getWidth(this.field_25891);
         this.textRenderer.draw(matrices, this.field_25891, (float)(_snowman - _snowmanxx + 192 - 44), 18.0F, 0);
         BookEditScreen.PageContent _snowmanxxx = this.getPageContent();

         for (BookEditScreen.Line _snowmanxxxx : _snowmanxxx.lines) {
            this.textRenderer.draw(matrices, _snowmanxxxx.text, (float)_snowmanxxxx.x, (float)_snowmanxxxx.y, -16777216);
         }

         this.method_27588(_snowmanxxx.field_24277);
         this.method_27581(matrices, _snowmanxxx.position, _snowmanxxx.field_24274);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void method_27581(MatrixStack _snowman, BookEditScreen.Position _snowman, boolean _snowman) {
      if (this.tickCounter / 6 % 2 == 0) {
         _snowman = this.method_27590(_snowman);
         if (!_snowman) {
            DrawableHelper.fill(_snowman, _snowman.x, _snowman.y - 1, _snowman.x + 1, _snowman.y + 9, -16777216);
         } else {
            this.textRenderer.draw(_snowman, "_", (float)_snowman.x, (float)_snowman.y, 0);
         }
      }
   }

   private void method_27588(Rect2i[] _snowman) {
      Tessellator _snowmanx = Tessellator.getInstance();
      BufferBuilder _snowmanxx = _snowmanx.getBuffer();
      RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
      RenderSystem.disableTexture();
      RenderSystem.enableColorLogicOp();
      RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
      _snowmanxx.begin(7, VertexFormats.POSITION);

      for (Rect2i _snowmanxxx : _snowman) {
         int _snowmanxxxx = _snowmanxxx.getX();
         int _snowmanxxxxx = _snowmanxxx.getY();
         int _snowmanxxxxxx = _snowmanxxxx + _snowmanxxx.getWidth();
         int _snowmanxxxxxxx = _snowmanxxxxx + _snowmanxxx.getHeight();
         _snowmanxx.vertex((double)_snowmanxxxx, (double)_snowmanxxxxxxx, 0.0).next();
         _snowmanxx.vertex((double)_snowmanxxxxxx, (double)_snowmanxxxxxxx, 0.0).next();
         _snowmanxx.vertex((double)_snowmanxxxxxx, (double)_snowmanxxxxx, 0.0).next();
         _snowmanxx.vertex((double)_snowmanxxxx, (double)_snowmanxxxxx, 0.0).next();
      }

      _snowmanx.draw();
      RenderSystem.disableColorLogicOp();
      RenderSystem.enableTexture();
   }

   private BookEditScreen.Position method_27582(BookEditScreen.Position _snowman) {
      return new BookEditScreen.Position(_snowman.x - (this.width - 192) / 2 - 36, _snowman.y - 32);
   }

   private BookEditScreen.Position method_27590(BookEditScreen.Position _snowman) {
      return new BookEditScreen.Position(_snowman.x + (this.width - 192) / 2 + 36, _snowman.y + 32);
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (super.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         if (button == 0) {
            long _snowman = Util.getMeasuringTimeMs();
            BookEditScreen.PageContent _snowmanx = this.getPageContent();
            int _snowmanxx = _snowmanx.method_27602(this.textRenderer, this.method_27582(new BookEditScreen.Position((int)mouseX, (int)mouseY)));
            if (_snowmanxx >= 0) {
               if (_snowmanxx != this.lastClickIndex || _snowman - this.lastClickTime >= 250L) {
                  this.field_24269.method_27560(_snowmanxx, Screen.hasShiftDown());
               } else if (!this.field_24269.method_27568()) {
                  this.method_27589(_snowmanxx);
               } else {
                  this.field_24269.selectAll();
               }

               this.invalidatePageContent();
            }

            this.lastClickIndex = _snowmanxx;
            this.lastClickTime = _snowman;
         }

         return true;
      }
   }

   private void method_27589(int _snowman) {
      String _snowmanx = this.getCurrentPageContent();
      this.field_24269.method_27548(TextHandler.moveCursorByWords(_snowmanx, -1, _snowman, false), TextHandler.moveCursorByWords(_snowmanx, 1, _snowman, false));
   }

   @Override
   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
         return true;
      } else {
         if (button == 0) {
            BookEditScreen.PageContent _snowman = this.getPageContent();
            int _snowmanx = _snowman.method_27602(this.textRenderer, this.method_27582(new BookEditScreen.Position((int)mouseX, (int)mouseY)));
            this.field_24269.method_27560(_snowmanx, true);
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
      String _snowman = this.getCurrentPageContent();
      if (_snowman.isEmpty()) {
         return BookEditScreen.PageContent.EMPTY;
      } else {
         int _snowmanx = this.field_24269.getSelectionStart();
         int _snowmanxx = this.field_24269.getSelectionEnd();
         IntList _snowmanxxx = new IntArrayList();
         List<BookEditScreen.Line> _snowmanxxxx = Lists.newArrayList();
         MutableInt _snowmanxxxxx = new MutableInt();
         MutableBoolean _snowmanxxxxxx = new MutableBoolean();
         TextHandler _snowmanxxxxxxx = this.textRenderer.getTextHandler();
         _snowmanxxxxxxx.wrapLines(_snowman, 114, Style.EMPTY, true, (_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx) -> {
            int _snowmanxxxxxxxxxxx = _snowman.getAndIncrement();
            String _snowmanx = _snowman.substring(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            _snowman.setValue(_snowmanx.endsWith("\n"));
            String _snowmanxx = StringUtils.stripEnd(_snowmanx, " \n");
            int _snowmanxxx = _snowmanxxxxxxxxxxx * 9;
            BookEditScreen.Position _snowmanxxxx = this.method_27590(new BookEditScreen.Position(0, _snowmanxxx));
            _snowman.add(_snowmanxxxxxxxxx);
            _snowman.add(new BookEditScreen.Line(_snowmanxxxxxxxx, _snowmanxx, _snowmanxxxx.x, _snowmanxxxx.y));
         });
         int[] _snowmanxxxxxxxx = _snowmanxxx.toIntArray();
         boolean _snowmanxxxxxxxxx = _snowmanx == _snowman.length();
         BookEditScreen.Position _snowmanxxxxxxxxxx;
         if (_snowmanxxxxxxxxx && _snowmanxxxxxx.isTrue()) {
            _snowmanxxxxxxxxxx = new BookEditScreen.Position(0, _snowmanxxxx.size() * 9);
         } else {
            int _snowmanxxxxxxxxxxx = method_27591(_snowmanxxxxxxxx, _snowmanx);
            int _snowmanxxxxxxxxxxxx = this.textRenderer.getWidth(_snowman.substring(_snowmanxxxxxxxx[_snowmanxxxxxxxxxxx], _snowmanx));
            _snowmanxxxxxxxxxx = new BookEditScreen.Position(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx * 9);
         }

         List<Rect2i> _snowmanxxxxxxxxxxx = Lists.newArrayList();
         if (_snowmanx != _snowmanxx) {
            int _snowmanxxxxxxxxxxxx = Math.min(_snowmanx, _snowmanxx);
            int _snowmanxxxxxxxxxxxxx = Math.max(_snowmanx, _snowmanxx);
            int _snowmanxxxxxxxxxxxxxx = method_27591(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxx = method_27591(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * 9;
               int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx];
               _snowmanxxxxxxxxxxx.add(this.method_27585(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx));
            } else {
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + 1 > _snowmanxxxxxxxx.length ? _snowman.length() : _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx + 1];
               _snowmanxxxxxxxxxxx.add(this.method_27585(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * 9, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxx]));

               for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx + 1; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * 9;
                  String _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.substring(_snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx], _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx + 1]);
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.getWidth(_snowmanxxxxxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxx.add(
                     this.method_27583(
                        new BookEditScreen.Position(0, _snowmanxxxxxxxxxxxxxxxxxx), new BookEditScreen.Position(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx + 9)
                     )
                  );
               }

               _snowmanxxxxxxxxxxx.add(this.method_27585(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxx], _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx * 9, _snowmanxxxxxxxx[_snowmanxxxxxxxxxxxxxxx]));
            }
         }

         return new BookEditScreen.PageContent(
            _snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxx.toArray(new BookEditScreen.Line[0]), _snowmanxxxxxxxxxxx.toArray(new Rect2i[0])
         );
      }
   }

   private static int method_27591(int[] _snowman, int _snowman) {
      int _snowmanxx = Arrays.binarySearch(_snowman, _snowman);
      return _snowmanxx < 0 ? -(_snowmanxx + 2) : _snowmanxx;
   }

   private Rect2i method_27585(String _snowman, TextHandler _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      String _snowmanxxxxxx = _snowman.substring(_snowman, _snowman);
      String _snowmanxxxxxxx = _snowman.substring(_snowman, _snowman);
      BookEditScreen.Position _snowmanxxxxxxxx = new BookEditScreen.Position((int)_snowman.getWidth(_snowmanxxxxxx), _snowman);
      BookEditScreen.Position _snowmanxxxxxxxxx = new BookEditScreen.Position((int)_snowman.getWidth(_snowmanxxxxxxx), _snowman + 9);
      return this.method_27583(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
   }

   private Rect2i method_27583(BookEditScreen.Position _snowman, BookEditScreen.Position _snowman) {
      BookEditScreen.Position _snowmanxx = this.method_27590(_snowman);
      BookEditScreen.Position _snowmanxxx = this.method_27590(_snowman);
      int _snowmanxxxx = Math.min(_snowmanxx.x, _snowmanxxx.x);
      int _snowmanxxxxx = Math.max(_snowmanxx.x, _snowmanxxx.x);
      int _snowmanxxxxxx = Math.min(_snowmanxx.y, _snowmanxxx.y);
      int _snowmanxxxxxxx = Math.max(_snowmanxx.y, _snowmanxxx.y);
      return new Rect2i(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx - _snowmanxxxx, _snowmanxxxxxxx - _snowmanxxxxxx);
   }

   static class Line {
      private final Style style;
      private final String content;
      private final Text text;
      private final int x;
      private final int y;

      public Line(Style _snowman, String content, int x, int y) {
         this.style = _snowman;
         this.content = content;
         this.x = x;
         this.y = y;
         this.text = new LiteralText(content).setStyle(_snowman);
      }
   }

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

      public PageContent(String pageContent, BookEditScreen.Position _snowman, boolean _snowman, int[] _snowman, BookEditScreen.Line[] lines, Rect2i[] _snowman) {
         this.pageContent = pageContent;
         this.position = _snowman;
         this.field_24274 = _snowman;
         this.field_24275 = _snowman;
         this.lines = lines;
         this.field_24277 = _snowman;
      }

      public int method_27602(TextRenderer _snowman, BookEditScreen.Position _snowman) {
         int _snowmanxx = _snowman.y / 9;
         if (_snowmanxx < 0) {
            return 0;
         } else if (_snowmanxx >= this.lines.length) {
            return this.pageContent.length();
         } else {
            BookEditScreen.Line _snowmanxxx = this.lines[_snowmanxx];
            return this.field_24275[_snowmanxx] + _snowman.getTextHandler().getTrimmedLength(_snowmanxxx.content, _snowman.x, _snowmanxxx.style);
         }
      }

      public int method_27601(int _snowman, int _snowman) {
         int _snowmanxx = BookEditScreen.method_27591(this.field_24275, _snowman);
         int _snowmanxxx = _snowmanxx + _snowman;
         int _snowmanxxxx;
         if (0 <= _snowmanxxx && _snowmanxxx < this.field_24275.length) {
            int _snowmanxxxxx = _snowman - this.field_24275[_snowmanxx];
            int _snowmanxxxxxx = this.lines[_snowmanxxx].content.length();
            _snowmanxxxx = this.field_24275[_snowmanxxx] + Math.min(_snowmanxxxxx, _snowmanxxxxxx);
         } else {
            _snowmanxxxx = _snowman;
         }

         return _snowmanxxxx;
      }

      public int method_27600(int _snowman) {
         int _snowmanx = BookEditScreen.method_27591(this.field_24275, _snowman);
         return this.field_24275[_snowmanx];
      }

      public int method_27604(int _snowman) {
         int _snowmanx = BookEditScreen.method_27591(this.field_24275, _snowman);
         return this.field_24275[_snowmanx] + this.lines[_snowmanx].content.length();
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
