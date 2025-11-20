package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargePufferfishEntityModel;
import net.minecraft.client.render.entity.model.MediumPufferfishEntityModel;
import net.minecraft.client.render.entity.model.SmallPufferfishEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.PufferfishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class PufferfishEntityRenderer extends MobEntityRenderer<PufferfishEntity, EntityModel<PufferfishEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fish/pufferfish.png");
   private int modelSize;
   private final SmallPufferfishEntityModel<PufferfishEntity> smallModel = new SmallPufferfishEntityModel<>();
   private final MediumPufferfishEntityModel<PufferfishEntity> mediumModel = new MediumPufferfishEntityModel<>();
   private final LargePufferfishEntityModel<PufferfishEntity> largeModel = new LargePufferfishEntityModel<>();

   public PufferfishEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new LargePufferfishEntityModel<>(), 0.2F);
      this.modelSize = 3;
   }

   public Identifier getTexture(PufferfishEntity _snowman) {
      return TEXTURE;
   }

   public void render(PufferfishEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      int _snowmanxxxxxx = _snowman.getPuffState();
      if (_snowmanxxxxxx != this.modelSize) {
         if (_snowmanxxxxxx == 0) {
            this.model = this.smallModel;
         } else if (_snowmanxxxxxx == 1) {
            this.model = this.mediumModel;
         } else {
            this.model = this.largeModel;
         }
      }

      this.modelSize = _snowmanxxxxxx;
      this.shadowRadius = 0.1F + 0.1F * (float)_snowmanxxxxxx;
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   protected void setupTransforms(PufferfishEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.translate(0.0, (double)(MathHelper.cos(_snowman * 0.05F) * 0.08F), 0.0);
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
