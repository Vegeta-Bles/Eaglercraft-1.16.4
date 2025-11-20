package net.minecraft.client.gui.hud;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ChatHud extends DrawableHelper {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftClient client;
   private final List<String> messageHistory = Lists.newArrayList();
   private final List<ChatHudLine<Text>> messages = Lists.newArrayList();
   private final List<ChatHudLine<OrderedText>> visibleMessages = Lists.newArrayList();
   private final Deque<Text> messageQueue = Queues.newArrayDeque();
   private int scrolledLines;
   private boolean hasUnreadNewMessages;
   private long lastMessageAddedTime = 0L;

   public ChatHud(MinecraftClient client) {
      this.client = client;
   }

   public void render(MatrixStack matrices, int tickDelta) {
      if (!this.isChatHidden()) {
         this.processMessageQueue();
         int j = this.getVisibleLineCount();
         int k = this.visibleMessages.size();
         if (k > 0) {
            boolean bl = false;
            if (this.isChatFocused()) {
               bl = true;
            }

            double d = this.getChatScale();
            int l = MathHelper.ceil((double)this.getWidth() / d);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(2.0F, 8.0F, 0.0F);
            RenderSystem.scaled(d, d, 1.0);
            double e = this.client.options.chatOpacity * 0.9F + 0.1F;
            double f = this.client.options.textBackgroundOpacity;
            double g = 9.0 * (this.client.options.chatLineSpacing + 1.0);
            double h = -8.0 * (this.client.options.chatLineSpacing + 1.0) + 4.0 * this.client.options.chatLineSpacing;
            int m = 0;

            for (int n = 0; n + this.scrolledLines < this.visibleMessages.size() && n < j; n++) {
               ChatHudLine<OrderedText> lv = this.visibleMessages.get(n + this.scrolledLines);
               if (lv != null) {
                  int o = tickDelta - lv.getCreationTick();
                  if (o < 200 || bl) {
                     double p = bl ? 1.0 : getMessageOpacityMultiplier(o);
                     int q = (int)(255.0 * p * e);
                     int r = (int)(255.0 * p * f);
                     m++;
                     if (q > 3) {
                        int s = 0;
                        double t = (double)(-n) * g;
                        matrices.push();
                        matrices.translate(0.0, 0.0, 50.0);
                        fill(matrices, -2, (int)(t - g), 0 + l + 4, (int)t, r << 24);
                        RenderSystem.enableBlend();
                        matrices.translate(0.0, 0.0, 50.0);
                        this.client.textRenderer.drawWithShadow(matrices, lv.getText(), 0.0F, (float)((int)(t + h)), 16777215 + (q << 24));
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                        matrices.pop();
                     }
                  }
               }
            }

            if (!this.messageQueue.isEmpty()) {
               int u = (int)(128.0 * e);
               int v = (int)(255.0 * f);
               matrices.push();
               matrices.translate(0.0, 0.0, 50.0);
               fill(matrices, -2, 0, l + 4, 9, v << 24);
               RenderSystem.enableBlend();
               matrices.translate(0.0, 0.0, 50.0);
               this.client
                  .textRenderer
                  .drawWithShadow(matrices, new TranslatableText("chat.queue", this.messageQueue.size()), 0.0F, 1.0F, 16777215 + (u << 24));
               matrices.pop();
               RenderSystem.disableAlphaTest();
               RenderSystem.disableBlend();
            }

            if (bl) {
               int w = 9;
               RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
               int x = k * w + k;
               int y = m * w + m;
               int z = this.scrolledLines * y / k;
               int aa = y * y / x;
               if (x != y) {
                  int ab = z > 0 ? 170 : 96;
                  int ac = this.hasUnreadNewMessages ? 13382451 : 3355562;
                  fill(matrices, 0, -z, 2, -z - aa, ac + (ab << 24));
                  fill(matrices, 2, -z, 1, -z - aa, 13421772 + (ab << 24));
               }
            }

            RenderSystem.popMatrix();
         }
      }
   }

   private boolean isChatHidden() {
      return this.client.options.chatVisibility == ChatVisibility.HIDDEN;
   }

   private static double getMessageOpacityMultiplier(int age) {
      double d = (double)age / 200.0;
      d = 1.0 - d;
      d *= 10.0;
      d = MathHelper.clamp(d, 0.0, 1.0);
      return d * d;
   }

   public void clear(boolean clearHistory) {
      this.messageQueue.clear();
      this.visibleMessages.clear();
      this.messages.clear();
      if (clearHistory) {
         this.messageHistory.clear();
      }
   }

   public void addMessage(Text message) {
      this.addMessage(message, 0);
   }

   private void addMessage(Text message, int messageId) {
      this.addMessage(message, messageId, this.client.inGameHud.getTicks(), false);
      LOGGER.info("[CHAT] {}", message.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void addMessage(Text message, int messageId, int timestamp, boolean refresh) {
      if (messageId != 0) {
         this.removeMessage(messageId);
      }

      int k = MathHelper.floor((double)this.getWidth() / this.getChatScale());
      List<OrderedText> list = ChatMessages.breakRenderedChatMessageLines(message, k, this.client.textRenderer);
      boolean bl2 = this.isChatFocused();

      for (OrderedText lv : list) {
         if (bl2 && this.scrolledLines > 0) {
            this.hasUnreadNewMessages = true;
            this.scroll(1.0);
         }

         this.visibleMessages.add(0, new ChatHudLine<>(timestamp, lv, messageId));
      }

      while (this.visibleMessages.size() > 100) {
         this.visibleMessages.remove(this.visibleMessages.size() - 1);
      }

      if (!refresh) {
         this.messages.add(0, new ChatHudLine<>(timestamp, message, messageId));

         while (this.messages.size() > 100) {
            this.messages.remove(this.messages.size() - 1);
         }
      }
   }

   public void reset() {
      this.visibleMessages.clear();
      this.resetScroll();

      for (int i = this.messages.size() - 1; i >= 0; i--) {
         ChatHudLine<Text> lv = this.messages.get(i);
         this.addMessage(lv.getText(), lv.getId(), lv.getCreationTick(), true);
      }
   }

   public List<String> getMessageHistory() {
      return this.messageHistory;
   }

   public void addToMessageHistory(String message) {
      if (this.messageHistory.isEmpty() || !this.messageHistory.get(this.messageHistory.size() - 1).equals(message)) {
         this.messageHistory.add(message);
      }
   }

   public void resetScroll() {
      this.scrolledLines = 0;
      this.hasUnreadNewMessages = false;
   }

   public void scroll(double amount) {
      this.scrolledLines = (int)((double)this.scrolledLines + amount);
      int i = this.visibleMessages.size();
      if (this.scrolledLines > i - this.getVisibleLineCount()) {
         this.scrolledLines = i - this.getVisibleLineCount();
      }

      if (this.scrolledLines <= 0) {
         this.scrolledLines = 0;
         this.hasUnreadNewMessages = false;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY) {
      if (this.isChatFocused() && !this.client.options.hudHidden && !this.isChatHidden() && !this.messageQueue.isEmpty()) {
         double f = mouseX - 2.0;
         double g = (double)this.client.getWindow().getScaledHeight() - mouseY - 40.0;
         if (f <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale())
            && g < 0.0
            && g > (double)MathHelper.floor(-9.0 * this.getChatScale())) {
            this.addMessage(this.messageQueue.remove());
            this.lastMessageAddedTime = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public Style getText(double x, double y) {
      if (this.isChatFocused() && !this.client.options.hudHidden && !this.isChatHidden()) {
         double f = x - 2.0;
         double g = (double)this.client.getWindow().getScaledHeight() - y - 40.0;
         f = (double)MathHelper.floor(f / this.getChatScale());
         g = (double)MathHelper.floor(g / (this.getChatScale() * (this.client.options.chatLineSpacing + 1.0)));
         if (!(f < 0.0) && !(g < 0.0)) {
            int i = Math.min(this.getVisibleLineCount(), this.visibleMessages.size());
            if (f <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale()) && g < (double)(9 * i + i)) {
               int j = (int)(g / 9.0 + (double)this.scrolledLines);
               if (j >= 0 && j < this.visibleMessages.size()) {
                  ChatHudLine<OrderedText> lv = this.visibleMessages.get(j);
                  return this.client.textRenderer.getTextHandler().getStyleAt(lv.getText(), (int)f);
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean isChatFocused() {
      return this.client.currentScreen instanceof ChatScreen;
   }

   private void removeMessage(int messageId) {
      this.visibleMessages.removeIf(message -> message.getId() == messageId);
      this.messages.removeIf(message -> message.getId() == messageId);
   }

   public int getWidth() {
      return getWidth(this.client.options.chatWidth);
   }

   public int getHeight() {
      return getHeight(
         (this.isChatFocused() ? this.client.options.chatHeightFocused : this.client.options.chatHeightUnfocused) / (this.client.options.chatLineSpacing + 1.0)
      );
   }

   public double getChatScale() {
      return this.client.options.chatScale;
   }

   public static int getWidth(double widthOption) {
      int i = 320;
      int j = 40;
      return MathHelper.floor(widthOption * 280.0 + 40.0);
   }

   public static int getHeight(double heightOption) {
      int i = 180;
      int j = 20;
      return MathHelper.floor(heightOption * 160.0 + 20.0);
   }

   public int getVisibleLineCount() {
      return this.getHeight() / 9;
   }

   private long getChatDelayMillis() {
      return (long)(this.client.options.chatDelay * 1000.0);
   }

   private void processMessageQueue() {
      if (!this.messageQueue.isEmpty()) {
         long l = System.currentTimeMillis();
         if (l - this.lastMessageAddedTime >= this.getChatDelayMillis()) {
            this.addMessage(this.messageQueue.remove());
            this.lastMessageAddedTime = l;
         }
      }
   }

   public void queueMessage(Text message) {
      if (this.client.options.chatDelay <= 0.0) {
         this.addMessage(message);
      } else {
         long l = System.currentTimeMillis();
         if (l - this.lastMessageAddedTime >= this.getChatDelayMillis()) {
            this.addMessage(message);
            this.lastMessageAddedTime = l;
         } else {
            this.messageQueue.add(message);
         }
      }
   }
}
