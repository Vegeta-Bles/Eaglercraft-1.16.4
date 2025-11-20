/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.screen.ingame.EnchantingPhrases;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class EnchantmentScreen
extends HandledScreen<EnchantmentScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/enchanting_table.png");
    private static final Identifier BOOK_TEXTURE = new Identifier("textures/entity/enchanting_table_book.png");
    private static final BookModel BOOK_MODEL = new BookModel();
    private final Random random = new Random();
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float approximatePageAngle;
    public float pageRotationSpeed;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    private ItemStack stack = ItemStack.EMPTY;

    public EnchantmentScreen(EnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    public void tick() {
        super.tick();
        this.doTick();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            double d = mouseX - (double)(n + 60);
            _snowman = mouseY - (double)(_snowman + 14 + 19 * _snowman);
            if (!(d >= 0.0) || !(_snowman >= 0.0) || !(d < 108.0) || !(_snowman < 19.0) || !((EnchantmentScreenHandler)this.handler).onButtonClick(this.client.player, _snowman)) continue;
            this.client.interactionManager.clickButton(((EnchantmentScreenHandler)this.handler).syncId, _snowman);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        DiffuseLighting.disableGuiDepthLighting();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        RenderSystem.matrixMode(5889);
        RenderSystem.pushMatrix();
        RenderSystem.loadIdentity();
        _snowman = (int)this.client.getWindow().getScaleFactor();
        RenderSystem.viewport((this.width - 320) / 2 * _snowman, (this.height - 240) / 2 * _snowman, 320 * _snowman, 240 * _snowman);
        RenderSystem.translatef(-0.34f, 0.23f, 0.0f);
        RenderSystem.multMatrix(Matrix4f.viewboxMatrix(90.0, 1.3333334f, 9.0f, 80.0f));
        RenderSystem.matrixMode(5888);
        matrices.push();
        MatrixStack.Entry _snowman2 = matrices.peek();
        _snowman2.getModel().loadIdentity();
        _snowman2.getNormal().loadIdentity();
        matrices.translate(0.0, 3.3f, 1984.0);
        float _snowman3 = 5.0f;
        matrices.scale(5.0f, 5.0f, 5.0f);
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(20.0f));
        float _snowman4 = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
        matrices.translate((1.0f - _snowman4) * 0.2f, (1.0f - _snowman4) * 0.1f, (1.0f - _snowman4) * 0.25f);
        float _snowman5 = -(1.0f - _snowman4) * 90.0f - 90.0f;
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman5));
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0f));
        float _snowman6 = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.25f;
        float _snowman7 = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.75f;
        _snowman6 = (_snowman6 - (float)MathHelper.fastFloor(_snowman6)) * 1.6f - 0.3f;
        _snowman7 = (_snowman7 - (float)MathHelper.fastFloor(_snowman7)) * 1.6f - 0.3f;
        if (_snowman6 < 0.0f) {
            _snowman6 = 0.0f;
        }
        if (_snowman7 < 0.0f) {
            _snowman7 = 0.0f;
        }
        if (_snowman6 > 1.0f) {
            _snowman6 = 1.0f;
        }
        if (_snowman7 > 1.0f) {
            _snowman7 = 1.0f;
        }
        RenderSystem.enableRescaleNormal();
        BOOK_MODEL.setPageAngles(0.0f, _snowman6, _snowman7, _snowman4);
        VertexConsumerProvider.Immediate _snowman8 = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        VertexConsumer _snowman9 = _snowman8.getBuffer(BOOK_MODEL.getLayer(BOOK_TEXTURE));
        BOOK_MODEL.render(matrices, _snowman9, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        _snowman8.draw();
        matrices.pop();
        RenderSystem.matrixMode(5889);
        RenderSystem.viewport(0, 0, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
        RenderSystem.popMatrix();
        RenderSystem.matrixMode(5888);
        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantingPhrases.getInstance().setSeed(((EnchantmentScreenHandler)this.handler).getSeed());
        _snowman = ((EnchantmentScreenHandler)this.handler).getLapisCount();
        for (_snowman = 0; _snowman < 3; ++_snowman) {
            _snowman = n + 60;
            _snowman = _snowman + 20;
            this.setZOffset(0);
            this.client.getTextureManager().bindTexture(TEXTURE);
            _snowman = ((EnchantmentScreenHandler)this.handler).enchantmentPower[_snowman];
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (_snowman == 0) {
                this.drawTexture(matrices, _snowman, _snowman + 14 + 19 * _snowman, 0, 185, 108, 19);
                continue;
            }
            String string = "" + _snowman;
            int _snowman10 = 86 - this.textRenderer.getWidth(string);
            StringVisitable _snowman11 = EnchantingPhrases.getInstance().generatePhrase(this.textRenderer, _snowman10);
            int _snowman12 = 6839882;
            if (!(_snowman >= _snowman + 1 && this.client.player.experienceLevel >= _snowman || this.client.player.abilities.creativeMode)) {
                this.drawTexture(matrices, _snowman, _snowman + 14 + 19 * _snowman, 0, 185, 108, 19);
                this.drawTexture(matrices, _snowman + 1, _snowman + 15 + 19 * _snowman, 16 * _snowman, 239, 16, 16);
                this.textRenderer.drawTrimmed(_snowman11, _snowman, _snowman + 16 + 19 * _snowman, _snowman10, (_snowman12 & 0xFEFEFE) >> 1);
                _snowman12 = 4226832;
            } else {
                int n2 = mouseX - (n + 60);
                _snowman = mouseY - (_snowman + 14 + 19 * _snowman);
                if (n2 >= 0 && _snowman >= 0 && n2 < 108 && _snowman < 19) {
                    this.drawTexture(matrices, _snowman, _snowman + 14 + 19 * _snowman, 0, 204, 108, 19);
                    _snowman12 = 0xFFFF80;
                } else {
                    this.drawTexture(matrices, _snowman, _snowman + 14 + 19 * _snowman, 0, 166, 108, 19);
                }
                this.drawTexture(matrices, _snowman + 1, _snowman + 15 + 19 * _snowman, 16 * _snowman, 223, 16, 16);
                this.textRenderer.drawTrimmed(_snowman11, _snowman, _snowman + 16 + 19 * _snowman, _snowman10, _snowman12);
                _snowman12 = 8453920;
            }
            this.textRenderer.drawWithShadow(matrices, string, (float)(_snowman + 86 - this.textRenderer.getWidth(string)), (float)(_snowman + 16 + 19 * _snowman + 7), _snowman12);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        delta = this.client.getTickDelta();
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        boolean bl = this.client.player.abilities.creativeMode;
        int _snowman2 = ((EnchantmentScreenHandler)this.handler).getLapisCount();
        for (int i = 0; i < 3; ++i) {
            _snowman = ((EnchantmentScreenHandler)this.handler).enchantmentPower[i];
            Enchantment enchantment = Enchantment.byRawId(((EnchantmentScreenHandler)this.handler).enchantmentId[i]);
            int _snowman3 = ((EnchantmentScreenHandler)this.handler).enchantmentLevel[i];
            int _snowman4 = i + 1;
            if (!this.isPointWithinBounds(60, 14 + 19 * i, 108, 17, mouseX, mouseY) || _snowman <= 0 || _snowman3 < 0 || enchantment == null) continue;
            ArrayList _snowman5 = Lists.newArrayList();
            _snowman5.add(new TranslatableText("container.enchant.clue", enchantment.getName(_snowman3)).formatted(Formatting.WHITE));
            if (!bl) {
                _snowman5.add(LiteralText.EMPTY);
                if (this.client.player.experienceLevel < _snowman) {
                    _snowman5.add(new TranslatableText("container.enchant.level.requirement", ((EnchantmentScreenHandler)this.handler).enchantmentPower[i]).formatted(Formatting.RED));
                } else {
                    TranslatableText translatableText = _snowman4 == 1 ? new TranslatableText("container.enchant.lapis.one") : new TranslatableText("container.enchant.lapis.many", _snowman4);
                    _snowman5.add(translatableText.formatted(_snowman2 >= _snowman4 ? Formatting.GRAY : Formatting.RED));
                    _snowman = _snowman4 == 1 ? new TranslatableText("container.enchant.level.one") : new TranslatableText("container.enchant.level.many", _snowman4);
                    _snowman5.add(_snowman.formatted(Formatting.GRAY));
                }
            }
            this.renderTooltip(matrices, _snowman5, mouseX, mouseY);
            break;
        }
    }

    public void doTick() {
        ItemStack itemStack = ((EnchantmentScreenHandler)this.handler).getSlot(0).getStack();
        if (!ItemStack.areEqual(itemStack, this.stack)) {
            this.stack = itemStack;
            do {
                this.approximatePageAngle += (float)(this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.nextPageAngle <= this.approximatePageAngle + 1.0f && this.nextPageAngle >= this.approximatePageAngle - 1.0f);
        }
        ++this.ticks;
        this.pageAngle = this.nextPageAngle;
        this.pageTurningSpeed = this.nextPageTurningSpeed;
        boolean _snowman2 = false;
        for (int i = 0; i < 3; ++i) {
            if (((EnchantmentScreenHandler)this.handler).enchantmentPower[i] == 0) continue;
            _snowman2 = true;
        }
        this.nextPageTurningSpeed = _snowman2 ? (this.nextPageTurningSpeed += 0.2f) : (this.nextPageTurningSpeed -= 0.2f);
        this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0f, 1.0f);
        float f = (this.approximatePageAngle - this.nextPageAngle) * 0.4f;
        _snowman = 0.2f;
        f = MathHelper.clamp(f, -0.2f, 0.2f);
        this.pageRotationSpeed += (f - this.pageRotationSpeed) * 0.9f;
        this.nextPageAngle += this.pageRotationSpeed;
    }
}

