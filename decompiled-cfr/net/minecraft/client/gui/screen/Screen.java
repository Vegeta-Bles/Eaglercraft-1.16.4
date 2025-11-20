/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import net.minecraft.client.gui.screen.ConfirmChatLinkScreen;
import net.minecraft.client.gui.screen.TickableElement;
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

public abstract class Screen
extends AbstractParentElement
implements TickableElement,
Drawable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<String> ALLOWED_PROTOCOLS = Sets.newHashSet((Object[])new String[]{"http", "https"});
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
        for (int i = 0; i < this.buttons.size(); ++i) {
            this.buttons.get(i).render(matrices, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        if (keyCode == 258) {
            boolean bl;
            boolean bl2 = bl = !Screen.hasShiftDown();
            if (!this.changeFocus(bl)) {
                this.changeFocus(bl);
            }
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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

    /*
     * WARNING - void declaration
     */
    public void renderOrderedTooltip(MatrixStack matrices, List<? extends OrderedText> lines, int x, int y) {
        int n;
        if (lines.isEmpty()) {
            return;
        }
        int n2 = 0;
        for (OrderedText orderedText : lines) {
            int n22 = this.textRenderer.getWidth(orderedText);
            if (n22 <= n2) continue;
            n2 = n22;
        }
        int n3 = x + 12;
        int n4 = y - 12;
        int n5 = n2;
        _snowman = 8;
        if (lines.size() > 1) {
            _snowman += 2 + (lines.size() - 1) * 10;
        }
        if (n3 + n2 > this.width) {
            n3 -= 28 + n2;
        }
        if (n4 + _snowman + 6 > this.height) {
            n = this.height - _snowman - 6;
        }
        matrices.push();
        _snowman = -267386864;
        _snowman = 0x505000FF;
        _snowman = 1344798847;
        _snowman = 400;
        Tessellator _snowman2 = Tessellator.getInstance();
        BufferBuilder _snowman3 = _snowman2.getBuffer();
        _snowman3.begin(7, VertexFormats.POSITION_COLOR);
        Matrix4f _snowman4 = matrices.peek().getModel();
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n - 4, n3 + n5 + 3, n - 3, 400, -267386864, -267386864);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n + _snowman + 3, n3 + n5 + 3, n + _snowman + 4, 400, -267386864, -267386864);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n - 3, n3 + n5 + 3, n + _snowman + 3, 400, -267386864, -267386864);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 4, n - 3, n3 - 3, n + _snowman + 3, 400, -267386864, -267386864);
        Screen.fillGradient(_snowman4, _snowman3, n3 + n5 + 3, n - 3, n3 + n5 + 4, n + _snowman + 3, 400, -267386864, -267386864);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n - 3 + 1, n3 - 3 + 1, n + _snowman + 3 - 1, 400, 0x505000FF, 1344798847);
        Screen.fillGradient(_snowman4, _snowman3, n3 + n5 + 2, n - 3 + 1, n3 + n5 + 3, n + _snowman + 3 - 1, 400, 0x505000FF, 1344798847);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n - 3, n3 + n5 + 3, n - 3 + 1, 400, 0x505000FF, 0x505000FF);
        Screen.fillGradient(_snowman4, _snowman3, n3 - 3, n + _snowman + 2, n3 + n5 + 3, n + _snowman + 3, 400, 1344798847, 1344798847);
        RenderSystem.enableDepthTest();
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        _snowman3.end();
        BufferRenderer.draw(_snowman3);
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
        VertexConsumerProvider.Immediate _snowman5 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        matrices.translate(0.0, 0.0, 400.0);
        for (_snowman = 0; _snowman < lines.size(); ++_snowman) {
            OrderedText orderedText = lines.get(_snowman);
            if (orderedText != null) {
                void var7_11;
                this.textRenderer.draw(orderedText, (float)n3, (float)var7_11, -1, true, _snowman4, (VertexConsumerProvider)_snowman5, false, 0, 0xF000F0);
            }
            if (_snowman == 0) {
                var7_11 += 2;
            }
            var7_11 += 10;
        }
        _snowman5.draw();
        matrices.pop();
    }

    protected void renderTextHoverEffect(MatrixStack matrices, @Nullable Style style, int x, int y) {
        if (style == null || style.getHoverEvent() == null) {
            return;
        }
        HoverEvent hoverEvent = style.getHoverEvent();
        HoverEvent.ItemStackContent _snowman2 = hoverEvent.getValue(HoverEvent.Action.SHOW_ITEM);
        if (_snowman2 != null) {
            this.renderTooltip(matrices, _snowman2.asStack(), x, y);
        } else {
            HoverEvent.EntityContent entityContent = hoverEvent.getValue(HoverEvent.Action.SHOW_ENTITY);
            if (entityContent != null) {
                if (this.client.options.advancedItemTooltips) {
                    this.renderTooltip(matrices, entityContent.asTooltip(), x, y);
                }
            } else {
                Text text = hoverEvent.getValue(HoverEvent.Action.SHOW_TEXT);
                if (text != null) {
                    this.renderOrderedTooltip(matrices, this.client.textRenderer.wrapLines(text, Math.max(this.width / 2, 200)), x, y);
                }
            }
        }
    }

    protected void insertText(String text, boolean override) {
    }

    public boolean handleTextClick(@Nullable Style style) {
        if (style == null) {
            return false;
        }
        ClickEvent clickEvent = style.getClickEvent();
        if (Screen.hasShiftDown()) {
            if (style.getInsertion() != null) {
                this.insertText(style.getInsertion(), false);
            }
        } else if (clickEvent != null) {
            block21: {
                if (clickEvent.getAction() == ClickEvent.Action.OPEN_URL) {
                    if (!this.client.options.chatLinks) {
                        return false;
                    }
                    try {
                        URI uRI = new URI(clickEvent.getValue());
                        String _snowman2 = uRI.getScheme();
                        if (_snowman2 == null) {
                            throw new URISyntaxException(clickEvent.getValue(), "Missing protocol");
                        }
                        if (!ALLOWED_PROTOCOLS.contains(_snowman2.toLowerCase(Locale.ROOT))) {
                            throw new URISyntaxException(clickEvent.getValue(), "Unsupported protocol: " + _snowman2.toLowerCase(Locale.ROOT));
                        }
                        if (this.client.options.chatLinksPrompt) {
                            this.clickedLink = uRI;
                            this.client.openScreen(new ConfirmChatLinkScreen(this::confirmLink, clickEvent.getValue(), false));
                            break block21;
                        }
                        this.openLink(uRI);
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        LOGGER.error("Can't open url for {}", (Object)clickEvent, (Object)uRISyntaxException);
                    }
                } else if (clickEvent.getAction() == ClickEvent.Action.OPEN_FILE) {
                    URI uRI = new File(clickEvent.getValue()).toURI();
                    this.openLink(uRI);
                } else if (clickEvent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
                    this.insertText(clickEvent.getValue(), true);
                } else if (clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    this.sendMessage(clickEvent.getValue(), false);
                } else if (clickEvent.getAction() == ClickEvent.Action.COPY_TO_CLIPBOARD) {
                    this.client.keyboard.setClipboard(clickEvent.getValue());
                } else {
                    LOGGER.error("Don't know how to handle {}", (Object)clickEvent);
                }
            }
            return true;
        }
        return false;
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
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder _snowman2 = tessellator.getBuffer();
        this.client.getTextureManager().bindTexture(OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float _snowman3 = 32.0f;
        _snowman2.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        _snowman2.vertex(0.0, this.height, 0.0).texture(0.0f, (float)this.height / 32.0f + (float)vOffset).color(64, 64, 64, 255).next();
        _snowman2.vertex(this.width, this.height, 0.0).texture((float)this.width / 32.0f, (float)this.height / 32.0f + (float)vOffset).color(64, 64, 64, 255).next();
        _snowman2.vertex(this.width, 0.0, 0.0).texture((float)this.width / 32.0f, vOffset).color(64, 64, 64, 255).next();
        _snowman2.vertex(0.0, 0.0, 0.0).texture(0.0f, vOffset).color(64, 64, 64, 255).next();
        tessellator.draw();
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
        if (MinecraftClient.IS_SYSTEM_MAC) {
            return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 343) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 347);
        }
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 341) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 345);
    }

    public static boolean hasShiftDown() {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 340) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 344);
    }

    public static boolean hasAltDown() {
        return InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 342) || InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), 346);
    }

    public static boolean isCut(int code) {
        return code == 88 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isPaste(int code) {
        return code == 86 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isCopy(int code) {
        return code == 67 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public static boolean isSelectAll(int code) {
        return code == 65 && Screen.hasControlDown() && !Screen.hasShiftDown() && !Screen.hasAltDown();
    }

    public void resize(MinecraftClient client, int width, int height) {
        this.init(client, width, height);
    }

    public static void wrapScreenError(Runnable task, String errorTitle, String screenName) {
        try {
            task.run();
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, errorTitle);
            CrashReportSection _snowman2 = crashReport.addElement("Affected screen");
            _snowman2.add("Screen name", () -> screenName);
            throw new CrashException(crashReport);
        }
    }

    protected boolean isValidCharacterForName(String name, char character, int cursorPos) {
        int n = name.indexOf(58);
        _snowman = name.indexOf(47);
        if (character == ':') {
            return (_snowman == -1 || cursorPos <= _snowman) && n == -1;
        }
        if (character == '/') {
            return cursorPos > n;
        }
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return true;
    }

    public void filesDragged(List<Path> paths) {
    }
}

