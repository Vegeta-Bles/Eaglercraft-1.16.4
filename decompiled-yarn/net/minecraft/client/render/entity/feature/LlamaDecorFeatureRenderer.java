package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.LlamaEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class LlamaDecorFeatureRenderer extends FeatureRenderer<LlamaEntity, LlamaEntityModel<LlamaEntity>> {
   private static final Identifier[] LLAMA_DECOR = new Identifier[]{
      new Identifier("textures/entity/llama/decor/white.png"),
      new Identifier("textures/entity/llama/decor/orange.png"),
      new Identifier("textures/entity/llama/decor/magenta.png"),
      new Identifier("textures/entity/llama/decor/light_blue.png"),
      new Identifier("textures/entity/llama/decor/yellow.png"),
      new Identifier("textures/entity/llama/decor/lime.png"),
      new Identifier("textures/entity/llama/decor/pink.png"),
      new Identifier("textures/entity/llama/decor/gray.png"),
      new Identifier("textures/entity/llama/decor/light_gray.png"),
      new Identifier("textures/entity/llama/decor/cyan.png"),
      new Identifier("textures/entity/llama/decor/purple.png"),
      new Identifier("textures/entity/llama/decor/blue.png"),
      new Identifier("textures/entity/llama/decor/brown.png"),
      new Identifier("textures/entity/llama/decor/green.png"),
      new Identifier("textures/entity/llama/decor/red.png"),
      new Identifier("textures/entity/llama/decor/black.png")
   };
   private static final Identifier TRADER_LLAMA_DECOR = new Identifier("textures/entity/llama/decor/trader_llama.png");
   private final LlamaEntityModel<LlamaEntity> model = new LlamaEntityModel<>(0.5F);

   public LlamaDecorFeatureRenderer(FeatureRendererContext<LlamaEntity, LlamaEntityModel<LlamaEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, LlamaEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      DyeColor _snowmanxxxxxxxxxx = _snowman.getCarpetColor();
      Identifier _snowmanxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxx != null) {
         _snowmanxxxxxxxxxxx = LLAMA_DECOR[_snowmanxxxxxxxxxx.getId()];
      } else {
         if (!_snowman.isTrader()) {
            return;
         }

         _snowmanxxxxxxxxxxx = TRADER_LLAMA_DECOR;
      }

      this.getContextModel().copyStateTo(this.model);
      this.model.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      VertexConsumer _snowmanxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityCutoutNoCull(_snowmanxxxxxxxxxxx));
      this.model.render(_snowman, _snowmanxxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
