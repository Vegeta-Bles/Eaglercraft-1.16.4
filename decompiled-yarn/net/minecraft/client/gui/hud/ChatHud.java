package net.minecraft.client.gui.hud;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
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
         int _snowman = this.getVisibleLineCount();
         int _snowmanx = this.visibleMessages.size();
         if (_snowmanx > 0) {
            boolean _snowmanxx = false;
            if (this.isChatFocused()) {
               _snowmanxx = true;
            }

            double _snowmanxxx = this.getChatScale();
            int _snowmanxxxx = MathHelper.ceil((double)this.getWidth() / _snowmanxxx);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(2.0F, 8.0F, 0.0F);
            RenderSystem.scaled(_snowmanxxx, _snowmanxxx, 1.0);
            double _snowmanxxxxx = this.client.options.chatOpacity * 0.9F + 0.1F;
            double _snowmanxxxxxx = this.client.options.textBackgroundOpacity;
            double _snowmanxxxxxxx = 9.0 * (this.client.options.chatLineSpacing + 1.0);
            double _snowmanxxxxxxxx = -8.0 * (this.client.options.chatLineSpacing + 1.0) + 4.0 * this.client.options.chatLineSpacing;
            int _snowmanxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx + this.scrolledLines < this.visibleMessages.size() && _snowmanxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxx++) {
               ChatHudLine<OrderedText> _snowmanxxxxxxxxxxx = this.visibleMessages.get(_snowmanxxxxxxxxxx + this.scrolledLines);
               if (_snowmanxxxxxxxxxxx != null) {
                  int _snowmanxxxxxxxxxxxx = tickDelta - _snowmanxxxxxxxxxxx.getCreationTick();
                  if (_snowmanxxxxxxxxxxxx < 200 || _snowmanxx) {
                     double _snowmanxxxxxxxxxxxxx = _snowmanxx ? 1.0 : getMessageOpacityMultiplier(_snowmanxxxxxxxxxxxx);
                     int _snowmanxxxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxxxxxxxxx * _snowmanxxxxx);
                     int _snowmanxxxxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxxxxxxxxx * _snowmanxxxxxx);
                     _snowmanxxxxxxxxx++;
                     if (_snowmanxxxxxxxxxxxxxx > 3) {
                        int _snowmanxxxxxxxxxxxxxxxx = 0;
                        double _snowmanxxxxxxxxxxxxxxxxx = (double)(-_snowmanxxxxxxxxxx) * _snowmanxxxxxxx;
                        matrices.push();
                        matrices.translate(0.0, 0.0, 50.0);
                        fill(matrices, -2, (int)(_snowmanxxxxxxxxxxxxxxxxx - _snowmanxxxxxxx), 0 + _snowmanxxxx + 4, (int)_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx << 24);
                        RenderSystem.enableBlend();
                        matrices.translate(0.0, 0.0, 50.0);
                        this.client
                           .textRenderer
                           .drawWithShadow(
                              matrices, _snowmanxxxxxxxxxxx.getText(), 0.0F, (float)((int)(_snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxx)), 16777215 + (_snowmanxxxxxxxxxxxxxx << 24)
                           );
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                        matrices.pop();
                     }
                  }
               }
            }

            if (!this.messageQueue.isEmpty()) {
               int _snowmanxxxxxxxxxxx = (int)(128.0 * _snowmanxxxxx);
               int _snowmanxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxx);
               matrices.push();
               matrices.translate(0.0, 0.0, 50.0);
               fill(matrices, -2, 0, _snowmanxxxx + 4, 9, _snowmanxxxxxxxxxxxx << 24);
               RenderSystem.enableBlend();
               matrices.translate(0.0, 0.0, 50.0);
               this.client
                  .textRenderer
                  .drawWithShadow(matrices, new TranslatableText("chat.queue", this.messageQueue.size()), 0.0F, 1.0F, 16777215 + (_snowmanxxxxxxxxxxx << 24));
               matrices.pop();
               RenderSystem.disableAlphaTest();
               RenderSystem.disableBlend();
            }

            if (_snowmanxx) {
               int _snowmanxxxxxxxxxxx = 9;
               RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
               int _snowmanxxxxxxxxxxxx = _snowmanx * _snowmanxxxxxxxxxxx + _snowmanx;
               int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxx = this.scrolledLines * _snowmanxxxxxxxxxxxxx / _snowmanx;
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxx) {
                  int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx > 0 ? 170 : 96;
                  int _snowmanxxxxxxxxxxxxxxxxx = this.hasUnreadNewMessages ? 13382451 : 3355562;
                  fill(matrices, 0, -_snowmanxxxxxxxxxxxxxx, 2, -_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxx << 24));
                  fill(matrices, 2, -_snowmanxxxxxxxxxxxxxx, 1, -_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx, 13421772 + (_snowmanxxxxxxxxxxxxxxxx << 24));
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
      double _snowman = (double)age / 200.0;
      _snowman = 1.0 - _snowman;
      _snowman *= 10.0;
      _snowman = MathHelper.clamp(_snowman, 0.0, 1.0);
      return _snowman * _snowman;
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

      int _snowman = MathHelper.floor((double)this.getWidth() / this.getChatScale());
      List<OrderedText> _snowmanx = ChatMessages.breakRenderedChatMessageLines(message, _snowman, this.client.textRenderer);
      boolean _snowmanxx = this.isChatFocused();

      for (OrderedText _snowmanxxx : _snowmanx) {
         if (_snowmanxx && this.scrolledLines > 0) {
            this.hasUnreadNewMessages = true;
            this.scroll(1.0);
         }

         this.visibleMessages.add(0, new ChatHudLine<>(timestamp, _snowmanxxx, messageId));
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

      for (int _snowman = this.messages.size() - 1; _snowman >= 0; _snowman--) {
         ChatHudLine<Text> _snowmanx = this.messages.get(_snowman);
         this.addMessage(_snowmanx.getText(), _snowmanx.getId(), _snowmanx.getCreationTick(), true);
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
      int _snowman = this.visibleMessages.size();
      if (this.scrolledLines > _snowman - this.getVisibleLineCount()) {
         this.scrolledLines = _snowman - this.getVisibleLineCount();
      }

      if (this.scrolledLines <= 0) {
         this.scrolledLines = 0;
         this.hasUnreadNewMessages = false;
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY) {
      if (this.isChatFocused() && !this.client.options.hudHidden && !this.isChatHidden() && !this.messageQueue.isEmpty()) {
         double _snowman = mouseX - 2.0;
         double _snowmanx = (double)this.client.getWindow().getScaledHeight() - mouseY - 40.0;
         if (_snowman <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale())
            && _snowmanx < 0.0
            && _snowmanx > (double)MathHelper.floor(-9.0 * this.getChatScale())) {
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
         double _snowman = x - 2.0;
         double _snowmanx = (double)this.client.getWindow().getScaledHeight() - y - 40.0;
         _snowman = (double)MathHelper.floor(_snowman / this.getChatScale());
         _snowmanx = (double)MathHelper.floor(_snowmanx / (this.getChatScale() * (this.client.options.chatLineSpacing + 1.0)));
         if (!(_snowman < 0.0) && !(_snowmanx < 0.0)) {
            int _snowmanxx = Math.min(this.getVisibleLineCount(), this.visibleMessages.size());
            if (_snowman <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale()) && _snowmanx < (double)(9 * _snowmanxx + _snowmanxx)) {
               int _snowmanxxx = (int)(_snowmanx / 9.0 + (double)this.scrolledLines);
               if (_snowmanxxx >= 0 && _snowmanxxx < this.visibleMessages.size()) {
                  ChatHudLine<OrderedText> _snowmanxxxx = this.visibleMessages.get(_snowmanxxx);
                  return this.client.textRenderer.getTextHandler().getStyleAt(_snowmanxxxx.getText(), (int)_snowman);
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
      int _snowman = 320;
      int _snowmanx = 40;
      return MathHelper.floor(widthOption * 280.0 + 40.0);
   }

   public static int getHeight(double heightOption) {
      int _snowman = 180;
      int _snowmanx = 20;
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
         long _snowman = System.currentTimeMillis();
         if (_snowman - this.lastMessageAddedTime >= this.getChatDelayMillis()) {
            this.addMessage(this.messageQueue.remove());
            this.lastMessageAddedTime = _snowman;
         }
      }
   }

   public void queueMessage(Text message) {
      if (this.client.options.chatDelay <= 0.0) {
         this.addMessage(message);
      } else {
         long _snowman = System.currentTimeMillis();
         if (_snowman - this.lastMessageAddedTime >= this.getChatDelayMillis()) {
            this.addMessage(message);
            this.lastMessageAddedTime = _snowman;
         } else {
            this.messageQueue.add(message);
         }
      }
   }
}
