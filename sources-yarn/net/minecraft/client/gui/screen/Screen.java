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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      for (int k = 0; k < this.buttons.size(); k++) {
         this.buttons.get(k).render(matrices, mouseX, mouseY, delta);
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256 && this.shouldCloseOnEsc()) {
         this.onClose();
         return true;
      } else if (keyCode == 258) {
         boolean bl = !hasShiftDown();
         if (!this.changeFocus(bl)) {
            this.changeFocus(bl);
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
         int k = 0;

         for (OrderedText lv : lines) {
            int l = this.textRenderer.getWidth(lv);
            if (l > k) {
               k = l;
            }
         }

         int m = x + 12;
         int n = y - 12;
         int p = 8;
         if (lines.size() > 1) {
            p += 2 + (lines.size() - 1) * 10;
         }

         if (m + k > this.width) {
            m -= 28 + k;
         }

         if (n + p + 6 > this.height) {
            n = this.height - p - 6;
         }

         matrices.push();
         int q = -267386864;
         int r = 1347420415;
         int s = 1344798847;
         int t = 400;
         Tessellator lv2 = Tessellator.getInstance();
         BufferBuilder lv3 = lv2.getBuffer();
         lv3.begin(7, VertexFormats.POSITION_COLOR);
         Matrix4f lv4 = matrices.peek().getModel();
         fillGradient(lv4, lv3, m - 3, n - 4, m + k + 3, n - 3, 400, -267386864, -267386864);
         fillGradient(lv4, lv3, m - 3, n + p + 3, m + k + 3, n + p + 4, 400, -267386864, -267386864);
         fillGradient(lv4, lv3, m - 3, n - 3, m + k + 3, n + p + 3, 400, -267386864, -267386864);
         fillGradient(lv4, lv3, m - 4, n - 3, m - 3, n + p + 3, 400, -267386864, -267386864);
         fillGradient(lv4, lv3, m + k + 3, n - 3, m + k + 4, n + p + 3, 400, -267386864, -267386864);
         fillGradient(lv4, lv3, m - 3, n - 3 + 1, m - 3 + 1, n + p + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(lv4, lv3, m + k + 2, n - 3 + 1, m + k + 3, n + p + 3 - 1, 400, 1347420415, 1344798847);
         fillGradient(lv4, lv3, m - 3, n - 3, m + k + 3, n - 3 + 1, 400, 1347420415, 1347420415);
         fillGradient(lv4, lv3, m - 3, n + p + 2, m + k + 3, n + p + 3, 400, 1344798847, 1344798847);
         RenderSystem.enableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.shadeModel(7425);
         lv3.end();
         BufferRenderer.draw(lv3);
         RenderSystem.shadeModel(7424);
         RenderSystem.disableBlend();
         RenderSystem.enableTexture();
         VertexConsumerProvider.Immediate lv5 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
         matrices.translate(0.0, 0.0, 400.0);

         for (int u = 0; u < lines.size(); u++) {
            OrderedText lv6 = lines.get(u);
            if (lv6 != null) {
               this.textRenderer.draw(lv6, (float)m, (float)n, -1, true, lv4, lv5, false, 0, 15728880);
            }

            if (u == 0) {
               n += 2;
            }

            n += 10;
         }

         lv5.draw();
         matrices.pop();
      }
   }

   protected void renderTextHoverEffect(MatrixStack matrices, @Nullable Style style, int x, int y) {
      if (style != null && style.getHoverEvent() != null) {
         HoverEvent lv = style.getHoverEvent();
         HoverEvent.ItemStackContent lv2 = lv.getValue(HoverEvent.Action.SHOW_ITEM);
         if (lv2 != null) {
            this.renderTooltip(matrices, lv2.asStack(), x, y);
         } else {
            HoverEvent.EntityContent lv3 = lv.getValue(HoverEvent.Action.SHOW_ENTITY);
            if (lv3 != null) {
               if (this.client.options.advancedItemTooltips) {
                  this.renderTooltip(matrices, lv3.asTooltip(), x, y);
               }
            } else {
               Text lv4 = lv.getValue(HoverEvent.Action.SHOW_TEXT);
               if (lv4 != null) {
                  this.renderOrderedTooltip(matrices, this.client.textRenderer.wrapLines(lv4, Math.max(this.width / 2, 200)), x, y);
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
         ClickEvent lv = style.getClickEvent();
         if (hasShiftDown()) {
            if (style.getInsertion() != null) {
               this.insertText(style.getInsertion(), false);
            }
         } else if (lv != null) {
            if (lv.getAction() == ClickEvent.Action.OPEN_URL) {
               if (!this.client.options.chatLinks) {
                  return false;
               }

               try {
                  URI uRI = new URI(lv.getValue());
                  String string = uRI.getScheme();
                  if (string == null) {
                     throw new URISyntaxException(lv.getValue(), "Missing protocol");
                  }

                  if (!ALLOWED_PROTOCOLS.contains(string.toLowerCase(Locale.ROOT))) {
                     throw new URISyntaxException(lv.getValue(), "Unsupported protocol: " + string.toLowerCase(Locale.ROOT));
                  }

                  if (this.client.options.chatLinksPrompt) {
                     this.clickedLink = uRI;
                     this.client.openScreen(new ConfirmChatLinkScreen(this::confirmLink, lv.getValue(), false));
                  } else {
                     this.openLink(uRI);
                  }
               } catch (URISyntaxException var5) {
                  LOGGER.error("Can't open url for {}", lv, var5);
               }
            } else if (lv.getAction() == ClickEvent.Action.OPEN_FILE) {
               URI uRI2 = new File(lv.getValue()).toURI();
               this.openLink(uRI2);
            } else if (lv.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
               this.insertText(lv.getValue(), true);
            } else if (lv.getAction() == ClickEvent.Action.RUN_COMMAND) {
               this.sendMessage(lv.getValue(), false);
            } else if (lv.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
               this.client.keyboard.setClipboard(lv.getValue());
            } else {
               LOGGER.error("Don't know how to handle {}", lv);
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
      Tessellator lv = Tessellator.getInstance();
      BufferBuilder lv2 = lv.getBuffer();
      this.client.getTextureManager().bindTexture(OPTIONS_BACKGROUND_TEXTURE);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = 32.0F;
      lv2.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
      lv2.vertex(0.0, (double)this.height, 0.0).texture(0.0F, (float)this.height / 32.0F + (float)vOffset).color(64, 64, 64, 255).next();
      lv2.vertex((double)this.width, (double)this.height, 0.0)
         .texture((float)this.width / 32.0F, (float)this.height / 32.0F + (float)vOffset)
         .color(64, 64, 64, 255)
         .next();
      lv2.vertex((double)this.width, 0.0, 0.0).texture((float)this.width / 32.0F, (float)vOffset).color(64, 64, 64, 255).next();
      lv2.vertex(0.0, 0.0, 0.0).texture(0.0F, (float)vOffset).color(64, 64, 64, 255).next();
      lv.draw();
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
         CrashReport lv = CrashReport.create(var6, errorTitle);
         CrashReportSection lv2 = lv.addElement("Affected screen");
         lv2.add("Screen name", () -> screenName);
         throw new CrashException(lv);
      }
   }

   protected boolean isValidCharacterForName(String name, char character, int cursorPos) {
      int j = name.indexOf(58);
      int k = name.indexOf(47);
      if (character == ':') {
         return (k == -1 || cursorPos <= k) && j == -1;
      } else {
         return character == '/'
            ? cursorPos > j
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
