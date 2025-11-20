/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.AbstractPressableButtonWidget;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateBeaconC2SPacket;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class BeaconScreen
extends HandledScreen<BeaconScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/beacon.png");
    private static final Text field_26560 = new TranslatableText("block.minecraft.beacon.primary");
    private static final Text field_26561 = new TranslatableText("block.minecraft.beacon.secondary");
    private DoneButtonWidget doneButton;
    private boolean consumeGem;
    private StatusEffect primaryEffect;
    private StatusEffect secondaryEffect;

    public BeaconScreen(BeaconScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 230;
        this.backgroundHeight = 219;
        handler.addListener(new ScreenHandlerListener(this, handler){
            final /* synthetic */ BeaconScreenHandler field_17414;
            final /* synthetic */ BeaconScreen field_17415;
            {
                this.field_17415 = beaconScreen;
                this.field_17414 = beaconScreenHandler;
            }

            public void onHandlerRegistered(ScreenHandler handler, DefaultedList<ItemStack> stacks) {
            }

            public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
            }

            public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
                BeaconScreen.method_17544(this.field_17415, this.field_17414.getPrimaryEffect());
                BeaconScreen.method_17546(this.field_17415, this.field_17414.getSecondaryEffect());
                BeaconScreen.method_17545(this.field_17415, true);
            }
        });
    }

    @Override
    protected void init() {
        super.init();
        this.doneButton = this.addButton(new DoneButtonWidget(this.x + 164, this.y + 107));
        this.addButton(new CancelButtonWidget(this.x + 190, this.y + 107));
        this.consumeGem = true;
        this.doneButton.active = false;
    }

    @Override
    public void tick() {
        super.tick();
        int n = ((BeaconScreenHandler)this.handler).getProperties();
        if (this.consumeGem && n >= 0) {
            EffectButtonWidget _snowman2;
            StatusEffect statusEffect;
            this.consumeGem = false;
            for (n2 = 0; n2 <= 2; ++n2) {
                n3 = BeaconBlockEntity.EFFECTS_BY_LEVEL[n2].length;
                _snowman = n3 * 22 + (n3 - 1) * 2;
                for (_snowman = 0; _snowman < n3; ++_snowman) {
                    statusEffect = BeaconBlockEntity.EFFECTS_BY_LEVEL[n2][_snowman];
                    _snowman2 = new EffectButtonWidget(this.x + 76 + _snowman * 24 - _snowman / 2, this.y + 22 + n2 * 25, statusEffect, true);
                    this.addButton(_snowman2);
                    if (n2 >= n) {
                        _snowman2.active = false;
                        continue;
                    }
                    if (statusEffect != this.primaryEffect) continue;
                    _snowman2.setDisabled(true);
                }
            }
            int n2 = 3;
            n3 = BeaconBlockEntity.EFFECTS_BY_LEVEL[3].length + 1;
            _snowman = n3 * 22 + (n3 - 1) * 2;
            for (_snowman = 0; _snowman < n3 - 1; ++_snowman) {
                statusEffect = BeaconBlockEntity.EFFECTS_BY_LEVEL[3][_snowman];
                _snowman2 = new EffectButtonWidget(this.x + 167 + _snowman * 24 - _snowman / 2, this.y + 47, statusEffect, false);
                this.addButton(_snowman2);
                if (3 >= n) {
                    _snowman2.active = false;
                    continue;
                }
                if (statusEffect != this.secondaryEffect) continue;
                _snowman2.setDisabled(true);
            }
            if (this.primaryEffect != null) {
                int n3;
                EffectButtonWidget _snowman3 = new EffectButtonWidget(this.x + 167 + (n3 - 1) * 24 - _snowman / 2, this.y + 47, this.primaryEffect, false);
                this.addButton(_snowman3);
                if (3 >= n) {
                    _snowman3.active = false;
                } else if (this.primaryEffect == this.secondaryEffect) {
                    _snowman3.setDisabled(true);
                }
            }
        }
        this.doneButton.active = ((BeaconScreenHandler)this.handler).hasPayment() && this.primaryEffect != null;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        BeaconScreen.drawCenteredText(matrices, this.textRenderer, field_26560, 62, 10, 0xE0E0E0);
        BeaconScreen.drawCenteredText(matrices, this.textRenderer, field_26561, 169, 10, 0xE0E0E0);
        for (AbstractButtonWidget abstractButtonWidget : this.buttons) {
            if (!abstractButtonWidget.isHovered()) continue;
            abstractButtonWidget.renderToolTip(matrices, mouseX - this.x, mouseY - this.y);
            break;
        }
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.itemRenderer.zOffset = 100.0f;
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.NETHERITE_INGOT), n + 20, _snowman + 109);
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.EMERALD), n + 41, _snowman + 109);
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.DIAMOND), n + 41 + 22, _snowman + 109);
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.GOLD_INGOT), n + 42 + 44, _snowman + 109);
        this.itemRenderer.renderInGuiWithOverrides(new ItemStack(Items.IRON_INGOT), n + 42 + 66, _snowman + 109);
        this.itemRenderer.zOffset = 0.0f;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    static /* synthetic */ boolean method_17545(BeaconScreen beaconScreen, boolean bl) {
        beaconScreen.consumeGem = bl;
        return beaconScreen.consumeGem;
    }

    class CancelButtonWidget
    extends IconButtonWidget {
        public CancelButtonWidget(int x, int y) {
            super(x, y, 112, 220);
        }

        @Override
        public void onPress() {
            ((BeaconScreen)BeaconScreen.this).client.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(((BeaconScreen)BeaconScreen.this).client.player.currentScreenHandler.syncId));
            BeaconScreen.this.client.openScreen(null);
        }

        @Override
        public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
            BeaconScreen.this.renderTooltip(matrices, ScreenTexts.CANCEL, mouseX, mouseY);
        }
    }

    class DoneButtonWidget
    extends IconButtonWidget {
        public DoneButtonWidget(int x, int y) {
            super(x, y, 90, 220);
        }

        @Override
        public void onPress() {
            BeaconScreen.this.client.getNetworkHandler().sendPacket(new UpdateBeaconC2SPacket(StatusEffect.getRawId(BeaconScreen.this.primaryEffect), StatusEffect.getRawId(BeaconScreen.this.secondaryEffect)));
            ((BeaconScreen)BeaconScreen.this).client.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(((BeaconScreen)BeaconScreen.this).client.player.currentScreenHandler.syncId));
            BeaconScreen.this.client.openScreen(null);
        }

        @Override
        public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
            BeaconScreen.this.renderTooltip(matrices, ScreenTexts.DONE, mouseX, mouseY);
        }
    }

    static abstract class IconButtonWidget
    extends BaseButtonWidget {
        private final int u;
        private final int v;

        protected IconButtonWidget(int x, int y, int u, int v) {
            super(x, y);
            this.u = u;
            this.v = v;
        }

        @Override
        protected void renderExtra(MatrixStack matrixStack) {
            this.drawTexture(matrixStack, this.x + 2, this.y + 2, this.u, this.v, 18, 18);
        }
    }

    class EffectButtonWidget
    extends BaseButtonWidget {
        private final StatusEffect effect;
        private final Sprite sprite;
        private final boolean primary;
        private final Text field_26562;

        public EffectButtonWidget(int x, int y, StatusEffect statusEffect, boolean primary) {
            super(x, y);
            this.effect = statusEffect;
            this.sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(statusEffect);
            this.primary = primary;
            this.field_26562 = this.method_30902(statusEffect, primary);
        }

        private Text method_30902(StatusEffect statusEffect, boolean bl) {
            TranslatableText translatableText = new TranslatableText(statusEffect.getTranslationKey());
            if (!bl && statusEffect != StatusEffects.REGENERATION) {
                translatableText.append(" II");
            }
            return translatableText;
        }

        @Override
        public void onPress() {
            if (this.isDisabled()) {
                return;
            }
            if (this.primary) {
                BeaconScreen.this.primaryEffect = this.effect;
            } else {
                BeaconScreen.this.secondaryEffect = this.effect;
            }
            BeaconScreen.this.buttons.clear();
            BeaconScreen.this.children.clear();
            BeaconScreen.this.init();
            BeaconScreen.this.tick();
        }

        @Override
        public void renderToolTip(MatrixStack matrices, int mouseX, int mouseY) {
            BeaconScreen.this.renderTooltip(matrices, this.field_26562, mouseX, mouseY);
        }

        @Override
        protected void renderExtra(MatrixStack matrixStack) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(this.sprite.getAtlas().getId());
            EffectButtonWidget.drawSprite(matrixStack, this.x + 2, this.y + 2, this.getZOffset(), 18, 18, this.sprite);
        }
    }

    static abstract class BaseButtonWidget
    extends AbstractPressableButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y) {
            super(x, y, 22, 22, LiteralText.EMPTY);
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n = 219;
            _snowman = 0;
            if (!this.active) {
                _snowman += this.width * 2;
            } else if (this.disabled) {
                _snowman += this.width * 1;
            } else if (this.isHovered()) {
                _snowman += this.width * 3;
            }
            this.drawTexture(matrices, this.x, this.y, _snowman, 219, this.width, this.height);
            this.renderExtra(matrices);
        }

        protected abstract void renderExtra(MatrixStack var1);

        public boolean isDisabled() {
            return this.disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }
}

