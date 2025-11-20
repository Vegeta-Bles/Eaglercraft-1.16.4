package net.minecraft.client.gui.screen;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PresetsScreen extends Screen {
   private static final Logger field_25043 = LogManager.getLogger();
   private static final List<PresetsScreen.SuperflatPreset> PRESETS = Lists.newArrayList();
   private final CustomizeFlatLevelScreen parent;
   private Text shareText;
   private Text listText;
   private PresetsScreen.SuperflatPresetsListWidget listWidget;
   private ButtonWidget selectPresetButton;
   private TextFieldWidget customPresetField;
   private FlatChunkGeneratorConfig field_25044;

   public PresetsScreen(CustomizeFlatLevelScreen parent) {
      super(new TranslatableText("createWorld.customize.presets.title"));
      this.parent = parent;
   }

   @Nullable
   private static FlatChunkGeneratorLayer method_29059(String _snowman, int _snowman) {
      String[] _snowmanxx = _snowman.split("\\*", 2);
      int _snowmanxxx;
      if (_snowmanxx.length == 2) {
         try {
            _snowmanxxx = Math.max(Integer.parseInt(_snowmanxx[0]), 0);
         } catch (NumberFormatException var10) {
            field_25043.error("Error while parsing flat world string => {}", var10.getMessage());
            return null;
         }
      } else {
         _snowmanxxx = 1;
      }

      int _snowmanxxxx = Math.min(_snowman + _snowmanxxx, 256);
      int _snowmanxxxxx = _snowmanxxxx - _snowman;
      String _snowmanxxxxxx = _snowmanxx[_snowmanxx.length - 1];

      Block _snowmanxxxxxxx;
      try {
         _snowmanxxxxxxx = Registry.BLOCK.getOrEmpty(new Identifier(_snowmanxxxxxx)).orElse(null);
      } catch (Exception var9) {
         field_25043.error("Error while parsing flat world string => {}", var9.getMessage());
         return null;
      }

      if (_snowmanxxxxxxx == null) {
         field_25043.error("Error while parsing flat world string => Unknown block, {}", _snowmanxxxxxx);
         return null;
      } else {
         FlatChunkGeneratorLayer _snowmanxxxxxxxx = new FlatChunkGeneratorLayer(_snowmanxxxxx, _snowmanxxxxxxx);
         _snowmanxxxxxxxx.setStartY(_snowman);
         return _snowmanxxxxxxxx;
      }
   }

   private static List<FlatChunkGeneratorLayer> method_29058(String _snowman) {
      List<FlatChunkGeneratorLayer> _snowmanx = Lists.newArrayList();
      String[] _snowmanxx = _snowman.split(",");
      int _snowmanxxx = 0;

      for (String _snowmanxxxx : _snowmanxx) {
         FlatChunkGeneratorLayer _snowmanxxxxx = method_29059(_snowmanxxxx, _snowmanxxx);
         if (_snowmanxxxxx == null) {
            return Collections.emptyList();
         }

         _snowmanx.add(_snowmanxxxxx);
         _snowmanxxx += _snowmanxxxxx.getThickness();
      }

      return _snowmanx;
   }

   public static FlatChunkGeneratorConfig method_29060(Registry<Biome> _snowman, String _snowman, FlatChunkGeneratorConfig _snowman) {
      Iterator<String> _snowmanxxx = Splitter.on(';').split(_snowman).iterator();
      if (!_snowmanxxx.hasNext()) {
         return FlatChunkGeneratorConfig.getDefaultConfig(_snowman);
      } else {
         List<FlatChunkGeneratorLayer> _snowmanxxxx = method_29058(_snowmanxxx.next());
         if (_snowmanxxxx.isEmpty()) {
            return FlatChunkGeneratorConfig.getDefaultConfig(_snowman);
         } else {
            FlatChunkGeneratorConfig _snowmanxxxxx = _snowman.method_29965(_snowmanxxxx, _snowman.getStructuresConfig());
            RegistryKey<Biome> _snowmanxxxxxx = BiomeKeys.PLAINS;
            if (_snowmanxxx.hasNext()) {
               try {
                  Identifier _snowmanxxxxxxx = new Identifier(_snowmanxxx.next());
                  _snowmanxxxxxx = RegistryKey.of(Registry.BIOME_KEY, _snowmanxxxxxxx);
                  _snowman.getOrEmpty(_snowmanxxxxxx).orElseThrow(() -> new IllegalArgumentException("Invalid Biome: " + _snowman));
               } catch (Exception var8) {
                  field_25043.error("Error while parsing flat world string => {}", var8.getMessage());
               }
            }

            RegistryKey<Biome> _snowmanxxxxxxx = _snowmanxxxxxx;
            _snowmanxxxxx.setBiome(() -> _snowman.getOrThrow(_snowman));
            return _snowmanxxxxx;
         }
      }
   }

   private static String method_29062(Registry<Biome> _snowman, FlatChunkGeneratorConfig _snowman) {
      StringBuilder _snowmanxx = new StringBuilder();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.getLayers().size(); _snowmanxxx++) {
         if (_snowmanxxx > 0) {
            _snowmanxx.append(",");
         }

         _snowmanxx.append(_snowman.getLayers().get(_snowmanxxx));
      }

      _snowmanxx.append(";");
      _snowmanxx.append(_snowman.getId(_snowman.getBiome()));
      return _snowmanxx.toString();
   }

   @Override
   protected void init() {
      this.client.keyboard.setRepeatEvents(true);
      this.shareText = new TranslatableText("createWorld.customize.presets.share");
      this.listText = new TranslatableText("createWorld.customize.presets.list");
      this.customPresetField = new TextFieldWidget(this.textRenderer, 50, 40, this.width - 100, 20, this.shareText);
      this.customPresetField.setMaxLength(1230);
      Registry<Biome> _snowman = this.parent.parent.moreOptionsDialog.method_29700().get(Registry.BIOME_KEY);
      this.customPresetField.setText(method_29062(_snowman, this.parent.method_29055()));
      this.field_25044 = this.parent.method_29055();
      this.children.add(this.customPresetField);
      this.listWidget = new PresetsScreen.SuperflatPresetsListWidget();
      this.children.add(this.listWidget);
      this.selectPresetButton = this.addButton(
         new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, new TranslatableText("createWorld.customize.presets.select"), _snowmanx -> {
            FlatChunkGeneratorConfig _snowmanx = method_29060(_snowman, this.customPresetField.getText(), this.field_25044);
            this.parent.method_29054(_snowmanx);
            this.client.openScreen(this.parent);
         })
      );
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, _snowmanx -> this.client.openScreen(this.parent)));
      this.updateSelectButton(this.listWidget.getSelected() != null);
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
      return this.listWidget.mouseScrolled(mouseX, mouseY, amount);
   }

   @Override
   public void resize(MinecraftClient client, int width, int height) {
      String _snowman = this.customPresetField.getText();
      this.init(client, width, height);
      this.customPresetField.setText(_snowman);
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
      RenderSystem.translatef(0.0F, 0.0F, 400.0F);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
      drawTextWithShadow(matrices, this.textRenderer, this.shareText, 50, 30, 10526880);
      drawTextWithShadow(matrices, this.textRenderer, this.listText, 50, 70, 10526880);
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

   private static void addPreset(
      Text _snowman, ItemConvertible icon, RegistryKey<Biome> _snowman, List<StructureFeature<?>> structures, boolean _snowman, boolean _snowman, boolean _snowman, FlatChunkGeneratorLayer... _snowman
   ) {
      PRESETS.add(new PresetsScreen.SuperflatPreset(icon.asItem(), _snowman, _snowmanxxxxxxxxxxxx -> {
         Map<StructureFeature<?>, StructureConfig> _snowmanxxxxxxx = Maps.newHashMap();

         for (StructureFeature<?> _snowmanxxxxxxxx : structures) {
            _snowmanxxxxxxx.put(_snowmanxxxxxxxx, (StructureConfig)StructuresConfig.DEFAULT_STRUCTURES.get(_snowmanxxxxxxxx));
         }

         StructuresConfig _snowmanxxxxxxxx = new StructuresConfig(_snowman ? Optional.of(StructuresConfig.DEFAULT_STRONGHOLD) : Optional.empty(), _snowmanxxxxxxx);
         FlatChunkGeneratorConfig _snowmanxxxxxxxxx = new FlatChunkGeneratorConfig(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx);
         if (_snowman) {
            _snowmanxxxxxxxxx.enableFeatures();
         }

         if (_snowman) {
            _snowmanxxxxxxxxx.enableLakes();
         }

         for (int _snowmanxxxxxxxxxx = _snowman.length - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
            _snowmanxxxxxxxxx.getLayers().add(_snowman[_snowmanxxxxxxxxxx]);
         }

         _snowmanxxxxxxxxx.setBiome(() -> _snowmanxxxxxxxxxxxx.getOrThrow(_snowman));
         _snowmanxxxxxxxxx.updateLayerBlocks();
         return _snowmanxxxxxxxxx.withStructuresConfig(_snowmanxxxxxxxx);
      }));
   }

   static {
      addPreset(
         new TranslatableText("createWorld.customize.preset.classic_flat"),
         Blocks.GRASS_BLOCK,
         BiomeKeys.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE),
         false,
         false,
         false,
         new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK),
         new FlatChunkGeneratorLayer(2, Blocks.DIRT),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.tunnelers_dream"),
         Blocks.STONE,
         BiomeKeys.MOUNTAINS,
         Arrays.asList(StructureFeature.MINESHAFT),
         true,
         true,
         false,
         new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK),
         new FlatChunkGeneratorLayer(5, Blocks.DIRT),
         new FlatChunkGeneratorLayer(230, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.water_world"),
         Items.WATER_BUCKET,
         BiomeKeys.DEEP_OCEAN,
         Arrays.asList(StructureFeature.OCEAN_RUIN, StructureFeature.SHIPWRECK, StructureFeature.MONUMENT),
         false,
         false,
         false,
         new FlatChunkGeneratorLayer(90, Blocks.WATER),
         new FlatChunkGeneratorLayer(5, Blocks.SAND),
         new FlatChunkGeneratorLayer(5, Blocks.DIRT),
         new FlatChunkGeneratorLayer(5, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.overworld"),
         Blocks.GRASS,
         BiomeKeys.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.MINESHAFT, StructureFeature.PILLAGER_OUTPOST, StructureFeature.RUINED_PORTAL),
         true,
         true,
         true,
         new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK),
         new FlatChunkGeneratorLayer(3, Blocks.DIRT),
         new FlatChunkGeneratorLayer(59, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.snowy_kingdom"),
         Blocks.SNOW,
         BiomeKeys.SNOWY_TUNDRA,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.IGLOO),
         false,
         false,
         false,
         new FlatChunkGeneratorLayer(1, Blocks.SNOW),
         new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK),
         new FlatChunkGeneratorLayer(3, Blocks.DIRT),
         new FlatChunkGeneratorLayer(59, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.bottomless_pit"),
         Items.FEATHER,
         BiomeKeys.PLAINS,
         Arrays.asList(StructureFeature.VILLAGE),
         false,
         false,
         false,
         new FlatChunkGeneratorLayer(1, Blocks.GRASS_BLOCK),
         new FlatChunkGeneratorLayer(3, Blocks.DIRT),
         new FlatChunkGeneratorLayer(2, Blocks.COBBLESTONE)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.desert"),
         Blocks.SAND,
         BiomeKeys.DESERT,
         Arrays.asList(StructureFeature.VILLAGE, StructureFeature.DESERT_PYRAMID, StructureFeature.MINESHAFT),
         true,
         true,
         false,
         new FlatChunkGeneratorLayer(8, Blocks.SAND),
         new FlatChunkGeneratorLayer(52, Blocks.SANDSTONE),
         new FlatChunkGeneratorLayer(3, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.redstone_ready"),
         Items.REDSTONE,
         BiomeKeys.DESERT,
         Collections.emptyList(),
         false,
         false,
         false,
         new FlatChunkGeneratorLayer(52, Blocks.SANDSTONE),
         new FlatChunkGeneratorLayer(3, Blocks.STONE),
         new FlatChunkGeneratorLayer(1, Blocks.BEDROCK)
      );
      addPreset(
         new TranslatableText("createWorld.customize.preset.the_void"),
         Blocks.BARRIER,
         BiomeKeys.THE_VOID,
         Collections.emptyList(),
         false,
         true,
         false,
         new FlatChunkGeneratorLayer(1, Blocks.AIR)
      );
   }

   static class SuperflatPreset {
      public final Item icon;
      public final Text name;
      public final Function<Registry<Biome>, FlatChunkGeneratorConfig> field_25045;

      public SuperflatPreset(Item icon, Text _snowman, Function<Registry<Biome>, FlatChunkGeneratorConfig> _snowman) {
         this.icon = icon;
         this.name = _snowman;
         this.field_25045 = _snowman;
      }

      public Text getName() {
         return this.name;
      }
   }

   class SuperflatPresetsListWidget extends AlwaysSelectedEntryListWidget<PresetsScreen.SuperflatPresetsListWidget.SuperflatPresetEntry> {
      public SuperflatPresetsListWidget() {
         super(PresetsScreen.this.client, PresetsScreen.this.width, PresetsScreen.this.height, 80, PresetsScreen.this.height - 37, 24);

         for (int _snowman = 0; _snowman < PresetsScreen.PRESETS.size(); _snowman++) {
            this.addEntry(new PresetsScreen.SuperflatPresetsListWidget.SuperflatPresetEntry());
         }
      }

      public void setSelected(@Nullable PresetsScreen.SuperflatPresetsListWidget.SuperflatPresetEntry _snowman) {
         super.setSelected(_snowman);
         if (_snowman != null) {
            NarratorManager.INSTANCE
               .narrate(new TranslatableText("narrator.select", PresetsScreen.PRESETS.get(this.children().indexOf(_snowman)).getName()).getString());
         }

         PresetsScreen.this.updateSelectButton(_snowman != null);
      }

      @Override
      protected boolean isFocused() {
         return PresetsScreen.this.getFocused() == this;
      }

      @Override
      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
         } else {
            if ((keyCode == 257 || keyCode == 335) && this.getSelected() != null) {
               this.getSelected().setPreset();
            }

            return false;
         }
      }

      public class SuperflatPresetEntry extends AlwaysSelectedEntryListWidget.Entry<PresetsScreen.SuperflatPresetsListWidget.SuperflatPresetEntry> {
         public SuperflatPresetEntry() {
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            PresetsScreen.SuperflatPreset _snowman = PresetsScreen.PRESETS.get(index);
            this.method_2200(matrices, x, y, _snowman.icon);
            PresetsScreen.this.textRenderer.draw(matrices, _snowman.name, (float)(x + 18 + 5), (float)(y + 6), 16777215);
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
            PresetsScreen.SuperflatPreset _snowman = PresetsScreen.PRESETS.get(SuperflatPresetsListWidget.this.children().indexOf(this));
            Registry<Biome> _snowmanx = PresetsScreen.this.parent.parent.moreOptionsDialog.method_29700().get(Registry.BIOME_KEY);
            PresetsScreen.this.field_25044 = _snowman.field_25045.apply(_snowmanx);
            PresetsScreen.this.customPresetField.setText(PresetsScreen.method_29062(_snowmanx, PresetsScreen.this.field_25044));
            PresetsScreen.this.customPresetField.setCursorToStart();
         }

         private void method_2200(MatrixStack _snowman, int _snowman, int _snowman, Item _snowman) {
            this.method_2198(_snowman, _snowman + 1, _snowman + 1);
            RenderSystem.enableRescaleNormal();
            PresetsScreen.this.itemRenderer.renderGuiItemIcon(new ItemStack(_snowman), _snowman + 2, _snowman + 2);
            RenderSystem.disableRescaleNormal();
         }

         private void method_2198(MatrixStack _snowman, int _snowman, int _snowman) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            SuperflatPresetsListWidget.this.client.getTextureManager().bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
            DrawableHelper.drawTexture(_snowman, _snowman, _snowman, PresetsScreen.this.getZOffset(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
