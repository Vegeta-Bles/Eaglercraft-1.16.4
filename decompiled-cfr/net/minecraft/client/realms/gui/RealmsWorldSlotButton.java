/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.realms.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsWorldOptions;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RealmsWorldSlotButton
extends ButtonWidget
implements TickableElement {
    public static final Identifier SLOT_FRAME = new Identifier("realms", "textures/gui/realms/slot_frame.png");
    public static final Identifier EMPTY_FRAME = new Identifier("realms", "textures/gui/realms/empty_frame.png");
    public static final Identifier PANORAMA_0 = new Identifier("minecraft", "textures/gui/title/background/panorama_0.png");
    public static final Identifier PANORAMA_2 = new Identifier("minecraft", "textures/gui/title/background/panorama_2.png");
    public static final Identifier PANORAMA_3 = new Identifier("minecraft", "textures/gui/title/background/panorama_3.png");
    private static final Text field_26468 = new TranslatableText("mco.configure.world.slot.tooltip.active");
    private static final Text field_26469 = new TranslatableText("mco.configure.world.slot.tooltip.minigame");
    private static final Text field_26470 = new TranslatableText("mco.configure.world.slot.tooltip");
    private final Supplier<RealmsServer> serverDataProvider;
    private final Consumer<Text> toolTipSetter;
    private final int slotIndex;
    private int animTick;
    @Nullable
    private State state;

    public RealmsWorldSlotButton(int x, int y, int width, int height, Supplier<RealmsServer> serverDataProvider, Consumer<Text> toolTipSetter, int id, ButtonWidget.PressAction action) {
        super(x, y, width, height, LiteralText.EMPTY, action);
        this.serverDataProvider = serverDataProvider;
        this.slotIndex = id;
        this.toolTipSetter = toolTipSetter;
    }

    @Nullable
    public State getState() {
        return this.state;
    }

    @Override
    public void tick() {
        String _snowman5;
        long _snowman4;
        String _snowman3;
        ++this.animTick;
        RealmsServer realmsServer = this.serverDataProvider.get();
        if (realmsServer == null) {
            return;
        }
        RealmsWorldOptions _snowman2 = realmsServer.slots.get(this.slotIndex);
        boolean bl = _snowman = this.slotIndex == 4;
        if (_snowman) {
            boolean bl2 = realmsServer.worldType == RealmsServer.WorldType.MINIGAME;
            _snowman3 = "Minigame";
            _snowman4 = realmsServer.minigameId;
            _snowman5 = realmsServer.minigameImage;
            _snowman = realmsServer.minigameId == -1;
        } else {
            bl2 = realmsServer.activeSlot == this.slotIndex && realmsServer.worldType != RealmsServer.WorldType.MINIGAME;
            _snowman3 = _snowman2.getSlotName(this.slotIndex);
            _snowman4 = _snowman2.templateId;
            _snowman5 = _snowman2.templateImage;
            _snowman = _snowman2.empty;
        }
        Action action = RealmsWorldSlotButton.method_27455(realmsServer, bl2, _snowman);
        Pair<Text, Text> _snowman6 = this.method_27454(realmsServer, _snowman3, _snowman, _snowman, action);
        this.state = new State(bl2, _snowman3, _snowman4, _snowman5, _snowman, _snowman, action, (Text)_snowman6.getFirst());
        this.setMessage((Text)_snowman6.getSecond());
    }

    private static Action method_27455(RealmsServer realmsServer, boolean bl, boolean bl2) {
        if (bl) {
            if (!realmsServer.expired && realmsServer.state != RealmsServer.State.UNINITIALIZED) {
                return Action.JOIN;
            }
        } else if (bl2) {
            if (!realmsServer.expired) {
                return Action.SWITCH_SLOT;
            }
        } else {
            return Action.SWITCH_SLOT;
        }
        return Action.NOTHING;
    }

    private Pair<Text, Text> method_27454(RealmsServer realmsServer, String string, boolean bl, boolean bl2, Action action) {
        if (action == Action.NOTHING) {
            return Pair.of(null, (Object)new LiteralText(string));
        }
        Text text = bl2 ? (bl ? LiteralText.EMPTY : new LiteralText(" ").append(string).append(" ").append(realmsServer.minigameName)) : new LiteralText(" ").append(string);
        _snowman = action == Action.JOIN ? field_26468 : (bl2 ? field_26469 : field_26470);
        MutableText _snowman2 = _snowman.shallowCopy().append(text);
        return Pair.of((Object)_snowman, (Object)_snowman2);
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.state == null) {
            return;
        }
        this.drawSlotFrame(matrices, this.x, this.y, mouseX, mouseY, this.state.isCurrentlyActiveSlot, this.state.slotName, this.slotIndex, this.state.imageId, this.state.image, this.state.empty, this.state.minigame, this.state.action, this.state.actionPrompt);
    }

    private void drawSlotFrame(MatrixStack matrices, int x, int y, int mouseX, int mouseY, boolean bl, String text, int n, long l, @Nullable String string, boolean bl2, boolean bl3, Action action, @Nullable Text text2) {
        boolean bl4;
        boolean bl5 = this.isHovered();
        if (this.isMouseOver(mouseX, mouseY) && text2 != null) {
            this.toolTipSetter.accept(text2);
        }
        MinecraftClient _snowman2 = MinecraftClient.getInstance();
        TextureManager _snowman3 = _snowman2.getTextureManager();
        if (bl3) {
            RealmsTextureManager.bindWorldTemplate(String.valueOf(l), string);
        } else if (bl2) {
            _snowman3.bindTexture(EMPTY_FRAME);
        } else if (string != null && l != -1L) {
            RealmsTextureManager.bindWorldTemplate(String.valueOf(l), string);
        } else if (n == 1) {
            _snowman3.bindTexture(PANORAMA_0);
        } else if (n == 2) {
            _snowman3.bindTexture(PANORAMA_2);
        } else if (n == 3) {
            _snowman3.bindTexture(PANORAMA_3);
        }
        if (bl) {
            float f = 0.85f + 0.15f * MathHelper.cos((float)this.animTick * 0.2f);
            RenderSystem.color4f(f, f, f, 1.0f);
        } else {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsWorldSlotButton.drawTexture(matrices, x + 3, y + 3, 0.0f, 0.0f, 74, 74, 74, 74);
        _snowman3.bindTexture(SLOT_FRAME);
        boolean bl6 = bl4 = bl5 && action != Action.NOTHING;
        if (bl4) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (bl) {
            RenderSystem.color4f(0.8f, 0.8f, 0.8f, 1.0f);
        } else {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsWorldSlotButton.drawTexture(matrices, x, y, 0.0f, 0.0f, 80, 80, 80, 80);
        RealmsWorldSlotButton.drawCenteredString(matrices, _snowman2.textRenderer, text, x + 40, y + 66, 0xFFFFFF);
    }

    public static class State {
        private final boolean isCurrentlyActiveSlot;
        private final String slotName;
        private final long imageId;
        private final String image;
        public final boolean empty;
        public final boolean minigame;
        public final Action action;
        @Nullable
        private final Text actionPrompt;

        State(boolean isCurrentlyActiveSlot, String slotName, long imageId, @Nullable String image, boolean empty, boolean minigame, Action action, @Nullable Text actionPrompt) {
            this.isCurrentlyActiveSlot = isCurrentlyActiveSlot;
            this.slotName = slotName;
            this.imageId = imageId;
            this.image = image;
            this.empty = empty;
            this.minigame = minigame;
            this.action = action;
            this.actionPrompt = actionPrompt;
        }
    }

    public static enum Action {
        NOTHING,
        SWITCH_SLOT,
        JOIN;

    }
}

