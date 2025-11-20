/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.hud;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHudLine;
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

public class ChatHud
extends DrawableHelper {
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
        int n;
        int _snowman10;
        int _snowman9;
        if (this.isChatHidden()) {
            return;
        }
        this.processMessageQueue();
        int n2 = this.getVisibleLineCount();
        _snowman = this.visibleMessages.size();
        if (_snowman <= 0) {
            return;
        }
        boolean _snowman2 = false;
        if (this.isChatFocused()) {
            _snowman2 = true;
        }
        double _snowman3 = this.getChatScale();
        _snowman = MathHelper.ceil((double)this.getWidth() / _snowman3);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(2.0f, 8.0f, 0.0f);
        RenderSystem.scaled(_snowman3, _snowman3, 1.0);
        double _snowman4 = this.client.options.chatOpacity * (double)0.9f + (double)0.1f;
        double _snowman5 = this.client.options.textBackgroundOpacity;
        double _snowman6 = 9.0 * (this.client.options.chatLineSpacing + 1.0);
        double _snowman7 = -8.0 * (this.client.options.chatLineSpacing + 1.0) + 4.0 * this.client.options.chatLineSpacing;
        _snowman = 0;
        for (n = 0; n + this.scrolledLines < this.visibleMessages.size() && n < n2; ++n) {
            ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(n + this.scrolledLines);
            if (chatHudLine == null || (_snowman = tickDelta - chatHudLine.getCreationTick()) >= 200 && !_snowman2) continue;
            double _snowman8 = _snowman2 ? 1.0 : ChatHud.getMessageOpacityMultiplier(_snowman);
            _snowman9 = (int)(255.0 * _snowman8 * _snowman4);
            _snowman10 = (int)(255.0 * _snowman8 * _snowman5);
            ++_snowman;
            if (_snowman9 <= 3) continue;
            boolean _snowman11 = false;
            double _snowman12 = (double)(-n) * _snowman6;
            matrices.push();
            matrices.translate(0.0, 0.0, 50.0);
            ChatHud.fill(matrices, -2, (int)(_snowman12 - _snowman6), 0 + _snowman + 4, (int)_snowman12, _snowman10 << 24);
            RenderSystem.enableBlend();
            matrices.translate(0.0, 0.0, 50.0);
            this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0.0f, (float)((int)(_snowman12 + _snowman7)), 0xFFFFFF + (_snowman9 << 24));
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
            matrices.pop();
        }
        if (!this.messageQueue.isEmpty()) {
            n = (int)(128.0 * _snowman4);
            _snowman = (int)(255.0 * _snowman5);
            matrices.push();
            matrices.translate(0.0, 0.0, 50.0);
            ChatHud.fill(matrices, -2, 0, _snowman + 4, 9, _snowman << 24);
            RenderSystem.enableBlend();
            matrices.translate(0.0, 0.0, 50.0);
            this.client.textRenderer.drawWithShadow(matrices, new TranslatableText("chat.queue", this.messageQueue.size()), 0.0f, 1.0f, 0xFFFFFF + (n << 24));
            matrices.pop();
            RenderSystem.disableAlphaTest();
            RenderSystem.disableBlend();
        }
        if (_snowman2) {
            n = this.client.textRenderer.fontHeight;
            RenderSystem.translatef(-3.0f, 0.0f, 0.0f);
            _snowman = _snowman * n + _snowman;
            _snowman = _snowman * n + _snowman;
            _snowman = this.scrolledLines * _snowman / _snowman;
            _snowman = _snowman * _snowman / _snowman;
            if (_snowman != _snowman) {
                _snowman9 = _snowman > 0 ? 170 : 96;
                _snowman10 = this.hasUnreadNewMessages ? 0xCC3333 : 0x3333AA;
                ChatHud.fill(matrices, 0, -_snowman, 2, -_snowman - _snowman, _snowman10 + (_snowman9 << 24));
                ChatHud.fill(matrices, 2, -_snowman, 1, -_snowman - _snowman, 0xCCCCCC + (_snowman9 << 24));
            }
        }
        RenderSystem.popMatrix();
    }

    private boolean isChatHidden() {
        return this.client.options.chatVisibility == ChatVisibility.HIDDEN;
    }

    private static double getMessageOpacityMultiplier(int age) {
        double d = (double)age / 200.0;
        d = 1.0 - d;
        d *= 10.0;
        d = MathHelper.clamp(d, 0.0, 1.0);
        d *= d;
        return d;
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
        LOGGER.info("[CHAT] {}", (Object)message.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
    }

    private void addMessage(Text message, int messageId, int timestamp, boolean refresh) {
        if (messageId != 0) {
            this.removeMessage(messageId);
        }
        int n = MathHelper.floor((double)this.getWidth() / this.getChatScale());
        List<OrderedText> _snowman2 = ChatMessages.breakRenderedChatMessageLines(message, n, this.client.textRenderer);
        boolean _snowman3 = this.isChatFocused();
        for (OrderedText orderedText : _snowman2) {
            if (_snowman3 && this.scrolledLines > 0) {
                this.hasUnreadNewMessages = true;
                this.scroll(1.0);
            }
            this.visibleMessages.add(0, new ChatHudLine<OrderedText>(timestamp, orderedText, messageId));
        }
        while (this.visibleMessages.size() > 100) {
            this.visibleMessages.remove(this.visibleMessages.size() - 1);
        }
        if (!refresh) {
            this.messages.add(0, new ChatHudLine<Text>(timestamp, message, messageId));
            while (this.messages.size() > 100) {
                this.messages.remove(this.messages.size() - 1);
            }
        }
    }

    public void reset() {
        this.visibleMessages.clear();
        this.resetScroll();
        for (int i = this.messages.size() - 1; i >= 0; --i) {
            ChatHudLine<Text> chatHudLine = this.messages.get(i);
            this.addMessage(chatHudLine.getText(), chatHudLine.getId(), chatHudLine.getCreationTick(), true);
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
        int n = this.visibleMessages.size();
        if (this.scrolledLines > n - this.getVisibleLineCount()) {
            this.scrolledLines = n - this.getVisibleLineCount();
        }
        if (this.scrolledLines <= 0) {
            this.scrolledLines = 0;
            this.hasUnreadNewMessages = false;
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY) {
        if (!this.isChatFocused() || this.client.options.hudHidden || this.isChatHidden() || this.messageQueue.isEmpty()) {
            return false;
        }
        double d = mouseX - 2.0;
        _snowman = (double)this.client.getWindow().getScaledHeight() - mouseY - 40.0;
        if (d <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale()) && _snowman < 0.0 && _snowman > (double)MathHelper.floor(-9.0 * this.getChatScale())) {
            this.addMessage(this.messageQueue.remove());
            this.lastMessageAddedTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    @Nullable
    public Style getText(double x, double y) {
        if (!this.isChatFocused() || this.client.options.hudHidden || this.isChatHidden()) {
            return null;
        }
        double d = x - 2.0;
        _snowman = (double)this.client.getWindow().getScaledHeight() - y - 40.0;
        d = MathHelper.floor(d / this.getChatScale());
        _snowman = MathHelper.floor(_snowman / (this.getChatScale() * (this.client.options.chatLineSpacing + 1.0)));
        if (d < 0.0 || _snowman < 0.0) {
            return null;
        }
        int _snowman2 = Math.min(this.getVisibleLineCount(), this.visibleMessages.size());
        if (d <= (double)MathHelper.floor((double)this.getWidth() / this.getChatScale()) && _snowman < (double)(this.client.textRenderer.fontHeight * _snowman2 + _snowman2) && (_snowman = (int)(_snowman / (double)this.client.textRenderer.fontHeight + (double)this.scrolledLines)) >= 0 && _snowman < this.visibleMessages.size()) {
            ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(_snowman);
            return this.client.textRenderer.getTextHandler().getStyleAt(chatHudLine.getText(), (int)d);
        }
        return null;
    }

    private boolean isChatFocused() {
        return this.client.currentScreen instanceof ChatScreen;
    }

    private void removeMessage(int messageId) {
        this.visibleMessages.removeIf(message -> message.getId() == messageId);
        this.messages.removeIf(message -> message.getId() == messageId);
    }

    public int getWidth() {
        return ChatHud.getWidth(this.client.options.chatWidth);
    }

    public int getHeight() {
        return ChatHud.getHeight((this.isChatFocused() ? this.client.options.chatHeightFocused : this.client.options.chatHeightUnfocused) / (this.client.options.chatLineSpacing + 1.0));
    }

    public double getChatScale() {
        return this.client.options.chatScale;
    }

    public static int getWidth(double widthOption) {
        int n = 320;
        _snowman = 40;
        return MathHelper.floor(widthOption * 280.0 + 40.0);
    }

    public static int getHeight(double heightOption) {
        int n = 180;
        _snowman = 20;
        return MathHelper.floor(heightOption * 160.0 + 20.0);
    }

    public int getVisibleLineCount() {
        return this.getHeight() / 9;
    }

    private long getChatDelayMillis() {
        return (long)(this.client.options.chatDelay * 1000.0);
    }

    private void processMessageQueue() {
        if (this.messageQueue.isEmpty()) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - this.lastMessageAddedTime >= this.getChatDelayMillis()) {
            this.addMessage(this.messageQueue.remove());
            this.lastMessageAddedTime = l;
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

