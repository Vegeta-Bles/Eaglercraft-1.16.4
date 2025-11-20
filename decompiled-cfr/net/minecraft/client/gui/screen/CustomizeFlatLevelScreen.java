/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.PresetsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;

public class CustomizeFlatLevelScreen
extends Screen {
    protected final CreateWorldScreen parent;
    private final Consumer<FlatChunkGeneratorConfig> field_24565;
    private FlatChunkGeneratorConfig config;
    private Text tileText;
    private Text heightText;
    private SuperflatLayersListWidget layers;
    private ButtonWidget widgetButtonRemoveLayer;

    public CustomizeFlatLevelScreen(CreateWorldScreen createWorldScreen, Consumer<FlatChunkGeneratorConfig> consumer, FlatChunkGeneratorConfig flatChunkGeneratorConfig) {
        super(new TranslatableText("createWorld.customize.flat.title"));
        this.parent = createWorldScreen;
        this.field_24565 = consumer;
        this.config = flatChunkGeneratorConfig;
    }

    public FlatChunkGeneratorConfig method_29055() {
        return this.config;
    }

    public void method_29054(FlatChunkGeneratorConfig flatChunkGeneratorConfig) {
        this.config = flatChunkGeneratorConfig;
    }

    @Override
    protected void init() {
        this.tileText = new TranslatableText("createWorld.customize.flat.tile");
        this.heightText = new TranslatableText("createWorld.customize.flat.height");
        this.layers = new SuperflatLayersListWidget();
        this.children.add(this.layers);
        this.widgetButtonRemoveLayer = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.flat.removeLayer"), buttonWidget -> {
            if (!this.method_2147()) {
                return;
            }
            List<FlatChunkGeneratorLayer> list = this.config.getLayers();
            int _snowman2 = this.layers.children().indexOf(this.layers.getSelected());
            int _snowman3 = list.size() - _snowman2 - 1;
            list.remove(_snowman3);
            this.layers.setSelected(list.isEmpty() ? null : (SuperflatLayersListWidget.SuperflatLayerItem)this.layers.children().get(Math.min(_snowman2, list.size() - 1)));
            this.config.updateLayerBlocks();
            this.layers.method_19372();
            this.method_2145();
        }));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.presets"), buttonWidget -> {
            this.client.openScreen(new PresetsScreen(this));
            this.config.updateLayerBlocks();
            this.method_2145();
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, ScreenTexts.DONE, buttonWidget -> {
            this.field_24565.accept(this.config);
            this.client.openScreen(this.parent);
            this.config.updateLayerBlocks();
        }));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, buttonWidget -> {
            this.client.openScreen(this.parent);
            this.config.updateLayerBlocks();
        }));
        this.config.updateLayerBlocks();
        this.method_2145();
    }

    private void method_2145() {
        this.widgetButtonRemoveLayer.active = this.method_2147();
    }

    private boolean method_2147() {
        return this.layers.getSelected() != null;
    }

    @Override
    public void onClose() {
        this.client.openScreen(this.parent);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.layers.render(matrices, mouseX, mouseY, delta);
        CustomizeFlatLevelScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
        int n = this.width / 2 - 92 - 16;
        CustomizeFlatLevelScreen.drawTextWithShadow(matrices, this.textRenderer, this.tileText, n, 32, 0xFFFFFF);
        CustomizeFlatLevelScreen.drawTextWithShadow(matrices, this.textRenderer, this.heightText, n + 2 + 213 - this.textRenderer.getWidth(this.heightText), 32, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    class SuperflatLayersListWidget
    extends AlwaysSelectedEntryListWidget<SuperflatLayerItem> {
        public SuperflatLayersListWidget() {
            super(CustomizeFlatLevelScreen.this.client, CustomizeFlatLevelScreen.this.width, CustomizeFlatLevelScreen.this.height, 43, CustomizeFlatLevelScreen.this.height - 60, 24);
            for (int i = 0; i < CustomizeFlatLevelScreen.this.config.getLayers().size(); ++i) {
                this.addEntry(new SuperflatLayerItem());
            }
        }

        @Override
        public void setSelected(@Nullable SuperflatLayerItem superflatLayerItem) {
            super.setSelected(superflatLayerItem);
            if (superflatLayerItem != null && (_snowman = (_snowman = CustomizeFlatLevelScreen.this.config.getLayers().get(CustomizeFlatLevelScreen.this.config.getLayers().size() - this.children().indexOf(superflatLayerItem) - 1)).getBlockState().getBlock().asItem()) != Items.AIR) {
                NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", _snowman.getName(new ItemStack(_snowman))).getString());
            }
            CustomizeFlatLevelScreen.this.method_2145();
        }

        @Override
        protected boolean isFocused() {
            return CustomizeFlatLevelScreen.this.getFocused() == this;
        }

        @Override
        protected int getScrollbarPositionX() {
            return this.width - 70;
        }

        public void method_19372() {
            int n = this.children().indexOf(this.getSelected());
            this.clearEntries();
            for (_snowman = 0; _snowman < CustomizeFlatLevelScreen.this.config.getLayers().size(); ++_snowman) {
                this.addEntry(new SuperflatLayerItem());
            }
            List _snowman2 = this.children();
            if (n >= 0 && n < _snowman2.size()) {
                this.setSelected((SuperflatLayerItem)_snowman2.get(n));
            }
        }

        class SuperflatLayerItem
        extends AlwaysSelectedEntryListWidget.Entry<SuperflatLayerItem> {
            private SuperflatLayerItem() {
            }

            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                FlatChunkGeneratorLayer flatChunkGeneratorLayer = CustomizeFlatLevelScreen.this.config.getLayers().get(CustomizeFlatLevelScreen.this.config.getLayers().size() - index - 1);
                BlockState _snowman2 = flatChunkGeneratorLayer.getBlockState();
                Item _snowman3 = _snowman2.getBlock().asItem();
                if (_snowman3 == Items.AIR) {
                    if (_snowman2.isOf(Blocks.WATER)) {
                        _snowman3 = Items.WATER_BUCKET;
                    } else if (_snowman2.isOf(Blocks.LAVA)) {
                        _snowman3 = Items.LAVA_BUCKET;
                    }
                }
                ItemStack _snowman4 = new ItemStack(_snowman3);
                this.method_19375(matrices, x, y, _snowman4);
                CustomizeFlatLevelScreen.this.textRenderer.draw(matrices, _snowman3.getName(_snowman4), (float)(x + 18 + 5), (float)(y + 3), 0xFFFFFF);
                String _snowman5 = index == 0 ? I18n.translate("createWorld.customize.flat.layer.top", flatChunkGeneratorLayer.getThickness()) : (index == CustomizeFlatLevelScreen.this.config.getLayers().size() - 1 ? I18n.translate("createWorld.customize.flat.layer.bottom", flatChunkGeneratorLayer.getThickness()) : I18n.translate("createWorld.customize.flat.layer", flatChunkGeneratorLayer.getThickness()));
                CustomizeFlatLevelScreen.this.textRenderer.draw(matrices, _snowman5, (float)(x + 2 + 213 - CustomizeFlatLevelScreen.this.textRenderer.getWidth(_snowman5)), (float)(y + 3), 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    SuperflatLayersListWidget.this.setSelected(this);
                    return true;
                }
                return false;
            }

            private void method_19375(MatrixStack matrixStack, int n, int n2, ItemStack itemStack) {
                this.method_19373(matrixStack, n + 1, n2 + 1);
                RenderSystem.enableRescaleNormal();
                if (!itemStack.isEmpty()) {
                    CustomizeFlatLevelScreen.this.itemRenderer.renderGuiItemIcon(itemStack, n + 2, n2 + 2);
                }
                RenderSystem.disableRescaleNormal();
            }

            private void method_19373(MatrixStack matrixStack, int n, int n2) {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                SuperflatLayersListWidget.this.client.getTextureManager().bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, n, n2, CustomizeFlatLevelScreen.this.getZOffset(), 0.0f, 0.0f, 18, 18, 128, 128);
            }
        }
    }
}

