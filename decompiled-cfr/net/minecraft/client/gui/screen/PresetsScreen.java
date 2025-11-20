/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.CustomizeFlatLevelScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PresetsScreen
extends Screen {
    private static final Logger field_25043 = LogManager.getLogger();
    private static final List<SuperflatPreset> PRESETS = Lists.newArrayList();
    private final CustomizeFlatLevelScreen parent;
    private Text shareText;
    private Text listText;
    private SuperflatPresetsListWidget listWidget;
    private ButtonWidget selectPresetButton;
    private TextFieldWidget customPresetField;
    private FlatChunkGeneratorConfig field_25044;

    public PresetsScreen(CustomizeFlatLevelScreen parent) {
        super(new TranslatableText("createWorld.customize.presets.title"));
        this.parent = parent;
    }

    @Nullable
    private static FlatChunkGeneratorLayer method_29059(String string, int n2) {
        int n2;
        int n3;
        String[] stringArray = string.split("\\*", 2);
        if (stringArray.length == 2) {
            try {
                n3 = Math.max(Integer.parseInt(stringArray[0]), 0);
            }
            catch (NumberFormatException numberFormatException) {
                field_25043.error("Error while parsing flat world string => {}", (Object)numberFormatException.getMessage());
                return null;
            }
        } else {
            n3 = 1;
        }
        _snowman = Math.min(n2 + n3, 256);
        _snowman = _snowman - n2;
        String _snowman2 = stringArray[stringArray.length - 1];
        try {
            Block block = Registry.BLOCK.getOrEmpty(new Identifier(_snowman2)).orElse(null);
        }
        catch (Exception exception) {
            field_25043.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
            return null;
        }
        if (block == null) {
            field_25043.error("Error while parsing flat world string => Unknown block, {}", (Object)_snowman2);
            return null;
        }
        FlatChunkGeneratorLayer flatChunkGeneratorLayer = new FlatChunkGeneratorLayer(_snowman, block);
        flatChunkGeneratorLayer.setStartY(n2);
        return flatChunkGeneratorLayer;
    }

    private static List<FlatChunkGeneratorLayer> method_29058(String string) {
        ArrayList arrayList = Lists.newArrayList();
        String[] _snowman2 = string.split(",");
        int _snowman3 = 0;
        for (String string2 : _snowman2) {
            FlatChunkGeneratorLayer flatChunkGeneratorLayer = PresetsScreen.method_29059(string2, _snowman3);
            if (flatChunkGeneratorLayer == null) {
                return Collections.emptyList();
            }
            arrayList.add(flatChunkGeneratorLayer);
            _snowman3 += flatChunkGeneratorLayer.getThickness();
        }
        return arrayList;
    }

    public static FlatChunkGeneratorConfig method_29060(Registry<Biome> registry, String string, FlatChunkGeneratorConfig flatChunkGeneratorConfig) {
        Object object;
        Iterator iterator = Splitter.on((char)';').split((CharSequence)string).iterator();
        if (!iterator.hasNext()) {
            return FlatChunkGeneratorConfig.getDefaultConfig(registry);
        }
        List<FlatChunkGeneratorLayer> _snowman2 = PresetsScreen.method_29058((String)iterator.next());
        if (_snowman2.isEmpty()) {
            return FlatChunkGeneratorConfig.getDefaultConfig(registry);
        }
        FlatChunkGeneratorConfig _snowman3 = flatChunkGeneratorConfig.method_29965(_snowman2, flatChunkGeneratorConfig.getStructuresConfig());
        RegistryKey<Biome> _snowman4 = BiomeKeys.PLAINS;
        if (iterator.hasNext()) {
            try {
                object = new Identifier((String)iterator.next());
                _snowman4 = RegistryKey.of(Registry.BIOME_KEY, (Identifier)object);
                registry.getOrEmpty(_snowman4).orElseThrow(() -> PresetsScreen.method_29061((Identifier)object));
            }
            catch (Exception exception) {
                field_25043.error("Error while parsing flat world string => {}", (Object)exception.getMessage());
            }
        }
        object = _snowman4;
        _snowman3.setBiome(() -> (Biome)registry.getOrThrow((RegistryKey<Biome>)object));
        return _snowman3;
    }

    private static String method_29062(Registry<Biome> registry, FlatChunkGeneratorConfig flatChunkGeneratorConfig) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < flatChunkGeneratorConfig.getLayers().size(); ++i) {
            if (i > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(flatChunkGeneratorConfig.getLayers().get(i));
        }
        stringBuilder.append(";");
        stringBuilder.append(registry.getId(flatChunkGeneratorConfig.getBiome()));
        return stringBuilder.toString();
    }

    @Override
    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.shareText = new TranslatableText("createWorld.customize.presets.share");
        this.listText = new TranslatableText("createWorld.customize.presets.list");
        this.customPresetField = new TextFieldWidget(this.textRenderer, 50, 40, this.width - 100, 20, this.shareText);
        this.customPresetField.setMaxLength(1230);
        MutableRegistry<Biome> mutableRegistry = this.parent.parent.moreOptionsDialog.method_29700().get(Registry.BIOME_KEY);
        this.customPresetField.setText(PresetsScreen.method_29062(mutableRegistry, this.parent.method_29055()));
        this.field_25044 = this.parent.method_29055();
        this.children.add(this.customPresetField);
        this.listWidget = new SuperflatPresetsListWidget();
        this.children.add(this.listWidget);
        this.selectPresetButton = this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, new TranslatableText("createWorld.customize.presets.select"), buttonWidget -> {
            FlatChunkGeneratorConfig flatChunkGeneratorConfig = PresetsScreen.method_29060(mutableRegistry, this.customPresetField.getText(), this.field_25044);
            this.parent.method_29054(flatChunkGeneratorConfig);
            this.client.openScreen(this.parent);
        }));
        this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, buttonWidget -> this.client.openScreen(this.parent)));
        this.updateSelectButton(this.listWidget.getSelected() != null);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return this.listWidget.mouseScrolled(mouseX, mouseY, amount);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.customPresetField.getText();
        this.init(client, width, height);
        this.customPresetField.setText(string);
    }

    @Override
    public void onClose() {
        this.client.openScreen(this.parent);
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.listWidget.render(matrices, mouseX, mouseY, delta);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0f, 0.0f, 400.0f);
        PresetsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 0xFFFFFF);
        PresetsScreen.drawTextWithShadow(matrices, this.textRenderer, this.shareText, 50, 30, 0xA0A0A0);
        PresetsScreen.drawTextWithShadow(matrices, this.textRenderer, this.listText, 50, 70, 0xA0A0A0);
        RenderSystem.popMatrix();
        this.customPresetField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        this.customPresetField.tick();
        super.tick();
    }

    public void updateSelectButton(boolean hasSelected) {
        this.selectPresetButton.active = hasSelected || this.customPresetField.getText().length() > 1;
    }

    private static void addPreset(Text text, ItemConvertible icon, RegistryKey<Biome> registryKey, List<StructureFeature<?>> structures, boolean bl, boolean bl2, boolean bl3, FlatChunkGeneratorLayer ... flatChunkGeneratorLayerArray) {
        PRESETS.add(new SuperflatPreset(icon.asItem(), text, registry2 -> {
            Registry registry2;
            boolean bl4;
            Object _snowman32;
            HashMap hashMap = Maps.newHashMap();
            for (Object _snowman32 : structures) {
                hashMap.put(_snowman32, StructuresConfig.DEFAULT_STRUCTURES.get(_snowman32));
            }
            StructuresConfig _snowman2 = new StructuresConfig(bl ? Optional.of(StructuresConfig.DEFAULT_STRONGHOLD) : Optional.empty(), hashMap);
            _snowman32 = new FlatChunkGeneratorConfig(_snowman2, (Registry<Biome>)registry2);
            if (bl2) {
                ((FlatChunkGeneratorConfig)_snowman32).enableFeatures();
            }
            if (bl3) {
                ((FlatChunkGeneratorConfig)_snowman32).enableLakes();
            }
            for (int i = flatChunkGeneratorLayerArray.length - 1; i >= 0; --i) {
                ((FlatChunkGeneratorConfig)_snowman32).getLayers().add(flatChunkGeneratorLayerArray[i]);
            }
            ((FlatChunkGeneratorConfig)_snowman32).setBiome(() -> (Biome)registry2.getOrThrow(registryKey));
            ((FlatChunkGeneratorConfig)_snowman32).updateLayerBlocks();
            return ((FlatChunkGeneratorConfig)_snowman32).withStructuresConfig(_snowman2);
        }));
    }

    private static /* synthetic */ IllegalArgumentException method_29061(Identifier identifier) {
        return new IllegalArgumentException("Invalid Biome: " + identifier);
    }

    static {
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.classic_flat"), Blocks.GRASS_BLOCK, BiomeKeys.PLAINS, Arrays.asList(StructureFeature.VILLAGE), false, false, false, new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK), new FlatChunkGeneratorLayer(2, Blocks.DIRT), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.tunnelers_dream"), Blocks.STONE, BiomeKeys.MOUNTAINS, Arrays.asList(StructureFeature.MINESHAFT), true, true, false, new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK), new FlatChunkGeneratorLayer(5, Blocks.DIRT), new FlatChunkGeneratorLayer(230, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.water_world"), Items.WATER_BUCKET, BiomeKeys.DEEP_OCEAN, Arrays.asList(StructureFeature.OCEAN_RUIN, StructureFeature.SHIPWRECK, StructureFeature.MONUMENT), false, false, false, new FlatChunkGeneratorLayer(90, Blocks.WATER), new FlatChunkGeneratorLayer(5, Blocks.SAND), new FlatChunkGeneratorLayer(5, Blocks.DIRT), new FlatChunkGeneratorLayer(5, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.overworld"), Blocks.GRASS, BiomeKeys.PLAINS, Arrays.asList(StructureFeature.VILLAGE, StructureFeature.MINESHAFT, StructureFeature.PILLAGER_OUTPOST, StructureFeature.RUINED_PORTAL), true, true, true, new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK), new FlatChunkGeneratorLayer(3, Blocks.DIRT), new FlatChunkGeneratorLayer(59, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.snowy_kingdom"), Blocks.SNOW, BiomeKeys.SNOWY_TUNDRA, Arrays.asList(StructureFeature.VILLAGE, StructureFeature.IGLOO), false, false, false, new FlatChunkGeneratorLayer(1, Blocks.SNOW), new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK), new FlatChunkGeneratorLayer(3, Blocks.DIRT), new FlatChunkGeneratorLayer(59, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.bottomless_pit"), Items.FEATHER, BiomeKeys.PLAINS, Arrays.asList(StructureFeature.VILLAGE), false, false, false, new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK), new FlatChunkGeneratorLayer(3, Blocks.DIRT), new FlatChunkGeneratorLayer(2, Blocks.COBBLESTONE));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.desert"), Blocks.SAND, BiomeKeys.DESERT, Arrays.asList(StructureFeature.VILLAGE, StructureFeature.DESERT_PYRAMID, StructureFeature.MINESHAFT), true, true, false, new FlatChunkGeneratorLayer(8, Blocks.SAND), new FlatChunkGeneratorLayer(52, Blocks.SANDSTONE), new FlatChunkGeneratorLayer(3, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.redstone_ready"), Items.REDSTONE, BiomeKeys.DESERT, Collections.emptyList(), false, false, false, new FlatChunkGeneratorLayer(52, Blocks.SANDSTONE), new FlatChunkGeneratorLayer(3, Blocks.STONE), new FlatChunkGeneratorLayer(1, Blocks.BEDROCK));
        PresetsScreen.addPreset(new TranslatableText("createWorld.customize.preset.the_void"), Blocks.BARRIER, BiomeKeys.THE_VOID, Collections.emptyList(), false, true, false, new FlatChunkGeneratorLayer(1, Blocks.AIR));
    }

    static class SuperflatPreset {
        public final Item icon;
        public final Text name;
        public final Function<Registry<Biome>, FlatChunkGeneratorConfig> field_25045;

        public SuperflatPreset(Item icon, Text text, Function<Registry<Biome>, FlatChunkGeneratorConfig> function) {
            this.icon = icon;
            this.name = text;
            this.field_25045 = function;
        }

        public Text getName() {
            return this.name;
        }
    }

    class SuperflatPresetsListWidget
    extends AlwaysSelectedEntryListWidget<SuperflatPresetEntry> {
        public SuperflatPresetsListWidget() {
            super(PresetsScreen.this.client, PresetsScreen.this.width, PresetsScreen.this.height, 80, PresetsScreen.this.height - 37, 24);
            for (int i = 0; i < PRESETS.size(); ++i) {
                this.addEntry(new SuperflatPresetEntry());
            }
        }

        @Override
        public void setSelected(@Nullable SuperflatPresetEntry superflatPresetEntry) {
            super.setSelected(superflatPresetEntry);
            if (superflatPresetEntry != null) {
                NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", ((SuperflatPreset)PRESETS.get(this.children().indexOf(superflatPresetEntry))).getName()).getString());
            }
            PresetsScreen.this.updateSelectButton(superflatPresetEntry != null);
        }

        @Override
        protected boolean isFocused() {
            return PresetsScreen.this.getFocused() == this;
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (super.keyPressed(keyCode, scanCode, modifiers)) {
                return true;
            }
            if ((keyCode == 257 || keyCode == 335) && this.getSelected() != null) {
                ((SuperflatPresetEntry)this.getSelected()).setPreset();
            }
            return false;
        }

        public class SuperflatPresetEntry
        extends AlwaysSelectedEntryListWidget.Entry<SuperflatPresetEntry> {
            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                SuperflatPreset superflatPreset = (SuperflatPreset)PRESETS.get(index);
                this.method_2200(matrices, x, y, superflatPreset.icon);
                PresetsScreen.this.textRenderer.draw(matrices, superflatPreset.name, (float)(x + 18 + 5), (float)(y + 6), 0xFFFFFF);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    this.setPreset();
                }
                return false;
            }

            private void setPreset() {
                SuperflatPresetsListWidget.this.setSelected(this);
                SuperflatPreset superflatPreset = (SuperflatPreset)PRESETS.get(SuperflatPresetsListWidget.this.children().indexOf(this));
                MutableRegistry<Biome> _snowman2 = ((PresetsScreen)PresetsScreen.this).parent.parent.moreOptionsDialog.method_29700().get(Registry.BIOME_KEY);
                PresetsScreen.this.field_25044 = superflatPreset.field_25045.apply(_snowman2);
                PresetsScreen.this.customPresetField.setText(PresetsScreen.method_29062(_snowman2, PresetsScreen.this.field_25044));
                PresetsScreen.this.customPresetField.setCursorToStart();
            }

            private void method_2200(MatrixStack matrixStack, int n, int n2, Item item) {
                this.method_2198(matrixStack, n + 1, n2 + 1);
                RenderSystem.enableRescaleNormal();
                PresetsScreen.this.itemRenderer.renderGuiItemIcon(new ItemStack(item), n + 2, n2 + 2);
                RenderSystem.disableRescaleNormal();
            }

            private void method_2198(MatrixStack matrixStack, int n, int n2) {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                SuperflatPresetsListWidget.this.client.getTextureManager().bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
                DrawableHelper.drawTexture(matrixStack, n, n2, PresetsScreen.this.getZOffset(), 0.0f, 0.0f, 18, 18, 128, 128);
            }
        }
    }
}

