package net.minecraft.client.render.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class EndCrystalEntityRenderer extends EntityRenderer<EndCrystalEntity> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal.png");
   private static final RenderLayer END_CRYSTAL = RenderLayer.getEntityCutoutNoCull(TEXTURE);
   private static final float SINE_45_DEGREES = (float)Math.sin(Math.PI / 4);
   private final ModelPart core;
   private final ModelPart frame;
   private final ModelPart bottom;

   public EndCrystalEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
      this.shadowRadius = 0.5F;
      this.frame = new ModelPart(64, 32, 0, 0);
      this.frame.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.core = new ModelPart(64, 32, 32, 0);
      this.core.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.bottom = new ModelPart(64, 32, 0, 16);
      this.bottom.addCuboid(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F);
   }

   public void render(EndCrystalEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      _snowman.push();
      float _snowmanxxxxxx = getYOffset(_snowman, _snowman);
      float _snowmanxxxxxxx = ((float)_snowman.endCrystalAge + _snowman) * 3.0F;
      VertexConsumer _snowmanxxxxxxxx = _snowman.getBuffer(END_CRYSTAL);
      _snowman.push();
      _snowman.scale(2.0F, 2.0F, 2.0F);
      _snowman.translate(0.0, -0.5, 0.0);
      int _snowmanxxxxxxxxx = OverlayTexture.DEFAULT_UV;
      if (_snowman.getShowBottom()) {
         this.bottom.render(_snowman, _snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
      }

      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxx));
      _snowman.translate(0.0, (double)(1.5F + _snowmanxxxxxx / 2.0F), 0.0);
      _snowman.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      this.frame.render(_snowman, _snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxx = 0.875F;
      _snowman.scale(0.875F, 0.875F, 0.875F);
      _snowman.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxx));
      this.frame.render(_snowman, _snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
      _snowman.scale(0.875F, 0.875F, 0.875F);
      _snowman.multiply(new Quaternion(new Vector3f(SINE_45_DEGREES, 0.0F, SINE_45_DEGREES), 60.0F, true));
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxx));
      this.core.render(_snowman, _snowmanxxxxxxxx, _snowman, _snowmanxxxxxxxxx);
      _snowman.pop();
      _snowman.pop();
      BlockPos _snowmanxxxxxxxxxxx = _snowman.getBeamTarget();
      if (_snowmanxxxxxxxxxxx != null) {
         float _snowmanxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxx.getX() + 0.5F;
         float _snowmanxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxx.getY() + 0.5F;
         float _snowmanxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxx.getZ() + 0.5F;
         float _snowmanxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxx - _snowman.getX());
         float _snowmanxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxx - _snowman.getY());
         float _snowmanxxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxx - _snowman.getZ());
         _snowman.translate((double)_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxxxx);
         EnderDragonEntityRenderer.renderCrystalBeam(-_snowmanxxxxxxxxxxxxxxx, -_snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxx, -_snowmanxxxxxxxxxxxxxxxxx, _snowman, _snowman.endCrystalAge, _snowman, _snowman, _snowman);
      }

      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static float getYOffset(EndCrystalEntity crystal, float tickDelta) {
      float _snowman = (float)crystal.endCrystalAge + tickDelta;
      float _snowmanx = MathHelper.sin(_snowman * 0.2F) / 2.0F + 0.5F;
      _snowmanx = (_snowmanx * _snowmanx + _snowmanx) * 0.4F;
      return _snowmanx - 1.4F;
   }

   public Identifier getTexture(EndCrystalEntity _snowman) {
      return TEXTURE;
   }

   public boolean shouldRender(EndCrystalEntity _snowman, Frustum _snowman, double _snowman, double _snowman, double _snowman) {
      return super.shouldRender(_snowman, _snowman, _snowman, _snowman, _snowman) || _snowman.getBeamTarget() != null;
   }
}
