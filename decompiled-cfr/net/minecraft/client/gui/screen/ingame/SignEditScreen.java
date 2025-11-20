/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.SignBlock;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;

public class SignEditScreen
extends Screen {
    private final SignBlockEntityRenderer.SignModel model = new SignBlockEntityRenderer.SignModel();
    private final SignBlockEntity sign;
    private int ticksSinceOpened;
    private int currentRow;
    private SelectionManager selectionManager;
    private final String[] text = (String[])IntStream.range(0, 4).mapToObj(sign::getTextOnRow).map(Text::getString).toArray(String[]::new);

    public SignEditScreen(SignBlockEntity sign) {
        super(new TranslatableText("sign.edit"));
        this.sign = sign;
    }

    @Override
    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 120, 200, 20, ScreenTexts.DONE, buttonWidget -> this.finishEditing()));
        this.sign.setEditable(false);
        this.selectionManager = new SelectionManager(() -> this.text[this.currentRow], string -> {
            this.text[this.currentRow] = string;
            this.sign.setTextOnRow(this.currentRow, new LiteralText((String)string));
        }, SelectionManager.makeClipboardGetter(this.client), SelectionManager.makeClipboardSetter(this.client), string -> this.client.textRenderer.getWidth((String)string) <= 90);
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            clientPlayNetworkHandler.sendPacket(new UpdateSignC2SPacket(this.sign.getPos(), this.text[0], this.text[1], this.text[2], this.text[3]));
        }
        this.sign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.ticksSinceOpened;
        if (!this.sign.getType().supports(this.sign.getCachedState().getBlock())) {
            this.finishEditing();
        }
    }

    private void finishEditing() {
        this.sign.markDirty();
        this.client.openScreen(null);
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        this.selectionManager.insert(chr);
        return true;
    }

    @Override
    public void onClose() {
        this.finishEditing();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) {
            this.currentRow = this.currentRow - 1 & 3;
            this.selectionManager.moveCaretToEnd();
            return true;
        }
        if (keyCode == 264 || keyCode == 257 || keyCode == 335) {
            this.currentRow = this.currentRow + 1 & 3;
            this.selectionManager.moveCaretToEnd();
            return true;
        }
        if (this.selectionManager.handleSpecialKey(keyCode)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int _snowman15;
        int _snowman14;
        String string;
        int n;
        DiffuseLighting.disableGuiDepthLighting();
        this.renderBackground(matrices);
        SignEditScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
        matrices.push();
        matrices.translate(this.width / 2, 0.0, 50.0);
        float f = 93.75f;
        matrices.scale(93.75f, -93.75f, 93.75f);
        matrices.translate(0.0, -1.3125, 0.0);
        BlockState _snowman2 = this.sign.getCachedState();
        boolean _snowman3 = _snowman2.getBlock() instanceof SignBlock;
        if (!_snowman3) {
            matrices.translate(0.0, -0.3125, 0.0);
        }
        boolean _snowman4 = this.ticksSinceOpened / 6 % 2 == 0;
        _snowman = 0.6666667f;
        matrices.push();
        matrices.scale(0.6666667f, -0.6666667f, -0.6666667f);
        VertexConsumerProvider.Immediate _snowman5 = this.client.getBufferBuilders().getEntityVertexConsumers();
        SpriteIdentifier _snowman6 = SignBlockEntityRenderer.getModelTexture(_snowman2.getBlock());
        VertexConsumer _snowman7 = _snowman6.getVertexConsumer(_snowman5, this.model::getLayer);
        this.model.field.render(matrices, _snowman7, 0xF000F0, OverlayTexture.DEFAULT_UV);
        if (_snowman3) {
            this.model.foot.render(matrices, _snowman7, 0xF000F0, OverlayTexture.DEFAULT_UV);
        }
        matrices.pop();
        _snowman = 0.010416667f;
        matrices.translate(0.0, 0.3333333432674408, 0.046666666865348816);
        matrices.scale(0.010416667f, -0.010416667f, 0.010416667f);
        int _snowman8 = this.sign.getTextColor().getSignColor();
        int _snowman9 = this.selectionManager.getSelectionStart();
        int _snowman10 = this.selectionManager.getSelectionEnd();
        int _snowman11 = this.currentRow * 10 - this.text.length * 5;
        Matrix4f _snowman12 = matrices.peek().getModel();
        for (n = 0; n < this.text.length; ++n) {
            string = this.text[n];
            if (string == null) continue;
            if (this.textRenderer.isRightToLeft()) {
                string = this.textRenderer.mirror(string);
            }
            float _snowman13 = -this.client.textRenderer.getWidth(string) / 2;
            this.client.textRenderer.draw(string, _snowman13, n * 10 - this.text.length * 5, _snowman8, false, _snowman12, _snowman5, false, 0, 0xF000F0, false);
            if (n != this.currentRow || _snowman9 < 0 || !_snowman4) continue;
            _snowman14 = this.client.textRenderer.getWidth(string.substring(0, Math.max(Math.min(_snowman9, string.length()), 0)));
            _snowman15 = _snowman14 - this.client.textRenderer.getWidth(string) / 2;
            if (_snowman9 < string.length()) continue;
            this.client.textRenderer.draw("_", _snowman15, _snowman11, _snowman8, false, _snowman12, _snowman5, false, 0, 0xF000F0, false);
        }
        _snowman5.draw();
        for (n = 0; n < this.text.length; ++n) {
            string = this.text[n];
            if (string == null || n != this.currentRow || _snowman9 < 0) continue;
            int _snowman16 = this.client.textRenderer.getWidth(string.substring(0, Math.max(Math.min(_snowman9, string.length()), 0)));
            _snowman14 = _snowman16 - this.client.textRenderer.getWidth(string) / 2;
            if (_snowman4 && _snowman9 < string.length()) {
                SignEditScreen.fill(matrices, _snowman14, _snowman11 - 1, _snowman14 + 1, _snowman11 + this.client.textRenderer.fontHeight, 0xFF000000 | _snowman8);
            }
            if (_snowman10 == _snowman9) continue;
            _snowman15 = Math.min(_snowman9, _snowman10);
            int _snowman17 = Math.max(_snowman9, _snowman10);
            int _snowman18 = this.client.textRenderer.getWidth(string.substring(0, _snowman15)) - this.client.textRenderer.getWidth(string) / 2;
            int _snowman19 = this.client.textRenderer.getWidth(string.substring(0, _snowman17)) - this.client.textRenderer.getWidth(string) / 2;
            int _snowman20 = Math.min(_snowman18, _snowman19);
            int _snowman21 = Math.max(_snowman18, _snowman19);
            Tessellator _snowman22 = Tessellator.getInstance();
            BufferBuilder _snowman23 = _snowman22.getBuffer();
            RenderSystem.disableTexture();
            RenderSystem.enableColorLogicOp();
            RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
            _snowman23.begin(7, VertexFormats.POSITION_COLOR);
            _snowman23.vertex(_snowman12, _snowman20, _snowman11 + this.client.textRenderer.fontHeight, 0.0f).color(0, 0, 255, 255).next();
            _snowman23.vertex(_snowman12, _snowman21, _snowman11 + this.client.textRenderer.fontHeight, 0.0f).color(0, 0, 255, 255).next();
            _snowman23.vertex(_snowman12, _snowman21, _snowman11, 0.0f).color(0, 0, 255, 255).next();
            _snowman23.vertex(_snowman12, _snowman20, _snowman11, 0.0f).color(0, 0, 255, 255).next();
            _snowman23.end();
            BufferRenderer.draw(_snowman23);
            RenderSystem.disableColorLogicOp();
            RenderSystem.enableTexture();
        }
        matrices.pop();
        DiffuseLighting.enableGuiDepthLighting();
        super.render(matrices, mouseX, mouseY, delta);
    }
}

