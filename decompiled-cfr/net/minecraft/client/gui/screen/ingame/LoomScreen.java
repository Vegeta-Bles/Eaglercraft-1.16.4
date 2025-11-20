/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.LoomScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LoomScreen
extends HandledScreen<LoomScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/loom.png");
    private static final int PATTERN_BUTTON_ROW_COUNT = (BannerPattern.COUNT - BannerPattern.field_24417 - 1 + 4 - 1) / 4;
    private final ModelPart bannerField;
    @Nullable
    private List<Pair<BannerPattern, DyeColor>> field_21841;
    private ItemStack banner = ItemStack.EMPTY;
    private ItemStack dye = ItemStack.EMPTY;
    private ItemStack pattern = ItemStack.EMPTY;
    private boolean canApplyDyePattern;
    private boolean canApplySpecialPattern;
    private boolean hasTooManyPatterns;
    private float scrollPosition;
    private boolean scrollbarClicked;
    private int firstPatternButtonId = 1;

    public LoomScreen(LoomScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.bannerField = BannerBlockEntityRenderer.createBanner();
        handler.setInventoryChangeListener(this::onInventoryChanged);
        this.titleY -= 2;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = this.x;
        _snowman = this.y;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        Slot _snowman2 = ((LoomScreenHandler)this.handler).getBannerSlot();
        Slot _snowman3 = ((LoomScreenHandler)this.handler).getDyeSlot();
        Slot _snowman4 = ((LoomScreenHandler)this.handler).getPatternSlot();
        Slot _snowman5 = ((LoomScreenHandler)this.handler).getOutputSlot();
        if (!_snowman2.hasStack()) {
            this.drawTexture(matrices, n + _snowman2.x, _snowman + _snowman2.y, this.backgroundWidth, 0, 16, 16);
        }
        if (!_snowman3.hasStack()) {
            this.drawTexture(matrices, n + _snowman3.x, _snowman + _snowman3.y, this.backgroundWidth + 16, 0, 16, 16);
        }
        if (!_snowman4.hasStack()) {
            this.drawTexture(matrices, n + _snowman4.x, _snowman + _snowman4.y, this.backgroundWidth + 32, 0, 16, 16);
        }
        _snowman = (int)(41.0f * this.scrollPosition);
        this.drawTexture(matrices, n + 119, _snowman + 13 + _snowman, 232 + (this.canApplyDyePattern ? 0 : 12), 0, 12, 15);
        DiffuseLighting.disableGuiDepthLighting();
        if (this.field_21841 != null && !this.hasTooManyPatterns) {
            VertexConsumerProvider.Immediate immediate = this.client.getBufferBuilders().getEntityVertexConsumers();
            matrices.push();
            matrices.translate(n + 139, _snowman + 52, 0.0);
            matrices.scale(24.0f, -24.0f, 1.0f);
            matrices.translate(0.5, 0.5, 0.5);
            float _snowman6 = 0.6666667f;
            matrices.scale(0.6666667f, -0.6666667f, -0.6666667f);
            this.bannerField.pitch = 0.0f;
            this.bannerField.pivotY = -32.0f;
            BannerBlockEntityRenderer.method_29999(matrices, immediate, 0xF000F0, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, this.field_21841);
            matrices.pop();
            immediate.draw();
        } else if (this.hasTooManyPatterns) {
            this.drawTexture(matrices, n + _snowman5.x - 2, _snowman + _snowman5.y - 2, this.backgroundWidth, 17, 17, 16);
        }
        if (this.canApplyDyePattern) {
            int _snowman7 = n + 60;
            int _snowman8 = _snowman + 13;
            int _snowman9 = this.firstPatternButtonId + 16;
            for (int i = this.firstPatternButtonId; i < _snowman9 && i < BannerPattern.COUNT - BannerPattern.field_24417; ++i) {
                _snowman = i - this.firstPatternButtonId;
                _snowman = _snowman7 + _snowman % 4 * 14;
                _snowman = _snowman8 + _snowman / 4 * 14;
                this.client.getTextureManager().bindTexture(TEXTURE);
                _snowman = this.backgroundHeight;
                if (i == ((LoomScreenHandler)this.handler).getSelectedPattern()) {
                    _snowman += 14;
                } else if (mouseX >= _snowman && mouseY >= _snowman && mouseX < _snowman + 14 && mouseY < _snowman + 14) {
                    _snowman += 28;
                }
                this.drawTexture(matrices, _snowman, _snowman, 0, _snowman, 14, 14);
                this.method_22692(i, _snowman, _snowman);
            }
        } else if (this.canApplySpecialPattern) {
            _snowman = n + 60;
            _snowman = _snowman + 13;
            this.client.getTextureManager().bindTexture(TEXTURE);
            this.drawTexture(matrices, _snowman, _snowman, 0, this.backgroundHeight, 14, 14);
            _snowman = ((LoomScreenHandler)this.handler).getSelectedPattern();
            this.method_22692(_snowman, _snowman, _snowman);
        }
        DiffuseLighting.enableGuiDepthLighting();
    }

    private void method_22692(int n, int n2, int n3) {
        ItemStack itemStack = new ItemStack(Items.GRAY_BANNER);
        CompoundTag _snowman2 = itemStack.getOrCreateSubTag("BlockEntityTag");
        ListTag _snowman3 = new BannerPattern.Patterns().add(BannerPattern.BASE, DyeColor.GRAY).add(BannerPattern.values()[n], DyeColor.WHITE).toTag();
        _snowman2.put("Patterns", _snowman3);
        MatrixStack _snowman4 = new MatrixStack();
        _snowman4.push();
        _snowman4.translate((float)n2 + 0.5f, n3 + 16, 0.0);
        _snowman4.scale(6.0f, -6.0f, 1.0f);
        _snowman4.translate(0.5, 0.5, 0.0);
        _snowman4.translate(0.5, 0.5, 0.5);
        float _snowman5 = 0.6666667f;
        _snowman4.scale(0.6666667f, -0.6666667f, -0.6666667f);
        VertexConsumerProvider.Immediate _snowman6 = this.client.getBufferBuilders().getEntityVertexConsumers();
        this.bannerField.pitch = 0.0f;
        this.bannerField.pivotY = -32.0f;
        List<Pair<BannerPattern, DyeColor>> _snowman7 = BannerBlockEntity.method_24280(DyeColor.GRAY, BannerBlockEntity.getPatternListTag(itemStack));
        BannerBlockEntityRenderer.method_29999(_snowman4, _snowman6, 0xF000F0, OverlayTexture.DEFAULT_UV, this.bannerField, ModelLoader.BANNER_BASE, true, _snowman7);
        _snowman4.pop();
        _snowman6.draw();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.scrollbarClicked = false;
        if (this.canApplyDyePattern) {
            int n = this.x + 60;
            _snowman = this.y + 13;
            _snowman = this.firstPatternButtonId + 16;
            for (_snowman = this.firstPatternButtonId; _snowman < _snowman; ++_snowman) {
                _snowman = _snowman - this.firstPatternButtonId;
                double d = mouseX - (double)(n + _snowman % 4 * 14);
                _snowman = mouseY - (double)(_snowman + _snowman / 4 * 14);
                if (!(d >= 0.0) || !(_snowman >= 0.0) || !(d < 14.0) || !(_snowman < 14.0) || !((LoomScreenHandler)this.handler).onButtonClick(this.client.player, _snowman)) continue;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_LOOM_SELECT_PATTERN, 1.0f));
                this.client.interactionManager.clickButton(((LoomScreenHandler)this.handler).syncId, _snowman);
                return true;
            }
            n = this.x + 119;
            _snowman = this.y + 9;
            if (mouseX >= (double)n && mouseX < (double)(n + 12) && mouseY >= (double)_snowman && mouseY < (double)(_snowman + 56)) {
                this.scrollbarClicked = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrollbarClicked && this.canApplyDyePattern) {
            int n = this.y + 13;
            _snowman = n + 56;
            this.scrollPosition = ((float)mouseY - (float)n - 7.5f) / ((float)(_snowman - n) - 15.0f);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0f, 1.0f);
            _snowman = PATTERN_BUTTON_ROW_COUNT - 4;
            _snowman = (int)((double)(this.scrollPosition * (float)_snowman) + 0.5);
            if (_snowman < 0) {
                _snowman = 0;
            }
            this.firstPatternButtonId = 1 + _snowman * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.canApplyDyePattern) {
            int n = PATTERN_BUTTON_ROW_COUNT - 4;
            this.scrollPosition = (float)((double)this.scrollPosition - amount / (double)n);
            this.scrollPosition = MathHelper.clamp(this.scrollPosition, 0.0f, 1.0f);
            this.firstPatternButtonId = 1 + (int)((double)(this.scrollPosition * (float)n) + 0.5) * 4;
        }
        return true;
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
    }

    private void onInventoryChanged() {
        ItemStack itemStack = ((LoomScreenHandler)this.handler).getOutputSlot().getStack();
        this.field_21841 = itemStack.isEmpty() ? null : BannerBlockEntity.method_24280(((BannerItem)itemStack.getItem()).getColor(), BannerBlockEntity.getPatternListTag(itemStack));
        _snowman = ((LoomScreenHandler)this.handler).getBannerSlot().getStack();
        _snowman = ((LoomScreenHandler)this.handler).getDyeSlot().getStack();
        _snowman = ((LoomScreenHandler)this.handler).getPatternSlot().getStack();
        CompoundTag _snowman2 = _snowman.getOrCreateSubTag("BlockEntityTag");
        boolean bl = this.hasTooManyPatterns = _snowman2.contains("Patterns", 9) && !_snowman.isEmpty() && _snowman2.getList("Patterns", 10).size() >= 6;
        if (this.hasTooManyPatterns) {
            this.field_21841 = null;
        }
        if (!(ItemStack.areEqual(_snowman, this.banner) && ItemStack.areEqual(_snowman, this.dye) && ItemStack.areEqual(_snowman, this.pattern))) {
            this.canApplyDyePattern = !_snowman.isEmpty() && !_snowman.isEmpty() && _snowman.isEmpty() && !this.hasTooManyPatterns;
            this.canApplySpecialPattern = !this.hasTooManyPatterns && !_snowman.isEmpty() && !_snowman.isEmpty() && !_snowman.isEmpty();
        }
        this.banner = _snowman.copy();
        this.dye = _snowman.copy();
        this.pattern = _snowman.copy();
    }
}

