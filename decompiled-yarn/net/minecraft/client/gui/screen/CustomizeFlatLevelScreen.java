package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawableHelper;
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

public class CustomizeFlatLevelScreen extends Screen {
   protected final CreateWorldScreen parent;
   private final Consumer<FlatChunkGeneratorConfig> field_24565;
   private FlatChunkGeneratorConfig config;
   private Text tileText;
   private Text heightText;
   private CustomizeFlatLevelScreen.SuperflatLayersListWidget layers;
   private ButtonWidget widgetButtonRemoveLayer;

   public CustomizeFlatLevelScreen(CreateWorldScreen _snowman, Consumer<FlatChunkGeneratorConfig> _snowman, FlatChunkGeneratorConfig _snowman) {
      super(new TranslatableText("createWorld.customize.flat.title"));
      this.parent = _snowman;
      this.field_24565 = _snowman;
      this.config = _snowman;
   }

   public FlatChunkGeneratorConfig method_29055() {
      return this.config;
   }

   public void method_29054(FlatChunkGeneratorConfig _snowman) {
      this.config = _snowman;
   }

   @Override
   protected void init() {
      this.tileText = new TranslatableText("createWorld.customize.flat.tile");
      this.heightText = new TranslatableText("createWorld.customize.flat.height");
      this.layers = new CustomizeFlatLevelScreen.SuperflatLayersListWidget();
      this.children.add(this.layers);
      this.widgetButtonRemoveLayer = this.addButton(
         new ButtonWidget(this.width / 2 - 155, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.flat.removeLayer"), _snowman -> {
            if (this.method_2147()) {
               List<FlatChunkGeneratorLayer> _snowmanx = this.config.getLayers();
               int _snowmanxx = this.layers.children().indexOf(this.layers.getSelected());
               int _snowmanxxx = _snowmanx.size() - _snowmanxx - 1;
               _snowmanx.remove(_snowmanxxx);
               this.layers.setSelected(_snowmanx.isEmpty() ? null : this.layers.children().get(Math.min(_snowmanxx, _snowmanx.size() - 1)));
               this.config.updateLayerBlocks();
               this.layers.method_19372();
               this.method_2145();
            }
         })
      );
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.presets"), _snowman -> {
         this.client.openScreen(new PresetsScreen(this));
         this.config.updateLayerBlocks();
         this.method_2145();
      }));
      this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, ScreenTexts.DONE, _snowman -> {
         this.field_24565.accept(this.config);
         this.client.openScreen(this.parent);
         this.config.updateLayerBlocks();
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, _snowman -> {
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
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, 16777215);
      int _snowman = this.width / 2 - 92 - 16;
      drawTextWithShadow(matrices, this.textRenderer, this.tileText, _snowman, 32, 16777215);
      drawTextWithShadow(matrices, this.textRenderer, this.heightText, _snowman + 2 + 213 - this.textRenderer.getWidth(this.heightText), 32, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   class SuperflatLayersListWidget extends AlwaysSelectedEntryListWidget<CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem> {
      public SuperflatLayersListWidget() {
         super(
            CustomizeFlatLevelScreen.this.client,
            CustomizeFlatLevelScreen.this.width,
            CustomizeFlatLevelScreen.this.height,
            43,
            CustomizeFlatLevelScreen.this.height - 60,
            24
         );

         for (int _snowman = 0; _snowman < CustomizeFlatLevelScreen.this.config.getLayers().size(); _snowman++) {
            this.addEntry(new CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem());
         }
      }

      public void setSelected(@Nullable CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem _snowman) {
         super.setSelected(_snowman);
         if (_snowman != null) {
            FlatChunkGeneratorLayer _snowmanx = CustomizeFlatLevelScreen.this.config
               .getLayers()
               .get(CustomizeFlatLevelScreen.this.config.getLayers().size() - this.children().indexOf(_snowman) - 1);
            Item _snowmanxx = _snowmanx.getBlockState().getBlock().asItem();
            if (_snowmanxx != Items.AIR) {
               NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", _snowmanxx.getName(new ItemStack(_snowmanxx))).getString());
            }
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
         int _snowman = this.children().indexOf(this.getSelected());
         this.clearEntries();

         for (int _snowmanx = 0; _snowmanx < CustomizeFlatLevelScreen.this.config.getLayers().size(); _snowmanx++) {
            this.addEntry(new CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem());
         }

         List<CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem> _snowmanx = this.children();
         if (_snowman >= 0 && _snowman < _snowmanx.size()) {
            this.setSelected(_snowmanx.get(_snowman));
         }
      }

      class SuperflatLayerItem extends AlwaysSelectedEntryListWidget.Entry<CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem> {
         private SuperflatLayerItem() {
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            FlatChunkGeneratorLayer _snowman = CustomizeFlatLevelScreen.this.config
               .getLayers()
               .get(CustomizeFlatLevelScreen.this.config.getLayers().size() - index - 1);
            BlockState _snowmanx = _snowman.getBlockState();
            Item _snowmanxx = _snowmanx.getBlock().asItem();
            if (_snowmanxx == Items.AIR) {
               if (_snowmanx.isOf(Blocks.WATER)) {
                  _snowmanxx = Items.WATER_BUCKET;
               } else if (_snowmanx.isOf(Blocks.LAVA)) {
                  _snowmanxx = Items.LAVA_BUCKET;
               }
            }

            ItemStack _snowmanxxx = new ItemStack(_snowmanxx);
            this.method_19375(matrices, x, y, _snowmanxxx);
            CustomizeFlatLevelScreen.this.textRenderer.draw(matrices, _snowmanxx.getName(_snowmanxxx), (float)(x + 18 + 5), (float)(y + 3), 16777215);
            String _snowmanxxxx;
            if (index == 0) {
               _snowmanxxxx = I18n.translate("createWorld.customize.flat.layer.top", _snowman.getThickness());
            } else if (index == CustomizeFlatLevelScreen.this.config.getLayers().size() - 1) {
               _snowmanxxxx = I18n.translate("createWorld.customize.flat.layer.bottom", _snowman.getThickness());
            } else {
               _snowmanxxxx = I18n.translate("createWorld.customize.flat.layer", _snowman.getThickness());
            }

            CustomizeFlatLevelScreen.this.textRenderer
               .draw(matrices, _snowmanxxxx, (float)(x + 2 + 213 - CustomizeFlatLevelScreen.this.textRenderer.getWidth(_snowmanxxxx)), (float)(y + 3), 16777215);
         }

         @Override
         public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
               SuperflatLayersListWidget.this.setSelected(this);
               return true;
            } else {
               return false;
            }
         }

         private void method_19375(MatrixStack _snowman, int _snowman, int _snowman, ItemStack _snowman) {
            this.method_19373(_snowman, _snowman + 1, _snowman + 1);
            RenderSystem.enableRescaleNormal();
            if (!_snowman.isEmpty()) {
               CustomizeFlatLevelScreen.this.itemRenderer.renderGuiItemIcon(_snowman, _snowman + 2, _snowman + 2);
            }

            RenderSystem.disableRescaleNormal();
         }

         private void method_19373(MatrixStack _snowman, int _snowman, int _snowman) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            SuperflatLayersListWidget.this.client.getTextureManager().bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
            DrawableHelper.drawTexture(_snowman, _snowman, _snowman, CustomizeFlatLevelScreen.this.getZOffset(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
