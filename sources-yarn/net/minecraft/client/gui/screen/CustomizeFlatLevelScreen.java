package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class CustomizeFlatLevelScreen extends Screen {
   protected final CreateWorldScreen parent;
   private final Consumer<FlatChunkGeneratorConfig> field_24565;
   private FlatChunkGeneratorConfig config;
   private Text tileText;
   private Text heightText;
   private CustomizeFlatLevelScreen.SuperflatLayersListWidget layers;
   private ButtonWidget widgetButtonRemoveLayer;

   public CustomizeFlatLevelScreen(CreateWorldScreen arg, Consumer<FlatChunkGeneratorConfig> consumer, FlatChunkGeneratorConfig arg2) {
      super(new TranslatableText("createWorld.customize.flat.title"));
      this.parent = arg;
      this.field_24565 = consumer;
      this.config = arg2;
   }

   public FlatChunkGeneratorConfig method_29055() {
      return this.config;
   }

   public void method_29054(FlatChunkGeneratorConfig arg) {
      this.config = arg;
   }

   @Override
   protected void init() {
      this.tileText = new TranslatableText("createWorld.customize.flat.tile");
      this.heightText = new TranslatableText("createWorld.customize.flat.height");
      this.layers = new CustomizeFlatLevelScreen.SuperflatLayersListWidget();
      this.children.add(this.layers);
      this.widgetButtonRemoveLayer = this.addButton(
         new ButtonWidget(this.width / 2 - 155, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.flat.removeLayer"), arg -> {
            if (this.method_2147()) {
               List<FlatChunkGeneratorLayer> list = this.config.getLayers();
               int i = this.layers.children().indexOf(this.layers.getSelected());
               int j = list.size() - i - 1;
               list.remove(j);
               this.layers.setSelected(list.isEmpty() ? null : this.layers.children().get(Math.min(i, list.size() - 1)));
               this.config.updateLayerBlocks();
               this.layers.method_19372();
               this.method_2145();
            }
         })
      );
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 52, 150, 20, new TranslatableText("createWorld.customize.presets"), arg -> {
         this.client.openScreen(new PresetsScreen(this));
         this.config.updateLayerBlocks();
         this.method_2145();
      }));
      this.addButton(new ButtonWidget(this.width / 2 - 155, this.height - 28, 150, 20, ScreenTexts.DONE, arg -> {
         this.field_24565.accept(this.config);
         this.client.openScreen(this.parent);
         this.config.updateLayerBlocks();
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 5, this.height - 28, 150, 20, ScreenTexts.CANCEL, arg -> {
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
      int k = this.width / 2 - 92 - 16;
      drawTextWithShadow(matrices, this.textRenderer, this.tileText, k, 32, 16777215);
      drawTextWithShadow(matrices, this.textRenderer, this.heightText, k + 2 + 213 - this.textRenderer.getWidth(this.heightText), 32, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
   }

   @Environment(EnvType.CLIENT)
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

         for (int i = 0; i < CustomizeFlatLevelScreen.this.config.getLayers().size(); i++) {
            this.addEntry(new CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem());
         }
      }

      public void setSelected(@Nullable CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem arg) {
         super.setSelected(arg);
         if (arg != null) {
            FlatChunkGeneratorLayer lv = CustomizeFlatLevelScreen.this.config
               .getLayers()
               .get(CustomizeFlatLevelScreen.this.config.getLayers().size() - this.children().indexOf(arg) - 1);
            Item lv2 = lv.getBlockState().getBlock().asItem();
            if (lv2 != Items.AIR) {
               NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", lv2.getName(new ItemStack(lv2))).getString());
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
         int i = this.children().indexOf(this.getSelected());
         this.clearEntries();

         for (int j = 0; j < CustomizeFlatLevelScreen.this.config.getLayers().size(); j++) {
            this.addEntry(new CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem());
         }

         List<CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem> list = this.children();
         if (i >= 0 && i < list.size()) {
            this.setSelected(list.get(i));
         }
      }

      @Environment(EnvType.CLIENT)
      class SuperflatLayerItem extends AlwaysSelectedEntryListWidget.Entry<CustomizeFlatLevelScreen.SuperflatLayersListWidget.SuperflatLayerItem> {
         private SuperflatLayerItem() {
         }

         @Override
         public void render(
            MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
         ) {
            FlatChunkGeneratorLayer lv = CustomizeFlatLevelScreen.this.config
               .getLayers()
               .get(CustomizeFlatLevelScreen.this.config.getLayers().size() - index - 1);
            BlockState lv2 = lv.getBlockState();
            Item lv3 = lv2.getBlock().asItem();
            if (lv3 == Items.AIR) {
               if (lv2.isOf(Blocks.WATER)) {
                  lv3 = Items.WATER_BUCKET;
               } else if (lv2.isOf(Blocks.LAVA)) {
                  lv3 = Items.LAVA_BUCKET;
               }
            }

            ItemStack lv4 = new ItemStack(lv3);
            this.method_19375(matrices, x, y, lv4);
            CustomizeFlatLevelScreen.this.textRenderer.draw(matrices, lv3.getName(lv4), (float)(x + 18 + 5), (float)(y + 3), 16777215);
            String string;
            if (index == 0) {
               string = I18n.translate("createWorld.customize.flat.layer.top", lv.getThickness());
            } else if (index == CustomizeFlatLevelScreen.this.config.getLayers().size() - 1) {
               string = I18n.translate("createWorld.customize.flat.layer.bottom", lv.getThickness());
            } else {
               string = I18n.translate("createWorld.customize.flat.layer", lv.getThickness());
            }

            CustomizeFlatLevelScreen.this.textRenderer
               .draw(matrices, string, (float)(x + 2 + 213 - CustomizeFlatLevelScreen.this.textRenderer.getWidth(string)), (float)(y + 3), 16777215);
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

         private void method_19375(MatrixStack arg, int i, int j, ItemStack arg2) {
            this.method_19373(arg, i + 1, j + 1);
            RenderSystem.enableRescaleNormal();
            if (!arg2.isEmpty()) {
               CustomizeFlatLevelScreen.this.itemRenderer.renderGuiItemIcon(arg2, i + 2, j + 2);
            }

            RenderSystem.disableRescaleNormal();
         }

         private void method_19373(MatrixStack arg, int i, int j) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            SuperflatLayersListWidget.this.client.getTextureManager().bindTexture(DrawableHelper.STATS_ICON_TEXTURE);
            DrawableHelper.drawTexture(arg, i, j, CustomizeFlatLevelScreen.this.getZOffset(), 0.0F, 0.0F, 18, 18, 128, 128);
         }
      }
   }
}
