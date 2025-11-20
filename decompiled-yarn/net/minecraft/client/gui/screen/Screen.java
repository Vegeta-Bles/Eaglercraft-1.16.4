package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.Matrix4f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Screen extends AbstractParentElement implements TickableElement, Drawable {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet(new String[]{"http", "https"});
   protected final Text title;
   protected final List<Element> children = Lists.newArrayList();
   @Nullable
   protected MinecraftClient client;
   protected ItemRenderer itemRenderer;
   public int width;
   public int height;
   protected final List<AbstractButtonWidget> buttons = Lists.newArrayList();
   public boolean passEvents;
   protected TextRenderer textRenderer;
   private URI clickedLink;

   protected Screen(Text title) {
      this.title = title;
   }

   public Text getTitle() {
      return this.title;
   }

   public String getNarrationMessage() {
      return this.getTitle().getString();
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      for (int _snowman = 0; _snowman < this.buttons.size(); _snowman++) {
         this.buttons.get(_snowman).render(matrices, mouseX, mouseY, delta);
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256 && this.shouldCloseOnEsc()) {
         this.onClose();
         return true;
      } else if (keyCode == 258) {
         boolean _snowman = !hasShiftDown();
         if (!this.changeFocus(_snowman)) {
            this.changeFocus(_snowman);
         }

         return false;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean shouldCloseOnEsc() {
      return true;
   }

   public void onClose() {
      this.client.openScreen(null);
   }

   protected <T extends AbstractButtonWidget> T addButton(T button) {
      this.buttons.add(button);
      return this.addChild(button);
   }

   protected <T extends Element> T addChild(T child) {
      this.children.add(child);
      return child;
   }

   protected void renderTooltip(MatrixStack matrices, ItemStack stack, int x, int y) {
      this.renderTooltip(matrices, this.getTooltipFromItem(stack), x, y);
   }

   public List<Text> getTooltipFromItem(ItemStack stack) {
      return stack.getTooltip(this.client.player, this.client.options.advancedItemTooltips ? TooltipContext.Default.ADVANCED : TooltipContext.Default.NORMAL);
   }

   public void renderTooltip(MatrixStack matrices, Text text, int x, int y) {
      this.renderOrderedTooltip(matrices, Arrays.asList(text.asOrderedText()), x, y);
   }

   public void renderTooltip(MatrixStack matrices, List<Text> lines, int x, int y) {
      this.renderOrderedTooltip(matrices, Lists.transform(lines, Text::asOrderedText), x, y);
   }

   public void renderOrderedTooltip(MatrixStack matrices, List<? extends OrderedText> lines, int x, int y) {
      if (!lines.isEmpty()) {
         int _snowman = 0;

         for (OrderedText _snowmanx : lines) {
            int _snowmanxx = this.textRenderer.getWidth(_snowmanx);
            if (_snowmanxx > _snowman) {
               _snowman = _snowmanxx;
            }
         }

         int _snowmanxx = x + 12;
         int _snowmanxxx = y - 12;
         int _snowmanxxxx = 8;
         if (lines.size() > 1) {
            _snowmanxxxx += 2 + (lines.size() - 1) * 10;
         }

         if (_snowmanxx + _snowman > this.width) {
            _snowmanxx -= 28 + _snowman;
         }

         if (_snowmanxxx + _snowmanxxxx + 6 > this.height) {
            _snowmanxxx = this.height - _snowmanxxxx - 6;
         }

         matrices.push();
         int _snowmanxxxxx = -267386864;
         int _snowmanxxxxxx = 1347420415;
         int _snowmanxxxxxxx = 1344798847;
         int _snowmanxxxxxxxx = 400;
         Tessellator _snowmanxxxxxxxxx = Tessellator.getInstance();
         BufferBuilder _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getBuffer();
         _snowmanxxxxxxxxxx.begin(7, VertexFormats.POSITION_COLOR);
         Matrix4f _snowmanxxxxxxxxxxx = matrices.peek().getModel();
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 4, _snowmanxx + _snowman + 3, _snowmanxxx - 3, 400, -267386864, -267386864);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 3, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 4, 400, -267386864, -267386864);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 4, _snowmanxxx - 3, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx + _snowman + 3, _snowmanxxx - 3, _snowmanxx + _snowman + 4, _snowmanxxx + _snowmanxxxx + 3, 400, -267386864, -267386864);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3 + 1, _snowmanxx - 3 + 1, _snowmanxxx + _snowmanxxxx + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx + _snowman + 2, _snowmanxxx - 3 + 1, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx - 3, _snowmanxx + _snowman + 3, _snowmanxxx - 3 + 1, 400, 1347420415, 1347420415);
         fillGradient(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxx - 3, _snowmanxxx + _snowmanxxxx + 2, _snowmanxx + _snowman + 3, _snowmanxxx + _snowmanxxxx + 3, 400, 1344798847, 1344798847);
         RenderSystem.enableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.shadeModel(7425);
         _snowmanxxxxxxxxxx.end();
         BufferRenderer.draw(_snowmanxxxxxxxxxx);
         RenderSystem.shadeModel(7424);
         RenderSystem.disableBlend();
         RenderSystem.enableTexture();
         VertexConsumerProvider.Immediate _snowmanxxxxxxxxxxxx = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         matrices.translate(0.0, 0.0, 400.0);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < lines.size(); _snowmanxxxxxxxxxxxxx++) {
            OrderedText _snowmanxxxxxxxxxxxxxx = lines.get(_snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx != null) {
               this.textRenderer.draw(_snowmanxxxxxxxxxxxxxx, (float)_snowmanxx, (float)_snowmanxxx, -1, true, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, false, 0, 15728880);
            }

            if (_snowmanxxxxxxxxxxxxx == 0) {
               _snowmanxxx += 2;
            }

            _snowmanxxx += 10;
         }

         _snowmanxxxxxxxxxxxx.draw();
         matrices.pop();
      }
   }

   protected void renderTextHoverEffect(MatrixStack matrices, @Nullable Style style, int x, int y) {
      if (style != null && style.getHoverEvent() != null) {
         HoverEvent _snowman = style.getHoverEvent();
         HoverEvent.ItemStackContent _snowmanx = _snowman.getValue(HoverEvent.Action.SHOW_ITEM);
         if (_snowmanx != null) {
            this.renderTooltip(matrices, _snowmanx.asStack(), x, y);
         } else {
            HoverEvent.EntityContent _snowmanxx = _snowman.getValue(HoverEvent.Action.SHOW_ENTITY);
            if (_snowmanxx != null) {
               if (this.client.options.advancedItemTooltips) {
                  this.renderTooltip(matrices, _snowmanxx.asTooltip(), x, y);
               }
            } else {
               Text _snowmanxxx = _snowman.getValue(HoverEvent.Action.SHOW_TEXT);
               if (_snowmanxxx != null) {
                  this.renderOrderedTooltip(matrices, this.client.textRenderer.wrapLines(_snowmanxxx, Math.max(this.width / 2, 200)), x, y);
               }
            }
         }
      }
   }

   protected void insertText(String text, boolean override) {
   }

   public boolean handleTextClick(@Nullable Style style) {
      if (style == null) {
         return false;
      } else {
         ClickEvent _snowman = style.getClickEvent();
         if (hasShiftDown()) {
            if (style.getInsertion() != null) {
               this.insertText(style.getInsertion(), false);
            }
         } else if (_snowman != null) {
            if (_snowman.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.client.options.chatLinks) {
                  return false;
               }

               try {
                  URI _snowmanx = new URI(_snowman.getValue());
                  String _snowmanxx = _snowmanx.getScheme();
                  if (_snowmanxx == null) {
                     throw new URISyntaxException(_snowman.getValue(), "Missing protocol");
                  }

                  if (!ALLOWED_PROTOCOLS.contains(_snowmanxx.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(_snowman.getValue(), "Unsupported protocol: " + _snowmanxx.toLowerCase(Locale.ROOT));
                  }

                  if (this.client.options.chatLinksPrompt) {
                     this.clickedLink = _snowmanx;
                     this.client.openScreen(new ConfirmChatLinkScreen(this::confirmLink, _snowman.getValue(), false));
                  } else {
                     this.openLink(_snowmanx);
                  }
               } catch (URISyntaxException var5) {
                  LOGGER.error("Can't open url for {}", _snowman, var5);
               }
            } else if (_snowman.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI _snowmanxxx = new File(_snowman.getValue()).toURI();
               this.openLink(_snowmanxxx);
            } else if (_snowman.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.insertText(_snowman.getValue(), true);
            } else if (_snowman.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendMessage(_snowman.getValue(), false);
            } else if (_snowman.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
               this.client.keyboard.setClipboard(_snowman.getValue());
            } else {
               LOGGER.error("Don't know how to handle {}", _snowman);
            }

            return true;
         }

         return false;
      }
   }

   public void sendMessage(String message) {
      this.sendMessage(message, true);
   }

   public void sendMessage(String message, boolean toHud) {
      if (toHud) {
         this.client.inGameHud.getChatHud().addToMessageHistory(message);
      }

      this.client.player.sendChatMessage(message);
   }

   public void init(MinecraftClient client, int width, int height) {
      this.client = client;
      this.itemRenderer = client.getItemRenderer();
      this.textRenderer = client.textRenderer;
      this.width = width;
      this.height = height;
      this.buttons.clear();
      this.children.clear();
      this.setFocused(null);
      this.init();
   }

   @Override
   public List<? extends Element> children() {
      return this.children;
   }

   protected void init() {
   }

   @Override
   public void tick() {
   }

   public void removed() {
   }

   public void renderBackground(MatrixStack matrices) {
      this.renderBackground(matrices, 0);
   }

   public void renderBackground(MatrixStack matrices, int vOffset) {
      if (this.client.world != null) {
         this.fillGradient(matrices, 0, 0, this.width, this.height, -1072689136, -804253680);
      } else {
         this.renderBackgroundTexture(vOffset);
      }
   }

   public void renderBackgroundTexture(int vOffset) {
      Tessellator _snowman = Tessellator.getInstance();
      BufferBuilder _snowmanx = _snowman.getBuffer();
      this.client.getTextureManager().bindTexture(OPTIONS_BACKGROUND_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxx = 32.0F;
      _snowmanx.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
      _snowmanx.vertex(0.0, (double)this.height, 0.0).texture(0.0F, (float)this.height / 32.0F + (float)vOffset).color(64, 64, 64, 255).next();
      _snowmanx.vertex((double)this.width, (double)this.height, 0.0)
         .texture((float)this.width / 32.0F, (float)this.height / 32.0F + (float)vOffset)
         .color(64, 64, 64, 255)
         .next();
      _snowmanx.vertex((double)this.width, 0.0, 0.0).texture((float)this.width / 32.0F, (float)vOffset).color(64, 64, 64, 255).next();
      _snowmanx.vertex(0.0, 0.0, 0.0).texture(0.0F, (float)vOffset).color(64, 64, 64, 255).next();
      _snowman.draw();
   }

   public boolean isPauseScreen() {
      return true;
   }

   private void confirmLink(boolean open) {
      if (open) {
         this.openLink(this.clickedLink);
      }

      this.clickedLink = null;
      this.client.openScreen(this);
   }

   private void openLink(URI link) {
      Util.getOperatingSystem().open(link);
   }

   public static boolean hasControlDown() {
      return MinecraftClient.IS_SYSTEM_MAC
         ? InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 343)
            || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 347)
         : InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 341)
            || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 345);
   }

   public static boolean hasShiftDown() {
      return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340)
         || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
   }

   public static boolean hasAltDown() {
      return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 342)
         || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 346);
   }

   public static boolean isCut(int code) {
      return code == 88 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isPaste(int code) {
      return code == 86 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isCopy(int code) {
      return code == 67 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public static boolean isSelectAll(int code) {
      return code == 65 && hasControlDown() && !hasShiftDown() && !hasAltDown();
   }

   public void resize(MinecraftClient client, int width, int height) {
      this.init(client, width, height);
   }

   public static void wrapScreenError(Runnable task, String errorTitle, String screenName) {
      try {
         task.run();
      } catch (Throwable var6) {
         CrashReport _snowman = CrashReport.create(var6, errorTitle);
         CrashReportSection _snowmanx = _snowman.addElement("Affected screen");
         _snowmanx.add("Screen name", () -> screenName);
         throw new CrashException(_snowman);
      }
   }

   protected boolean isValidCharacterForName(String name, char character, int cursorPos) {
      int _snowman = name.indexOf(58);
      int _snowmanx = name.indexOf(47);
      if (character == ':') {
         return (_snowmanx == -1 || cursorPos <= _snowmanx) && _snowman == -1;
      } else {
         return character == '/'
            ? cursorPos > _snowman
            : character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
      }
   }

   @Override
   public boolean isMouseOver(double mouseX, double mouseY) {
      return true;
   }

   public void filesDragged(List<Path> paths) {
   }
}
