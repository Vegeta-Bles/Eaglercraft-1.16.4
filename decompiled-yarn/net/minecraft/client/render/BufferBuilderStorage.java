package net.minecraft.client.render;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.SortedMap;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.util.Util;

public class BufferBuilderStorage {
   private final BlockBufferBuilderStorage blockBuilders = new BlockBufferBuilderStorage();
   private final SortedMap<RenderLayer, BufferBuilder> entityBuilders = Util.make(new Object2ObjectLinkedOpenHashMap(), _snowman -> {
      _snowman.put(TexturedRenderLayers.getEntitySolid(), this.blockBuilders.get(RenderLayer.getSolid()));
      _snowman.put(TexturedRenderLayers.getEntityCutout(), this.blockBuilders.get(RenderLayer.getCutout()));
      _snowman.put(TexturedRenderLayers.getBannerPatterns(), this.blockBuilders.get(RenderLayer.getCutoutMipped()));
      _snowman.put(TexturedRenderLayers.getEntityTranslucentCull(), this.blockBuilders.get(RenderLayer.getTranslucent()));
      assignBufferBuilder(_snowman, TexturedRenderLayers.getShieldPatterns());
      assignBufferBuilder(_snowman, TexturedRenderLayers.getBeds());
      assignBufferBuilder(_snowman, TexturedRenderLayers.getShulkerBoxes());
      assignBufferBuilder(_snowman, TexturedRenderLayers.getSign());
      assignBufferBuilder(_snowman, TexturedRenderLayers.getChest());
      assignBufferBuilder(_snowman, RenderLayer.getTranslucentNoCrumbling());
      assignBufferBuilder(_snowman, RenderLayer.getArmorGlint());
      assignBufferBuilder(_snowman, RenderLayer.getArmorEntityGlint());
      assignBufferBuilder(_snowman, RenderLayer.getGlint());
      assignBufferBuilder(_snowman, RenderLayer.getDirectGlint());
      assignBufferBuilder(_snowman, RenderLayer.method_30676());
      assignBufferBuilder(_snowman, RenderLayer.getEntityGlint());
      assignBufferBuilder(_snowman, RenderLayer.getDirectEntityGlint());
      assignBufferBuilder(_snowman, RenderLayer.getWaterMask());
      ModelLoader.BLOCK_DESTRUCTION_RENDER_LAYERS.forEach(_snowmanxx -> assignBufferBuilder(_snowman, _snowmanxx));
   });
   private final VertexConsumerProvider.Immediate entityVertexConsumers = VertexConsumerProvider.immediate(this.entityBuilders, new BufferBuilder(256));
   private final VertexConsumerProvider.Immediate effectVertexConsumers = VertexConsumerProvider.immediate(new BufferBuilder(256));
   private final OutlineVertexConsumerProvider outlineVertexConsumers = new OutlineVertexConsumerProvider(this.entityVertexConsumers);

   public BufferBuilderStorage() {
   }

   private static void assignBufferBuilder(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferBuilder> builderStorage, RenderLayer layer) {
      builderStorage.put(layer, new BufferBuilder(layer.getExpectedBufferSize()));
   }

   public BlockBufferBuilderStorage getBlockBufferBuilders() {
      return this.blockBuilders;
   }

   public VertexConsumerProvider.Immediate getEntityVertexConsumers() {
      return this.entityVertexConsumers;
   }

   public VertexConsumerProvider.Immediate getEffectVertexConsumers() {
      return this.effectVertexConsumers;
   }

   public OutlineVertexConsumerProvider getOutlineVertexConsumers() {
      return this.outlineVertexConsumers;
   }
}
